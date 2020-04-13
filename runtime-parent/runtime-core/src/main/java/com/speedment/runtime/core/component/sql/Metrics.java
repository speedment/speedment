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
package com.speedment.runtime.core.component.sql;

import com.speedment.runtime.core.internal.component.sql.MetricsImpl;

/**
 *
 */
public interface Metrics {

    /**
     * Returns the number of pipeline reductions an optimizer was able to do. If
     * an optimizer cannot optimize the pipeline at all, then {@code 0} should
     * be returned.
     *
     * @return The number of pipeline reductions an optimizer was able to do
     */
    int getPipelineReductions();

    /**
     * Returns the number of while predicate expressions in the SQL query.
     *
     * @return the number of while predicate expressions in the SQL query
     */
    int getSqlWhileCount();

    /**
     * Returns the number of order by columns in the SQL query.
     *
     * @return the number of order by columns in the SQL query
     */
    int getSqlOrderCount();

    /**
     * Returns the number of skip statements (0 or 1) in the SQL query.
     *
     * @return the number of skip statements (0 or 1) in the SQL query
     */
    int getSqlSkipCount();

    /**
     * Returns the number of limit statements (0 or 1) in the SQL query.
     *
     * @return the number of limit statements (0 or 1) in the SQL query
     */
    int getSqlLimitCount();

    /**
     * Returns the sum of {@link #getSqlWhileCount() }, {@link #getSqlOrderCount() }
     * , {@link #getSqlSkipCount() } and {@link #getSqlLimitCount() }.
     *
     *
     * @return the sum of {@link #getSqlWhileCount() }, {@link #getSqlOrderCount() }
     * , {@link #getSqlSkipCount() } and {@link #getSqlLimitCount() }
     */
    default int getSqlCount() {
        return getSqlWhileCount() + getSqlOrderCount() + getSqlSkipCount() + getSqlLimitCount();
    }

    /**
     * Creates and returns a new Metrics.
     *
     * @param pipelineReductions number of pipeline reductions that were made
     * @param sqlWhileCount the number of while predicates
     * @param sqlOrderCount the number of columns to sort by
     * @param sqlSkipCount the number of skip statements (0 or 1)
     * @param sqlLimitCount the number of limit statements (0 or 1)
     * @return a new Metrics
     */
    static Metrics of(
        final int pipelineReductions,
        final int sqlWhileCount,
        final int sqlOrderCount,
        final int sqlSkipCount,
        final int sqlLimitCount
    ) {
        if (pipelineReductions == 0 && sqlWhileCount == 0 && sqlOrderCount == 0 && sqlSkipCount == 0 && sqlLimitCount == 0) {
            return empty();
        }
        return new MetricsImpl(pipelineReductions, sqlWhileCount, sqlOrderCount, sqlSkipCount, sqlLimitCount);
    }

    /**
     * Returns an empty metrics. This can be used by optimizers that are unable
     * to optimize the current stream.
     *
     * @return Returns an empty metrics
     */
    static Metrics empty() {
        return MetricsImpl.EMPTY_INSTANCE;
    }

}
