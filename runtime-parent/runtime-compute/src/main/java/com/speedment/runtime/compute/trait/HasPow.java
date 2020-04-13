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
package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToInt;

/**
 * Trait for expressions that has {@link #pow(int)} methods for getting the
 * result of the current expression raised to a particular power.
 *
 * @param <T> the input type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasPow<T> {

    /**
     * Creates and returns an expression that returns the exponential power of
     * the result from the current expression. For an example, if the result of
     * the current expression was {@code -3} and the power was set to {@code 2},
     * then the result of the returned expression would be {@code 9}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param power  the power to use as the exponent
     * @return       the new expression
     */
    ToDouble<T> pow(int power);

    /**
     * Creates and returns an expression that returns the exponential power of
     * the result from the current expression. For an example, if the result of
     * the current expression was {@code -3} and the power was set to {@code 2},
     * then the result of the returned expression would be {@code 9}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param power  the power to use as the exponent
     * @return       the new expression
     */
    ToDouble<T> pow(double power);

    /**
     * Creates and returns an expression that returns the exponential power of
     * the result from the current expression. For an example, if the result of
     * the current expression was {@code -3} and the power was set to {@code 2},
     * then the result of the returned expression would be {@code 9}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param power  the power to use as the exponent
     * @return       the new expression
     */
    @SuppressWarnings("overloads")
    ToDouble<T> pow(ToInt<T> power);

    /**
     * Creates and returns an expression that returns the exponential power of
     * the result from the current expression. For an example, if the result of
     * the current expression was {@code -3} and the power was set to {@code 2},
     * then the result of the returned expression would be {@code 9}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param power  the power to use as the exponent
     * @return       the new expression
     */
    @SuppressWarnings("overloads")
    ToDouble<T> pow(ToDouble<T> power);

}
