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
import com.speedment.runtime.compute.ToBoolean;
import com.speedment.runtime.compute.ToBooleanNullable;
import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToByteNullable;
import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.ToFloat;
import com.speedment.runtime.compute.ToFloatNullable;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToIntNullable;
import com.speedment.runtime.compute.ToLong;
import com.speedment.runtime.compute.ToLongNullable;
import com.speedment.runtime.compute.ToShort;
import com.speedment.runtime.compute.ToShortNullable;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.UnaryExpression;
import com.speedment.runtime.compute.internal.ToBooleanNullableImpl;
import com.speedment.runtime.compute.internal.ToByteNullableImpl;
import com.speedment.runtime.compute.internal.ToDoubleNullableImpl;
import com.speedment.runtime.compute.internal.ToFloatNullableImpl;
import com.speedment.runtime.compute.internal.ToIntNullableImpl;
import com.speedment.runtime.compute.internal.ToLongNullableImpl;
import com.speedment.runtime.compute.internal.ToShortNullableImpl;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Utility class used to construct expression that gives the negation of
 * another expression.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class NegateUtil {

    private NegateUtil() {}

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> negateByte(ToByte<T> expression) {
        class NegateByte extends AbstractNegate<T, ToByte<T>> implements ToByte<T> {
            private NegateByte(ToByte<T> inner) {
                super(inner);
            }

            @Override
            public byte applyAsByte(T object) {
                return (byte) -inner.applyAsByte(object);
            }
        }

        return new NegateByte(expression);
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToShort<T> negateShort(ToShort<T> expression) {
        class NegateShort extends AbstractNegate<T, ToShort<T>> implements ToShort<T> {
            private NegateShort(ToShort<T> inner) {
                super(inner);
            }

            @Override
            public short applyAsShort(T object) {
                return (short) -inner.applyAsShort(object);
            }
        }

        return new NegateShort(expression);
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToInt<T> negateInt(ToInt<T> expression) {
        class NegateInt extends AbstractNegate<T, ToInt<T>> implements ToInt<T> {
            private NegateInt(ToInt<T> inner) {
                super(inner);
            }

            @Override
            public int applyAsInt(T object) {
                return -inner.applyAsInt(object);
            }
        }

        return new NegateInt(expression);
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> negateLong(ToLong<T> expression) {
        class NegateLong extends AbstractNegate<T, ToLong<T>> implements ToLong<T> {
            private NegateLong(ToLong<T> inner) {
                super(inner);
            }

            @Override
            public long applyAsLong(T object) {
                return -inner.applyAsLong(object);
            }
        }

        return new NegateLong(expression);
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToFloat<T> negateFloat(ToFloat<T> expression) {
        class NegateFloat extends AbstractNegate<T, ToFloat<T>> implements ToFloat<T> {
            private NegateFloat(ToFloat<T> inner) {
                super(inner);
            }

            @Override
            public float applyAsFloat(T object) {
                return -inner.applyAsFloat(object);
            }
        }

        return new NegateFloat(expression);
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> negateDouble(ToDouble<T> expression) {
        class NegateDouble extends AbstractNegate<T, ToDouble<T>> implements ToDouble<T> {
            private NegateDouble(ToDouble<T> inner) {
                super(inner);
            }

            @Override
            public double applyAsDouble(T object) {
                return -inner.applyAsDouble(object);
            }
        }

        return new NegateDouble(expression);
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToBigDecimal<T> negateBigDecimal(ToBigDecimal<T> expression) {
        class NegateBigDecimal extends AbstractNegate<T, ToBigDecimal<T>> implements ToBigDecimal<T> {
            private NegateBigDecimal(ToBigDecimal<T> inner) {
                super(inner);
            }

            @Override
            public BigDecimal apply(T object) {
                return inner.apply(object).negate();
            }
        }

        return new NegateBigDecimal(expression);
    }


    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToBoolean<T> negateBoolean(ToBoolean<T> expression) {
        class NegateBoolean extends AbstractNegate<T, ToBoolean<T>> implements ToBoolean<T> {
            private NegateBoolean(ToBoolean<T> inner) {
                super(inner);
            }

            @Override
            public boolean applyAsBoolean(T object) {
                return !inner.applyAsBoolean(object);
            }
        }

        return new NegateBoolean(expression);
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression. If the result of the original
     * expression is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByteNullable<T> negateByteOrNull(ToByteNullable<T> expression) {
        return new ToByteNullableImpl<>(
            negateByte(expression.orThrow()),
            expression.isNull()
        );
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression. If the result of the original
     * expression is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToShortNullable<T> negateShortOrNull(ToShortNullable<T> expression) {
        return new ToShortNullableImpl<>(
            negateShort(expression.orThrow()),
            expression.isNull()
        );
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression. If the result of the original
     * expression is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToIntNullable<T> negateIntOrNull(ToIntNullable<T> expression) {
        return new ToIntNullableImpl<>(
            negateInt(expression.orThrow()),
            expression.isNull()
        );
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression. If the result of the original
     * expression is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLongNullable<T> negateLongOrNull(ToLongNullable<T> expression) {
        return new ToLongNullableImpl<>(
            negateLong(expression.orThrow()),
            expression.isNull()
        );
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression. If the result of the original
     * expression is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToFloatNullable<T> negateFloatOrNull(ToFloatNullable<T> expression) {
        return new ToFloatNullableImpl<>(
            negateFloat(expression.orThrow()),
            expression.isNull()
        );
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression. If the result of the original
     * expression is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T> negateDoubleOrNull(ToDoubleNullable<T> expression) {
        return new ToDoubleNullableImpl<>(
            negateDouble(expression.orThrow()),
            expression.isNull()
        );
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression. If the result of the original
     * expression is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToBooleanNullable<T> negateBooleanOrNull(ToBooleanNullable<T> expression) {
        return new ToBooleanNullableImpl<>(
            negateBoolean(expression.orThrow()),
            expression.isNull()
        );
    }

    /**
     * The abstract base for a negate expression.
     *
     * @param <T>      the input type
     * @param <INNER>  the inner expression type
     */
    abstract static class AbstractNegate<T, INNER extends Expression<T>>
    implements UnaryExpression<T, INNER> {

        final INNER inner;

        AbstractNegate(INNER inner) {
            this.inner = requireNonNull(inner);
        }

        @Override
        public final INNER inner() {
            return inner;
        }

        @Override
        public final Operator operator() {
            return Operator.NEGATE;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            else if (!(o instanceof UnaryExpression)) return false;
            final UnaryExpression<?, ?> that = (UnaryExpression<?, ?>) o;
            return Objects.equals(inner(), that.inner())
                && operator() == that.operator();
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner(), operator());
        }
    }

}
