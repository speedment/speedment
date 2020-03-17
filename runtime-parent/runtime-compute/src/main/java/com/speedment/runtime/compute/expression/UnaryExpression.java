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

/**
 * An {@link Expression} that has a single operand that implements
 * {@link Expression}.
 * <p>
 * Equality is determined by looking at {@link #inner()} and
 * {@link #operator()}.
 *
 * @param <T>      the input entity type
 * @param <INNER>  the type of the inner expression
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface UnaryExpression<T, INNER extends Expression<T>>
extends Expression<T> {

    /**
     * The inner expression that this wraps and applies a unary operator to.
     *
     * @return  the inner expression
     */
    INNER inner();

    /**
     * Returns the binary operator that this expression represents.
     *
     * @return  the operator
     */
    Operator operator();

    /**
     * The unary expression operator type.
     */
    enum Operator {

        /**
         * This expression is the absolute value of the result from the inner
         * expression. That is, the negation sign is ignored, returning only
         * positive values.
         */
        ABS,

        /**
         * This expression is the result of the inner expression casted to a
         * different type
         */
        CAST,

        /**
         * This expression is the negation of the inner expression. That is, the
         * negation sign is flipped.
         */
        NEGATE,

        /**
         * The result of this expression is {@code 0} if the result of the inner
         * expression is {@code 0}, {@code 1} of the inner returned something
         * positive and {@code -1} if the inner returned something negative.
         */
        SIGN,

        /**
         * The result of this expression is the positive square root of the
         * result of the inner expression.
         */
        SQRT
    }
}