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

import com.speedment.runtime.compute.ToBigDecimalNullable;
import com.speedment.runtime.compute.ToBooleanNullable;
import com.speedment.runtime.compute.ToByteNullable;
import com.speedment.runtime.compute.ToCharNullable;
import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.ToEnumNullable;
import com.speedment.runtime.compute.ToFloatNullable;
import com.speedment.runtime.compute.ToIntNullable;
import com.speedment.runtime.compute.ToLongNullable;
import com.speedment.runtime.compute.ToShortNullable;
import com.speedment.runtime.compute.ToStringNullable;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.NonNullableExpression;
import com.speedment.runtime.compute.expression.orelse.OrElseThrowExpression;
import com.speedment.runtime.compute.expression.orelse.ToBigDecimalOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToBooleanOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToByteOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToCharOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToDoubleOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToEnumOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToFloatOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToIntOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToLongOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToShortOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToStringOrThrow;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Utility class used to create expressions that wrap a nullable expression and
 * handles any {@code null}-values by throwing an exception.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class OrElseThrowUtil {

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, throws an exception.
     *
     * @param expression  the nullable expression to wrap
     * @param <T>         the input entity type
     * @return            the non-nullable expression
     */
    public static <T> ToDoubleOrThrow<T>
    doubleOrElseThrow(ToDoubleNullable<T> expression) {
        return new ToDoubleOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToDoubleNullable.doubleOrElse(double)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToDoubleOrElseThrowImpl<T>
    extends AbstractNonNullable<T, ToDoubleNullable<T>>
    implements ToDoubleOrThrow<T> {
        private ToDoubleOrElseThrowImpl(ToDoubleNullable<T> inner) {
            super(inner);
        }

        @Override
        public double applyAsDouble(T object) {
            return inner.applyAsDouble(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, throws an exception.
     *
     * @param expression  the nullable expression to wrap
     * @param <T>         the input entity type
     * @return            the non-nullable expression
     */
    public static <T> ToFloatOrThrow<T>
    floatOrElseThrow(ToFloatNullable<T> expression) {
        return new ToFloatOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToFloatNullable.doubleOrElse(float)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToFloatOrElseThrowImpl<T>
        extends AbstractNonNullable<T, ToFloatNullable<T>>
        implements ToFloatOrThrow<T> {
        private ToFloatOrElseThrowImpl(ToFloatNullable<T> inner) {
            super(inner);
        }

        @Override
        public float applyAsFloat(T object) {
            return inner.applyAsFloat(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, throws an exception.
     *
     * @param expression  the nullable expression to wrap
     * @param <T>         the input entity type
     * @return            the non-nullable expression
     */
    public static <T> ToLongOrThrow<T>
    longOrElseThrow(ToLongNullable<T> expression) {
        return new ToLongOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToLongNullable.doubleOrElse(long)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToLongOrElseThrowImpl<T>
        extends AbstractNonNullable<T, ToLongNullable<T>>
        implements ToLongOrThrow<T> {
        private ToLongOrElseThrowImpl(ToLongNullable<T> inner) {
            super(inner);
        }

        @Override
        public long applyAsLong(T object) {
            return inner.applyAsLong(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, throws an exception.
     *
     * @param expression  the nullable expression to wrap
     * @param <T>         the input entity type
     * @return            the non-nullable expression
     */
    public static <T> ToIntOrThrow<T>
    intOrElseThrow(ToIntNullable<T> expression) {
        return new ToIntOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToIntNullable.doubleOrElse(int)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToIntOrElseThrowImpl<T>
        extends AbstractNonNullable<T, ToIntNullable<T>>
        implements ToIntOrThrow<T> {
        private ToIntOrElseThrowImpl(ToIntNullable<T> inner) {
            super(inner);
        }

        @Override
        public int applyAsInt(T object) {
            return inner.applyAsInt(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, throws an exception.
     *
     * @param expression  the nullable expression to wrap
     * @param <T>         the input entity type
     * @return            the non-nullable expression
     */
    public static <T> ToShortOrThrow<T>
    shortOrElseThrow(ToShortNullable<T> expression) {
        return new ToShortOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToShortNullable.doubleOrElse(short)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToShortOrElseThrowImpl<T>
        extends AbstractNonNullable<T, ToShortNullable<T>>
        implements ToShortOrThrow<T> {
        private ToShortOrElseThrowImpl(ToShortNullable<T> inner) {
            super(inner);
        }

        @Override
        public short applyAsShort(T object) {
            return inner.applyAsShort(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, throws an exception.
     *
     * @param expression  the nullable expression to wrap
     * @param <T>         the input entity type
     * @return            the non-nullable expression
     */
    public static <T> ToByteOrThrow<T>
    byteOrElseThrow(ToByteNullable<T> expression) {
        return new ToByteOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToByteNullable.doubleOrElse(byte)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToByteOrElseThrowImpl<T>
        extends AbstractNonNullable<T, ToByteNullable<T>>
        implements ToByteOrThrow<T> {
        private ToByteOrElseThrowImpl(ToByteNullable<T> inner) {
            super(inner);
        }

        @Override
        public byte applyAsByte(T object) {
            return inner.applyAsByte(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, throws an exception.
     *
     * @param expression  the nullable expression to wrap
     * @param <T>         the input entity type
     * @return            the non-nullable expression
     */
    public static <T> ToCharOrThrow<T>
    charOrElseThrow(ToCharNullable<T> expression) {
        return new ToCharOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToCharNullable.doubleOrElse(char)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToCharOrElseThrowImpl<T>
        extends AbstractNonNullable<T, ToCharNullable<T>>
        implements ToCharOrThrow<T> {
        private ToCharOrElseThrowImpl(ToCharNullable<T> inner) {
            super(inner);
        }

        @Override
        public char applyAsChar(T object) {
            return inner.applyAsChar(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, throws an exception.
     *
     * @param expression  the nullable expression to wrap
     * @param <T>         the input entity type
     * @return            the non-nullable expression
     */
    public static <T> ToBooleanOrThrow<T>
    booleanOrElseThrow(ToBooleanNullable<T> expression) {
        return new ToBooleanOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToBooleanNullable.doubleOrElse(boolean)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToBooleanOrElseThrowImpl<T>
        extends AbstractNonNullable<T, ToBooleanNullable<T>>
        implements ToBooleanOrThrow<T> {
        private ToBooleanOrElseThrowImpl(ToBooleanNullable<T> inner) {
            super(inner);
        }

        @Override
        public boolean applyAsBoolean(T object) {
            return inner.applyAsBoolean(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, throws an exception.
     *
     * @param expression  the nullable expression to wrap
     * @param <T>         the input entity type
     * @return            the non-nullable expression
     */
    public static <T> ToStringOrThrow<T>
    stringOrElseThrow(ToStringNullable<T> expression) {
        return new ToStringOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToStringNullable.doubleOrElse(String)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToStringOrElseThrowImpl<T>
        extends AbstractNonNullable<T, ToStringNullable<T>>
        implements ToStringOrThrow<T> {
        private ToStringOrElseThrowImpl(ToStringNullable<T> inner) {
            super(inner);
        }

        @Override
        public String apply(T object) {
            return requireNonNull(inner.apply(object));
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, throws an exception.
     *
     * @param expression  the nullable expression to wrap
     * @param <T>         the input entity type
     * @return            the non-nullable expression
     */
    public static <T> ToBigDecimalOrThrow<T>
    bigDecimalOrElseThrow(ToBigDecimalNullable<T> expression) {
        return new ToBigDecimalOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling 
     * {@code ToBigDecimalNullable.doubleOrElse(BigDecimal)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToBigDecimalOrElseThrowImpl<T>
        extends AbstractNonNullable<T, ToBigDecimalNullable<T>>
        implements ToBigDecimalOrThrow<T> {
        private ToBigDecimalOrElseThrowImpl(ToBigDecimalNullable<T> inner) {
            super(inner);
        }

        @Override
        public BigDecimal apply(T object) {
            return requireNonNull(inner.apply(object));
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, throws an exception.
     *
     * @param expression  the nullable expression to wrap
     * @param <T>         the input entity type
     * @param <E>         the enum type
     * @return            the non-nullable expression
     */
    public static <T, E extends Enum<E>> ToEnumOrThrow<T, E>
    enumOrElseThrow(ToEnumNullable<T, E> expression) {
        return new ToEnumOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToEnumNullable.doubleOrElse(Enum)}.
     *
     * @param <T>  the input entity type
     * @param <E>  the enum type
     */
    private static final class ToEnumOrElseThrowImpl<T, E extends Enum<E>>
    extends AbstractNonNullable<T, ToEnumNullable<T, E>>
    implements ToEnumOrThrow<T, E> {

        private ToEnumOrElseThrowImpl(ToEnumNullable<T, E> inner) {
            super(inner);
        }

        @Override
        public E apply(T object) {
            return requireNonNull(inner.apply(object));
        }
    }

    /**
     * Abstract base for a {@link NonNullableExpression}.
     *
     * @param <T>     the input type
     * @param <INNER> the wrapped nullable expression type
     */
    abstract static class AbstractNonNullable<T, INNER extends Expression<T>>
    implements OrElseThrowExpression<T, INNER> {
        final INNER inner;

        AbstractNonNullable(INNER inner) {
            this.inner = requireNonNull(inner);
        }

        @Override
        public final INNER innerNullable() {
            return inner;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NonNullableExpression)) return false;
            final NonNullableExpression<?, ?> that = (NonNullableExpression<?, ?>) o;
            return Objects.equals(inner, that.innerNullable()) &&
                Objects.equals(nullStrategy(), that.nullStrategy());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner);
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private OrElseThrowUtil() {}
}
