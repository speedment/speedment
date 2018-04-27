package com.speedment.runtime.compute.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.internal.ToByteNullableImpl;
import com.speedment.runtime.compute.internal.ToDoubleNullableImpl;
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
        return SignUtil.sign(expression);
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
        return SignUtil.sign(expression);
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
        return SignUtil.sign(expression);
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
        return SignUtil.sign(expression);
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
        return SignUtil.sign(expression);
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
        return SignUtil.sign(expression);
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
    public static <T> ToByte<T> sign(ToBigDecimal<T> expression) {
        return SignUtil.sign(expression);
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
        return new ToByteNullableImpl<>(
            SignUtil.sign(expression.orThrow()),
            expression.isNull());
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
        return new ToByteNullableImpl<>(
            SignUtil.sign(expression.orThrow()),
            expression.isNull());
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
        return new ToByteNullableImpl<>(
            SignUtil.sign(expression.orThrow()),
            expression.isNull());
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
        return new ToByteNullableImpl<>(
            SignUtil.sign(expression.orThrow()),
            expression.isNull());
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
        return new ToByteNullableImpl<>(
            SignUtil.sign(expression.orThrow()),
            expression.isNull());
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
        return new ToByteNullableImpl<>(
            SignUtil.sign(expression.orThrow()),
            expression.isNull());
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
        return SqrtUtil.sqrt(expression);
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
        return SqrtUtil.sqrt(expression);
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
        return SqrtUtil.sqrt(expression);
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
        return SqrtUtil.sqrt(expression);
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
        return SqrtUtil.sqrt(expression);
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
        return SqrtUtil.sqrt(expression);
    }

    /**
     * Creates and returns an expression that returns the square root of the
     * result from the input expression.
     *
     * @param expression  the input expression
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> sqrt(ToBigDecimal<T> expression) {
        return SqrtUtil.sqrt(expression);
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
            SqrtUtil.sqrt(expression.orThrow()),
            expression.isNull()
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
            SqrtUtil.sqrt(expression.orThrow()),
            expression.isNull()
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
            SqrtUtil.sqrt(expression.orThrow()),
            expression.isNull()
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
            SqrtUtil.sqrt(expression.orThrow()),
            expression.isNull()
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
            SqrtUtil.sqrt(expression.orThrow()),
            expression.isNull()
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
            SqrtUtil.sqrt(expression.orThrow()),
            expression.isNull()
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
    public static <T> ToDouble<T> pow(ToByte<T> expression, int power) {
        return PowUtil.pow(expression, power);
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
        return PowUtil.pow(expression, power);
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
    public static <T> ToDouble<T> pow(ToByte<T> expression, ToInt<T> power) {
        return PowUtil.pow(expression, power);
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
        return PowUtil.pow(expression, power);
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
    powOrNull(ToByteNullable<T> expression, int power) {
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
    powOrNull(ToByteNullable<T> expression, ToInt<T> power) {
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
    powOrNull(ToByteNullable<T> expression, ToDouble<T> power) {
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
    public static <T> ToDouble<T> pow(ToShort<T> expression, int power) {
        return PowUtil.pow(expression, power);
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
        return PowUtil.pow(expression, power);
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
    public static <T> ToDouble<T> pow(ToShort<T> expression, ToInt<T> power) {
        return PowUtil.pow(expression, power);
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
    public static <T> ToDouble<T> pow(ToShort<T> expression, ToDouble<T> power) {
        return PowUtil.pow(expression, power);
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
    powOrNull(ToShortNullable<T> expression, int power) {
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
    powOrNull(ToShortNullable<T> expression, ToInt<T> power) {
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
    powOrNull(ToShortNullable<T> expression, ToDouble<T> power) {
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
    public static <T> ToDouble<T> pow(ToInt<T> expression, int power) {
        return PowUtil.pow(expression, power);
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
        return PowUtil.pow(expression, power);
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
    public static <T> ToDouble<T> pow(ToInt<T> expression, ToInt<T> power) {
        return PowUtil.pow(expression, power);
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
        return PowUtil.pow(expression, power);
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
    powOrNull(ToIntNullable<T> expression, int power) {
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
    powOrNull(ToIntNullable<T> expression, ToInt<T> power) {
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
    powOrNull(ToIntNullable<T> expression, ToDouble<T> power) {
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
    public static <T> ToDouble<T> pow(ToLong<T> expression, int power) {
        return PowUtil.pow(expression, power);
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
        return PowUtil.pow(expression, power);
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
    public static <T> ToDouble<T> pow(ToLong<T> expression, ToInt<T> power) {
        return PowUtil.pow(expression, power);
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
        return PowUtil.pow(expression, power);
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
    powOrNull(ToLongNullable<T> expression, int power) {
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
    powOrNull(ToLongNullable<T> expression, ToInt<T> power) {
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
    powOrNull(ToLongNullable<T> expression, ToDouble<T> power) {
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
        return PowUtil.pow(expression, power);
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
        return PowUtil.pow(expression, power);
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
        return PowUtil.pow(expression, power);
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
    public static <T> ToDouble<T> pow(ToFloat<T> expression, ToDouble<T> power) {
        return PowUtil.pow(expression, power);
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
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
    powOrNull(ToFloatNullable<T> expression, ToDouble<T> power) {
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
        return PowUtil.pow(expression, power);
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
        return PowUtil.pow(expression, power);
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
    public static <T> ToDouble<T> pow(ToDouble<T> expression, ToInt<T> power) {
        return PowUtil.pow(expression, power);
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
    public static <T> ToDouble<T> pow(ToDouble<T> expression, ToDouble<T> power) {
        return PowUtil.pow(expression, power);
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
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
    powOrNull(ToDoubleNullable<T> expression, ToDouble<T> power) {
        return new ToDoubleNullableImpl<>(
            PowUtil.pow(expression.orThrow(), power),
            expression.isNull()
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
    public static <T> ToBigDecimal<T> negate(ToBigDecimal<T> expression) {
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
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToByte<T> first, int second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToByte<T> first, long second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToByte<T> first, double second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToByte<T> first, ToInt<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToByte<T> first, ToLong<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToByte<T> first, ToDouble<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToShort<T> first, int second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToShort<T> first, long second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToShort<T> first, double second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToShort<T> first, ToInt<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToShort<T> first, ToLong<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToShort<T> first, ToDouble<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToInt<T> first, int second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToInt<T> first, long second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToInt<T> first, double second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToInt<T> first, ToInt<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToInt<T> first, ToLong<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToInt<T> first, ToDouble<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToLong<T> first, int second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToLong<T> first, long second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToLong<T> first, double second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToLong<T> first, ToInt<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToLong<T> first, ToLong<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToLong<T> first, ToDouble<T> second) {
        return DivideUtil.divide(first, second);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                 ToFloat                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToFloat<T> first, int second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToFloat<T> first, long second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToFloat<T> first, double second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToFloat<T> first, ToInt<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToFloat<T> first, ToLong<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToFloat<T> first, ToDouble<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToDouble<T> first, int second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToDouble<T> first, long second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToDouble<T> first, double second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToDouble<T> first, ToInt<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToDouble<T> first, ToLong<T> second) {
        return DivideUtil.divide(first, second);
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> divide(ToDouble<T> first, ToDouble<T> second) {
        return DivideUtil.divide(first, second);
    }
}