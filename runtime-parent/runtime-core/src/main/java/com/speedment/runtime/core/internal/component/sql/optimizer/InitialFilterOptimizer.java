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
package com.speedment.runtime.core.internal.component.sql.optimizer;

import com.speedment.runtime.core.component.sql.Metrics;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizer;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import static com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil.modifySource;
import static com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil.topLevelAndPredicates;
import com.speedment.runtime.core.stream.Pipeline;
import com.speedment.runtime.field.predicate.FieldPredicate;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * This Optimizer can take care of the case where there is a mix of Field
 * predicates and other predicates. Field predicates will be optimized and other
 * predicates will be applied in the stream.
 *
 * @author Per Minborg
 * @param <ENTITY> the entity type
 */
public final class InitialFilterOptimizer<ENTITY> implements SqlStreamOptimizer<ENTITY> {

    // Todo: A more general expression would be better. Eg. stream().peek().filter() would still be possible...
    @Override
    public Metrics metrics(Pipeline initialPipeline, DbmsType dbmsType) {
        requireNonNull(initialPipeline);
        requireNonNull(dbmsType);
        int noFilters = topLevelAndPredicates(initialPipeline).size();
        return Metrics.of(noFilters);
    }

    @Override
    public <P extends Pipeline> P optimize(
        final P initialPipeline,
        final SqlStreamOptimizerInfo<ENTITY> info,
        final AsynchronousQueryResult<ENTITY> query
    ) {
        requireNonNull(initialPipeline);
        requireNonNull(info);
        requireNonNull(query);
        final List<FieldPredicate<ENTITY>> andPredicateBuilders = topLevelAndPredicates(initialPipeline);

        if (!andPredicateBuilders.isEmpty()) {
            modifySource(andPredicateBuilders, info, query);
        }
        return initialPipeline;
    }

}
