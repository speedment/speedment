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
package com.speedment.runtime.internal.field.predicate;

import com.speedment.runtime.field.predicate.trait.HasNegated;
import com.speedment.runtime.internal.field.predicate.AbstractCombinedPredicate.AndCombinedBasePredicate;
import com.speedment.runtime.internal.field.predicate.AbstractCombinedPredicate.OrCombinedBasePredicate;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * This class represents a Predicate that is used to build up higher orders
 * of predicates.
 *
 * @param <T>  the type being evaluated
 * 
 * @author  Per Minborg
 * @since   2.1.0
 */
abstract class AbstractPredicate<T> implements HasNegated, Predicate<T> {

    private boolean negated;

    protected AbstractPredicate() {
        this.negated = false;
    }

    @Override
    public Predicate<T> and(Predicate<? super T> other) {
        requireNonNull(other);
        return new AndCombinedBasePredicate<>(this, other);
    }

    @Override
    public Predicate<T> or(Predicate<? super T> other) {
        requireNonNull(other);
        return new OrCombinedBasePredicate<>(this, other);
    }

    @Override
    public AbstractPredicate<T> negate() {
        negated = !negated;
        return this;
    }

    @Override
    public boolean isNegated() {
        return negated;
    }

    @Override
    public final boolean test(T instance) {
        return testWithoutNegation(instance) ^ negated;
    }
    
    /**
     * Tests this predicate without applying negation. If the predicate
     * has been negated, the result from this method will be negated
     * afterwards.
     * 
     * @param instance  the instance to test
     * @return          the result of the test (without negation)
     */
    protected abstract boolean testWithoutNegation(T instance);
}