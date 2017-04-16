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

import com.speedment.runtime.core.component.sql.Metrics;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizer;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.db.SqlPredicateFragment;
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
import java.util.Arrays;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * This Optimizer takes care of the following case:
 * <br>
 * <ul>
 * <li> a) Zero or more filter() operations
 * <li> b) Zero or more sorted() operations
 * <li> c) Zero or more skip() operations
 *
 * <em>No other operations<em> must be in the sequence a-c or within the
 * individual items a-c. <em>All</em> parameters in a and b must be obtained via
 * fields. Failure to any of these rules will make the Optimizer reject
 * optimization.
 *
 * Thus, this optimizer can handle a (FILTER*,SORTED*,SKIP*) pattern where all
 * non-primitive parameters are all Field derived
 *
 * TODO: Sorted can come first, add limit
 *
 * @author Per Minborg
 * @param <ENTITY> entity type
 */
public final class FilterSortedSkipOptimizer<ENTITY> implements SqlStreamOptimizer<ENTITY> {


    private final FilterOperation FILTER_OPERATION = new FilterOperation();
    private final SortedOperation SORTED_OPERATION = new SortedOperation();
    private final SkipOperation SKIP_OPERATION = new SkipOperation();

    private final List<Operation<ENTITY>> FILTER_SORTED_SKIP_PATH = Arrays.asList(
        FILTER_OPERATION,
        SORTED_OPERATION,
        SKIP_OPERATION
    );
    private final List<Operation<ENTITY>> SORTED_FILTER_SKIP_PATH = Arrays.asList(
        SORTED_OPERATION,
        FILTER_OPERATION,
        SKIP_OPERATION
    );

    // FILTER <-> SORTED
    // This optimizer can handle a (FILTER*,SORTED*,SKIP*) pattern where parameters are all Field derived
    @Override
    public Metrics metrics(Pipeline initialPipeline, DbmsType dbmsType) {
        requireNonNull(initialPipeline);
        requireNonNull(dbmsType);

        final AtomicInteger cnt = new AtomicInteger();
        traverse(initialPipeline, fc -> cnt.incrementAndGet(), fc -> cnt.incrementAndGet(), fc -> cnt.incrementAndGet());
        return Metrics.of(cnt.get());
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

            sql.append(" WHERE ").append(expression);

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
                if (!(i == (sorteds.size() - 1))) {
                    sql.append(", ");
                }
                final SortedComparatorAction<ENTITY> sortedAction = sorteds.get(i);
                @SuppressWarnings("unchecked")
                final FieldComparator<ENTITY, ?> fieldComparator = (FieldComparator<ENTITY, ?>) sortedAction.getComparator();
                boolean isReversed = fieldComparator.isReversed();
                String fieldName = info.getSqlColumnNamer().apply(fieldComparator.getField());
                sql.append(fieldName);
                if (isReversed) {
                    sql.append(" DESC");
                } else {
                    sql.append(" ASC");
                }
            }
        }

        if (!skips.isEmpty()) {
            final long sumSkip = skips.stream().mapToLong(SkipAction::getSkip).sum();
            values.add(sumSkip);
            // Only works for MySQL. Make DB independent using DbmsType
            sql.append(" LIMIT 9223372036854775807 OFFSET ?");
        }

        query.setSql(sql.toString());
        query.setValues(values);

        initialPipeline.removeIf(a -> filters.contains(a) || sorteds.contains(a) || skips.contains(a));
        
//        // Todo: Optimize this
//        Iterator<Action<?, ?>> iterator = initialPipeline.iterator();
//        while (iterator.hasNext()) {
//            final Action<?, ?> action = iterator.next();
//            if (filters.contains(action)) {
//                iterator.remove();
//            } else if (sorteds.contains(action)) {
//                iterator.remove();
//            } else if (skips.contains(action)) {
//                iterator.remove();
//            }
//        }

        return initialPipeline;
    }

    private void traverse(Pipeline pipeline,
        final Consumer<? super FilterAction<ENTITY>> filterConsumer,
        final Consumer<? super SortedComparatorAction<ENTITY>> sortedConsumer,
        final Consumer<? super SkipAction<ENTITY>> skipConsumer
    ) {
        if (pipeline.isEmpty()) {
            return;
        }

        final Consumers<ENTITY> consumers = new Consumers<>(filterConsumer, sortedConsumer, skipConsumer);

        final Action<?, ?> firstAction = pipeline.getFirst();
        final List<Operation<ENTITY>> path;
        if (isFilterActionWithFieldPredicate(firstAction)) {
            path = FILTER_SORTED_SKIP_PATH;
        } else {
            path = SORTED_FILTER_SKIP_PATH;
        }

        Operation<ENTITY> operation = path.get(0);

        for (Action<?, ?> action : pipeline) {

            if (operation == path.get(0)) {
                if (operation.is(action)) {
                    operation.consume(action, consumers);
                } else {
                    if (path.get(1).is(action)) {
                        operation = path.get(1);
                    } else {
                        if (path.get(2).is(action)) {
                            operation = path.get(2);
                        } else {
                            return;
                        }
                    }
                }
            }

            if (operation == path.get(1)) {
                if (operation.is(action)) {
                    operation.consume(action, consumers);
                } else {
                    if (path.get(2).is(action)) {
                        operation = path.get(2);
                    } else {
                        return;
                    }
                }
            }

            if (operation == path.get(2)) {
                if (operation.is(action)) {
                    operation.consume(action, consumers);
                } else {
                    return;
                }
            }
        }
    }

//    private void traverseOld(Pipeline pipeline,
//        final Consumer<? super FilterAction<ENTITY>> filterConsumer,
//        final Consumer<? super SortedComparatorAction<ENTITY>> sortedConsumer,
//        final Consumer<? super SkipAction<ENTITY>> skipConsumer
//    ) {
//
//        State state = FILTER;
//
//        for (Action<?, ?> action : pipeline) {
//
//            if (state == FILTER) {
//                if (isFilterActionWithFieldPredicate(action)) {
//                    @SuppressWarnings("unchecked")
//                    final FilterAction<ENTITY> filterAction = (FilterAction<ENTITY>) action;
//                    filterConsumer.accept(filterAction);
//                } else {
//                    if (isSortedActionWithFieldPredicate(action)) { // Note: SortedAction will not work because an Entity is not Comparable
//                        state = SORTED;
//                    } else {
//                        if (action instanceof SkipAction) {
//                            state = SKIP;
//                        } else {
//                            return;
//                        }
//                    }
//                }
//            }
//
//            if (state == SORTED) {
//                if (isSortedActionWithFieldPredicate(action)) {
//                    @SuppressWarnings("unchecked")
//                    final SortedComparatorAction<ENTITY> sortedAction = (SortedComparatorAction<ENTITY>) action;
//                    sortedConsumer.accept(sortedAction);
//                } else {
//                    if (action instanceof SkipAction) {
//                        state = SKIP;
//                    } else {
//                        return;
//                    }
//                }
//            }
//
//            if (state == SKIP) {
//                if (action instanceof SkipAction) {
//                    @SuppressWarnings("unchecked")
//                    final SkipAction<ENTITY> skipAction = (SkipAction<ENTITY>) action;
//                    skipConsumer.accept(skipAction);
//                } else {
//                    return;
//                }
//            }
//        }
//    }

    private static class Consumers<ENTITY> {

        private final Consumer<? super FilterAction<ENTITY>> filterConsumer;
        private final Consumer<? super SortedComparatorAction<ENTITY>> sortedConsumer;
        private final Consumer<? super SkipAction<ENTITY>> skipConsumer;

        public Consumers(
            final Consumer<? super FilterAction<ENTITY>> filterConsumer,
            final Consumer<? super SortedComparatorAction<ENTITY>> sortedConsumer,
            final Consumer<? super SkipAction<ENTITY>> skipConsumer
        ) {
            this.filterConsumer = requireNonNull(filterConsumer);
            this.sortedConsumer = requireNonNull(sortedConsumer);;
            this.skipConsumer = requireNonNull(skipConsumer);
        }

        public Consumer<? super FilterAction<ENTITY>> getFilterConsumer() {
            return filterConsumer;
        }

        public Consumer<? super SortedComparatorAction<ENTITY>> getSortedConsumer() {
            return sortedConsumer;
        }

        public Consumer<? super SkipAction<ENTITY>> getSkipConsumer() {
            return skipConsumer;
        }

    }

    private interface Operation<ENTITY> {

        //State getState();

        boolean is(Action<?, ?> action);

        void consume(Action<?, ?> action, Consumers<ENTITY> consumers);

    }

    private class FilterOperation implements Operation<ENTITY> {

//        @Override
//        public State getState() {
//            return State.FILTER;
//        }

        @Override
        public boolean is(Action<?, ?> action) {
            return isFilterActionWithFieldPredicate(action);
        }

        @Override
        public void consume(Action<?, ?> action, Consumers<ENTITY> consumers) {
            @SuppressWarnings("unchecked")
            final FilterAction<ENTITY> filterAction = (FilterAction<ENTITY>) action;
            consumers.getFilterConsumer().accept(filterAction);
        }

    }

    private class SortedOperation implements Operation<ENTITY> {

//        @Override
//        public State getState() {
//            return State.SORTED;
//        }

        @Override
        public boolean is(Action<?, ?> action) {
            return isSortedActionWithFieldPredicate(action);
        }

        @Override
        public void consume(Action<?, ?> action, Consumers<ENTITY> consumers) {
            @SuppressWarnings("unchecked")
            final SortedComparatorAction<ENTITY> sortedAction = (SortedComparatorAction<ENTITY>) action;
            consumers.getSortedConsumer().accept(sortedAction);
        }

    }

    private class SkipOperation implements Operation<ENTITY> {

//        @Override
//        public State getState() {
//            return State.SKIP;
//        }

        @Override
        public boolean is(Action<?, ?> action) {
            return action instanceof SkipAction;
        }

        @Override
        public void consume(Action<?, ?> action, Consumers<ENTITY> consumers) {
            @SuppressWarnings("unchecked")
            final SkipAction<ENTITY> skipAction = (SkipAction<ENTITY>) action;
            consumers.getSkipConsumer().accept(skipAction);
        }

    }

}
