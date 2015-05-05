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

import static com.speedment.orm.code.model.java.DefaultJavaClassTranslator.CopyConstructorMode.BUILDER;
import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.Dbms;
import com.speedment.orm.config.model.PrimaryKeyColumn;
import com.speedment.orm.config.model.Schema;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.core.Buildable;
import com.speedment.orm.core.manager.AbstractManager;
import com.speedment.orm.db.DbmsHandler;
import com.speedment.orm.field.BinaryPredicateBuilder;
import com.speedment.orm.field.Operator;
import com.speedment.orm.field.PredicateBuilder;
import com.speedment.orm.field.UnaryPredicateBuilder;
import com.speedment.orm.platform.Platform;
import com.speedment.orm.platform.component.DbmsHandlerComponent;
import com.speedment.util.Cast;
import com.speedment.util.stream.builder.ReferenceStreamBuilder;
import com.speedment.util.stream.builder.pipeline.BasePipeline;
import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import static java.lang.reflect.Array.set;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import static java.util.stream.Collector.Characteristics.values;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;
import static javax.swing.text.html.parser.DTDConstants.ENTITY;
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

    private Function<ResultSet, ENTITY> sqlEntityMapper;

    private Map<Operator, BiFunction<Column, Object, String>> binaryParserMap;
    private Map<Operator, Function<Column, String>> unaryParserMap;

    public AbstractSqlManager() {

    }

    @Override
    public Stream<ENTITY> stream() {
        final SqlStreamTerminator<PK, ENTITY, BUILDER> terminator = new SqlStreamTerminator(this);
        return new ReferenceStreamBuilder<>(new BasePipeline<>(this::streamOfAll), terminator);
    }

    public Stream<ENTITY> streamOfAll() {
        return streamOf(sqlSelect(""));
    }

    public Stream<ENTITY> streamOf(final String sql) {
        return streamOf(sql, Collections.emptyList());
    }

    public Stream<ENTITY> streamOf(final List<PredicateBuilder> predicateBuilders) {
        if (predicateBuilders.isEmpty()) {
            return streamOfAll();
        }
        final StringBuilder sb = new StringBuilder();
        final List<Object> values = new ArrayList<>();
        for (PredicateBuilder pb : predicateBuilders) {
            final Optional<BinaryPredicateBuilder> oBinaryPredicateBuilder = Cast.cast(pb, BinaryPredicateBuilder.class);
            oBinaryPredicateBuilder.ifPresent(b -> {
                Object value = b.getValueAsObject();
                values.add(value);
                sb.append(binaryParserMap.get(b.getOperator()).apply(b.getField().getColumn(), value));
            });
            final Optional<UnaryPredicateBuilder> oUnaryPredicateBuilder = Cast.cast(pb, UnaryPredicateBuilder.class);
            oUnaryPredicateBuilder.ifPresent(u -> {
                sb.append(unaryParserMap.get(u.getOperator()).apply(u.getField().getColumn()));
            });
        }
        return streamOf(sqlSelect("where " + sb.toString()), values);
    }

    public Stream<ENTITY> streamOf(final String sql, final List<Object> values) {
        final Table table = getTable();
        final Dbms dbms = table.ancestor(Dbms.class
        ).get();
        final DbmsHandler dbmsHandler = Platform.get().get(DbmsHandlerComponent.class).get(dbms);

        return dbmsHandler.executeQuery(sql, values, getSqlEntityMapper());
    }

    private String
        sqlColumnList() {
        return getTable().streamOf(Column.class
        ).map(Column::getName).collect(Collectors.joining(","));
    }

    private String
        sqlTableReference() {
        return getTable().getRelativeName(Schema.class
        );
    }

    private String sqlSelect(String suffix) {
        return "select " + sqlColumnList() + " from " + sqlTableReference() + suffix;
    }

    @Override
    public Function<ResultSet, ENTITY> getSqlEntityMapper() {
        return sqlEntityMapper;
    }

    @Override
    public void setSqlEntityMapper(Function<ResultSet, ENTITY> sqlEntityMapper) {
        this.sqlEntityMapper = sqlEntityMapper;
    }

    @Override
    public Optional<ENTITY> persist(ENTITY entity) {
        final Table table = getTable();
        final StringBuilder sb = new StringBuilder();
        sb
            .append("insert into ").append(table.getRelativeName(Schema.class
                ));
        sb.append(
            "(").append(table.streamOf(Column.class
                ).map(Column::getName).collect(joining(", "))).append(")");
        sb.append(
            " values ");
        sb.append(
            "(").append(table.streamOf(Column.class
                ).map(c -> "?").collect(joining(", "))).append(")");

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

        return executeUpdate(entity, sb.toString(), values, generatedKeyconsumer);
    }

    @Override
    public Optional<ENTITY> update(ENTITY entity) {
        final Table table = getTable();

        final StringBuilder sb = new StringBuilder();
        sb.append("update ").append(table.getRelativeName(Schema.class)).append(" set ");
        sb.append(table.streamOf(Column.class).map(c -> c.getName() + " = ?").collect(joining(", ")));
        sb.append(" where ");
        sb.append(table.streamOf(PrimaryKeyColumn.class).map(pk -> "(" + pk.getName() + " = ?)").collect(joining(" AND ")));

        final List<Object> values = table.streamOf(Column.class).map(c -> get(entity, c)).collect(Collectors.toList());
        table.streamOf(PrimaryKeyColumn.class).map(pkc -> pkc.getColumn()).forEachOrdered(c -> values.add(get(entity, c)));

        return executeUpdate(entity, sb.toString(), values, NOTHING);
    }

    @Override
    public Optional<ENTITY> remove(ENTITY entity) {
        final Table table = getTable();
        final StringBuilder sb = new StringBuilder();
        sb.append("delete from ").append(table.getRelativeName(Schema.class)).append(" set ");
        sb.append(" where ");
        sb.append(table.streamOf(PrimaryKeyColumn.class).map(pk -> "(" + pk.getName() + " = ?)").collect(joining(" AND ")));
        final List<Object> values = table.streamOf(PrimaryKeyColumn.class).map(pk -> get(entity, pk.getColumn())).collect(Collectors.toList());

        return executeUpdate(entity, sb.toString(), values, NOTHING);
    }

    private Optional<ENTITY> executeUpdate(ENTITY entity, String sql, List<Object> values, final Function<BUILDER, Consumer<List<Long>>> generatedKeyconsumer) {
        final Table table = getTable();
        final Dbms dbms = table.ancestor(Dbms.class).get();
        final DbmsHandler dbmsHandler = Platform.get().get(DbmsHandlerComponent.class).get(dbms);
        final BUILDER builder = toBuilder(entity);
        try {
            dbmsHandler.executeUpdate(sql, values, generatedKeyconsumer.apply(builder));
        } catch (SQLException sqle) {
            LOGGER.error("Unable to persist", sqle);
            return Optional.empty();
        }
        return Optional.of(builder.build());
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

}
