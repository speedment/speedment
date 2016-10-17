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
package com.speedment.runtime.core.internal.manager.sql;

import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.db.SqlPredicateFragment;
import com.speedment.runtime.core.internal.stream.builder.pipeline.DoublePipeline;
import com.speedment.runtime.core.internal.stream.builder.pipeline.IntPipeline;
import com.speedment.runtime.core.internal.stream.builder.pipeline.LongPipeline;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil;
import com.speedment.runtime.core.stream.Pipeline;
import com.speedment.runtime.core.stream.action.Action;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.typemapper.TypeMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.LongSupplier;
import java.util.function.Predicate;

import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import com.speedment.runtime.core.internal.stream.builder.action.reference.FilterAction;
import static com.speedment.runtime.core.stream.action.Property.SIZE;
import static com.speedment.runtime.core.stream.action.Verb.PRESERVE;
import java.util.Collections;
import static java.util.Objects.requireNonNull;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 */
public final class SqlStreamTerminator<ENTITY> implements StreamTerminator {

    private final DbmsType dbmsType;
    private final String sqlSelect;
    private final String sqlSelectCount;
    //private final LongSupplier sqlCounter;
    private final BiFunction<String, List<Object>, Long> counter;
    private final Function<Field<ENTITY>, String> sqlColumnNamer;
    private final AsynchronousQueryResult<ENTITY> asynchronousQueryResult;

    public SqlStreamTerminator(
        DbmsType dbmsType,
        String sqlSelect,
        String sqlSelectCount,
        BiFunction<String, List<Object>, Long> counter,
        //LongSupplier sqlCounter,
        Function<Field<ENTITY>, String> sqlColumnNamer,
        AsynchronousQueryResult<ENTITY> asynchronousQueryResult) {

        this.dbmsType = requireNonNull(dbmsType);
        this.sqlSelect = requireNonNull(sqlSelect);
        this.sqlSelectCount = requireNonNull(sqlSelectCount);
        this.counter = requireNonNull(counter);
        //this.sqlCounter = requireNonNull(sqlCounter);
        this.sqlColumnNamer = requireNonNull(sqlColumnNamer);
        this.asynchronousQueryResult = requireNonNull(asynchronousQueryResult);
    }

    @Override
    public <P extends Pipeline> P optimize(P initialPipeline) {
        requireNonNull(initialPipeline);
        final List<FieldPredicate<ENTITY>> andPredicateBuilders = StreamTerminatorUtil.topLevelAndPredicates(initialPipeline);

        if (!andPredicateBuilders.isEmpty()) {
            modifySource(andPredicateBuilders, asynchronousQueryResult);
        }

        return initialPipeline;
    }

    public void modifySource(List<FieldPredicate<ENTITY>> predicateBuilders, AsynchronousQueryResult<ENTITY> qr) {
        requireNonNull(predicateBuilders);
        requireNonNull(qr);

        if (predicateBuilders.isEmpty()) {
            // Nothing to do...
            return;
        }

        final SqlInfo sqlInfo = sqlInfo(sqlSelect, predicateBuilders);
        qr.setSql(sqlInfo.sql);
        qr.setValues(sqlInfo.values);
    }

    public SqlInfo sqlInfo(String sqlBase, List<FieldPredicate<ENTITY>> predicateBuilders) {
        requireNonNull(predicateBuilders);

        if (predicateBuilders.isEmpty()) {
            // Nothing to do...
            return new SqlInfo(sqlBase, Collections.emptyList());
        }

        final FieldPredicateView spv = dbmsType.getFieldPredicateView();
        final List<SqlPredicateFragment> fragments = predicateBuilders.stream()
            .map(sp -> spv.transform(sqlColumnNamer, sp))
            .collect(toList());

        final String sql = sqlBase + " WHERE "
            + fragments.stream()
                .map(SqlPredicateFragment::getSql)
                .collect(joining(" AND "));

        final List<Object> values = new ArrayList<>();
        for (int i = 0; i < fragments.size(); i++) {

            final FieldPredicate<ENTITY> p = predicateBuilders.get(i);
            final Field<ENTITY> referenceFieldTrait = p.getField();

            @SuppressWarnings("unchecked")
            final TypeMapper<Object, Object> tm = (TypeMapper<Object, Object>) referenceFieldTrait.typeMapper();

            fragments.get(i).objects()
                .map(tm::toDatabaseType)
                .forEach(values::add);
        }
        return new SqlInfo(sql, values);
    }

    @Override
    public long count(DoublePipeline pipeline) {
        requireNonNull(pipeline);
        return countHelper(pipeline, () -> StreamTerminator.super.count(pipeline));
    }

    @Override
    public long count(IntPipeline pipeline) {
        requireNonNull(pipeline);
        return countHelper(pipeline, () -> StreamTerminator.super.count(pipeline));
    }

    @Override
    public long count(LongPipeline pipeline) {
        requireNonNull(pipeline);
        return countHelper(pipeline, () -> StreamTerminator.super.count(pipeline));
    }

    @Override
    public <T> long count(ReferencePipeline<T> pipeline) {
        requireNonNull(pipeline);
        return countHelper(pipeline, () -> StreamTerminator.super.count(pipeline));
    }

    private static final Predicate<Action<?, ?>> CHECK_RETAIN_SIZE = action -> action.is(PRESERVE, SIZE);

    /**
     * Optimizer for count operations.
     *
     * @param pipeline the pipeline
     * @param fallbackSupplier a fallback supplier should every item be size
     * retaining
     * @return the number of rows
     */
    private long countHelper(Pipeline pipeline, LongSupplier fallbackSupplier) {
        requireNonNulls(pipeline, fallbackSupplier);
        
        if (isCountOptimizable(pipeline)) {
            // select count(*) from 'table' where ...
            final List<FieldPredicate<ENTITY>> andPredicateBuilders = StreamTerminatorUtil.topLevelAndPredicates(pipeline);
            final SqlInfo sqlInfo = sqlInfo(sqlSelectCount, andPredicateBuilders);
            return counter.apply(sqlInfo.sql, sqlInfo.values);
        } else if (pipeline.stream().allMatch(CHECK_RETAIN_SIZE)) {
            // select count(*) from 'table'
            return counter.apply(sqlSelectCount, Collections.emptyList());
        } else {
            // Iterate over all materialized ENTITIES....
            return fallbackSupplier.getAsLong();
        }
    }

    private boolean isCountOptimizable(Pipeline pipeline) {
        int filters = 0;
        for (final Action<?, ?> action : pipeline) {
            if (action instanceof FilterAction) {
                filters++;
            } else {
                break;
            }
        }
        return filters > 0;
    }

    private static class SqlInfo {

        private final String sql;
        private final List<Object> values;
//        private static final SqlInfo EMPTY = new SqlInfo("", Collections.emptyList());

        public SqlInfo(String sql, List<Object> values) {
            this.sql = sql;
            this.values = values;
        }

//        public static SqlInfo empty() {
//            return EMPTY;
//        }
    }

//    
//        private long sqlCount() {
//        return dbmsType.getOperationHandler().executeQuery(dbms,
//            "SELECT COUNT(*) FROM " + sqlTableReference,
//            Collections.emptyList(),
//            rs -> rs.getLong(1)
//        ).findAny().get();
//    }
}
