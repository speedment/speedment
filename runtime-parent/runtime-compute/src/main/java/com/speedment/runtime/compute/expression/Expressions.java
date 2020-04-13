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
package com.speedment.runtime.compute.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.internal.BinaryJoiningExpressionImpl;
import com.speedment.runtime.compute.internal.JoiningExpressionImpl;
import com.speedment.runtime.compute.internal.ToByteNullableImpl;
import com.speedment.runtime.compute.internal.ToDoubleNullableImpl;
import com.speedment.runtime.compute.internal.expression.AbsUtil;
import com.speedment.runtime.compute.internal.expression.DivideUtil;
import com.speedment.runtime.compute.internal.expression.MinusUtil;
import com.speedment.runtime.compute.internal.expression.MultiplyUtil;
import com.speedment.runtime.compute.internal.expression.NegateUtil;
import com.speedment.runtime.compute.internal.expression.PlusUtil;
import com.speedment.runtime.compute.internal.expression.PowUtil;
import com.speedment.runtime.compute.internal.expression.SignUtil;
import com.speedment.runtime.compute.internal.expression.SqrtUtil;

import static java.util.Arrays.asList;

/**
 * Common mathematical expressions often used on Speedment entities.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
@SuppressWarnings("overloads")
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
        return AbsUtil.absByte(expression);
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
        return AbsUtil.absShort(expression);
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
        return AbsUtil.absInt(expression);
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
        return AbsUtil.absLong(expression);
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
        return AbsUtil.absFloat(expression);
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
        return AbsUtil.absDouble(expression);
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
        return AbsUtil.absByteOrNull(expression);
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
        return AbsUtil.absShortOrNull(expression);
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
        return AbsUtil.absIntOrNull(expression);
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
        return AbsUtil.absLongOrNull(expression);
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
        return AbsUtil.absFloatOrNull(expression);
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
        return AbsUtil.absDoubleOrNull(expression);
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
        return SignUtil.signByte(expression);
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
        return SignUtil.signShort(expression);
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
        return SignUtil.signInt(expression);
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
        return SignUtil.signLong(expression);
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
        return SignUtil.signFloat(expression);
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
        return SignUtil.signDouble(expression);
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
        return SignUtil.signBigDecimal(expression);
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
            SignUtil.signByte(expression.orThrow()),
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
            SignUtil.signShort(expression.orThrow()),
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
            SignUtil.signInt(expression.orThrow()),
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
            SignUtil.signLong(expression.orThrow()),
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
            SignUtil.signFloat(expression.orThrow()),
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
            SignUtil.signDouble(expression.orThrow()),
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
        return SqrtUtil.sqrtByte(expression);
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
        return SqrtUtil.sqrtShort(expression);
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
        return SqrtUtil.sqrtInt(expression);
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
        return SqrtUtil.sqrtLong(expression);
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
        return SqrtUtil.sqrtFloat(expression);
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
        return SqrtUtil.sqrtDouble(expression);
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
            SqrtUtil.sqrtByte(expression.orThrow()),
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
            SqrtUtil.sqrtShort(expression.orThrow()),
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
            SqrtUtil.sqrtInt(expression.orThrow()),
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
            SqrtUtil.sqrtLong(expression.orThrow()),
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
            SqrtUtil.sqrtFloat(expression.orThrow()),
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
            SqrtUtil.sqrtDouble(expression.orThrow()),
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
        return PowUtil.bytePowInt(expression, power);
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
        return PowUtil.bytePowDouble(expression, power);
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
        return PowUtil.bytePowInt(expression, power);
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
        return PowUtil.bytePowDouble(expression, power);
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
            PowUtil.bytePowInt(expression.orThrow(), power),
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
            PowUtil.bytePowDouble(expression.orThrow(), power),
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
            PowUtil.bytePowInt(expression.orThrow(), power),
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
            PowUtil.bytePowDouble(expression.orThrow(), power),
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
        return PowUtil.shortPowInt(expression, power);
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
        return PowUtil.shortPowDouble(expression, power);
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
        return PowUtil.shortPowInt(expression, power);
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
        return PowUtil.shortPowDouble(expression, power);
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
            PowUtil.shortPowInt(expression.orThrow(), power),
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
            PowUtil.shortPowDouble(expression.orThrow(), power),
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
            PowUtil.shortPowInt(expression.orThrow(), power),
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
            PowUtil.shortPowDouble(expression.orThrow(), power),
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
        return PowUtil.intPowInt(expression, power);
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
        return PowUtil.intPowDouble(expression, power);
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
        return PowUtil.intPowInt(expression, power);
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
        return PowUtil.intPowDouble(expression, power);
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
            PowUtil.intPowInt(expression.orThrow(), power),
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
            PowUtil.intPowDouble(expression.orThrow(), power),
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
            PowUtil.intPowInt(expression.orThrow(), power),
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
            PowUtil.intPowDouble(expression.orThrow(), power),
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
        return PowUtil.longPowInt(expression, power);
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
        return PowUtil.longPowDouble(expression, power);
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
        return PowUtil.longPowInt(expression, power);
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
        return PowUtil.longPowDouble(expression, power);
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
            PowUtil.longPowInt(expression.orThrow(), power),
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
            PowUtil.longPowDouble(expression.orThrow(), power),
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
            PowUtil.longPowInt(expression.orThrow(), power),
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
            PowUtil.longPowDouble(expression.orThrow(), power),
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
        return PowUtil.floatPowInt(expression, power);
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
        return PowUtil.floatPowDouble(expression, power);
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
        return PowUtil.floatPowInt(expression, power);
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
        return PowUtil.floatPowDouble(expression, power);
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
            PowUtil.floatPowInt(expression.orThrow(), power),
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
            PowUtil.floatPowDouble(expression.orThrow(), power),
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
            PowUtil.floatPowInt(expression.orThrow(), power),
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
            PowUtil.floatPowDouble(expression.orThrow(), power),
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
        return PowUtil.doublePowInt(expression, power);
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
        return PowUtil.doublePowDouble(expression, power);
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
        return PowUtil.doublePowInt(expression, power);
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
        return PowUtil.doublePowDouble(expression, power);
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
            PowUtil.doublePowInt(expression.orThrow(), power),
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
            PowUtil.doublePowDouble(expression.orThrow(), power),
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
            PowUtil.doublePowInt(expression.orThrow(), power),
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
            PowUtil.doublePowDouble(expression.orThrow(), power),
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
        return NegateUtil.negateByte(expression);
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
        return NegateUtil.negateShort(expression);
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
        return NegateUtil.negateInt(expression);
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
        return NegateUtil.negateLong(expression);
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
        return NegateUtil.negateFloat(expression);
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
        return NegateUtil.negateDouble(expression);
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
        return NegateUtil.negateBigDecimal(expression);
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
        return NegateUtil.negateBoolean(expression);
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
        return NegateUtil.negateByteOrNull(expression);
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
        return NegateUtil.negateShortOrNull(expression);
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
        return NegateUtil.negateIntOrNull(expression);
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
        return NegateUtil.negateLongOrNull(expression);
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
        return NegateUtil.negateFloatOrNull(expression);
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
        return NegateUtil.negateDoubleOrNull(expression);
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
        return NegateUtil.negateBooleanOrNull(expression);
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
        return PlusUtil.bytePlusByte(first, second);
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
        return PlusUtil.bytePlusInt(first, second);
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
        return PlusUtil.bytePlusLong(first, second);
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
        return PlusUtil.bytePlusByte(first, second);
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
        return PlusUtil.shortPlusByte(first, second);
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
        return PlusUtil.shortPlusInt(first, second);
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
        return PlusUtil.shortPlusLong(first, second);
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
        return PlusUtil.shortPlusShort(first, second);
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
        return PlusUtil.intPlusByte(first, second);
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
        return PlusUtil.intPlusInt(first, second);
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
        return PlusUtil.intPlusLong(first, second);
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
        return PlusUtil.intPlusByte(first, second);
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
        return PlusUtil.intPlusInt(first, second);
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
        return PlusUtil.longPlusByte(first, second);
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
        return PlusUtil.longPlusInt(first, second);
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
        return PlusUtil.longPlusLong(first, second);
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
        return PlusUtil.longPlusInt(first, second);
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
        return PlusUtil.longPlusLong(first, second);
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
        return PlusUtil.floatPlusInt(first, second);
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
        return PlusUtil.floatPlusLong(first, second);
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
        return PlusUtil.floatPlusFloat(first, second);
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
        return PlusUtil.floatPlusInt(first, second);
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
        return PlusUtil.floatPlusLong(first, second);
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
        return PlusUtil.floatPlusFloat(first, second);
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
        return PlusUtil.doublePlusInt(first, second);
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
        return PlusUtil.doublePlusLong(first, second);
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
        return PlusUtil.doublePlusDouble(first, second);
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
        return PlusUtil.doublePlusInt(first, second);
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
        return PlusUtil.doublePlusLong(first, second);
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
        return PlusUtil.doublePlusDouble(first, second);
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
        return MinusUtil.byteMinusByte(first, second);
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
        return MinusUtil.byteMinusInt(first, second);
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
        return MinusUtil.byteMinusLong(first, second);
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
        return MinusUtil.byteMinusByte(first, second);
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
        return MinusUtil.shortMinusByte(first, second);
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
        return MinusUtil.shortMinusInt(first, second);
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
        return MinusUtil.shortMinusLong(first, second);
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
        return MinusUtil.shortMinusShort(first, second);
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
        return MinusUtil.intMinusByte(first, second);
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
        return MinusUtil.intMinusInt(first, second);
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
        return MinusUtil.intMinusLong(first, second);
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
        return MinusUtil.intMinusByte(first, second);
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
        return MinusUtil.intMinusInt(first, second);
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
        return MinusUtil.longMinusByte(first, second);
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
        return MinusUtil.longMinusInt(first, second);
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
        return MinusUtil.longMinusLong(first, second);
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
        return MinusUtil.longMinusInt(first, second);
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
        return MinusUtil.longMinusLong(first, second);
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
        return MinusUtil.floatMinusInt(first, second);
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
        return MinusUtil.floatMinusLong(first, second);
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
        return MinusUtil.floatMinusFloat(first, second);
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
        return MinusUtil.floatMinusInt(first, second);
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
        return MinusUtil.floatMinusLong(first, second);
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
        return MinusUtil.floatMinusFloat(first, second);
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
        return MinusUtil.doubleMinusInt(first, second);
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
        return MinusUtil.doubleMinusLong(first, second);
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
        return MinusUtil.doubleMinusDouble(first, second);
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
        return MinusUtil.doubleMinusInt(first, second);
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
        return MinusUtil.doubleMinusLong(first, second);
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
        return MinusUtil.doubleMinusDouble(first, second);
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
        return MultiplyUtil.byteMultiplyByte(first, second);
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
        return MultiplyUtil.byteMultiplyInt(first, second);
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
        return MultiplyUtil.byteMultiplyLong(first, second);
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
        return MultiplyUtil.byteMultiplyByte(first, second);
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
        return MultiplyUtil.shortMultiplyByte(first, second);
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
        return MultiplyUtil.shortMultiplyInt(first, second);
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
        return MultiplyUtil.shortMultiplyLong(first, second);
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
        return MultiplyUtil.shortMultiplyShort(first, second);
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
        return MultiplyUtil.intMultiplyByte(first, second);
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
        return MultiplyUtil.intMultiplyInt(first, second);
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
        return MultiplyUtil.intMultiplyLong(first, second);
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
        return MultiplyUtil.intMultiplyByte(first, second);
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
        return MultiplyUtil.intMultiplyInt(first, second);
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
        return MultiplyUtil.longMultiplyByte(first, second);
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
        return MultiplyUtil.longMultiplyInt(first, second);
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
        return MultiplyUtil.longMultiplyLong(first, second);
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
        return MultiplyUtil.longMultiplyInt(first, second);
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
        return MultiplyUtil.longMultiplyLong(first, second);
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
        return MultiplyUtil.floatMultiplyInt(first, second);
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
        return MultiplyUtil.floatMultiplyLong(first, second);
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
        return MultiplyUtil.floatMultiplyFloat(first, second);
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
        return MultiplyUtil.floatMultiplyInt(first, second);
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
        return MultiplyUtil.floatMultiplyLong(first, second);
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
        return MultiplyUtil.floatMultiplyFloat(first, second);
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
        return MultiplyUtil.doubleMultiplyInt(first, second);
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
        return MultiplyUtil.doubleMultiplyLong(first, second);
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
        return MultiplyUtil.doubleMultiplyDouble(first, second);
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
        return MultiplyUtil.doubleMultiplyInt(first, second);
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
        return MultiplyUtil.doubleMultiplyLong(first, second);
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
        return MultiplyUtil.doubleMultiplyDouble(first, second);
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
        return DivideUtil.byteDivideInt(first, second);
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
        return DivideUtil.byteDivideLong(first, second);
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
        return DivideUtil.byteDivideDouble(first, second);
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
        return DivideUtil.byteDivideInt(first, second);
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
        return DivideUtil.byteDivideLong(first, second);
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
        return DivideUtil.byteDivideDouble(first, second);
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
        return DivideUtil.shortDivideInt(first, second);
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
        return DivideUtil.shortDivideLong(first, second);
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
        return DivideUtil.shortDivideDouble(first, second);
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
        return DivideUtil.shortDivideInt(first, second);
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
        return DivideUtil.shortDivideLong(first, second);
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
        return DivideUtil.shortDivideDouble(first, second);
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
        return DivideUtil.intDivideInt(first, second);
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
        return DivideUtil.intDivideLong(first, second);
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
        return DivideUtil.intDivideDouble(first, second);
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
        return DivideUtil.intDivideInt(first, second);
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
        return DivideUtil.intDivideLong(first, second);
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
        return DivideUtil.intDivideDouble(first, second);
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
        return DivideUtil.longDivideInt(first, second);
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
        return DivideUtil.longDivideLong(first, second);
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
        return DivideUtil.longDivideDouble(first, second);
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
        return DivideUtil.longDivideInt(first, second);
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
        return DivideUtil.longDivideLong(first, second);
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
        return DivideUtil.longDivideDouble(first, second);
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
        return DivideUtil.floatDivideInt(first, second);
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
        return DivideUtil.floatDivideLong(first, second);
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
        return DivideUtil.floatDivideDouble(first, second);
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
        return DivideUtil.floatDivideInt(first, second);
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
        return DivideUtil.floatDivideLong(first, second);
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
        return DivideUtil.floatDivideDouble(first, second);
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
        return DivideUtil.doubleDivideInt(first, second);
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
        return DivideUtil.doubleDivideLong(first, second);
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
        return DivideUtil.doubleDivideDouble(first, second);
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
        return DivideUtil.doubleDivideInt(first, second);
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
        return DivideUtil.doubleDivideLong(first, second);
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
        return DivideUtil.doubleDivideDouble(first, second);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                ToString                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that represents the result of the two specified
     * expressions joined together, forming a single string.
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the joined expression
     */
    public static <T> ToString<T> joining(
            ToString<T> first,
            ToString<T> second) {
        return joining("", first, second);
    }

    /**
     * Returns an expression that represents the result of the two specified
     * expressions joined together with a separator between them, forming a
     * single string.
     *
     * @param separator  the separator (may be empty but never {@code null})
     * @param first      the first expression
     * @param second     the second expression
     * @param <T>        the input entity type
     * @return           the joined expression
     */
    public static <T> ToString<T> joining(
            CharSequence separator,
            ToString<T> first,
            ToString<T> second) {
        return joining(separator, "", "", first, second);
    }

    /**
     * Returns an expression that represents the result of the two specified
     * expressions joined together with a separator between them, with a prefix
     * before the entire result and a suffix after it, forming a single string.
     *
     * @param separator  the separator (may be empty but never {@code null})
     * @param prefix     the prefix to put before the whole result
     * @param suffix     the suffix to put after the whole result
     * @param first      the first expression
     * @param second     the second expression
     * @param <T>        the input entity type
     * @return           the joined expression
     */
    public static <T> ToString<T> joining(
            CharSequence separator,
            CharSequence prefix,
            CharSequence suffix,
            ToString<T> first,
            ToString<T> second) {
        return new BinaryJoiningExpressionImpl<>(separator, prefix, suffix, first, second);
    }

    /**
     * Returns an expression that represents the result of all the specified
     * expressions joined together, forming a single string.
     *
     * @param expressions  the array of expressions to join
     * @param <T>          the input entity type
     * @return             the joined expression
     */
    @SuppressWarnings("varargs")
    @SafeVarargs
    public static <T> ToString<T> joining(
            ToString<T>... expressions) {
        return joining("", expressions);
    }

    /**
     * Returns an expression that represents the result of all the specified
     * expressions joined together with separators between them, forming a
     * single string.
     *
     * @param separator    the separator (may be empty but never {@code null})
     * @param expressions  the array of expressions to join
     * @param <T>          the input entity type
     * @return             the joined expression
     */
    @SuppressWarnings("varargs")
    @SafeVarargs
    public static <T> ToString<T> joining(
            CharSequence separator,
            ToString<T>... expressions) {
        return joining(separator, "", "", expressions);
    }

    /**
     * Returns an expression that represents the result of all the specified
     * expressions joined together with separators between them, with a prefix
     * before the entire result and a suffix after it, forming a single string.
     *
     * @param separator    the separator (may be empty but never {@code null})
     * @param prefix       the prefix to put before the whole result
     * @param suffix       the suffix to put after the whole result
     * @param expressions  the array of expressions to join
     * @param <T>          the input entity type
     * @return             the joined expression
     */
    @SuppressWarnings("varargs")
    @SafeVarargs
    public static <T> ToString<T> joining(
            CharSequence separator,
            CharSequence prefix,
            CharSequence suffix,
            ToString<T>... expressions) {
        return new JoiningExpressionImpl<>(separator, prefix, suffix, asList(expressions));
    }
}