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
package com.speedment.runtime.core.provider;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizer;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizerComponent;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.component.sql.SqlStreamOptimizerComponentImpl;
import com.speedment.runtime.core.stream.Pipeline;

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public final class DelegateSqlStreamOptimizerComponent implements SqlStreamOptimizerComponent {

    private final SqlStreamOptimizerComponent inner;

    public DelegateSqlStreamOptimizerComponent() {
        this.inner = new SqlStreamOptimizerComponentImpl();
    }

    @Override
    public <ENTITY> SqlStreamOptimizer<ENTITY> get(Pipeline initialPipeline, DbmsType dbmsType) {
        return inner.get(initialPipeline, dbmsType);
    }

    @Override
    public <ENTITY> void install(SqlStreamOptimizer<ENTITY> sqlStreamOptimizer) {
        inner.install(sqlStreamOptimizer);
    }

    @Override
    public Stream<SqlStreamOptimizer<?>> stream() {
        return inner.stream();
    }
}
