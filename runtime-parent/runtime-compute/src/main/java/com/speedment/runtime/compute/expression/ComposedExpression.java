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

import java.util.function.Function;

/**
 * Specialized {@link Expression} interface used when a {@link #firstStep()}
 * function is first applied to an incomming entity, before a
 * {@link #secondStep()} expression is applied to get the result. This is
 * typically used to implement operations like
 * {@link Function#compose(Function)}.
 *
 * @param <T> initial type
 * @param <A> intermediate type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ComposedExpression<T, A> extends Expression<T> {

    /**
     * The function that is applied to incoming entities to get the value that
     * is to be passed to {@link #secondStep()}.
     *
     * @return  the first step function
     */
    Function<T, A> firstStep();

    /**
     * The inner expression that is applied to the result of
     * {@link #firstStep()} to get the result of this full expression.
     *
     * @return  the second step expression
     */
    Expression<A> secondStep();

}
