/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.internal.stream.builder.streamterminator;

import com.speedment.runtime.internal.field.predicate.AbstractCombinedPredicate;
import com.speedment.runtime.internal.stream.builder.action.reference.FilterAction;
import com.speedment.runtime.internal.util.Cast;
import com.speedment.runtime.stream.Pipeline;
import com.speedment.runtime.stream.action.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import com.speedment.runtime.field.predicate.FieldPredicate;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class StreamTerminatorUtil {

    public static <T extends Pipeline, ENTITY> List<FieldPredicate<ENTITY>> topLevelAndPredicates(T initialPipeline) {
        final List<FieldPredicate<ENTITY>> andPredicateBuilders = new ArrayList<>();

        for (final Action<?, ?> action : initialPipeline.stream().collect(toList())) {
            @SuppressWarnings("rawtypes")
            final Optional<FilterAction> oFilterAction = Cast.cast(action, FilterAction.class);
            if (oFilterAction.isPresent()) {
                @SuppressWarnings("unchecked")
                final List<FieldPredicate<ENTITY>> newAndPredicates = andPredicates(oFilterAction.get());
                andPredicateBuilders.addAll(newAndPredicates);
            } else {
                break; // We can only do initial consecutive FilterAction(s)
            }
        }
        return andPredicateBuilders;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <ENTITY> List<FieldPredicate<?>> andPredicates(FilterAction<ENTITY> action) {
        requireNonNull(action);
        final List<FieldPredicate<?>> andPredicateBuilders = new ArrayList<>();
        final Predicate<? super ENTITY> predicate = action.getPredicate();

        final Optional<FieldPredicate> oPredicateBuilder = Cast.cast(predicate, FieldPredicate.class);
        if (oPredicateBuilder.isPresent()) {
            andPredicateBuilders.add(oPredicateBuilder.get()); // Just a top level predicate builder
        } else {

            final Optional<AbstractCombinedPredicate.AndCombinedBasePredicate> oAndCombinedBasePredicate = Cast.cast(predicate, AbstractCombinedPredicate.AndCombinedBasePredicate.class);
            if (oAndCombinedBasePredicate.isPresent()) {

                final AbstractCombinedPredicate.AndCombinedBasePredicate<ENTITY> andCombinedBasePredicate = (AbstractCombinedPredicate.AndCombinedBasePredicate<ENTITY>) oAndCombinedBasePredicate.get();
                andCombinedBasePredicate.stream()
                    .map(p -> Cast.cast(p, FieldPredicate.class))
                    .filter(p -> p.isPresent())
                    .map(Optional::get)
                    .forEachOrdered(andPredicateBuilders::add);
            }
        }
        return andPredicateBuilders;
    }

    private StreamTerminatorUtil() {}
}