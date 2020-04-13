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

import com.speedment.runtime.field.predicate.CombinedPredicate;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;

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
abstract class AbstractPredicate<T> implements SpeedmentPredicate<T> {

    AbstractPredicate() {}

    @Override
    public SpeedmentPredicate<T> and(Predicate<? super T> other) {
        requireNonNull(other);
        return CombinedPredicate.and(this, other);
    }

    @Override
    public SpeedmentPredicate<T> or(Predicate<? super T> other) {
        requireNonNull(other);
        return CombinedPredicate.or(this, other);
    }
}