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

import com.speedment.runtime.core.component.sql.*;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.component.sql.optimiser.InitialFilterOptimizer;
import com.speedment.runtime.core.stream.Pipeline;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Per Minborg
 */
public final class SqlStreamOptimizerComponentImpl implements SqlStreamOptimizerComponent {

    private static final SqlStreamOptimizer<?> FALL_BACK = new FallbackStreamOptimizer<>();

    private final List<SqlStreamOptimizer<?>> optimizers;

    public SqlStreamOptimizerComponentImpl() {
        this.optimizers = new CopyOnWriteArrayList<>();
        install(new InitialFilterOptimizer<>());
    }

    @Override
    public <ENTITY> SqlStreamOptimizer<ENTITY> get(Pipeline initialPipeline, DbmsType dbmsType) {
        @SuppressWarnings("unchecked")
        SqlStreamOptimizer<ENTITY> result = (SqlStreamOptimizer<ENTITY>) FALL_BACK;
        int metric = 0;
        for (int i = 0; i < optimizers.size(); i++) {
            @SuppressWarnings("unchecked")
            SqlStreamOptimizer<ENTITY> candidate = (SqlStreamOptimizer<ENTITY>) optimizers.get(i);
            int candidateMetric = candidate.metrics(initialPipeline, dbmsType);
            if (candidateMetric > metric) {
                metric = candidateMetric;
                result = candidate;
                if (metric == Integer.MAX_VALUE) {
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

    private static class FallbackStreamOptimizer<ENTITY> implements SqlStreamOptimizer<ENTITY> {

        @Override
        public <P extends Pipeline> int metrics(P initialPipeline, DbmsType dbmsType) {
            return 0;
        }

        @Override
        public <P extends Pipeline> P optimize(P initialPipeline, SqlStreamOptimizerInfo<ENTITY> info, AsynchronousQueryResult<ENTITY> query) {
            return initialPipeline;
        }

    }

}
