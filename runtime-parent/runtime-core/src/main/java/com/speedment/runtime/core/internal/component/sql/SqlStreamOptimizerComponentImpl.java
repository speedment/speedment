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

import static com.speedment.common.logger.Level.DEBUG;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.component.sql.*;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.component.sql.optimizer.FilterSortedSkipOptimizer;
import com.speedment.runtime.core.internal.component.sql.optimizer.InitialFilterOptimizer;
import com.speedment.runtime.core.stream.Pipeline;
import java.util.Comparator;
import static java.util.Comparator.comparingInt;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public final class SqlStreamOptimizerComponentImpl implements SqlStreamOptimizerComponent {

    private static final Logger LOGGER_STREAM_OPTIMIZER = LoggerManager.getLogger(ApplicationBuilder.LogType.STREAM_OPTIMIZER.getLoggerName());

    private static final SqlStreamOptimizer<?> FALL_BACK = new FallbackStreamOptimizer<>();

    private final List<SqlStreamOptimizer<?>> optimizers;

    public SqlStreamOptimizerComponentImpl() {
        this.optimizers = new CopyOnWriteArrayList<>();
        install(new InitialFilterOptimizer<>());
        install(new FilterSortedSkipOptimizer<>());
    }

    @Override
    public <ENTITY> SqlStreamOptimizer<ENTITY> get(Pipeline initialPipeline, DbmsType dbmsType) {
        if (DEBUG.isEqualOrHigherThan(LOGGER_STREAM_OPTIMIZER.getLevel())) {
            LOGGER_STREAM_OPTIMIZER.debug("Evaluating %s pipeline: %s", initialPipeline.isParallel() ? "parallel" : "sequential", initialPipeline.toString());
        }
        final SqlStreamOptimizer<ENTITY> result = getHelper(initialPipeline, dbmsType);
        if (DEBUG.isEqualOrHigherThan(LOGGER_STREAM_OPTIMIZER.getLevel())) {
            LOGGER_STREAM_OPTIMIZER.debug("Selected: %s", result.getClass().getSimpleName());
        }
        return result;
    }

    private static final Comparator<Metrics> METRICS_COMPARATOR
        = comparingInt(Metrics::getPipelineReductions)
            .thenComparing(comparingInt(Metrics::getSqlCount).reversed());

    private <ENTITY> SqlStreamOptimizer<ENTITY> getHelper(Pipeline initialPipeline, DbmsType dbmsType) {
        @SuppressWarnings("unchecked")
        SqlStreamOptimizer<ENTITY> result = (SqlStreamOptimizer<ENTITY>) FALL_BACK;
        if (initialPipeline.isEmpty()) {
            return result;
        }

        Metrics metric = Metrics.empty();
        for (int i = optimizers.size() - 1; i >= 0; i--) {
            @SuppressWarnings("unchecked")
            final SqlStreamOptimizer<ENTITY> candidate = (SqlStreamOptimizer<ENTITY>) optimizers.get(i);
            final Metrics candidateMetric = candidate.metrics(initialPipeline, dbmsType);
            if (DEBUG.isEqualOrHigherThan(LOGGER_STREAM_OPTIMIZER.getLevel())) {
                LOGGER_STREAM_OPTIMIZER.debug("Candidate: %-30s : %s ", candidate.getClass().getSimpleName(), candidateMetric);
            }
            if (METRICS_COMPARATOR.compare(candidateMetric, metric) > 0) {
                metric = candidateMetric;
                result = candidate;
                if (metric.getPipelineReductions() == Integer.MAX_VALUE) {
                    return result;
                }
            }
        }
        return result;
    }

    @Override
    public <ENTITY> void install(SqlStreamOptimizer<ENTITY> sqlStreamOptimizer) {
        requireNonNull(sqlStreamOptimizer);
        optimizers.add(sqlStreamOptimizer);
    }

    @Override
    public Stream<SqlStreamOptimizer<?>> stream() {
        return optimizers.stream();
    }

    private static class FallbackStreamOptimizer<ENTITY> implements SqlStreamOptimizer<ENTITY> {

        @Override
        public <P extends Pipeline> Metrics metrics(P initialPipeline, DbmsType dbmsType) {
            return Metrics.empty();
        }

        @Override
        public <P extends Pipeline> P optimize(P initialPipeline, SqlStreamOptimizerInfo<ENTITY> info, AsynchronousQueryResult<ENTITY> query) {
            return initialPipeline;
        }

    }

}
