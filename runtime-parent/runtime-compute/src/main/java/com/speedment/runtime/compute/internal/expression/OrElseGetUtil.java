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

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.NonNullableExpression;
import com.speedment.runtime.compute.expression.orelse.*;

import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Utility class used to create expressions that wrap a nullable expression and
 * handles any {@code null}-values by applying a different expression to the
 * input.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class OrElseGetUtil {

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, applies a different expression to the input.
     *
     * @param expression     the nullable expression to wrap
     * @param defaultGetter  getter for the default value from the input
     * @param <T>            the input entity type
     * @return               the non-nullable expression
     */
    public static <T> ToDoubleOrElseGet<T>
    doubleOrElseGet(ToDoubleNullable<T> expression, ToDouble<T> defaultGetter) {
        return new ToDoubleOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToDoubleNullable.doubleOrElse(double)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToDoubleOrElseGetImpl<T>
    extends AbstractNonNullable<T, ToDoubleNullable<T>, ToDouble<T>>
    implements ToDoubleOrElseGet<T> {
        private ToDoubleOrElseGetImpl(ToDoubleNullable<T> inner, ToDouble<T> getter) {
            super(inner, getter);
        }

        @Override
        public double applyAsDouble(T object) {
            return inner.isNull(object)
                ? getter.applyAsDouble(object)
                : inner.applyAsDouble(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, applies a different expression to the input.
     *
     * @param expression     the nullable expression to wrap
     * @param defaultGetter  getter for the default value from the input
     * @param <T>            the input entity type
     * @return               the non-nullable expression
     */
    public static <T> ToFloatOrElseGet<T>
    floatOrElseGet(ToFloatNullable<T> expression, ToFloat<T> defaultGetter) {
        return new ToFloatOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToFloatNullable.doubleOrElse(float)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToFloatOrElseGetImpl<T>
        extends AbstractNonNullable<T, ToFloatNullable<T>, ToFloat<T>>
        implements ToFloatOrElseGet<T> {
        private ToFloatOrElseGetImpl(ToFloatNullable<T> inner, ToFloat<T> getter) {
            super(inner, getter);
        }

        @Override
        public float applyAsFloat(T object) {
            return inner.isNull(object)
                ? getter.applyAsFloat(object)
                : inner.applyAsFloat(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, applies a different expression to the input.
     *
     * @param expression     the nullable expression to wrap
     * @param defaultGetter  getter for the default value from the input
     * @param <T>            the input entity type
     * @return               the non-nullable expression
     */
    public static <T> ToLongOrElseGet<T>
    longOrElseGet(ToLongNullable<T> expression, ToLong<T> defaultGetter) {
        return new ToLongOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToLongNullable.doubleOrElse(long)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToLongOrElseGetImpl<T>
        extends AbstractNonNullable<T, ToLongNullable<T>, ToLong<T>>
        implements ToLongOrElseGet<T> {
        private ToLongOrElseGetImpl(ToLongNullable<T> inner, ToLong<T> getter) {
            super(inner, getter);
        }

        @Override
        public long applyAsLong(T object) {
            return inner.isNull(object)
                ? getter.applyAsLong(object)
                : inner.applyAsLong(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, applies a different expression to the input.
     *
     * @param expression     the nullable expression to wrap
     * @param defaultGetter  getter for the default value from the input
     * @param <T>            the input entity type
     * @return               the non-nullable expression
     */
    public static <T> ToIntOrElseGet<T>
    intOrElseGet(ToIntNullable<T> expression, ToInt<T> defaultGetter) {
        return new ToIntOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToIntNullable.doubleOrElse(int)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToIntOrElseGetImpl<T>
        extends AbstractNonNullable<T, ToIntNullable<T>, ToInt<T>>
        implements ToIntOrElseGet<T> {
        private ToIntOrElseGetImpl(ToIntNullable<T> inner, ToInt<T> getter) {
            super(inner, getter);
        }

        @Override
        public int applyAsInt(T object) {
            return inner.isNull(object)
                ? getter.applyAsInt(object)
                : inner.applyAsInt(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, applies a different expression to the input.
     *
     * @param expression     the nullable expression to wrap
     * @param defaultGetter  getter for the default value from the input
     * @param <T>            the input entity type
     * @return               the non-nullable expression
     */
    public static <T> ToShortOrElseGet<T>
    shortOrElseGet(ToShortNullable<T> expression, ToShort<T> defaultGetter) {
        return new ToShortOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToShortNullable.doubleOrElse(short)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToShortOrElseGetImpl<T>
        extends AbstractNonNullable<T, ToShortNullable<T>, ToShort<T>>
        implements ToShortOrElseGet<T> {
        private ToShortOrElseGetImpl(ToShortNullable<T> inner, ToShort<T> getter) {
            super(inner, getter);
        }

        @Override
        public short applyAsShort(T object) {
            return inner.isNull(object)
                ? getter.applyAsShort(object)
                : inner.applyAsShort(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, applies a different expression to the input.
     *
     * @param expression     the nullable expression to wrap
     * @param defaultGetter  getter for the default value from the input
     * @param <T>            the input entity type
     * @return               the non-nullable expression
     */
    public static <T> ToByteOrElseGet<T>
    byteOrElseGet(ToByteNullable<T> expression, ToByte<T> defaultGetter) {
        return new ToByteOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToByteNullable.doubleOrElse(byte)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToByteOrElseGetImpl<T>
        extends AbstractNonNullable<T, ToByteNullable<T>, ToByte<T>>
        implements ToByteOrElseGet<T> {
        private ToByteOrElseGetImpl(ToByteNullable<T> inner, ToByte<T> getter) {
            super(inner, getter);
        }

        @Override
        public byte applyAsByte(T object) {
            return inner.isNull(object)
                ? getter.applyAsByte(object)
                : inner.applyAsByte(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, applies a different expression to the input.
     *
     * @param expression     the nullable expression to wrap
     * @param defaultGetter  getter for the default value from the input
     * @param <T>            the input entity type
     * @return               the non-nullable expression
     */
    public static <T> ToCharOrElseGet<T>
    charOrElseGet(ToCharNullable<T> expression, ToChar<T> defaultGetter) {
        return new ToCharOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToCharNullable.doubleOrElse(char)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToCharOrElseGetImpl<T>
        extends AbstractNonNullable<T, ToCharNullable<T>, ToChar<T>>
        implements ToCharOrElseGet<T> {
        private ToCharOrElseGetImpl(ToCharNullable<T> inner, ToChar<T> getter) {
            super(inner, getter);
        }

        @Override
        public char applyAsChar(T object) {
            return inner.isNull(object)
                ? getter.applyAsChar(object)
                : inner.applyAsChar(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, applies a different expression to the input.
     *
     * @param expression     the nullable expression to wrap
     * @param defaultGetter  getter for the default value from the input
     * @param <T>            the input entity type
     * @return               the non-nullable expression
     */
    public static <T> ToBooleanOrElseGet<T>
    booleanOrElseGet(ToBooleanNullable<T> expression, ToBoolean<T> defaultGetter) {
        return new ToBooleanOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToBooleanNullable.doubleOrElse(boolean)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToBooleanOrElseGetImpl<T>
        extends AbstractNonNullable<T, ToBooleanNullable<T>, ToBoolean<T>>
        implements ToBooleanOrElseGet<T> {
        private ToBooleanOrElseGetImpl(ToBooleanNullable<T> inner, ToBoolean<T> getter) {
            super(inner, getter);
        }

        @Override
        public boolean applyAsBoolean(T object) {
            return inner.isNull(object)
                ? getter.applyAsBoolean(object)
                : inner.applyAsBoolean(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, applies a different expression to the input.
     *
     * @param expression     the nullable expression to wrap
     * @param defaultGetter  getter for the default value from the input
     * @param <T>            the input entity type
     * @return               the non-nullable expression
     */
    public static <T> ToStringOrElseGet<T>
    stringOrElseGet(ToStringNullable<T> expression, ToString<T> defaultGetter) {
        return new ToStringOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToStringNullable.doubleOrElse(string)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToStringOrElseGetImpl<T>
    extends AbstractNonNullable<T, ToStringNullable<T>, ToString<T>>
    implements ToStringOrElseGet<T> {
        private ToStringOrElseGetImpl(ToStringNullable<T> inner, ToString<T> getter) {
            super(inner, getter);
        }

        @Override
        public String apply(T object) {
            return inner.isNull(object)
                ? getter.apply(object)
                : inner.apply(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, applies a different expression to the input.
     *
     * @param expression     the nullable expression to wrap
     * @param defaultGetter  getter for the default value from the input
     * @param <T>            the input entity type
     * @return               the non-nullable expression
     */
    public static <T> ToBigDecimalOrElseGet<T>
    bigDecimalOrElseGet(ToBigDecimalNullable<T> expression, ToBigDecimal<T> defaultGetter) {
        return new ToBigDecimalOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling
     * {@code ToBigDecimalNullable.doubleOrElse(bigDecimal)}.
     *
     * @param <T>  the input entity type
     */
    private static final class ToBigDecimalOrElseGetImpl<T>
        extends AbstractNonNullable<T, ToBigDecimalNullable<T>, ToBigDecimal<T>>
        implements ToBigDecimalOrElseGet<T> {
        private ToBigDecimalOrElseGetImpl(ToBigDecimalNullable<T> inner, ToBigDecimal<T> getter) {
            super(inner, getter);
        }

        @Override
        public BigDecimal apply(T object) {
            return inner.isNull(object)
                ? getter.apply(object)
                : inner.apply(object);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, applies a different expression to the input.
     *
     * @param expression     the nullable expression to wrap
     * @param defaultGetter  getter for the default value from the input
     * @param <T>            the input entity type
     * @param <E>            the enum type
     * @return               the non-nullable expression
     */
    public static <T, E extends Enum<E>> ToEnumOrElseGet<T, E>
    enumOrElseGet(ToEnumNullable<T, E> expression, ToEnum<T, E> defaultGetter) {
        return new ToEnumOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToEnumNullable.doubleOrElse(enum)}.
     *
     * @param <T>  the input entity type
     * @param <E>  the enum type
     */
    private static final class ToEnumOrElseGetImpl<T, E extends Enum<E>>
    extends AbstractNonNullable<T, ToEnumNullable<T, E>, ToEnum<T, E>>
    implements ToEnumOrElseGet<T, E> {
        private ToEnumOrElseGetImpl(ToEnumNullable<T, E> inner, ToEnum<T, E> getter) {
            super(inner, getter);
        }

        @Override
        public Class<E> enumClass() {
            return inner.enumClass();
        }

        @Override
        public E apply(T object) {
            return inner.isNull(object)
                ? getter.apply(object)
                : inner.apply(object);
        }
    }

    /**
     * Abstract base for a {@link NonNullableExpression}.
     *
     * @param <T>        the input type
     * @param <INNER>    the wrapped nullable expression type
     * @param <DEFAULT>  the default value expression type
     */
    private abstract static class AbstractNonNullable
        <T, INNER extends Expression<T>, DEFAULT extends Expression<T>>
    implements OrElseGetExpression<T, INNER, DEFAULT> {

        final INNER inner;
        final DEFAULT getter;

        AbstractNonNullable(INNER inner, DEFAULT getter) {
            this.inner  = requireNonNull(inner);
            this.getter = requireNonNull(getter);
        }

        @Override
        public final INNER innerNullable() {
            return inner;
        }

        @Override
        public final DEFAULT defaultValueGetter() {
            return getter;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OrElseGetExpression)) return false;
            final OrElseGetExpression<?, ?, ?> that = (OrElseGetExpression<?, ?, ?>) o;
            return Objects.equals(inner, that.innerNullable()) &&
                Objects.equals(getter, that.defaultValueGetter());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, getter);
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private OrElseGetUtil() {}
}
