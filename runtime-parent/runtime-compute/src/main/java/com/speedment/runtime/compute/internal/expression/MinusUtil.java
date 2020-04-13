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
package com.speedment.runtime.compute.internal.expression;

import static java.util.Objects.requireNonNull;

import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToFloat;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToLong;
import com.speedment.runtime.compute.ToShort;
import com.speedment.runtime.compute.expression.BinaryExpression;
import com.speedment.runtime.compute.expression.BinaryObjExpression;
import com.speedment.runtime.compute.expression.Expression;

import java.util.Objects;

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
    public static <T> ToShort<T> byteMinusByte(ToByte<T> first, byte second) {
        class ByteMinusByte extends AbstractMinusByte<T, ToByte<T>> implements ToShort<T> {
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
    public static <T> ToInt<T> byteMinusInt(ToByte<T> first, int second) {
        class ByteMinusInt extends AbstractMinusInt<T, ToByte<T>> implements ToInt<T> {
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
    public static <T> ToLong<T> byteMinusLong(ToByte<T> first, long second) {
        class ByteMinusLong extends AbstractMinusLong<T, ToByte<T>> implements ToLong<T> {
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
    public static <T> ToShort<T> byteMinusByte(ToByte<T> first, ToByte<T> second) {
        class ByteMinusByte extends AbstractMinus<T, ToByte<T>, ToByte<T>> implements ToShort<T> {
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
    public static <T> ToInt<T> shortMinusByte(ToShort<T> first, byte second) {
        class ShortMinusShort extends AbstractMinusByte<T, ToShort<T>> implements ToInt<T> {
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
    public static <T> ToInt<T> shortMinusInt(ToShort<T> first, int second) {
        class ShortMinusInt extends AbstractMinusInt<T, ToShort<T>> implements ToInt<T> {
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
    public static <T> ToLong<T> shortMinusLong(ToShort<T> first, long second) {
        class ShortMinusLong extends AbstractMinusLong<T, ToShort<T>> implements ToLong<T> {
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
    public static <T> ToShort<T> shortMinusShort(ToShort<T> first, ToShort<T> second) {
        class ShortMinusShort extends AbstractMinus<T, ToShort<T>, ToShort<T>> implements ToShort<T> {
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
    public static <T> ToInt<T> intMinusByte(ToInt<T> first, byte second) {
        class IntMinusByte extends AbstractMinusByte<T, ToInt<T>> implements ToInt<T> {
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
    public static <T> ToInt<T> intMinusInt(ToInt<T> first, int second) {
        class IntMinusInt extends AbstractMinusInt<T, ToInt<T>> implements ToInt<T> {
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
    public static <T> ToLong<T> intMinusLong(ToInt<T> first, long second) {
        class IntMinusLong extends AbstractMinusLong<T, ToInt<T>> implements ToLong<T> {
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
    public static <T> ToInt<T> intMinusByte(ToInt<T> first, ToByte<T> second) {
        class IntMinusByte extends AbstractMinus<T, ToInt<T>, ToByte<T>> implements ToInt<T> {
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
    public static <T> ToInt<T> intMinusInt(ToInt<T> first, ToInt<T> second) {
        class IntMinusInt extends AbstractMinus<T, ToInt<T>, ToInt<T>> implements ToInt<T> {
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
    public static <T> ToLong<T> longMinusByte(ToLong<T> first, byte second) {
        class LongMinusLong extends AbstractMinusByte<T, ToLong<T>> implements ToLong<T> {
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
    public static <T> ToLong<T> longMinusInt(ToLong<T> first, int second) {
        class LongMinusInt extends AbstractMinusInt<T, ToLong<T>> implements ToLong<T> {
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
    public static <T> ToLong<T> longMinusLong(ToLong<T> first, long second) {
        class LongMinusLong extends AbstractMinusLong<T, ToLong<T>> implements ToLong<T> {
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
    public static <T> ToLong<T> longMinusInt(ToLong<T> first, ToInt<T> second) {
        class LongMinusInt extends AbstractMinus<T, ToLong<T>, ToInt<T>> implements ToLong<T> {
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
    public static <T> ToLong<T> longMinusLong(ToLong<T> first, ToLong<T> second) {
        class LongMinusLong extends AbstractMinus<T, ToLong<T>, ToLong<T>> implements ToLong<T> {
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
    public static <T> ToFloat<T> floatMinusInt(ToFloat<T> first, int second) {
        class FloatMinusInt extends AbstractMinusInt<T, ToFloat<T>> implements ToFloat<T> {
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
    public static <T> ToDouble<T> floatMinusLong(ToFloat<T> first, long second) {
        class FloatMinusLong extends AbstractMinusLong<T, ToFloat<T>> implements ToDouble<T> {
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
    public static <T> ToFloat<T> floatMinusFloat(ToFloat<T> first, float second) {
        class FloatMinusFloat extends AbstractMinusFloat<T, ToFloat<T>> implements ToFloat<T> {
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
    public static <T> ToFloat<T> floatMinusInt(ToFloat<T> first, ToInt<T> second) {
        class FloatMinusInt extends AbstractMinus<T, ToFloat<T>, ToInt<T>> implements ToFloat<T> {
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
    public static <T> ToDouble<T> floatMinusLong(ToFloat<T> first, ToLong<T> second) {
        class FloatMinusLong extends AbstractMinus<T, ToFloat<T>, ToLong<T>> implements ToDouble<T> {
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
    public static <T> ToFloat<T> floatMinusFloat(ToFloat<T> first, ToFloat<T> second) {
        class FloatMinusFloat extends AbstractMinus<T, ToFloat<T>, ToFloat<T>> implements ToFloat<T> {
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
    public static <T> ToDouble<T> doubleMinusInt(ToDouble<T> first, int second) {
        class DoubleMinusInt extends AbstractMinusInt<T, ToDouble<T>> implements ToDouble<T> {
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
    public static <T> ToDouble<T> doubleMinusLong(ToDouble<T> first, long second) {
        class DoubleMinusLong extends AbstractMinusLong<T, ToDouble<T>> implements ToDouble<T> {
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
    public static <T> ToDouble<T> doubleMinusDouble(ToDouble<T> first, double second) {
        class DoubleMinusDouble extends AbstractMinusDouble<T, ToDouble<T>> implements ToDouble<T> {
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
    public static <T> ToDouble<T> doubleMinusInt(ToDouble<T> first, ToInt<T> second) {
        class DoubleMinusInt extends AbstractMinus<T, ToDouble<T>, ToInt<T>> implements ToDouble<T> {
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
    public static <T> ToDouble<T> doubleMinusLong(ToDouble<T> first, ToLong<T> second) {
        class DoubleMinusLong extends AbstractMinus<T, ToDouble<T>, ToLong<T>> implements ToDouble<T> {
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
    public static <T> ToDouble<T> doubleMinusDouble(ToDouble<T> first, ToDouble<T> second) {
        class DoubleMinusDouble extends AbstractMinus<T, ToDouble<T>, ToDouble<T>> implements ToDouble<T> {
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
     * @param <T>       the input type
     * @param <FIRST>   the first operand expression type
     * @param <SECOND>  the second operand expression type
     */
    abstract static class AbstractMinus<T, FIRST extends Expression<T>, SECOND extends Expression<T>>
    implements BinaryExpression<T, FIRST, SECOND> {

        final FIRST firstInner;
        final SECOND secondInner;

        AbstractMinus(FIRST first, SECOND second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = requireNonNull(second);
        }

        @Override
        public final FIRST first() {
            return firstInner;
        }

        @Override
        public final SECOND second() {
            return secondInner;
        }

        @Override
        public final Operator operator() {
            return Operator.MINUS;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryExpression)) return false;
            final BinaryExpression<?, ?, ?> that = (BinaryExpression<?, ?, ?>) o;
            return Objects.equals(firstInner, that.first()) &&
                Objects.equals(secondInner, that.second()) &&
                Objects.equals(operator(), that.operator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, operator());
        }
    }

    /**
     * Abstract base for a minus operation that takes a {@code byte} as the
     * second operand.
     *
     * @param <T>      the input type
     * @param <INNER>  the first operand expression type
     */
    abstract static class AbstractMinusByte<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Byte> {

        final INNER firstInner;
        final byte secondInner;

        AbstractMinusByte(INNER first, byte second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = second;
        }

        @Override
        public final INNER first() {
            return firstInner;
        }

        @Override
        public final Byte second() {
            return secondInner;
        }

        @Override
        public final Operator operator() {
            return Operator.MINUS;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?, ?> that = (BinaryObjExpression<?, ?, ?>) o;
            return Objects.equals(firstInner, that.first()) &&
                Objects.equals(secondInner, that.second()) &&
                Objects.equals(operator(), that.operator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, operator());
        }
    }

    /**
     * Abstract base for a minus operation that takes an {@code int} as the
     * second operand.
     *
     * @param <T>      the input type
     * @param <INNER>  the first operand expression type
     */
    abstract static class AbstractMinusInt<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Integer> {

        final INNER firstInner;
        final int secondInner;

        AbstractMinusInt(INNER first, int second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = second;
        }

        @Override
        public final INNER first() {
            return firstInner;
        }

        @Override
        public final Integer second() {
            return secondInner;
        }

        @Override
        public final Operator operator() {
            return Operator.MINUS;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?, ?> that = (BinaryObjExpression<?, ?, ?>) o;
            return Objects.equals(firstInner, that.first()) &&
                Objects.equals(secondInner, that.second()) &&
                Objects.equals(operator(), that.operator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, operator());
        }
    }

    /**
     * Abstract base for a minus operation that takes a {@code long} as the
     * second operand.
     *
     * @param <T>      the input type
     * @param <INNER>  the first operand expression type
     */
    abstract static class AbstractMinusLong<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Long> {

        final INNER firstInner;
        final long secondInner;

        AbstractMinusLong(INNER first, long second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = second;
        }

        @Override
        public final INNER first() {
            return firstInner;
        }

        @Override
        public final Long second() {
            return secondInner;
        }

        @Override
        public final Operator operator() {
            return Operator.MINUS;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?, ?> that = (BinaryObjExpression<?, ?, ?>) o;
            return Objects.equals(firstInner, that.first()) &&
                Objects.equals(secondInner, that.second()) &&
                Objects.equals(operator(), that.operator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, operator());
        }
    }

    /**
     * Abstract base for a minus operation that takes a {@code float} as the
     * second operand.
     *
     * @param <T>      the input type
     * @param <INNER>  the first operand expression type
     */
    abstract static class AbstractMinusFloat<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Float> {

        final INNER firstInner;
        final float secondInner;

        AbstractMinusFloat(INNER first, float second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = second;
        }

        @Override
        public final INNER first() {
            return firstInner;
        }

        @Override
        public final Float second() {
            return secondInner;
        }

        @Override
        public final Operator operator() {
            return Operator.MINUS;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?, ?> that = (BinaryObjExpression<?, ?, ?>) o;
            return Objects.equals(firstInner, that.first()) &&
                Objects.equals(secondInner, that.second()) &&
                Objects.equals(operator(), that.operator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, operator());
        }
    }

    /**
     * Abstract base for a minus operation that takes a {@code double} as the
     * second operand.
     *
     * @param <T>      the input type
     * @param <INNER>  the first operand expression type
     */
    abstract static class AbstractMinusDouble<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Double> {

        final INNER firstInner;
        final double secondInner;

        AbstractMinusDouble(INNER first, double second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = second;
        }

        @Override
        public final INNER first() {
            return firstInner;
        }

        @Override
        public final Double second() {
            return secondInner;
        }

        @Override
        public final Operator operator() {
            return Operator.MINUS;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?, ?> that = (BinaryObjExpression<?, ?, ?>) o;
            return Objects.equals(firstInner, that.first()) &&
                Objects.equals(secondInner, that.second()) &&
                Objects.equals(operator(), that.operator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, operator());
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private MinusUtil() {}
}
