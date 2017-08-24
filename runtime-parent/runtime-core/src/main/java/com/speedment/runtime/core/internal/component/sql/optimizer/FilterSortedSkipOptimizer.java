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
package com.speedment.runtime.core.internal.component.sql.optimizer;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.core.component.sql.Metrics;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizer;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.stream.builder.action.reference.FilterAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.LimitAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.SkipAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.SortedComparatorAction;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil.RenderResult;
import com.speedment.runtime.core.stream.Pipeline;
import com.speedment.runtime.core.stream.action.Action;
import com.speedment.runtime.field.comparator.CombinedComparator;
import com.speedment.runtime.field.comparator.FieldComparator;
import com.speedment.runtime.field.comparator.NullOrder;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.speedment.runtime.core.db.DbmsType.SkipLimitSupport.NONE;
import static com.speedment.runtime.core.db.DbmsType.SkipLimitSupport.ONLY_AFTER_SORTED;
import static com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil.isContainingOnlyFieldPredicate;
import static com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil.isSortedActionWithFieldPredicate;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * This Optimizer takes care of the following case:
 * <ul>
 *   <li> a) Zero or more filter() operations
 *   <li> b) Zero or more sorted() operations
 *   <li> c) Zero or more skip() operations
 *   <li> d) Zero or more limit() operations
 * </ul>
 *
 * <em>No other operations</em> must be in the sequence a-d or within the
 * individual items a-d. <em>All</em> parameters in a and b must be obtained via
 * fields. Failure to any of these rules will make the Optimizer reject
 * optimization. Steps a) and b) may swap places.
 *
 * Thus, this optimizer can handle a (FILTER*, SORTED*, SKIP*, LIMIT*) or
 * (SORTED*, LIMIT*, SKIP*, LIMIT*) pattern where all non-primitive parameters
 * are all Field derived
 *
 * @author Per Minborg
 * @param <ENTITY> entity type
 */
public final class FilterSortedSkipOptimizer<ENTITY> implements SqlStreamOptimizer<ENTITY> {

    private final FilterOperation FILTER_OPERATION = new FilterOperation();
    private final SortedOperation SORTED_OPERATION = new SortedOperation();
    private final SkipOperation SKIP_OPERATION = new SkipOperation();
    private final LimitOperation LIMIT_OPERATION = new LimitOperation();

    private final List<Operation<ENTITY>> FILTER_SORTED_SKIP_LIMIT_PATH = Arrays.asList(
        FILTER_OPERATION,
        SORTED_OPERATION,
        SKIP_OPERATION,
        LIMIT_OPERATION
    );
    private final List<Operation<ENTITY>> SORTED_FILTER_SKIP_LIMIT_PATH = Arrays.asList(
        SORTED_OPERATION,
        FILTER_OPERATION,
        SKIP_OPERATION,
        LIMIT_OPERATION
    );

    // FILTER <-> SORTED
    // This optimizer can handle a (FILTER*,SORTED*,SKIP*, LIMIT*) pattern where filter and sorted parameters are all Field derived
    @Override
    public Metrics metrics(Pipeline initialPipeline, DbmsType dbmsType) {
        requireNonNull(initialPipeline);
        requireNonNull(dbmsType);
        final DbmsType.SkipLimitSupport skipLimitSupport = dbmsType.getSkipLimitSupport();
        final AtomicInteger filterCounter = new AtomicInteger();
        final AtomicInteger orderCounter = new AtomicInteger();
        final AtomicInteger skipCounter = new AtomicInteger();
        final AtomicInteger limitCounter = new AtomicInteger();

        traverse(initialPipeline,
            $ -> filterCounter.incrementAndGet(),
            $ -> orderCounter.incrementAndGet(),
            $ -> skipCounter.incrementAndGet(),
            $ -> limitCounter.incrementAndGet()
        );

        if (skipLimitSupport == ONLY_AFTER_SORTED && orderCounter.get() == 0) {
            // Just decline. There are other optimizer that handles just filtering better
            return Metrics.empty();
        }
        if (skipLimitSupport == NONE) {
            return Metrics.of(filterCounter.get() + orderCounter.get(), filterCounter.get(), orderCounter.get(), 0, 0);
        }

        return Metrics.of(
            filterCounter.get() + orderCounter.get() + skipCounter.get() + limitCounter.get(),
            filterCounter.get(),
            orderCounter.get(),
            skipCounter.get() > 0 ? 1 : 0,
            limitCounter.get() > 0 ? 1 : 0
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <P extends Pipeline> P optimize(
        final P initialPipeline,
        final SqlStreamOptimizerInfo<ENTITY> info,
        final AsynchronousQueryResult<ENTITY> query
    ) {
        requireNonNull(initialPipeline);
        requireNonNull(info);
        requireNonNull(query);
        final DbmsType dbmsType = info.getDbmsType();
        final DbmsType.SkipLimitSupport skipLimitSupport = dbmsType.getSkipLimitSupport();
        final List<FilterAction<ENTITY>> filters = new ArrayList<>();
        final List<SortedComparatorAction<ENTITY>> sorteds = new ArrayList<>();
        final List<SkipAction<ENTITY>> skips = new ArrayList<>();
        final List<LimitAction<ENTITY>> limits = new ArrayList<>();

        traverse(initialPipeline, filters::add, sorteds::add, skips::add, limits::add);

        final List<Object> values = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();

        sql.append(info.getSqlSelect());

        if (!filters.isEmpty()) {
            @SuppressWarnings("unchecked")
            List<Predicate<ENTITY>> predicates = filters.stream()
                .map(FilterAction::getPredicate)
                .map(p -> (Predicate<ENTITY>) p)
                .collect(toList());

            final RenderResult rr = StreamTerminatorUtil.renderSqlWhere(
                dbmsType,
                info.getSqlColumnNamer(),
                info.getSqlDatabaseTypeFunction(),
                predicates
            );

            sql.append(" WHERE ").append(rr.getSql());
            values.addAll(rr.getValues());
        }

        if (!sorteds.isEmpty()) {

            final List<FieldComparator<ENTITY>> fieldComparators = new ArrayList<>();
            for (int i = sorteds.size() - 1; i >= 0; i--) {
                final SortedComparatorAction<ENTITY> sortedAction = sorteds.get(i);
                @SuppressWarnings("unchecked")
                final Comparator<? super ENTITY> comparator = sortedAction.getComparator();
                if (comparator instanceof FieldComparator) {
                    @SuppressWarnings("unchecked")
                    final FieldComparator<ENTITY> fieldComparator = (FieldComparator<ENTITY>) sortedAction.getComparator();
                    fieldComparators.add(fieldComparator);
                }
                if (comparator instanceof CombinedComparator) {
                    @SuppressWarnings("unchecked")
                    final CombinedComparator<ENTITY> combinedComparator = (CombinedComparator<ENTITY>) sortedAction.getComparator();
                    combinedComparator.stream()
                        .map(c -> (FieldComparator<ENTITY>) c)
                        .forEachOrdered(fieldComparators::add);
                }
            }

            if (!fieldComparators.isEmpty()) {

                sql.append(" ORDER BY ");
                // Iterate backwards
                final Set<ColumnIdentifier<ENTITY>> columns = new HashSet<>();
                int cnt = 0;
                for (FieldComparator<ENTITY> fieldComparator : fieldComparators) {
                    final ColumnIdentifier<ENTITY> columnIdentifier = fieldComparator.getField().identifier();

                    // Some databases (e.g. SQL Server) only allows distinct columns in ORDER BY 
                    if (columns.add(columnIdentifier)) {
                        if (cnt++ != 0) {
                            sql.append(", ");
                        }

                        boolean isReversed = fieldComparator.isReversed();
                        String fieldName = info.getSqlColumnNamer().apply(fieldComparator.getField());

                        final NullOrder effectiveNullOrder = isReversed
                            ? fieldComparator.getNullOrder().reversed()
                            : fieldComparator.getNullOrder();

                        // Specify NullOrder pre column if nulls are first
                        if (effectiveNullOrder == NullOrder.FIRST) {
                            if (dbmsType.getSortByNullOrderInsertion() == DbmsType.SortByNullOrderInsertion.PRE) {
                                sql.append(fieldName).append("IS NOT NULL, ");
                            }
                            if (dbmsType.getSortByNullOrderInsertion() == DbmsType.SortByNullOrderInsertion.PRE_WITH_CASE) {
                                sql.append("CASE WHEN ").append(fieldName).append(" IS NULL THEN 0 ELSE 1 END, ");
                            }
                        }

                        sql.append(fieldName);
                        if (isReversed) {
                            sql.append(" DESC");
                        } else {
                            sql.append(" ASC");
                        }

                        // Specify NullOrder post column
                        if (effectiveNullOrder == NullOrder.FIRST && dbmsType.getSortByNullOrderInsertion() == DbmsType.SortByNullOrderInsertion.POST) {
                            sql.append(" NULLS FIRST");
                        }

                    }
                }
            }
        }

        final String finalSql;
        if (skipLimitSupport == NONE) {
            finalSql = sql.toString();
            initialPipeline.removeIf(a -> filters.contains(a) || sorteds.contains(a));
        } else {
            final long sumSkip = skips.stream().mapToLong(SkipAction::getSkip).sum();
            final long minLimit = limits.stream().mapToLong(LimitAction::getLimit).min().orElse(Long.MAX_VALUE);
            finalSql = dbmsType
                .applySkipLimit(sql.toString(), values, sumSkip, minLimit);
            initialPipeline.removeIf(a -> filters.contains(a) || sorteds.contains(a) || skips.contains(a) || limits.contains(a));
        }

        query.setSql(finalSql);
        query.setValues(values);

        return initialPipeline;
    }

    private void traverse(Pipeline pipeline,
        final Consumer<? super FilterAction<ENTITY>> filterConsumer,
        final Consumer<? super SortedComparatorAction<ENTITY>> sortedConsumer,
        final Consumer<? super SkipAction<ENTITY>> skipConsumer,
        final Consumer<? super LimitAction<ENTITY>> limitConsumer
    ) {
        if (pipeline.isEmpty()) {
            return;
        }

        final Consumers<ENTITY> consumers = new Consumers<>(filterConsumer, sortedConsumer, skipConsumer, limitConsumer);

        final Action<?, ?> firstAction = pipeline.getFirst();

        // The path is the way we can walk the stream pipeline
        // and still satisfy the requirement on this optimizer
        // There are two paths:
        //   Sorted*,Filter*,Skip*,Limit*
        //   Filter*,Sorted*,Skip*,Limit*
        // If there are other operations types in between, the optimizer will not kick in
        final List<Operation<ENTITY>> path;
        if (firstAction instanceof SortedComparatorAction) {
            path = SORTED_FILTER_SKIP_LIMIT_PATH;
        } else {
            path = FILTER_SORTED_SKIP_LIMIT_PATH;
        }

        // Keeps track on where we are in the path
        // Start with the first operation type (i.e. either SORTED or FILTER)
        Operation<ENTITY> operation = path.get(0);

        for (Action<?, ?> action : pipeline) {

            // Are we on the first operation type in the path
            if (operation == path.get(0)) {
                // Check if the current stream action is of the first operational type (e.g. SORTED)
                if (operation.is(action)) {
                    // If so, consume the stream action (e.g. increase a counter or put it in a list)
                    operation.consume(action, consumers);
                    continue;
                } else {
                    // Check if the current stream action is of the second operational type (e.g. FILTER)
                    if (path.get(1).is(action)) {
                        // Move the operation state to the second operational type
                        operation = path.get(1);
                    } else {
                        if (path.get(2).is(action)) {
                            operation = path.get(2);
                        } else {
                            if (path.get(3).is(action)) {
                                operation = path.get(3);
                            } else {
                                return;
                            }
                        }
                    }
                }
            }

            // The same principle as above but starting at the second operation type in the path
            if (operation == path.get(1)) {
                if (operation.is(action)) {
                    operation.consume(action, consumers);
                    continue;
                } else {
                    if (path.get(2).is(action)) {
                        operation = path.get(2);
                    } else {
                        if (path.get(3).is(action)) {
                            operation = path.get(3);
                        } else {
                            return;
                        }
                    }
                }
            }

            if (operation == path.get(2)) {
                if (operation.is(action)) {
                    operation.consume(action, consumers);
                    continue;
                } else {
                    if (path.get(3).is(action)) {
                        operation = path.get(3);
                    } else {
                        return;
                    }
                }
            }

            if (operation == path.get(3)) {
                if (operation.is(action)) {
                    operation.consume(action, consumers);
                    continue;
                } else {
                    return;
                }
            }
        }
    }

    private boolean isFilterActionAndContainingOnlyFieldPredicate(Action<?, ?> action) {
        if (action instanceof FilterAction) {
            @SuppressWarnings("unchecked")
            final FilterAction<ENTITY> filterAction = (FilterAction<ENTITY>) action;
            return isContainingOnlyFieldPredicate(filterAction.getPredicate());
        }
        return false;
    }

    private static class Consumers<ENTITY> {

        private final Consumer<? super FilterAction<ENTITY>> filterConsumer;
        private final Consumer<? super SortedComparatorAction<ENTITY>> sortedConsumer;
        private final Consumer<? super SkipAction<ENTITY>> skipConsumer;
        private final Consumer<? super LimitAction<ENTITY>> limitConsumer;

        public Consumers(
            final Consumer<? super FilterAction<ENTITY>> filterConsumer,
            final Consumer<? super SortedComparatorAction<ENTITY>> sortedConsumer,
            final Consumer<? super SkipAction<ENTITY>> skipConsumer,
            final Consumer<? super LimitAction<ENTITY>> limitConsumer
        ) {
            this.filterConsumer = requireNonNull(filterConsumer);
            this.sortedConsumer = requireNonNull(sortedConsumer);;
            this.skipConsumer = requireNonNull(skipConsumer);
            this.limitConsumer = requireNonNull(limitConsumer);
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

        public Consumer<? super LimitAction<ENTITY>> getLimitConsumer() {
            return limitConsumer;
        }

    }

    private interface Operation<ENTITY> {

        boolean is(Action<?, ?> action);

        void consume(Action<?, ?> action, Consumers<ENTITY> consumers);

    }

    private class FilterOperation implements Operation<ENTITY> {

        @Override
        public boolean is(Action<?, ?> action) {
            return isFilterActionAndContainingOnlyFieldPredicate(action);
        }

        @Override
        public void consume(Action<?, ?> action, Consumers<ENTITY> consumers) {
            @SuppressWarnings("unchecked")
            final FilterAction<ENTITY> filterAction = (FilterAction<ENTITY>) action;
            consumers.getFilterConsumer().accept(filterAction);
        }

    }

    private class SortedOperation implements Operation<ENTITY> {

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

    private class LimitOperation implements Operation<ENTITY> {

        @Override
        public boolean is(Action<?, ?> action) {
            return action instanceof LimitAction;
        }

        @Override
        public void consume(Action<?, ?> action, Consumers<ENTITY> consumers) {
            @SuppressWarnings("unchecked")
            final LimitAction<ENTITY> limitAction = (LimitAction<ENTITY>) action;
            consumers.getLimitConsumer().accept(limitAction);
        }

    }

}
