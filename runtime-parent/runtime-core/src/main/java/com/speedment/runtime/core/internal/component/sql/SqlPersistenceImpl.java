/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.core.internal.component.sql;

import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.config.*;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.core.component.resultset.ResultSetMapping;
import com.speedment.runtime.core.db.DatabaseNamingConvention;
import com.speedment.runtime.core.db.DbmsColumnHandler;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.util.DatabaseUtil;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.typemapper.TypeMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static com.speedment.runtime.config.util.DocumentUtil.Name.DATABASE_NAME;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Default implementation of the {@link SqlPersistence}-interface.
 * 
 * @param <ENTITY>  the entity type
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
final class SqlPersistenceImpl<ENTITY> implements SqlPersistence<ENTITY> {

    private final Supplier<Stream<Field<ENTITY>>> primaryKeyFields;
    private final Supplier<Stream<Field<ENTITY>>> fields;
    
    private final Dbms dbms;
    private final Table table;
    private final DbmsType dbmsType;
    private final String sqlTableReference;
    private final boolean hasPrimaryKeyColumns;
    private final DatabaseNamingConvention naming;
    private final DbmsOperationHandler operationHandler;
    private final DbmsColumnHandler columnHandler;
    private final Class<ENTITY> entityClass;
    
    private final String insertStatement;
    private final String updateStatement;
    private final String deleteStatement;
    
    private final List<GeneratedFieldSupport<ENTITY, ?>> generatedFieldSupports;
    private final List<Field<ENTITY>> generatedFields;
    private final Map<Field<ENTITY>, Column> columnsByFields;


    public SqlPersistenceImpl(
            TableIdentifier<ENTITY> tableId,
            ProjectComponent projectComponent,
            DbmsHandlerComponent dbmsHandlerComponent,
            ManagerComponent managerComponent,
            ResultSetMapperComponent resultSetMapperComponent) {
        
        requireNonNulls(tableId, 
            projectComponent, 
            dbmsHandlerComponent, 
            managerComponent, 
            resultSetMapperComponent
        );

        final Project project = projectComponent.getProject();
        
        this.table = DocumentDbUtil.referencedTable(project, tableId);
        this.dbms  = DocumentDbUtil.referencedDbms(project, tableId);
        this.dbmsType = DatabaseUtil.dbmsTypeOf(dbmsHandlerComponent, dbms);
        this.naming           = dbmsType.getDatabaseNamingConvention();
        this.operationHandler = dbmsType.getOperationHandler();
        this.columnHandler    = dbmsType.getColumnHandler();
        
        @SuppressWarnings("unchecked")
        final Manager<ENTITY> manager = (Manager<ENTITY>) managerComponent.stream()
            .filter(m -> tableId.equals(m.getTableIdentifier()))
            .findAny().orElseThrow(() -> new SpeedmentException(
                "Could not find any manager for table '" + tableId + "'."
            ));
        
        this.primaryKeyFields = manager::primaryKeyFields;
        this.fields           = manager::fields;
        this.entityClass      = manager.getEntityClass();
        
        this.sqlTableReference = naming.fullNameOf(table);
        this.hasPrimaryKeyColumns = manager.primaryKeyFields().anyMatch(m -> true);

        final Predicate<Column> included = columnHandler.excludedInInsertStatement().negate();
        this.insertStatement = "INSERT INTO " + sqlTableReference + " (" +
            sqlColumnList(included, identity()) + ") VALUES (" +
            sqlColumnList(included, c -> "?") + ")";

        this.updateStatement = "UPDATE " + sqlTableReference + " SET " +
            sqlColumnList(c -> true, n -> n + " = ?") + " WHERE " +
            sqlPrimaryKeyColumnList(pk -> pk + " = ?");
        this.deleteStatement = "DELETE FROM " + sqlTableReference + " WHERE " +
            sqlPrimaryKeyColumnList(pk -> pk + " = ?");

        this.columnsByFields = MapStream.fromKeys(fields.get(), f ->
            DocumentDbUtil.referencedColumn(project, f.identifier())
        ).toMap();

        this.generatedFieldSupports = columnsByFields.entrySet().stream().filter(e -> e.getValue().isAutoIncrement())
        .map(e -> new GeneratedFieldSupport<>(
            e.getKey(), e.getValue(),
            resultSetMapperComponent.apply(e.getValue().findDatabaseType())
        )).collect(toList());
        
        this.generatedFields = generatedFieldSupports.stream()
            .map(GeneratedFieldSupport::getField).collect(toList());
    }
    
    @Override
    public ENTITY persist(ENTITY entity) throws SpeedmentException {
        final List<Object> values = fields.get()
            .filter(f -> !columnHandler.excludedInInsertStatement().test(columnsByFields.get(f)))
            .map(f -> toDatabaseType(f, entity))
            .collect(toList());

        try {
            operationHandler.executeInsert(dbms, insertStatement, values, generatedFields, newGeneratedKeyConsumer(entity));
            return entity;
        } catch (final SQLException ex) {
            throw new SpeedmentException(ex);
        }
    }
    
    @Override
    public ENTITY update(ENTITY entity) throws SpeedmentException {
        assertHasPrimaryKeyColumns();

        final List<Object> values = Stream.concat(
            fields.get(), 
            primaryKeyFields.get()
        )
            .map(f -> toDatabaseType(f, entity))
            .collect(Collectors.toList());

        try {
            operationHandler.executeUpdate(dbms, updateStatement, values);
            return entity;
        } catch (final SQLException ex) {
            throw new SpeedmentException(ex);
        }
    }

    @Override
    public ENTITY remove(ENTITY entity) throws SpeedmentException {
        assertHasPrimaryKeyColumns();
        
        final List<Object> values = primaryKeyFields.get()
            .map(f -> toDatabaseType(f, entity))
            .collect(toList());

        try {
            operationHandler.executeDelete(dbms, deleteStatement, values);
            return entity;
        } catch (final SQLException ex) {
            throw new SpeedmentException(ex);
        }
    }
    
    private Consumer<List<Long>> newGeneratedKeyConsumer(ENTITY entity) {
        return l -> {
            if (!l.isEmpty()) {
                final AtomicInteger cnt = new AtomicInteger();

                // Just assume that they are in order, what else is there to do?
                generatedFieldSupports.forEach(generated -> {

                    // Cast from Long to the column target type
                    final Object val = generated.mapping
                        .parse(l.get(cnt.getAndIncrement()));

                    @SuppressWarnings("unchecked")
                    final Object javaValue = ((TypeMapper<Object, Object>) 
                        generated.field.typeMapper()
                        ).toJavaType(generated.column, entityClass, val);

                    generated.field.setter().set(entity, javaValue);
                });
            }
        };
    }
    
    private <F extends Field<ENTITY>> Object toDatabaseType(F field, ENTITY entity) {
        final Object javaValue = field.getter().apply(entity);
        
        @SuppressWarnings("unchecked")
        final Object dbValue = ((TypeMapper<Object, Object>) field.typeMapper()).toDatabaseType(javaValue);
        
        return dbValue;
    }
    
    private String sqlPrimaryKeyColumnList(Function<String, String> postMapper) {
        requireNonNull(postMapper);
        return table.primaryKeyColumns()
            .sorted(comparing(PrimaryKeyColumn::getOrdinalPosition))
            .map(this::findColumn)
            .map(Column::getName)
            .map(naming::encloseField)
            .map(postMapper)
            .collect(joining(" AND "));
    }

    private String sqlColumnList(Predicate<Column> preFilter, Function<String, String> postMapper) {
        return table.columns()
            .sorted(comparing(Column::getOrdinalPosition))
            .filter(Column::isEnabled)
            .filter(preFilter)
            .map(Column::getName)
            .map(naming::encloseField)
            .map(postMapper)
            .collect(joining(","));
    }
    
    private Column findColumn(PrimaryKeyColumn pkc) {
        return pkc.findColumn()
            .orElseThrow(() -> new SpeedmentException(
                "Cannot find column for " + pkc
            ));
    }
    
    private void assertHasPrimaryKeyColumns() {
        if (!hasPrimaryKeyColumns) {
            throw new SpeedmentException(
                "The table "
                + DocumentUtil.relativeName(table, Project.class, DATABASE_NAME)
                + " does not have any primary keys. Some operations like "
                + "update() and remove() requires at least one primary key."
            );
        }
    }
    
    private final static class GeneratedFieldSupport<ENTITY, T> {

        private final Field<ENTITY> field;
        private final Column column;
        private final ResultSetMapping<T> mapping;

        private GeneratedFieldSupport(
            final Field<ENTITY> field,
            final Column column,
            final ResultSetMapping<T> mapping
        ) {
            this.field = field;
            this.column = column;
            this.mapping = mapping;
        }

        public Field<ENTITY> getField() {
            return field;
        }

        public Column getColumn() {
            return column;
        }

        public ResultSetMapping<T> getMapping() {
            return mapping;
        }
    }
    
    
}