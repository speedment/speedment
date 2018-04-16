package com.speedment.runtime.compute.internal.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.UnaryExpression;
import com.speedment.runtime.compute.internal.*;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Utility class used to construct expression that gives the negation of
 * another expression.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class NegateUtil {

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> negate(ToByte<T> expression) {
        class NegateByte extends AbstractNegate<ToByte<T>> implements ToByte<T> {
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
    public static <T> ToShort<T> negate(ToShort<T> expression) {
        class NegateShort extends AbstractNegate<ToShort<T>> implements ToShort<T> {
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
    public static <T> ToInt<T> negate(ToInt<T> expression) {
        class NegateInt extends AbstractNegate<ToInt<T>> implements ToInt<T> {
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
    public static <T> ToLong<T> negate(ToLong<T> expression) {
        class NegateLong extends AbstractNegate<ToLong<T>> implements ToLong<T> {
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
    public static <T> ToFloat<T> negate(ToFloat<T> expression) {
        class NegateFloat extends AbstractNegate<ToFloat<T>> implements ToFloat<T> {
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
    public static <T> ToDouble<T> negate(ToDouble<T> expression) {
        class NegateDouble extends AbstractNegate<ToDouble<T>> implements ToDouble<T> {
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
    public static <T> ToBoolean<T> negate(ToBoolean<T> expression) {
        class NegateBoolean extends AbstractNegate<ToBoolean<T>> implements ToBoolean<T> {
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
    public static <T> ToByteNullable<T> negateOrNull(ToByteNullable<T> expression) {
        return new ToByteNullableImpl<>(
            negate(expression.orThrow()),
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
    public static <T> ToShortNullable<T> negateOrNull(ToShortNullable<T> expression) {
        return new ToShortNullableImpl<>(
            negate(expression.orThrow()),
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
    public static <T> ToIntNullable<T> negateOrNull(ToIntNullable<T> expression) {
        return new ToIntNullableImpl<>(
            negate(expression.orThrow()),
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
    public static <T> ToLongNullable<T> negateOrNull(ToLongNullable<T> expression) {
        return new ToLongNullableImpl<>(
            negate(expression.orThrow()),
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
    public static <T> ToFloatNullable<T> negateOrNull(ToFloatNullable<T> expression) {
        return new ToFloatNullableImpl<>(
            negate(expression.orThrow()),
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
    public static <T> ToDoubleNullable<T> negateOrNull(ToDoubleNullable<T> expression) {
        return new ToDoubleNullableImpl<>(
            negate(expression.orThrow()),
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
    public static <T> ToBooleanNullable<T> negateOrNull(ToBooleanNullable<T> expression) {
        return new ToBooleanNullableImpl<>(
            negate(expression.orThrow()),
            expression.isNull()
        );
    }

    /**
     * The abstract base for a negate expression.
     *
     * @param <INNER>  the inner expression type
     */
    private abstract static class AbstractNegate<INNER extends Expression>
    implements UnaryExpression<INNER> {

        final INNER inner;

        private AbstractNegate(INNER inner) {
            this.inner = requireNonNull(inner);
        }

        @Override
        public final INNER getInner() {
            return inner;
        }

        @Override
        public final Operator getOperator() {
            return Operator.NEGATE;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            else if (!(o instanceof UnaryExpression)) return false;
            final UnaryExpression<?> that = (UnaryExpression<?>) o;
            return Objects.equals(getInner(), that.getInner())
                && getOperator().equals(that.getOperator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(getInner(), getOperator());
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private NegateUtil() {}
}
