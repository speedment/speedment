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
public final class MultiplyUtil {

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> byteMultiplyByte(ToByte<T> first, byte second) {
        class ByteMultiplyByte extends AbstractMultiplyByte<T, ToByte<T>> implements ToInt<T> {
            private ByteMultiplyByte(ToByte<T> first, byte second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsByte(object) * secondInner;
            }
        }

        return new ByteMultiplyByte(first, second);
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
    public static <T> ToInt<T> byteMultiplyInt(ToByte<T> first, int second) {
        class ByteMultiplyInt extends AbstractMultiplyInt<T, ToByte<T>> implements ToInt<T> {
            private ByteMultiplyInt(ToByte<T> first, int second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsByte(object) * secondInner;
            }
        }

        return new ByteMultiplyInt(first, second);
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
    public static <T> ToLong<T> byteMultiplyLong(ToByte<T> first, long second) {
        class ByteMultiplyLong extends AbstractMultiplyLong<T, ToByte<T>> implements ToLong<T> {
            private ByteMultiplyLong(ToByte<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsByte(object) * secondInner;
            }
        }

        return new ByteMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> byteMultiplyByte(ToByte<T> first, ToByte<T> second) {
        class ByteMultiplyByte extends AbstractMultiply<T, ToByte<T>, ToByte<T>> implements ToInt<T> {
            private ByteMultiplyByte(ToByte<T> first, ToByte<T> second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsByte(object)
                    * secondInner.applyAsByte(object);
            }
        }

        return new ByteMultiplyByte(first, second);
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
    public static <T> ToInt<T> shortMultiplyByte(ToShort<T> first, byte second) {
        class ShortMultiplyShort extends AbstractMultiplyByte<T, ToShort<T>> implements ToInt<T> {
            private ShortMultiplyShort(ToShort<T> first, byte second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsShort(object) * secondInner;
            }
        }

        return new ShortMultiplyShort(first, second);
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
    public static <T> ToInt<T> shortMultiplyInt(ToShort<T> first, int second) {
        class ShortMultiplyInt extends AbstractMultiplyInt<T, ToShort<T>> implements ToInt<T> {
            private ShortMultiplyInt(ToShort<T> first, int second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsShort(object) * secondInner;
            }
        }

        return new ShortMultiplyInt(first, second);
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
    public static <T> ToLong<T> shortMultiplyLong(ToShort<T> first, long second) {
        class ShortMultiplyLong extends AbstractMultiplyLong<T, ToShort<T>> implements ToLong<T> {
            private ShortMultiplyLong(ToShort<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsShort(object) * secondInner;
            }
        }

        return new ShortMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> shortMultiplyShort(ToShort<T> first, ToShort<T> second) {
        class ShortMultiplyShort extends AbstractMultiply<T, ToShort<T>, ToShort<T>> implements ToInt<T> {
            private ShortMultiplyShort(ToShort<T> first, ToShort<T> second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsShort(object)
                    * secondInner.applyAsShort(object);
            }
        }

        return new ShortMultiplyShort(first, second);
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
    public static <T> ToInt<T> intMultiplyByte(ToInt<T> first, byte second) {
        class IntMultiplyByte extends AbstractMultiplyByte<T, ToInt<T>> implements ToInt<T> {
            private IntMultiplyByte(ToInt<T> first, byte second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) * secondInner;
            }
        }

        return new IntMultiplyByte(first, second);
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
    public static <T> ToInt<T> intMultiplyInt(ToInt<T> first, int second) {
        class IntMultiplyInt extends AbstractMultiplyInt<T, ToInt<T>> implements ToInt<T> {
            private IntMultiplyInt(ToInt<T> first, int second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) * secondInner;
            }
        }

        return new IntMultiplyInt(first, second);
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
    public static <T> ToLong<T> intMultiplyLong(ToInt<T> first, long second) {
        class IntMultiplyLong extends AbstractMultiplyLong<T, ToInt<T>> implements ToLong<T> {
            private IntMultiplyLong(ToInt<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsInt(object) * secondInner;
            }
        }

        return new IntMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> intMultiplyByte(ToInt<T> first, ToByte<T> second) {
        class IntMultiplyByte extends AbstractMultiply<T, ToInt<T>, ToByte<T>> implements ToInt<T> {
            private IntMultiplyByte(ToInt<T> first, ToByte<T> second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) *
                    secondInner.applyAsByte(object);
            }
        }

        return new IntMultiplyByte(first, second);
    }


    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> intMultiplyInt(ToInt<T> first, ToInt<T> second) {
        class IntMultiplyInt extends AbstractMultiply<T, ToInt<T>, ToInt<T>> implements ToInt<T> {
            private IntMultiplyInt(ToInt<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) *
                    secondInner.applyAsInt(object);
            }
        }

        return new IntMultiplyInt(first, second);
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
    public static <T> ToLong<T> longMultiplyByte(ToLong<T> first, byte second) {
        class LongMultiplyLong extends AbstractMultiplyByte<T, ToLong<T>> implements ToLong<T> {
            private LongMultiplyLong(ToLong<T> first, byte second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object) * secondInner;
            }
        }

        return new LongMultiplyLong(first, second);
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
    public static <T> ToLong<T> longMultiplyInt(ToLong<T> first, int second) {
        class LongMultiplyInt extends AbstractMultiplyInt<T, ToLong<T>> implements ToLong<T> {
            private LongMultiplyInt(ToLong<T> first, int second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object) * secondInner;
            }
        }

        return new LongMultiplyInt(first, second);
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
    public static <T> ToLong<T> longMultiplyLong(ToLong<T> first, long second) {
        class LongMultiplyLong extends AbstractMultiplyLong<T, ToLong<T>> implements ToLong<T> {
            private LongMultiplyLong(ToLong<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object) * secondInner;
            }
        }

        return new LongMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> longMultiplyInt(ToLong<T> first, ToInt<T> second) {
        class LongMultiplyInt extends AbstractMultiply<T, ToLong<T>, ToInt<T>> implements ToLong<T> {
            private LongMultiplyInt(ToLong<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object)
                    * secondInner.applyAsInt(object);
            }
        }

        return new LongMultiplyInt(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> longMultiplyLong(ToLong<T> first, ToLong<T> second) {
        class LongMultiplyLong extends AbstractMultiply<T, ToLong<T>, ToLong<T>> implements ToLong<T> {
            private LongMultiplyLong(ToLong<T> first, ToLong<T> second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object)
                    * secondInner.applyAsLong(object);
            }
        }

        return new LongMultiplyLong(first, second);
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
    public static <T> ToFloat<T> floatMultiplyInt(ToFloat<T> first, int second) {
        class FloatMultiplyInt extends AbstractMultiplyInt<T, ToFloat<T>> implements ToFloat<T> {
            private FloatMultiplyInt(ToFloat<T> first, int second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object) * secondInner;
            }
        }

        return new FloatMultiplyInt(first, second);
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
    public static <T> ToDouble<T> floatMultiplyLong(ToFloat<T> first, long second) {
        class FloatMultiplyLong extends AbstractMultiplyLong<T, ToFloat<T>> implements ToDouble<T> {
            private FloatMultiplyLong(ToFloat<T> first, long second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsFloat(object) * secondInner;
            }
        }

        return new FloatMultiplyLong(first, second);
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
    public static <T> ToFloat<T> floatMultiplyFloat(ToFloat<T> first, float second) {
        class FloatMultiplyFloat extends AbstractMultiplyFloat<T, ToFloat<T>> implements ToFloat<T> {
            private FloatMultiplyFloat(ToFloat<T> first, float second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object) * secondInner;
            }
        }

        return new FloatMultiplyFloat(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> floatMultiplyInt(ToFloat<T> first, ToInt<T> second) {
        class FloatMultiplyInt extends AbstractMultiply<T, ToFloat<T>, ToInt<T>> implements ToFloat<T> {
            private FloatMultiplyInt(ToFloat<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object)
                    * secondInner.applyAsInt(object);
            }
        }

        return new FloatMultiplyInt(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> floatMultiplyLong(ToFloat<T> first, ToLong<T> second) {
        class FloatMultiplyLong extends AbstractMultiply<T, ToFloat<T>, ToLong<T>> implements ToDouble<T> {
            private FloatMultiplyLong(ToFloat<T> first, ToLong<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsFloat(object)
                    * secondInner.applyAsLong(object);
            }
        }

        return new FloatMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> floatMultiplyFloat(ToFloat<T> first, ToFloat<T> second) {
        class FloatMultiplyFloat extends AbstractMultiply<T, ToFloat<T>, ToFloat<T>> implements ToFloat<T> {
            private FloatMultiplyFloat(ToFloat<T> first, ToFloat<T> second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object)
                    * secondInner.applyAsFloat(object);
            }
        }

        return new FloatMultiplyFloat(first, second);
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
    public static <T> ToDouble<T> doubleMultiplyInt(ToDouble<T> first, int second) {
        class DoubleMultiplyInt extends AbstractMultiplyInt<T, ToDouble<T>> implements ToDouble<T> {
            private DoubleMultiplyInt(ToDouble<T> first, int second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object) * secondInner;
            }
        }

        return new DoubleMultiplyInt(first, second);
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
    public static <T> ToDouble<T> doubleMultiplyLong(ToDouble<T> first, long second) {
        class DoubleMultiplyLong extends AbstractMultiplyLong<T, ToDouble<T>> implements ToDouble<T> {
            private DoubleMultiplyLong(ToDouble<T> first, long second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object) * secondInner;
            }
        }

        return new DoubleMultiplyLong(first, second);
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
    public static <T> ToDouble<T> doubleMultiplyDouble(ToDouble<T> first, double second) {
        class DoubleMultiplyDouble extends AbstractMultiplyDouble<T, ToDouble<T>> implements ToDouble<T> {
            private DoubleMultiplyDouble(ToDouble<T> first, double second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object) * secondInner;
            }
        }

        return new DoubleMultiplyDouble(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> doubleMultiplyInt(ToDouble<T> first, ToInt<T> second) {
        class DoubleMultiplyInt extends AbstractMultiply<T, ToDouble<T>, ToInt<T>> implements ToDouble<T> {
            private DoubleMultiplyInt(ToDouble<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object)
                    * secondInner.applyAsInt(object);
            }
        }

        return new DoubleMultiplyInt(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> doubleMultiplyLong(ToDouble<T> first, ToLong<T> second) {
        class DoubleMultiplyLong extends AbstractMultiply<T, ToDouble<T>, ToLong<T>> implements ToDouble<T> {
            private DoubleMultiplyLong(ToDouble<T> first, ToLong<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object)
                    * secondInner.applyAsLong(object);
            }
        }

        return new DoubleMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> doubleMultiplyDouble(ToDouble<T> first, ToDouble<T> second) {
        class DoubleMultiplyDouble extends AbstractMultiply<T, ToDouble<T>, ToDouble<T>> implements ToDouble<T> {
            private DoubleMultiplyDouble(ToDouble<T> first, ToDouble<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object)
                    * secondInner.applyAsDouble(object);
            }
        }

        return new DoubleMultiplyDouble(first, second);
    }

    /**
     * Abstract base for a multiply operation.
     *
     * @param <T>       the input type
     * @param <FIRST>   the first operand expression type
     * @param <SECOND>  the second operand expression type
     */
    abstract static class AbstractMultiply<T, FIRST extends Expression<T>, SECOND extends Expression<T>>
    implements BinaryExpression<T, FIRST, SECOND> {

        final FIRST firstInner;
        final SECOND secondInner;

        AbstractMultiply(FIRST first, SECOND second) {
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
            return Operator.MULTIPLY;
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
     * Abstract base for a multiply operation that takes a {@code byte} as the
     * second operand.
     *
     * @param <INNER>  the first operand expression type
     */
    abstract static class AbstractMultiplyByte<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Byte> {

        final INNER firstInner;
        final byte secondInner;

        AbstractMultiplyByte(INNER first, byte second) {
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
            return Operator.MULTIPLY;
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
     * Abstract base for a multiply operation that takes an {@code int} as the
     * second operand.
     *
     * @param <T>      the input type
     * @param <INNER>  the first operand expression type
     */
    abstract static class AbstractMultiplyInt<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Integer> {

        final INNER firstInner;
        final int secondInner;

        AbstractMultiplyInt(INNER first, int second) {
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
            return Operator.MULTIPLY;
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
     * Abstract base for a multiply operation that takes a {@code long} as the
     * second operand.
     *
     * @param <T>      the input type
     * @param <INNER>  the first operand expression type
     */
    abstract static class AbstractMultiplyLong<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Long> {

        final INNER firstInner;
        final long secondInner;

        AbstractMultiplyLong(INNER first, long second) {
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
            return Operator.MULTIPLY;
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
     * Abstract base for a multiply operation that takes a {@code float} as the
     * second operand.
     *
     * @param <T>      the input type
     * @param <INNER>  the first operand expression type
     */
    abstract static class AbstractMultiplyFloat<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Float> {

        final INNER firstInner;
        final float secondInner;

        AbstractMultiplyFloat(INNER first, float second) {
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
            return Operator.MULTIPLY;
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
     * Abstract base for a multiply operation that takes a {@code double} as the
     * second operand.
     *
     * @param <T>      the input type
     * @param <INNER>  the first operand expression type
     */
    abstract static class AbstractMultiplyDouble<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Double> {

        final INNER firstInner;
        final double secondInner;

        AbstractMultiplyDouble(INNER first, double second) {
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
            return Operator.MULTIPLY;
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
    private MultiplyUtil() {}
}
