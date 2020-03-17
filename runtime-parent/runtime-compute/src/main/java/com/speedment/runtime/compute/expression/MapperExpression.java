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
 * Specialized expression that takes the result of an inner expression and
 * applies a mapping function to it.
 * <p>
 * Equality is determined by looking at {@link #inner()},
 * {@link #mapper()} and {@link #mapperType()}.
 *
 * @param <T>       the input entity type
 * @param <INNER>   the type of the inner expression
 * @param <MAPPER>  the functional interface used to map from the type returned
 *                  by the inner expression and the resulting type of this
 *                  expression
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface MapperExpression<T, INNER extends Expression<T>, MAPPER>
extends Expression<T> {

    /**
     * Returns the inner expression used in this.
     *
     * @return  the inner expression
     */
    INNER inner();

    /**
     * Returns the mapping operation applied to the result from the inner
     * operation.
     *
     * @return  the mapping operation
     */
    MAPPER mapper();

    /**
     * Returns the type of mapper that the {@link #mapper()}-method returns
     * an instance of.
     *
     * @return  the type of mapper
     */
    MapperType mapperType();

    /**
     * Enumeration of all possible mapping types. Every mapping type has a
     * corresponding functional interface, and it should be safe to assume the
     * {@link #mapper()}-method to return the correct one.
     */
    enum MapperType {
        /**
         * Implemented as a {@link com.speedment.common.function.BooleanUnaryOperator}.
         */
        BOOLEAN_TO_BOOLEAN,

        /**
         * Implemented as a {@link com.speedment.common.function.BooleanToDoubleFunction}.
         */
        BOOLEAN_TO_DOUBLE,

        /**
         * Implemented as a {@link com.speedment.common.function.CharUnaryOperator}.
         */
        CHAR_TO_CHAR,

        /**
         * Implemented as a {@link com.speedment.common.function.ByteUnaryOperator}.
         */
        BYTE_TO_BYTE,

        /**
         * Implemented as a {@link com.speedment.common.function.ByteToDoubleFunction}.
         */
        BYTE_TO_DOUBLE,

        /**
         * Implemented as a {@link com.speedment.common.function.ShortUnaryOperator}.
         */
        SHORT_TO_SHORT,

        /**
         * Implemented as a {@link com.speedment.common.function.ShortToDoubleFunction}.
         */
        SHORT_TO_DOUBLE,

        /**
         * Implemented as a {@link java.util.function.IntUnaryOperator}.
         */
        INT_TO_INT,

        /**
         * Implemented as a {@link java.util.function.IntToDoubleFunction}.
         */
        INT_TO_DOUBLE,

        /**
         * Implemented as a {@link java.util.function.LongUnaryOperator}.
         */
        LONG_TO_LONG,

        /**
         * Implemented as a {@link java.util.function.LongToDoubleFunction}.
         */
        LONG_TO_DOUBLE,

        /**
         * Implemented as a {@link com.speedment.common.function.FloatUnaryOperator}.
         */
        FLOAT_TO_FLOAT,

        /**
         * Implemented as a {@link com.speedment.common.function.FloatToDoubleFunction}.
         */
        FLOAT_TO_DOUBLE,

        /**
         * Implemented as a {@link java.util.function.DoubleUnaryOperator}.
         */
        DOUBLE_TO_DOUBLE,

        /**
         * Implemented as a {@link java.util.function.UnaryOperator}.
         */
        ENUM_TO_ENUM,

        /**
         * Implemented as a {@link java.util.function.UnaryOperator}.
         */
        STRING_TO_STRING,

        /**
         * Implemented as a {@link java.util.function.UnaryOperator}.
         */
        BIG_DECIMAL_TO_DOUBLE,

        /**
         * Implemented as a {@link java.util.function.ToDoubleFunction}.
         */
        BIG_DECIMAL_TO_BIG_DECIMAL
    }
}