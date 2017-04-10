/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.component.sql.optimiser;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizer;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.db.SqlPredicateFragment;
import static com.speedment.runtime.core.internal.component.sql.optimiser.FilterOrderSkipOptimizer.State.FILTER;
import static com.speedment.runtime.core.internal.component.sql.optimiser.FilterOrderSkipOptimizer.State.SKIP;
import static com.speedment.runtime.core.internal.component.sql.optimiser.FilterOrderSkipOptimizer.State.SORTED;
import com.speedment.runtime.core.internal.stream.builder.action.reference.FilterAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.SkipAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.SortedComparatorAction;

import static com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil.isFilterActionWithFieldPredicate;
import static com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil.isSortedActionWithFieldPredicate;
import com.speedment.runtime.core.stream.Pipeline;
import com.speedment.runtime.core.stream.action.Action;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.comparator.FieldComparator;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.typemapper.TypeMapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> entity type
 */
public final class FilterOrderSkipOptimizer<ENTITY> implements SqlStreamOptimizer<ENTITY> {

    enum State {
        FILTER, SORTED, SKIP;
    }

    // This optimizer can handle a (FILTER*,SORTED*,SKIP*) pattern where parameters are all Field derived
    @Override
    public int metrics(Pipeline initialPipeline, DbmsType dbmsType) {
        requireNonNull(initialPipeline);
        requireNonNull(dbmsType);

        final AtomicInteger cnt = new AtomicInteger();

        traverse(initialPipeline, fc -> cnt.incrementAndGet(), fc -> cnt.incrementAndGet(), fc -> cnt.incrementAndGet());

        return cnt.get() * 10;

//        State state = FILTER;
//        int metrics = 0;
//
//        for (Action<?, ?> action : initialPipeline) {
//
//            if (state == FILTER) {
//                if (isFilterActionWithFieldPredicate(action)) {
//                    metrics += 10;
//                } else {
//                    if (isSortedActionWithFieldPredicate(action)) { // Note: SortedAction will not work because an Entity is not Comparable
//                        state = SORTED;
//                    } else {
//                        if (action instanceof SkipAction) {
//                            state = SKIP;
//                        } else {
//                            return metrics;
//                        }
//                    }
//                }
//            }
//
//            if (state == SORTED) {
//                if (isSortedActionWithFieldPredicate(action)) {
//                    metrics += 10;
//                } else {
//                    if (action instanceof SkipAction) {
//                        state = SKIP;
//                    } else {
//                        return metrics;
//                    }
//                }
//            }
//
//            if (state == SKIP) {
//                if (action instanceof SkipAction) {
//                    metrics += 10;
//                } else {
//                    return metrics;
//                }
//            }
//        }
//        return metrics;
    }

    @Override
    public <P extends Pipeline> P optimize(
        final P initialPipeline,
        final SqlStreamOptimizerInfo<ENTITY> info,
        final AsynchronousQueryResult<ENTITY> query
    ) {
        requireNonNull(initialPipeline);
        requireNonNull(info);
        requireNonNull(query);

        final List<FilterAction<ENTITY>> filters = new ArrayList<>();
        final List<SortedComparatorAction<ENTITY>> sorteds = new ArrayList<>();
        final List<SkipAction<ENTITY>> skips = new ArrayList<>();

        traverse(initialPipeline, filters::add, sorteds::add, skips::add);

        final List<Object> values = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();

        sql.append(info.getSqlSelect());

        if (!filters.isEmpty()) {
            final FieldPredicateView spv = info.getDbmsType().getFieldPredicateView();

//            for (FilterAction<ENTITY> filter: filters) {
//                Predicate<? super ENTITY> predicate = filter.getPredicate();
//                @SuppressWarnings("unchecked")
//                FieldPredicate<ENTITY> fPredicate = (FieldPredicate<ENTITY>)filter.getPredicate();
//                SqlPredicateFragment fragment = spv.transform(
//                    info.getSqlColumnNamer(), 
//                    info.getSqlDatabaseTypeFunction(), 
//                    fPredicate
//                );
//            }
            @SuppressWarnings("unchecked")
            final List<SqlPredicateFragment> fragments = filters.stream()
                .map(f -> f.getPredicate())
                .map(p -> (FieldPredicate<ENTITY>) p)
                .map(sp -> spv.transform(info.getSqlColumnNamer(), info.getSqlDatabaseTypeFunction(), sp))
                .collect(toList());

            // Todo: Make this in one sweep
            String expression = fragments.stream()
                .map(SqlPredicateFragment::getSql)
                .collect(joining(" AND "));

            sql.append(" WHERE ").append(expression).append(" ");

            for (int i = 0; i < fragments.size(); i++) {

                @SuppressWarnings("unchecked")
                final FieldPredicate<ENTITY> p = (FieldPredicate<ENTITY>) filters.get(i).getPredicate();
                final Field<ENTITY> referenceFieldTrait = p.getField();

                @SuppressWarnings("unchecked")
                final TypeMapper<Object, Object> tm = (TypeMapper<Object, Object>) referenceFieldTrait.typeMapper();

                fragments.get(i).objects()
                    .map(tm::toDatabaseType)
                    .forEach(values::add);

            }

        }

        if (!sorteds.isEmpty()) {
            sql.append(" ORDER BY ");
            // Iterate backwards
            for (int i = sorteds.size() - 1; i >= 0; i--) {
                if (!(1 == (sorteds.size() - 1))) {
                    sql.append(", ");
                }
                final SortedComparatorAction<ENTITY> sortedAction = sorteds.get(i);
                @SuppressWarnings("unchecked")
                final FieldComparator<ENTITY, ?> fieldComparator = (FieldComparator<ENTITY, ?>) sortedAction.getComparator();
                boolean isReversed = fieldComparator.isReversed();
                String fieldName = info.getSqlColumnNamer().apply(fieldComparator.getField());
                sql.append(fieldName);
                if (isReversed) {
                    sql.append(" DESC ");
                } else {
                    sql.append(" ASC ");
                }
            }
        }

        if (!sorteds.isEmpty()) {
            final long sumSkip = skips.stream().mapToLong(SkipAction::getSkip).sum();
            values.add(sumSkip);
            // Only works for MySQL. Make DB independent using DbmsType
            sql.append(" LIMIT ?, ");
            sql.append(2l << 63);
            sql.append(" ");
        }

        query.setSql(sql.toString());

        // Todo: Optimize this
        Iterator<Action<?, ?>> iterator = initialPipeline.iterator();
        while (iterator.hasNext()) {
            final Action<?, ?> action = iterator.next();
            if (filters.contains(action)) {
                iterator.remove();
            } else if (sorteds.contains(action)) {
                iterator.remove();
            } else if (skips.contains(action)) {
                iterator.remove();
            }
        }

        return initialPipeline;
    }

    private void traverse(Pipeline pipeline,
        final Consumer<? super FilterAction<ENTITY>> filterConsumer,
        final Consumer<? super SortedComparatorAction<ENTITY>> sortedConsumer,
        final Consumer<? super SkipAction<ENTITY>> skipConsumer
    ) {
        State state = FILTER;

        for (Action<?, ?> action : pipeline) {

            if (state == FILTER) {
                if (isFilterActionWithFieldPredicate(action)) {
                    @SuppressWarnings("unchecked")
                    final FilterAction<ENTITY> filterAction = (FilterAction<ENTITY>) action;
                    filterConsumer.accept(filterAction);
                } else {
                    if (isSortedActionWithFieldPredicate(action)) { // Note: SortedAction will not work because an Entity is not Comparable
                        state = SORTED;
                    } else {
                        if (action instanceof SkipAction) {
                            state = SKIP;
                        } else {
                            return;
                        }
                    }
                }
            }

            if (state == SORTED) {
                if (isSortedActionWithFieldPredicate(action)) {
                    @SuppressWarnings("unchecked")
                    final SortedComparatorAction<ENTITY> sortedAction = (SortedComparatorAction<ENTITY>) action;
                    sortedConsumer.accept(sortedAction);
                } else {
                    if (action instanceof SkipAction) {
                        state = SKIP;
                    } else {
                        return;
                    }
                }
            }

            if (state == SKIP) {
                if (action instanceof SkipAction) {
                    @SuppressWarnings("unchecked")
                    final SkipAction<ENTITY> skipAction = (SkipAction<ENTITY>) action;
                    skipConsumer.accept(skipAction);
                } else {
                    return;
                }
            }
        }
    }

}
