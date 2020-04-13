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
package com.speedment.runtime.field.predicate;

import com.speedment.runtime.field.internal.predicate.AbstractCombinedPredicate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Aggregation of a number of {@link Predicate Predicates} of the same type
 * (e.g. AND or OR) that can be applied in combination.
 *
 * @param <ENTITY> the entity type
 *
 * @author Per Minborg
 * @author Emil Forslund
 */
public interface CombinedPredicate<ENTITY> extends SpeedmentPredicate<ENTITY> {

    /**
     * This enum list all the different types of combinations
     */
    enum Type {
        AND, OR
    }

    /**
     * Creates and returns a {link Stream} of all predicates that this
     * CombinedPredicate holds.
     *
     * @return a {link Stream} of all predicates that this CombinedPredicate
     * holds
     */
    Stream<Predicate<? super ENTITY>> stream();

    /**
     * Returns the number of predicates that this CombinedBasePredicate holds
     *
     * @return the number of predicates that this CombinedBasePredicate holds
     */
    int size();

    /**
     * Returns the {@link Type} of this CombinedBasePredicate
     *
     * @return the {@link Type} of this CombinedBasePredicate
     */
    Type getType();

    @Override
    CombinedPredicate<ENTITY> and(Predicate<? super ENTITY> other);

    @Override
    CombinedPredicate<ENTITY> or(Predicate<? super ENTITY> other);
    
    @Override
    CombinedPredicate<ENTITY> negate();

    /**
     * Creates and returns a new CombinedPredicate that is the logical AND
     * combination of the given predicates.
     *
     * @param <ENTITY> entity type
     * @param first the first predicate used in the AND operation
     * @param second the first predicate used in the AND operation
     * @return a new CombinedPredicate that is the logical AND combination of
     * the given predicates
     */
    static <ENTITY> CombinedPredicate<ENTITY> and(Predicate<ENTITY> first, Predicate<? super ENTITY> second) {
        @SuppressWarnings("unchecked")
        final Predicate<ENTITY> secondCasted = (Predicate<ENTITY>) second;
        return new AbstractCombinedPredicate.AndCombinedBasePredicateImpl<>(
            Arrays.asList(first, secondCasted)
        );
    }

    /**
     * Creates and returns a new CombinedPredicate that is the logical AND
     * combination of the given predicates.
     *
     * @param <ENTITY> entity type
     * @param predicates the predicates that make up the AND operation
     * @return a new CombinedPredicate that is the logical AND combination of
     * the given predicates
     */
    static <ENTITY> CombinedPredicate<ENTITY> and(List<Predicate<? super ENTITY>> predicates) {
        return new AbstractCombinedPredicate.AndCombinedBasePredicateImpl<>(predicates);
    }

    /**
     * Creates and returns a new CombinedPredicate that is the logical OR
     * combination of the given predicates.
     *
     * @param <ENTITY> entity type
     * @param first the first predicate used in the OR operation
     * @param second the first predicate used in the OR operation
     * @return a new CombinedPredicate that is the logical OR combination of the
     * given predicates
     */
    static <ENTITY> CombinedPredicate<ENTITY> or(Predicate<ENTITY> first, Predicate<? super ENTITY> second) {
        @SuppressWarnings("unchecked")
        final Predicate<ENTITY> secondCasted = (Predicate<ENTITY>) second;
        return new AbstractCombinedPredicate.OrCombinedBasePredicateImpl<>(
            Arrays.asList(first, secondCasted)
        );
    }

    /**
     * Creates or returns a new CombinedPredicate that is the logical OR
     * combination of the given predicates.
     *
     * @param <ENTITY> entity type
     * @param predicates the predicates that make up the OR operation
     * @return a new CombinedPredicate that is the logical OR combination of
     * the given predicates
     */
    static <ENTITY> CombinedPredicate<ENTITY> or(List<Predicate<? super ENTITY>> predicates) {
        return new AbstractCombinedPredicate.OrCombinedBasePredicateImpl<>(predicates);
    }
}
