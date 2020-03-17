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
    public static <T> ToShort<T> bytePlusByte(ToByte<T> first, byte second) {
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
    public static <T> ToInt<T> bytePlusInt(ToByte<T> first, int second) {
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
    public static <T> ToLong<T> bytePlusLong(ToByte<T> first, long second) {
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
    public static <T> ToShort<T> bytePlusByte(ToByte<T> first, ToByte<T> second) {
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
    public static <T> ToInt<T> shortPlusByte(ToShort<T> first, byte second) {
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
    public static <T> ToInt<T> shortPlusInt(ToShort<T> first, int second) {
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
    public static <T> ToLong<T> shortPlusLong(ToShort<T> first, long second) {
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
    public static <T> ToShort<T> shortPlusShort(ToShort<T> first, ToShort<T> second) {
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
    public static <T> ToInt<T> intPlusByte(ToInt<T> first, byte second) {
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
    public static <T> ToInt<T> intPlusInt(ToInt<T> first, int second) {
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
    public static <T> ToLong<T> intPlusLong(ToInt<T> first, long second) {
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
    public static <T> ToInt<T> intPlusByte(ToInt<T> first, ToByte<T> second) {
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
    public static <T> ToInt<T> intPlusInt(ToInt<T> first, ToInt<T> second) {
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
    public static <T> ToLong<T> longPlusByte(ToLong<T> first, byte second) {
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
    public static <T> ToLong<T> longPlusInt(ToLong<T> first, int second) {
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
    public static <T> ToLong<T> longPlusLong(ToLong<T> first, long second) {
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
    public static <T> ToLong<T> longPlusInt(ToLong<T> first, ToInt<T> second) {
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
    public static <T> ToLong<T> longPlusLong(ToLong<T> first, ToLong<T> second) {
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
    public static <T> ToFloat<T> floatPlusInt(ToFloat<T> first, int second) {
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
    public static <T> ToDouble<T> floatPlusLong(ToFloat<T> first, long second) {
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
    public static <T> ToFloat<T> floatPlusFloat(ToFloat<T> first, float second) {
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
    public static <T> ToFloat<T> floatPlusInt(ToFloat<T> first, ToInt<T> second) {
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
    public static <T> ToDouble<T> floatPlusLong(ToFloat<T> first, ToLong<T> second) {
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
    public static <T> ToFloat<T> floatPlusFloat(ToFloat<T> first, ToFloat<T> second) {
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
    public static <T> ToDouble<T> doublePlusInt(ToDouble<T> first, int second) {
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
    public static <T> ToDouble<T> doublePlusLong(ToDouble<T> first, long second) {
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
    public static <T> ToDouble<T> doublePlusDouble(ToDouble<T> first, double second) {
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
    public static <T> ToDouble<T> doublePlusInt(ToDouble<T> first, ToInt<T> second) {
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
    public static <T> ToDouble<T> doublePlusLong(ToDouble<T> first, ToLong<T> second) {
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
    public static <T> ToDouble<T> doublePlusDouble(ToDouble<T> first, ToDouble<T> second) {
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
    abstract static class AbstractPlus<T, FIRST extends Expression<T>, SECOND extends Expression<T>>
    implements BinaryExpression<T, FIRST, SECOND> {

        final FIRST firstInner;
        final SECOND secondInner;

        AbstractPlus(FIRST first, SECOND second) {
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
            return Operator.PLUS;
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
     * Abstract base for a plus operation that takes a {@code byte} as the
     * second operand.
     *
     * @param <T>      the input entity type
     * @param <INNER>  the first operand expression type
     */
    abstract static class AbstractPlusByte<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Byte> {

        final INNER firstInner;
        final byte secondInner;

        AbstractPlusByte(INNER first, byte second) {
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
            return Operator.PLUS;
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
     * Abstract base for a plus operation that takes an {@code int} as the
     * second operand.
     *
     * @param <T>      the input entity type
     * @param <INNER>  the first operand expression type
     */
    abstract static class AbstractPlusInt<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Integer> {

        final INNER firstInner;
        final int secondInner;

        AbstractPlusInt(INNER first, int second) {
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
            return Operator.PLUS;
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
     * Abstract base for a plus operation that takes a {@code long} as the
     * second operand.
     *
     * @param <T>      the input entity type
     * @param <INNER>  the first operand expression type
     */
    abstract static class AbstractPlusLong<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Long> {

        final INNER firstInner;
        final long secondInner;

        AbstractPlusLong(INNER first, long second) {
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
            return Operator.PLUS;
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
     * Abstract base for a plus operation that takes a {@code float} as the
     * second operand.
     *
     * @param <T>      the input entity type
     * @param <INNER>  the first operand expression type
     */
    abstract static class AbstractPlusFloat<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Float> {

        final INNER firstInner;
        final float secondInner;

        AbstractPlusFloat(INNER first, float second) {
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
            return Operator.PLUS;
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
     * Abstract base for a plus operation that takes a {@code double} as the
     * second operand.
     *
     * @param <T>      the input type
     * @param <INNER>  the first operand expression type
     */
    abstract static class AbstractPlusDouble<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Double> {

        final INNER firstInner;
        final double secondInner;

        AbstractPlusDouble(INNER first, double second) {
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
            return Operator.PLUS;
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
    private PlusUtil() {}
}
