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

import com.speedment.orm.config.model.Column;
import com.speedment.orm.core.Buildable;
import com.speedment.orm.field.CombinedBasePredicate.AndCombinedBasePredicate;
import com.speedment.orm.field.PredicateBuilder;
import com.speedment.util.Cast;
import com.speedment.util.stream.builder.action.Action;
import com.speedment.util.stream.builder.action.reference.FilterAction;
import com.speedment.util.stream.builder.pipeline.DoublePipeline;
import com.speedment.util.stream.builder.pipeline.IntPipeline;
import com.speedment.util.stream.builder.pipeline.LongPipeline;
import com.speedment.util.stream.builder.pipeline.Pipeline;
import com.speedment.util.stream.builder.pipeline.ReferencePipeline;
import com.speedment.util.stream.builder.streamterminator.StreamTerminator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
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
        final List<PredicateBuilder> andPredicateBuilders = new ArrayList<>();

        for (Action<?, ?> action : initialPipeline.stream().collect(toList())) {
            final Optional<FilterAction> oFilterAction = Cast.cast(action, FilterAction.class);
            if (oFilterAction.isPresent()) {
                final List<PredicateBuilder> newAndPredicateBuilders = andPredicateBuilders(oFilterAction.get());
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

    private List<PredicateBuilder> andPredicateBuilders(FilterAction action) {
        final List<PredicateBuilder> andPredicateBuilders = new ArrayList<>();
        final Predicate<ENTITY> predicate = action.getPredicate();
        final Optional<PredicateBuilder> oPredicateBuilder = Cast.cast(predicate, PredicateBuilder.class);
        if (oPredicateBuilder.isPresent()) {
            andPredicateBuilders.add(oPredicateBuilder.get()); // Just a top level predicate builder
        } else {
            final Optional<AndCombinedBasePredicate> oAndCombinedBasePredicate = Cast.cast(predicate, AndCombinedBasePredicate.class);
            if (oAndCombinedBasePredicate.isPresent()) {
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
    public long count(DoublePipeline pipeline
    ) {
        return StreamTerminator.super.count(pipeline); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> long count(IntPipeline pipeline
    ) {
        return StreamTerminator.super.count(pipeline); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long count(LongPipeline pipeline
    ) {
        return StreamTerminator.super.count(pipeline); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> long count(ReferencePipeline<T> pipeline
    ) {
        return StreamTerminator.super.count(pipeline); //To change body of generated methods, choose Tools | Templates.
    }

    private long count(Pipeline pipeline) {
        return 1;
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
