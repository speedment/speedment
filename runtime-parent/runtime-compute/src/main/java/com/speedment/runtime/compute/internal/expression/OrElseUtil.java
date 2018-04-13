package com.speedment.runtime.compute.internal.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.NonNullableExpression;
import com.speedment.runtime.compute.expression.orelse.*;

import static java.util.Objects.requireNonNull;

/**
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
    orElse(ToDoubleNullable<T> expression, double defaultValue) {
        return new ToDoubleOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToDoubleNullable.orElse(double)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToDoubleOrElseImpl<T>
    extends AbstractNonNullable<ToDoubleNullable<T>>
    implements ToDoubleOrElse<T> {
        private final double value;

        private ToDoubleOrElseImpl(ToDoubleNullable<T> inner, double value) {
            super(inner);
            this.value = value;
        }

        @Override
        public double getDefaultValue() {
            return value;
        }

        @Override
        public double applyAsDouble(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsDouble(object);
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
    orElse(ToFloatNullable<T> expression, float defaultValue) {
        return new ToFloatOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToFloatNullable.orElse(float)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToFloatOrElseImpl<T>
        extends AbstractNonNullable<ToFloatNullable<T>>
        implements ToFloatOrElse<T> {
        private final float value;

        private ToFloatOrElseImpl(ToFloatNullable<T> inner, float value) {
            super(inner);
            this.value = value;
        }

        @Override
        public float getDefaultValue() {
            return value;
        }

        @Override
        public float applyAsFloat(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsFloat(object);
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
    orElse(ToLongNullable<T> expression, long defaultValue) {
        return new ToLongOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToLongNullable.orElse(long)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToLongOrElseImpl<T>
        extends AbstractNonNullable<ToLongNullable<T>>
        implements ToLongOrElse<T> {
        private final long value;

        private ToLongOrElseImpl(ToLongNullable<T> inner, long value) {
            super(inner);
            this.value = value;
        }

        @Override
        public long getDefaultValue() {
            return value;
        }

        @Override
        public long applyAsLong(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsLong(object);
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
    orElse(ToIntNullable<T> expression, int defaultValue) {
        return new ToIntOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToIntNullable.orElse(int)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToIntOrElseImpl<T>
        extends AbstractNonNullable<ToIntNullable<T>>
        implements ToIntOrElse<T> {
        private final int value;

        private ToIntOrElseImpl(ToIntNullable<T> inner, int value) {
            super(inner);
            this.value = value;
        }

        @Override
        public int getDefaultValue() {
            return value;
        }

        @Override
        public int applyAsInt(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsInt(object);
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
    orElse(ToShortNullable<T> expression, short defaultValue) {
        return new ToShortOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToShortNullable.orElse(short)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToShortOrElseImpl<T>
        extends AbstractNonNullable<ToShortNullable<T>>
        implements ToShortOrElse<T> {
        private final short value;

        private ToShortOrElseImpl(ToShortNullable<T> inner, short value) {
            super(inner);
            this.value = value;
        }

        @Override
        public short getDefaultValue() {
            return value;
        }

        @Override
        public short applyAsShort(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsShort(object);
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
    orElse(ToByteNullable<T> expression, byte defaultValue) {
        return new ToByteOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToByteNullable.orElse(byte)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToByteOrElseImpl<T>
        extends AbstractNonNullable<ToByteNullable<T>>
        implements ToByteOrElse<T> {
        private final byte value;

        private ToByteOrElseImpl(ToByteNullable<T> inner, byte value) {
            super(inner);
            this.value = value;
        }

        @Override
        public byte getDefaultValue() {
            return value;
        }

        @Override
        public byte applyAsByte(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsByte(object);
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
    orElse(ToCharNullable<T> expression, char defaultValue) {
        return new ToCharOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToCharNullable.orElse(char)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToCharOrElseImpl<T>
        extends AbstractNonNullable<ToCharNullable<T>>
        implements ToCharOrElse<T> {
        private final char value;

        private ToCharOrElseImpl(ToCharNullable<T> inner, char value) {
            super(inner);
            this.value = value;
        }

        @Override
        public char getDefaultValue() {
            return value;
        }

        @Override
        public char applyAsChar(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsChar(object);
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
    orElse(ToBooleanNullable<T> expression, boolean defaultValue) {
        return new ToBooleanOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToBooleanNullable.orElse(boolean)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToBooleanOrElseImpl<T>
        extends AbstractNonNullable<ToBooleanNullable<T>>
        implements ToBooleanOrElse<T> {
        private final boolean value;

        private ToBooleanOrElseImpl(ToBooleanNullable<T> inner, boolean value) {
            super(inner);
            this.value = value;
        }

        @Override
        public boolean getDefaultValue() {
            return value;
        }

        @Override
        public boolean applyAsBoolean(T object) {
            return inner.isNull(object) ? value :
                inner.applyAsBoolean(object);
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
    orElse(ToStringNullable<T> expression, String defaultValue) {
        return new ToStringOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToStringNullable.orElse(String)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToStringOrElseImpl<T>
    extends AbstractNonNullable<ToStringNullable<T>>
    implements ToStringOrElse<T> {
        private final String value;

        private ToStringOrElseImpl(ToStringNullable<T> inner, String value) {
            super(inner);
            this.value = value;
        }

        @Override
        public String getDefaultValue() {
            return value;
        }

        @Override
        public String apply(T object) {
            return inner.isNull(object) ? value :
                inner.apply(object);
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
    orElse(ToEnumNullable<T, E> expression, E defaultValue) {
        return new ToEnumOrElseImpl<>(expression, defaultValue);
    }

    /**
     * Internal class used when calling {@code ToEnumNullable.orElse(Enum)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToEnumOrElseImpl<T, E extends Enum<E>>
    extends AbstractNonNullable<ToEnumNullable<T, E>>
    implements ToEnumOrElse<T, E> {
        private final E value;

        private ToEnumOrElseImpl(ToEnumNullable<T, E> inner, E value) {
            super(inner);
            this.value = value;
        }

        @Override
        public Class<E> enumClass() {
            return inner.enumClass();
        }

        @Override
        public E getDefaultValue() {
            return value;
        }

        @Override
        public E apply(T object) {
            return inner.isNull(object) ? value :
                inner.apply(object);
        }
    }
    
    /**
     * Abstract base for a {@link NonNullableExpression}.
     *
     * @param <INNER>  the wrapped nullable expression type
     */
    private static abstract class AbstractNonNullable<INNER extends Expression>
    implements NonNullableExpression<INNER> {
        final INNER inner;

        AbstractNonNullable(INNER inner) {
            this.inner = requireNonNull(inner);
        }

        @Override
        public final INNER getInnerNullable() {
            return inner;
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private OrElseUtil() {}
}
