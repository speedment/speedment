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
package com.speedment.orm.core.manager.sql;

import com.speedment.orm.core.Buildable;
import com.speedment.orm.field.CombinedBasePredicate.AndCombinedBasePredicate;
import com.speedment.orm.field.PredicateBuilder;
import com.speedment.util.Cast;
import com.speedment.util.stream.builder.action.Action;
import static com.speedment.util.stream.builder.action.Property.SIZE;
import static com.speedment.util.stream.builder.action.Verb.PRESERVE;
import com.speedment.util.stream.builder.action.reference.FilterAction;
import com.speedment.util.stream.builder.pipeline.DoublePipeline;
import com.speedment.util.stream.builder.pipeline.IntPipeline;
import com.speedment.util.stream.builder.pipeline.LongPipeline;
import com.speedment.util.stream.builder.pipeline.Pipeline;
import com.speedment.util.stream.builder.pipeline.ReferencePipeline;
import com.speedment.util.stream.builder.streamterminator.StreamTerminator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;
import java.util.function.Predicate;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author pemi
 * @param <PK>
 * @param <ENTITY>
 * @param <BUILDER>
 */
public class SqlStreamTerminator<PK, ENTITY, BUILDER extends Buildable<ENTITY>> implements StreamTerminator {

    private final AbstractSqlManager<PK, ENTITY, BUILDER> manager;
    //private final Table table;

    public SqlStreamTerminator(/*Table table*/AbstractSqlManager<PK, ENTITY, BUILDER> manager) {
        this.manager = manager;
        //this.table = table;
    }

    @Override
    public <T extends Pipeline> T optimize(T initialPipeline) {
        final List<PredicateBuilder<?>> andPredicateBuilders = new ArrayList<>();

        for (Action<?, ?> action : initialPipeline.stream().collect(toList())) {
            @SuppressWarnings("rawtypes")
            final Optional<FilterAction> oFilterAction = Cast.cast(action, FilterAction.class);
            if (oFilterAction.isPresent()) {
                @SuppressWarnings("unchecked")
                final List<PredicateBuilder<?>> newAndPredicateBuilders = andPredicateBuilders(oFilterAction.get());
                andPredicateBuilders.addAll(newAndPredicateBuilders);
            } else {
                break; // We can only do initial consecutive FilterAction(s)
            }
        }

        if (!andPredicateBuilders.isEmpty()) {
            initialPipeline.setInitialSupplier(() -> manager.streamOf(andPredicateBuilders));
        }
        return initialPipeline;
    }

    private List<PredicateBuilder<?>> andPredicateBuilders(FilterAction<ENTITY> action) {
        final List<PredicateBuilder<?>> andPredicateBuilders = new ArrayList<>();
        final Predicate<? super ENTITY> predicate = action.getPredicate();
        @SuppressWarnings("rawtypes")
        final Optional<PredicateBuilder> oPredicateBuilder = Cast.cast(predicate, PredicateBuilder.class);
        if (oPredicateBuilder.isPresent()) {
            andPredicateBuilders.add(oPredicateBuilder.get()); // Just a top level predicate builder
        } else {
            @SuppressWarnings("rawtypes")
            final Optional<AndCombinedBasePredicate> oAndCombinedBasePredicate = Cast.cast(predicate, AndCombinedBasePredicate.class);
            if (oAndCombinedBasePredicate.isPresent()) {
                @SuppressWarnings("unchecked")
                final AndCombinedBasePredicate<ENTITY> andCombinedBasePredicate = (AndCombinedBasePredicate<ENTITY>) oAndCombinedBasePredicate.get();
                andCombinedBasePredicate.stream()
                    .map(p -> (Cast.cast(p, PredicateBuilder.class)))
                    .filter(p -> p.isPresent())
                    .map(Optional::get)
                    .forEachOrdered(andPredicateBuilders::add);
            }
        }
        return andPredicateBuilders;
    }

    @Override
    public long count(DoublePipeline pipeline) {
        return countHelper(pipeline, () -> StreamTerminator.super.count(pipeline));
    }

    @Override
    public <T> long count(IntPipeline pipeline) {
        return countHelper(pipeline, () -> StreamTerminator.super.count(pipeline));
    }

    @Override
    public long count(LongPipeline pipeline) {
        return countHelper(pipeline, () -> StreamTerminator.super.count(pipeline));
    }

    @Override
    public <T> long count(ReferencePipeline<T> pipeline) {
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
        if (pipeline.stream().allMatch(CHECK_RETAIN_SIZE)) {
            final String sql = "select count(*) from " + manager.sqlTableReference();
            return manager.streamOf(sql, Collections.emptyList(), rs -> rs.getLong(1)).findAny().get();
        }
        return fallbackSupplier.getAsLong();
    }
//
//    private Supplier<Stream<Entity>> supplier(Collection<Field> fields) {
//        final Dbms dbms = table.ancestor(Dbms.class).get();
//        final DbmsHandler dbmsHandler = Platform.get().get(DbmsHandlerComponent.class).get(dbms);
//        return () -> {
//            return dbmsHandler.executeQuery(null, null, null);
//        };
//    }

}
