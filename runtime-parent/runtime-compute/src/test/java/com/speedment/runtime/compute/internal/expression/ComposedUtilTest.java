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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.compute.ToBigDecimal;
import com.speedment.runtime.compute.ToBoolean;
import com.speedment.runtime.compute.ToBooleanNullable;
import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToChar;
import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToEnum;
import com.speedment.runtime.compute.ToFloat;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToLong;
import com.speedment.runtime.compute.ToShort;
import com.speedment.runtime.compute.ToString;
import com.speedment.runtime.compute.internal.expression.ComposedUtil.ComposeToBigDecimal;
import com.speedment.runtime.compute.internal.expression.ComposedUtil.ComposeToBoolean;
import com.speedment.runtime.compute.internal.expression.ComposedUtil.ComposeToBooleanNullable;
import com.speedment.runtime.compute.internal.expression.ComposedUtil.ComposeToByte;
import com.speedment.runtime.compute.internal.expression.ComposedUtil.ComposeToChar;
import com.speedment.runtime.compute.internal.expression.ComposedUtil.ComposeToDouble;
import com.speedment.runtime.compute.internal.expression.ComposedUtil.ComposeToEnum;
import com.speedment.runtime.compute.internal.expression.ComposedUtil.ComposeToFloat;
import com.speedment.runtime.compute.internal.expression.ComposedUtil.ComposeToInt;
import com.speedment.runtime.compute.internal.expression.ComposedUtil.ComposeToLong;
import com.speedment.runtime.compute.internal.expression.ComposedUtil.ComposeToShort;
import com.speedment.runtime.compute.internal.expression.ComposedUtil.ComposeToString;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.function.Function;

@SuppressWarnings("unchecked")
final class ComposedUtilTest {

    @Test
    void composeToByte() {
        final ToByte<String> toByte = string -> string.getBytes()[0];
        final ComposeToByte<String, String, ToByte<String>> composeToByte =
                (ComposeToByte<String, String, ToByte<String>>)
                        ComposedUtil.composeToByte(Function.identity(), toByte);

        assertNotNull(composeToByte.firstStep());
        assertNotNull(composeToByte.secondStep());

        assertNull(composeToByte.apply(null));
        assertNotNull(composeToByte.apply("test"));
    }

    @Test
    void composeToShort() {
        final ToShort<String> toShort = string -> (short) string.length();
        final ComposeToShort<String, String, ToShort<String>> composeToShort =
                (ComposeToShort<String, String, ToShort<String>>)
                        ComposedUtil.composeToShort(Function.identity(), toShort);

        assertNotNull(composeToShort.firstStep());
        assertNotNull(composeToShort.secondStep());

        assertNull(composeToShort.apply(null));
        assertNotNull(composeToShort.apply("test"));
    }

    @Test
    void composeToInt() {
        final ToInt<String> toInt = String::length;
        final ComposeToInt<String, String, ToInt<String>> composeToInt =
                (ComposeToInt<String, String, ToInt<String>>)
                        ComposedUtil.composeToInt(Function.identity(), toInt);

        assertNotNull(composeToInt.firstStep());
        assertNotNull(composeToInt.secondStep());

        assertNull(composeToInt.apply(null));
        assertNotNull(composeToInt.apply("test"));
    }

    @Test
    void composeToLong() {
        final ToLong<String> toLong = String::length;
        final ComposeToLong<String, String, ToLong<String>> composeToLong =
                (ComposeToLong<String, String, ToLong<String>>)
                        ComposedUtil.composeToLong(Function.identity(), toLong);

        assertNotNull(composeToLong.firstStep());
        assertNotNull(composeToLong.secondStep());

        assertNull(composeToLong.apply(null));
        assertNotNull(composeToLong.apply("test"));
    }

    @Test
    void composeToFloat() {
        final ToFloat<String> toFloat = String::length;
        final ComposeToFloat<String, String, ToFloat<String>> composeToFloat =
                (ComposeToFloat<String, String, ToFloat<String>>)
                        ComposedUtil.composeToFloat(Function.identity(), toFloat);

        assertNotNull(composeToFloat.firstStep());
        assertNotNull(composeToFloat.secondStep());

        assertNull(composeToFloat.apply(null));
        assertNotNull(composeToFloat.apply("test"));
    }

    @Test
    void composeToDouble() {
        final ToDouble<String> toDouble = String::length;
        final ComposeToDouble<String, String, ToDouble<String>> composeToDouble =
                (ComposeToDouble<String, String, ToDouble<String>>)
                        ComposedUtil.composeToDouble(Function.identity(), toDouble);

        assertNotNull(composeToDouble.firstStep());
        assertNotNull(composeToDouble.secondStep());

        assertNull(composeToDouble.apply(null));
        assertNotNull(composeToDouble.apply("test"));
    }

    @Test
    void composeToBoolean() {
        final ToBoolean<String> toBoolean = string -> string.length() > 2;
        final ComposeToBoolean<String, String, ToBoolean<String>> composeToBoolean =
                (ComposeToBoolean<String, String, ToBoolean<String>>)
                        ComposedUtil.composeToBoolean(Function.identity(), toBoolean);

        assertNotNull(composeToBoolean.firstStep());
        assertNotNull(composeToBoolean.secondStep());

        assertFalse(composeToBoolean.applyAsBoolean(null));
        assertTrue(composeToBoolean.applyAsBoolean("test"));
        assertFalse(composeToBoolean.applyAsBoolean("a"));
    }

    @Test
    void composeToBooleanNullable() {
        final ToBooleanNullable<String> toBooleanNullable = string -> string.length() > 2;
        final ComposeToBooleanNullable<String, String, ToBooleanNullable<String>> composeToBooleanNullable =
                (ComposeToBooleanNullable<String, String, ToBooleanNullable<String>>)
                        ComposedUtil.composeToBooleanNullable(Function.identity(), toBooleanNullable);

        assertNotNull(composeToBooleanNullable.firstStep());
        assertNotNull(composeToBooleanNullable.secondStep());

        assertTrue(composeToBooleanNullable.applyAsBoolean("test"));

        assertNull(composeToBooleanNullable.apply(null));
        assertNotNull(composeToBooleanNullable.apply("test"));
    }

    @Test
    void composeToChar() {
        final ToChar<String> toChar = string -> string.toCharArray()[0];
        final ComposeToChar<String, String, ToChar<String>> composeToChar =
                (ComposeToChar<String, String, ToChar<String>>)
                        ComposedUtil.composeToChar(Function.identity(), toChar);

        assertNotNull(composeToChar.firstStep());
        assertNotNull(composeToChar.secondStep());

        assertEquals('t', composeToChar.applyAsChar("test"));

        assertNull(composeToChar.apply(null));
        assertNotNull(composeToChar.apply("test"));
    }

    @Test
    void composeToString() {
        final ToString<String> toString = string -> string;
        final ComposeToString<String, String, ToString<String>> composeToString =
                (ComposeToString<String, String, ToString<String>>)
                        ComposedUtil.composeToString(Function.identity(), toString);

        assertNotNull(composeToString.firstStep());
        assertNotNull(composeToString.secondStep());

        assertNull(composeToString.apply(null));
        assertNotNull(composeToString.apply("test"));
    }

    @Test
    void composeToBigDecimal() {
        final ToBigDecimal<String> toBigDecimal = string -> BigDecimal.valueOf(string.length());
        final ComposeToBigDecimal<String, String, ToBigDecimal<String>> composeToBigDecimal =
                (ComposeToBigDecimal<String, String, ToBigDecimal<String>>)
                        ComposedUtil.composeToBigDecimal(Function.identity(), toBigDecimal);

        assertNotNull(composeToBigDecimal.firstStep());
        assertNotNull(composeToBigDecimal.secondStep());

        assertNull(composeToBigDecimal.apply(null));
        assertNotNull(composeToBigDecimal.apply("test"));
    }

    @Test
    void composeToEnum() {
        final ToEnum<String, TestEnum> toEnum = ToEnum.of(TestEnum.class, TestEnum::valueOf);
        final ComposeToEnum<String, String, TestEnum, ToEnum<String, TestEnum>> composeToEnum =
                (ComposeToEnum<String, String, TestEnum, ToEnum<String, TestEnum>>)
                        ComposedUtil.composeToEnum(Function.identity(), toEnum);

        assertEquals(TestEnum.class, composeToEnum.enumClass());

        assertNotNull(composeToEnum.firstStep());
        assertNotNull(composeToEnum.secondStep());

        assertNull(composeToEnum.apply(null));
        assertNotNull(composeToEnum.apply("VALUE"));
    }

    private enum TestEnum {
        VALUE
    }
}
