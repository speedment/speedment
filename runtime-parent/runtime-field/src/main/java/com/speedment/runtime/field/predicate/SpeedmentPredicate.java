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

import com.speedment.runtime.compute.ToBoolean;
import com.speedment.runtime.field.internal.predicate.ComposedPredicateImpl;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Common interface for all metadata-rich predicates in the Speedment library.
 * Speedment predicates typically operates on an entity from a manager generated
 * by Speedment.
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public interface SpeedmentPredicate<ENTITY> extends ToBoolean<ENTITY> {

    @Override
    default <T> ComposedPredicate<T, ENTITY>
    compose(Function<? super T, ? extends ENTITY> before) {
        return new ComposedPredicateImpl<>(before, this);
    }

    @Override
    default SpeedmentPredicate<ENTITY> negate() {
        return t -> !test(t);
    }

    @Override
    default SpeedmentPredicate<ENTITY> and(Predicate<? super ENTITY> other) {
        return CombinedPredicate.and(this, other);
    }

    @Override
    default SpeedmentPredicate<ENTITY> or(Predicate<? super ENTITY> other) {
        return CombinedPredicate.or(this, other);
    }
}