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
package com.speedment.runtime.compute.expression;

import java.util.function.Predicate;

/**
 * Specific type of {@link Expression} that has an {@link #inner() inner}
 * expression that is used for elements that does not pass the
 * {@link #isNullPredicate() isNull} predicate.
 * <p>
 * Equality is determined by looking at the {@link #inner()} and
 * {@link #isNullPredicate()}.
 *
 * @param <T>      the input entity type
 * @param <INNER>  type of the inner expression
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface NullableExpression<T, INNER extends Expression<T>>
extends Expression<T> {

    /**
     * The inner predicate that is used to determine the result after the
     * {@link #isNullPredicate()} has returned {@code false}. The inner expression
     * should have the same {@link Expression#expressionType() type} as this
     * one, except that it might not be nullable. It could also be nullable,
     * however.
     *
     * @return  the inner expression
     */
    INNER inner();

    /**
     * Returns the predicate used to evaluate if an incoming element will be
     * mapped to {@code null} in this expression, or if the {@link #inner()}
     * expression should be used.
     *
     * @return  predicate that gives {@code true} if an element should result in
     *          a {@code null} value in this expression, or {@code false} if the
     *          {@link #inner()} expression should be used to determine the
     *          result
     */
    Predicate<T> isNullPredicate();
}
