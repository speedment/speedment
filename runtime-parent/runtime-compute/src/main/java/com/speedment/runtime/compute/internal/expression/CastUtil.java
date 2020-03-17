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

import com.speedment.runtime.compute.ToBigDecimal;
import com.speedment.runtime.compute.ToBoolean;
import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToChar;
import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToFloat;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToLong;
import com.speedment.runtime.compute.ToShort;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.UnaryExpression;

import java.util.Objects;

/**
 * Utility class for creating expression that casts the result of another
 * expression into some type, preserving as much metadata as possible.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class CastUtil {

    private CastUtil() {}

    ////////////////////////////////////////////////////////////////////////////
    //                               ToDouble                                 //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code double}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToDouble} expression
     */
    public static <T> ToDouble<T> castByteToDouble(ToByte<T> original) {
        class ByteToDouble extends CastToDouble<T, ToByte<T>> {
            private ByteToDouble(ToByte<T> tToByte) {
                super(tToByte);
            }

            @Override
            public double applyAsDouble(T object) {
                return inner.applyAsByte(object);
            }
        }

        return new ByteToDouble(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code double}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToDouble} expression
     */
    public static <T> ToDouble<T> castShortToDouble(ToShort<T> original) {
        class ShortToDouble extends CastToDouble<T, ToShort<T>> {
            private ShortToDouble(ToShort<T> tToShort) {
                super(tToShort);
            }

            @Override
            public double applyAsDouble(T object) {
                return inner.applyAsShort(object);
            }
        }

        return new ShortToDouble(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code double}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToDouble} expression
     */
    public static <T> ToDouble<T> castIntToDouble(ToInt<T> original) {
        class IntToDouble extends CastToDouble<T, ToInt<T>> {
            private IntToDouble(ToInt<T> tToInt) {
                super(tToInt);
            }

            @Override
            public double applyAsDouble(T object) {
                return inner.applyAsInt(object);
            }
        }

        return new IntToDouble(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code double}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToDouble} expression
     */
    public static <T> ToDouble<T> castLongToDouble(ToLong<T> original) {
        class LongToDouble extends CastToDouble<T, ToLong<T>> {
            private LongToDouble(ToLong<T> tToLong) {
                super(tToLong);
            }

            @Override
            public double applyAsDouble(T object) {
                return inner.applyAsLong(object);
            }
        }

        return new LongToDouble(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code double}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToDouble} expression
     */
    public static <T> ToDouble<T> castFloatToDouble(ToFloat<T> original) {
        class FloatToDouble extends CastToDouble<T, ToFloat<T>> {
            private FloatToDouble(ToFloat<T> tToFloat) {
                super(tToFloat);
            }

            @Override
            public double applyAsDouble(T object) {
                return inner.applyAsFloat(object);
            }
        }

        return new FloatToDouble(original);
    }
    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code double}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToDouble} expression
     */
    public static <T> ToDouble<T> castBigDecimalToDouble(ToBigDecimal<T> original) {
        class BigDecimalToDouble extends CastToDouble<T, ToBigDecimal<T>> {
            private BigDecimalToDouble(ToBigDecimal<T> tToBigDecimal) {
                super(tToBigDecimal);
            }

            @Override
            public double applyAsDouble(T object) {
                return inner.apply(object).doubleValue();
            }
        }

        return new BigDecimalToDouble(original);
    }


    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code double}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToDouble} expression
     */
    public static <T> ToDouble<T> castBooleanToDouble(ToBoolean<T> original) {
        class BooleanToDouble extends CastToDouble<T, ToBoolean<T>> {
            private BooleanToDouble(ToBoolean<T> tToBoolean) {
                super(tToBoolean);
            }

            @Override
            public double applyAsDouble(T object) {
                return inner.applyAsBoolean(object) ? 1 : 0;
            }
        }

        return new BooleanToDouble(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code double}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToDouble} expression
     */
    public static <T> ToDouble<T> castCharToDouble(ToChar<T> original) {
        class CharToDouble extends CastToDouble<T, ToChar<T>> {
            private CharToDouble(ToChar<T> tToChar) {
                super(tToChar);
            }

            @Override
            public double applyAsDouble(T object) {
                return inner.applyAsChar(object);
            }
        }

        return new CharToDouble(original);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                  ToInt                                 //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code int}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToInt} expression
     */
    public static <T> ToInt<T> castByteToInt(ToByte<T> original) {
        class ByteToInt extends CastToInt<T, ToByte<T>> {
            private ByteToInt(ToByte<T> tToByte) {
                super(tToByte);
            }

            @Override
            public int applyAsInt(T object) {
                return inner.applyAsByte(object);
            }
        }

        return new ByteToInt(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code int}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToInt} expression
     */
    public static <T> ToInt<T> castShortToInt(ToShort<T> original) {
        class ShortToInt extends CastToInt<T, ToShort<T>> {
            private ShortToInt(ToShort<T> tToShort) {
                super(tToShort);
            }

            @Override
            public int applyAsInt(T object) {
                return inner.applyAsShort(object);
            }
        }

        return new ShortToInt(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code int}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToInt} expression
     */
    public static <T> ToInt<T> castLongToInt(ToLong<T> original) {
        class LongToInt extends CastToInt<T, ToLong<T>> {
            private LongToInt(ToLong<T> tToLong) {
                super(tToLong);
            }

            @Override
            public int applyAsInt(T object) {
                return (int) inner.applyAsLong(object);
            }
        }

        return new LongToInt(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code int}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToInt} expression
     */
    public static <T> ToInt<T> castFloatToInt(ToFloat<T> original) {
        class FloatToInt extends CastToInt<T, ToFloat<T>> {
            private FloatToInt(ToFloat<T> tToFloat) {
                super(tToFloat);
            }

            @Override
            public int applyAsInt(T object) {
                return (int) inner.applyAsFloat(object);
            }
        }

        return new FloatToInt(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code int}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToInt} expression
     */
    public static <T> ToInt<T> castDoubleToInt(ToDouble<T> original) {
        class DoubleToInt extends CastToInt<T, ToDouble<T>> {
            private DoubleToInt(ToDouble<T> tToDouble) {
                super(tToDouble);
            }

            @Override
            public int applyAsInt(T object) {
                return (int) inner.applyAsDouble(object);
            }
        }

        return new DoubleToInt(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code int}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToInt} expression
     */
    public static <T> ToInt<T> castBigDecimalToInt(ToBigDecimal<T> original) {
        class BigDecimalToInt extends CastToInt<T, ToBigDecimal<T>> {
            private BigDecimalToInt(ToBigDecimal<T> tToBigDecimal) {
                super(tToBigDecimal);
            }

            @Override
            public int applyAsInt(T object) {
                return inner.apply(object).intValueExact();
            }
        }

        return new BigDecimalToInt(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code int}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToInt} expression
     */
    public static <T> ToInt<T> castBooleanToInt(ToBoolean<T> original) {
        class BooleanToInt extends CastToInt<T, ToBoolean<T>> {
            private BooleanToInt(ToBoolean<T> tToBoolean) {
                super(tToBoolean);
            }

            @Override
            public int applyAsInt(T object) {
                return inner.applyAsBoolean(object) ? 1 : 0;
            }
        }

        return new BooleanToInt(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code int}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToInt} expression
     */
    public static <T> ToInt<T> castCharToInt(ToChar<T> original) {
        class CharToInt extends CastToInt<T, ToChar<T>> {
            private CharToInt(ToChar<T> tToChar) {
                super(tToChar);
            }

            @Override
            public int applyAsInt(T object) {
                return inner.applyAsChar(object);
            }
        }

        return new CharToInt(original);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                 ToLong                                 //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code long}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToLong} expression
     */
    public static <T> ToLong<T> castByteToLong(ToByte<T> original) {
        class ByteToLong extends CastToLong<T, ToByte<T>> {
            private ByteToLong(ToByte<T> tToByte) {
                super(tToByte);
            }

            @Override
            public long applyAsLong(T object) {
                return inner.applyAsByte(object);
            }
        }

        return new ByteToLong(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code long}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToLong} expression
     */
    public static <T> ToLong<T> castShortToLong(ToShort<T> original) {
        class ShortToLong extends CastToLong<T, ToShort<T>> {
            private ShortToLong(ToShort<T> tToShort) {
                super(tToShort);
            }

            @Override
            public long applyAsLong(T object) {
                return inner.applyAsShort(object);
            }
        }

        return new ShortToLong(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code long}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToLong} expression
     */
    public static <T> ToLong<T> castIntToLong(ToInt<T> original) {
        class IntToLong extends CastToLong<T, ToInt<T>> {
            private IntToLong(ToInt<T> tToInt) {
                super(tToInt);
            }

            @Override
            public long applyAsLong(T object) {
                return inner.applyAsInt(object);
            }
        }

        return new IntToLong(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code long}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToLong} expression
     */
    public static <T> ToLong<T> castFloatToLong(ToFloat<T> original) {
        class FloatToLong extends CastToLong<T, ToFloat<T>> {
            private FloatToLong(ToFloat<T> tToFloat) {
                super(tToFloat);
            }

            @Override
            public long applyAsLong(T object) {
                return (long) inner.applyAsFloat(object);
            }
        }

        return new FloatToLong(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code long}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToLong} expression
     */
    public static <T> ToLong<T> castDoubleToLong(ToDouble<T> original) {
        class DoubleToLong extends CastToLong<T, ToDouble<T>> {
            private DoubleToLong(ToDouble<T> tToDouble) {
                super(tToDouble);
            }

            @Override
            public long applyAsLong(T object) {
                return (long) inner.applyAsDouble(object);
            }
        }

        return new DoubleToLong(original);
    }


    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code long}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToInt} expression
     */
    public static <T> ToLong<T> castBigDecimalToLong(ToBigDecimal<T> original) {
        class BigDecimalToLong extends CastToLong<T, ToBigDecimal<T>> {
            private BigDecimalToLong(ToBigDecimal<T> tToBigDecimal) {
                super(tToBigDecimal);
            }

            @Override
            public long applyAsLong(T object) {
                return inner.apply(object).longValueExact();
            }
        }

        return new BigDecimalToLong(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code long}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToLong} expression
     */
    public static <T> ToLong<T> castBooleanToLong(ToBoolean<T> original) {
        class BooleanToLong extends CastToLong<T, ToBoolean<T>> {
            private BooleanToLong(ToBoolean<T> tToBoolean) {
                super(tToBoolean);
            }

            @Override
            public long applyAsLong(T object) {
                return inner.applyAsBoolean(object) ? 1 : 0;
            }
        }

        return new BooleanToLong(original);
    }

    /**
     * Returns an expression that wraps the specified expression and casts the
     * result from it into a {@code long}.
     *
     * @param original  the original expression
     * @param <T>       the input type
     * @return          the new {@link ToLong} expression
     */
    public static <T> ToLong<T> castCharToLong(ToChar<T> original) {
        class CharToLong extends CastToLong<T, ToChar<T>> {
            private CharToLong(ToChar<T> tToChar) {
                super(tToChar);
            }

            @Override
            public long applyAsLong(T object) {
                return inner.applyAsChar(object);
            }
        }

        return new CharToLong(original);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                Internal                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Internal abstract implementation of the cast operation that casts to a
     * {@code double}.
     *
     * @param <T>      the input type
     * @param <INNER>  the inner expression type
     */
    private abstract static class CastToDouble<T, INNER extends Expression<T>>
    extends AbstractCast<T, INNER> implements ToDouble<T> {
        CastToDouble(INNER inner) {
            super(inner);
        }
    }

    /**
     * Internal abstract implementation of the cast operation that casts to an
     * {@code int}.
     *
     * @param <T>      the input type
     * @param <INNER>  the inner expression type
     */
    private abstract static class CastToInt<T, INNER extends Expression<T>>
    extends AbstractCast<T, INNER> implements ToInt<T> {
        CastToInt(INNER inner) {
            super(inner);
        }
    }

    /**
     * Internal abstract implementation of the cast operation that casts to a
     * {@code long}.
     *
     * @param <T>      the input type
     * @param <INNER>  the inner expression type
     */
    private abstract static class CastToLong<T, INNER extends Expression<T>>
    extends AbstractCast<T, INNER> implements ToLong<T> {
        CastToLong(INNER inner) {
            super(inner);
        }
    }

    /**
     * Internal abstract implementation of the cast operation.
     *
     * @param <T>      the input entity type
     * @param <INNER>  the inner expression type
     */
    abstract static class AbstractCast<T, INNER extends Expression<T>>
    implements UnaryExpression<T, INNER> {

        final INNER inner;

        AbstractCast(INNER inner) {
            this.inner = requireNonNull(inner);
        }

        @Override
        public final INNER inner() {
            return inner;
        }

        @Override
        public final Operator operator() {
            return Operator.CAST;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            else if (!(o instanceof UnaryExpression)) return false;
            final UnaryExpression<?, ?> that = (UnaryExpression<?, ?>) o;
            return Objects.equals(inner(), that.inner())
                && operator() == that.operator();
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner(), operator());
        }
    }

}
