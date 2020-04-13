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
package com.speedment.runtime.core.internal.component.sql.optimizer;

import com.speedment.runtime.core.component.sql.Metrics;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizer;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.stream.builder.action.reference.FilterAction;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil;
import com.speedment.runtime.core.stream.Pipeline;
import com.speedment.runtime.core.stream.action.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil.isContainingOnlyFieldPredicate;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

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
    // Todo: Allow CombinedPredicates
    @Override
    public Metrics metrics(Pipeline initialPipeline, DbmsType dbmsType) {
        requireNonNull(initialPipeline);
        requireNonNull(dbmsType);
        final AtomicInteger filterCounter = new AtomicInteger();
        traverse(initialPipeline, unused -> filterCounter.getAndIncrement());
        return Metrics.of(filterCounter.get(), filterCounter.get(), 0, 0, 0);
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

        final List<FilterAction<ENTITY>> filters = new ArrayList<>();
        traverse(initialPipeline, filters::add);

        final List<Object> values = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();

        sql.append(info.getSqlSelect());

        if (!filters.isEmpty()) {
            @SuppressWarnings("unchecked")
            List<Predicate<ENTITY>> predicates = filters.stream()
                .map(FilterAction::getPredicate)
                .map(p -> (Predicate<ENTITY>) p)
                .collect(toList());

            final StreamTerminatorUtil.RenderResult rr = StreamTerminatorUtil.renderSqlWhere(
                info.getDbmsType(),
                info.getSqlColumnNamer(),
                info.getSqlDatabaseTypeFunction(),
                predicates
            );

            final String whereFragmentSql = rr.getSql();
            if (!whereFragmentSql.isEmpty()) {
                sql.append(" WHERE ").append(whereFragmentSql);
                values.addAll(rr.getValues());
            }
        }

        query.setSql(sql.toString());
        query.setValues(values);

        initialPipeline.removeIf(filters::contains);
        return initialPipeline;
    }

    private void traverse(Pipeline pipeline,
        final Consumer<? super FilterAction<ENTITY>> filterConsumer
    ) {
        for (Action<?, ?> action : pipeline) {
            if (action instanceof FilterAction) {
                @SuppressWarnings("unchecked")
                final FilterAction<ENTITY> filterAction = (FilterAction<ENTITY>) action;
                if (isContainingOnlyFieldPredicate(filterAction.getPredicate())) {
                    filterConsumer.accept(filterAction);
                }
            } else {
                // We are done. Only initial filters can be optimized
                return;
            }
        }
    }

}
