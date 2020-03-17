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
 * An {@link Expression} that has two operands, one is another
 * {@link Expression} and the other is a primitive {@code int}.
 * <p>
 * Equality is determined by looking at {@link #first()},
 * {@link #second()} and {@link #operator()}.
 *
 * @param <T>      the input entity type
 * @param <FIRST>  the type of the first operand, an expression
 * @param <V>      the type of the second operand, a constant value
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface BinaryObjExpression<T, FIRST extends Expression<T>, V>
extends Expression<T> {

    /**
     * Returns the first operand, an inner expression.
     *
     * @return  the first operand
     */
    FIRST first();

    /**
     * Returns the second operand, a constant value.
     *
     * @return  the second operand
     */
    V second();

    /**
     * Returns the binary operator that this expression represents.
     *
     * @return  the operator
     */
    Operator operator();

    /**
     * Operator types that could be returned by {@link #operator()}.
     */
    enum Operator {
        /**
         * The result of the first operand raised to the power of the second.
         */
        POW,

        /**
         * The result of the first operand added to the second (addition).
         */
        PLUS,

        /**
         * The result of the first operand minus the second (subtraction).
         */
        MINUS,

        /**
         * The result of the first operand multiplied by the second
         * (multiplication).
         */
        MULTIPLY,

        /**
         * The result of the first operand divided by the second (division).
         */
        DIVIDE
    }
}