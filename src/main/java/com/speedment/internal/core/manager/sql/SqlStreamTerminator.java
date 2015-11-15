/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.manager.sql;

import com.speedment.config.Column;
import com.speedment.config.mapper.TypeMapper;
import com.speedment.db.AsynchronousQueryResult;
import com.speedment.internal.core.stream.builder.action.Action;
import static com.speedment.internal.core.stream.builder.action.Property.SIZE;
import static com.speedment.internal.core.stream.builder.action.Verb.PRESERVE;
import com.speedment.internal.core.stream.builder.pipeline.DoublePipeline;
import com.speedment.internal.core.stream.builder.pipeline.IntPipeline;
import com.speedment.internal.core.stream.builder.pipeline.LongPipeline;
import com.speedment.internal.core.stream.builder.pipeline.Pipeline;
import com.speedment.internal.core.stream.builder.pipeline.ReferencePipeline;
import com.speedment.internal.core.stream.builder.streamterminator.StreamTerminator;
import java.util.Collections;
import java.util.List;
import java.util.function.LongSupplier;
import java.util.function.Predicate;
import static java.util.stream.Collectors.toList;
import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.field.trait.FieldTrait;
import com.speedment.internal.core.stream.builder.streamterminator.StreamTerminatorUtil;
import com.speedment.stream.StreamDecorator;
import java.util.ArrayList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 */
public final class SqlStreamTerminator<ENTITY> implements StreamTerminator {
    
    private final AbstractSqlManager<ENTITY> manager;
    private final AsynchronousQueryResult<ENTITY> asynchronousQueryResult;
    private final StreamDecorator decorator;
    
    public SqlStreamTerminator(AbstractSqlManager<ENTITY> manager, AsynchronousQueryResult<ENTITY> asynchronousQueryResult, StreamDecorator decorator) {
        this.manager = requireNonNull(manager);
        this.asynchronousQueryResult = requireNonNull(asynchronousQueryResult);
        this.decorator = requireNonNull(decorator);
    }
    
    @Override
    public StreamDecorator getStreamDecorator() {
        return decorator;
    }
    
    @Override
    public <P extends Pipeline> P optimize(P initialPipeline) {
        requireNonNull(initialPipeline);
        final List<SpeedmentPredicate<ENTITY, ?>> andPredicateBuilders = StreamTerminatorUtil.topLevelAndPredicates(initialPipeline);
        
        if (!andPredicateBuilders.isEmpty()) {
            modifySource(andPredicateBuilders, asynchronousQueryResult);
        }
        
        return getStreamDecorator().apply(initialPipeline);
    }
    
    public void modifySource(final List<SpeedmentPredicate<ENTITY, ?>> predicateBuilders, AsynchronousQueryResult<ENTITY> qr) {
        requireNonNull(predicateBuilders);
        requireNonNull(qr);
        if (predicateBuilders.isEmpty()) {
            // Nothing to do...
            return;
        }
        
        final List<Column> columns = predicateBuilders
                .stream()
                .map(SpeedmentPredicate::getField)
                .map(FieldTrait::getColumnName)
                .map(this::findColumn)
                .collect(toList());
        
        final SpeedmentPredicateView spv = new MySqlSpeedmentPredicateView();
        final List<SqlPredicateFragment> fragments = predicateBuilders.stream()
                .map(spv::transform)
                .collect(toList());
        
        final String sql = manager.sqlSelect(" where "
                + fragments.stream()
                .map(SqlPredicateFragment::getSql)
                .collect(joining(" AND "))
        );

//        @SuppressWarnings("rawtypes")
//        final List<Object> values = fragments.stream()
//            .flatMap(SqlPredicateFragment::objects)
//            .collect(toList());
        final List<Object> values = new ArrayList<>();
        for (int i = 0; i < fragments.size(); i++) {
            @SuppressWarnings("unchecked")
            final TypeMapper<Object, Object> tm = (TypeMapper<Object, Object>) columns.get(i).getTypeMapper();
            fragments.get(i).objects()
                    .map(tm::toDatabaseType)
                    .forEach(values::add);
        }
        
        qr.setSql(sql);
        qr.setValues(values);
    }
    
    private Column findColumn(String name) {
        return manager.getTable().streamOfColumns()
                .filter(c -> name.equals(c.getName()))
                .findAny().get();
    }
    
    @Override
    public long count(DoublePipeline pipeline) {
        requireNonNull(pipeline);
        return countHelper(pipeline, () -> StreamTerminator.super.count(pipeline));
    }
    
    @Override
    public <T> long count(IntPipeline pipeline) {
        requireNonNull(pipeline);
        return countHelper(pipeline, () -> StreamTerminator.super.count(pipeline));
    }
    
    @Override
    public long count(LongPipeline pipeline) {
        requireNonNull(pipeline);
        return countHelper(pipeline, () -> StreamTerminator.super.count(pipeline));
    }
    
    @Override
    public <T> long count(ReferencePipeline<T> pipeline) {
        requireNonNull(pipeline);
        return countHelper(pipeline, () -> StreamTerminator.super.count(pipeline));
    }
    
    private static final Predicate<Action<?, ?>> CHECK_RETAIN_SIZE = action -> action.is(PRESERVE, SIZE);

    /**
     * Optimizer for count operations!
     *
     * @param pipeline
     * @param fallbackSupplier
     * @return the number of rows
     */
    private long countHelper(Pipeline pipeline, LongSupplier fallbackSupplier) {
        requireNonNull(pipeline);
        requireNonNull(fallbackSupplier);
        if (pipeline.stream().allMatch(CHECK_RETAIN_SIZE)) {
            final String sql = "select count(*) from " + manager.sqlTableReference();
            return manager.synchronousStreamOf(sql, Collections.emptyList(), rs -> rs.getLong(1)).findAny().get();
        }
        return fallbackSupplier.getAsLong();
    }
    
}
