/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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

import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.PrimaryKeyColumn;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.config.util.DocumentUtil;
import static com.speedment.runtime.config.util.DocumentUtil.Name.DATABASE_NAME;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.core.component.resultset.ResultSetMapping;
import com.speedment.runtime.core.db.DatabaseNamingConvention;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.field.Field;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.util.DatabaseUtil;
import com.speedment.runtime.typemapper.TypeMapper;
import java.sql.SQLException;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import static java.util.function.Function.identity;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @param <ENTITY>  the entity type
 * 
 * @author  Emil Forslund
 * @since   1.0.0
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
    private final Class<ENTITY> entityClass;
    
    private final String insertStatement;
    private final String updateStatement;
    private final String deleteStatement;
    
    private final List<GeneratedFieldSupport<ENTITY, ?>> generatedFieldSupports;
    private final List<Field<ENTITY>> generatedFields;
    
    private final static class GeneratedFieldSupport<ENTITY, T> {
        
        private final Field<ENTITY> field;
        private final Column column;
        private final ResultSetMapping<T> mapping;

        public GeneratedFieldSupport(Field<ENTITY> field, Column column, ResultSetMapping<T> mapping) {
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
        
        this.insertStatement = "INSERT INTO " + sqlTableReference + " (" + 
            sqlColumnList(identity()) + ") VALUES (" + 
            sqlColumnList(c -> "?") + ")";
        
        this.updateStatement = "UPDATE " + sqlTableReference + " SET " +
            sqlColumnList(n -> n + " = ?") + " WHERE " + 
            sqlPrimaryKeyColumnList(pk -> pk + " = ?");
        this.deleteStatement = "DELETE FROM " + sqlTableReference + " WHERE " + 
            sqlPrimaryKeyColumnList(pk -> pk + " = ?");
        
        this.generatedFieldSupports = MapStream.fromKeys(fields.get(), f -> 
            DocumentDbUtil.referencedColumn(project, f.identifier())
        ).filterValue(Column::isAutoIncrement)
        .map((field, col) -> new GeneratedFieldSupport<>(
            field, col, 
            resultSetMapperComponent.apply(col.findDatabaseType())
        )).collect(toList());
        
        this.generatedFields = generatedFieldSupports.stream()
            .map(GeneratedFieldSupport::getField).collect(toList());
          
    }
    
    @Override
    public ENTITY persist(ENTITY entity) throws SpeedmentException {
        final List<Object> values = fields.get()
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
            .map(f -> f.getter().apply(entity))
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
            .map(this::findColumn)
            .map(Column::getName)
            .map(naming::encloseField)
            .map(postMapper)
            .collect(joining(" AND "));
    }
    
    private String sqlColumnList(Function<String, String> postMapper) {
        return table.columns()
            .filter(Column::isEnabled)
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
}