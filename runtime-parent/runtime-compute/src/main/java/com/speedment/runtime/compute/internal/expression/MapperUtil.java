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

import com.speedment.common.function.BooleanToDoubleFunction;
import com.speedment.common.function.BooleanUnaryOperator;
import com.speedment.common.function.ByteToDoubleFunction;
import com.speedment.common.function.ByteUnaryOperator;
import com.speedment.common.function.CharUnaryOperator;
import com.speedment.common.function.FloatToDoubleFunction;
import com.speedment.common.function.FloatUnaryOperator;
import com.speedment.common.function.ShortToDoubleFunction;
import com.speedment.common.function.ShortUnaryOperator;
import com.speedment.runtime.compute.ToBigDecimal;
import com.speedment.runtime.compute.ToBoolean;
import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToChar;
import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToEnum;
import com.speedment.runtime.compute.ToFloat;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToLong;
import com.speedment.runtime.compute.ToShort;
import com.speedment.runtime.compute.ToString;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.MapperExpression;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

/**
 * Utility class used to create expressions that map from one value to another.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class MapperUtil {

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToBoolean<T> mapBoolean(ToBoolean<T> expression, BooleanUnaryOperator mapper) {
        return new ToBooleanMapper<T, ToBoolean<T>, BooleanUnaryOperator>(expression, mapper) {
            @Override
            public boolean applyAsBoolean(T object) {
                return this.mapper.applyAsBoolean(this.inner.applyAsBoolean(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.BOOLEAN_TO_BOOLEAN;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> mapBooleanToDouble(ToBoolean<T> expression, BooleanToDoubleFunction mapper) {
        return new ToDoubleMapper<T, ToBoolean<T>, BooleanToDoubleFunction>(expression, mapper) {
            @Override
            public double applyAsDouble(T object) {
                return this.mapper.applyAsDouble(this.inner.applyAsBoolean(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.BOOLEAN_TO_DOUBLE;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToChar<T> mapChar(ToChar<T> expression, CharUnaryOperator mapper) {
        return new ToCharMapper<T, ToChar<T>, CharUnaryOperator>(expression, mapper) {
            @Override
            public char applyAsChar(T object) {
                return this.mapper.applyAsChar(this.inner.applyAsChar(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.CHAR_TO_CHAR;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToByte<T> mapByte(ToByte<T> expression, ByteUnaryOperator mapper) {
        return new ToByteMapper<T, ToByte<T>, ByteUnaryOperator>(expression, mapper) {
            @Override
            public byte applyAsByte(T object) {
                return this.mapper.applyAsByte(this.inner.applyAsByte(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.BYTE_TO_BYTE;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> mapByteToDouble(ToByte<T> expression, ByteToDoubleFunction mapper) {
        return new ToDoubleMapper<T, ToByte<T>, ByteToDoubleFunction>(expression, mapper) {
            @Override
            public double applyAsDouble(T object) {
                return this.mapper.applyAsDouble(this.inner.applyAsByte(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.BYTE_TO_DOUBLE;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToShort<T> mapShort(ToShort<T> expression, ShortUnaryOperator mapper) {
        return new ToShortMapper<T, ToShort<T>, ShortUnaryOperator>(expression, mapper) {
            @Override
            public short applyAsShort(T object) {
                return this.mapper.applyAsShort(this.inner.applyAsShort(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.SHORT_TO_SHORT;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> mapShortToDouble(ToShort<T> expression, ShortToDoubleFunction mapper) {
        return new ToDoubleMapper<T, ToShort<T>, ShortToDoubleFunction>(expression, mapper) {
            @Override
            public double applyAsDouble(T object) {
                return this.mapper.applyAsDouble(this.inner.applyAsShort(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.SHORT_TO_DOUBLE;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToInt<T> mapInt(ToInt<T> expression, IntUnaryOperator mapper) {
        return new ToIntMapper<T, ToInt<T>, IntUnaryOperator>(expression, mapper) {
            @Override
            public int applyAsInt(T object) {
                return this.mapper.applyAsInt(this.inner.applyAsInt(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.INT_TO_INT;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> mapIntToDouble(ToInt<T> expression, IntToDoubleFunction mapper) {
        return new ToDoubleMapper<T, ToInt<T>, IntToDoubleFunction>(expression, mapper) {
            @Override
            public double applyAsDouble(T object) {
                return this.mapper.applyAsDouble(this.inner.applyAsInt(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.INT_TO_DOUBLE;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToLong<T> mapLong(ToLong<T> expression, LongUnaryOperator mapper) {
        return new ToLongMapper<T, ToLong<T>, LongUnaryOperator>(expression, mapper) {
            @Override
            public long applyAsLong(T object) {
                return this.mapper.applyAsLong(this.inner.applyAsLong(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.LONG_TO_LONG;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> mapLongToDouble(ToLong<T> expression, LongToDoubleFunction mapper) {
        return new ToDoubleMapper<T, ToLong<T>, LongToDoubleFunction>(expression, mapper) {
            @Override
            public double applyAsDouble(T object) {
                return this.mapper.applyAsDouble(this.inner.applyAsLong(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.LONG_TO_DOUBLE;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToFloat<T> mapFloat(ToFloat<T> expression, FloatUnaryOperator mapper) {
        return new ToFloatMapper<T, ToFloat<T>, FloatUnaryOperator>(expression, mapper) {
            @Override
            public float applyAsFloat(T object) {
                return this.mapper.applyAsFloat(this.inner.applyAsFloat(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.FLOAT_TO_FLOAT;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> mapFloatToDouble(ToFloat<T> expression, FloatToDoubleFunction mapper) {
        return new ToDoubleMapper<T, ToFloat<T>, FloatToDoubleFunction>(expression, mapper) {
            @Override
            public double applyAsDouble(T object) {
                return this.mapper.applyAsDouble(this.inner.applyAsFloat(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.FLOAT_TO_DOUBLE;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> mapDouble(ToDouble<T> expression, DoubleUnaryOperator mapper) {
        return new ToDoubleMapper<T, ToDouble<T>, DoubleUnaryOperator>(expression, mapper) {
            @Override
            public double applyAsDouble(T object) {
                return this.mapper.applyAsDouble(this.inner.applyAsDouble(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.DOUBLE_TO_DOUBLE;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToString<T> mapString(ToString<T> expression, UnaryOperator<String> mapper) {
        return new ToStringMapper<T, ToString<T>, UnaryOperator<String>>(expression, mapper) {
            @Override
            public String apply(T object) {
                return this.mapper.apply(this.inner.apply(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.STRING_TO_STRING;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T, E extends Enum<E>> ToEnum<T, E> mapEnum(ToEnum<T, E> expression, UnaryOperator<E> mapper) {
        return new ToEnumMapper<T, E, ToEnum<T, E>, UnaryOperator<E>>(expression, mapper) {
            @Override
            public E apply(T object) {
                return this.mapper.apply(this.inner.apply(object));
            }

            @Override
            public Class<E> enumClass() {
                return this.inner.enumClass();
            }

            @Override
            public MapperType mapperType() {
                return MapperType.ENUM_TO_ENUM;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToBigDecimal<T> mapBigDecimal(ToBigDecimal<T> expression, UnaryOperator<BigDecimal> mapper) {
        return new ToBigDecimalMapper<T, ToBigDecimal<T>, UnaryOperator<BigDecimal>>(expression, mapper) {
            @Override
            public BigDecimal apply(T object) {
                return this.mapper.apply(this.inner.apply(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.BIG_DECIMAL_TO_BIG_DECIMAL;
            }
        };
    }

    /**
     * Returns an expression that first applies the specified expression to get
     * a value, then applies the specified mapping operation to that value to
     * get the final result.
     *
     * @param expression  the expression to apply to the input
     * @param mapper      the mapper to apply to the result
     * @param <T>         the input type
     * @return            the new expression
     */
    public static <T> ToDouble<T> mapBigDecimalToDouble(ToBigDecimal<T> expression, ToDoubleFunction<BigDecimal> mapper) {
        return new ToDoubleMapper<T, ToBigDecimal<T>, ToDoubleFunction<BigDecimal>>(expression, mapper) {
            @Override
            public double applyAsDouble(T object) {
                return this.mapper.applyAsDouble(this.inner.apply(object));
            }

            @Override
            public MapperType mapperType() {
                return MapperType.BIG_DECIMAL_TO_DOUBLE;
            }
        };
    }

    /**
     * Abstract base for a mapping operation that results in a {@code byte}.
     *
     * @param <T>       the input type
     * @param <INNER>   the inner expression type
     * @param <MAPPER>  the mapping functional interface
     */
    abstract static class ToByteMapper<T, INNER extends Expression<T>, MAPPER>
    extends AbstractMapper<T, INNER, MAPPER> implements ToByte<T> {
        ToByteMapper(INNER inner, MAPPER mapper) {
            super(inner, mapper);
        }
    }

    /**
     * Abstract base for a mapping operation that results in a {@code short}.
     *
     * @param <T>       the input type
     * @param <INNER>   the inner expression type
     * @param <MAPPER>  the mapping functional interface
     */
    abstract static class ToShortMapper<T, INNER extends Expression<T>, MAPPER>
    extends AbstractMapper<T, INNER, MAPPER> implements ToShort<T> {
        ToShortMapper(INNER inner, MAPPER mapper) {
            super(inner, mapper);
        }
    }

    /**
     * Abstract base for a mapping operation that results in a {@code int}.
     *
     * @param <T>       the input type
     * @param <INNER>   the inner expression type
     * @param <MAPPER>  the mapping functional interface
     */
    abstract static class ToIntMapper<T, INNER extends Expression<T>, MAPPER>
    extends AbstractMapper<T, INNER, MAPPER> implements ToInt<T> {
        ToIntMapper(INNER inner, MAPPER mapper) {
            super(inner, mapper);
        }
    }

    /**
     * Abstract base for a mapping operation that results in a {@code long}.
     *
     * @param <T>       the input type
     * @param <INNER>   the inner expression type
     * @param <MAPPER>  the mapping functional interface
     */
    abstract static class ToLongMapper<T, INNER extends Expression<T>, MAPPER>
    extends AbstractMapper<T, INNER, MAPPER> implements ToLong<T> {
        ToLongMapper(INNER inner, MAPPER mapper) {
            super(inner, mapper);
        }
    }

    /**
     * Abstract base for a mapping operation that results in a {@code float}.
     *
     * @param <T>       the input type
     * @param <INNER>   the inner expression type
     * @param <MAPPER>  the mapping functional interface
     */
    abstract static class ToFloatMapper<T, INNER extends Expression<T>, MAPPER>
    extends AbstractMapper<T, INNER, MAPPER> implements ToFloat<T> {
        ToFloatMapper(INNER inner, MAPPER mapper) {
            super(inner, mapper);
        }
    }

    /**
     * Abstract base for a mapping operation that results in a {@code double}.
     *
     * @param <T>       the input type
     * @param <INNER>   the inner expression type
     * @param <MAPPER>  the mapping functional interface
     */
    abstract static class ToDoubleMapper<T, INNER extends Expression<T>, MAPPER>
    extends AbstractMapper<T, INNER, MAPPER> implements ToDouble<T> {
        ToDoubleMapper(INNER inner, MAPPER mapper) {
            super(inner, mapper);
        }
    }

    /**
     * Abstract base for a mapping operation that results in a {@code char}.
     *
     * @param <T>       the input type
     * @param <INNER>   the inner expression type
     * @param <MAPPER>  the mapping functional interface
     */
    abstract static class ToCharMapper<T, INNER extends Expression<T>, MAPPER>
    extends AbstractMapper<T, INNER, MAPPER> implements ToChar<T> {
        ToCharMapper(INNER inner, MAPPER mapper) {
            super(inner, mapper);
        }
    }

    /**
     * Abstract base for a mapping operation that results in a {@code boolean}.
     *
     * @param <T>       the input type
     * @param <INNER>   the inner expression type
     * @param <MAPPER>  the mapping functional interface
     */
    abstract static class ToBooleanMapper<T, INNER extends Expression<T>, MAPPER>
    extends AbstractMapper<T, INNER, MAPPER> implements ToBoolean<T> {
        ToBooleanMapper(INNER inner, MAPPER mapper) {
            super(inner, mapper);
        }
    }

    /**
     * Abstract base for a mapping operation that results in a {@code enum}.
     *
     * @param <T>       the input type
     * @param <E>       the enum type
     * @param <INNER>   the inner expression type
     * @param <MAPPER>  the mapping functional interface
     */
    abstract static class ToEnumMapper<T, E extends Enum<E>, INNER extends Expression<T>, MAPPER>
    extends AbstractMapper<T, INNER, MAPPER> implements ToEnum<T, E> {
        ToEnumMapper(INNER inner, MAPPER mapper) {
            super(inner, mapper);
        }
    }

    /**
     * Abstract base for a mapping operation that results in a {@code string}.
     *
     * @param <T>       the input type
     * @param <INNER>   the inner expression type
     * @param <MAPPER>  the mapping functional interface
     */
    abstract static class ToStringMapper<T, INNER extends Expression<T>, MAPPER>
    extends AbstractMapper<T, INNER, MAPPER> implements ToString<T> {
        ToStringMapper(INNER inner, MAPPER mapper) {
            super(inner, mapper);
        }
    }

    /**
     * Abstract base for a mapping operation that results in a {@code bigDecimal}.
     *
     * @param <T>       the input type
     * @param <INNER>   the inner expression type
     * @param <MAPPER>  the mapping functional interface
     */
    abstract static class ToBigDecimalMapper<T, INNER extends Expression<T>, MAPPER>
        extends AbstractMapper<T, INNER, MAPPER> implements ToBigDecimal<T> {
        ToBigDecimalMapper(INNER inner, MAPPER mapper) {
            super(inner, mapper);
        }
    }

    /**
     * Abstract base for a mapping operation.
     *
     * @param <T>       the input type
     * @param <INNER>   the inner expression type
     * @param <MAPPER>  the mapping functional interface
     */
    abstract static class AbstractMapper<T, INNER extends Expression<T>, MAPPER>
    implements MapperExpression<T, INNER, MAPPER> {
        final INNER inner;
        final MAPPER mapper;

        AbstractMapper(INNER inner, MAPPER mapper) {
            this.inner  = requireNonNull(inner);
            this.mapper = requireNonNull(mapper);
        }

        @Override
        public final INNER inner() {
            return inner;
        }

        @Override
        public final MAPPER mapper() {
            return mapper;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            else if (!(o instanceof MapperExpression)) return false;
            final MapperExpression<?, ?, ?> that = (MapperExpression<?, ?, ?>) o;
            return Objects.equals(inner(), that.inner()) &&
                Objects.equals(mapper(), that.mapper()) &&
                Objects.equals(mapperType(), that.mapperType());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner(), mapper(), mapperType());
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private MapperUtil() {}
}
