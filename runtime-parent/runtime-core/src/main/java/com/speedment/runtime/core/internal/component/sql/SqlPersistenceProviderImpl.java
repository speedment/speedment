/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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

import com.speedment.runtime.config.*;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.config.trait.HasColumn;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.component.PersistenceTableInfo;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.core.component.resultset.ResultSetMapping;
import com.speedment.runtime.core.db.DatabaseNamingConvention;
import com.speedment.runtime.core.db.DbmsColumnHandler;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.manager.*;
import com.speedment.runtime.core.util.DatabaseUtil;
import com.speedment.runtime.core.util.MergeUtil;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.typemapper.TypeMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.runtime.config.util.DocumentUtil.Name.DATABASE_NAME;
import static java.util.Collections.singleton;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.*;

/**
 * Default implementation of the {@link SqlPersistence}-interface.
 * 
 * @param <ENTITY>  the entity type
 *
 * @author  Emil Forslund
 * @author  Dan Lawesson
 * @since   3.0.1
 */
final class SqlPersistenceProviderImpl<ENTITY> implements PersistenceProvider<ENTITY> {

    private final Supplier<Stream<Field<ENTITY>>> primaryKeyFields;
    private final Supplier<Stream<Field<ENTITY>>> fields;
    
    private final Dbms dbms;
    private final Table table;
    private final String sqlTableReference;
    private final boolean hasPrimaryKeyColumns;
    private final DatabaseNamingConvention naming;
    private final DbmsOperationHandler operationHandler;
    private final ManagerComponent managerComponent;
    private final Class<ENTITY> entityClass;
    
    private final String insertStatement;
    private final String updateStatement;
    private final String deleteStatement;
    
    private final List<GeneratedFieldSupport<ENTITY, ?>> generatedFieldSupports;
    private final List<Field<ENTITY>> generatedFields;
    private final Map<Field<ENTITY>, Column> columnsByFields;
    private final Predicate<Column> insertColumnFilter;
    private final Predicate<Column> updateColumnFilter;

    SqlPersistenceProviderImpl(
        final PersistenceTableInfo<ENTITY> tableInfo,
        final ProjectComponent projectComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final ManagerComponent managerComponent,
        final ResultSetMapperComponent resultSetMapperComponent
    ) {
        requireNonNull(tableInfo);
        requireNonNull(projectComponent);
        requireNonNull(dbmsHandlerComponent);
        requireNonNull(managerComponent);
        requireNonNull(resultSetMapperComponent);

        final Project project = projectComponent.getProject();

        final TableIdentifier<ENTITY> tableId = tableInfo.getTableIdentifier();
        this.table = DocumentDbUtil.referencedTable(project, tableId);
        this.dbms  = DocumentDbUtil.referencedDbms(project, tableId);
        final DbmsType dbmsType = DatabaseUtil.dbmsTypeOf(dbmsHandlerComponent, dbms);
        this.naming           = dbmsType.getDatabaseNamingConvention();
        this.operationHandler = dbmsType.getOperationHandler();
        final DbmsColumnHandler columnHandler = dbmsType.getColumnHandler();
        
        this.primaryKeyFields = tableInfo::primaryKeyFields;
        this.fields           = tableInfo::fields;
        this.entityClass      = tableInfo.getEntityClass();
        
        this.sqlTableReference = naming.fullNameOf(table);
        this.hasPrimaryKeyColumns = tableInfo.primaryKeyFields().anyMatch(m -> true);

        this.insertColumnFilter = columnHandler.excludedInInsertStatement().negate();
        this.insertStatement = getInsertStatement(insertColumnFilter);

        this.updateColumnFilter = columnHandler.excludedInUpdateStatement().negate();
        this.updateStatement = getUpdateStatement(updateColumnFilter);
        this.deleteStatement = "DELETE FROM " + sqlTableReference + " WHERE " +
            sqlPrimaryKeyColumnList(pk -> pk + " = ?");

         this.columnsByFields = fields.get()
            .collect(toMap(
                Function.identity(),
                f -> DocumentDbUtil.referencedColumn(project, f.identifier())
            ));

        this.generatedFieldSupports = columnsByFields.entrySet().stream().filter(e -> e.getValue().isAutoIncrement())
        .map(e -> new GeneratedFieldSupport<>(
            e.getKey(), e.getValue(),
            resultSetMapperComponent.apply(e.getValue().findDatabaseType())
        )).collect(toList());
        
        this.generatedFields = generatedFieldSupports.stream()
            .map(GeneratedFieldSupport::getField).collect(toList());

        this.managerComponent = requireNonNull(managerComponent);
    }

    private String getInsertStatement(Predicate<Column> includedInInsert) {
        return "INSERT INTO " + sqlTableReference + " (" +
            sqlColumnList(includedInInsert, identity()) + ") VALUES (" +
            sqlColumnList(includedInInsert, c -> "?") + ")";
    }

    @Override
    public Persister<ENTITY> persister() {
        return entity -> persist(entity, f -> insertColumnFilter.test(columnsByFields.get(f)), insertStatement);
    }

    @Override
    public Persister<ENTITY> persister(HasLabelSet<ENTITY> includedFields) {
        Predicate<Column> columns = insertColumnFilter.and(c -> includedFields.test(c.getId()));
        String statement = getInsertStatement(updateColumnFilter.and(c12 -> includedFields.test(c12.getId())));
        return entity -> persist(entity, f -> columns.test(columnsByFields.get(f)), statement);
    }

    private ENTITY persist(ENTITY entity, Predicate<Field<ENTITY>> includedFields, String insertStatement) {
        final List<Object> values = fields.get()
            .filter(includedFields)
            .map(f -> toDatabaseType(f, entity))
            .collect(toList());

        try {
            operationHandler.executeInsert(dbms, insertStatement, values, generatedFields, newGeneratedKeyConsumer(entity));
            return entity;
        } catch (final SQLException ex) {
            throw new SpeedmentException(ex);
        }
    }

    private String getUpdateStatement(Predicate<Column> includedInUpdate) {
        return "UPDATE " + sqlTableReference + " SET " +
            sqlColumnList(includedInUpdate, n -> n + " = ?") + " WHERE " +
            sqlPrimaryKeyColumnList(pk -> pk + " = ?");
    }

    @Override
    public Updater<ENTITY> updater() {
        assertHasPrimaryKeyColumns();
        return entity -> update(entity, f -> updateColumnFilter.test(columnsByFields.get(f)), updateStatement);
    }

    @Override
    public Updater<ENTITY> updater(HasLabelSet<ENTITY> includedFields) {
        assertHasPrimaryKeyColumns();
        Predicate<Column> columns = updateColumnFilter.and(c -> includedFields.test(c.getId()));
        String statement = getUpdateStatement(updateColumnFilter.and(c12 -> includedFields.test(c12.getId())));
        return entity -> update(entity, f -> columns.test(columnsByFields.get(f)), statement);
    }

    private ENTITY update(ENTITY entity, Predicate<Field<ENTITY>> includedFields, String updateStatement) {
        final List<Object> values = Stream.concat(
            fields.get().filter(includedFields),
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
    public Remover<ENTITY> remover() {
        assertHasPrimaryKeyColumns();
        return this::remove;
    }

    private ENTITY remove(ENTITY entity) {
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

    @Override
    public Merger<ENTITY> merger() {
        assertHasPrimaryKeyColumns();
        final Manager<ENTITY> manager = managerComponent.managerOf(entityClass);
        return entity -> merge(manager, entity);
    }

    private ENTITY merge(Manager<ENTITY> manager, ENTITY entity) {
        final Set<ENTITY> result = MergeUtil.merge(manager, singleton(entity));
        if (result.size() != 1) {
            throw new SpeedmentException("Unable to merge entity of type " + entity.getClass().getName());
        }
        return result.iterator().next();
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
    
    private String sqlPrimaryKeyColumnList(UnaryOperator<String> postMapper) {
        requireNonNull(postMapper);
        return table.primaryKeyColumns()
            .sorted(comparing(PrimaryKeyColumn::getOrdinalPosition))
            .map(HasColumn::findColumnOrThrow)
            .map(Column::getName)
            .map(naming::encloseField)
            .map(postMapper)
            .collect(joining(" AND "));
    }

    private String sqlColumnList(Predicate<Column> preFilter, UnaryOperator<String> postMapper) {
        return table.columns()
            .sorted(comparing(Column::getOrdinalPosition))
            .filter(Column::isEnabled)
            .filter(preFilter)
            .map(Column::getName)
            .map(naming::encloseField)
            .map(postMapper)
            .collect(joining(","));
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

    private static final class GeneratedFieldSupport<ENTITY, T> {

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