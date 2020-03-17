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

import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToFloat;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToLong;
import com.speedment.runtime.compute.ToShort;
import com.speedment.runtime.compute.expression.BinaryExpression.Operator;
import com.speedment.runtime.compute.expression.BinaryObjExpression;
import com.speedment.runtime.compute.internal.expression.DivideUtil.DivideObjToDouble;
import com.speedment.runtime.compute.internal.expression.DivideUtil.DivideToDouble;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("unchecked")
final class DivideUtilTest {

    @ParameterizedTest
    @ValueSource(strings = "test")
    void byteDivideInt(String input) {
        final ToByte<String> toByte = string -> string.getBytes()[0];
        final DivideObjToDouble<String, ToByte<String>, Integer> divide =
                (DivideObjToDouble<String, ToByte<String>, Integer>)
                        DivideUtil.byteDivideInt(toByte, 1);

        assertEquals(toByte.applyAsByte(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void byteDivideLong(String input) {
        final ToByte<String> toByte = string -> string.getBytes()[0];
        final DivideObjToDouble<String, ToByte<String>, Long> divide =
                (DivideObjToDouble<String, ToByte<String>, Long>)
                        DivideUtil.byteDivideLong(toByte, 1);

        assertEquals(toByte.applyAsByte(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void byteDivideDouble(String input) {
        final ToByte<String> toByte = string -> string.getBytes()[0];
        final DivideObjToDouble<String, ToByte<String>, Double> divide =
                (DivideObjToDouble<String, ToByte<String>, Double>)
                        DivideUtil.byteDivideDouble(toByte, 1);

        assertEquals(toByte.applyAsByte(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void shortDivideInt(String input) {
        final ToShort<String> toShort = string -> (short) string.length();
        final DivideObjToDouble<String, ToShort<String>, Integer> divide =
                (DivideObjToDouble<String, ToShort<String>, Integer>)
                        DivideUtil.shortDivideInt(toShort, 1);

        assertEquals(toShort.applyAsShort(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void shortDivideLong(String input) {
        final ToShort<String> toShort = string -> (short) string.length();
        final DivideObjToDouble<String, ToShort<String>, Long> divide =
                (DivideObjToDouble<String, ToShort<String>, Long>)
                        DivideUtil.shortDivideLong(toShort, 1);

        assertEquals(toShort.applyAsShort(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void shortDivideDouble(String input) {
        final ToShort<String> toShort = string -> (short) string.length();
        final DivideObjToDouble<String, ToShort<String>, Double> divide =
                (DivideObjToDouble<String, ToShort<String>, Double>)
                        DivideUtil.shortDivideDouble(toShort, 1);

        assertEquals(toShort.applyAsShort(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void intDivideInt(String input) {
        final ToInt<String> toInt = String::length;
        final DivideObjToDouble<String, ToInt<String>, Integer> divide =
                (DivideObjToDouble<String, ToInt<String>, Integer>)
                        DivideUtil.intDivideInt(toInt, 1);

        assertEquals(toInt.applyAsInt(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void intDivideLong(String input) {
        final ToInt<String> toInt = String::length;
        final DivideObjToDouble<String, ToInt<String>, Long> divide =
                (DivideObjToDouble<String, ToInt<String>, Long>)
                        DivideUtil.intDivideLong(toInt, 1);

        assertEquals(toInt.applyAsInt(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void intDivideDouble(String input) {
        final ToInt<String> toInt = String::length;
        final DivideObjToDouble<String, ToShort<String>, Double> divide =
                (DivideObjToDouble<String, ToShort<String>, Double>)
                        DivideUtil.intDivideDouble(toInt, 1);

        assertEquals(toInt.applyAsInt(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void longDivideInt(String input) {
        final ToLong<String> toLong = String::length;
        final DivideObjToDouble<String, ToLong<String>, Integer> divide =
                (DivideObjToDouble<String, ToLong<String>, Integer>)
                        DivideUtil.longDivideInt(toLong, 1);

        assertEquals(toLong.applyAsLong(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void longDivideLong(String input) {
        final ToLong<String> toLong = String::length;
        final DivideObjToDouble<String, ToLong<String>, Long> divide =
                (DivideObjToDouble<String, ToLong<String>, Long>)
                        DivideUtil.longDivideLong(toLong, 1);

        assertEquals(toLong.applyAsLong(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void longDivideDouble(String input) {
        final ToLong<String> toLong = String::length;
        final DivideObjToDouble<String, ToLong<String>, Double> divide =
                (DivideObjToDouble<String, ToLong<String>, Double>)
                        DivideUtil.longDivideDouble(toLong, 1);

        assertEquals(toLong.applyAsLong(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void floatDivideInt(String input) {
        final ToFloat<String> toFloat = String::length;
        final DivideObjToDouble<String, ToFloat<String>, Integer> divide =
                (DivideObjToDouble<String, ToFloat<String>, Integer>)
                        DivideUtil.floatDivideInt(toFloat, 1);

        assertEquals(toFloat.applyAsFloat(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void floatDivideLong(String input) {
        final ToFloat<String> toFloat = String::length;
        final DivideObjToDouble<String, ToFloat<String>, Long> divide =
                (DivideObjToDouble<String, ToFloat<String>, Long>)
                        DivideUtil.floatDivideLong(toFloat, 1);

        assertEquals(toFloat.applyAsFloat(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void floatDivideDouble(String input) {
        final ToFloat<String> toFloat = String::length;
        final DivideObjToDouble<String, ToFloat<String>, Double> divide =
                (DivideObjToDouble<String, ToFloat<String>, Double>)
                        DivideUtil.floatDivideDouble(toFloat, 1);

        assertEquals(toFloat.applyAsFloat(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void doubleDivideInt(String input) {
        final ToDouble<String> toDouble = String::length;
        final DivideObjToDouble<String, ToDouble<String>, Integer> divide =
                (DivideObjToDouble<String, ToDouble<String>, Integer>)
                        DivideUtil.doubleDivideInt(toDouble, 1);

        assertEquals(toDouble.applyAsDouble(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void doubleDivideLong(String input) {
        final ToDouble<String> toDouble = String::length;
        final DivideObjToDouble<String, ToDouble<String>, Long> divide =
                (DivideObjToDouble<String, ToDouble<String>, Long>)
                        DivideUtil.doubleDivideLong(toDouble, 1);

        assertEquals(toDouble.applyAsDouble(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void doubleDivideDouble(String input) {
        final ToDouble<String> toDouble = String::length;
        final DivideObjToDouble<String, ToDouble<String>, Double> divide =
                (DivideObjToDouble<String, ToDouble<String>, Double>)
                        DivideUtil.doubleDivideDouble(toDouble, 1);

        assertEquals(toDouble.applyAsDouble(input), divide.applyAsDouble(input));

        assertNotNull(divide.second());
        assertEquals(1, divide.second());
    }

    @Test
    void divideToDouble() {
        final ToDouble<String> toDouble = String::length;
        final DivideToDouble<String, ToDouble<String>, ToDouble<String>> divide =
                (DivideToDouble<String, ToDouble<String>, ToDouble<String>>)
                        DivideUtil.doubleDivideDouble(toDouble, toDouble);

        assertEquals(Operator.DIVIDE, divide.operator());
        assertNotNull(divide.first());
        assertNotNull(divide.second());
        assertNotEquals(0, divide.hashCode());

        final DivideToDouble<String, ToDouble<String>, ToDouble<String>> copy = divide;

        assertTrue(divide.equals(copy));
        assertFalse(divide.equals(null));

        final DivideToDouble<String, ToDouble<String>, ToDouble<String>> another1 =
                new DivideToDouble<String, ToDouble<String>, ToDouble<String>>(divide.first(), divide.second()) {
            @Override
            public double applyAsDouble(String object) {
                return 0;
            }
        };

        assertTrue(divide.equals(another1));

        final DivideToDouble<String, ToDouble<String>, ToDouble<String>> another2 =
                new DivideToDouble<String, ToDouble<String>, ToDouble<String>>(divide.first(), ToDouble.constant(1)) {
            @Override
            public double applyAsDouble(String object) {
                return 0;
            }
        };

        final DivideToDouble<String, ToDouble<String>, ToDouble<String>> another3 =
                new DivideToDouble<String, ToDouble<String>, ToDouble<String>>(ToDouble.constant(1), divide.second()) {
                    @Override
                    public double applyAsDouble(String object) {
                        return 0;
                    }
                };

        assertFalse(divide.equals(another2));
    }

    @Test
    void divideObjToDouble() {
        final ToDouble<String> toDouble = String::length;
        final DivideObjToDouble<String, ToDouble<String>, Double> divide =
                (DivideObjToDouble<String, ToDouble<String>, Double>)
                        DivideUtil.doubleDivideDouble(toDouble, 1);

        assertEquals(BinaryObjExpression.Operator.DIVIDE, divide.operator());
        assertNotNull(divide.first());
        assertNotEquals(0, divide.hashCode());

        final DivideObjToDouble<String, ToDouble<String>, Double> copy = divide;

        assertTrue(divide.equals(copy));
        assertFalse(divide.equals(null));
        assertFalse(divide.equals(1));

        final DivideObjToDouble<String, ToDouble<String>, Double> another1 =
                new DivideObjToDouble<String, ToDouble<String>, Double>(divide.first()) {

            @Override
            public Double second() {
                return null;
            }

            @Override
            public double applyAsDouble(String object) {
                return 0;
            }
        };

        assertTrue(divide.equals(another1));

        final DivideObjToDouble<String, ToDouble<String>, Double> another2 =
                new DivideObjToDouble<String, ToDouble<String>, Double>(ToDouble.constant(1)) {

            @Override
            public Double second() {
                return null;
            }

            @Override
            public double applyAsDouble(String object) {
            return 0;
            }
        };

        assertFalse(divide.equals(another2));

    }

}
