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
import com.speedment.runtime.compute.expression.orelse.ToBigDecimalOrElse;
import com.speedment.runtime.compute.expression.orelse.ToBooleanOrElse;
import com.speedment.runtime.compute.expression.orelse.ToByteOrElse;
import com.speedment.runtime.compute.expression.orelse.ToCharOrElse;
import com.speedment.runtime.compute.expression.orelse.ToDoubleOrElse;
import com.speedment.runtime.compute.expression.orelse.ToEnumOrElse;
import com.speedment.runtime.compute.expression.orelse.ToFloatOrElse;
import com.speedment.runtime.compute.expression.orelse.ToIntOrElse;
import com.speedment.runtime.compute.expression.orelse.ToLongOrElse;
import com.speedment.runtime.compute.expression.orelse.ToShortOrElse;
import com.speedment.runtime.compute.expression.orelse.ToStringOrElse;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Utility class used to create expressions that wrap a nullable expression and
 * handles any {@code null}-values by simply returning a default value instead.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class OrElseUtil {

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, then the default value is returned instead.
     *
     * @param expression    the nullable expression to wrap
     * @param defaultValue  the default value to use
     * @param <T>           the input entity type
     * @return              the non-nullable expression
     */
    public static <T> ToDoubleOrElse<T>
    doubleOrElse(ToDoubleNullable<T> expression, double defaultValue) {
        return new ToDoubleOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToDoubleNullable.orElse(double)}.
     *
     * @param <T>  the input entity type
     */
    static final class ToDoubleOrElseImpl<T>
    extends AbstractNonNullable<T, ToDoubleNullable<T>>
    implements ToDoubleOrElse<T> {
        private final double value;

        ToDoubleOrElseImpl(ToDoubleNullable<T> inner, double value) {
            super(inner);
            this.value = value;
        }

        @Override
        public double defaultValue() {
            return value;
        }

        @Override
        public double applyAsDouble(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsDouble(object);
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ToDoubleOrElse)) return false;
            final ToDoubleOrElse<?> that = (ToDoubleOrElse<?>) o;
            return Objects.equals(inner, that.innerNullable()) &&
                value == that.defaultValue();
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, value);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, then the default value is returned instead.
     *
     * @param expression    the nullable expression to wrap
     * @param defaultValue  the default value to use
     * @param <T>           the input entity type
     * @return              the non-nullable expression
     */
    public static <T> ToFloatOrElse<T>
    floatOrElse(ToFloatNullable<T> expression, float defaultValue) {
        return new ToFloatOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToFloatNullable.orElse(float)}.
     *
     * @param <T>  the input entity type
     */
    static final class ToFloatOrElseImpl<T>
        extends AbstractNonNullable<T, ToFloatNullable<T>>
        implements ToFloatOrElse<T> {
        private final float value;

        ToFloatOrElseImpl(ToFloatNullable<T> inner, float value) {
            super(inner);
            this.value = value;
        }

        @Override
        public float defaultValue() {
            return value;
        }

        @Override
        public float applyAsFloat(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsFloat(object);
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ToFloatOrElse)) return false;
            final ToFloatOrElse<?> that = (ToFloatOrElse<?>) o;
            return Objects.equals(inner, that.innerNullable()) &&
                value == that.defaultValue();
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, value);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, then the default value is returned instead.
     *
     * @param expression    the nullable expression to wrap
     * @param defaultValue  the default value to use
     * @param <T>           the input entity type
     * @return              the non-nullable expression
     */
    public static <T> ToLongOrElse<T>
    longOrElse(ToLongNullable<T> expression, long defaultValue) {
        return new ToLongOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToLongNullable.orElse(long)}.
     *
     * @param <T>  the input entity type
     */
    static final class ToLongOrElseImpl<T>
        extends AbstractNonNullable<T, ToLongNullable<T>>
        implements ToLongOrElse<T> {
        private final long value;

        ToLongOrElseImpl(ToLongNullable<T> inner, long value) {
            super(inner);
            this.value = value;
        }

        @Override
        public long defaultValue() {
            return value;
        }

        @Override
        public long applyAsLong(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsLong(object);
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ToLongOrElse)) return false;
            final ToLongOrElse<?> that = (ToLongOrElse<?>) o;
            return Objects.equals(inner, that.innerNullable()) &&
                value == that.defaultValue();
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, value);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, then the default value is returned instead.
     *
     * @param expression    the nullable expression to wrap
     * @param defaultValue  the default value to use
     * @param <T>           the input entity type
     * @return              the non-nullable expression
     */
    public static <T> ToIntOrElse<T>
    intOrElse(ToIntNullable<T> expression, int defaultValue) {
        return new ToIntOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToIntNullable.orElse(int)}.
     *
     * @param <T>  the input entity type
     */
    static final class ToIntOrElseImpl<T>
        extends AbstractNonNullable<T, ToIntNullable<T>>
        implements ToIntOrElse<T> {
        private final int value;

        ToIntOrElseImpl(ToIntNullable<T> inner, int value) {
            super(inner);
            this.value = value;
        }

        @Override
        public int defaultValue() {
            return value;
        }

        @Override
        public int applyAsInt(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsInt(object);
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ToIntOrElse)) return false;
            final ToIntOrElse<?> that = (ToIntOrElse<?>) o;
            return Objects.equals(inner, that.innerNullable()) &&
                value == that.defaultValue();
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, value);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, then the default value is returned instead.
     *
     * @param expression    the nullable expression to wrap
     * @param defaultValue  the default value to use
     * @param <T>           the input entity type
     * @return              the non-nullable expression
     */
    public static <T> ToShortOrElse<T>
    shortOrElse(ToShortNullable<T> expression, short defaultValue) {
        return new ToShortOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToShortNullable.orElse(short)}.
     *
     * @param <T>  the input entity type
     */
    static final class ToShortOrElseImpl<T>
        extends AbstractNonNullable<T, ToShortNullable<T>>
        implements ToShortOrElse<T> {
        private final short value;

        ToShortOrElseImpl(ToShortNullable<T> inner, short value) {
            super(inner);
            this.value = value;
        }

        @Override
        public short defaultValue() {
            return value;
        }

        @Override
        public short applyAsShort(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsShort(object);
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ToShortOrElse)) return false;
            final ToShortOrElse<?> that = (ToShortOrElse<?>) o;
            return Objects.equals(inner, that.innerNullable()) &&
                value == that.defaultValue();
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, value);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, then the default value is returned instead.
     *
     * @param expression    the nullable expression to wrap
     * @param defaultValue  the default value to use
     * @param <T>           the input entity type
     * @return              the non-nullable expression
     */
    public static <T> ToByteOrElse<T>
    byteOrElse(ToByteNullable<T> expression, byte defaultValue) {
        return new ToByteOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToByteNullable.orElse(byte)}.
     *
     * @param <T>  the input entity type
     */
    static final class ToByteOrElseImpl<T>
        extends AbstractNonNullable<T, ToByteNullable<T>>
        implements ToByteOrElse<T> {
        private final byte value;

        ToByteOrElseImpl(ToByteNullable<T> inner, byte value) {
            super(inner);
            this.value = value;
        }

        @Override
        public byte defaultValue() {
            return value;
        }

        @Override
        public byte applyAsByte(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsByte(object);
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ToByteOrElse)) return false;
            final ToByteOrElse<?> that = (ToByteOrElse<?>) o;
            return Objects.equals(inner, that.innerNullable()) &&
                value == that.defaultValue();
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, value);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, then the default value is returned instead.
     *
     * @param expression    the nullable expression to wrap
     * @param defaultValue  the default value to use
     * @param <T>           the input entity type
     * @return              the non-nullable expression
     */
    public static <T> ToCharOrElse<T>
    charOrElse(ToCharNullable<T> expression, char defaultValue) {
        return new ToCharOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToCharNullable.orElse(char)}.
     *
     * @param <T>  the input entity type
     */
    static final class ToCharOrElseImpl<T>
        extends AbstractNonNullable<T, ToCharNullable<T>>
        implements ToCharOrElse<T> {
        private final char value;

        ToCharOrElseImpl(ToCharNullable<T> inner, char value) {
            super(inner);
            this.value = value;
        }

        @Override
        public char defaultValue() {
            return value;
        }

        @Override
        public char applyAsChar(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsChar(object);
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ToCharOrElse)) return false;
            final ToCharOrElse<?> that = (ToCharOrElse<?>) o;
            return Objects.equals(inner, that.innerNullable()) &&
                value == that.defaultValue();
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, value);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, then the default value is returned instead.
     *
     * @param expression    the nullable expression to wrap
     * @param defaultValue  the default value to use
     * @param <T>           the input entity type
     * @return              the non-nullable expression
     */
    public static <T> ToBooleanOrElse<T>
    booleanOrElse(ToBooleanNullable<T> expression, boolean defaultValue) {
        return new ToBooleanOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToBooleanNullable.orElse(boolean)}.
     *
     * @param <T>  the input entity type
     */
    static final class ToBooleanOrElseImpl<T>
        extends AbstractNonNullable<T, ToBooleanNullable<T>>
        implements ToBooleanOrElse<T> {
        private final boolean value;

        ToBooleanOrElseImpl(ToBooleanNullable<T> inner, boolean value) {
            super(inner);
            this.value = value;
        }

        @Override
        public boolean defaultValue() {
            return value;
        }

        @Override
        public boolean applyAsBoolean(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsBoolean(object);
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ToBooleanOrElse)) return false;
            final ToBooleanOrElse<?> that = (ToBooleanOrElse<?>) o;
            return Objects.equals(inner, that.innerNullable()) &&
                value == that.defaultValue();
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, value);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, then the default value is returned instead.
     *
     * @param expression    the nullable expression to wrap
     * @param defaultValue  the default value to use
     * @param <T>           the input entity type
     * @return              the non-nullable expression
     */
    public static <T> ToStringOrElse<T>
    stringOrElse(ToStringNullable<T> expression, String defaultValue) {
        return new ToStringOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToStringNullable.orElse(String)}.
     *
     * @param <T>  the input entity type
     */
    static final class ToStringOrElseImpl<T>
    extends AbstractNonNullable<T, ToStringNullable<T>>
    implements ToStringOrElse<T> {
        private final String value;

        ToStringOrElseImpl(ToStringNullable<T> inner, String value) {
            super(inner);
            this.value = value;
        }

        @Override
        public String defaultValue() {
            return value;
        }

        @Override
        public String apply(T object) {
            return inner.isNull(object) ? value :
                inner.apply(object);
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ToStringOrElse)) return false;
            final ToStringOrElse<?> that = (ToStringOrElse<?>) o;
            return Objects.equals(inner, that.innerNullable()) &&
                Objects.equals(value, that.defaultValue());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, value);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, then the default value is returned instead.
     *
     * @param expression    the nullable expression to wrap
     * @param defaultValue  the default value to use
     * @param <T>           the input entity type
     * @param <E>           the enum type
     * @return              the non-nullable expression
     */
    public static <T, E extends Enum<E>> ToEnumOrElse<T, E>
    enumOrElse(ToEnumNullable<T, E> expression, E defaultValue) {
        return new ToEnumOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToEnumNullable.orElse(Enum)}.
     *
     * @param <T>  the input entity type
     */
    static final class ToEnumOrElseImpl<T, E extends Enum<E>>
    extends AbstractNonNullable<T, ToEnumNullable<T, E>>
    implements ToEnumOrElse<T, E> {
        private final E value;

        ToEnumOrElseImpl(ToEnumNullable<T, E> inner, E value) {
            super(inner);
            this.value = value;
        }

        @Override
        public Class<E> enumClass() {
            return inner.enumClass();
        }

        @Override
        public E defaultValue() {
            return value;
        }

        @Override
        public E apply(T object) {
            return inner.isNull(object) ? value :
                inner.apply(object);
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ToEnumOrElse)) return false;
            final ToEnumOrElse<?, ?> that = (ToEnumOrElse<?, ?>) o;
            return Objects.equals(inner, that.innerNullable()) &&
                Objects.equals(value, that.defaultValue());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, value);
        }
    }

    /**
     * Returns an expression that returns the same value as the specified
     * expression if it is not {@code null}, and if the wrapped expression
     * returns {@code null}, then the default value is returned instead.
     *
     * @param expression    the nullable expression to wrap
     * @param defaultValue  the default value to use
     * @param <T>           the input entity type
     * @return              the non-nullable expression
     */
    public static <T> ToBigDecimalOrElse<T>
    bigDecimalOrElse(ToBigDecimalNullable<T> expression, BigDecimal defaultValue) {
        return new ToBigDecimalOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToBigDecimalNullable.orElse(BigDecimal)}.
     *
     * @param <T>  the input entity type
     */
    static final class ToBigDecimalOrElseImpl<T>
    extends AbstractNonNullable<T, ToBigDecimalNullable<T>>
    implements ToBigDecimalOrElse<T> {

        private final BigDecimal value;

        ToBigDecimalOrElseImpl(ToBigDecimalNullable<T> inner, BigDecimal value) {
            super(inner);
            this.value = value;
        }

        @Override
        public BigDecimal defaultValue() {
            return value;
        }

        @Override
        public BigDecimal apply(T object) {
            return inner.isNull(object) ? value :
                inner.apply(object);
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ToBigDecimalOrElse)) return false;
            final ToBigDecimalOrElse<?> that = (ToBigDecimalOrElse<?>) o;
            return Objects.equals(inner, that.innerNullable()) &&
                Objects.equals(value, that.defaultValue());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, value);
        }
    }
    
    /**
     * Abstract base for a {@link NonNullableExpression}.
     *
     * @param <T>      the input type
     * @param <INNER>  the wrapped nullable expression type
     */
    private abstract static class AbstractNonNullable<T, INNER extends Expression<T>>
    implements NonNullableExpression<T, INNER> {
        final INNER inner;

        AbstractNonNullable(INNER inner) {
            this.inner = requireNonNull(inner);
        }

        @Override
        public final INNER innerNullable() {
            return inner;
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private OrElseUtil() {}
}
