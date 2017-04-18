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
package com.speedment.runtime.core.internal.component.sql;

import static com.speedment.common.invariant.IntRangeUtil1.requireNonNegative;
import com.speedment.runtime.core.component.sql.Metrics;

/**
 *
 * @author Per Minborg
 */
public class MetricsImpl implements Metrics {

    private final int pipelineReductions;

    public MetricsImpl(int pipelineReductions) {
        this.pipelineReductions = requireNonNegative(pipelineReductions);
    }

    @Override
    public int getPipelineReductions() {
        return pipelineReductions;
    }

    @Override
    public String toString() {
        return String.format("Metrics {pipelineReductions = %d}", getPipelineReductions());
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
        return this.getPipelineReductions() == that.getPipelineReductions();
    }

    @Override
    public int hashCode() {
        return getPipelineReductions();
    }

    public static final Metrics EMPTY = new MetricsImpl(0) {
        @Override
        public String toString() {
            return "Metrics.empty()";
        }

    };

}
