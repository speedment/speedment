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
import com.speedment.runtime.compute.internal.ToByteNullableImpl;
import com.speedment.runtime.compute.internal.ToDoubleNullableImpl;
import com.speedment.runtime.compute.internal.ToFloatNullableImpl;
import com.speedment.runtime.compute.internal.ToIntNullableImpl;
import com.speedment.runtime.compute.internal.ToLongNullableImpl;
import com.speedment.runtime.compute.internal.ToShortNullableImpl;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Utility class used to construct expression that gives the absolute value of
 * another expression.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class AbsUtil {

    /**
     * Returns an expression that takes an expression and returns its absolute
     * (removing the negation sign if any).
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> absByte(ToByte<T> expression) {
        class AbsByte extends AbstractAbs<T, ToByte<T>> implements ToByte<T> {
            private AbsByte(ToByte<T> inner) {
                super(inner);
            }

            @Override
            public byte applyAsByte(T object) {
                final byte value = inner.applyAsByte(object);
                return value < 0 ? (byte) -value : value;
            }
        }

        return new AbsByte(expression);
    }

    /**
     * Returns an expression that takes an expression and returns its absolute
     * (removing the negation sign if any).
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToShort<T> absShort(ToShort<T> expression) {
        class AbsShort extends AbstractAbs<T, ToShort<T>> implements ToShort<T> {
            private AbsShort(ToShort<T> inner) {
                super(inner);
            }

            @Override
            public short applyAsShort(T object) {
                final short value = inner.applyAsShort(object);
                return value < 0 ? (short) -value : value;
            }
        }

        return new AbsShort(expression);
    }

    /**
     * Returns an expression that takes an expression and returns its absolute
     * (removing the negation sign if any).
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToInt<T> absInt(ToInt<T> expression) {
        class AbsInt extends AbstractAbs<T, ToInt<T>> implements ToInt<T> {
            private AbsInt(ToInt<T> inner) {
                super(inner);
            }

            @Override
            public int applyAsInt(T object) {
                final int value = inner.applyAsInt(object);
                return value < 0 ? -value : value;
            }
        }

        return new AbsInt(expression);
    }

    /**
     * Returns an expression that takes an expression and returns its absolute
     * (removing the negation sign if any).
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> absLong(ToLong<T> expression) {
        class AbsLong extends AbstractAbs<T, ToLong<T>> implements ToLong<T> {
            private AbsLong(ToLong<T> inner) {
                super(inner);
            }

            @Override
            public long applyAsLong(T object) {
                final long value = inner.applyAsLong(object);
                return value < 0 ? -value : value;
            }
        }

        return new AbsLong(expression);
    }

    /**
     * Returns an expression that takes an expression and returns its absolute
     * (removing the negation sign if any).
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToFloat<T> absFloat(ToFloat<T> expression) {
        class AbsFloat extends AbstractAbs<T, ToFloat<T>> implements ToFloat<T> {
            private AbsFloat(ToFloat<T> inner) {
                super(inner);
            }

            @Override
            public float applyAsFloat(T object) {
                final float value = inner.applyAsFloat(object);
                return value < 0 ? -value : value;
            }
        }

        return new AbsFloat(expression);
    }

    /**
     * Returns an expression that takes an expression and returns its absolute
     * (removing the negation sign if any).
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> absDouble(ToDouble<T> expression) {
        class AbsDouble extends AbstractAbs<T, ToDouble<T>> implements ToDouble<T> {
            private AbsDouble(ToDouble<T> inner) {
                super(inner);
            }

            @Override
            public double applyAsDouble(T object) {
                final double value = inner.applyAsDouble(object);
                return value < 0 ? -value : value;
            }
        }

        return new AbsDouble(expression);
    }

    /**
     * Returns an expression that takes an expression and returns its absolute
     * (removing the negation sign if any).
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToBigDecimal<T> absBigDecimal(ToBigDecimal<T> expression) {
        class AbsBigDecimal extends AbstractAbs<T, ToBigDecimal<T>> implements ToBigDecimal<T> {
            private AbsBigDecimal(ToBigDecimal<T> inner) {
                super(inner);
            }

            @Override
            public BigDecimal apply(T object) {
                return inner.apply(object).abs();
            }
        }

        return new AbsBigDecimal(expression);
    }

    /**
     * Returns an expression that takes an expression and returns its absolute
     * (removing the negation sign if any). If the result of the original
     * expression is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByteNullable<T> absByteOrNull(ToByteNullable<T> expression) {
        return new ToByteNullableImpl<>(
            absByte(expression.orThrow()),
            expression.isNull()
        );
    }

    /**
     * Returns an expression that takes an expression and returns its absolute
     * (removing the negation sign if any). If the result of the original
     * expression is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToShortNullable<T> absShortOrNull(ToShortNullable<T> expression) {
        return new ToShortNullableImpl<>(
            absShort(expression.orThrow()),
            expression.isNull()
        );
    }

    /**
     * Returns an expression that takes an expression and returns its absolute
     * (removing the negation sign if any). If the result of the original
     * expression is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToIntNullable<T> absIntOrNull(ToIntNullable<T> expression) {
        return new ToIntNullableImpl<>(
            absInt(expression.orThrow()),
            expression.isNull()
        );
    }

    /**
     * Returns an expression that takes an expression and returns its absolute
     * (removing the negation sign if any). If the result of the original
     * expression is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLongNullable<T> absLongOrNull(ToLongNullable<T> expression) {
        return new ToLongNullableImpl<>(
            absLong(expression.orThrow()),
            expression.isNull()
        );
    }

    /**
     * Returns an expression that takes an expression and returns its absolute
     * (removing the negation sign if any). If the result of the original
     * expression is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToFloatNullable<T> absFloatOrNull(ToFloatNullable<T> expression) {
        return new ToFloatNullableImpl<>(
            absFloat(expression.orThrow()),
            expression.isNull()
        );
    }

    /**
     * Returns an expression that takes an expression and returns its absolute
     * (removing the negation sign if any). If the result of the original
     * expression is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T> absDoubleOrNull(ToDoubleNullable<T> expression) {
        return new ToDoubleNullableImpl<>(
            absDouble(expression.orThrow()),
            expression.isNull()
        );
    }

    /**
     * The abstract base for an absolute expression.
     *
     * @param <T>      the input entity type
     * @param <INNER>  the inner expression type
     */
    abstract static class AbstractAbs<T, INNER extends Expression<T>>
    implements UnaryExpression<T, INNER> {

        final INNER inner;

        AbstractAbs(INNER inner) {
            this.inner = requireNonNull(inner);
        }

        @Override
        public final INNER inner() {
            return inner;
        }

        @Override
        public final Operator operator() {
            return Operator.ABS;
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

    /**
     * Utility classes should not be instantiated.
     */
    private AbsUtil() {}
}
