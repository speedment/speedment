package com.speedment.runtime.compute.internal.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.BinaryExpression;
import com.speedment.runtime.compute.expression.BinaryObjExpression;
import com.speedment.runtime.compute.expression.Expression;

import static java.util.Objects.requireNonNull;

/**
 * Utility class used to construct expression that gives the sum of two
 * expressions.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class MinusUtil {

    /**
     * Creates and returns an expression that takes the result of the expression
     * and adds a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToShort<T> minus(ToByte<T> first, byte second) {
        class ByteMinusByte extends AbstractMinusByte<ToByte<T>> implements ToShort<T> {
            private ByteMinusByte(ToByte<T> first, byte second) {
                super(first, second);
            }

            @Override
            public short applyAsShort(T object) {
                return (short) (firstInner.applyAsByte(object) - secondInner);
            }
        }

        return new ByteMinusByte(first, second);
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
    public static <T> ToInt<T> minus(ToByte<T> first, int second) {
        class ByteMinusInt extends AbstractMinusInt<ToByte<T>> implements ToInt<T> {
            private ByteMinusInt(ToByte<T> first, int second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsByte(object) - secondInner;
            }
        }

        return new ByteMinusInt(first, second);
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
    public static <T> ToLong<T> minus(ToByte<T> first, long second) {
        class ByteMinusLong extends AbstractMinusLong<ToByte<T>> implements ToLong<T> {
            private ByteMinusLong(ToByte<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsByte(object) - secondInner;
            }
        }

        return new ByteMinusLong(first, second);
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
    public static <T> ToShort<T> minus(ToByte<T> first, ToByte<T> second) {
        class ByteMinusByte extends AbstractMinus<ToByte<T>, ToByte<T>> implements ToShort<T> {
            private ByteMinusByte(ToByte<T> first, ToByte<T> second) {
                super(first, second);
            }

            @Override
            public short applyAsShort(T object) {
                return (short) (firstInner.applyAsByte(object)
                    - secondInner.applyAsByte(object));
            }
        }

        return new ByteMinusByte(first, second);
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
    public static <T> ToInt<T> minus(ToShort<T> first, byte second) {
        class ShortMinusShort extends AbstractMinusByte<ToShort<T>> implements ToInt<T> {
            private ShortMinusShort(ToShort<T> first, byte second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsShort(object) - secondInner;
            }
        }

        return new ShortMinusShort(first, second);
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
    public static <T> ToInt<T> minus(ToShort<T> first, int second) {
        class ShortMinusInt extends AbstractMinusInt<ToShort<T>> implements ToInt<T> {
            private ShortMinusInt(ToShort<T> first, int second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsShort(object) - secondInner;
            }
        }

        return new ShortMinusInt(first, second);
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
    public static <T> ToLong<T> minus(ToShort<T> first, long second) {
        class ShortMinusLong extends AbstractMinusLong<ToShort<T>> implements ToLong<T> {
            private ShortMinusLong(ToShort<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsShort(object) - secondInner;
            }
        }

        return new ShortMinusLong(first, second);
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
    public static <T> ToShort<T> minus(ToShort<T> first, ToShort<T> second) {
        class ShortMinusShort extends AbstractMinus<ToShort<T>, ToShort<T>> implements ToShort<T> {
            private ShortMinusShort(ToShort<T> first, ToShort<T> second) {
                super(first, second);
            }

            @Override
            public short applyAsShort(T object) {
                return (short) (firstInner.applyAsShort(object)
                    - secondInner.applyAsShort(object));
            }
        }

        return new ShortMinusShort(first, second);
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
    public static <T> ToInt<T> minus(ToInt<T> first, byte second) {
        class IntMinusByte extends AbstractMinusByte<ToInt<T>> implements ToInt<T> {
            private IntMinusByte(ToInt<T> first, byte second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) - secondInner;
            }
        }

        return new IntMinusByte(first, second);
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
    public static <T> ToInt<T> minus(ToInt<T> first, int second) {
        class IntMinusInt extends AbstractMinusInt<ToInt<T>> implements ToInt<T> {
            private IntMinusInt(ToInt<T> first, int second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) - secondInner;
            }
        }

        return new IntMinusInt(first, second);
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
    public static <T> ToLong<T> minus(ToInt<T> first, long second) {
        class IntMinusLong extends AbstractMinusLong<ToInt<T>> implements ToLong<T> {
            private IntMinusLong(ToInt<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsInt(object) - secondInner;
            }
        }

        return new IntMinusLong(first, second);
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
    public static <T> ToInt<T> minus(ToInt<T> first, ToByte<T> second) {
        class IntMinusByte extends AbstractMinus<ToInt<T>, ToByte<T>> implements ToInt<T> {
            private IntMinusByte(ToInt<T> first, ToByte<T> second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) -
                    secondInner.applyAsByte(object);
            }
        }

        return new IntMinusByte(first, second);
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
    public static <T> ToInt<T> minus(ToInt<T> first, ToInt<T> second) {
        class IntMinusInt extends AbstractMinus<ToInt<T>, ToInt<T>> implements ToInt<T> {
            private IntMinusInt(ToInt<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) -
                    secondInner.applyAsInt(object);
            }
        }

        return new IntMinusInt(first, second);
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
    public static <T> ToLong<T> minus(ToLong<T> first, byte second) {
        class LongMinusLong extends AbstractMinusByte<ToLong<T>> implements ToLong<T> {
            private LongMinusLong(ToLong<T> first, byte second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object) - secondInner;
            }
        }

        return new LongMinusLong(first, second);
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
    public static <T> ToLong<T> minus(ToLong<T> first, int second) {
        class LongMinusInt extends AbstractMinusInt<ToLong<T>> implements ToLong<T> {
            private LongMinusInt(ToLong<T> first, int second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object) - secondInner;
            }
        }

        return new LongMinusInt(first, second);
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
    public static <T> ToLong<T> minus(ToLong<T> first, long second) {
        class LongMinusLong extends AbstractMinusLong<ToLong<T>> implements ToLong<T> {
            private LongMinusLong(ToLong<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object) - secondInner;
            }
        }

        return new LongMinusLong(first, second);
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
    public static <T> ToLong<T> minus(ToLong<T> first, ToInt<T> second) {
        class LongMinusInt extends AbstractMinus<ToLong<T>, ToInt<T>> implements ToLong<T> {
            private LongMinusInt(ToLong<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object)
                    - secondInner.applyAsInt(object);
            }
        }

        return new LongMinusInt(first, second);
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
    public static <T> ToLong<T> minus(ToLong<T> first, ToLong<T> second) {
        class LongMinusLong extends AbstractMinus<ToLong<T>, ToLong<T>> implements ToLong<T> {
            private LongMinusLong(ToLong<T> first, ToLong<T> second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object)
                    - secondInner.applyAsLong(object);
            }
        }

        return new LongMinusLong(first, second);
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
    public static <T> ToFloat<T> minus(ToFloat<T> first, int second) {
        class FloatMinusInt extends AbstractMinusInt<ToFloat<T>> implements ToFloat<T> {
            private FloatMinusInt(ToFloat<T> first, int second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object) - secondInner;
            }
        }

        return new FloatMinusInt(first, second);
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
    public static <T> ToDouble<T> minus(ToFloat<T> first, long second) {
        class FloatMinusLong extends AbstractMinusLong<ToFloat<T>> implements ToDouble<T> {
            private FloatMinusLong(ToFloat<T> first, long second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsFloat(object) - secondInner;
            }
        }

        return new FloatMinusLong(first, second);
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
    public static <T> ToFloat<T> minus(ToFloat<T> first, float second) {
        class FloatMinusFloat extends AbstractMinusFloat<ToFloat<T>> implements ToFloat<T> {
            private FloatMinusFloat(ToFloat<T> first, float second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object) - secondInner;
            }
        }

        return new FloatMinusFloat(first, second);
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
    public static <T> ToFloat<T> minus(ToFloat<T> first, ToInt<T> second) {
        class FloatMinusInt extends AbstractMinus<ToFloat<T>, ToInt<T>> implements ToFloat<T> {
            private FloatMinusInt(ToFloat<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object)
                    - secondInner.applyAsInt(object);
            }
        }

        return new FloatMinusInt(first, second);
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
    public static <T> ToDouble<T> minus(ToFloat<T> first, ToLong<T> second) {
        class FloatMinusLong extends AbstractMinus<ToFloat<T>, ToLong<T>> implements ToDouble<T> {
            private FloatMinusLong(ToFloat<T> first, ToLong<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsFloat(object)
                    - secondInner.applyAsLong(object);
            }
        }

        return new FloatMinusLong(first, second);
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
    public static <T> ToFloat<T> minus(ToFloat<T> first, ToFloat<T> second) {
        class FloatMinusFloat extends AbstractMinus<ToFloat<T>, ToFloat<T>> implements ToFloat<T> {
            private FloatMinusFloat(ToFloat<T> first, ToFloat<T> second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object)
                    - secondInner.applyAsFloat(object);
            }
        }

        return new FloatMinusFloat(first, second);
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
    public static <T> ToDouble<T> minus(ToDouble<T> first, int second) {
        class DoubleMinusInt extends AbstractMinusInt<ToDouble<T>> implements ToDouble<T> {
            private DoubleMinusInt(ToDouble<T> first, int second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object) - secondInner;
            }
        }

        return new DoubleMinusInt(first, second);
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
    public static <T> ToDouble<T> minus(ToDouble<T> first, long second) {
        class DoubleMinusLong extends AbstractMinusLong<ToDouble<T>> implements ToDouble<T> {
            private DoubleMinusLong(ToDouble<T> first, long second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object) - secondInner;
            }
        }

        return new DoubleMinusLong(first, second);
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
    public static <T> ToDouble<T> minus(ToDouble<T> first, double second) {
        class DoubleMinusDouble extends AbstractMinusDouble<ToDouble<T>> implements ToDouble<T> {
            private DoubleMinusDouble(ToDouble<T> first, double second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object) - secondInner;
            }
        }

        return new DoubleMinusDouble(first, second);
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
    public static <T> ToDouble<T> minus(ToDouble<T> first, ToInt<T> second) {
        class DoubleMinusInt extends AbstractMinus<ToDouble<T>, ToInt<T>> implements ToDouble<T> {
            private DoubleMinusInt(ToDouble<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object)
                    - secondInner.applyAsInt(object);
            }
        }

        return new DoubleMinusInt(first, second);
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
    public static <T> ToDouble<T> minus(ToDouble<T> first, ToLong<T> second) {
        class DoubleMinusLong extends AbstractMinus<ToDouble<T>, ToLong<T>> implements ToDouble<T> {
            private DoubleMinusLong(ToDouble<T> first, ToLong<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object)
                    - secondInner.applyAsLong(object);
            }
        }

        return new DoubleMinusLong(first, second);
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
    public static <T> ToDouble<T> minus(ToDouble<T> first, ToDouble<T> second) {
        class DoubleMinusDouble extends AbstractMinus<ToDouble<T>, ToDouble<T>> implements ToDouble<T> {
            private DoubleMinusDouble(ToDouble<T> first, ToDouble<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object)
                    - secondInner.applyAsDouble(object);
            }
        }

        return new DoubleMinusDouble(first, second);
    }

    /**
     * Abstract base for a minus operation.
     *
     * @param <FIRST>   the first operand expression type
     * @param <SECOND>  the second operand expression type
     */
    private static abstract class AbstractMinus<FIRST extends Expression, SECOND extends Expression>
        implements BinaryExpression<FIRST, SECOND> {

        final FIRST firstInner;
        final SECOND secondInner;

        private AbstractMinus(FIRST first, SECOND second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = requireNonNull(second);
        }

        @Override
        public final FIRST getFirst() {
            return firstInner;
        }

        @Override
        public final SECOND getSecond() {
            return secondInner;
        }

        @Override
        public final Operator getOperator() {
            return Operator.MINUS;
        }
    }

    /**
     * Abstract base for a minus operation that takes a {@code byte} as the
     * second operand.
     *
     * @param <INNER>  the first operand expression type
     */
    private static abstract class AbstractMinusByte<INNER extends Expression>
        implements BinaryObjExpression<INNER, Byte> {

        final INNER firstInner;
        final byte secondInner;

        private AbstractMinusByte(INNER first, byte second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = second;
        }

        @Override
        public final INNER getFirst() {
            return firstInner;
        }

        @Override
        public final Byte getSecond() {
            return secondInner;
        }

        @Override
        public final Operator getOperator() {
            return Operator.MINUS;
        }
    }

    /**
     * Abstract base for a minus operation that takes an {@code int} as the
     * second operand.
     *
     * @param <INNER>  the first operand expression type
     */
    private static abstract class AbstractMinusInt<INNER extends Expression>
        implements BinaryObjExpression<INNER, Integer> {

        final INNER firstInner;
        final int secondInner;

        private AbstractMinusInt(INNER first, int second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = second;
        }

        @Override
        public final INNER getFirst() {
            return firstInner;
        }

        @Override
        public final Integer getSecond() {
            return secondInner;
        }

        @Override
        public final Operator getOperator() {
            return Operator.MINUS;
        }
    }

    /**
     * Abstract base for a minus operation that takes a {@code long} as the
     * second operand.
     *
     * @param <INNER>  the first operand expression type
     */
    private static abstract class AbstractMinusLong<INNER extends Expression>
        implements BinaryObjExpression<INNER, Long> {

        final INNER firstInner;
        final long secondInner;

        private AbstractMinusLong(INNER first, long second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = second;
        }

        @Override
        public final INNER getFirst() {
            return firstInner;
        }

        @Override
        public final Long getSecond() {
            return secondInner;
        }

        @Override
        public final Operator getOperator() {
            return Operator.MINUS;
        }
    }

    /**
     * Abstract base for a minus operation that takes a {@code float} as the
     * second operand.
     *
     * @param <INNER>  the first operand expression type
     */
    private static abstract class AbstractMinusFloat<INNER extends Expression>
        implements BinaryObjExpression<INNER, Float> {

        final INNER firstInner;
        final float secondInner;

        private AbstractMinusFloat(INNER first, float second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = second;
        }

        @Override
        public final INNER getFirst() {
            return firstInner;
        }

        @Override
        public final Float getSecond() {
            return secondInner;
        }

        @Override
        public final Operator getOperator() {
            return Operator.MINUS;
        }
    }

    /**
     * Abstract base for a minus operation that takes a {@code double} as the
     * second operand.
     *
     * @param <INNER>  the first operand expression type
     */
    private static abstract class AbstractMinusDouble<INNER extends Expression>
        implements BinaryObjExpression<INNER, Double> {

        final INNER firstInner;
        final double secondInner;

        private AbstractMinusDouble(INNER first, double second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = second;
        }

        @Override
        public final INNER getFirst() {
            return firstInner;
        }

        @Override
        public final Double getSecond() {
            return secondInner;
        }

        @Override
        public final Operator getOperator() {
            return Operator.MINUS;
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private MinusUtil() {}
}
