/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
