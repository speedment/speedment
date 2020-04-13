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

import com.speedment.runtime.compute.ToBigDecimal;
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
 * Utility class for creating expressions that gives the sign ({@code 1},
 * {@code -1} or {@code 0}) depending on if the result of another expression is
 * positive, negative or {@code 0}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class SignUtil {

    private static final byte NEGATIVE = -1;
    private static final byte POSITIVE = 1;
    private static final byte ZERO = 0;

    /**
     * Returns an expression that wraps another expression and returns
     * {@code -1} if its result is negative, {@code 1} if its result is positive
     * and {@code 0} if its result is equal to {@code 0}.
     *
     * @param expression  the expression to wrap
     * @param <T>  the input entity type
     * @return  sign of the result of the wrapped expression
     */
    public static <T> ToByte<T> signBigDecimal(ToBigDecimal<T> expression) {
        class BigDecimalSign extends AbstractSign<T, ToBigDecimal<T>> {
            private BigDecimalSign(ToBigDecimal<T> tToBigDecimal) {
                super(tToBigDecimal);
            }

            @Override
            public byte applyAsByte(T object) {
                return (byte) inner.apply(object).signum();
            }
        }

        return new BigDecimalSign(expression);
    }

    /**
     * Returns an expression that wraps another expression and returns
     * {@code -1} if its result is negative, {@code 1} if its result is positive
     * and {@code 0} if its result is equal to {@code 0}.
     *
     * @param expression  the expression to wrap
     * @param <T>  the input entity type
     * @return  sign of the result of the wrapped expression
     */
    public static <T> ToByte<T> signDouble(ToDouble<T> expression) {
        class DoubleSign extends AbstractSign<T, ToDouble<T>> {
            private DoubleSign(ToDouble<T> tToDouble) {
                super(tToDouble);
            }

            @Override
            public byte applyAsByte(T object) {
                final double value = inner.applyAsDouble(object);
                if (value < 0) {
                    return NEGATIVE;
                } else {
                    return value > 0 ? POSITIVE : ZERO;
                }
            }
        }

        return new DoubleSign(expression);
    }

    /**
     * Returns an expression that wraps another expression and returns
     * {@code -1} if its result is negative, {@code 1} if its result is positive
     * and {@code 0} if its result is equal to {@code 0}.
     *
     * @param expression  the expression to wrap
     * @param <T>  the input entity type
     * @return  sign of the result of the wrapped expression
     */
    public static <T> ToByte<T> signFloat(ToFloat<T> expression) {
        class FloatSign extends AbstractSign<T, ToFloat<T>> {
            private FloatSign(ToFloat<T> tToFloat) {
                super(tToFloat);
            }

            @Override
            public byte applyAsByte(T object) {
                final float value = inner.applyAsFloat(object);
                if (value < 0) {
                    return NEGATIVE;
                } else {
                    return value > 0 ? POSITIVE : ZERO;
                }
            }
        }

        return new FloatSign(expression);
    }

    /**
     * Returns an expression that wraps another expression and returns
     * {@code -1} if its result is negative, {@code 1} if its result is positive
     * and {@code 0} if its result is equal to {@code 0}.
     *
     * @param expression  the expression to wrap
     * @param <T>  the input entity type
     * @return  sign of the result of the wrapped expression
     */
    public static <T> ToByte<T> signLong(ToLong<T> expression) {
        class LongSign extends AbstractSign<T, ToLong<T>> {
            private LongSign(ToLong<T> tToLong) {
                super(tToLong);
            }

            @Override
            public byte applyAsByte(T object) {
                final long value = inner.applyAsLong(object);
                if (value < 0) {
                    return NEGATIVE;
                } else {
                    return value > 0 ? POSITIVE : ZERO;
                }
            }
        }

        return new LongSign(expression);
    }

    /**
     * Returns an expression that wraps another expression and returns
     * {@code -1} if its result is negative, {@code 1} if its result is positive
     * and {@code 0} if its result is equal to {@code 0}.
     *
     * @param expression  the expression to wrap
     * @param <T>  the input entity type
     * @return  sign of the result of the wrapped expression
     */
    public static <T> ToByte<T> signInt(ToInt<T> expression) {
        class IntSign extends AbstractSign<T, ToInt<T>> {
            private IntSign(ToInt<T> tToInt) {
                super(tToInt);
            }

            @Override
            public byte applyAsByte(T object) {
                final int value = inner.applyAsInt(object);
                if (value < 0) {
                    return NEGATIVE;
                } else {
                    return value > 0 ? POSITIVE : ZERO;
                }
            }
        }

        return new IntSign(expression);
    }

    /**
     * Returns an expression that wraps another expression and returns
     * {@code -1} if its result is negative, {@code 1} if its result is positive
     * and {@code 0} if its result is equal to {@code 0}.
     *
     * @param expression  the expression to wrap
     * @param <T>  the input entity type
     * @return  sign of the result of the wrapped expression
     */
    public static <T> ToByte<T> signShort(ToShort<T> expression) {
        class ShortSign extends AbstractSign<T, ToShort<T>> {
            private ShortSign(ToShort<T> tToShort) {
                super(tToShort);
            }

            @Override
            public byte applyAsByte(T object) {
                final short value = inner.applyAsShort(object);
                if (value < 0) {
                    return NEGATIVE;
                } else {
                    return value > 0 ? POSITIVE : ZERO;
                }
            }
        }

        return new ShortSign(expression);
    }

    /**
     * Returns an expression that wraps another expression and returns
     * {@code -1} if its result is negative, {@code 1} if its result is positive
     * and {@code 0} if its result is equal to {@code 0}.
     *
     * @param expression  the expression to wrap
     * @param <T>  the input entity type
     * @return  sign of the result of the wrapped expression
     */
    public static <T> ToByte<T> signByte(ToByte<T> expression) {
        class ByteSign extends AbstractSign<T, ToByte<T>> {
            private ByteSign(ToByte<T> tToByte) {
                super(tToByte);
            }

            @Override
            public byte applyAsByte(T object) {
                final byte value = inner.applyAsByte(object);
                if (value < 0) {
                    return NEGATIVE;
                } else {
                    return value > 0 ? POSITIVE : ZERO;
                }
            }
        }

        return new ByteSign(expression);
    }

    /**
     * Internal base implementation for a sign-operation.
     *
     * @param <T>      the input entity type
     * @param <INNER>  the inner expression type
     */
    abstract static class AbstractSign<T, INNER extends Expression<T>>
    implements UnaryExpression<T, INNER>, ToByte<T> {
        final INNER inner;

        AbstractSign(INNER inner) {
            this.inner = requireNonNull(inner);
        }

        @Override
        public final INNER inner() {
            return inner;
        }

        @Override
        public final Operator operator() {
            return Operator.SIGN;
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
    private SignUtil() {}
}
