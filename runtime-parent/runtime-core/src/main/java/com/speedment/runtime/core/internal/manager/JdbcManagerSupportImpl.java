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
package com.speedment.runtime.core.internal.manager;

import com.speedment.common.injector.Injector;
import com.speedment.common.lazy.Lazy;
import com.speedment.common.lazy.LazyReference;
import com.speedment.common.lazy.specialized.LazyString;
import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.PrimaryKeyColumn;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DatabaseNamingConvention;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.core.db.SqlRunnable;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.field.Field;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.ReferenceStreamBuilder;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.config.util.DocumentUtil;

import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.stream.StreamDecorator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.runtime.config.util.DocumentUtil.Name.DATABASE_NAME;
import com.speedment.runtime.core.manager.JdbcManagerSupport;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import com.speedment.runtime.config.util.DocumentDbUtil;
import static com.speedment.runtime.core.util.DatabaseUtil.dbmsTypeOf;
import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static com.speedment.runtime.config.util.DocumentDbUtil.isSame;
import static com.speedment.runtime.config.util.DocumentDbUtil.referencedTable;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class JdbcManagerSupportImpl<ENTITY> implements JdbcManagerSupport<ENTITY> {
    
    private final Injector injector;
    private final Manager<ENTITY> manager;
    
    private final LazyString sqlColumnList;
    private final LazyString sqlTableReference;
    private final LazyString sqlSelect;
    private final Map<String, Field<ENTITY>> fieldMap;
    private final boolean hasPrimaryKeyColumns;

    private final SqlFunction<ResultSet, ENTITY> entityMapper;

    private final DbmsHandlerComponent dbmsHandlerComponent;
    private final ResultSetMapperComponent resultSetMapperComponent;
    
    private final Project project;
    private final Table table;
    private final Schema schema;
    private final Dbms dbms;
    private final Lazy<DbmsType> dbmsType;
    private final Lazy<DbmsOperationHandler> dbmsOperationHandler;
    
    public JdbcManagerSupportImpl(
            Injector injector, 
            Manager<ENTITY> manager, 
            SqlFunction<ResultSet, ENTITY> entityMapper
    ) {
        this.injector     = requireNonNull(injector);
        this.manager      = requireNonNull(manager);
        this.entityMapper = requireNonNull(entityMapper);
        
        this.sqlColumnList     = LazyString.create();
        this.sqlTableReference = LazyString.create();
        this.sqlSelect         = LazyString.create();

        this.hasPrimaryKeyColumns = manager.primaryKeyFields().findAny().isPresent();
        
        this.dbmsHandlerComponent     = injector.getOrThrow(DbmsHandlerComponent.class);
        this.resultSetMapperComponent = injector.getOrThrow(ResultSetMapperComponent.class);
        
        this.project = injector.getOrThrow(ProjectComponent.class).getProject();
        this.table   = referencedTable(project, manager.getTableIdentifier());

        this.schema = table.getParentOrThrow();
        this.dbms = schema.getParentOrThrow();       
        this.dbmsType = LazyReference.create();
        this.dbmsOperationHandler = LazyReference.create();
        
        // Only include fields that point towards a column in this table.
        // In the future we might add fields that reference columns in foreign
        // tables.
        this.fieldMap = manager.fields()
            .filter(f -> f.findColumn(project)
                .map(c -> c.getParent())
                .map(t -> isSame(table, t.get()))
                .orElse(false)
            ).collect(toMap(f -> f.identifier().getColumnName(), identity()));
    }

    @Override
    public Manager<ENTITY> getManager() {
        return manager;
    }

    @Override
    public String fullColumnName(Field<ENTITY> field) {
        return naming().fullNameOf(field.identifier());
    }

    @Override
    public Stream<ENTITY> stream() {
        return nativeStream(StreamDecorator.identity());
    }

    @Override
    public ENTITY persist(ENTITY entity) throws SpeedmentException {
        return persistHelp(entity);
    }

    @Override
    public ENTITY update(ENTITY entity) throws SpeedmentException {
        return updateHelper(entity);
    }

    @Override
    public ENTITY remove(ENTITY entity) throws SpeedmentException {
        return removeHelper(entity);
    }

    private Stream<ENTITY> nativeStream(StreamDecorator decorator) {
        final AsynchronousQueryResult<ENTITY> asynchronousQueryResult = decorator.apply(dbmsOperationHandler().executeQueryAsync(dbms, sqlSelect(), Collections.emptyList(), entityMapper));
        final SqlStreamTerminator<ENTITY> terminator = new SqlStreamTerminator<>(injector, this, asynchronousQueryResult, decorator);
        final Supplier<BaseStream<?, ?>> initialSupplier = () -> decorator.applyOnInitial(asynchronousQueryResult.stream());
        final Stream<ENTITY> result = decorator.applyOnFinal(new ReferenceStreamBuilder<>(new PipelineImpl<>(initialSupplier), terminator));

        // Make sure we are closing the ResultSet, Statement and Connection later
        result.onClose(asynchronousQueryResult::close);

        return result;
    }
    
    private <T> Stream<T> synchronousStreamOf(String sql, List<Object> values, SqlFunction<ResultSet, T> rsMapper) {
        requireNonNulls(sql, values, rsMapper);
        return dbmsOperationHandler().executeQuery(dbms, sql, values, rsMapper);
    }

    /**
     * Counts the number of elements in the current table by querying the
     * database.
     *
     * @return the number of elements in the table
     */
    @Override
    public long sqlCount() {
        return synchronousStreamOf(
            "SELECT COUNT(*) FROM " + sqlTableReference(),
            Collections.emptyList(),
            rs -> rs.getLong(1)
        ).findAny().get();
    }

    /**
     * Returns a {@code SELECT/FROM} SQL statement with the full column list and
     * the current table specified in accordance to the current
     * {@link DbmsType}. The specified statement will not have any trailing
     * spaces or semicolons.
     * <p>
     * <b>Example:</b>
     * <code>SELECT `id`, `name` FROM `myschema`.`users`</code>
     *
     * @return the SQL statement
     */
    @Override
    public String sqlSelect() {
        return sqlSelect.getOrCompute(() -> "SELECT " + sqlColumnList() + " FROM " + sqlTableReference());
    }

    private DbmsType dbmsType() {
        return dbmsType.getOrCompute(() -> dbmsTypeOf(dbmsHandlerComponent, dbms));
    }
    
    private DbmsOperationHandler dbmsOperationHandler() {
        return dbmsOperationHandler.getOrCompute(()-> dbmsType().getOperationHandler());
    }
    


    /**
     * Returns the column corresponding to a particular field in an entity
     * managed by this manager.
     * 
     * @param field  the field
     * @return       the corresponding Column
     */
    private Column getColumn(Field<ENTITY> field) {
        return field.findColumn(project)
            .orElseThrow(() -> new SpeedmentException(
                "Could not find column '" + field.identifier() + "'."
            )
        );
    }
    
    /**
     * Short-cut for retrieving the current {@link DatabaseNamingConvention}.
     *
     * @return the current naming convention
     */
    private DatabaseNamingConvention naming() {
        return dbmsType().getDatabaseNamingConvention();
    }

    /**
     * Returns a comma separated list of column names, fully formatted in
     * accordance to the current {@link DbmsType}.
     *
     * @return the comma separated column list
     */
    private String sqlColumnList() {
        return sqlColumnList.getOrCompute(() -> sqlColumnList(Function.identity()));
    }

    /**
     * Returns a {@code AND} separated list of {@link PrimaryKeyColumn} database
     * names, formatted in accordance to the current {@link DbmsType}.
     *
     * @param postMapper mapper to be applied to each column name
     * @return list of fully quoted primary key column names
     */
    private String sqlColumnList(Function<String, String> postMapper) {
        requireNonNull(postMapper);
        return table.columns()
            .filter(Column::isEnabled)
            .map(Column::getName)
            .map(naming()::encloseField)
            .map(postMapper)
            .collect(joining(","));
    }

    /**
     * Returns a {@code AND} separated list of {@link PrimaryKeyColumn} database
     * names, formatted in accordance to the current {@link DbmsType}.
     *
     * @return list of fully quoted primary key column names
     */
    private String sqlPrimaryKeyColumnList(Function<String, String> postMapper) {
        requireNonNull(postMapper);
        return table.primaryKeyColumns()
            .map(this::findColumn)
            .map(Column::getName)
            .map(naming()::encloseField)
            .map(postMapper)
            .collect(joining(" AND "));
    }

    private Column findColumn(PrimaryKeyColumn pkc) {
        return pkc.findColumn()
            .orElseThrow(() -> new SpeedmentException(
                "Cannot find column for " + pkc
            ));
    }

    /**
     * Returns the full name of a table formatted in accordance to the current
     * {@link DbmsType}. The returned value will be within quotes if that is
     * what the database expects.
     *
     * @return the full quoted table name
     */
    private String sqlTableReference() {
        return sqlTableReference.getOrCompute(() -> naming().fullNameOf(table));
    }

    private <F extends Field<ENTITY>> Object toDatabaseType(F field, ENTITY entity) {
        final Object javaValue = field.getter().apply(entity);
        
        @SuppressWarnings("unchecked")
        final Object dbValue = ((TypeMapper<Object, Object>) field.typeMapper()).toDatabaseType(javaValue);
        
        return dbValue;
    }

    private ENTITY persistHelp(ENTITY entity) throws SpeedmentException {
        final List<Column> cols = persistColumns(entity);
        String sb = "INSERT INTO " + sqlTableReference() +
            " (" + persistColumnList(cols) + ")" +
            " VALUES " +
            "(" + persistColumnListWithQuestionMarks(cols) + ")";

        @SuppressWarnings("unchecked")
        final List<Object> values = cols.stream()
            .map(Column::getName)
            .map(fieldMap::get)
            .map(f -> toDatabaseType(f, entity))
            .collect(toList());
 
        @SuppressWarnings("unchecked")
        final Map<Field<ENTITY>, Column> generatedFields = MapStream.fromKeys(manager.fields(), 
            f -> DocumentDbUtil.referencedColumn(project, f.identifier()))
                .filterValue(Column::isAutoIncrement)
                .toMap();

        final Function<ENTITY, Consumer<List<Long>>> generatedKeyconsumer = newEntity -> {
            return l -> {
                if (!l.isEmpty()) {
                    final AtomicInteger cnt = new AtomicInteger();
                    // Just assume that they are in order, what else is there to do?
                    generatedFields
                        .forEach((f, col) -> {

                            // Cast from Long to the column target type
                            final Object val = resultSetMapperComponent
                                .apply(col.findDatabaseType())
                                .parse(l.get(cnt.getAndIncrement()));

                            @SuppressWarnings("unchecked")
                            final Object javaValue = ((TypeMapper<Object, Object>) 
                                f.typeMapper()).toJavaType(col, manager.getEntityClass(), val);
                            
                            f.setter().set(newEntity, javaValue);
                        });
                }
            };
        };

        executeInsert(entity, sb, values, generatedFields.keySet(), generatedKeyconsumer);
        return entity;
    }

    private ENTITY updateHelper(ENTITY entity) throws SpeedmentException {
        assertHasPrimaryKeyColumns();
        String sb = "UPDATE " + sqlTableReference() + " SET " +
            sqlColumnList(n -> n + " = ?") +
            " WHERE " +
            sqlPrimaryKeyColumnList(pk -> pk + " = ?");

        final List<Object> values = manager.fields()
            .map(f -> toDatabaseType(f, entity))
            .collect(Collectors.toList());

        manager.primaryKeyFields()
            .map(f -> f.getter().apply(entity))
            .forEachOrdered(values::add);

        executeUpdate(sb, values);
        return entity;
    }

    private ENTITY removeHelper(ENTITY entity) throws SpeedmentException {
        assertHasPrimaryKeyColumns();
        String sb = "DELETE FROM " + sqlTableReference() +
            " WHERE " +
            sqlPrimaryKeyColumnList(pk -> pk + " = ?");

        final List<Object> values = manager.primaryKeyFields()
            .map(f -> toDatabaseType(f, entity))
            .collect(toList());

        executeDelete(sb, values);
        return entity;
    }

    private String persistColumnList(List<Column> cols) {
        return cols.stream()
            .map(Column::getName)
            .map(naming()::encloseField)
            .collect(joining(","));
    }

    private String persistColumnListWithQuestionMarks(List<Column> cols) {
        return cols.stream()
            .map(c -> "?")
            .collect(joining(","));
    }

    /**
     * Returns a List of the columns that shall be used in an insert/update
     * statement. Some database types (e.g. PostgreSQL) does not allow auto
     * increment columns that are null in an insert/update statement.
     *
     * @param entity to be inserted/updated
     * @return a List of the columns that shall be used in an insert/update
     * statement
     */
    protected List<Column> persistColumns(ENTITY entity) {
        return table.columns()
            .filter(c -> isPersistColumn(entity, c))
            .collect(toList());
    }

    /**
     * Returns if a columns that shall be used in an insert/update statement.
     * Some database types (e.g. PostgreSQL) does not allow auto increment columns
     * that are null in an insert/update statement.
     *
     * @param entity to be inserted/updated
     * @param c column
     * @return if a columns that shall be used in an insert/update statement
     */
    protected boolean isPersistColumn(ENTITY entity, Column c) {
        if (c.isAutoIncrement()) {
            final Field<ENTITY> field = fieldMap.get(c.getName());
            if (field != null) {
                final Object colValue = field.getter().apply(entity);
                if (colValue != null) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private void executeInsert(
        final ENTITY entity,
        final String sql,
        final List<Object> values,
        final Collection<Field<ENTITY>> generatedFields,
        final Function<ENTITY, Consumer<List<Long>>> generatedKeyconsumer
    ) throws SpeedmentException {
        executeHelper(sql, values, 
            () -> dbmsOperationHandler().executeInsert(
                dbms, sql, values, generatedFields, generatedKeyconsumer.apply(entity)
            )
        );
    }

    private void executeUpdate(
        final String sql,
        final List<Object> values
    ) throws SpeedmentException {
        executeHelper(sql, values, () -> dbmsOperationHandler().executeUpdate(dbms, sql, values));
    }

    private void executeDelete(
        final String sql,
        final List<Object> values
    ) throws SpeedmentException {
        executeHelper(sql, values,  () -> dbmsOperationHandler().executeDelete(dbms, sql, values));
    }

    private void executeHelper(
        String sql,
        List<Object> values,
        SqlRunnable action
    ) throws SpeedmentException {
        requireNonNulls(sql, values, action);
        try {
            action.run();
        } catch (final SQLException sqle) {
            throw new SpeedmentException(sqle);
        }
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