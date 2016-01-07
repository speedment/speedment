/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
import com.speedment.config.db.Schema;
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
import com.speedment.internal.core.stream.builder.ReferenceStreamBuilder;
import com.speedment.internal.core.stream.builder.pipeline.PipelineImpl;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
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
import com.speedment.internal.util.Lazy;
import com.speedment.stream.StreamDecorator;
import static com.speedment.internal.core.stream.OptionalUtil.unwrap;
import static com.speedment.internal.util.document.DocumentUtil.ancestor;
import static com.speedment.internal.util.document.DocumentUtil.relativeName;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 *
 * @param <ENTITY> Entity type for this Manager
 */
public abstract class AbstractSqlManager<ENTITY> extends AbstractManager<ENTITY> implements SqlManager<ENTITY> {

    private SqlFunction<ResultSet, ENTITY> sqlEntityMapper;
    private final Lazy<String> sqlColumnList;
    private final Lazy<String> sqlColumnListQuestionMarks;

    public AbstractSqlManager(Speedment speedment) {
        super(speedment);
        sqlColumnList = new Lazy<>();
        sqlColumnListQuestionMarks = new Lazy<>();
    }

    @Override
    public Stream<ENTITY> nativeStream(StreamDecorator decorator) {
        final AsynchronousQueryResult<ENTITY> asynchronousQueryResult = decorator.apply(dbmsHandler().executeQueryAsync(sqlSelect(""), Collections.emptyList(), sqlEntityMapper.unWrap()));
        final SqlStreamTerminator<ENTITY> terminator = new SqlStreamTerminator<>(this, asynchronousQueryResult, decorator);
        final Supplier<BaseStream<?, ?>> initialSupplier = () -> decorator.apply(asynchronousQueryResult.stream());
        final Stream<ENTITY> result = decorator.apply(new ReferenceStreamBuilder<>(new PipelineImpl<>(initialSupplier), terminator));
        result.onClose(asynchronousQueryResult::close); // Make sure we are closing the ResultSet, Statement and Connection later
        return result;
    }

    public <T> Stream<T> synchronousStreamOf(final String sql, final List<Object> values, SqlFunction<ResultSet, T> rsMapper) {
        //LOGGER.debug(sql + " <- " + values);
        requireNonNull(sql);
        requireNonNull(values);
        requireNonNull(rsMapper);
        return dbmsHandler().executeQuery(sql, values, rsMapper);
    }

    private final Supplier<String> columnListSupplier = () -> sqlColumnList(Function.identity());
    
    public String sqlColumnList() {
        if (getTable().isImmutable()) {
            return sqlColumnList.getOrCompute(columnListSupplier);
        } else {
            return columnListSupplier.get();
        }
    }

    private final Supplier<String> columnListWithQuestionMarkSupplier = () -> sqlColumnList(c -> "?");
    
    public String sqlColumnListWithQuestionMarks() {
        if (getTable().isImmutable()) {
            return sqlColumnListQuestionMarks.getOrCompute(columnListWithQuestionMarkSupplier);
        } else {
            return columnListWithQuestionMarkSupplier.get();
        }
    }

    private String sqlColumnList(Function<String, String> postMapper) {
        requireNonNull(postMapper);
        return getTable().columns()
                .map(Column::getName)
                .map(this::quoteField)
                .map(postMapper)
                .collect(Collectors.joining(","));
    }

    public String sqlPrimaryKeyColumnList(Function<String, String> postMapper) {
        requireNonNull(postMapper);
        return getTable().primaryKeyColumns()
                .map(PrimaryKeyColumn::getName)
                .map(this::quoteField)
                .map(postMapper)
                .collect(Collectors.joining(" AND "));
    }

    public String sqlTableReference() {
        return relativeName(getTable(), Schema.class, this::quoteField);
    }

    public String sqlSelect(String suffix) {
        requireNonNull(suffix);
        final String sql = "select " + sqlColumnList() + " from " + sqlTableReference() + suffix;
        return sql;
    }

    @Override
    public SqlFunction<ResultSet, ENTITY> getSqlEntityMapper() {
        return sqlEntityMapper;
    }

    @Override
    public void setSqlEntityMapper(SqlFunction<ResultSet, ENTITY> sqlEntityMapper) {
        this.sqlEntityMapper = requireNonNull(sqlEntityMapper);
    }

    @Override
    public ENTITY persist(ENTITY entity) throws SpeedmentException {
        return persistHelp(entity, Optional.empty());
    }

    @Override
    public ENTITY persist(ENTITY entity, Consumer<MetaResult<ENTITY>> listener) throws SpeedmentException {
        requireNonNull(entity);
        requireNonNull(listener);
        return persistHelp(entity, Optional.of(listener));
    }

    @Override
    public ENTITY update(ENTITY entity) {
        requireNonNull(entity);
        return updateHelper(entity, Optional.empty());
    }

    @Override
    public ENTITY update(ENTITY entity, Consumer<MetaResult<ENTITY>> listener) throws SpeedmentException {
        requireNonNull(entity);
        requireNonNull(listener);
        return updateHelper(entity, Optional.of(listener));
    }

    @Override
    public ENTITY remove(ENTITY entity) {
        requireNonNull(entity);
        return removeHelper(entity, Optional.empty());
    }

    @Override
    public ENTITY remove(ENTITY entity, Consumer<MetaResult<ENTITY>> listener) throws SpeedmentException {
        requireNonNull(entity);
        requireNonNull(listener);
        return removeHelper(entity, Optional.of(listener));
    }

    protected Dbms getDbms() {
        return ancestor(getTable(), Dbms.class).get();
    }

    protected DbmsType getDbmsType() {
        return speedment.getDbmsHandlerComponent().findByName(getDbms().getTypeName()).get();
    }

    private String quoteField(final String s) {
        final DbmsType dbmsType = getDbmsType();
        return getDbmsType().getFieldEncloserStart() + s + dbmsType.getFieldEncloserEnd();
    }

    protected DbmsHandler dbmsHandler() {
        return speedment.getDbmsHandlerComponent().get(getDbms());
    }

    // Null safe RS getters, must have the same name as ResultSet getters
    protected Object getObject(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getObject(columnName));
    }

    protected Boolean getBoolean(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getBoolean(columnName));
    }

    protected Byte getByte(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getByte(columnName));
    }

    protected Short getShort(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getShort(columnName));
    }

    protected Integer getInt(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getInt(columnName));
    }

    protected Long getLong(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getLong(columnName));
    }

    protected Float getFloat(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getFloat(columnName));
    }

    protected Double getDouble(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getDouble(columnName));
    }

    protected String getString(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getString(columnName));
    }

    protected Date getDate(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getDate(columnName));
    }

    protected Time getTime(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getTime(columnName));
    }

    protected Timestamp getTimestamp(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getTimestamp(columnName));
    }

    protected BigDecimal getBigDecimal(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getBigDecimal(columnName));
    }

    protected Blob getBlob(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getBlob(columnName));
    }

    protected Clob getClob(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getClob(columnName));
    }

    protected Array getArray(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getArray(columnName));
    }

    protected Ref getRef(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getRef(columnName));
    }

    protected URL getURL(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getURL(columnName));
    }

    protected RowId getRowId(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getRowId(columnName));
    }

    protected NClob getNClob(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getNClob(columnName));
    }

    protected SQLXML getSQLXML(final ResultSet resultSet, final String columnName) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getSQLXML(columnName));
    }

    // Null safe RS getters (int), must have the same name as ResultSet getters
    protected Object getObject(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getObject(ordinalPosition));
    }

    protected Boolean getBoolean(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getBoolean(ordinalPosition));
    }

    protected Byte getByte(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getByte(ordinalPosition));
    }

    protected Short getShort(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getShort(ordinalPosition));
    }

    protected Integer getInt(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getInt(ordinalPosition));
    }

    protected Long getLong(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getLong(ordinalPosition));
    }

    protected Float getFloat(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getFloat(ordinalPosition));
    }

    protected Double getDouble(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getDouble(ordinalPosition));
    }

    protected String getString(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getString(ordinalPosition));
    }

    protected Date getDate(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getDate(ordinalPosition));
    }

    protected Time getTime(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getTime(ordinalPosition));
    }

    protected Timestamp getTimestamp(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getTimestamp(ordinalPosition));
    }

    protected BigDecimal getBigDecimal(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getBigDecimal(ordinalPosition));
    }

    protected Blob getBlob(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getBlob(ordinalPosition));
    }

    protected Clob getClob(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getClob(ordinalPosition));
    }

    protected Array getArray(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getArray(ordinalPosition));
    }

    protected Ref getRef(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getRef(ordinalPosition));
    }

    protected URL getURL(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getURL(ordinalPosition));
    }

    protected RowId getRowId(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getRowId(ordinalPosition));
    }

    protected NClob getNClob(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getNClob(ordinalPosition));
    }

    protected SQLXML getSQLXML(final ResultSet resultSet, final int ordinalPosition) throws SQLException {
        return getNullableFrom(resultSet, rs -> rs.getSQLXML(ordinalPosition));
    }

    private <T> T getNullableFrom(ResultSet rs, SqlFunction<ResultSet, T> mapper) throws SQLException {
        final T result = mapper.apply(rs);
        if (rs.wasNull()) {
            return null;
        } else {
            return result;
        }

    }

    private final Function<ENTITY, Consumer<List<Long>>> NOTHING = b -> l -> { // Nothing to do for updates...
    };

    private Object toDatabaseType(Column column, ENTITY entity) {
        final Object javaValue = unwrap(get(entity, column));
        @SuppressWarnings("unchecked")
        final Object dbValue = ((TypeMapper<Object, Object>) column.findTypeMapper()).toDatabaseType(javaValue);
        return dbValue;
    }

    private ENTITY persistHelp(ENTITY entity, Optional<Consumer<MetaResult<ENTITY>>> listener) throws SpeedmentException {
        final Table table = getTable();
        final StringBuilder sb = new StringBuilder();
        sb.append("insert into ").append(sqlTableReference());
        sb.append(" (").append(sqlColumnList()).append(")");
        sb.append(" values ");
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
        sb.append("update ").append(sqlTableReference()).append(" set ");
        sb.append(sqlColumnList(n -> n + " = ?"));
        sb.append(" where ");
        sb.append(sqlPrimaryKeyColumnList(pk -> pk + " = ?"));

        final List<Object> values = table.columns()
                .map(c -> toDatabaseType(c, entity))
                .collect(Collectors.toList());

        table.primaryKeyColumns().map(pkc -> pkc.findColumn()).forEachOrdered(c -> values.add(get(entity, c)));

        executeUpdate(entity, sb.toString(), values, NOTHING, listener);
        return entity;
    }

    private ENTITY removeHelper(ENTITY entity, Optional<Consumer<MetaResult<ENTITY>>> listener) throws SpeedmentException {
        final Table table = getTable();
        final StringBuilder sb = new StringBuilder();
        sb.append("delete from ").append(sqlTableReference());
        sb.append(" where ");
        sb.append(sqlPrimaryKeyColumnList(pk -> pk + " = ?"));
        final List<Object> values = table.primaryKeyColumns()
                .map(pk -> toDatabaseType(pk.findColumn(), entity))
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
        requireNonNull(entity);
        requireNonNull(sql);
        requireNonNull(values);
        requireNonNull(generatedKeyconsumer);
        requireNonNull(listener);

        final SqlMetaResultImpl<ENTITY> meta;

        if (listener.isPresent()) {
            meta = new SqlMetaResultImpl<ENTITY>().setQuery(sql).setParameters(values);
        } else {
            meta = null;
        }
        try {
            executeUpdate(entity, sql, values, generatedKeyconsumer);
        } catch (SQLException sqle) {
            //LOGGER.error("Unable to persist", sqle);
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
        //final ENTITY builder = toBuilder(entity);
        dbmsHandler().executeUpdate(sql, values, generatedKeyconsumer.apply(entity));
        //return entity;
    }

    private String sqlQuote(Object o) {
        if (o == null) {
            return "null";
        }
        if (o instanceof Number) {
            return o.toString();
        }
        return "'" + o.toString() + "'";
    }

}
