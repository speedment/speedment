package com.speedment.runtime.compute.internal.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.BinaryExpression;
import com.speedment.runtime.compute.expression.BinaryObjExpression;
import com.speedment.runtime.compute.expression.Expression;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Utility class used to construct expression that gives the sum of two
 * expressions.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class PlusUtil {

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
        class BytePlusByte extends AbstractPlusByte<T, ToByte<T>> implements ToShort<T> {
            private BytePlusByte(ToByte<T> first, byte second) {
                super(first, second);
            }

            @Override
            public short applyAsShort(T object) {
                return (short) (firstInner.applyAsByte(object) + secondInner);
            }
        }

        return new BytePlusByte(first, second);
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
        class BytePlusInt extends AbstractPlusInt<T, ToByte<T>> implements ToInt<T> {
            private BytePlusInt(ToByte<T> first, int second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsByte(object) + secondInner;
            }
        }

        return new BytePlusInt(first, second);
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
        class BytePlusLong extends AbstractPlusLong<T, ToByte<T>> implements ToLong<T> {
            private BytePlusLong(ToByte<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsByte(object) + secondInner;
            }
        }

        return new BytePlusLong(first, second);
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
        class BytePlusByte extends AbstractPlus<T, ToByte<T>, ToByte<T>> implements ToShort<T> {
            private BytePlusByte(ToByte<T> first, ToByte<T> second) {
                super(first, second);
            }

            @Override
            public short applyAsShort(T object) {
                return (short) (firstInner.applyAsByte(object)
                    + secondInner.applyAsByte(object));
            }
        }

        return new BytePlusByte(first, second);
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
        class ShortPlusShort extends AbstractPlusByte<T, ToShort<T>> implements ToInt<T> {
            private ShortPlusShort(ToShort<T> first, byte second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsShort(object) + secondInner;
            }
        }

        return new ShortPlusShort(first, second);
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
        class ShortPlusInt extends AbstractPlusInt<T, ToShort<T>> implements ToInt<T> {
            private ShortPlusInt(ToShort<T> first, int second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsShort(object) + secondInner;
            }
        }

        return new ShortPlusInt(first, second);
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
        class ShortPlusLong extends AbstractPlusLong<T, ToShort<T>> implements ToLong<T> {
            private ShortPlusLong(ToShort<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsShort(object) + secondInner;
            }
        }

        return new ShortPlusLong(first, second);
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
        class ShortPlusShort extends AbstractPlus<T, ToShort<T>, ToShort<T>> implements ToShort<T> {
            private ShortPlusShort(ToShort<T> first, ToShort<T> second) {
                super(first, second);
            }

            @Override
            public short applyAsShort(T object) {
                return (short) (firstInner.applyAsShort(object)
                    + secondInner.applyAsShort(object));
            }
        }

        return new ShortPlusShort(first, second);
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
        class IntPlusByte extends AbstractPlusByte<T, ToInt<T>> implements ToInt<T> {
            private IntPlusByte(ToInt<T> first, byte second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) + secondInner;
            }
        }

        return new IntPlusByte(first, second);
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
        class IntPlusInt extends AbstractPlusInt<T, ToInt<T>> implements ToInt<T> {
            private IntPlusInt(ToInt<T> first, int second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) + secondInner;
            }
        }

        return new IntPlusInt(first, second);
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
        class IntPlusLong extends AbstractPlusLong<T, ToInt<T>> implements ToLong<T> {
            private IntPlusLong(ToInt<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsInt(object) + secondInner;
            }
        }

        return new IntPlusLong(first, second);
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
        class IntPlusByte extends AbstractPlus<T, ToInt<T>, ToByte<T>> implements ToInt<T> {
            private IntPlusByte(ToInt<T> first, ToByte<T> second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) +
                    secondInner.applyAsByte(object);
            }
        }

        return new IntPlusByte(first, second);
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
        class IntPlusInt extends AbstractPlus<T, ToInt<T>, ToInt<T>> implements ToInt<T> {
            private IntPlusInt(ToInt<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) +
                    secondInner.applyAsInt(object);
            }
        }

        return new IntPlusInt(first, second);
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
        class LongPlusLong extends AbstractPlusByte<T, ToLong<T>> implements ToLong<T> {
            private LongPlusLong(ToLong<T> first, byte second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object) + secondInner;
            }
        }

        return new LongPlusLong(first, second);
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
        class LongPlusInt extends AbstractPlusInt<T, ToLong<T>> implements ToLong<T> {
            private LongPlusInt(ToLong<T> first, int second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object) + secondInner;
            }
        }

        return new LongPlusInt(first, second);
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
        class LongPlusLong extends AbstractPlusLong<T, ToLong<T>> implements ToLong<T> {
            private LongPlusLong(ToLong<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object) + secondInner;
            }
        }

        return new LongPlusLong(first, second);
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
        class LongPlusInt extends AbstractPlus<T, ToLong<T>, ToInt<T>> implements ToLong<T> {
            private LongPlusInt(ToLong<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object)
                    + secondInner.applyAsInt(object);
            }
        }

        return new LongPlusInt(first, second);
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
        class LongPlusLong extends AbstractPlus<T, ToLong<T>, ToLong<T>> implements ToLong<T> {
            private LongPlusLong(ToLong<T> first, ToLong<T> second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object)
                    + secondInner.applyAsLong(object);
            }
        }

        return new LongPlusLong(first, second);
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
        class FloatPlusInt extends AbstractPlusInt<T, ToFloat<T>> implements ToFloat<T> {
            private FloatPlusInt(ToFloat<T> first, int second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object) + secondInner;
            }
        }

        return new FloatPlusInt(first, second);
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
        class FloatPlusLong extends AbstractPlusLong<T, ToFloat<T>> implements ToDouble<T> {
            private FloatPlusLong(ToFloat<T> first, long second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsFloat(object) + secondInner;
            }
        }

        return new FloatPlusLong(first, second);
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
        class FloatPlusFloat extends AbstractPlusFloat<T, ToFloat<T>> implements ToFloat<T> {
            private FloatPlusFloat(ToFloat<T> first, float second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object) + secondInner;
            }
        }

        return new FloatPlusFloat(first, second);
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
        class FloatPlusInt extends AbstractPlus<T, ToFloat<T>, ToInt<T>> implements ToFloat<T> {
            private FloatPlusInt(ToFloat<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object)
                    + secondInner.applyAsInt(object);
            }
        }

        return new FloatPlusInt(first, second);
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
        class FloatPlusLong extends AbstractPlus<T, ToFloat<T>, ToLong<T>> implements ToDouble<T> {
            private FloatPlusLong(ToFloat<T> first, ToLong<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsFloat(object)
                    + secondInner.applyAsLong(object);
            }
        }

        return new FloatPlusLong(first, second);
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
        class FloatPlusFloat extends AbstractPlus<T, ToFloat<T>, ToFloat<T>> implements ToFloat<T> {
            private FloatPlusFloat(ToFloat<T> first, ToFloat<T> second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object)
                    + secondInner.applyAsFloat(object);
            }
        }

        return new FloatPlusFloat(first, second);
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
        class DoublePlusInt extends AbstractPlusInt<T, ToDouble<T>> implements ToDouble<T> {
            private DoublePlusInt(ToDouble<T> first, int second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object) + secondInner;
            }
        }

        return new DoublePlusInt(first, second);
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
        class DoublePlusLong extends AbstractPlusLong<T, ToDouble<T>> implements ToDouble<T> {
            private DoublePlusLong(ToDouble<T> first, long second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object) + secondInner;
            }
        }

        return new DoublePlusLong(first, second);
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
        class DoublePlusDouble extends AbstractPlusDouble<T, ToDouble<T>> implements ToDouble<T> {
            private DoublePlusDouble(ToDouble<T> first, double second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object) + secondInner;
            }
        }

        return new DoublePlusDouble(first, second);
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
        class DoublePlusInt extends AbstractPlus<T, ToDouble<T>, ToInt<T>> implements ToDouble<T> {
            private DoublePlusInt(ToDouble<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object)
                    + secondInner.applyAsInt(object);
            }
        }

        return new DoublePlusInt(first, second);
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
        class DoublePlusLong extends AbstractPlus<T, ToDouble<T>, ToLong<T>> implements ToDouble<T> {
            private DoublePlusLong(ToDouble<T> first, ToLong<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object)
                    + secondInner.applyAsLong(object);
            }
        }

        return new DoublePlusLong(first, second);
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
        class DoublePlusDouble extends AbstractPlus<T, ToDouble<T>, ToDouble<T>> implements ToDouble<T> {
            private DoublePlusDouble(ToDouble<T> first, ToDouble<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object)
                    + secondInner.applyAsDouble(object);
            }
        }

        return new DoublePlusDouble(first, second);
    }

    /**
     * Abstract base for a plus operation.
     *
     * @param <T>       the input entity type
     * @param <FIRST>   the first operand expression type
     * @param <SECOND>  the second operand expression type
     */
    private static abstract class AbstractPlus<T, FIRST extends Expression<T>, SECOND extends Expression<T>>
    implements BinaryExpression<T, FIRST, SECOND> {

        final FIRST firstInner;
        final SECOND secondInner;

        private AbstractPlus(FIRST first, SECOND second) {
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
            return Operator.PLUS;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryExpression)) return false;
            final BinaryExpression<?, ?, ?> that = (BinaryExpression<?, ?, ?>) o;
            return Objects.equals(firstInner, that.getFirst()) &&
                Objects.equals(secondInner, that.getSecond()) &&
                Objects.equals(getOperator(), that.getOperator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, getOperator());
        }
    }

    /**
     * Abstract base for a plus operation that takes a {@code byte} as the
     * second operand.
     *
     * @param <T>      the input entity type
     * @param <INNER>  the first operand expression type
     */
    private static abstract class AbstractPlusByte<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Byte> {

        final INNER firstInner;
        final byte secondInner;

        private AbstractPlusByte(INNER first, byte second) {
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
            return Operator.PLUS;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?, ?> that = (BinaryObjExpression<?, ?, ?>) o;
            return Objects.equals(firstInner, that.getFirst()) &&
                Objects.equals(secondInner, that.getSecond()) &&
                Objects.equals(getOperator(), that.getOperator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, getOperator());
        }
    }

    /**
     * Abstract base for a plus operation that takes an {@code int} as the
     * second operand.
     *
     * @param <T>      the input entity type
     * @param <INNER>  the first operand expression type
     */
    private static abstract class AbstractPlusInt<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Integer> {

        final INNER firstInner;
        final int secondInner;

        private AbstractPlusInt(INNER first, int second) {
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
            return Operator.PLUS;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?, ?> that = (BinaryObjExpression<?, ?, ?>) o;
            return Objects.equals(firstInner, that.getFirst()) &&
                Objects.equals(secondInner, that.getSecond()) &&
                Objects.equals(getOperator(), that.getOperator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, getOperator());
        }
    }

    /**
     * Abstract base for a plus operation that takes a {@code long} as the
     * second operand.
     *
     * @param <T>      the input entity type
     * @param <INNER>  the first operand expression type
     */
    private static abstract class AbstractPlusLong<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Long> {

        final INNER firstInner;
        final long secondInner;

        private AbstractPlusLong(INNER first, long second) {
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
            return Operator.PLUS;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?, ?> that = (BinaryObjExpression<?, ?, ?>) o;
            return Objects.equals(firstInner, that.getFirst()) &&
                Objects.equals(secondInner, that.getSecond()) &&
                Objects.equals(getOperator(), that.getOperator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, getOperator());
        }
    }

    /**
     * Abstract base for a plus operation that takes a {@code float} as the
     * second operand.
     *
     * @param <T>      the input entity type
     * @param <INNER>  the first operand expression type
     */
    private static abstract class AbstractPlusFloat<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Float> {

        final INNER firstInner;
        final float secondInner;

        private AbstractPlusFloat(INNER first, float second) {
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
            return Operator.PLUS;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?, ?> that = (BinaryObjExpression<?, ?, ?>) o;
            return Objects.equals(firstInner, that.getFirst()) &&
                Objects.equals(secondInner, that.getSecond()) &&
                Objects.equals(getOperator(), that.getOperator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, getOperator());
        }
    }

    /**
     * Abstract base for a plus operation that takes a {@code double} as the
     * second operand.
     *
     * @param <T>      the input type
     * @param <INNER>  the first operand expression type
     */
    private static abstract class AbstractPlusDouble<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Double> {

        final INNER firstInner;
        final double secondInner;

        private AbstractPlusDouble(INNER first, double second) {
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
            return Operator.PLUS;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?, ?> that = (BinaryObjExpression<?, ?, ?>) o;
            return Objects.equals(firstInner, that.getFirst()) &&
                Objects.equals(secondInner, that.getSecond()) &&
                Objects.equals(getOperator(), that.getOperator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, getOperator());
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private PlusUtil() {}
}
