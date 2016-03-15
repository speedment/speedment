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
package com.speedment.internal.core.manager.sql;

import com.speedment.Speedment;
import com.speedment.config.db.Column;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Table;
import com.speedment.config.db.parameters.DbmsType;
import com.speedment.internal.core.manager.AbstractManager;
import com.speedment.db.MetaResult;
import com.speedment.internal.core.manager.metaresult.SqlMetaResultImpl;
import com.speedment.db.AsynchronousQueryResult;
import com.speedment.db.DbmsHandler;
import com.speedment.db.SqlFunction;
import com.speedment.exception.SpeedmentException;
import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.db.DatabaseNamingConvention;
import com.speedment.internal.core.stream.builder.ReferenceStreamBuilder;
import com.speedment.internal.core.stream.builder.pipeline.PipelineImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;
import com.speedment.internal.util.LazyString;
import com.speedment.stream.StreamDecorator;
import static com.speedment.internal.util.document.DocumentDbUtil.dbmsTypeOf;
import static com.speedment.internal.util.document.DocumentUtil.ancestor;
import static com.speedment.internal.core.stream.OptionalUtil.unwrap;
import static com.speedment.util.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 *
 * @param <ENTITY> Entity type for this Manager
 */
public abstract class AbstractSqlManager<ENTITY> extends AbstractManager<ENTITY> implements SqlManager<ENTITY> {

    private final LazyString sqlColumnList;
    private final LazyString sqlColumnListQuestionMarks;
    private final LazyString sqlTableReference;
    private final LazyString sqlSelect;
    private SqlFunction<ResultSet, ENTITY> entityMapper;

    protected AbstractSqlManager(Speedment speedment) {
        super(speedment);
        this.sqlColumnList = LazyString.create();
        this.sqlColumnListQuestionMarks = LazyString.create();
        this.sqlTableReference = LazyString.create();
        this.sqlSelect = LazyString.create();
    }

    @Override
    public Stream<ENTITY> nativeStream(StreamDecorator decorator) {
        final AsynchronousQueryResult<ENTITY> asynchronousQueryResult = decorator.apply(dbmsHandler().executeQueryAsync(sqlSelect(), Collections.emptyList(), entityMapper.unWrap()));
        final SqlStreamTerminator<ENTITY> terminator = new SqlStreamTerminator<>(this, asynchronousQueryResult, decorator);
        final Supplier<BaseStream<?, ?>> initialSupplier = () -> decorator.applyOnInitial(asynchronousQueryResult.stream());
        final Stream<ENTITY> result = decorator.applyOnFinal(new ReferenceStreamBuilder<>(new PipelineImpl<>(initialSupplier), terminator));

        // Make sure we are closing the ResultSet, Statement and Connection later
        result.onClose(asynchronousQueryResult::close);

        return result;
    }

    public <T> Stream<T> synchronousStreamOf(String sql, List<Object> values, SqlFunction<ResultSet, T> rsMapper) {
        requireNonNulls(sql, values, rsMapper);
        return dbmsHandler().executeQuery(sql, values, rsMapper);
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
        return dbmsTypeOf(speedment, getDbms());
    }

    /**
     * Short-cut for retrieving the current {@link DbmsHandler}.
     *
     * @return the current dbms handler
     */
    protected final DbmsHandler dbmsHandler() {
        return speedment.getDbmsHandlerComponent().get(getDbms());
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
        return sqlColumnList.getOrCompute(() -> sqlColumnList(c -> c));
    }

    /**
     * Returns a comma separated list of question marks (?), fully formatted in
     * accordance to the current {@link DbmsType} to represent column value
     * wildcards.
     *
     * @return the comma separated question mark list
     */
    private String sqlColumnListWithQuestionMarks() {
        return sqlColumnListQuestionMarks.getOrCompute(() -> sqlColumnList(c -> "?"));
    }

    /**
     * Returns a {@code AND} separated list of {@link PrimaryKeyColumn} database
     * names, formatted in accordance to the current {@link DbmsType}.
     *
     * @return list of fully quoted primary key column names
     */
    protected String sqlColumnList(Function<String, String> postMapper) {
        requireNonNull(postMapper);
        return getTable().columns()
                .map(naming()::fullNameOf)
                .map(postMapper)
                .collect(Collectors.joining(","));
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
                .map(naming()::fullNameOf)
                .map(postMapper)
                .collect(Collectors.joining(" AND "));
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

    private final Function<ENTITY, Consumer<List<Long>>> NOTHING
            = b -> l -> {
                /* Nothing to do for updates... */ };

    private Object toDatabaseType(Column column, ENTITY entity) {
        final Object javaValue = unwrap(get(entity, column));
        @SuppressWarnings("unchecked")
        final Object dbValue = ((TypeMapper<Object, Object>) column.findTypeMapper()).toDatabaseType(javaValue);
        return dbValue;
    }

    private ENTITY persistHelp(ENTITY entity, Optional<Consumer<MetaResult<ENTITY>>> listener) throws SpeedmentException {
        final Table table = getTable();
        final StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(sqlTableReference());
        sb.append(" (").append(sqlColumnList()).append(")");
        sb.append(" VALUES ");
        sb.append("(").append(sqlColumnListWithQuestionMarks()).append(")");

        final List<Object> values = table.columns()
                .map(c -> toDatabaseType(c, entity))
                .collect(Collectors.toList());

        final Function<ENTITY, Consumer<List<Long>>> generatedKeyconsumer = builder -> {
            return l -> {
                if (!l.isEmpty()) {
                    final AtomicInteger cnt = new AtomicInteger();
                    // Just assume that they are in order, what else is there to do?
                    table.columns()
                            .filter(Column::isAutoIncrement)
                            .forEachOrdered(column -> {
                                // Cast from Long to the column target type

                                final Object val = speedment
                                        .getJavaTypeMapperComponent()
                                        .apply(column.findTypeMapper().getJavaType())
                                        .parse(
                                                l.get(cnt.getAndIncrement())
                                        );

                                //final Object val = StandardJavaTypeMappingOld.parse(column.getMapping(), l.get(cnt.getAndIncrement()));
                                @SuppressWarnings("unchecked")
                                final Object javaValue = ((TypeMapper<Object, Object>) column.findTypeMapper()).toJavaType(val);
                                set(builder, column, javaValue);
                            });
                }
            };
        };

        executeUpdate(entity, sb.toString(), values, generatedKeyconsumer, listener);
        return entity;
    }

    private ENTITY updateHelper(ENTITY entity, Optional<Consumer<MetaResult<ENTITY>>> listener) throws SpeedmentException {
        final Table table = getTable();

        final StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(sqlTableReference()).append(" SET ");
        sb.append(sqlColumnList(n -> n + " = ?"));
        sb.append(" WHERE ");
        sb.append(sqlPrimaryKeyColumnList(pk -> pk + " = ?"));

        final List<Object> values = table.columns()
                .map(c -> toDatabaseType(c, entity))
                .collect(Collectors.toList());

        table.primaryKeyColumns().map(pkc -> pkc.findColumn().get()).forEachOrdered(c -> values.add(get(entity, c)));

        executeUpdate(entity, sb.toString(), values, NOTHING, listener);
        return entity;
    }

    private ENTITY removeHelper(ENTITY entity, Optional<Consumer<MetaResult<ENTITY>>> listener) throws SpeedmentException {
        final Table table = getTable();
        final StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ").append(sqlTableReference());
        sb.append(" WHERE ");
        sb.append(sqlPrimaryKeyColumnList(pk -> pk + " = ?"));
        final List<Object> values = table.primaryKeyColumns()
                .map(pk -> toDatabaseType(pk.findColumn().get(), entity))
                .collect(Collectors.toList());

        executeUpdate(entity, sb.toString(), values, NOTHING, listener);
        return entity;
    }

    private void executeUpdate(
            final ENTITY entity,
            final String sql,
            final List<Object> values,
            final Function<ENTITY, Consumer<List<Long>>> generatedKeyconsumer,
            final Optional<Consumer<MetaResult<ENTITY>>> listener
    ) throws SpeedmentException {
        requireNonNulls(entity, sql, values, generatedKeyconsumer, listener);

        final SqlMetaResultImpl<ENTITY> meta = listener.isPresent()
                ? new SqlMetaResultImpl<ENTITY>()
                .setQuery(sql)
                .setParameters(values)
                : null;

        try {
            executeUpdate(entity, sql, values, generatedKeyconsumer);
        } catch (final SQLException sqle) {
            if (meta != null) {
                meta.setThrowable(sqle);
            }
            throw new SpeedmentException(sqle);
        } finally {
            listener.ifPresent(c -> c.accept(meta));
        }
    }

    private void executeUpdate(
            final ENTITY entity,
            final String sql,
            final List<Object> values,
            final Function<ENTITY, Consumer<List<Long>>> generatedKeyconsumer
    ) throws SQLException {
        dbmsHandler().executeUpdate(sql, values, generatedKeyconsumer.apply(entity));
    }
}
