package com.speedment.runtime.compute.internal.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.UnaryExpression;

import java.math.BigDecimal;
import java.math.BigInteger;
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

    /**
     * Returns an expression that takes the result from the specified expression
     * and returns the positive square root of it.
     *
     * @param other  the expression to take the square root of
     * @param <T>    the input entity type
     * @return       expression for the square root
     */
    public static <T> ToBigDecimal<T> sqrt(ToBigDecimal<T> other) {
        class DoubleSqrt implements UnaryExpression<T, ToBigDecimal<T>>, ToBigDecimal<T> {
            private final ToBigDecimal<T> inner;

            private DoubleSqrt(ToBigDecimal<T> inner) {
                this.inner = inner;
            }

            @Override
            public ToBigDecimal<T> inner() {
                return inner;
            }

            @Override
            public Operator operator() {
                return Operator.SQRT;
            }

            @Override
            public BigDecimal apply(T object) {
                return SqrtUtil.sqrt(inner.apply(object));
            }
        }

        return new DoubleSqrt(other);
    }

    /**
     * Compute the square root of the given value. The computation is performed in double precision.
     * TODO - if this is the precision we want, then of course we should also return a double.
     *
     * @param x the value
     * @return the square root of x
     */
    private static BigDecimal sqrt(BigDecimal x) {
        return BigDecimal.valueOf(Math.sqrt(x.doubleValue()));
    }


    /**
     * Abstract base implementation of a {@link UnaryExpression} for a
     * square root-operation.
     *
     * @param <T>      the input entity type
     * @param <INNER>  the inner expression type to take the square root of
     */
    private abstract static class AbstractSqrt<T, INNER extends Expression<T>>
    implements UnaryExpression<T, INNER>, ToDouble<T> {

        final INNER inner;

        AbstractSqrt(INNER inner) {
            this.inner = requireNonNull(inner);
        }

        @Override
        public final INNER inner() {
            return inner;
        }

        @Override
        public final Operator operator() {
            return Operator.SQRT;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UnaryExpression)) return false;
            final UnaryExpression<?, ?> that = (UnaryExpression<?, ?>) o;
            return Objects.equals(inner(), that.inner())
                && Objects.equals(operator(), that.operator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner(), operator());
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private SqrtUtil() {}
}
