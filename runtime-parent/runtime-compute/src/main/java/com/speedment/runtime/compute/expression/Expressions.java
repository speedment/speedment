package com.speedment.runtime.compute.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.internal.ToByteNullableImpl;
import com.speedment.runtime.compute.internal.ToDoubleNullableImpl;
import com.speedment.runtime.compute.internal.ToLongNullableImpl;
import com.speedment.runtime.compute.internal.expression.*;

/**
 * Common mathematical expressions often used on Speedment entities.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class Expressions {

     /**
     * Utility classes should not be instantiated.
     */
    private Expressions() {throw new UnsupportedOperationException();}

    ////////////////////////////////////////////////////////////////////////////
    //                               Conversions                              //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates and returns an expression that converts the result of the
     * specified expression into a {@code double} by casting.
     *
     * @param expression  the initial expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> byteToDouble(ToByte<T> expression) {
        return expression.asDouble();
    }

    /**
     * Creates and returns an expression that converts the result of the
     * specified expression into a {@code double} by casting.
     *
     * @param expression  the initial expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> shortToDouble(ToShort<T> expression) {
        return expression.asDouble();
    }

    /**
     * Creates and returns an expression that converts the result of the
     * specified expression into a {@code double} by casting.
     *
     * @param expression  the initial expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> intToDouble(ToInt<T> expression) {
        return expression.asDouble();
    }

    /**
     * Creates and returns an expression that converts the result of the
     * specified expression into a {@code double} by casting.
     *
     * @param expression  the initial expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> longToDouble(ToLong<T> expression) {
        return expression.asDouble();
    }

    /**
     * Creates and returns an expression that converts the result of the
     * specified expression into a {@code double} by casting.
     *
     * @param expression  the initial expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> floatToDouble(ToFloat<T> expression) {
        return expression.asDouble();
    }

    /**
     * Creates and returns an expression that converts the result of the
     * specified expression into a {@code double} by casting. If the result of
     * the input is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the initial expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T> byteToDoubleNullable(ToByteNullable<T> expression) {
        return expression.mapToDoubleIfPresent(b -> b);
    }

    /**
     * Creates and returns an expression that converts the result of the
     * specified expression into a {@code double} by casting. If the result of
     * the input is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the initial expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T> shortToDoubleNullable(ToShortNullable<T> expression) {
        return expression.mapToDoubleIfPresent(b -> b);
    }

    /**
     * Creates and returns an expression that converts the result of the
     * specified expression into a {@code double} by casting. If the result of
     * the input is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the initial expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T> intToDoubleNullable(ToIntNullable<T> expression) {
        return expression.mapToDoubleIfPresent(b -> b);
    }

    /**
     * Creates and returns an expression that converts the result of the
     * specified expression into a {@code double} by casting. If the result of
     * the input is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the initial expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T> longToDoubleNullable(ToLongNullable<T> expression) {
        return expression.mapToDoubleIfPresent(b -> b);
    }

    /**
     * Creates and returns an expression that converts the result of the
     * specified expression into a {@code double} by casting. If the result of
     * the input is {@code null}, then the new expression will also return
     * {@code null}.
     *
     * @param expression  the initial expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T> floatToDoubleNullable(ToFloatNullable<T> expression) {
        return expression.mapToDoubleIfPresent(b -> b);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //                              Absolute Value                            //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that takes an expression and returns its absolute
     * (removing the negation sign if any).
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> abs(ToByte<T> expression) {
        return AbsUtil.abs(expression);
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
        return AbsUtil.abs(expression);
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
        return AbsUtil.abs(expression);
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
        return AbsUtil.abs(expression);
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
        return AbsUtil.abs(expression);
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
        return AbsUtil.abs(expression);
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
        return AbsUtil.absOrNull(expression);
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
        return AbsUtil.absOrNull(expression);
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
        return AbsUtil.absOrNull(expression);
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
        return AbsUtil.absOrNull(expression);
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
        return AbsUtil.absOrNull(expression);
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
        return AbsUtil.absOrNull(expression);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                               Get The Sign                             //
    ////////////////////////////////////////////////////////////////////////////

    private static final byte NEG_ONE = -1, ZERO = 0, ONE = 1;

    /**
     * Creates and returns an expression that returns {@code 1} if the result of
     * the input expression is positive, {@code -1} if the result of the input
     * expression is negative and {@code 0} if the result of the input
     * expression is {@code 0}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> sign(ToByte<T> expression) {
        return object -> {
            final byte v = expression.applyAsByte(object);
            return v < 0 ? NEG_ONE : (v > 0) ? ONE : ZERO;
        };
    }

    /**
     * Creates and returns an expression that returns {@code 1} if the result of
     * the input expression is positive, {@code -1} if the result of the input
     * expression is negative and {@code 0} if the result of the input
     * expression is {@code 0}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> sign(ToShort<T> expression) {
        return object -> {
            final short v = expression.applyAsShort(object);
            return v < 0 ? NEG_ONE : (v > 0) ? ONE : ZERO;
        };
    }

    /**
     * Creates and returns an expression that returns {@code 1} if the result of
     * the input expression is positive, {@code -1} if the result of the input
     * expression is negative and {@code 0} if the result of the input
     * expression is {@code 0}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> sign(ToInt<T> expression) {
        return object -> {
            final int v = expression.applyAsInt(object);
            return v < 0 ? NEG_ONE : (v > 0) ? ONE : ZERO;
        };
    }

    /**
     * Creates and returns an expression that returns {@code 1} if the result of
     * the input expression is positive, {@code -1} if the result of the input
     * expression is negative and {@code 0} if the result of the input
     * expression is {@code 0}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> sign(ToLong<T> expression) {
        return object -> {
            final long v = expression.applyAsLong(object);
            return v < 0 ? NEG_ONE : (v > 0) ? ONE : ZERO;
        };
    }

    /**
     * Creates and returns an expression that returns {@code 1} if the result of
     * the input expression is positive, {@code -1} if the result of the input
     * expression is negative and {@code 0} if the result of the input
     * expression is {@code 0}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> sign(ToFloat<T> expression) {
        return object -> {
            final int v = Float.compare(0, expression.applyAsFloat(object));
            return v < 0 ? NEG_ONE : (v > 0) ? ONE : ZERO;
        };
    }

    /**
     * Creates and returns an expression that returns {@code 1} if the result of
     * the input expression is positive, {@code -1} if the result of the input
     * expression is negative and {@code 0} if the result of the input
     * expression is {@code 0}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> sign(ToDouble<T> expression) {
        return object -> {
            final int v = Double.compare(0, expression.applyAsDouble(object));
            return v < 0 ? NEG_ONE : (v > 0) ? ONE : ZERO;
        };
    }

    /**
     * Creates and returns an expression that returns {@code 1} if the result of
     * the input expression is positive, {@code -1} if the result of the input
     * expression is negative and {@code 0} if the result of the input
     * expression is {@code 0}. If the result of the given expression is
     * {@code null}, then the result of this expression will also be
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByteNullable<T> signOrNull(ToByteNullable<T> expression) {
        return new ToByteNullableImpl<>(object -> {
            final byte v = expression.applyAsByte(object);
            return v < 0 ? NEG_ONE : (v > 0) ? ONE : ZERO;
        }, expression::isNull);
    }

    /**
     * Creates and returns an expression that returns {@code 1} if the result of
     * the input expression is positive, {@code -1} if the result of the input
     * expression is negative and {@code 0} if the result of the input
     * expression is {@code 0}. If the result of the given expression is
     * {@code null}, then the result of this expression will also be
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByteNullable<T> signOrNull(ToShortNullable<T> expression) {
        return new ToByteNullableImpl<>(object -> {
            final short v = expression.applyAsShort(object);
            return v < 0 ? NEG_ONE : (v > 0) ? ONE : ZERO;
        }, expression::isNull);
    }

    /**
     * Creates and returns an expression that returns {@code 1} if the result of
     * the input expression is positive, {@code -1} if the result of the input
     * expression is negative and {@code 0} if the result of the input
     * expression is {@code 0}. If the result of the given expression is
     * {@code null}, then the result of this expression will also be
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByteNullable<T> signOrNull(ToIntNullable<T> expression) {
        return new ToByteNullableImpl<>(object -> {
            final int v = expression.applyAsInt(object);
            return v < 0 ? NEG_ONE : (v > 0) ? ONE : ZERO;
        }, expression::isNull);
    }

    /**
     * Creates and returns an expression that returns {@code 1} if the result of
     * the input expression is positive, {@code -1} if the result of the input
     * expression is negative and {@code 0} if the result of the input
     * expression is {@code 0}. If the result of the given expression is
     * {@code null}, then the result of this expression will also be
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByteNullable<T> signOrNull(ToLongNullable<T> expression) {
        return new ToByteNullableImpl<>(object -> {
            final long v = expression.applyAsLong(object);
            return v < 0 ? NEG_ONE : (v > 0) ? ONE : ZERO;
        }, expression::isNull);
    }

    /**
     * Creates and returns an expression that returns {@code 1} if the result of
     * the input expression is positive, {@code -1} if the result of the input
     * expression is negative and {@code 0} if the result of the input
     * expression is {@code 0}. If the result of the given expression is
     * {@code null}, then the result of this expression will also be
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByteNullable<T> signOrNull(ToFloatNullable<T> expression) {
        return new ToByteNullableImpl<>(object -> {
            final int v = Float.compare(0, expression.applyAsFloat(object));
            return v < 0 ? NEG_ONE : (v > 0) ? ONE : ZERO;
        }, expression::isNull);
    }

    /**
     * Creates and returns an expression that returns {@code 1} if the result of
     * the input expression is positive, {@code -1} if the result of the input
     * expression is negative and {@code 0} if the result of the input
     * expression is {@code 0}. If the result of the given expression is
     * {@code null}, then the result of this expression will also be
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByteNullable<T> signOrNull(ToDoubleNullable<T> expression) {
        return new ToByteNullableImpl<>(object -> {
            final int v = Double.compare(0, expression.applyAsDouble(object));
            return v < 0 ? NEG_ONE : (v > 0) ? ONE : ZERO;
        }, expression::isNull);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                               Square Root                              //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates and returns an expression that returns the square root of the
     * result from the input expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> sqrt(ToByte<T> expression) {
        return object -> Math.sqrt(expression.applyAsByte(object));
    }

    /**
     * Creates and returns an expression that returns the square root of the
     * result from the input expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> sqrt(ToShort<T> expression) {
        return object -> Math.sqrt(expression.applyAsShort(object));
    }

    /**
     * Creates and returns an expression that returns the square root of the
     * result from the input expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> sqrt(ToInt<T> expression) {
        return object -> Math.sqrt(expression.applyAsInt(object));
    }

    /**
     * Creates and returns an expression that returns the square root of the
     * result from the input expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> sqrt(ToLong<T> expression) {
        return object -> Math.sqrt(expression.applyAsLong(object));
    }

    /**
     * Creates and returns an expression that returns the square root of the
     * result from the input expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> sqrt(ToFloat<T> expression) {
        return object -> Math.sqrt(expression.applyAsFloat(object));
    }

    /**
     * Creates and returns an expression that returns the square root of the
     * result from the input expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> sqrt(ToDouble<T> expression) {
        return object -> Math.sqrt(expression.applyAsDouble(object));
    }

    /**
     * Creates and returns an expression that returns the square root of the
     * result from the input expression. If the result of the input expression
     * is {@code null}, then the result of the new expression will also be
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T> sqrtOrNull(ToByteNullable<T> expression) {
        return new ToDoubleNullableImpl<>(
            object -> Math.sqrt(expression.applyAsByte(object)),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that returns the square root of the
     * result from the input expression. If the result of the input expression
     * is {@code null}, then the result of the new expression will also be
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T> sqrtOrNull(ToShortNullable<T> expression) {
        return new ToDoubleNullableImpl<>(
            object -> Math.sqrt(expression.applyAsShort(object)),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that returns the square root of the
     * result from the input expression. If the result of the input expression
     * is {@code null}, then the result of the new expression will also be
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T> sqrtOrNull(ToIntNullable<T> expression) {
        return new ToDoubleNullableImpl<>(
            object -> Math.sqrt(expression.applyAsInt(object)),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that returns the square root of the
     * result from the input expression. If the result of the input expression
     * is {@code null}, then the result of the new expression will also be
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T> sqrtOrNull(ToLongNullable<T> expression) {
        return new ToDoubleNullableImpl<>(
            object -> Math.sqrt(expression.applyAsLong(object)),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that returns the square root of the
     * result from the input expression. If the result of the input expression
     * is {@code null}, then the result of the new expression will also be
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T> sqrtOrNull(ToFloatNullable<T> expression) {
        return new ToDoubleNullableImpl<>(
            object -> Math.sqrt(expression.applyAsFloat(object)),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that returns the square root of the
     * result from the input expression. If the result of the input expression
     * is {@code null}, then the result of the new expression will also be
     * {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T> sqrtOrNull(ToDoubleNullable<T> expression) {
        return new ToDoubleNullableImpl<>(
            object -> Math.sqrt(expression.applyAsDouble(object)),
            expression::isNull
        );
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                 Power                                  //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> pow(ToByte<T> expression, int power) {
        switch (power) {
            case 0 : return object -> 1;
            case 1 : return expression.asLong();
            case 2 : return object -> {
                final byte v = expression.applyAsByte(object);
                return v * v;
            };
            case 3 : return object -> {
                final byte v = expression.applyAsByte(object);
                return v * v * v;
            };
        }

        return object -> (long) Math.pow(expression.applyAsByte(object), power);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToByte<T> expression, double power) {
        return object -> Math.pow(expression.applyAsByte(object), power);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> pow(ToByte<T> expression, ToByte<T> power) {
        return object -> {
            final byte v = expression.applyAsByte(object);
            final byte p = power.applyAsByte(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        };
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> pow(ToByte<T> expression, ToInt<T> power) {
        return object -> {
            final byte v = expression.applyAsByte(object);
            final int p = power.applyAsInt(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        };
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToByte<T> expression, ToDouble<T> power) {
        return object -> Math.pow(
            expression.applyAsByte(object),
            power.applyAsDouble(object)
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLongNullable<T>
    powOrNull(ToByteNullable<T> expression, int power) {
        switch (power) {
            case 0 : return new ToLongNullableImpl<>(
                object -> 1, object -> false);
            case 1 : return new ToLongNullableImpl<>(
                expression::applyAsByte,
                expression::isNull);
            case 2 : return new ToLongNullableImpl<>(object -> {
                final byte v = expression.applyAsByte(object);
                return v * v;
            }, expression::isNull);
            case 3 : return new ToLongNullableImpl<>(object -> {
                final byte v = expression.applyAsByte(object);
                return v * v * v;
            }, expression::isNull);
        }

        return new ToLongNullableImpl<>(
            object -> (long) Math.pow(expression.applyAsByte(object), power),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToByteNullable<T> expression, double power) {
        return new ToDoubleNullableImpl<>(
            object -> Math.pow(expression.applyAsByte(object), power),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLongNullable<T>
    powOrNull(ToByteNullable<T> expression, ToByte<T> power) {
        return new ToLongNullableImpl<>(object -> {
            final byte v = expression.applyAsByte(object);
            final byte p = power.applyAsByte(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        }, expression::isNull);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLongNullable<T>
    powOrNull(ToByteNullable<T> expression, ToInt<T> power) {
        return new ToLongNullableImpl<>(object -> {
            final byte v = expression.applyAsByte(object);
            final int p = power.applyAsInt(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        }, expression::isNull);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToByteNullable<T> expression, ToDouble<T> power) {
        return new ToDoubleNullableImpl<>(
            object -> Math.pow(
                expression.applyAsByte(object),
                power.applyAsDouble(object)
            ), expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> pow(ToShort<T> expression, int power) {
        switch (power) {
            case 0 : return object -> 1;
            case 1 : return expression.asLong();
            case 2 : return object -> {
                final short v = expression.applyAsShort(object);
                return v * v;
            };
            case 3 : return object -> {
                final short v = expression.applyAsShort(object);
                return v * v * v;
            };
        }

        return object -> (long) Math.pow(
            expression.applyAsShort(object),
            power
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToShort<T> expression, double power) {
        return object -> Math.pow(expression.applyAsShort(object), power);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T>
    pow(ToShort<T> expression, ToByte<T> power) {
        return object -> {
            final short v = expression.applyAsShort(object);
            final byte p = power.applyAsByte(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        };
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T>
    pow(ToShort<T> expression, ToInt<T> power) {
        return object -> {
            final short v = expression.applyAsShort(object);
            final int p = power.applyAsInt(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        };
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T>
    pow(ToShort<T> expression, ToDouble<T> power) {
        return object -> Math.pow(
            expression.applyAsShort(object),
            power.applyAsDouble(object)
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLongNullable<T>
    powOrNull(ToShortNullable<T> expression, int power) {
        switch (power) {
            case 0 : return new ToLongNullableImpl<>(
                object -> 1, object -> false);
            case 1 : return new ToLongNullableImpl<>(
                expression::applyAsShort,
                expression::isNull);
            case 2 : return new ToLongNullableImpl<>(object -> {
                final short v = expression.applyAsShort(object);
                return v * v;
            }, expression::isNull);
            case 3 : return new ToLongNullableImpl<>(object -> {
                final short v = expression.applyAsShort(object);
                return v * v * v;
            }, expression::isNull);
        }

        return new ToLongNullableImpl<>(
            object -> (long) Math.pow(expression.applyAsShort(object), power),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToShortNullable<T> expression, double power) {
        return new ToDoubleNullableImpl<>(
            object -> Math.pow(expression.applyAsShort(object), power),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLongNullable<T>
    powOrNull(ToShortNullable<T> expression, ToByte<T> power) {
        return new ToLongNullableImpl<>(object -> {
            final short v = expression.applyAsShort(object);
            final byte p = power.applyAsByte(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        }, expression::isNull);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLongNullable<T>
    powOrNull(ToShortNullable<T> expression, ToInt<T> power) {
        return new ToLongNullableImpl<>(object -> {
            final short v = expression.applyAsShort(object);
            final int p = power.applyAsInt(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        }, expression::isNull);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToShortNullable<T> expression, ToDouble<T> power) {
        return new ToDoubleNullableImpl<>(
            object -> Math.pow(
                expression.applyAsShort(object),
                power.applyAsDouble(object)
            ), expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> pow(ToInt<T> expression, int power) {
        switch (power) {
            case 0 : return object -> 1;
            case 1 : return expression.asLong();
            case 2 : return object -> {
                final int v = expression.applyAsInt(object);
                return v * v;
            };
            case 3 : return object -> {
                final int v = expression.applyAsInt(object);
                return v * v * v;
            };
        }

        return object -> (long) Math.pow(expression.applyAsInt(object), power);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToInt<T> expression, double power) {
        return object -> Math.pow(expression.applyAsInt(object), power);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> pow(ToInt<T> expression, ToByte<T> power) {
        return object -> {
            final int v = expression.applyAsInt(object);
            final byte p = power.applyAsByte(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        };
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> pow(ToInt<T> expression, ToInt<T> power) {
        return object -> {
            final int v = expression.applyAsInt(object);
            final int p = power.applyAsInt(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        };
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToInt<T> expression, ToDouble<T> power) {
        return object -> Math.pow(
            expression.applyAsInt(object),
            power.applyAsDouble(object)
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLongNullable<T>
    powOrNull(ToIntNullable<T> expression, int power) {
        switch (power) {
            case 0 : return new ToLongNullableImpl<>(
                object -> 1, object -> false);
            case 1 : return new ToLongNullableImpl<>(
                expression::applyAsInt,
                expression::isNull);
            case 2 : return new ToLongNullableImpl<>(object -> {
                final int v = expression.applyAsInt(object);
                return v * v;
            }, expression::isNull);
            case 3 : return new ToLongNullableImpl<>(object -> {
                final int v = expression.applyAsInt(object);
                return v * v * v;
            }, expression::isNull);
        }

        return new ToLongNullableImpl<>(
            object -> (long) Math.pow(expression.applyAsInt(object), power),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToIntNullable<T> expression, double power) {
        return new ToDoubleNullableImpl<>(
            object -> Math.pow(expression.applyAsInt(object), power),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLongNullable<T>
    powOrNull(ToIntNullable<T> expression, ToByte<T> power) {
        return new ToLongNullableImpl<>(object -> {
            final int v = expression.applyAsInt(object);
            final byte p = power.applyAsByte(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        }, expression::isNull);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLongNullable<T>
    powOrNull(ToIntNullable<T> expression, ToInt<T> power) {
        return new ToLongNullableImpl<>(object -> {
            final int v = expression.applyAsInt(object);
            final int p = power.applyAsInt(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        }, expression::isNull);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToIntNullable<T> expression, ToDouble<T> power) {
        return new ToDoubleNullableImpl<>(
            object -> Math.pow(
                expression.applyAsInt(object),
                power.applyAsDouble(object)
            ), expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> pow(ToLong<T> expression, int power) {
        switch (power) {
            case 0 : return object -> 1;
            case 1 : return expression.asLong();
            case 2 : return object -> {
                final long v = expression.applyAsLong(object);
                return v * v;
            };
            case 3 : return object -> {
                final long v = expression.applyAsLong(object);
                return v * v * v;
            };
        }

        return object -> (long) Math.pow(expression.applyAsLong(object), power);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToLong<T> expression, double power) {
        return object -> Math.pow(expression.applyAsLong(object), power);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> pow(ToLong<T> expression, ToByte<T> power) {
        return object -> {
            final long v = expression.applyAsLong(object);
            final byte p = power.applyAsByte(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        };
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> pow(ToLong<T> expression, ToInt<T> power) {
        return object -> {
            final long v = expression.applyAsLong(object);
            final int p = power.applyAsInt(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        };
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToLong<T> expression, ToDouble<T> power) {
        return object -> Math.pow(
            expression.applyAsLong(object),
            power.applyAsDouble(object)
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLongNullable<T>
    powOrNull(ToLongNullable<T> expression, int power) {
        switch (power) {
            case 0 : return new ToLongNullableImpl<>(
                object -> 1, object -> false);
            case 1 : return new ToLongNullableImpl<>(
                expression::applyAsLong,
                expression::isNull);
            case 2 : return new ToLongNullableImpl<>(object -> {
                final long v = expression.applyAsLong(object);
                return v * v;
            }, expression::isNull);
            case 3 : return new ToLongNullableImpl<>(object -> {
                final long v = expression.applyAsLong(object);
                return v * v * v;
            }, expression::isNull);
        }

        return new ToLongNullableImpl<>(
            object -> (long) Math.pow(expression.applyAsLong(object), power),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToLongNullable<T> expression, double power) {
        return new ToDoubleNullableImpl<>(
            object -> Math.pow(expression.applyAsLong(object), power),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLongNullable<T>
    powOrNull(ToLongNullable<T> expression, ToByte<T> power) {
        return new ToLongNullableImpl<>(object -> {
            final long v = expression.applyAsLong(object);
            final byte p = power.applyAsByte(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        }, expression::isNull);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLongNullable<T>
    powOrNull(ToLongNullable<T> expression, ToInt<T> power) {
        return new ToLongNullableImpl<>(object -> {
            final long v = expression.applyAsLong(object);
            final int p = power.applyAsInt(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return (long) Math.pow(v, p);
        }, expression::isNull);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToLongNullable<T> expression, ToDouble<T> power) {
        return new ToDoubleNullableImpl<>(
            object -> Math.pow(
                expression.applyAsLong(object),
                power.applyAsDouble(object)
            ), expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToFloat<T> expression, int power) {
        switch (power) {
            case 0 : return object -> 1d;
            case 1 : return expression.asDouble();
            case 2 : return object -> {
                final double v = expression.applyAsFloat(object);
                return v * v;
            };
            case 3 : return object -> {
                final double v = expression.applyAsFloat(object);
                return v * v * v;
            };
        }

        return object -> Math.pow(expression.applyAsFloat(object), power);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToFloat<T> expression, double power) {
        return object -> Math.pow(expression.applyAsFloat(object), power);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToFloat<T> expression, ToByte<T> power) {
        return object -> {
            final double v = expression.applyAsFloat(object);
            final byte p = power.applyAsByte(object);
            switch (p) {
                case 0 : return 1d;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return Math.pow(v, p);
        };
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToFloat<T> expression, ToInt<T> power) {
        return object -> {
            final double v = expression.applyAsFloat(object);
            final int p = power.applyAsInt(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return Math.pow(v, p);
        };
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T>
    pow(ToFloat<T> expression, ToDouble<T> power) {
        return object -> Math.pow(
            expression.applyAsFloat(object),
            power.applyAsDouble(object)
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToFloatNullable<T> expression, int power) {
        switch (power) {
            case 0 : return new ToDoubleNullableImpl<>(
                object -> 1d, object -> false);
            case 1 : return new ToDoubleNullableImpl<>(
                expression::applyAsFloat,
                expression::isNull);
            case 2 : return new ToDoubleNullableImpl<>(object -> {
                final double v = expression.applyAsFloat(object);
                return v * v;
            }, expression::isNull);
            case 3 : return new ToDoubleNullableImpl<>(object -> {
                final double v = expression.applyAsFloat(object);
                return v * v * v;
            }, expression::isNull);
        }

        return new ToDoubleNullableImpl<>(
            object -> Math.pow(expression.applyAsFloat(object), power),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToFloatNullable<T> expression, double power) {
        return new ToDoubleNullableImpl<>(
            object -> Math.pow(expression.applyAsFloat(object), power),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToFloatNullable<T> expression, ToInt<T> power) {
        return new ToDoubleNullableImpl<>(object -> {
            final double v = expression.applyAsFloat(object);
            final int p = power.applyAsInt(object);
            switch (p) {
                case 0 : return 1d;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return Math.pow(v, p);
        }, expression::isNull);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToFloatNullable<T> expression, ToDouble<T> power) {
        return new ToDoubleNullableImpl<>(
            object -> Math.pow(
                expression.applyAsFloat(object),
                power.applyAsDouble(object)
            ), expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToDouble<T> expression, int power) {
        switch (power) {
            case 0 : return object -> 1d;
            case 1 : return expression.asDouble();
            case 2 : return object -> {
                final double v = expression.applyAsDouble(object);
                return v * v;
            };
            case 3 : return object -> {
                final double v = expression.applyAsDouble(object);
                return v * v * v;
            };
        }

        return object -> Math.pow(expression.applyAsDouble(object), power);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToDouble<T> expression, double power) {
        return object -> Math.pow(expression.applyAsDouble(object), power);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T>
    pow(ToDouble<T> expression, ToByte<T> power) {
        return object -> {
            final double v = expression.applyAsDouble(object);
            final byte p = power.applyAsByte(object);
            switch (p) {
                case 0 : return 1d;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return Math.pow(v, p);
        };
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T>
    pow(ToDouble<T> expression, ToInt<T> power) {
        return object -> {
            final double v = expression.applyAsDouble(object);
            final int p = power.applyAsInt(object);
            switch (p) {
                case 0 : return 1;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return Math.pow(v, p);
        };
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T>
    pow(ToDouble<T> expression, ToDouble<T> power) {
        return object -> Math.pow(
            expression.applyAsDouble(object),
            power.applyAsDouble(object)
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToDoubleNullable<T> expression, int power) {
        switch (power) {
            case 0 : return new ToDoubleNullableImpl<>(
                object -> 1d,
                object -> false);
            case 1 : return new ToDoubleNullableImpl<>(
                expression::applyAsDouble,
                expression::isNull);
            case 2 : return new ToDoubleNullableImpl<>(object -> {
                final double v = expression.applyAsDouble(object);
                return v * v;
            }, expression::isNull);
            case 3 : return new ToDoubleNullableImpl<>(object -> {
                final double v = expression.applyAsDouble(object);
                return v * v * v;
            }, expression::isNull);
        }

        return new ToDoubleNullableImpl<>(
            object -> Math.pow(expression.applyAsDouble(object), power),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself {@code power} times.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToDoubleNullable<T> expression, double power) {
        return new ToDoubleNullableImpl<>(
            object -> Math.pow(expression.applyAsDouble(object), power),
            expression::isNull
        );
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToDoubleNullable<T> expression, ToInt<T> power) {
        return new ToDoubleNullableImpl<>(object -> {
            final double v = expression.applyAsDouble(object);
            final int p = power.applyAsInt(object);
            switch (p) {
                case 0 : return 1d;
                case 1 : return v;
                case 2 : return v * v;
                case 3 : return v * v * v;
            }
            return Math.pow(v, p);
        }, expression::isNull);
    }

    /**
     * Creates and returns an expression that takes the result of an input
     * expression and multiplies it with itself as many times as the result of
     * applying {@code power} to the input.
     *
     * @param expression  the input expression
     * @param power       the power
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T>
    powOrNull(ToDoubleNullable<T> expression, ToDouble<T> power) {
        return new ToDoubleNullableImpl<>(
            object -> Math.pow(
                expression.applyAsDouble(object),
                power.applyAsDouble(object)
            ), expression::isNull
        );
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                 Negate                                 //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> negate(ToByte<T> expression) {
        return NegateUtil.negate(expression);
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
        return NegateUtil.negate(expression);
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
        return NegateUtil.negate(expression);
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
        return NegateUtil.negate(expression);
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
        return NegateUtil.negate(expression);
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
        return NegateUtil.negate(expression);
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
        return NegateUtil.negate(expression);
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression. If the specified expression results in
     * {@code null}, then the new expression will also return {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByteNullable<T> negateOrNull(ToByteNullable<T> expression) {
        return NegateUtil.negateOrNull(expression);
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression. If the specified expression results in
     * {@code null}, then the new expression will also return {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToShortNullable<T> negateOrNull(ToShortNullable<T> expression) {
        return NegateUtil.negateOrNull(expression);
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression. If the specified expression results in
     * {@code null}, then the new expression will also return {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToIntNullable<T> negateOrNull(ToIntNullable<T> expression) {
        return NegateUtil.negateOrNull(expression);
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression. If the specified expression results in
     * {@code null}, then the new expression will also return {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLongNullable<T> negateOrNull(ToLongNullable<T> expression) {
        return NegateUtil.negateOrNull(expression);
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression. If the specified expression results in
     * {@code null}, then the new expression will also return {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToFloatNullable<T> negateOrNull(ToFloatNullable<T> expression) {
        return NegateUtil.negateOrNull(expression);
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression. If the specified expression results in
     * {@code null}, then the new expression will also return {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDoubleNullable<T> negateOrNull(ToDoubleNullable<T> expression) {
        return NegateUtil.negateOrNull(expression);
    }

    /**
     * Creates and returns an expression that will compute the negative result
     * of the specified expression. If the specified expression results in
     * {@code null}, then the new expression will also return {@code null}.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToBooleanNullable<T> negateOrNull(ToBooleanNullable<T> expression) {
        return NegateUtil.negateOrNull(expression);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                  Plus                                  //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToShort<T> plus(ToByte<T> first, byte second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> plus(ToByte<T> first, int second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> plus(ToByte<T> first, long second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and add them together.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToShort<T> plus(ToByte<T> first, ToByte<T> second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> plus(ToShort<T> first, byte second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> plus(ToShort<T> first, int second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> plus(ToShort<T> first, long second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and add them together.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToShort<T> plus(ToShort<T> first, ToShort<T> second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> plus(ToInt<T> first, byte second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> plus(ToInt<T> first, int second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> plus(ToInt<T> first, long second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and add them together.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> plus(ToInt<T> first, ToByte<T> second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and add them together.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> plus(ToInt<T> first, ToInt<T> second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> plus(ToLong<T> first, byte second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> plus(ToLong<T> first, int second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> plus(ToLong<T> first, long second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and add them together.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> plus(ToLong<T> first, ToInt<T> second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and add them together.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> plus(ToLong<T> first, ToLong<T> second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> plus(ToFloat<T> first, int second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> plus(ToFloat<T> first, long second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> plus(ToFloat<T> first, float second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and add them together.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> plus(ToFloat<T> first, ToInt<T> second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and add them together.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> plus(ToFloat<T> first, ToLong<T> second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and add them together.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> plus(ToFloat<T> first, ToFloat<T> second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> plus(ToDouble<T> first, int second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> plus(ToDouble<T> first, long second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> plus(ToDouble<T> first, double second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and add them together.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> plus(ToDouble<T> first, ToInt<T> second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and add them together.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> plus(ToDouble<T> first, ToLong<T> second) {
        return PlusUtil.plus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and add them together.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> plus(ToDouble<T> first, ToDouble<T> second) {
        return PlusUtil.plus(first, second);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //                                Minus                                   //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToShort<T> minus(ToByte<T> first, byte second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> minus(ToByte<T> first, int second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> minus(ToByte<T> first, long second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the difference.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToShort<T> minus(ToByte<T> first, ToByte<T> second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> minus(ToShort<T> first, byte second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> minus(ToShort<T> first, int second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> minus(ToShort<T> first, long second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the difference.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToShort<T> minus(ToShort<T> first, ToShort<T> second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> minus(ToInt<T> first, byte second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> minus(ToInt<T> first, int second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> minus(ToInt<T> first, long second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the difference.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> minus(ToInt<T> first, ToByte<T> second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the difference.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> minus(ToInt<T> first, ToInt<T> second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> minus(ToLong<T> first, byte second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> minus(ToLong<T> first, int second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> minus(ToLong<T> first, long second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the difference.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> minus(ToLong<T> first, ToInt<T> second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the difference.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> minus(ToLong<T> first, ToLong<T> second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> minus(ToFloat<T> first, int second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> minus(ToFloat<T> first, long second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> minus(ToFloat<T> first, float second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the difference.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> minus(ToFloat<T> first, ToInt<T> second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the difference.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> minus(ToFloat<T> first, ToLong<T> second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the difference.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> minus(ToFloat<T> first, ToFloat<T> second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> minus(ToDouble<T> first, int second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> minus(ToDouble<T> first, long second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and subtracts a constant from it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> minus(ToDouble<T> first, double second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the difference.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> minus(ToDouble<T> first, ToInt<T> second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the difference.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> minus(ToDouble<T> first, ToLong<T> second) {
        return MinusUtil.minus(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the difference.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> minus(ToDouble<T> first, ToDouble<T> second) {
        return MinusUtil.minus(first, second);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                               Multiply                                 //
    ////////////////////////////////////////////////////////////////////////////

    // TODO: Some multiplications could be performed with shift operations instead

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToByte<T> first, byte second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToByte<T> first, int second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToByte<T> first, long second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToByte<T> first, ToByte<T> second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToShort<T> first, byte second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToShort<T> first, int second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToShort<T> first, long second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToShort<T> first, ToShort<T> second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToInt<T> first, byte second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToInt<T> first, int second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToInt<T> first, long second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToInt<T> first, ToByte<T> second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToInt<T> first, ToInt<T> second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToLong<T> first, byte second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToLong<T> first, int second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToLong<T> first, long second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToLong<T> first, ToInt<T> second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToLong<T> first, ToLong<T> second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> multiply(ToFloat<T> first, int second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToFloat<T> first, long second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> multiply(ToFloat<T> first, float second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> multiply(ToFloat<T> first, ToInt<T> second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToFloat<T> first, ToLong<T> second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> multiply(ToFloat<T> first, ToFloat<T> second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToDouble<T> first, int second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToDouble<T> first, long second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToDouble<T> first, double second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToDouble<T> first, ToInt<T> second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToDouble<T> first, ToLong<T> second) {
        return MultiplyUtil.multiply(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and computes the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToDouble<T> first, ToDouble<T> second) {
        return MultiplyUtil.multiply(first, second);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                               Division                                 //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> divideFloor(ToByte<T> expression, byte divisor) {
        switch (divisor) {
            case -1  : return negate(expression);
            case 0   : throw new IllegalArgumentException("Divisor can't be 0!");
            case 1   : return expression;
            case 2   : return expression.map(v -> (byte) (v >> 1));
            case 4   : return expression.map(v -> (byte) (v >> 2));
            case 8   : return expression.map(v -> (byte) (v >> 3));
            case 16  : return expression.map(v -> (byte) (v >> 4));
            case 32  : return expression.map(v -> (byte) (v >> 5));
            case 64  : return expression.map(v -> (byte) (v >> 6));
        }
        return expression.map(v -> (byte) (v / divisor));
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> divideFloor(ToByte<T> expression, int divisor) {
        switch (divisor) {
            case -1  : return negate(expression);
            case 0   : throw new IllegalArgumentException("Divisor can't be 0!");
            case 1   : return expression;
            case 2   : return expression.map(v -> (byte) (v >> 1));
            case 4   : return expression.map(v -> (byte) (v >> 2));
            case 8   : return expression.map(v -> (byte) (v >> 3));
            case 16  : return expression.map(v -> (byte) (v >> 4));
            case 32  : return expression.map(v -> (byte) (v >> 5));
            case 64  : return expression.map(v -> (byte) (v >> 6));
            case 128 : return expression.map(v -> (byte) (v >> 7));
        }
        return expression.map(v -> (byte) (v / divisor));
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> divideFloor(ToByte<T> expression, long divisor) {
        if (divisor >= -1 && divisor <= Integer.MAX_VALUE) {
            return divideFloor(expression, (int) divisor);
        } else {
            return expression.map(v -> (byte) (v / divisor));
        }
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> divideFloor(ToByte<T> expression, ToByte<T> divisor) {
        return t -> {
            final byte d = divisor.applyAsByte(t);
            switch (d) {
                case -1: return (byte) -expression.applyAsByte(t);
                case 0:  throw new IllegalArgumentException("Divisor can't be 0!");
                case 1:  return expression.applyAsByte(t);
                case 2:  return (byte) (expression.applyAsByte(t) >> 1);
                case 4:  return (byte) (expression.applyAsByte(t) >> 2);
                case 8:  return (byte) (expression.applyAsByte(t) >> 3);
                case 16: return (byte) (expression.applyAsByte(t) >> 4);
                case 32: return (byte) (expression.applyAsByte(t) >> 5);
                case 64: return (byte) (expression.applyAsByte(t) >> 6);
            }
            return (byte) (expression.applyAsByte(t) / d);
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> divideFloor(ToByte<T> expression, ToInt<T> divisor) {
        return t -> {
            final int d = divisor.applyAsInt(t);
            switch (d) {
                case -1:  return (byte) -expression.applyAsByte(t);
                case 0:   throw new IllegalArgumentException("Divisor can't be 0!");
                case 1:   return expression.applyAsByte(t);
                case 2:   return (byte) (expression.applyAsByte(t) >> 1);
                case 4:   return (byte) (expression.applyAsByte(t) >> 2);
                case 8:   return (byte) (expression.applyAsByte(t) >> 3);
                case 16:  return (byte) (expression.applyAsByte(t) >> 4);
                case 32:  return (byte) (expression.applyAsByte(t) >> 5);
                case 64:  return (byte) (expression.applyAsByte(t) >> 6);
                case 128: return (byte) (expression.applyAsByte(t) >> 7);
            }
            return (byte) (expression.applyAsByte(t) / d);
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> divideFloor(ToByte<T> expression, ToLong<T> divisor) {
        return t -> {
            final long d = divisor.applyAsLong(t);
            if (d <= Byte.MAX_VALUE) {
                if (d >= -1) {
                    switch ((int) d) {
                        case -1:  return (byte) -expression.applyAsByte(t);
                        case 0:   throw new IllegalArgumentException("Divisor can't be 0!");
                        case 1:   return expression.applyAsByte(t);
                        case 2:   return (byte) (expression.applyAsByte(t) >> 1);
                        case 4:   return (byte) (expression.applyAsByte(t) >> 2);
                        case 8:   return (byte) (expression.applyAsByte(t) >> 3);
                        case 16:  return (byte) (expression.applyAsByte(t) >> 4);
                        case 32:  return (byte) (expression.applyAsByte(t) >> 5);
                        case 64:  return (byte) (expression.applyAsByte(t) >> 6);
                        case 128: return (byte) (expression.applyAsByte(t) >> 7);
                    }
                }

                return (byte) (expression.applyAsByte(t) / d);
            } else return (byte) 0;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToByte<T> expression, byte divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.mapToDouble(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToByte<T> expression, int divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.mapToDouble(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToByte<T> expression, long divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.mapToDouble(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToByte<T> expression, double divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        return expression.mapToDouble(v -> v / divisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToByte<T> expression, ToByte<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsByte(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsByte(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToByte<T> expression, ToInt<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsInt(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsByte(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToByte<T> expression, ToLong<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsLong(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsByte(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToByte<T> expression, ToDouble<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsDouble(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsByte(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToShort<T> divideFloor(ToShort<T> expression, byte divisor) {
        switch (divisor) {
            case -1  : return negate(expression);
            case 0   : throw new IllegalArgumentException("Divisor can't be 0!");
            case 1   : return expression;
            case 2   : return expression.map(v -> (short) (v >> 1));
            case 4   : return expression.map(v -> (short) (v >> 2));
            case 8   : return expression.map(v -> (short) (v >> 3));
            case 16  : return expression.map(v -> (short) (v >> 4));
            case 32  : return expression.map(v -> (short) (v >> 5));
            case 64  : return expression.map(v -> (short) (v >> 6));
        }
        return expression.map(v -> (short) (v / divisor));
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToShort<T> divideFloor(ToShort<T> expression, int divisor) {
        switch (divisor) {
            case -1  : return negate(expression);
            case 0   : throw new IllegalArgumentException("Divisor can't be 0!");
            case 1   : return expression;
            case 2   : return expression.map(v -> (short) (v >> 1));
            case 4   : return expression.map(v -> (short) (v >> 2));
            case 8   : return expression.map(v -> (short) (v >> 3));
            case 16  : return expression.map(v -> (short) (v >> 4));
            case 32  : return expression.map(v -> (short) (v >> 5));
            case 64  : return expression.map(v -> (short) (v >> 6));
            case 128 : return expression.map(v -> (short) (v >> 7));
        }
        return expression.map(v -> (short) (v / divisor));
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToShort<T> divideFloor(ToShort<T> expression, long divisor) {
        if (divisor >= -1 && divisor <= Integer.MAX_VALUE) {
            return divideFloor(expression, (int) divisor);
        } else {
            return expression.map(v -> (short) (v / divisor));
        }
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToShort<T> divideFloor(ToShort<T> expression, ToByte<T> divisor) {
        return t -> {
            final byte d = divisor.applyAsByte(t);
            switch (d) {
                case -1: return (short) -expression.applyAsShort(t);
                case 0:  throw new IllegalArgumentException("Divisor can't be 0!");
                case 1:  return expression.applyAsShort(t);
                case 2:  return (short) (expression.applyAsShort(t) >> 1);
                case 4:  return (short) (expression.applyAsShort(t) >> 2);
                case 8:  return (short) (expression.applyAsShort(t) >> 3);
                case 16: return (short) (expression.applyAsShort(t) >> 4);
                case 32: return (short) (expression.applyAsShort(t) >> 5);
                case 64: return (short) (expression.applyAsShort(t) >> 6);
            }
            return (short) (expression.applyAsShort(t) / d);
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToShort<T> divideFloor(ToShort<T> expression, ToInt<T> divisor) {
        return t -> {
            final int d = divisor.applyAsInt(t);
            switch (d) {
                case -1:  return (short) -expression.applyAsShort(t);
                case 0:   throw new IllegalArgumentException("Divisor can't be 0!");
                case 1:   return expression.applyAsShort(t);
                case 2:   return (short) (expression.applyAsShort(t) >> 1);
                case 4:   return (short) (expression.applyAsShort(t) >> 2);
                case 8:   return (short) (expression.applyAsShort(t) >> 3);
                case 16:  return (short) (expression.applyAsShort(t) >> 4);
                case 32:  return (short) (expression.applyAsShort(t) >> 5);
                case 64:  return (short) (expression.applyAsShort(t) >> 6);
                case 128: return (short) (expression.applyAsShort(t) >> 7);
            }
            return (short) (expression.applyAsShort(t) / d);
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToShort<T> divideFloor(ToShort<T> expression, ToLong<T> divisor) {
        return t -> {
            final long d = divisor.applyAsLong(t);
            if (d <= Short.MAX_VALUE) {
                if (d >= -1) {
                    switch ((int) d) {
                        case -1:  return (short) -expression.applyAsShort(t);
                        case 0:   throw new IllegalArgumentException("Divisor can't be 0!");
                        case 1:   return expression.applyAsShort(t);
                        case 2:   return (short) (expression.applyAsShort(t) >> 1);
                        case 4:   return (short) (expression.applyAsShort(t) >> 2);
                        case 8:   return (short) (expression.applyAsShort(t) >> 3);
                        case 16:  return (short) (expression.applyAsShort(t) >> 4);
                        case 32:  return (short) (expression.applyAsShort(t) >> 5);
                        case 64:  return (short) (expression.applyAsShort(t) >> 6);
                        case 128: return (short) (expression.applyAsShort(t) >> 7);
                    }
                }

                return (short) (expression.applyAsShort(t) / d);
            } else return (short) 0;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToShort<T> expression, byte divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.mapToDouble(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToShort<T> expression, int divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.mapToDouble(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToShort<T> expression, long divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.mapToDouble(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToShort<T> expression, double divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        return expression.mapToDouble(v -> v / divisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToShort<T> expression, ToByte<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsByte(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsShort(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToShort<T> expression, ToInt<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsInt(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsShort(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToShort<T> expression, ToLong<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsLong(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsShort(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToShort<T> expression, ToDouble<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsDouble(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsShort(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToInt<T> divideFloor(ToInt<T> expression, byte divisor) {
        switch (divisor) {
            case -1  : return negate(expression);
            case 0   : throw new IllegalArgumentException("Divisor can't be 0!");
            case 1   : return expression;
            case 2   : return expression.map(v -> (v >> 1));
            case 4   : return expression.map(v -> (v >> 2));
            case 8   : return expression.map(v -> (v >> 3));
            case 16  : return expression.map(v -> (v >> 4));
            case 32  : return expression.map(v -> (v >> 5));
            case 64  : return expression.map(v -> (v >> 6));
        }
        return expression.map(v -> (v / divisor));
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToInt<T> divideFloor(ToInt<T> expression, int divisor) {
        switch (divisor) {
            case -1  : return negate(expression);
            case 0   : throw new IllegalArgumentException("Divisor can't be 0!");
            case 1   : return expression;
            case 2   : return expression.map(v -> (v >> 1));
            case 4   : return expression.map(v -> (v >> 2));
            case 8   : return expression.map(v -> (v >> 3));
            case 16  : return expression.map(v -> (v >> 4));
            case 32  : return expression.map(v -> (v >> 5));
            case 64  : return expression.map(v -> (v >> 6));
            case 128 : return expression.map(v -> (v >> 7));
        }
        return expression.map(v -> (v / divisor));
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToInt<T> divideFloor(ToInt<T> expression, long divisor) {
        if (divisor >= -1 && divisor <= Integer.MAX_VALUE) {
            return divideFloor(expression, divisor);
        } else {
            return expression.map(v -> (int) (v / divisor));
        }
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToInt<T> divideFloor(ToInt<T> expression, ToByte<T> divisor) {
        return t -> {
            final byte d = divisor.applyAsByte(t);
            switch (d) {
                case -1: return -expression.applyAsInt(t);
                case 0:  throw new IllegalArgumentException("Divisor can't be 0!");
                case 1:  return expression.applyAsInt(t);
                case 2:  return (expression.applyAsInt(t) >> 1);
                case 4:  return (expression.applyAsInt(t) >> 2);
                case 8:  return (expression.applyAsInt(t) >> 3);
                case 16: return (expression.applyAsInt(t) >> 4);
                case 32: return (expression.applyAsInt(t) >> 5);
                case 64: return (expression.applyAsInt(t) >> 6);
            }
            return (expression.applyAsInt(t) / d);
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToInt<T> divideFloor(ToInt<T> expression, ToInt<T> divisor) {
        return t -> {
            final int d = divisor.applyAsInt(t);
            switch (d) {
                case -1:  return -expression.applyAsInt(t);
                case 0:   throw new IllegalArgumentException("Divisor can't be 0!");
                case 1:   return expression.applyAsInt(t);
                case 2:   return (expression.applyAsInt(t) >> 1);
                case 4:   return (expression.applyAsInt(t) >> 2);
                case 8:   return (expression.applyAsInt(t) >> 3);
                case 16:  return (expression.applyAsInt(t) >> 4);
                case 32:  return (expression.applyAsInt(t) >> 5);
                case 64:  return (expression.applyAsInt(t) >> 6);
                case 128: return (expression.applyAsInt(t) >> 7);
            }
            return (expression.applyAsInt(t) / d);
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToInt<T> divideFloor(ToInt<T> expression, ToLong<T> divisor) {
        return t -> {
            final long d = divisor.applyAsLong(t);
            if (d <= Integer.MAX_VALUE) {
                if (d >= -1) {
                    switch ((int) d) {
                        case -1:  return -expression.applyAsInt(t);
                        case 0:   throw new IllegalArgumentException("Divisor can't be 0!");
                        case 1:   return expression.applyAsInt(t);
                        case 2:   return (expression.applyAsInt(t) >> 1);
                        case 4:   return (expression.applyAsInt(t) >> 2);
                        case 8:   return (expression.applyAsInt(t) >> 3);
                        case 16:  return (expression.applyAsInt(t) >> 4);
                        case 32:  return (expression.applyAsInt(t) >> 5);
                        case 64:  return (expression.applyAsInt(t) >> 6);
                        case 128: return (expression.applyAsInt(t) >> 7);
                    }
                }

                return (int) (expression.applyAsInt(t) / d);
            } else return 0;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToInt<T> expression, byte divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.mapToDouble(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToInt<T> expression, int divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.mapToDouble(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToInt<T> expression, long divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.mapToDouble(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToInt<T> expression, double divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        return expression.mapToDouble(v -> v / divisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToInt<T> expression, ToByte<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsByte(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsInt(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToInt<T> expression, ToInt<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsInt(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsInt(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToInt<T> expression, ToLong<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsLong(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsInt(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToInt<T> expression, ToDouble<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsDouble(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsInt(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> divideFloor(ToLong<T> expression, byte divisor) {
        switch (divisor) {
            case -1  : return negate(expression);
            case 0   : throw new IllegalArgumentException("Divisor can't be 0!");
            case 1   : return expression;
            case 2   : return expression.map(v -> (v >> 1));
            case 4   : return expression.map(v -> (v >> 2));
            case 8   : return expression.map(v -> (v >> 3));
            case 16  : return expression.map(v -> (v >> 4));
            case 32  : return expression.map(v -> (v >> 5));
            case 64  : return expression.map(v -> (v >> 6));
        }
        return expression.map(v -> (v / divisor));
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> divideFloor(ToLong<T> expression, int divisor) {
        switch (divisor) {
            case -1  : return negate(expression);
            case 0   : throw new IllegalArgumentException("Divisor can't be 0!");
            case 1   : return expression;
            case 2   : return expression.map(v -> (v >> 1));
            case 4   : return expression.map(v -> (v >> 2));
            case 8   : return expression.map(v -> (v >> 3));
            case 16  : return expression.map(v -> (v >> 4));
            case 32  : return expression.map(v -> (v >> 5));
            case 64  : return expression.map(v -> (v >> 6));
            case 128 : return expression.map(v -> (v >> 7));
        }
        return expression.map(v -> (v / divisor));
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> divideFloor(ToLong<T> expression, long divisor) {
        if (divisor >= -1 && divisor <= Integer.MAX_VALUE) {
            return divideFloor(expression, (int) divisor);
        } else {
            return expression.map(v -> (v / divisor));
        }
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> divideFloor(ToLong<T> expression, ToByte<T> divisor) {
        return t -> {
            final byte d = divisor.applyAsByte(t);
            switch (d) {
                case -1: return -expression.applyAsLong(t);
                case 0:  throw new IllegalArgumentException("Divisor can't be 0!");
                case 1:  return expression.applyAsLong(t);
                case 2:  return (expression.applyAsLong(t) >> 1);
                case 4:  return (expression.applyAsLong(t) >> 2);
                case 8:  return (expression.applyAsLong(t) >> 3);
                case 16: return (expression.applyAsLong(t) >> 4);
                case 32: return (expression.applyAsLong(t) >> 5);
                case 64: return (expression.applyAsLong(t) >> 6);
            }
            return (expression.applyAsLong(t) / d);
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> divideFloor(ToLong<T> expression, ToInt<T> divisor) {
        return t -> {
            final int d = divisor.applyAsInt(t);
            switch (d) {
                case -1:  return -expression.applyAsLong(t);
                case 0:   throw new IllegalArgumentException("Divisor can't be 0!");
                case 1:   return expression.applyAsLong(t);
                case 2:   return (expression.applyAsLong(t) >> 1);
                case 4:   return (expression.applyAsLong(t) >> 2);
                case 8:   return (expression.applyAsLong(t) >> 3);
                case 16:  return (expression.applyAsLong(t) >> 4);
                case 32:  return (expression.applyAsLong(t) >> 5);
                case 64:  return (expression.applyAsLong(t) >> 6);
                case 128: return (expression.applyAsLong(t) >> 7);
            }
            return (expression.applyAsLong(t) / d);
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> divideFloor(ToLong<T> expression, ToLong<T> divisor) {
        return t -> {
            final long d = divisor.applyAsLong(t);
            if (d <= Long.MAX_VALUE) {
                if (d >= -1) {
                    switch ((int) d) {
                        case -1:  return -expression.applyAsLong(t);
                        case 0:   throw new IllegalArgumentException("Divisor can't be 0!");
                        case 1:   return expression.applyAsLong(t);
                        case 2:   return (expression.applyAsLong(t) >> 1);
                        case 4:   return (expression.applyAsLong(t) >> 2);
                        case 8:   return (expression.applyAsLong(t) >> 3);
                        case 16:  return (expression.applyAsLong(t) >> 4);
                        case 32:  return (expression.applyAsLong(t) >> 5);
                        case 64:  return (expression.applyAsLong(t) >> 6);
                        case 128: return (expression.applyAsLong(t) >> 7);
                    }
                }

                return (expression.applyAsLong(t) / d);
            } else return 0;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToLong<T> expression, byte divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.mapToDouble(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToLong<T> expression, int divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.mapToDouble(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToLong<T> expression, long divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.mapToDouble(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToLong<T> expression, double divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        return expression.mapToDouble(v -> v / divisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToLong<T> expression, ToByte<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsByte(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsLong(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToLong<T> expression, ToInt<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsInt(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsLong(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToLong<T> expression, ToLong<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsLong(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsLong(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToLong<T> expression, ToDouble<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsDouble(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsLong(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToInt<T> divideFloor(ToFloat<T> expression, byte divisor) {
        return expression.mapToDouble(v -> Math.floor(v / divisor)).asInt();
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToInt<T> divideFloor(ToFloat<T> expression, int divisor) {
        return expression.mapToDouble(v -> Math.floor(v / divisor)).asInt();
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToInt<T> divideFloor(ToFloat<T> expression, long divisor) {
        return expression.mapToDouble(v -> Math.floor(v / divisor)).asInt();
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToInt<T> divideFloor(ToFloat<T> expression, ToByte<T> divisor) {
        return t -> {
            final byte d = divisor.applyAsByte(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return (int) Math.floor(expression.applyAsFloat(t) / d);
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToInt<T> divideFloor(ToFloat<T> expression, ToInt<T> divisor) {
        return t -> {
            final int d = divisor.applyAsInt(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return (int) Math.floor(expression.applyAsFloat(t) / d);
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToInt<T> divideFloor(ToFloat<T> expression, ToLong<T> divisor) {
        return t -> {
            final long d = divisor.applyAsLong(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return (int) Math.floor(expression.applyAsFloat(t) / d);
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToFloat<T> expression, byte divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.mapToDouble(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToFloat<T> expression, int divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.mapToDouble(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToFloat<T> expression, long divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.mapToDouble(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToFloat<T> expression, double divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        return expression.mapToDouble(v -> v / divisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToFloat<T> expression, ToByte<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsByte(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsFloat(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToFloat<T> expression, ToInt<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsInt(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsFloat(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToFloat<T> expression, ToLong<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsLong(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsFloat(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToFloat<T> expression, ToDouble<T> divisor) {
        return t -> {
            final double d = divisor.applyAsDouble(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsFloat(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> divideFloor(ToDouble<T> expression, byte divisor) {
        return expression.map(v -> Math.floor(v / divisor)).asLong();
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> divideFloor(ToDouble<T> expression, int divisor) {
        return expression.map(v -> Math.floor(v / divisor)).asLong();
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> divideFloor(ToDouble<T> expression, long divisor) {
        return expression.map(v -> Math.floor(v / divisor)).asLong();
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> divideFloor(ToDouble<T> expression, ToByte<T> divisor) {
        return t -> {
            final byte d = divisor.applyAsByte(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return (long) Math.floor(expression.applyAsDouble(t) / d);
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> divideFloor(ToDouble<T> expression, ToInt<T> divisor) {
        return t -> {
            final int d = divisor.applyAsInt(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return (long) Math.floor(expression.applyAsDouble(t) / d);
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}. This result from this method will be floored.
     *
     * @param expression  the input expression
     * @param divisor     the divisor expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> divideFloor(ToDouble<T> expression, ToLong<T> divisor) {
        return t -> {
            final long d = divisor.applyAsLong(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return (long) Math.floor(expression.applyAsDouble(t) / d);
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToDouble<T> expression, byte divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.map(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToDouble<T> expression, int divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.map(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToDouble<T> expression, long divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        final double dDivisor = (double) divisor;
        return expression.map(v -> v / dDivisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToDouble<T> expression, double divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Divisor can't be 0!");
        return expression.map(v -> v / divisor);
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToDouble<T> expression, ToByte<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsByte(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsDouble(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToDouble<T> expression, ToInt<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsInt(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsDouble(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToDouble<T> expression, ToLong<T> divisor) {
        return t -> {
            final double d = (double) divisor.applyAsLong(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsDouble(t) / d;
        };
    }

    /**
     * Creates and returns an expression that takes the result of the specified
     * expression and divides it with the specified constant. The constant must
     * not be {@code 0}.
     *
     * @param expression  the input expression
     * @param divisor     the divisor
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> divide(ToDouble<T> expression, ToDouble<T> divisor) {
        return t -> {
            final double d = divisor.applyAsDouble(t);
            if (d == 0) throw new IllegalArgumentException("Divisor can't be 0!");
            return expression.applyAsDouble(t) / d;
        };
    }
}