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
package com.speedment.orm.core.manager.sql;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.Dbms;
import com.speedment.orm.config.model.PrimaryKeyColumn;
import com.speedment.orm.config.model.Schema;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.core.Buildable;
import com.speedment.orm.core.manager.AbstractManager;
import com.speedment.orm.core.manager.metaresult.MetaResult;
import com.speedment.orm.core.manager.metaresult.SqlMetaResult;
import com.speedment.orm.db.AsynchronousQueryResult;
import com.speedment.orm.db.DbmsHandler;
import com.speedment.orm.db.impl.SqlFunction;
import com.speedment.orm.platform.Platform;
import com.speedment.orm.platform.component.DbmsHandlerComponent;
import com.speedment.util.stream.builder.ReferenceStreamBuilder;
import com.speedment.util.stream.builder.pipeline.BasePipeline;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pemi
 *
 * @param <PK> PrimaryKey type for this Manager
 * @param <ENTITY> Entity type for this Manager
 * @param <BUILDER> Builder type for this Manager
 */
public abstract class AbstractSqlManager<PK, ENTITY, BUILDER extends Buildable<ENTITY>> extends AbstractManager<PK, ENTITY, BUILDER> implements SqlManager<PK, ENTITY, BUILDER> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractSqlManager.class);

    private SqlFunction<ResultSet, ENTITY> sqlEntityMapper;

    @Override
    public Stream<ENTITY> stream() {
        final AsynchronousQueryResult<ENTITY> asynchronousQueryResult = dbmsHandler().executeQueryAsync(sqlSelect(""), Collections.emptyList(), sqlEntityMapper.unWrap());
        final SqlStreamTerminator<PK, ENTITY, BUILDER> terminator = new SqlStreamTerminator<>(this, asynchronousQueryResult);
        final Supplier<BaseStream<?, ?>> initialSupplier = () -> asynchronousQueryResult.stream();
        final Stream<ENTITY> result = new ReferenceStreamBuilder<>(new BasePipeline<>(initialSupplier), terminator);
        result.onClose(asynchronousQueryResult::close); // Make sure we are closing the ResultSet, Statement and Connection later
        return result;
    }

    public <T> Stream<T> synchronousStreamOf(final String sql, final List<Object> values, SqlFunction<ResultSet, T> rsMapper) {
        LOGGER.debug(sql + " <- " + values);
        return dbmsHandler().executeQuery(sql, values, rsMapper);
    }

    public String sqlColumnList() {
        return getTable().streamOf(Column.class).map(Column::getName).collect(Collectors.joining(","));
    }

    public String sqlTableReference() {
        return getTable().getRelativeName(Schema.class);
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
    public Optional<ENTITY> persist(ENTITY entity) {
        return persist(entity, null);
    }

    @Override
    public Optional<ENTITY> persist(ENTITY entity, Consumer<MetaResult<ENTITY>> listener) {
        final Table table = getTable();
        final StringBuilder sb = new StringBuilder();
        sb.append("insert into ").append(table.getRelativeName(Schema.class));
        sb.append("(").append(table.streamOf(Column.class).map(Column::getName).collect(joining(", "))).append(")");
        sb.append(" values ");
        sb.append("(").append(table.streamOf(Column.class).map(c -> "?").collect(joining(", "))).append(")");

        final List<Object> values = table.streamOf(Column.class).map(c -> get(entity, c)).collect(Collectors.toList());

        final Function<BUILDER, Consumer<List<Long>>> generatedKeyconsumer = b -> {
            return l -> {
                if (!l.isEmpty()) {
                    final AtomicInteger cnt = new AtomicInteger();
                    // Just assume that they are in order, what else is there to do?
                    table.streamOf(Column.class)
                        .filter(Column::isAutoincrement)
                        .forEachOrdered(c -> {
                            System.out.println("COLUMN:" + c);
                            set(b, c, l.get(cnt.getAndIncrement()));
                        });
                }
            };
        };

        return executeUpdate(entity, sb.toString(), values, generatedKeyconsumer, listener);
    }

    @Override
    public Optional<ENTITY> update(ENTITY entity) {
        return update(entity, null);
    }

    @Override
    public Optional<ENTITY> update(ENTITY entity, Consumer<MetaResult<ENTITY>> listener) {
        final Table table = getTable();

        final StringBuilder sb = new StringBuilder();
        sb.append("update ").append(table.getRelativeName(Schema.class)).append(" set ");
        sb.append(table.streamOf(Column.class).map(c -> c.getName() + " = ?").collect(joining(", ")));
        sb.append(" where ");
        sb.append(table.streamOf(PrimaryKeyColumn.class).map(pk -> "(" + pk.getName() + " = ?)").collect(joining(" AND ")));

        final List<Object> values = table.streamOf(Column.class).map(c -> get(entity, c)).collect(Collectors.toList());
        table.streamOf(PrimaryKeyColumn.class).map(pkc -> pkc.getColumn()).forEachOrdered(c -> values.add(get(entity, c)));

        return executeUpdate(entity, sb.toString(), values, NOTHING, listener);
    }

    @Override
    public Optional<ENTITY> remove(ENTITY entity) {
        return remove(entity, null);
    }

    @Override
    public Optional<ENTITY> remove(ENTITY entity, Consumer<MetaResult<ENTITY>> listener) {
        final Table table = getTable();
        final StringBuilder sb = new StringBuilder();
        sb.append("delete from ").append(table.getRelativeName(Schema.class)).append(" set ");
        sb.append(" where ");
        sb.append(table.streamOf(PrimaryKeyColumn.class).map(pk -> "(" + pk.getName() + " = ?)").collect(joining(" AND ")));
        final List<Object> values = table.streamOf(PrimaryKeyColumn.class).map(pk -> get(entity, pk.getColumn())).collect(Collectors.toList());

        return executeUpdate(entity, sb.toString(), values, NOTHING, listener);
    }

    private Optional<ENTITY> executeUpdate(
        final ENTITY entity,
        final String sql,
        final List<Object> values,
        final Function<BUILDER, Consumer<List<Long>>> generatedKeyconsumer,
        final Consumer<MetaResult<ENTITY>> listener
    ) {
        ENTITY newEntity;
        SqlMetaResult<ENTITY> meta = null;
        if (listener != null) {
            meta = new SqlMetaResult<ENTITY>().setQuery(sql).setParameters(values);
        }
        try {
            newEntity = executeUpdate(entity, sql, values, generatedKeyconsumer);
        } catch (SQLException sqle) {
            LOGGER.error("Unable to persist", sqle);
            if (meta != null) {
                meta.setThrowable(sqle);
            }
            return Optional.empty();
        } finally {
            if (listener != null) {
                listener.accept(meta);
            }
        }
        return Optional.of(newEntity);
    }

    private ENTITY executeUpdate(
        final ENTITY entity,
        final String sql,
        final List<Object> values,
        final Function<BUILDER, Consumer<List<Long>>> generatedKeyconsumer
    ) throws SQLException {
        final Table table = getTable();
        final Dbms dbms = table.ancestor(Dbms.class).get();
        final DbmsHandler dbmsHandler = Platform.get().get(DbmsHandlerComponent.class).get(dbms);
        final BUILDER builder = toBuilder(entity);
        dbmsHandler.executeUpdate(sql, values, generatedKeyconsumer.apply(builder));
        return builder.build();
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

    private final Function<BUILDER, Consumer<List<Long>>> NOTHING = b -> l -> { // Nothing to do for updates...
    };

    protected DbmsHandler dbmsHandler() {
        final Table table = getTable();
        final Dbms dbms = table.ancestor(Dbms.class).get();
        return Platform.get().get(DbmsHandlerComponent.class).get(dbms);
    }

}
