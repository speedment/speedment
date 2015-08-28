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
package com.speedment.core.manager.sql;

import com.speedment.api.config.Column;
import com.speedment.api.config.Dbms;
import com.speedment.api.config.PrimaryKeyColumn;
import com.speedment.api.config.Schema;
import com.speedment.api.config.Table;
import com.speedment.api.config.parameters.DbmsType;
import com.speedment.core.manager.AbstractManager;
import com.speedment.api.db.MetaResult;
import com.speedment.core.manager.metaresult.SqlMetaResultImpl;
import com.speedment.api.db.AsynchronousQueryResult;
import com.speedment.api.db.DbmsHandler;
import com.speedment.api.db.SqlFunction;
import com.speedment.api.exception.SpeedmentException;
import com.speedment.core.platform.SpeedmentImpl;
import com.speedment.core.platform.component.DbmsHandlerComponent;
import com.speedment.core.platform.component.JavaTypeMapperComponent;
import com.speedment.core.stream.builder.ReferenceStreamBuilder;
import com.speedment.core.stream.builder.pipeline.BasePipeline;
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
import static com.speedment.core.stream.OptionalUtil.unwrap;

/**
 *
 * @author pemi
 *
 * @param <ENTITY> Entity type for this Manager
 */
public abstract class AbstractSqlManager<ENTITY> extends AbstractManager<ENTITY> implements SqlManager<ENTITY> {

    private SqlFunction<ResultSet, ENTITY> sqlEntityMapper;

    public AbstractSqlManager(SpeedmentImpl speedment) {
        super(speedment);
    }

    @Override
    public Stream<ENTITY> stream() {
        final AsynchronousQueryResult<ENTITY> asynchronousQueryResult = dbmsHandler().executeQueryAsync(sqlSelect(""), Collections.emptyList(), sqlEntityMapper.unWrap());
        final SqlStreamTerminator<ENTITY> terminator = new SqlStreamTerminator<>(this, asynchronousQueryResult);
        final Supplier<BaseStream<?, ?>> initialSupplier = () -> asynchronousQueryResult.stream();
        final Stream<ENTITY> result = new ReferenceStreamBuilder<>(new BasePipeline<>(initialSupplier), terminator);
        result.onClose(asynchronousQueryResult::close); // Make sure we are closing the ResultSet, Statement and Connection later
        return result;
    }

    public <T> Stream<T> synchronousStreamOf(final String sql, final List<Object> values, SqlFunction<ResultSet, T> rsMapper) {
        //LOGGER.debug(sql + " <- " + values);
        return dbmsHandler().executeQuery(sql, values, rsMapper);
    }

    public String sqlColumnList() {
        return sqlColumnList(Function.identity());
    }

    public String sqlColumnList(Function<String, String> postMapper) {
        return getTable().streamOf(Column.class)
            .map(Column::getName)
            .map(this::quoteField)
            .map(postMapper)
            .collect(Collectors.joining(","));
    }

    public String sqlPrimaryKeyColumnList(Function<String, String> postMapper) {
        return getTable().streamOf(PrimaryKeyColumn.class)
            .map(PrimaryKeyColumn::getName)
            .map(this::quoteField)
            .map(postMapper)
            .collect(Collectors.joining(" AND "));
    }

    public String sqlTableReference() {
        return getTable().getRelativeName(Schema.class, this::quoteField);
    }

    public String sqlSelect(String suffix) {
        final String sql = "select " + sqlColumnList() + " from " + sqlTableReference() + suffix;
        return sql;
    }

    @Override
    public SqlFunction<ResultSet, ENTITY> getSqlEntityMapper() {
        return sqlEntityMapper;
    }

    @Override
    public void setSqlEntityMapper(SqlFunction<ResultSet, ENTITY> sqlEntityMapper) {
        this.sqlEntityMapper = sqlEntityMapper;
    }

    @Override
    public ENTITY persist(ENTITY entity) throws SpeedmentException {
        return persist(entity, null);
    }

    @Override
    public ENTITY persist(ENTITY entity, Consumer<MetaResult<ENTITY>> listener) throws SpeedmentException {
        final Table table = getTable();
        final StringBuilder sb = new StringBuilder();
        sb.append("insert into ").append(sqlTableReference());
        sb.append(" (").append(sqlColumnList()).append(")");
        sb.append(" values ");
        sb.append("(").append(sqlColumnList(c -> "?")).append(")");

        final List<Object> values = table.streamOf(Column.class).map(c -> unwrap(get(entity, c))).collect(Collectors.toList());

        final Function<ENTITY, Consumer<List<Long>>> generatedKeyconsumer = builder -> {
            return l -> {
                if (!l.isEmpty()) {
                    final AtomicInteger cnt = new AtomicInteger();
                    // Just assume that they are in order, what else is there to do?
                    table.streamOf(Column.class)
                        .filter(Column::isAutoincrement)
                        .forEachOrdered(column -> {
                            // Cast from Long to the column target type

                            final Object val = speedment
                                .get(JavaTypeMapperComponent.class)
                                .apply(column.getMapping())
                                .parse(
                                    l.get(cnt.getAndIncrement())
                                );

                            //final Object val = StandardJavaTypeMappingOld.parse(column.getMapping(), l.get(cnt.getAndIncrement()));
                            set(builder, column, val);
                        });
                }
            };
        };

        executeUpdate(entity, sb.toString(), values, generatedKeyconsumer, listener);
        return entity;
    }

    @Override
    public ENTITY update(ENTITY entity) {
        return update(entity, null);
    }

    @Override
    public ENTITY update(ENTITY entity, Consumer<MetaResult<ENTITY>> listener) throws SpeedmentException {
        final Table table = getTable();

        final StringBuilder sb = new StringBuilder();
        sb.append("update ").append(sqlTableReference()).append(" set ");
        sb.append(sqlColumnList(n -> n + " = ?"));
        sb.append(" where ");
        sb.append(sqlPrimaryKeyColumnList(pk -> pk + " = ?"));

        final List<Object> values = table.streamOf(Column.class).map(c -> unwrap(get(entity, c))).collect(Collectors.toList());
        table.streamOf(PrimaryKeyColumn.class).map(pkc -> pkc.getColumn()).forEachOrdered(c -> values.add(get(entity, c)));

        executeUpdate(entity, sb.toString(), values, NOTHING, listener);
        return entity;
    }

    @Override
    public ENTITY remove(ENTITY entity) {
        return remove(entity, null);
    }

    @Override
    public ENTITY remove(ENTITY entity, Consumer<MetaResult<ENTITY>> listener) throws SpeedmentException {
        final Table table = getTable();
        final StringBuilder sb = new StringBuilder();
        sb.append("delete from ").append(sqlTableReference());
        sb.append(" where ");
        sb.append(sqlPrimaryKeyColumnList(pk -> pk + " = ?"));
        final List<Object> values = table.streamOf(PrimaryKeyColumn.class).map(pk -> get(entity, pk.getColumn())).collect(Collectors.toList());

        executeUpdate(entity, sb.toString(), values, NOTHING, listener);
        return entity;
    }

    private void executeUpdate(
        final ENTITY entity,
        final String sql,
        final List<Object> values,
        final Function<ENTITY, Consumer<List<Long>>> generatedKeyconsumer,
        final Consumer<MetaResult<ENTITY>> listener
    ) throws SpeedmentException {
        SqlMetaResultImpl<ENTITY> meta = null;
        if (listener != null) {
            meta = new SqlMetaResultImpl<ENTITY>().setQuery(sql).setParameters(values);
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
            if (listener != null) {
                listener.accept(meta);
            }
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

    private final Function<ENTITY, Consumer<List<Long>>> NOTHING = b -> l -> { // Nothing to do for updates...
    };

    protected Dbms getDbms() {
        return getTable().ancestor(Dbms.class).get();
    }

    protected DbmsType getDbmsType() {
        return getDbms().getType();
    }

    private String quoteField(final String s) {
        final DbmsType dbmsType = getDbms().getType();
        return dbmsType.getFieldEncloserStart() + s + dbmsType.getFieldEncloserEnd();
    }

    protected DbmsHandler dbmsHandler() {
        return speedment.get(DbmsHandlerComponent.class).get(getDbms());
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

    private <T> T getNullableFrom(ResultSet rs, SqlFunction<ResultSet, T> mapper) throws SQLException {
        final T result = mapper.apply(rs);
        if (rs.wasNull()) {
            return null;
        } else {
            return result;
        }

    }

}
