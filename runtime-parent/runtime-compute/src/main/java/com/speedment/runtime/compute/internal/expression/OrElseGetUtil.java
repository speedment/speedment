package com.speedment.runtime.compute.internal.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.NonNullableExpression;
import com.speedment.runtime.compute.expression.orelse.*;

import java.math.BigDecimal;

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
    orElseGet(ToDoubleNullable<T> expression, ToDouble<T> defaultGetter) {
        return new ToDoubleOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToDoubleNullable.orElse(double)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToDoubleOrElseGetImpl<T>
    extends AbstractNonNullable<ToDoubleNullable<T>, ToDouble<T>>
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
    orElseGet(ToFloatNullable<T> expression, ToFloat<T> defaultGetter) {
        return new ToFloatOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToFloatNullable.orElse(float)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToFloatOrElseGetImpl<T>
        extends AbstractNonNullable<ToFloatNullable<T>, ToFloat<T>>
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
    orElseGet(ToLongNullable<T> expression, ToLong<T> defaultGetter) {
        return new ToLongOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToLongNullable.orElse(long)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToLongOrElseGetImpl<T>
        extends AbstractNonNullable<ToLongNullable<T>, ToLong<T>>
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
    orElseGet(ToIntNullable<T> expression, ToInt<T> defaultGetter) {
        return new ToIntOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToIntNullable.orElse(int)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToIntOrElseGetImpl<T>
        extends AbstractNonNullable<ToIntNullable<T>, ToInt<T>>
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
    orElseGet(ToShortNullable<T> expression, ToShort<T> defaultGetter) {
        return new ToShortOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToShortNullable.orElse(short)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToShortOrElseGetImpl<T>
        extends AbstractNonNullable<ToShortNullable<T>, ToShort<T>>
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
    orElseGet(ToByteNullable<T> expression, ToByte<T> defaultGetter) {
        return new ToByteOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToByteNullable.orElse(byte)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToByteOrElseGetImpl<T>
        extends AbstractNonNullable<ToByteNullable<T>, ToByte<T>>
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
    orElseGet(ToCharNullable<T> expression, ToChar<T> defaultGetter) {
        return new ToCharOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToCharNullable.orElse(char)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToCharOrElseGetImpl<T>
        extends AbstractNonNullable<ToCharNullable<T>, ToChar<T>>
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
    orElseGet(ToBooleanNullable<T> expression, ToBoolean<T> defaultGetter) {
        return new ToBooleanOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToBooleanNullable.orElse(boolean)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToBooleanOrElseGetImpl<T>
        extends AbstractNonNullable<ToBooleanNullable<T>, ToBoolean<T>>
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
    orElseGet(ToStringNullable<T> expression, ToString<T> defaultGetter) {
        return new ToStringOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToStringNullable.orElse(string)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToStringOrElseGetImpl<T>
    extends AbstractNonNullable<ToStringNullable<T>, ToString<T>>
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
    orElseGet(ToBigDecimalNullable<T> expression, ToBigDecimal<T> defaultGetter) {
        return new ToBigDecimalOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling
     * {@code ToBigDecimalNullable.orElse(bigDecimal)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToBigDecimalOrElseGetImpl<T>
        extends AbstractNonNullable<ToBigDecimalNullable<T>, ToBigDecimal<T>>
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
    orElseGet(ToEnumNullable<T, E> expression, ToEnum<T, E> defaultGetter) {
        return new ToEnumOrElseGetImpl<>(expression, defaultGetter);
    }

    /**
     * Internal class used when calling {@code ToEnumNullable.orElse(enum)}.
     *
     * @param <T>  the input entity type
     * @param <E>  the enum type
     */
    private final static class ToEnumOrElseGetImpl<T, E extends Enum<E>>
    extends AbstractNonNullable<ToEnumNullable<T, E>, ToEnum<T, E>>
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
     * @param <INNER>    the wrapped nullable expression type
     * @param <DEFAULT>  the default value expression type
     */
    private static abstract class AbstractNonNullable
        <INNER extends Expression, DEFAULT extends Expression>
    implements OrElseGetExpression<INNER, DEFAULT> {
        final INNER inner;
        final DEFAULT getter;

        AbstractNonNullable(INNER inner, DEFAULT getter) {
            this.inner  = requireNonNull(inner);
            this.getter = requireNonNull(getter);
        }

        @Override
        public final INNER getInnerNullable() {
            return inner;
        }

        @Override
        public final DEFAULT getDefaultValueGetter() {
            return getter;
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private OrElseGetUtil() {}
}
