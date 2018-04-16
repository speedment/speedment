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
    orElseThrow(ToDoubleNullable<T> expression) {
        return new ToDoubleOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToDoubleNullable.orElse(double)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToDoubleOrElseThrowImpl<T>
    extends AbstractNonNullable<ToDoubleNullable<T>>
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
    orElseThrow(ToFloatNullable<T> expression) {
        return new ToFloatOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToFloatNullable.orElse(float)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToFloatOrElseThrowImpl<T>
        extends AbstractNonNullable<ToFloatNullable<T>>
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
    orElseThrow(ToLongNullable<T> expression) {
        return new ToLongOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToLongNullable.orElse(long)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToLongOrElseThrowImpl<T>
        extends AbstractNonNullable<ToLongNullable<T>>
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
    orElseThrow(ToIntNullable<T> expression) {
        return new ToIntOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToIntNullable.orElse(int)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToIntOrElseThrowImpl<T>
        extends AbstractNonNullable<ToIntNullable<T>>
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
    orElseThrow(ToShortNullable<T> expression) {
        return new ToShortOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToShortNullable.orElse(short)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToShortOrElseThrowImpl<T>
        extends AbstractNonNullable<ToShortNullable<T>>
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
    orElseThrow(ToByteNullable<T> expression) {
        return new ToByteOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToByteNullable.orElse(byte)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToByteOrElseThrowImpl<T>
        extends AbstractNonNullable<ToByteNullable<T>>
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
    orElseThrow(ToCharNullable<T> expression) {
        return new ToCharOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToCharNullable.orElse(char)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToCharOrElseThrowImpl<T>
        extends AbstractNonNullable<ToCharNullable<T>>
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
    orElseThrow(ToBooleanNullable<T> expression) {
        return new ToBooleanOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToBooleanNullable.orElse(boolean)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToBooleanOrElseThrowImpl<T>
        extends AbstractNonNullable<ToBooleanNullable<T>>
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
    orElseThrow(ToStringNullable<T> expression) {
        return new ToStringOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToStringNullable.orElse(String)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToStringOrElseThrowImpl<T>
        extends AbstractNonNullable<ToStringNullable<T>>
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
    orElseThrow(ToBigDecimalNullable<T> expression) {
        return new ToBigDecimalOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling 
     * {@code ToBigDecimalNullable.orElse(BigDecimal)}.
     *
     * @param <T>  the input entity type
     */
    private final static class ToBigDecimalOrElseThrowImpl<T>
        extends AbstractNonNullable<ToBigDecimalNullable<T>>
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
    orElseThrow(ToEnumNullable<T, E> expression) {
        return new ToEnumOrElseThrowImpl<>(expression);
    }

    /**
     * Internal class used when calling {@code ToEnumNullable.orElse(Enum)}.
     *
     * @param <T>  the input entity type
     * @param <E>  the enum type
     */
    private final static class ToEnumOrElseThrowImpl<T, E extends Enum<E>>
        extends AbstractNonNullable<ToEnumNullable<T, E>>
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
     * @param <INNER> the wrapped nullable expression type
     */
    private static abstract class AbstractNonNullable<INNER extends Expression>
    implements OrElseThrowExpression<INNER> {
        final INNER inner;

        AbstractNonNullable(INNER inner) {
            this.inner = requireNonNull(inner);
        }

        @Override
        public final INNER getInnerNullable() {
            return inner;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NonNullableExpression)) return false;
            final NonNullableExpression<?> that = (NonNullableExpression<?>) o;
            return Objects.equals(inner, that.getInnerNullable()) &&
                Objects.equals(getNullStrategy(), that.getNullStrategy());
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
