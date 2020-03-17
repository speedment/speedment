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
package com.speedment.runtime.field.internal.predicate;

import com.speedment.runtime.field.predicate.ComposedPredicate;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link ComposedPredicate}
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public class ComposedPredicateImpl<T, A> implements ComposedPredicate<T, A> {

    private final Function<? super T, ? extends A> firstStep;
    private final SpeedmentPredicate<A> secondStep;

    public ComposedPredicateImpl(Function<? super T, ? extends A> firstStep,
                                 SpeedmentPredicate<A> secondStep) {
        this.firstStep  = requireNonNull(firstStep);
        this.secondStep = requireNonNull(secondStep);
    }

    @Override
    public Function<T, A> firstStep() {
        @SuppressWarnings("unchecked")
        final Function<T, A> function = (Function<T, A>) firstStep;
        return function;
    }

    @Override
    public SpeedmentPredicate<A> secondStep() {
        return secondStep;
    }

    @Override
    public boolean applyAsBoolean(T object) {
        return secondStep.applyAsBoolean(firstStep.apply(object));
    }
}