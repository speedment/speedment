/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.compute.internal.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.ComposedExpression;
import com.speedment.runtime.compute.expression.predicate.ComposedPredicate;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.predicate.IsNotNull;
import com.speedment.runtime.compute.expression.predicate.IsNull;
import com.speedment.runtime.compute.trait.ToNullable;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * Utility class used to produce types instances of {@link ComposedExpression}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class ComposedUtil {

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToByte<T>
    composeToByte(Function<T, A> first, ToByte<A> second) {
        return new ComposedToByte<>(first, second);
    }

    private final static class ComposedToByte<T, A>
    extends AbstractComposedExpression<T, A, ToByte<A>>
    implements ToByte<T> {
        ComposedToByte(Function<T, A> first, ToByte<A> aToByte) {
            super(first, aToByte);
        }

        @Override
        public byte applyAsByte(T object) {
            return second.applyAsByte(first.apply(object));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToShort<T>
    composeToShort(Function<T, A> first, ToShort<A> second) {
        return new ComposedToShort<>(first, second);
    }

    private final static class ComposedToShort<T, A>
    extends AbstractComposedExpression<T, A, ToShort<A>>
    implements ToShort<T> {
        ComposedToShort(Function<T, A> first, ToShort<A> aToShort) {
            super(first, aToShort);
        }

        @Override
        public short applyAsShort(T object) {
            return second.applyAsShort(first.apply(object));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToInt<T>
    composeToInt(Function<T, A> first, ToInt<A> second) {
        return new ComposedToInt<>(first, second);
    }

    private final static class ComposedToInt<T, A>
    extends AbstractComposedExpression<T, A, ToInt<A>>
    implements ToInt<T> {
        ComposedToInt(Function<T, A> first, ToInt<A> aToInt) {
            super(first, aToInt);
        }

        @Override
        public int applyAsInt(T object) {
            return second.applyAsInt(first.apply(object));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToLong<T>
    composeToLong(Function<T, A> first, ToLong<A> second) {
        return new ComposedToLong<>(first, second);
    }

    private final static class ComposedToLong<T, A>
    extends AbstractComposedExpression<T, A, ToLong<A>>
    implements ToLong<T> {
        ComposedToLong(Function<T, A> first, ToLong<A> aToLong) {
            super(first, aToLong);
        }

        @Override
        public long applyAsLong(T object) {
            return second.applyAsLong(first.apply(object));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToFloat<T>
    composeToFloat(Function<T, A> first, ToFloat<A> second) {
        return new ComposedToFloat<>(first, second);
    }

    private final static class ComposedToFloat<T, A>
    extends AbstractComposedExpression<T, A, ToFloat<A>>
    implements ToFloat<T> {
        ComposedToFloat(Function<T, A> first, ToFloat<A> aToFloat) {
            super(first, aToFloat);
        }

        @Override
        public float applyAsFloat(T object) {
            return second.applyAsFloat(first.apply(object));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToDouble<T>
    composeToDouble(Function<T, A> first, ToDouble<A> second) {
        return new ComposedToDouble<>(first, second);
    }

    private final static class ComposedToDouble<T, A>
    extends AbstractComposedExpression<T, A, ToDouble<A>>
    implements ToDouble<T> {
        ComposedToDouble(Function<T, A> first, ToDouble<A> aToDouble) {
            super(first, aToDouble);
        }

        @Override
        public double applyAsDouble(T object) {
            return second.applyAsDouble(first.apply(object));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToChar<T>
    composeToChar(Function<T, A> first, ToChar<A> second) {
        return new ComposedToChar<>(first, second);
    }

    private final static class ComposedToChar<T, A>
    extends AbstractComposedExpression<T, A, ToChar<A>>
    implements ToChar<T> {
        ComposedToChar(Function<T, A> first, ToChar<A> aToChar) {
            super(first, aToChar);
        }

        @Override
        public char applyAsChar(T object) {
            return second.applyAsChar(first.apply(object));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToBoolean<T>
    composeToBoolean(Function<T, A> first, ToBoolean<A> second) {
        return new ComposedToBoolean<>(first, second);
    }

    private final static class ComposedToBoolean<T, A>
    extends AbstractComposedExpression<T, A, ToBoolean<A>>
    implements ToBoolean<T> {
        ComposedToBoolean(Function<T, A> first, ToBoolean<A> aToBoolean) {
            super(first, aToBoolean);
        }

        @Override
        public boolean applyAsBoolean(T object) {
            return second.applyAsBoolean(first.apply(object));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToString<T>
    composeToString(Function<T, A> first, ToString<A> second) {
        return new ComposedToString<>(first, second);
    }

    private final static class ComposedToString<T, A>
    extends AbstractComposedExpression<T, A, ToString<A>>
    implements ToString<T> {
        ComposedToString(Function<T, A> first, ToString<A> aToString) {
            super(first, aToString);
        }

        @Override
        public String apply(T object) {
            return second.apply(first.apply(object));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToBigDecimal<T>
    composeToBigDecimal(Function<T, A> first, ToBigDecimal<A> second) {
        return new ComposedToBigDecimal<>(first, second);
    }

    private final static class ComposedToBigDecimal<T, A>
    extends AbstractComposedExpression<T, A, ToBigDecimal<A>>
    implements ToBigDecimal<T> {
        ComposedToBigDecimal(Function<T, A> first, ToBigDecimal<A> aToBigDecimal) {
            super(first, aToBigDecimal);
        }

        @Override
        public BigDecimal apply(T object) {
            return second.apply(first.apply(object));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @param <E>  the type of the enum
     * @return  the composed expression
     */
    public static <T, A, E extends Enum<E>> ToEnum<T, E>
    composeToEnum(Function<T, A> first, ToEnum<A, E> second) {
        return new ComposedToEnum<>(first, second);
    }

    private final static class ComposedToEnum<T, A, E extends Enum<E>>
    extends AbstractComposedExpression<T, A, ToEnum<A, E>>
    implements ToEnum<T, E> {
        ComposedToEnum(Function<T, A> first, ToEnum<A, E> aToEnum) {
            super(first, aToEnum);
        }

        @Override
        public Class<E> enumClass() {
            return second.enumClass();
        }

        @Override
        public E apply(T object) {
            return second.apply(first.apply(object));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToByteNullable<T>
    composeNullable(Function<T, A> first, ToByteNullable<A> second) {
        return new ComposedToByteNullable<>(first, second);
    }

    private final static class ComposedToByteNullable<T, A>
    extends AbstractComposedNullableExpression<T, A, Byte, ToByte<T>, ToByte<A>, ToByteNullable<A>>
    implements ToByteNullable<T> {
        ComposedToByteNullable(Function<T, A> first, ToByteNullable<A> aToByte) {
            super(first, aToByte);
        }

        @Override
        public ToByte<T> orThrow() {
            return new ComposedToByte<>(first, second.orThrow());
        }

        @Override
        public ToByte<T> orElse(Byte value) {
            return new ComposedToByte<>(first, second.orElse(value));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToShortNullable<T>
    composeNullable(Function<T, A> first, ToShortNullable<A> second) {
        return new ComposedToShortNullable<>(first, second);
    }

    private final static class ComposedToShortNullable<T, A>
    extends AbstractComposedNullableExpression<T, A, Short, ToShort<T>, ToShort<A>, ToShortNullable<A>>
    implements ToShortNullable<T> {
        ComposedToShortNullable(Function<T, A> first, ToShortNullable<A> aToShort) {
            super(first, aToShort);
        }

        @Override
        public ToShort<T> orThrow() {
            return new ComposedToShort<>(first, second.orThrow());
        }

        @Override
        public ToShort<T> orElse(Short value) {
            return new ComposedToShort<>(first, second.orElse(value));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToIntNullable<T>
    composeNullable(Function<T, A> first, ToIntNullable<A> second) {
        return new ComposedToIntNullable<>(first, second);
    }

    private final static class ComposedToIntNullable<T, A>
    extends AbstractComposedNullableExpression<T, A, Integer, ToInt<T>, ToInt<A>, ToIntNullable<A>>
    implements ToIntNullable<T> {
        ComposedToIntNullable(Function<T, A> first, ToIntNullable<A> aToInt) {
            super(first, aToInt);
        }

        @Override
        public ToInt<T> orThrow() {
            return new ComposedToInt<>(first, second.orThrow());
        }

        @Override
        public ToInt<T> orElse(Integer value) {
            return new ComposedToInt<>(first, second.orElse(value));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToLongNullable<T>
    composeNullable(Function<T, A> first, ToLongNullable<A> second) {
        return new ComposedToLongNullable<>(first, second);
    }

    private final static class ComposedToLongNullable<T, A>
    extends AbstractComposedNullableExpression<T, A, Long, ToLong<T>, ToLong<A>, ToLongNullable<A>>
    implements ToLongNullable<T> {
        ComposedToLongNullable(Function<T, A> first, ToLongNullable<A> aToLong) {
            super(first, aToLong);
        }

        @Override
        public ToLong<T> orThrow() {
            return new ComposedToLong<>(first, second.orThrow());
        }

        @Override
        public ToLong<T> orElse(Long value) {
            return new ComposedToLong<>(first, second.orElse(value));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToFloatNullable<T>
    composeNullable(Function<T, A> first, ToFloatNullable<A> second) {
        return new ComposedToFloatNullable<>(first, second);
    }

    private final static class ComposedToFloatNullable<T, A>
    extends AbstractComposedNullableExpression<T, A, Float, ToFloat<T>, ToFloat<A>, ToFloatNullable<A>>
    implements ToFloatNullable<T> {
        ComposedToFloatNullable(Function<T, A> first, ToFloatNullable<A> aToFloat) {
            super(first, aToFloat);
        }

        @Override
        public ToFloat<T> orThrow() {
            return new ComposedToFloat<>(first, second.orThrow());
        }

        @Override
        public ToFloat<T> orElse(Float value) {
            return new ComposedToFloat<>(first, second.orElse(value));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToDoubleNullable<T>
    composeNullable(Function<T, A> first, ToDoubleNullable<A> second) {
        return new ComposedToDoubleNullable<>(first, second);
    }

    private final static class ComposedToDoubleNullable<T, A>
    extends AbstractComposedNullableExpression<T, A, Double, ToDouble<T>, ToDouble<A>, ToDoubleNullable<A>>
    implements ToDoubleNullable<T> {
        ComposedToDoubleNullable(Function<T, A> first, ToDoubleNullable<A> aToDouble) {
            super(first, aToDouble);
        }

        @Override
        public ToDouble<T> orThrow() {
            return new ComposedToDouble<>(first, second.orThrow());
        }

        @Override
        public ToDouble<T> orElse(Double value) {
            return new ComposedToDouble<>(first, second.orElse(value));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToCharNullable<T>
    composeNullable(Function<T, A> first, ToCharNullable<A> second) {
        return new ComposedToCharNullable<>(first, second);
    }

    private final static class ComposedToCharNullable<T, A>
        extends AbstractComposedNullableExpression<T, A, Character, ToChar<T>, ToChar<A>, ToCharNullable<A>>
        implements ToCharNullable<T> {
        ComposedToCharNullable(Function<T, A> first, ToCharNullable<A> aToChar) {
            super(first, aToChar);
        }

        @Override
        public ToChar<T> orThrow() {
            return new ComposedToChar<>(first, second.orThrow());
        }

        @Override
        public ToChar<T> orElse(Character value) {
            return new ComposedToChar<>(first, second.orElse(value));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToBooleanNullable<T>
    composeNullable(Function<T, A> first, ToBooleanNullable<A> second) {
        return new ComposedToBooleanNullable<>(first, second);
    }

    private final static class ComposedToBooleanNullable<T, A>
    extends AbstractComposedNullableExpression<T, A, Boolean, ToBoolean<T>, ToBoolean<A>, ToBooleanNullable<A>>
    implements ToBooleanNullable<T> {
        ComposedToBooleanNullable(Function<T, A> first, ToBooleanNullable<A> aToBoolean) {
            super(first, aToBoolean);
        }

        @Override
        public ToBoolean<T> orThrow() {
            return new ComposedToBoolean<>(first, second.orThrow());
        }

        @Override
        public ToBoolean<T> orElse(Boolean value) {
            return new ComposedToBoolean<>(first, second.orElse(value));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToStringNullable<T>
    composeNullable(Function<T, A> first, ToStringNullable<A> second) {
        return new ComposedToStringNullable<>(first, second);
    }

    private final static class ComposedToStringNullable<T, A>
    extends AbstractComposedNullableExpression<T, A, String, ToString<T>, ToString<A>, ToStringNullable<A>>
    implements ToStringNullable<T> {
        ComposedToStringNullable(Function<T, A> first, ToStringNullable<A> aToString) {
            super(first, aToString);
        }

        @Override
        public ToString<T> orThrow() {
            return new ComposedToString<>(first, second.orThrow());
        }

        @Override
        public ToString<T> orElse(String value) {
            return new ComposedToString<>(first, second.orElse(value));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToBigDecimalNullable<T>
    composeNullable(Function<T, A> first, ToBigDecimalNullable<A> second) {
        return new ComposedToBigDecimalNullable<>(first, second);
    }

    private final static class ComposedToBigDecimalNullable<T, A>
    extends AbstractComposedNullableExpression<T, A, BigDecimal, ToBigDecimal<T>, ToBigDecimal<A>, ToBigDecimalNullable<A>>
    implements ToBigDecimalNullable<T> {
        ComposedToBigDecimalNullable(Function<T, A> first, ToBigDecimalNullable<A> aToBigDecimal) {
            super(first, aToBigDecimal);
        }

        @Override
        public ToBigDecimal<T> orThrow() {
            return new ComposedToBigDecimal<>(first, second.orThrow());
        }

        @Override
        public ToBigDecimal<T> orElse(BigDecimal value) {
            return new ComposedToBigDecimal<>(first, second.orElse(value));
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param first   the first function to apply
     * @param second  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @param <E>  the enum type
     * @return  the composed expression
     */
    public static <T, A, E extends Enum<E>> ToEnumNullable<T, E>
    composeNullable(Function<T, A> first, ToEnumNullable<A, E> second) {
        return new ComposedToEnumNullable<>(first, second);
    }

    private final static class ComposedToEnumNullable<T, A, E extends Enum<E>>
    extends AbstractComposedNullableExpression<T, A, E,
        ToEnum<T, E>, ToEnum<A, E>, ToEnumNullable<A, E>>
    implements ToEnumNullable<T, E> {
        ComposedToEnumNullable(Function<T, A> first, ToEnumNullable<A, E> aToEnum) {
            super(first, aToEnum);
        }

        @Override
        public Class<E> enumClass() {
            return second.enumClass();
        }

        @Override
        public ToEnum<T, E> orThrow() {
            return new ComposedToEnum<>(first, second.orThrow());
        }

        @Override
        public ToEnum<T, E> orElse(E value) {
            return new ComposedToEnum<>(first, second.orElse(value));
        }
    }

    private abstract static class AbstractComposedNullableExpression
        <T, A, R,
            T_EXPR extends Expression<T>,
            A_EXPR extends Expression<A>,
            SECOND extends ToNullable<A, R, A_EXPR>>
    implements ComposedExpression<T, A>, ToNullable<T, R, T_EXPR> {

        protected final Function<T, A> first;
        protected final SECOND second;

        AbstractComposedNullableExpression(Function<T, A> first, SECOND second) {
            this.first  = requireNonNull(first);
            this.second = requireNonNull(second);
        }

        @Override
        public final R apply(T t) {
            return second.apply(first.apply(t));
        }

        @Override
        public IsNull<T, R> isNull() {
            return new ComposedIsNull<>(this, first);
        }

        @Override
        public IsNotNull<T, R> isNotNull() {
            return new ComposedIsNotNull<>(this, first);
        }

        @Override
        public final boolean isNull(T object) {
            return second.isNull(first.apply(object));
        }

        @Override
        public final boolean isNotNull(T object) {
            return second.isNotNull(first.apply(object));
        }

        @Override
        public final Function<T, A> firstStep() {
            return first;
        }

        @Override
        public final Expression<A> secondStep() {
            return second;
        }
    }

    private abstract static class AbstractComposedExpression<T, A, SECOND extends Expression<A>>
    implements ComposedExpression<T, A> {

        protected final Function<T, A> first;
        protected final SECOND second;

        AbstractComposedExpression(Function<T, A> first, SECOND second) {
            this.first  = requireNonNull(first);
            this.second = requireNonNull(second);
        }

        @Override
        public final Function<T, A> firstStep() {
            return first;
        }

        @Override
        public final Expression<A> secondStep() {
            return second;
        }
    }

    private final static class ComposedIsNull<T, A, R, NON_NULLABLE extends Expression<T>>
    implements ComposedPredicate<T, A>, IsNull<T, R> {

        private final ToNullable<T, R, NON_NULLABLE> expression;
        private final Function<T, A> innerMapper;
        private final Predicate<A> innerPredicate;

        ComposedIsNull(ToNullable<T, R, NON_NULLABLE> expression, Function<T, A> innerMapper) {
            this.expression     = requireNonNull(expression);
            this.innerMapper    = requireNonNull(innerMapper);
            this.innerPredicate = Objects::isNull;
        }

        @Override
        public IsNotNull<T, R> negate() {
            return new ComposedIsNotNull<>(expression, innerMapper);
        }

        @Override
        public Function<T, A> innerMapper() {
            return innerMapper;
        }

        @Override
        public Predicate<A> innerPredicate() {
            return innerPredicate;
        }

        @Override
        public ToNullable<T, R, NON_NULLABLE> expression() {
            return expression;
        }

        @Override
        public boolean test(T t) {
            return innerMapper.apply(t) == null;
        }
    }

    private final static class ComposedIsNotNull<T, A, R, NON_NULLABLE extends Expression<T>>
    implements ComposedPredicate<T, A>, IsNotNull<T, R> {

        private final ToNullable<T, R, NON_NULLABLE> expression;
        private final Function<T, A> innerMapper;
        private final Predicate<A> innerPredicate;

        ComposedIsNotNull(ToNullable<T, R, NON_NULLABLE> expression, Function<T, A> innerMapper) {
            this.expression     = requireNonNull(expression);
            this.innerMapper    = requireNonNull(innerMapper);
            this.innerPredicate = Objects::nonNull;
        }

        @Override
        public IsNull<T, R> negate() {
            return new ComposedIsNull<>(expression, innerMapper);
        }

        @Override
        public Function<T, A> innerMapper() {
            return innerMapper;
        }

        @Override
        public Predicate<A> innerPredicate() {
            return innerPredicate;
        }

        @Override
        public ToNullable<T, R, NON_NULLABLE> expression() {
            return expression;
        }

        @Override
        public boolean test(T t) {
            return innerMapper.apply(t) != null;
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private ComposedUtil() {}
}
