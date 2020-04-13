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

import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.stream.Pipeline;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> Entity type
 */
public interface SqlStreamOptimizer<ENTITY> {


    /**
     * Returns a metric of how well this optimizer can optimize the given
     * pipeline. The best imaginable optimization metric is
     * {@code Integer.MAX_VALUE}. If this optimizer cannot optimize the pipeline
     * at all, then {@code 0} should be returned.
     * <p>
     * As a rule of thumb, metrics could return the number of steps in the
     * pipeline that it is able to eliminate times 10.
     *
     * @param <P> Pipeline type
     * @param initialPipeline to optimize
     * @param dbmsType the type of the database
     * @return how well this optimizer can optimize the given pipeline
     */
    <P extends Pipeline> Metrics metrics(P initialPipeline, DbmsType dbmsType);

    /**
     * Returns an optimized pipeline, potentially by modifying the query.
     *
     * @param <P> Pipeline type
     * @param initialPipeline to use
     * @param info about the SQL
     * @param query to optimize
     * @return an optimized pipeline
     */
    <P extends Pipeline> P optimize(
        P initialPipeline,
        SqlStreamOptimizerInfo<ENTITY> info,
        AsynchronousQueryResult<ENTITY> query
    );

}
