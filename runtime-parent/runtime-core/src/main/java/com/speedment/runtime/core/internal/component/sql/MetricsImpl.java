/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
