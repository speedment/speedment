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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.expression.MapperExpression.MapperType;
import com.speedment.runtime.compute.internal.expression.MapperUtil.AbstractMapper;
import com.speedment.runtime.compute.internal.expression.MapperUtil.ToBigDecimalMapper;
import com.speedment.runtime.compute.internal.expression.MapperUtil.ToBooleanMapper;
import com.speedment.runtime.compute.internal.expression.MapperUtil.ToByteMapper;
import com.speedment.runtime.compute.internal.expression.MapperUtil.ToCharMapper;
import com.speedment.runtime.compute.internal.expression.MapperUtil.ToDoubleMapper;
import com.speedment.runtime.compute.internal.expression.MapperUtil.ToEnumMapper;
import com.speedment.runtime.compute.internal.expression.MapperUtil.ToFloatMapper;
import com.speedment.runtime.compute.internal.expression.MapperUtil.ToIntMapper;
import com.speedment.runtime.compute.internal.expression.MapperUtil.ToLongMapper;
import com.speedment.runtime.compute.internal.expression.MapperUtil.ToShortMapper;
import com.speedment.runtime.compute.internal.expression.MapperUtil.ToStringMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
final class MapperUtilTest {

    @Test
    void mapBoolean() {
        final ToBoolean<Boolean> toBoolean = bool -> bool;
        final ToBooleanMapper<Boolean, ToBoolean<Boolean>, BooleanUnaryOperator> toBooleanMapper =
                (ToBooleanMapper<Boolean, ToBoolean<Boolean>, BooleanUnaryOperator>)
                        MapperUtil.mapBoolean(toBoolean, bool -> bool);

        assertEquals(MapperType.BOOLEAN_TO_BOOLEAN, toBooleanMapper.mapperType());
    }

    @Test
    void mapBooleanToDouble() {
        final ToBoolean<Boolean> toBoolean = bool -> bool;
        final ToDoubleMapper<Boolean, ToBoolean<Boolean>, BooleanToDoubleFunction> toDoubleMapper =
                (ToDoubleMapper<Boolean, ToBoolean<Boolean>, BooleanToDoubleFunction>)
                        MapperUtil.mapBooleanToDouble(toBoolean, bool -> 1);

        assertEquals(MapperType.BOOLEAN_TO_DOUBLE, toDoubleMapper.mapperType());
    }

    @Test
    void mapChar() {
        final ToChar<Character> toChar = c -> c;
        final ToCharMapper<Character, ToChar<Character>, CharUnaryOperator> toCharMapper =
                (ToCharMapper<Character, ToChar<Character>, CharUnaryOperator>)
                        MapperUtil.mapChar(toChar, c -> c);

        assertEquals(MapperType.CHAR_TO_CHAR, toCharMapper.mapperType());
    }

    @Test
    void mapByte() {
        final ToByte<Byte> toByte = b -> b;
        final ToByteMapper<Byte, ToByte<Byte>, ByteUnaryOperator> toByteMapper =
                (ToByteMapper<Byte, ToByte<Byte>, ByteUnaryOperator>)
                        MapperUtil.mapByte(toByte, c -> c);

        assertEquals(MapperType.BYTE_TO_BYTE, toByteMapper.mapperType());
    }
    
    @Test
    void mapByteToDouble() {
        final ToByte<Byte> toByte = b -> b;
        final ToDoubleMapper<Byte, ToByte<Byte>, ByteToDoubleFunction> toDoubleMapper =
                (ToDoubleMapper<Byte, ToByte<Byte>, ByteToDoubleFunction>)
                        MapperUtil.mapByteToDouble(toByte, b -> 1);

        assertEquals(MapperType.BYTE_TO_DOUBLE, toDoubleMapper.mapperType());
    }

    @Test
    void mapShort() {
        final ToShort<Short> toShort = s -> s;
        final ToShortMapper<Short, ToShort<Short>, ShortUnaryOperator> toShortMapper =
                (ToShortMapper<Short, ToShort<Short>, ShortUnaryOperator>)
                        MapperUtil.mapShort(toShort, s -> s);

        assertEquals(MapperType.SHORT_TO_SHORT, toShortMapper.mapperType());
    }

    @Test
    void mapShortToDouble() {
        final ToShort<Short> toShort = s -> s;
        final ToDoubleMapper<Short, ToShort<Short>, ShortToDoubleFunction> toDoubleMapper =
                (ToDoubleMapper<Short, ToShort<Short>, ShortToDoubleFunction>)
                        MapperUtil.mapShortToDouble(toShort, s -> 1);

        assertEquals(MapperType.SHORT_TO_DOUBLE, toDoubleMapper.mapperType());
    }

    @Test
    void mapInt() {
        final ToInt<Integer> toInt = i -> i;
        final ToIntMapper<Integer, ToInt<Integer>, IntUnaryOperator> toIntMapper =
                (ToIntMapper<Integer, ToInt<Integer>, IntUnaryOperator>)
                        MapperUtil.mapInt(toInt, s -> s);

        assertEquals(MapperType.INT_TO_INT, toIntMapper.mapperType());
    }

    @Test
    void mapIntToDouble() {
        final ToInt<Integer> toInt = i -> i;
        final ToDoubleMapper<Integer, ToInt<Integer>, IntToDoubleFunction> toDoubleMapper =
                (ToDoubleMapper<Integer, ToInt<Integer>, IntToDoubleFunction>)
                        MapperUtil.mapIntToDouble(toInt, i -> 1);

        assertEquals(MapperType.INT_TO_DOUBLE, toDoubleMapper.mapperType());
    }

    @Test
    void mapLong() {
        final ToLong<Long> toLong = l -> l;
        final ToLongMapper<Long, ToLong<Long>, LongUnaryOperator> toLongMapper =
                (ToLongMapper<Long, ToLong<Long>, LongUnaryOperator>)
                        MapperUtil.mapLong(toLong, l -> l);

        assertEquals(MapperType.LONG_TO_LONG, toLongMapper.mapperType());
    }

    @Test
    void mapLongToDouble() {
        final ToLong<Long> toLong = s -> s;
        final ToDoubleMapper<Long, ToLong<Long>, LongToDoubleFunction> toDoubleMapper =
                (ToDoubleMapper<Long, ToLong<Long>, LongToDoubleFunction>)
                        MapperUtil.mapLongToDouble(toLong, s -> 1);

        assertEquals(MapperType.LONG_TO_DOUBLE, toDoubleMapper.mapperType());
    }

    @Test
    void mapFloat() {
        final ToFloat<Float> toFloat = f -> f;
        final ToFloatMapper<Float, ToFloat<Float>, FloatUnaryOperator> toFloatMapper =
                (ToFloatMapper<Float, ToFloat<Float>, FloatUnaryOperator>)
                        MapperUtil.mapFloat(toFloat, f -> f);

        assertEquals(MapperType.FLOAT_TO_FLOAT, toFloatMapper.mapperType());
    }

    @Test
    void mapFloatToDouble() {
        final ToFloat<Float> toFloat = f -> f;
        final ToDoubleMapper<Float, ToFloat<Float>, FloatToDoubleFunction> toDoubleMapper =
                (ToDoubleMapper<Float, ToFloat<Float>, FloatToDoubleFunction>)
                        MapperUtil.mapFloatToDouble(toFloat, f -> 1);

        assertEquals(MapperType.FLOAT_TO_DOUBLE, toDoubleMapper.mapperType());
    }

    @Test
    void mapDouble() {
        final ToDouble<Double> toDouble = d -> d;
        final ToDoubleMapper<Double, ToDouble<Double>, DoubleUnaryOperator> toDoubleMapper =
                (ToDoubleMapper<Double, ToDouble<Double>, DoubleUnaryOperator>)
                        MapperUtil.mapDouble(toDouble, d -> d);

        assertEquals(MapperType.DOUBLE_TO_DOUBLE, toDoubleMapper.mapperType());
    }

    @Test
    void mapString() {
        final ToString<String> toString = string -> string;
        final ToStringMapper<String, ToString<String>, UnaryOperator<String>> toStringMapper =
                (ToStringMapper<String, ToString<String>, UnaryOperator<String>>)
                        MapperUtil.mapString(toString, string -> string);

        assertEquals(MapperType.STRING_TO_STRING, toStringMapper.mapperType());
    }

    @Test
    void mapEnum() {
        final ToEnum<String, TestEnum> toEnum = ToEnum.of(TestEnum.class, TestEnum::valueOf);
        final ToEnumMapper<String, TestEnum, ToEnum<String, TestEnum>, UnaryOperator<TestEnum>> toEnumMapper =
                (ToEnumMapper<String, TestEnum, ToEnum<String, TestEnum>, UnaryOperator<TestEnum>>)
                        MapperUtil.mapEnum(toEnum, e -> e);

        assertEquals(TestEnum.class, toEnumMapper.enumClass());
        assertEquals(MapperType.ENUM_TO_ENUM, toEnumMapper.mapperType());
    }

    @Test
    void mapBigDecimal() {
        final ToBigDecimal<BigDecimal> toBigDecimal = decimal -> decimal;
        final ToBigDecimalMapper<BigDecimal, ToBigDecimal<BigDecimal>, UnaryOperator<BigDecimal>> toBigDecimalMapper =
                (ToBigDecimalMapper<BigDecimal, ToBigDecimal<BigDecimal>, UnaryOperator<BigDecimal>>)
                        MapperUtil.mapBigDecimal(toBigDecimal, decimal -> decimal);

        assertEquals(MapperType.BIG_DECIMAL_TO_BIG_DECIMAL, toBigDecimalMapper.mapperType());
    }

    @Test
    void mapBigDecimalToDouble() {
        final ToBigDecimal<BigDecimal> toBigDecimal = decimal -> decimal;
        final ToDoubleMapper<BigDecimal, ToBigDecimal<BigDecimal>, ToDoubleFunction<BigDecimal>> toDoubleMapper =
                (ToDoubleMapper<BigDecimal, ToBigDecimal<BigDecimal>, ToDoubleFunction<BigDecimal>>)
                        MapperUtil.mapBigDecimalToDouble(toBigDecimal, BigDecimal::doubleValue);

        assertEquals(1, toDoubleMapper.applyAsDouble(BigDecimal.valueOf(1)));
        assertEquals(MapperType.BIG_DECIMAL_TO_DOUBLE, toDoubleMapper.mapperType());
    }

    @Test
    void abstractMapper() {
        final DummyMapper dummyMapper = new DummyMapper(string -> string, string -> string);

        assertNotNull(dummyMapper.inner());
        assertNotNull(dummyMapper.mapper());
        assertNotEquals(0, dummyMapper.hashCode());

        final DummyMapper copy = dummyMapper;

        assertTrue(dummyMapper.equals(copy));
        assertFalse(dummyMapper.equals(null));
        assertFalse(dummyMapper.equals(1));

        final DummyMapper same = new DummyMapper(dummyMapper.inner(), dummyMapper.mapper());
        assertTrue(dummyMapper.equals(same));

        final DummyMapper differentMapperType = new DummyMapper(dummyMapper.inner(),
                dummyMapper.mapper(), MapperType.BIG_DECIMAL_TO_BIG_DECIMAL);
        assertFalse(dummyMapper.equals(differentMapperType));

        final DummyMapper differentMapper = new DummyMapper(dummyMapper.inner, String::toUpperCase);
        assertFalse(dummyMapper.equals(differentMapper));

        final DummyMapper differentInner = new DummyMapper(String::toUpperCase, dummyMapper.mapper());
        assertFalse(dummyMapper.equals(differentInner));

        final DummyMapper allDifferent = new DummyMapper(String::toUpperCase, String::toLowerCase,
                MapperType.BIG_DECIMAL_TO_BIG_DECIMAL);
        assertFalse(dummyMapper.equals(allDifferent));
    }

    private static final class DummyMapper extends AbstractMapper<String, ToString<String>, UnaryOperator<String>> {

        private final MapperType mapperType;

        DummyMapper(ToString<String> stringToString,
                UnaryOperator<String> stringUnaryOperator) {
            super(stringToString, stringUnaryOperator);
            this.mapperType = MapperType.STRING_TO_STRING;
        }

        DummyMapper(ToString<String> stringToString,
                UnaryOperator<String> stringUnaryOperator, MapperType mapperType) {
            super(stringToString, stringUnaryOperator);
            this.mapperType = mapperType;
        }

        @Override
        public MapperType mapperType() {
            return mapperType;
        }

        @Override
        public ExpressionType expressionType() {
            return ExpressionType.STRING;
        }
    }

    private enum TestEnum {
    }
}
