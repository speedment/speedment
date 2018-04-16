package com.speedment.runtime.compute.internal.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.UnaryExpression;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Utility class for creating expressions that give the positive square root of
 * the result from another expression.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class SqrtUtil {

    /**
     * Returns an expression that takes the result from the specified expression
     * and returns the positive square root of it.
     *
     * @param other  the expression to take the square root of
     * @param <T>    the input entity type
     * @return       expression for the square root
     */
    public static <T> ToDouble<T> sqrt(ToByte<T> other) {
        class ByteSqrt extends AbstractSqrt<T, ToByte<T>> {
            private ByteSqrt(ToByte<T> tToByte) {
                super(tToByte);
            }

            @Override
            public double applyAsDouble(T object) {
                return Math.sqrt(inner.applyAsByte(object));
            }
        }

        return new ByteSqrt(other);
    }

    /**
     * Returns an expression that takes the result from the specified expression
     * and returns the positive square root of it.
     *
     * @param other  the expression to take the square root of
     * @param <T>    the input entity type
     * @return       expression for the square root
     */
    public static <T> ToDouble<T> sqrt(ToShort<T> other) {
        class ShortSqrt extends AbstractSqrt<T, ToShort<T>> {
            private ShortSqrt(ToShort<T> tToShort) {
                super(tToShort);
            }

            @Override
            public double applyAsDouble(T object) {
                return Math.sqrt(inner.applyAsShort(object));
            }
        }

        return new ShortSqrt(other);
    }

    /**
     * Returns an expression that takes the result from the specified expression
     * and returns the positive square root of it.
     *
     * @param other  the expression to take the square root of
     * @param <T>    the input entity type
     * @return       expression for the square root
     */
    public static <T> ToDouble<T> sqrt(ToInt<T> other) {
        class IntSqrt extends AbstractSqrt<T, ToInt<T>> {
            private IntSqrt(ToInt<T> tToInt) {
                super(tToInt);
            }

            @Override
            public double applyAsDouble(T object) {
                return Math.sqrt(inner.applyAsInt(object));
            }
        }

        return new IntSqrt(other);
    }

    /**
     * Returns an expression that takes the result from the specified expression
     * and returns the positive square root of it.
     *
     * @param other  the expression to take the square root of
     * @param <T>    the input entity type
     * @return       expression for the square root
     */
    public static <T> ToDouble<T> sqrt(ToLong<T> other) {
        class LongSqrt extends AbstractSqrt<T, ToLong<T>> {
            private LongSqrt(ToLong<T> tToLong) {
                super(tToLong);
            }

            @Override
            public double applyAsDouble(T object) {
                return Math.sqrt(inner.applyAsLong(object));
            }
        }

        return new LongSqrt(other);
    }

    /**
     * Returns an expression that takes the result from the specified expression
     * and returns the positive square root of it.
     *
     * @param other  the expression to take the square root of
     * @param <T>    the input entity type
     * @return       expression for the square root
     */
    public static <T> ToDouble<T> sqrt(ToFloat<T> other) {
        class FloatSqrt extends AbstractSqrt<T, ToFloat<T>> {
            private FloatSqrt(ToFloat<T> tToFloat) {
                super(tToFloat);
            }

            @Override
            public double applyAsDouble(T object) {
                return Math.sqrt(inner.applyAsFloat(object));
            }
        }

        return new FloatSqrt(other);
    }

    /**
     * Returns an expression that takes the result from the specified expression
     * and returns the positive square root of it.
     *
     * @param other  the expression to take the square root of
     * @param <T>    the input entity type
     * @return       expression for the square root
     */
    public static <T> ToDouble<T> sqrt(ToDouble<T> other) {
        class DoubleSqrt extends AbstractSqrt<T, ToDouble<T>> {
            private DoubleSqrt(ToDouble<T> tToDouble) {
                super(tToDouble);
            }

            @Override
            public double applyAsDouble(T object) {
                return Math.sqrt(inner.applyAsDouble(object));
            }
        }

        return new DoubleSqrt(other);
    }

    private abstract static class AbstractSqrt<T, INNER extends Expression>
    implements UnaryExpression<INNER>, ToDouble<T> {
        final INNER inner;

        AbstractSqrt(INNER inner) {
            this.inner = requireNonNull(inner);
        }

        @Override
        public final INNER getInner() {
            return inner;
        }

        @Override
        public final Operator getOperator() {
            return Operator.SQRT;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UnaryExpression)) return false;
            final UnaryExpression<?> that = (UnaryExpression<?>) o;
            return Objects.equals(getInner(), that.getInner())
                && Objects.equals(getOperator(), that.getOperator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(getInner(), getOperator());
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private SqrtUtil() {}
}
