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
import com.speedment.runtime.compute.ToLong;

/**
 * Trait that describes an expression that has several
 * {@link #divide(int)}-methods for generating new expressions for the division
 * of this value with a certain divisor.
 *
 * @param <T>  the input type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasDivide<T> {

    /**
     * Creates and returns an expression that returns the quotient of
     * the result from the current expression and the divisor. For an example, if the result of
     * the current expression was {@code 9} and the divisor was set to {@code 3},
     * then the result of the returned expression would be {@code 3}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param divisor  the divisor used for the division
     * @return the new expression
     */
    ToDouble<T> divide(int divisor);

    /**
     * Creates and returns an expression that returns the quotient of the result
     * from the current expression and the divisor. For an example, if the
     * result of the current expression was {@code 9} and the divisor was set to
     * {@code 3}, then the result of the returned expression would be {@code
     * 3}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param divisor the divisor used for the division
     * @return the new expression
     */
    @SuppressWarnings("overloads")
    ToDouble<T> divide(ToInt<T> divisor);

    /**
     * Creates and returns an expression that returns the quotient of the result
     * from the current expression and the divisor. For an example, if the
     * result of the current expression was {@code 9} and the divisor was set to
     * {@code 3}, then the result of the returned expression would be {@code
     * 3}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param divisor the divisor used for the division
     * @return the new expression
     */
    ToDouble<T> divide(long divisor);

    /**
     * Creates and returns an expression that returns the quotient of the result
     * from the current expression and the divisor. For an example, if the
     * result of the current expression was {@code 9} and the divisor was set to
     * {@code 3}, then the result of the returned expression would be {@code
     * 3}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param divisor the divisor used for the division
     * @return the new expression
     */
    @SuppressWarnings("overloads")
    ToDouble<T> divide(ToLong<T> divisor);

    /**
     * Creates and returns an expression that returns the quotient of the result
     * from the current expression and the divisor. For an example, if the
     * result of the current expression was {@code 9} and the divisor was set to
     * {@code 3}, then the result of the returned expression would be {@code
     * 3}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param divisor the divisor used for the division
     * @return the new expression
     */
    ToDouble<T> divide(double divisor);

    /**
     * Creates and returns an expression that returns the quotient of the result
     * from the current expression and the divisor. For an example, if the
     * result of the current expression was {@code 9} and the divisor was set to
     * {@code 3}, then the result of the returned expression would be {@code
     * 3}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param divisor the divisor used for the division
     * @return the new expression
     */
    @SuppressWarnings("overloads")
    ToDouble<T> divide(ToDouble<T> divisor);

}