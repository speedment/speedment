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
package com.speedment.runtime.compute.internal.expression;

import static java.util.Objects.requireNonNull;

import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToFloat;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToLong;
import com.speedment.runtime.compute.ToShort;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.UnaryExpression;

import java.util.Objects;

/**
 * Utility class for creating expressions that give the positive square root of
 * the result from another expression.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class SqrtUtil {

    /**
     * Returns an expression that takes the result from the specified expression
     * and returns the positive square root of it.
     *
     * @param other  the expression to take the square root of
     * @param <T>    the input entity type
     * @return       expression for the square root
     */
    public static <T> ToDouble<T> sqrtByte(ToByte<T> other) {
        class ByteSqrt extends AbstractSqrt<T, ToByte<T>> {
            private ByteSqrt(ToByte<T> tToByte) {
                super(tToByte);
            }

            @Override
            public double applyAsDouble(T object) {
                return Math.sqrt(inner.applyAsByte(object));
            }
        }

        return new ByteSqrt(other);
    }

    /**
     * Returns an expression that takes the result from the specified expression
     * and returns the positive square root of it.
     *
     * @param other  the expression to take the square root of
     * @param <T>    the input entity type
     * @return       expression for the square root
     */
    public static <T> ToDouble<T> sqrtShort(ToShort<T> other) {
        class ShortSqrt extends AbstractSqrt<T, ToShort<T>> {
            private ShortSqrt(ToShort<T> tToShort) {
                super(tToShort);
            }

            @Override
            public double applyAsDouble(T object) {
                return Math.sqrt(inner.applyAsShort(object));
            }
        }

        return new ShortSqrt(other);
    }

    /**
     * Returns an expression that takes the result from the specified expression
     * and returns the positive square root of it.
     *
     * @param other  the expression to take the square root of
     * @param <T>    the input entity type
     * @return       expression for the square root
     */
    public static <T> ToDouble<T> sqrtInt(ToInt<T> other) {
        class IntSqrt extends AbstractSqrt<T, ToInt<T>> {
            private IntSqrt(ToInt<T> tToInt) {
                super(tToInt);
            }

            @Override
            public double applyAsDouble(T object) {
                return Math.sqrt(inner.applyAsInt(object));
            }
        }

        return new IntSqrt(other);
    }

    /**
     * Returns an expression that takes the result from the specified expression
     * and returns the positive square root of it.
     *
     * @param other  the expression to take the square root of
     * @param <T>    the input entity type
     * @return       expression for the square root
     */
    public static <T> ToDouble<T> sqrtLong(ToLong<T> other) {
        class LongSqrt extends AbstractSqrt<T, ToLong<T>> {
            private LongSqrt(ToLong<T> tToLong) {
                super(tToLong);
            }

            @Override
            public double applyAsDouble(T object) {
                return Math.sqrt(inner.applyAsLong(object));
            }
        }

        return new LongSqrt(other);
    }

    /**
     * Returns an expression that takes the result from the specified expression
     * and returns the positive square root of it.
     *
     * @param other  the expression to take the square root of
     * @param <T>    the input entity type
     * @return       expression for the square root
     */
    public static <T> ToDouble<T> sqrtFloat(ToFloat<T> other) {
        class FloatSqrt extends AbstractSqrt<T, ToFloat<T>> {
            private FloatSqrt(ToFloat<T> tToFloat) {
                super(tToFloat);
            }

            @Override
            public double applyAsDouble(T object) {
                return Math.sqrt(inner.applyAsFloat(object));
            }
        }

        return new FloatSqrt(other);
    }

    /**
     * Returns an expression that takes the result from the specified expression
     * and returns the positive square root of it.
     *
     * @param other  the expression to take the square root of
     * @param <T>    the input entity type
     * @return       expression for the square root
     */
    public static <T> ToDouble<T> sqrtDouble(ToDouble<T> other) {
        class DoubleSqrt extends AbstractSqrt<T, ToDouble<T>> {
            private DoubleSqrt(ToDouble<T> tToDouble) {
                super(tToDouble);
            }

            @Override
            public double applyAsDouble(T object) {
                return Math.sqrt(inner.applyAsDouble(object));
            }
        }

        return new DoubleSqrt(other);
    }

    /**
     * Abstract base implementation of a {@link UnaryExpression} for a
     * square root-operation.
     *
     * @param <T>      the input entity type
     * @param <INNER>  the inner expression type to take the square root of
     */
    abstract static class AbstractSqrt<T, INNER extends Expression<T>>
    implements UnaryExpression<T, INNER>, ToDouble<T> {

        final INNER inner;

        AbstractSqrt(INNER inner) {
            this.inner = requireNonNull(inner);
        }

        @Override
        public final INNER inner() {
            return inner;
        }

        @Override
        public final Operator operator() {
            return Operator.SQRT;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UnaryExpression)) return false;
            final UnaryExpression<?, ?> that = (UnaryExpression<?, ?>) o;
            return Objects.equals(inner(), that.inner())
                && Objects.equals(operator(), that.operator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner(), operator());
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private SqrtUtil() {}
}
