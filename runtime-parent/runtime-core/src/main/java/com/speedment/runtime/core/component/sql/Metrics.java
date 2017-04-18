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
     * Creates and returns a new Metrics.
     *
     * @param pipelineReductions number of pipeline reductions that were made
     * @return a new Metrics
     */
    static Metrics of(int pipelineReductions) {
        return new MetricsImpl(pipelineReductions);
    }

    /**
     * Returns an empty metrics. This can be used by optimizers that are unable
     * to optimize the current stream.
     *
     * @return Returns an empty metrics
     */
    static Metrics empty() {
        return MetricsImpl.EMPTY;
    }

}
