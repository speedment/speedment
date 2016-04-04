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
package com.speedment.internal.core.stream.builder.streamterminator;

import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.internal.core.field.predicate.AbstractCombinedBasePredicate;
import com.speedment.internal.core.stream.builder.action.reference.FilterAction;
import com.speedment.internal.util.Cast;
import com.speedment.stream.Pipeline;
import com.speedment.stream.action.Action;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Predicate;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author pemi
 */
public class StreamTerminatorUtil {

    public static <T extends Pipeline, ENTITY> List<SpeedmentPredicate<ENTITY, ?, ?>> topLevelAndPredicates(T initialPipeline) {
        final List<SpeedmentPredicate<ENTITY, ?, ?>> andPredicateBuilders = new ArrayList<>();

        for (final Action<?, ?> action : initialPipeline.stream().collect(toList())) {
            @SuppressWarnings("rawtypes")
            final Optional<FilterAction> oFilterAction = Cast.cast(action, FilterAction.class);
            if (oFilterAction.isPresent()) {
                @SuppressWarnings("unchecked")
                final List<SpeedmentPredicate<ENTITY, ?, ?>> newAndPredicates = andPredicates(oFilterAction.get());
                andPredicateBuilders.addAll(newAndPredicates);
            } else {
                break; // We can only do initial consecutive FilterAction(s)
            }
        }
        return andPredicateBuilders;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <ENTITY> List<SpeedmentPredicate<?, ?, ?>> andPredicates(FilterAction<ENTITY> action) {
        requireNonNull(action);
        final List<SpeedmentPredicate<?, ?, ?>> andPredicateBuilders = new ArrayList<>();
        final Predicate<? super ENTITY> predicate = action.getPredicate();

        final Optional<SpeedmentPredicate> oPredicateBuilder = Cast.cast(predicate, SpeedmentPredicate.class);
        if (oPredicateBuilder.isPresent()) {
            andPredicateBuilders.add(oPredicateBuilder.get()); // Just a top level predicate builder
        } else {

            final Optional<AbstractCombinedBasePredicate.AndCombinedBasePredicate> oAndCombinedBasePredicate = Cast.cast(predicate, AbstractCombinedBasePredicate.AndCombinedBasePredicate.class);
            if (oAndCombinedBasePredicate.isPresent()) {

                final AbstractCombinedBasePredicate.AndCombinedBasePredicate<ENTITY> andCombinedBasePredicate = (AbstractCombinedBasePredicate.AndCombinedBasePredicate<ENTITY>) oAndCombinedBasePredicate.get();
                andCombinedBasePredicate.stream()
                    .map(p -> Cast.cast(p, SpeedmentPredicate.class))
                    .filter(p -> p.isPresent())
                    .map(Optional::get)
                    .forEachOrdered(andPredicateBuilders::add);
            }
        }
        return andPredicateBuilders;
    }

    private StreamTerminatorUtil() {
    }

}
