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
package com.speedment.runtime.internal.manager.sql;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.component.ManagerComponent;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.config.*;
import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.config.parameter.DbmsType;
import com.speedment.runtime.db.*;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.field.trait.ReferenceFieldTrait;
import com.speedment.runtime.internal.manager.AbstractManager;
import com.speedment.runtime.internal.manager.metaresult.SqlMetaResultImpl;
import com.speedment.runtime.internal.stream.builder.ReferenceStreamBuilder;
import com.speedment.runtime.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.internal.util.LazyString;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import com.speedment.runtime.internal.util.document.DocumentUtil;
import com.speedment.runtime.stream.StreamDecorator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.injector.State.RESOLVED;
import static com.speedment.runtime.internal.stream.OptionalUtil.unwrap;
import static com.speedment.runtime.internal.util.document.DocumentDbUtil.*;
import static com.speedment.runtime.internal.util.document.DocumentUtil.Name.DATABASE_NAME;
import static com.speedment.runtime.internal.util.document.DocumentUtil.ancestor;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 *
 * @param <ENTITY> entity type for this Manager
 *
 * @author Per Minborg
 */
public abstract class AbstractSqlManager<ENTITY> extends AbstractManager<ENTITY> implements SqlManager<ENTITY> {

    private final LazyString sqlColumnList;
    private final LazyString sqlTableReference;
    private final LazyString sqlSelect;
    private final Map<String, FieldTrait> fieldTraitMap;
    private final boolean hasPrimaryKeyColumns;

    private SqlFunction<ResultSet, ENTITY> entityMapper;

    private @Inject
    DbmsHandlerComponent dbmsHandlerComponent;
    private @Inject
    ResultSetMapperComponent resultSetMapperComponent;
    private @Inject
    ProjectComponent projectComponent;

    protected AbstractSqlManager() {
        this.sqlColumnList = LazyString.create();
        this.sqlTableReference = LazyString.create();
        this.sqlSelect = LazyString.create();
        this.fieldTraitMap = new HashMap<>();

        this.hasPrimaryKeyColumns = primaryKeyFields().findAny().isPresent();
    }

    @ExecuteBefore(INITIALIZED)
    void addToManager(
        @WithState(INITIALIZED) ManagerComponent managers,
        @WithState(INITIALIZED) ProjectComponent projectComponent) {
        requireNonNull(projectComponent); // Must be initalized for this to execute.
        managers.put(this);
    }

    @ExecuteBefore(RESOLVED)
    void createFieldTraitMap(@WithState(INITIALIZED) ProjectComponent projectComponent) {
        final Project project = projectComponent.getProject();
        final Table thisTable = getTable();

        // Only include fields that point towards a column in this table.
        // In the future we might add fields that reference columns in foreign
        // tables.
        fieldTraitMap.putAll(
            fields()
            .filter(f
                -> f.findColumn(project)
                .map(c -> c.getParent())
                .map(t -> isSame(thisTable, t.get()))
                .orElse(false)
            )
            .collect(Collectors.toMap(f -> f.getIdentifier().columnName(), identity()))
        );
    }

    @Override
    public Stream<ENTITY> nativeStream(StreamDecorator decorator) {
        final AsynchronousQueryResult<ENTITY> asynchronousQueryResult = decorator.apply(dbmsHandler().executeQueryAsync(getDbms(), sqlSelect(), Collections.emptyList(), entityMapper.unWrap()));
        final SqlStreamTerminator<ENTITY> terminator = new SqlStreamTerminator<>(this, asynchronousQueryResult, decorator);
        final Supplier<BaseStream<?, ?>> initialSupplier = () -> decorator.applyOnInitial(asynchronousQueryResult.stream());
        final Stream<ENTITY> result = decorator.applyOnFinal(new ReferenceStreamBuilder<>(new PipelineImpl<>(initialSupplier), terminator));

        // Make sure we are closing the ResultSet, Statement and Connection later
        result.onClose(asynchronousQueryResult::close);

        return result;
    }

    public <T> Stream<T> synchronousStreamOf(String sql, List<Object> values, SqlFunction<ResultSet, T> rsMapper) {
        requireNonNulls(sql, values, rsMapper);
        return dbmsHandler().executeQuery(getDbms(), sql, values, rsMapper);
    }

    /**
     * Counts the number of elements in the current table by querying the
     * database.
     *
     * @return the number of elements in the table
     */
    public long count() {
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
    public String sqlSelect() {
        return sqlSelect.getOrCompute(() -> "SELECT " + sqlColumnList() + " FROM " + sqlTableReference());
    }

    @Override
    public SqlFunction<ResultSet, ENTITY> getEntityMapper() {
        return entityMapper;
    }

    @Override
    public void setEntityMapper(SqlFunction<ResultSet, ENTITY> entityMapper) {
        this.entityMapper = requireNonNull(entityMapper);
    }

    @Override
    public ENTITY persist(ENTITY entity) throws SpeedmentException {
        return persistHelp(entity, Optional.empty());
    }

    @Override
    public ENTITY persist(ENTITY entity, Consumer<MetaResult<ENTITY>> listener) throws SpeedmentException {
        requireNonNulls(entity, listener);
        return persistHelp(entity, Optional.of(listener));
    }

    @Override
    public ENTITY update(ENTITY entity) {
        requireNonNull(entity);
        return updateHelper(entity, Optional.empty());
    }

    @Override
    public ENTITY update(ENTITY entity, Consumer<MetaResult<ENTITY>> listener) throws SpeedmentException {
        requireNonNulls(entity, listener);
        return updateHelper(entity, Optional.of(listener));
    }

    @Override
    public ENTITY remove(ENTITY entity) {
        requireNonNull(entity);
        return removeHelper(entity, Optional.empty());
    }

    @Override
    public ENTITY remove(ENTITY entity, Consumer<MetaResult<ENTITY>> listener) throws SpeedmentException {
        requireNonNulls(entity, listener);
        return removeHelper(entity, Optional.of(listener));
    }

    /**
     * Short-cut for retrieving the current {@link Dbms}.
     *
     * @return the current dbms
     */
    protected final Dbms getDbms() {
        return ancestor(getTable(), Dbms.class).get();
    }

    /**
     * Short-cut for retrieving the current {@link DbmsType}.
     *
     * @return the current dbms type
     */
    protected final DbmsType getDbmsType() {
        return dbmsTypeOf(dbmsHandlerComponent, getDbms());
    }

    /**
     * Short-cut for retrieving the current {@link DbmsHandler}.
     *
     * @return the current dbms handler
     */
    protected final DbmsOperationHandler dbmsHandler() {
        return findDbmsType(dbmsHandlerComponent, getDbms()).getOperationHandler();
    }

    /**
     * Short-cut for retrieving the current {@link DatabaseNamingConvention}.
     *
     * @return the current naming convention
     */
    protected final DatabaseNamingConvention naming() {
        return getDbmsType().getDatabaseNamingConvention();
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
    protected String sqlColumnList(Function<String, String> postMapper) {
        requireNonNull(postMapper);
        return getTable().columns()
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
        return getTable().primaryKeyColumns()
            .map(this::findColumn)
            .map(Column::getName)
            .map(naming()::encloseField)
            .map(postMapper)
            .collect(joining(" AND "));
    }

    private Column findColumn(PrimaryKeyColumn pkc) {
        return pkc.findColumn().orElseThrow(() -> new SpeedmentException("Cannot find column for " + pkc));
    }

    /**
     * Returns the full name of a table formatted in accordance to the current
     * {@link DbmsType}. The returned value will be within quotes if that is
     * what the database expects.
     *
     * @return the full quoted table name
     */
    protected String sqlTableReference() {
        return sqlTableReference.getOrCompute(() -> naming().fullNameOf(getTable()));
    }

    private <F extends FieldTrait & ReferenceFieldTrait<ENTITY, ?, ?>> Object toDatabaseType(F field, ENTITY entity) {
        final Object javaValue = unwrap(get(entity, field.getIdentifier()));
        @SuppressWarnings("unchecked")
        final Object dbValue = ((TypeMapper<Object, Object>) field.typeMapper()).toDatabaseType(javaValue);
        return dbValue;
    }

    private <F extends FieldTrait & ReferenceFieldTrait<ENTITY, ?, ?>> ENTITY persistHelp(ENTITY entity, Optional<Consumer<MetaResult<ENTITY>>> listener) throws SpeedmentException {
        final List<Column> cols = persistColumns(entity);
        final StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(sqlTableReference());
        sb.append(" (").append(persistColumnList(cols)).append(")");
        sb.append(" VALUES ");
        sb.append("(").append(persistColumnListWithQuestionMarks(cols)).append(")");

        @SuppressWarnings("unchecked")
        final List<Object> values = cols.stream()
            .map(Column::getName)
            .map(fieldTraitMap::get)
            .filter(ReferenceFieldTrait.class::isInstance)
            .map(f -> (FieldTrait & ReferenceFieldTrait<ENTITY, ?, ?>) f)
            .map(f -> toDatabaseType(f, entity))
            .collect(toList());

        // TODO: Make autoinc part of FieldTrait
        @SuppressWarnings("unchecked")
        final List<F> generatedFields = fields()
            .filter(f -> DocumentDbUtil.referencedColumn(projectComponent.getProject(), f.getIdentifier()).isAutoIncrement())
            .filter(ReferenceFieldTrait.class::isInstance)
            .map(f -> (F) f)
            .collect(toList());

        final Function<ENTITY, Consumer<List<Long>>> generatedKeyconsumer = builder -> {
            return l -> {
                if (!l.isEmpty()) {
                    final AtomicInteger cnt = new AtomicInteger();
                    // Just assume that they are in order, what else is there to do?
                    generatedFields
                        .forEach(f -> {

                            // Cast from Long to the column target type
                            final Object val = resultSetMapperComponent
                                .apply(f.typeMapper().getJavaType())
                                .parse(l.get(cnt.getAndIncrement()));

                            @SuppressWarnings("unchecked")
                            final Object javaValue = ((TypeMapper<Object, Object>) f.typeMapper()).toJavaType(val);
                            set(builder, f.getIdentifier(), javaValue);
                        });
                }
            };
        };

        executeInsert(entity, sb.toString(), values, generatedFields, generatedKeyconsumer, listener);
        return entity;
    }

    private ENTITY updateHelper(ENTITY entity, Optional<Consumer<MetaResult<ENTITY>>> listener) throws SpeedmentException {
        assertHasPrimaryKeyColumns();
        final StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(sqlTableReference()).append(" SET ");
        sb.append(sqlColumnList(n -> n + " = ?"));
        sb.append(" WHERE ");
        sb.append(sqlPrimaryKeyColumnList(pk -> pk + " = ?"));

        final List<Object> values = castedFieldsOf(this::fields)
            .map(f -> toDatabaseType(f, entity))
            .collect(Collectors.toList());

        castedFieldsOf(this::primaryKeyFields)
            .map(ReferenceFieldTrait::getIdentifier)
            .forEachOrdered(f -> values.add(get(entity, f)));

        executeUpdate(sb.toString(), values, listener);
        return entity;
    }

    private ENTITY removeHelper(ENTITY entity, Optional<Consumer<MetaResult<ENTITY>>> listener) throws SpeedmentException {
        assertHasPrimaryKeyColumns();
        final StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ").append(sqlTableReference());
        sb.append(" WHERE ");
        sb.append(sqlPrimaryKeyColumnList(pk -> pk + " = ?"));

        final List<Object> values = castedFieldsOf(this::primaryKeyFields)
            .map(f -> toDatabaseType(f, entity))
            .collect(toList());

        executeDelete(sb.toString(), values, listener);
        return entity;
    }

    private <T extends FieldTrait & ReferenceFieldTrait<ENTITY, ?, ?>> Stream<T> castedFieldsOf(Supplier<Stream<FieldTrait>> supplier) {
        @SuppressWarnings("unchecked")
        final Stream<T> result = supplier.get()
            .filter(ReferenceFieldTrait.class::isInstance)
            .map(f -> (T) f);
        return result;
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
     * statement. Some database types (e.g. Postgres) does not allow auto
     * increment columns that are null in an insert/update statement.
     *
     * @param entity to be inserted/updated
     * @return a List of the columns that shall be used in an insert/update
     * statement
     */
    protected List<Column> persistColumns(ENTITY entity) {
        return getTable().columns()
            .filter(c -> isPersistColumn(entity, c))
            .collect(toList());
    }

    /**
     * Returns if a columns that shall be used in an insert/update statement.
     * Some database types (e.g. Postgres) does not allow auto increment columns
     * that are null in an insert/update statement.
     *
     * @param entity to be inserted/updated
     * @param c column
     * @return if a columns that shall be used in an insert/update statement
     */
    protected boolean isPersistColumn(ENTITY entity, Column c) {
        if (c.isAutoIncrement()) {
            final FieldTrait ft = fieldTraitMap.get(c.getName());
            if (ft != null) {
                @SuppressWarnings("unchecked")
                final FieldIdentifier<ENTITY> fi = (FieldIdentifier<ENTITY>) ft.getIdentifier();
                final Object colValue = get(entity, fi);
                if (colValue != null) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private <F extends FieldTrait & ReferenceFieldTrait<ENTITY, ?, ?>> void executeInsert(
        final ENTITY entity,
        final String sql,
        final List<Object> values,
        final List<F> generatedFields,
        final Function<ENTITY, Consumer<List<Long>>> generatedKeyconsumer,
        final Optional<Consumer<MetaResult<ENTITY>>> listener
    ) throws SpeedmentException {
        executeHelper(sql, values, listener,
            () -> dbmsHandler().executeInsert(
                getDbms(), sql, values, generatedFields, generatedKeyconsumer.apply(entity)
            )
        );
    }

    private void executeUpdate(
        final String sql,
        final List<Object> values,
        final Optional<Consumer<MetaResult<ENTITY>>> listener
    ) throws SpeedmentException {
        executeHelper(sql, values, listener, () -> dbmsHandler().executeUpdate(getDbms(), sql, values));
    }

    private void executeDelete(
        final String sql,
        final List<Object> values,
        final Optional<Consumer<MetaResult<ENTITY>>> listener
    ) throws SpeedmentException {
        executeHelper(sql, values, listener, () -> dbmsHandler().executeDelete(getDbms(), sql, values));
    }

    private void executeHelper(
        String sql,
        List<Object> values,
        Optional<Consumer<MetaResult<ENTITY>>> listener,
        SqlRunnable action
    ) throws SpeedmentException {
        requireNonNulls(sql, values, listener, action);

        final SqlMetaResultImpl<ENTITY> meta = listener.isPresent()
            ? new SqlMetaResultImpl<ENTITY>()
            .setQuery(sql)
            .setParameters(values)
            : null;

        try {
            action.run();
        } catch (final SQLException sqle) {
            if (meta != null) {
                meta.setThrowable(sqle);
            }
            throw new SpeedmentException(sqle);
        } finally {
            listener.ifPresent(c -> c.accept(meta));
        }
    }

    private void assertHasPrimaryKeyColumns() {
        if (!hasPrimaryKeyColumns) {
            throw new SpeedmentException(
                "The table "
                + DocumentUtil.relativeName(getTable(), Project.class, DATABASE_NAME)
                + " does not have any primary keys. Some operations like "
                + "update() and remove() requires at least one primary key."
            );
        }
    }
}
