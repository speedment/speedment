package com.speedment.runtime.compute.internal.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.UnaryExpression;
import com.speedment.runtime.compute.internal.*;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

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
    public static <T> ToByte<T> abs(ToByte<T> expression) {
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
    public static <T> ToShort<T> abs(ToShort<T> expression) {
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
    public static <T> ToInt<T> abs(ToInt<T> expression) {
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
    public static <T> ToLong<T> abs(ToLong<T> expression) {
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
    public static <T> ToFloat<T> abs(ToFloat<T> expression) {
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
    public static <T> ToDouble<T> abs(ToDouble<T> expression) {
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
     * (removing the negation sign if any). If the result of the original
     * expression is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByteNullable<T> absOrNull(ToByteNullable<T> expression) {
        return new ToByteNullableImpl<>(
            abs(expression.orThrow()),
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
    public static <T> ToShortNullable<T> absOrNull(ToShortNullable<T> expression) {
        return new ToShortNullableImpl<>(
            abs(expression.orThrow()),
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
    public static <T> ToIntNullable<T> absOrNull(ToIntNullable<T> expression) {
        return new ToIntNullableImpl<>(
            abs(expression.orThrow()),
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
    public static <T> ToLongNullable<T> absOrNull(ToLongNullable<T> expression) {
        return new ToLongNullableImpl<>(
            abs(expression.orThrow()),
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
    public static <T> ToFloatNullable<T> absOrNull(ToFloatNullable<T> expression) {
        return new ToFloatNullableImpl<>(
            abs(expression.orThrow()),
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
    public static <T> ToDoubleNullable<T> absOrNull(ToDoubleNullable<T> expression) {
        return new ToDoubleNullableImpl<>(
            abs(expression.orThrow()),
            expression.isNull()
        );
    }

    /**
     * The abstract base for an absolute expression.
     *
     * @param <T>      the input entity type
     * @param <INNER>  the inner expression type
     */
    private abstract static class AbstractAbs<T, INNER extends Expression<T>>
    implements UnaryExpression<T, INNER> {

        final INNER inner;

        private AbstractAbs(INNER inner) {
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
                && operator().equals(that.operator());
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
