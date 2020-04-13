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

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.stream.Pipeline;

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
@InjectKey(SqlStreamOptimizerComponent.class)
public interface SqlStreamOptimizerComponent {

    <ENTITY> SqlStreamOptimizer<ENTITY> get(Pipeline initialPipeline, DbmsType dbmsType);

    <ENTITY> void install(SqlStreamOptimizer<ENTITY> sqlStreamOptimizer);

    /**
     * Create and return a new Stream of all the installed
     * SqlStreamOptimizer objects.
     * <p>
     * This is mainly for testing.
     *
     * @return a new Stream of all the installed
     *         SqlStreamOptimizer objects
     */
    Stream<SqlStreamOptimizer<?>> stream();

}
