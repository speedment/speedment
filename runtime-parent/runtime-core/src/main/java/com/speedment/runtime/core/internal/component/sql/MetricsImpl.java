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
package com.speedment.runtime.core.internal.component.sql;

import com.speedment.runtime.core.component.sql.Metrics;

import static com.speedment.common.invariant.IntRangeUtil.requireNonNegative;

/**
 *
 * @author Per Minborg
 */
public final class MetricsImpl implements Metrics {

    public static final Metrics EMPTY_INSTANCE = new MetricsImpl(0, 0, 0, 0, 0);

    private final int pipelineReductions;
    private final int sqlWhileCount;
    private final int sqlOrderCount;
    private final int sqlSkipCount;
    private final int sqlLimitCount;

    public MetricsImpl(
        final int pipelineReductions,
        final int sqlWhileCount,
        final int sqlOrderCount,
        final int sqlSkipCount,
        final int sqlLimitCount
    ) {
        this.pipelineReductions = requireNonNegative(pipelineReductions);
        this.sqlWhileCount = requireNonNegative(sqlWhileCount);
        this.sqlOrderCount = requireNonNegative(sqlOrderCount);
        this.sqlSkipCount = requireNonNegative(sqlSkipCount);
        this.sqlLimitCount = requireNonNegative(sqlLimitCount);
    }

    @Override
    public int getPipelineReductions() {
        return pipelineReductions;
    }

    @Override
    public int getSqlWhileCount() {
        return sqlWhileCount;
    }

    @Override
    public int getSqlOrderCount() {
        return sqlOrderCount;
    }

    @Override
    public int getSqlSkipCount() {
        return sqlSkipCount;
    }

    @Override
    public int getSqlLimitCount() {
        return sqlLimitCount;
    }

    @Override
    public String toString() {
        return String.format("Metrics {pipelineReductions = %d, sqlWhileCount = %d, sqlOrderCount = %d, sqlSkipCount = %d, sqlLimitCount = %d}",
            getPipelineReductions(),
            getSqlWhileCount(),
            getSqlOrderCount(),
            getSqlSkipCount(),
            getSqlLimitCount()
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Metrics)) {
            return false;
        }
        final Metrics that = (Metrics) obj;
        return this.getPipelineReductions() == that.getPipelineReductions()
            && this.getSqlWhileCount() == that.getSqlWhileCount()
            && this.getSqlOrderCount() == that.getSqlOrderCount()
            && this.getSqlSkipCount() == that.getSqlSkipCount()
            && this.getSqlLimitCount() == that.getSqlLimitCount();
    }

    @Override
    public int hashCode() {
        int hash = 33;
        hash += 33 * getPipelineReductions();
        hash += 33 * getSqlWhileCount();
        hash += 33 * getSqlOrderCount();
        hash += 33 * getSqlSkipCount();
        hash += 33 * getSqlLimitCount();
        return hash;
    }

}
