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
package com.speedment.runtime.core.internal.stream.builder.streamterminator;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.db.SqlPredicateFragment;
import com.speedment.runtime.core.internal.stream.builder.action.reference.FilterAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.SortedComparatorAction;
import com.speedment.runtime.core.internal.util.Cast;
import com.speedment.runtime.core.stream.Pipeline;
import com.speedment.runtime.core.stream.action.Action;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.comparator.CombinedComparator;
import com.speedment.runtime.field.comparator.FieldComparator;
import com.speedment.runtime.field.predicate.CombinedPredicate;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.TypeMapper.Ordering;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author pemi
 */
public final class StreamTerminatorUtil {

    private StreamTerminatorUtil() {}

    public static <T extends Pipeline, ENTITY> List<FieldPredicate<ENTITY>> topLevelAndPredicates(T initialPipeline) {
        final List<FieldPredicate<ENTITY>> andPredicateBuilders = new ArrayList<>();

        for (final Action<?, ?> action : initialPipeline) {
            @SuppressWarnings("rawtypes")
            final Optional<FilterAction> oFilterAction = Cast.cast(action, FilterAction.class);
            if (oFilterAction.isPresent()) {
                @SuppressWarnings("unchecked")
                final List<FieldPredicate<ENTITY>> newAndPredicates = andPredicates(oFilterAction.get());
                andPredicateBuilders.addAll(newAndPredicates);
            } else {
                break; // We can only do initial consecutive FilterAction(s)
            }
        }
        return andPredicateBuilders;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <ENTITY> List<FieldPredicate<?>> andPredicates(FilterAction<ENTITY> action) {
        requireNonNull(action);
        final List<FieldPredicate<?>> andPredicateBuilders = new ArrayList<>();
        final Predicate<? super ENTITY> predicate = action.getPredicate();

        final Optional<FieldPredicate> oPredicateBuilder = Cast.cast(predicate, FieldPredicate.class);
        if (oPredicateBuilder.isPresent()) {
            andPredicateBuilders.add(oPredicateBuilder.get()); // Just a top level predicate builder
        } else {

            final Optional<CombinedPredicate> oCombinedBasePredicate = Cast.cast(predicate, CombinedPredicate.class);
            if (oCombinedBasePredicate.isPresent()) {
                final CombinedPredicate<ENTITY> combinedBasePredicate = oCombinedBasePredicate.get();
                if (combinedBasePredicate.getType() == CombinedPredicate.Type.AND) {
                    combinedBasePredicate.stream()
                        .map(p -> Cast.cast(p, FieldPredicate.class))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .forEachOrdered(andPredicateBuilders::add);

                }
            }
        }
        return andPredicateBuilders;
    }

    private static final Set<PredicateType> NON_COMPARATIVE_PREDICATES
        = EnumSet.complementOf(
            EnumSet.of(
                PredicateType.BETWEEN,
                PredicateType.GREATER_OR_EQUAL,
                PredicateType.GREATER_THAN,
                PredicateType.LESS_OR_EQUAL,
                PredicateType.LESS_THAN,
                PredicateType.NOT_BETWEEN
            )
        );

    public static <ENTITY> boolean isContainingOnlyFieldPredicate(Predicate<ENTITY> predicate) {
        if (predicate instanceof FieldPredicate) {
            final FieldPredicate<ENTITY> fieldPredicate = (FieldPredicate<ENTITY>) predicate;

            // We can only optimize filters if the ordering is retained.. Bar equal, isNull, isNotNull etc.
            return fieldPredicate.getField().typeMapper().getOrdering() == Ordering.RETAIN
                || NON_COMPARATIVE_PREDICATES.contains(fieldPredicate.getPredicateType());
        } else if (predicate instanceof CombinedPredicate) {
            return ((CombinedPredicate<ENTITY>) predicate).stream().allMatch(StreamTerminatorUtil::isContainingOnlyFieldPredicate);
        }
        return false;
    }

    public static boolean isSortedActionWithFieldPredicate(Action<?, ?> action) {
        if (action instanceof SortedComparatorAction) {
            final SortedComparatorAction<?> sortedComparatorAction = (SortedComparatorAction) action;
            final Comparator<?> comparator = sortedComparatorAction.getComparator();
            if (comparator instanceof FieldComparator) {
                final FieldComparator<?> fieldComparator = (FieldComparator<?>) comparator;
                // We can only optimize filters if the ordering is retained.. Bar equal, isNull, isNotNull etc.
                return fieldComparator.getField().typeMapper().getOrdering() == Ordering.RETAIN;
            }
            if (comparator instanceof CombinedComparator) {
                final CombinedComparator<?> combinedComparator = (CombinedComparator<?>) comparator;
                // We can only optimize filters if the ordering is retained.. Bar equal, isNull, isNotNull etc.                
                return combinedComparator.stream()
                    .map(FieldComparator::getField)
                    .map(Field::typeMapper)
                    .map(TypeMapper::getOrdering)
                    .allMatch(o -> o == Ordering.RETAIN);
            }
        }
        return false;
    }

    public static <ENTITY> void modifySource(
        final List<FieldPredicate<ENTITY>> predicateBuilders,
        final SqlStreamOptimizerInfo<ENTITY> info,
        final AsynchronousQueryResult<ENTITY> query
    ) {
        requireNonNull(predicateBuilders);
        requireNonNull(info);
        requireNonNull(query);

        final List<FieldPredicate<ENTITY>> optimizedPredicateBuilders = optimize(predicateBuilders);

        final FieldPredicateView spv = info.getDbmsType().getFieldPredicateView();
        final List<SqlPredicateFragment> fragments = optimizedPredicateBuilders.stream()
            .map(sp -> spv.transform(info.getSqlColumnNamer(), info.getSqlDatabaseTypeFunction(), sp))
            .collect(toList());

        final String sql = info.getSqlSelect() + " WHERE "
            + fragments.stream()
                .map(SqlPredicateFragment::getSql)
                .collect(joining(" AND "));

        final List<Object> values = new ArrayList<>(fragments.size());
        for (int i = 0; i < fragments.size(); i++) {

            final FieldPredicate<ENTITY> p = optimizedPredicateBuilders.get(i);
            final Field<ENTITY> referenceFieldTrait = p.getField();

            @SuppressWarnings("unchecked")
            final TypeMapper<Object, Object> tm = (TypeMapper<Object, Object>) referenceFieldTrait.typeMapper();

            fragments.get(i).objects()
                .map(tm::toDatabaseType)
                .forEach(values::add);
        }

        query.setSql(sql);
        query.setValues(values);
    }


    private static <ENTITY> List<FieldPredicate<ENTITY>> optimize(List<FieldPredicate<ENTITY>> list) {
        return list.stream()
            .filter(sp -> sp.getPredicateType() != PredicateType.ALWAYS_TRUE) // Fix #495
            .collect(toList());
    }


    public interface RenderResult {

        String getSql();

        List<Object> getValues();

    }

    private static final class RenderResultImpl implements RenderResult {

        private final String sql;
        private final List<Object> values;

        RenderResultImpl(String sql, List<Object> values /*, Pipeline pipeline*/) {
            this.sql = sql;
            this.values = values;
        }

        @Override
        public String getSql() {
            return sql;
        }

        @Override
        public List<Object> getValues() {
            return values;
        }

        @Override
        public String toString() {
            return String.format("RenderResultImpl {sql=%s, values=%s}", sql, values);
        }
    }

    public static <ENTITY> RenderResult renderSqlWhere(
        final DbmsType dbmsType,
        final Function<Field<ENTITY>, String> columnNamer,
        final Function<Field<ENTITY>, Class<?>> columnDbTypeFunction,
        final List<Predicate<ENTITY>> predicates
    ) {
        final FieldPredicateView predicateView = dbmsType.getFieldPredicateView();
        final StringBuilder sql = new StringBuilder();
        final List<Object> values = new ArrayList<>();
        final AtomicInteger cnt = new AtomicInteger();
        predicates
            .stream()
            // Optimize away ALWAYS_TRUE. Fix #495
            .filter(p -> {
                if (p instanceof FieldPredicate) {
                    return ((FieldPredicate)p).getPredicateType() != PredicateType.ALWAYS_TRUE;
                } else {
                    return true;
                }
            })
            .forEach(predicate -> {
            if (cnt.getAndIncrement() != 0) {
                sql.append(" AND ");
            }
            renderSqlWhereHelper(predicateView, columnNamer, columnDbTypeFunction, sql, values, predicate);
        });
        return new RenderResultImpl(sql.toString(), values);
    }

    // See JoinSqlUtil::renderPredicateHelper for JOIN streams

    private static <ENTITY> void renderSqlWhereHelper(
        final FieldPredicateView spv,
        final Function<Field<ENTITY>, String> columnNamer,
        final Function<Field<ENTITY>, Class<?>> columnDbTypeFunction,
        final StringBuilder sql,
        final List<Object> values,
        final Predicate<ENTITY> predicate
    ) {
        if (predicate instanceof FieldPredicate) {
            final FieldPredicate<ENTITY> fieldPredicate = (FieldPredicate<ENTITY>) predicate;
            final SqlPredicateFragment fragment = spv.transform(columnNamer, columnDbTypeFunction, fieldPredicate);
            final Field<ENTITY> referenceFieldTrait = fieldPredicate.getField();
            @SuppressWarnings("unchecked")
            final TypeMapper<Object, Object> tm = (TypeMapper<Object, Object>) referenceFieldTrait.typeMapper();

            sql.append(fragment.getSql());
            fragment.objects().map(tm::toDatabaseType).forEachOrdered(values::add);
        } else if (predicate instanceof CombinedPredicate) {
            final CombinedPredicate<ENTITY> combinedPredicate = (CombinedPredicate<ENTITY>) predicate;
            final StringBuilder internalSql = new StringBuilder();
            final List<Object> internalValues = new ArrayList<>();
            final AtomicInteger cnt = new AtomicInteger();
            combinedPredicate.stream().forEachOrdered(internalPredicate -> {
                if (cnt.getAndIncrement() != 0) {
                    internalSql.append(" ").append(combinedPredicate.getType().toString()).append(" ");
                }
                @SuppressWarnings("unchecked")
                final Predicate<ENTITY> castedInternalPredicate = (Predicate<ENTITY>) internalPredicate;
                renderSqlWhereHelper(
                    spv,
                    columnNamer,
                    columnDbTypeFunction,
                    internalSql,
                    internalValues,
                    castedInternalPredicate
                );
            });
            sql.append("(").append(internalSql).append(")");
            values.addAll(internalValues);
        } else {
            throw new IllegalArgumentException("A predicate that is nether an instanceof FieldPredicate nor CombinedPredicate was given:" + predicate.toString());
        }
    }

}