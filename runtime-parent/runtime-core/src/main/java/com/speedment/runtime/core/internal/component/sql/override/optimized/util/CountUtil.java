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
package com.speedment.runtime.core.internal.component.sql.override.optimized.util;

import static com.speedment.runtime.core.stream.action.Property.SIZE;
import static com.speedment.runtime.core.stream.action.Verb.PRESERVE;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsTypeDefault.SubSelectAlias;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.stream.Pipeline;
import com.speedment.runtime.core.stream.action.Action;

import java.util.List;
import java.util.function.LongSupplier;
import java.util.function.Predicate;

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
     * @param sqlStreamTerminator that called us
     * @param pipeline the pipeline
     * @param fallbackSupplier a fallback supplier should every item be size
     * retaining
     * @return the number of rows
     */
    public static <ENTITY> long countHelper(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final Pipeline pipeline,
        final LongSupplier fallbackSupplier
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        requireNonNull(fallbackSupplier);

        // Can we count it directly (with no sub-select query)?
        if (pipeline.stream().allMatch(PRESERVE_SIZE)) {
            return info.getCounter().applyAsLong(info.getSqlSelectCount(), emptyList());
        } else {

            final Pipeline optimizedPipeline = sqlStreamTerminator.optimize(pipeline);
            // Can we count using a sub-select query?
            if (optimizedPipeline.stream().allMatch(PRESERVE_SIZE)) {
                final AsynchronousQueryResult<ENTITY> asynchronousQueryResult = sqlStreamTerminator.getAsynchronousQueryResult();
                final StringBuilder sql = new StringBuilder()
                    .append("SELECT COUNT(*) FROM (")
                    .append(asynchronousQueryResult.getSql())
                    .append(")");

                if (info.getDbmsType().getSubSelectAlias() == SubSelectAlias.REQUIRED) {
                    sql.append(" AS A");
                }
                @SuppressWarnings("unchecked")
                final List<Object> values = (List<Object>) asynchronousQueryResult.getValues();
                return info.getCounter().applyAsLong(sqlStreamTerminator.attachTraceData(sql.toString()), values);
            } else {
                // Iterate over all materialized ENTITIES....
                return fallbackSupplier.getAsLong();
            }
        }
    }

    private CountUtil() {
        throw new UnsupportedOperationException();
    }

}
