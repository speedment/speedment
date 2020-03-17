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
package com.speedment.runtime.compute.expression.predicate;

import com.speedment.runtime.compute.trait.ToNullable;

import java.util.function.Predicate;

/**
 * Specialized predicate that holds additional metadata about the condition that
 * can be used to optimize the expression.
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public interface NullPredicate<T, R>
extends Predicate<T> {

    /**
     * If this predicate represents a simple {@code a == null} or
     * {@code a != null} predicate on the mapped value, then this method may
     * choose to return a special value so that the predicate may be
     * short-circuited. The method may always return
     * {@link NullPredicateType#OTHER}, in which case no such optimization
     * can be done.
     *
     * @return  the type of predicate this represents
     */
    NullPredicateType nullPredicateType();

    /**
     * The expression that is invoked in an incoming entity to get the value
     * that the predicate should test.
     *
     * @return  the expression
     */
    ToNullable<T, R, ?> expression();

}
