/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.compute.expression.predicate;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Specialized {@link Predicate} that consists of an {@link #innerMapper()} that
 * returns an instance that is then passed to {@link #innerPredicate()} to
 * determine if the element passes the predicate or not.
 *
 * @param <T> initial type
 * @param <A> intermediate type
 * 
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ComposedPredicate<T, A> extends Predicate<T> {

    /**
     * The mapper to apply to an incomming value to get a value that can be
     * passed to the {@link #innerPredicate() inner predicate}.
     *
     * @return  the inner mapper
     */
    Function<T, A> innerMapper();

    /**
     * The predicate to test on the mapped value.
     *
     * @return  the inner predicate
     */
    Predicate<A> innerPredicate();

    @Override
    default boolean test(T t) {
        return innerPredicate().test(innerMapper().apply(t));
    }
}