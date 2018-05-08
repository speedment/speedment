package com.speedment.runtime.compute.internal.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.ComposedExpression;
import com.speedment.runtime.compute.expression.ComposedPredicate;
import com.speedment.runtime.compute.expression.Expression;
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
        public final ComposedPredicate<T, A> isNull() {
            return new IsNull<>(first);
        }

        @Override
        public final ComposedPredicate<T, A> isNotNull() {
            return new IsNotNull<>(first);
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

    private final static class IsNull<T, A>
    implements ComposedPredicate<T, A> {

        private final Function<T, A> innerMapper;
        private final Predicate<A> innerPredicate;

        IsNull(Function<T, A> innerMapper) {
            this.innerMapper    = requireNonNull(innerMapper);
            this.innerPredicate = Objects::isNull;
        }

        @Override
        public SpecialTypes specialType() {
            return SpecialTypes.IS_NULL;
        }

        @Override
        public boolean test(T t) {
            return innerMapper.apply(t) == null;
        }

        @Override
        public Function<T, A> innerMapper() {
            return innerMapper;
        }

        @Override
        public Predicate<A> innerPredicate() {
            return innerPredicate;
        }
    }

    private final static class IsNotNull<T, A>
    implements ComposedPredicate<T, A> {

        private final Function<T, A> innerMapper;
        private final Predicate<A> innerPredicate;

        IsNotNull(Function<T, A> innerMapper) {
            this.innerMapper    = requireNonNull(innerMapper);
            this.innerPredicate = Objects::nonNull;
        }

        @Override
        public SpecialTypes specialType() {
            return SpecialTypes.IS_NOT_NULL;
        }

        @Override
        public boolean test(T t) {
            return innerMapper.apply(t) != null;
        }

        @Override
        public Function<T, A> innerMapper() {
            return innerMapper;
        }

        @Override
        public Predicate<A> innerPredicate() {
            return innerPredicate;
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private ComposedUtil() {}
}
