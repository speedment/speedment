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
package com.speedment.runtime.core.internal.component.sql.optimiser;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizer;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.db.SqlPredicateFragment;
import static com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil.topLevelAndPredicates;
import com.speedment.runtime.core.stream.Pipeline;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.typemapper.TypeMapper;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Per Minborg
 */
public final class InitialFilterOptimizer<ENTITY> implements SqlStreamOptimizer<ENTITY> {

    // Todo: A more general expression would be better. Eg. stream().peek().filter() would still be possible...
    @Override
    public int metrics(Pipeline initialPipeline, DbmsType dbmsType) {
        requireNonNull(initialPipeline);
        requireNonNull(dbmsType);
        int noFilters = topLevelAndPredicates(initialPipeline).size();
        return noFilters * 10;
    }

    @Override
    public <P extends Pipeline> P optimize(
        final P initialPipeline,
        final SqlStreamOptimizerInfo<ENTITY> info,
        final AsynchronousQueryResult<ENTITY> query
    ) {
        requireNonNull(initialPipeline);
        requireNonNull(query);
        final List<FieldPredicate<ENTITY>> andPredicateBuilders = topLevelAndPredicates(initialPipeline);

        if (!andPredicateBuilders.isEmpty()) {
            modifySource(andPredicateBuilders, info, query);
        }

        return initialPipeline;
    }

    private void modifySource(
        final List<FieldPredicate<ENTITY>> predicateBuilders,
        final SqlStreamOptimizerInfo<ENTITY> info,
        final AsynchronousQueryResult<ENTITY> query
    ) {
        requireNonNull(predicateBuilders);
        requireNonNull(query);

        final FieldPredicateView spv = info.getDbmsType().getFieldPredicateView();
        final List<SqlPredicateFragment> fragments = predicateBuilders.stream()
            .map(sp -> spv.transform(info.getSqlColumnNamer(), info.getSqlDatabaseTypeFunction(), sp))
            .collect(toList());

        final String sql = info.getSqlSelect() + " WHERE "
            + fragments.stream()
                .map(SqlPredicateFragment::getSql)
                .collect(joining(" AND "));

        final List<Object> values = new ArrayList<>();
        for (int i = 0; i < fragments.size(); i++) {

            final FieldPredicate<ENTITY> p = predicateBuilders.get(i);
            final Field<ENTITY> referenceFieldTrait = p.getField();

            @SuppressWarnings("unchecked")
            final TypeMapper<Object, Object> tm = (TypeMapper<Object, Object>) referenceFieldTrait.typeMapper();

            fragments.get(i).objects()
                .map(tm::toDatabaseType)
                .forEach(values::add);
        }

        query.setSql(sql);
        query.setValues(values);
    }

}
