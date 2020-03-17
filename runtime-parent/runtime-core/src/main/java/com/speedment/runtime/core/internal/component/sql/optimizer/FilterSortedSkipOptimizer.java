/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
import com.speedment.runtime.core.db.DbmsTypeDefault;
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
import java.util.stream.Stream;

import static com.speedment.runtime.core.db.DbmsTypeDefault.SkipLimitSupport.NONE;
import static com.speedment.runtime.core.db.DbmsTypeDefault.SkipLimitSupport.ONLY_AFTER_SORTED;
import static com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil.isContainingOnlyFieldPredicate;
import static com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil.isSortedActionWithFieldPredicate;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.*;

/**
 * This Optimizer takes care of the following case:
 * <ul>
 *   <li> a) Zero or more filter() operations
 *   <li> b) Zero or more sorted() operations
 *   <li> c) Zero or more skip() operations
 *   <li> dSqlPersistenceProviderImpl) Zero or more limit() operations
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

    private static final FilterOperation<?> FILTER_OPERATION = new FilterOperation<>();
    private static final SortedOperation<?> SORTED_OPERATION = new SortedOperation<>();
    private static final SkipOperation<?> SKIP_OPERATION = new SkipOperation<>();
    private static final LimitOperation<?> LIMIT_OPERATION = new LimitOperation<>();

    private static final List<Operation<?>> FILTER_SORTED_SKIP_LIMIT_PATH =
        Stream.of(
            FILTER_OPERATION,
            SORTED_OPERATION,
            SKIP_OPERATION,
            LIMIT_OPERATION
        ).collect(collectingAndThen(toList(), Collections::unmodifiableList));

    private static final List<Operation<?>> SORTED_FILTER_SKIP_LIMIT_PATH =
        Stream.of(
            SORTED_OPERATION,
            FILTER_OPERATION,
            SKIP_OPERATION,
            LIMIT_OPERATION
        ).collect(collectingAndThen(toList(), Collections::unmodifiableList));

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
            unused -> filterCounter.incrementAndGet(),
            unused -> orderCounter.incrementAndGet(),
            unused -> skipCounter.incrementAndGet(),
            unused -> limitCounter.incrementAndGet()
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
            renderWhere(info, dbmsType, filters, values, sql);
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
                } else if (comparator instanceof CombinedComparator) {
                    @SuppressWarnings("unchecked")
                    final CombinedComparator<ENTITY> combinedComparator = (CombinedComparator<ENTITY>) sortedAction.getComparator();
                    combinedComparator.stream()
                        .map(c -> (FieldComparator<ENTITY>) c)
                        .forEachOrdered(fieldComparators::add);
                } else {
                    // We sort on a field that we do not know how to handle. Fallback to no optimization.
                    return initialPipeline;
                }
            }

            if (!fieldComparators.isEmpty()) {
                renderOrderBy(info, dbmsType, sql, fieldComparators);
            }
        }

        final String finalSql = renderSkipLimit(initialPipeline, dbmsType, skipLimitSupport, filters, sorteds, skips, limits, values, sql);

        query.setSql(finalSql);
        query.setValues(values);

        return initialPipeline;
    }

    private void renderWhere(SqlStreamOptimizerInfo<ENTITY> info, DbmsType dbmsType, List<FilterAction<ENTITY>> filters, List<Object> values, StringBuilder sql) {
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

        final String whereFragmentSql = rr.getSql();
        if (!whereFragmentSql.isEmpty()) {
            sql.append(" WHERE ").append(whereFragmentSql);
            values.addAll(rr.getValues());
        }
    }

    private <P extends Pipeline> String renderSkipLimit(P initialPipeline, DbmsType dbmsType, DbmsTypeDefault.SkipLimitSupport skipLimitSupport, List<FilterAction<ENTITY>> filters, List<SortedComparatorAction<ENTITY>> sorteds, List<SkipAction<ENTITY>> skips, List<LimitAction<ENTITY>> limits, List<Object> values, StringBuilder sql) {
        String finalSql;
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
        return finalSql;
    }

    private void renderOrderBy(SqlStreamOptimizerInfo<ENTITY> info, DbmsType dbmsType, StringBuilder sql, List<FieldComparator<ENTITY>> fieldComparators) {
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
                final String fieldName = info.getSqlColumnNamer().apply(fieldComparator.getField());

                final NullOrder effectiveNullOrder = effectiveNullOrder(fieldComparator, isReversed);

                // Specify NullOrder pre column if nulls are first
                appendNullOrderingPreColumnIfAny(dbmsType, sql, fieldName, effectiveNullOrder);

                sql.append(fieldName);
                appendStringCollationIfAny(info, dbmsType, sql, fieldComparator);

                appendAscOrDesc(sql, isReversed);

                // Specify NullOrder post column
                appendNullOrderingPostColumnIfAny(dbmsType, sql, effectiveNullOrder);

            }
        }
    }

    private void appendNullOrderingPostColumnIfAny(DbmsType dbmsType, StringBuilder sql, NullOrder effectiveNullOrder) {
        if (effectiveNullOrder == NullOrder.FIRST && dbmsType.getSortByNullOrderInsertion() == DbmsType.SortByNullOrderInsertion.POST) {
            sql.append(" NULLS FIRST");
        }
    }

    private void appendStringCollationIfAny(SqlStreamOptimizerInfo<ENTITY> info, DbmsType dbmsType, StringBuilder sql, FieldComparator<ENTITY> fieldComparator) {
        if (String.class.equals(info.getSqlDatabaseTypeFunction().apply(fieldComparator.getField()))) {
            sql.append(dbmsType.getCollateFragment().getSql());
        }
    }

    private void appendAscOrDesc(StringBuilder sql, boolean isReversed) {
        if (isReversed) {
            sql.append(" DESC");
        } else {
            sql.append(" ASC");
        }
    }

    private void appendNullOrderingPreColumnIfAny(DbmsType dbmsType, StringBuilder sql, String fieldName, NullOrder effectiveNullOrder) {
        if (effectiveNullOrder == NullOrder.FIRST) {
            if (dbmsType.getSortByNullOrderInsertion() == DbmsType.SortByNullOrderInsertion.PRE) {
                sql.append(fieldName).append("IS NOT NULL, ");
            }
            if (dbmsType.getSortByNullOrderInsertion() == DbmsType.SortByNullOrderInsertion.PRE_WITH_CASE) {
                sql.append("CASE WHEN ").append(fieldName).append(" IS NULL THEN 0 ELSE 1 END, ");
            }
        }
    }

    private NullOrder effectiveNullOrder(FieldComparator<ENTITY> fieldComparator, boolean isReversed) {
        return isReversed
            ? fieldComparator.getNullOrder().reversed()
            : fieldComparator.getNullOrder();
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
        final List<Operation<?>> path;
        if (firstAction instanceof SortedComparatorAction) {
            path = SORTED_FILTER_SKIP_LIMIT_PATH;
        } else {
            path = FILTER_SORTED_SKIP_LIMIT_PATH;
        }

        // Keeps track on where we are in the path
        // Start with the first operation type (i.e. either SORTED or FILTER)
        int pathStart = 0;
        for (Action<?, ?> action : pipeline) {
            for(int pos = pathStart; true; pos++) {
                if (pos >= path.size()) {
                    return;  // Reached the end of the path without finding the action
                }
                @SuppressWarnings("unchecked")
                final Operation<ENTITY> operation = (Operation<ENTITY>)path.get(pos);
                if (operation.is(action)) {
                    operation.consume(action, consumers);
                    pathStart = pos;  // Never look back at parts of the path that are now to be considered passed
                    break;
                }
            }
        }
    }

    private static class Consumers<ENTITY> {

        private final Consumer<? super FilterAction<ENTITY>> filterConsumer;
        private final Consumer<? super SortedComparatorAction<ENTITY>> sortedConsumer;
        private final Consumer<? super SkipAction<ENTITY>> skipConsumer;
        private final Consumer<? super LimitAction<ENTITY>> limitConsumer;

        Consumers(
            final Consumer<? super FilterAction<ENTITY>> filterConsumer,
            final Consumer<? super SortedComparatorAction<ENTITY>> sortedConsumer,
            final Consumer<? super SkipAction<ENTITY>> skipConsumer,
            final Consumer<? super LimitAction<ENTITY>> limitConsumer
        ) {
            this.filterConsumer = requireNonNull(filterConsumer);
            this.sortedConsumer = requireNonNull(sortedConsumer);
            this.skipConsumer = requireNonNull(skipConsumer);
            this.limitConsumer = requireNonNull(limitConsumer);
        }

        Consumer<? super FilterAction<ENTITY>> getFilterConsumer() {
            return filterConsumer;
        }

        Consumer<? super SortedComparatorAction<ENTITY>> getSortedConsumer() {
            return sortedConsumer;
        }

        Consumer<? super SkipAction<ENTITY>> getSkipConsumer() {
            return skipConsumer;
        }

        Consumer<? super LimitAction<ENTITY>> getLimitConsumer() {
            return limitConsumer;
        }

    }

    private interface Operation<ENTITY> {

        boolean is(Action<?, ?> action);

        void consume(Action<?, ?> action, Consumers<ENTITY> consumers);

    }

    private static final class FilterOperation<ENTITY> implements Operation<ENTITY> {

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

        private boolean isFilterActionAndContainingOnlyFieldPredicate(Action<?, ?> action) {
            if (action instanceof FilterAction) {
                @SuppressWarnings("unchecked")
                final FilterAction<ENTITY> filterAction = (FilterAction<ENTITY>) action;
                return isContainingOnlyFieldPredicate(filterAction.getPredicate());
            }
            return false;
        }

    }

    private static final class SortedOperation<ENTITY> implements Operation<ENTITY> {

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

    private static final class SkipOperation<ENTITY> implements Operation<ENTITY> {

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

    private static final class LimitOperation<ENTITY> implements Operation<ENTITY> {

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
