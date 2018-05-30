package com.speedment.runtime.compute.internal.expression;

import com.speedment.common.function.ToBooleanFunction;
import com.speedment.common.function.ToByteFunction;
import com.speedment.common.function.ToCharFunction;
import com.speedment.common.function.ToFloatFunction;
import com.speedment.common.function.ToShortFunction;
import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.ComposedExpression;
import com.speedment.runtime.compute.expression.Expression;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

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
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToByteNullable<T> composeToByte(Function<T, A> before, ToByte<A> after) {
        return new ComposeToByte<>(before, after);
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToByteNullable<T> composeToByteNullable(Function<T, A> before, ToByteNullable<A> after) {
        return new ComposeToByte<>(before, after);
    }

    /**
     * Internal implementation of the {@link ComposedExpression}.
     *
     * @param <T>      the outer input type
     * @param <A>      the inner input type
     * @param <AFTER>  the expression type of the {@code after} operation
     */
    private final static class ComposeToByte<T, A, AFTER extends ToByteFunction<A> & Expression<A>>
        implements ComposedExpression<T, A>, ToByteNullable<T> {

        private final Function<T, A> before;
        private final AFTER after;

        ComposeToByte(Function<T, A> before, AFTER after) {
            this.before = requireNonNull(before);
            this.after  = requireNonNull(after);
        }

        @Override
        public Function<T, A> firstStep() {
            return before;
        }

        @Override
        public AFTER secondStep() {
            return after;
        }

        @Override
        public byte applyAsByte(T object) throws NullPointerException {
            final A intermediate = before.apply(object);
            return after.applyAsByte(intermediate);
        }

        @Override
        public Byte apply(T object) {
            final A intermediate = before.apply(object);
            if (intermediate == null) return null;
            return after.applyAsByte(intermediate);
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToShortNullable<T> composeToShort(Function<T, A> before, ToShort<A> after) {
        return new ComposeToShort<>(before, after);
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToShortNullable<T> composeToShortNullable(Function<T, A> before, ToShortNullable<A> after) {
        return new ComposeToShort<>(before, after);
    }

    /**
     * Internal implementation of the {@link ComposedExpression}.
     *
     * @param <T>      the outer input type
     * @param <A>      the inner input type
     * @param <AFTER>  the expression type of the {@code after} operation
     */
    private final static class ComposeToShort<T, A, AFTER extends ToShortFunction<A> & Expression<A>>
        implements ComposedExpression<T, A>, ToShortNullable<T> {

        private final Function<T, A> before;
        private final AFTER after;

        ComposeToShort(Function<T, A> before, AFTER after) {
            this.before = requireNonNull(before);
            this.after  = requireNonNull(after);
        }

        @Override
        public Function<T, A> firstStep() {
            return before;
        }

        @Override
        public AFTER secondStep() {
            return after;
        }

        @Override
        public short applyAsShort(T object) throws NullPointerException {
            final A intermediate = before.apply(object);
            return after.applyAsShort(intermediate);
        }

        @Override
        public Short apply(T object) {
            final A intermediate = before.apply(object);
            if (intermediate == null) return null;
            return after.applyAsShort(intermediate);
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToIntNullable<T> composeToInt(Function<T, A> before, ToInt<A> after) {
        return new ComposeToInt<>(before, after);
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToIntNullable<T> composeToIntNullable(Function<T, A> before, ToIntNullable<A> after) {
        return new ComposeToInt<>(before, after);
    }

    /**
     * Internal implementation of the {@link ComposedExpression}.
     *
     * @param <T>      the outer input type
     * @param <A>      the inner input type
     * @param <AFTER>  the expression type of the {@code after} operation
     */
    private final static class ComposeToInt<T, A, AFTER extends ToIntFunction<A> & Expression<A>>
        implements ComposedExpression<T, A>, ToIntNullable<T> {

        private final Function<T, A> before;
        private final AFTER after;

        ComposeToInt(Function<T, A> before, AFTER after) {
            this.before = requireNonNull(before);
            this.after  = requireNonNull(after);
        }

        @Override
        public Function<T, A> firstStep() {
            return before;
        }

        @Override
        public AFTER secondStep() {
            return after;
        }

        @Override
        public int applyAsInt(T object) throws NullPointerException {
            final A intermediate = before.apply(object);
            return after.applyAsInt(intermediate);
        }

        @Override
        public Integer apply(T object) {
            final A intermediate = before.apply(object);
            if (intermediate == null) return null;
            return after.applyAsInt(intermediate);
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToLongNullable<T> composeToLong(Function<T, A> before, ToLong<A> after) {
        return new ComposeToLong<>(before, after);
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToLongNullable<T> composeToLongNullable(Function<T, A> before, ToLongNullable<A> after) {
        return new ComposeToLong<>(before, after);
    }

    /**
     * Internal implementation of the {@link ComposedExpression}.
     *
     * @param <T>      the outer input type
     * @param <A>      the inner input type
     * @param <AFTER>  the expression type of the {@code after} operation
     */
    private final static class ComposeToLong<T, A, AFTER extends ToLongFunction<A> & Expression<A>>
        implements ComposedExpression<T, A>, ToLongNullable<T> {

        private final Function<T, A> before;
        private final AFTER after;

        ComposeToLong(Function<T, A> before, AFTER after) {
            this.before = requireNonNull(before);
            this.after  = requireNonNull(after);
        }

        @Override
        public Function<T, A> firstStep() {
            return before;
        }

        @Override
        public AFTER secondStep() {
            return after;
        }

        @Override
        public long applyAsLong(T object) throws NullPointerException {
            final A intermediate = before.apply(object);
            return after.applyAsLong(intermediate);
        }

        @Override
        public Long apply(T object) {
            final A intermediate = before.apply(object);
            if (intermediate == null) return null;
            return after.applyAsLong(intermediate);
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToFloatNullable<T> composeToFloat(Function<T, A> before, ToFloat<A> after) {
        return new ComposeToFloat<>(before, after);
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToFloatNullable<T> composeToFloatNullable(Function<T, A> before, ToFloatNullable<A> after) {
        return new ComposeToFloat<>(before, after);
    }

    /**
     * Internal implementation of the {@link ComposedExpression}.
     *
     * @param <T>      the outer input type
     * @param <A>      the inner input type
     * @param <AFTER>  the expression type of the {@code after} operation
     */
    private final static class ComposeToFloat<T, A, AFTER extends ToFloatFunction<A> & Expression<A>>
        implements ComposedExpression<T, A>, ToFloatNullable<T> {

        private final Function<T, A> before;
        private final AFTER after;

        ComposeToFloat(Function<T, A> before, AFTER after) {
            this.before = requireNonNull(before);
            this.after  = requireNonNull(after);
        }

        @Override
        public Function<T, A> firstStep() {
            return before;
        }

        @Override
        public AFTER secondStep() {
            return after;
        }

        @Override
        public float applyAsFloat(T object) throws NullPointerException {
            final A intermediate = before.apply(object);
            return after.applyAsFloat(intermediate);
        }

        @Override
        public Float apply(T object) {
            final A intermediate = before.apply(object);
            if (intermediate == null) return null;
            return after.applyAsFloat(intermediate);
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToDoubleNullable<T> composeToDouble(Function<T, A> before, ToDouble<A> after) {
        return new ComposeToDouble<>(before, after);
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToDoubleNullable<T> composeToDoubleNullable(Function<T, A> before, ToDoubleNullable<A> after) {
        return new ComposeToDouble<>(before, after);
    }

    /**
     * Internal implementation of the {@link ComposedExpression}.
     *
     * @param <T>      the outer input type
     * @param <A>      the inner input type
     * @param <AFTER>  the expression type of the {@code after} operation
     */
    private final static class ComposeToDouble<T, A, AFTER extends ToDoubleFunction<A> & Expression<A>>
        implements ComposedExpression<T, A>, ToDoubleNullable<T> {

        private final Function<T, A> before;
        private final AFTER after;

        ComposeToDouble(Function<T, A> before, AFTER after) {
            this.before = requireNonNull(before);
            this.after  = requireNonNull(after);
        }

        @Override
        public Function<T, A> firstStep() {
            return before;
        }

        @Override
        public AFTER secondStep() {
            return after;
        }

        @Override
        public double applyAsDouble(T object) throws NullPointerException {
            final A intermediate = before.apply(object);
            return after.applyAsDouble(intermediate);
        }

        @Override
        public Double apply(T object) {
            final A intermediate = before.apply(object);
            if (intermediate == null) return null;
            return after.applyAsDouble(intermediate);
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToBooleanNullable<T> composeToBoolean(Function<T, A> before, ToBoolean<A> after) {
        return new ComposeToBoolean<>(before, after);
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToBooleanNullable<T> composeToBooleanNullable(Function<T, A> before, ToBooleanNullable<A> after) {
        return new ComposeToBoolean<>(before, after);
    }

    /**
     * Internal implementation of the {@link ComposedExpression}.
     *
     * @param <T>      the outer input type
     * @param <A>      the inner input type
     * @param <AFTER>  the expression type of the {@code after} operation
     */
    private final static class ComposeToBoolean<T, A, AFTER extends ToBooleanFunction<A> & Expression<A>>
        implements ComposedExpression<T, A>, ToBooleanNullable<T> {

        private final Function<T, A> before;
        private final AFTER after;

        ComposeToBoolean(Function<T, A> before, AFTER after) {
            this.before = requireNonNull(before);
            this.after  = requireNonNull(after);
        }

        @Override
        public Function<T, A> firstStep() {
            return before;
        }

        @Override
        public AFTER secondStep() {
            return after;
        }

        @Override
        public boolean applyAsBoolean(T object) throws NullPointerException {
            final A intermediate = before.apply(object);
            return after.applyAsBoolean(intermediate);
        }

        @Override
        public Boolean apply(T object) {
            final A intermediate = before.apply(object);
            if (intermediate == null) return null;
            return after.applyAsBoolean(intermediate);
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToCharNullable<T> composeToChar(Function<T, A> before, ToChar<A> after) {
        return new ComposeToChar<>(before, after);
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToCharNullable<T> composeToCharNullable(Function<T, A> before, ToCharNullable<A> after) {
        return new ComposeToChar<>(before, after);
    }

    /**
     * Internal implementation of the {@link ComposedExpression}.
     *
     * @param <T>      the outer input type
     * @param <A>      the inner input type
     * @param <AFTER>  the expression type of the {@code after} operation
     */
    private final static class ComposeToChar<T, A, AFTER extends ToCharFunction<A> & Expression<A>>
    implements ComposedExpression<T, A>, ToCharNullable<T> {

        private final Function<T, A> before;
        private final AFTER after;

        ComposeToChar(Function<T, A> before, AFTER after) {
            this.before = requireNonNull(before);
            this.after  = requireNonNull(after);
        }

        @Override
        public Function<T, A> firstStep() {
            return before;
        }

        @Override
        public AFTER secondStep() {
            return after;
        }

        @Override
        public char applyAsChar(T object) throws NullPointerException {
            final A intermediate = before.apply(object);
            return after.applyAsChar(intermediate);
        }

        @Override
        public Character apply(T object) {
            final A intermediate = before.apply(object);
            if (intermediate == null) return null;
            return after.applyAsChar(intermediate);
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToStringNullable<T> composeToString(Function<T, A> before, ToString<A> after) {
        return new ComposeToString<>(before, after);
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToStringNullable<T> composeToStringNullable(Function<T, A> before, ToStringNullable<A> after) {
        return new ComposeToString<>(before, after);
    }

    /**
     * Internal implementation of the {@link ComposedExpression}.
     *
     * @param <T>      the outer input type
     * @param <A>      the inner input type
     * @param <AFTER>  the expression type of the {@code after} operation
     */
    private final static class ComposeToString<T, A, AFTER extends Function<A, String> & Expression<A>>
    implements ComposedExpression<T, A>, ToStringNullable<T> {

        private final Function<T, A> before;
        private final AFTER after;

        ComposeToString(Function<T, A> before, AFTER after) {
            this.before = requireNonNull(before);
            this.after  = requireNonNull(after);
        }

        @Override
        public Function<T, A> firstStep() {
            return before;
        }

        @Override
        public AFTER secondStep() {
            return after;
        }

        @Override
        public String apply(T object) {
            final A intermediate = before.apply(object);
            if (intermediate == null) return null;
            return after.apply(intermediate);
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToBigDecimalNullable<T> composeToBigDecimal(Function<T, A> before, ToBigDecimal<A> after) {
        return new ComposeToBigDecimal<>(before, after);
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A> ToBigDecimalNullable<T> composeToBigDecimalNullable(Function<T, A> before, ToBigDecimalNullable<A> after) {
        return new ComposeToBigDecimal<>(before, after);
    }

    /**
     * Internal implementation of the {@link ComposedExpression}.
     *
     * @param <T>      the outer input type
     * @param <A>      the inner input type
     * @param <AFTER>  the expression type of the {@code after} operation
     */
    private final static class ComposeToBigDecimal<T, A, AFTER extends Function<A, BigDecimal> & Expression<A>>
        implements ComposedExpression<T, A>, ToBigDecimalNullable<T> {

        private final Function<T, A> before;
        private final AFTER after;

        ComposeToBigDecimal(Function<T, A> before, AFTER after) {
            this.before = requireNonNull(before);
            this.after  = requireNonNull(after);
        }

        @Override
        public Function<T, A> firstStep() {
            return before;
        }

        @Override
        public AFTER secondStep() {
            return after;
        }

        @Override
        public BigDecimal apply(T object) {
            final A intermediate = before.apply(object);
            if (intermediate == null) return null;
            return after.apply(intermediate);
        }
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A, E extends Enum<E>> ToEnumNullable<T, E> composeToEnum(Function<T, A> before, ToEnum<A, E> after) {
        return new ComposeToEnum<>(before, after, after.enumClass());
    }

    /**
     * Returns a new expression that first applies the {@code first} function
     * and then passes the result to the {@code second} expression.
     *
     * @param before the first function to apply
     * @param after  the expression to apply to the result
     * @param <T>  the type of the initial input
     * @param <A>  the type returned by {@code first}
     * @return  the composed expression
     */
    public static <T, A, E extends Enum<E>> ToEnumNullable<T, E> composeToEnumNullable(Function<T, A> before, ToEnumNullable<A, E> after) {
        return new ComposeToEnum<>(before, after, after.enumClass());
    }

    /**
     * Internal implementation of the {@link ComposedExpression}.
     *
     * @param <T>      the outer input type
     * @param <A>      the inner input type
     * @param <AFTER>  the expression type of the {@code after} operation
     */
    private final static class ComposeToEnum<T, A, E extends Enum<E>, AFTER extends Function<A, E> & Expression<A>>
    implements ComposedExpression<T, A>, ToEnumNullable<T, E> {

        private final Function<T, A> before;
        private final AFTER after;
        private final Class<E> enumClass;

        ComposeToEnum(Function<T, A> before, AFTER after, Class<E> enumClass) {
            this.before    = requireNonNull(before);
            this.after     = requireNonNull(after);
            this.enumClass = requireNonNull(enumClass);
        }

        @Override
        public Class<E> enumClass() {
            return enumClass;
        }

        @Override
        public Function<T, A> firstStep() {
            return before;
        }

        @Override
        public AFTER secondStep() {
            return after;
        }

        @Override
        public E apply(T object) {
            final A intermediate = before.apply(object);
            if (intermediate == null) return null;
            return after.apply(intermediate);
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private ComposedUtil() {}
}
