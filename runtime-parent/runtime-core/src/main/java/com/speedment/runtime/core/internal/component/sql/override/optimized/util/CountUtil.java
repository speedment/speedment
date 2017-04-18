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
package com.speedment.runtime.core.internal.component.sql.override.optimized.util;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.db.SqlPredicateFragment;
import com.speedment.runtime.core.internal.stream.builder.action.reference.FilterAction;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil;
import com.speedment.runtime.core.stream.Pipeline;
import com.speedment.runtime.core.stream.action.Action;
import static com.speedment.runtime.core.stream.action.Property.SIZE;
import static com.speedment.runtime.core.stream.action.Verb.PRESERVE;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.typemapper.TypeMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.LongSupplier;
import java.util.function.Predicate;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Per Minborg
 */
public final class CountUtil {

    private static final Predicate<Action<?, ?>> PRESERVE_SIZE = action -> action.is(PRESERVE, SIZE);

    /**
     * Optimizer for count operations.
     *
     * @param <ENTITY> the entity type
     * @param info about the stream optimizer
     * @param pipeline the pipeline
     * @param fallbackSupplier a fallback supplier should every item be size
     * retaining
     * @return the number of rows
     */
    public static <ENTITY> long countHelper(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final Pipeline pipeline,
        final LongSupplier fallbackSupplier
    ) {
        requireNonNull(info);
        requireNonNull(pipeline);
        requireNonNull(fallbackSupplier);

        if (allActionsAreOptimizableFilters(pipeline)) {
            // select count(*) from 'table' where ...
            final List<FieldPredicate<ENTITY>> andPredicateBuilders = StreamTerminatorUtil.topLevelAndPredicates(pipeline);

            final SqlInfo sqlInfo = sqlInfo(info, andPredicateBuilders);
            return info.getCounter().apply(sqlInfo.sql, sqlInfo.values);
        } else {
            // Iterate over all materialized ENTITIES....
            return fallbackSupplier.getAsLong();
        }
    }

    private static <ENTITY> SqlInfo sqlInfo(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final List<FieldPredicate<ENTITY>> predicateBuilders
    ) {
        requireNonNull(info);
        requireNonNull(predicateBuilders);

        if (predicateBuilders.isEmpty()) {
            // Nothing to do...
            return new SqlInfo(info.getSqlSelectCount(), Collections.emptyList());
        }

        final FieldPredicateView spv = info.getDbmsType().getFieldPredicateView();
        final List<SqlPredicateFragment> fragments = predicateBuilders.stream()
            .map(sp -> spv.transform(info.getSqlColumnNamer(), info.getSqlDatabaseTypeFunction(), sp))
            .collect(toList());

        final String sql = info.getSqlSelectCount() + " WHERE "
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

    private static boolean allActionsAreOptimizableFilters(Pipeline pipeline) {
        boolean scanningFieldPredicate = true;
        for (Action<?, ?> action : pipeline) {
            if (scanningFieldPredicate) {
                if (action instanceof FilterAction) {
                    if (!(((FilterAction) action).getPredicate() instanceof FieldPredicate)) {
                        // We have a non-field predicate and we cannot count on the DB side
                        return false;
                    }
                } else {
                    scanningFieldPredicate = false;
                }
            }
            if (!scanningFieldPredicate) {
                if (!PRESERVE_SIZE.test(action)) {
                    return false;
                }
            }
        }
        return true;

    }

    private static class SqlInfo {

        private final String sql;
        private final List<Object> values;

        public SqlInfo(String sql, List<Object> values) {
            this.sql = sql;
            this.values = values;
        }

    }

    private CountUtil() {
        throw new UnsupportedOperationException();
    }

}
