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
package com.speedment.runtime.compute.expression;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.speedment.runtime.compute.ToBoolean;
import com.speedment.runtime.compute.ToBooleanNullable;
import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToByteNullable;
import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.ToFloat;
import com.speedment.runtime.compute.ToFloatNullable;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToIntNullable;
import com.speedment.runtime.compute.ToLong;
import com.speedment.runtime.compute.ToLongNullable;
import com.speedment.runtime.compute.ToShort;
import com.speedment.runtime.compute.ToShortNullable;
import com.speedment.runtime.compute.ToString;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

final class ExpressionsTest {

    @Test
    void constructor()
            throws NoSuchMethodException {
        Constructor<Expressions> constructor = Expressions.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void byteToDouble() {
        assertNotNull(Expressions.byteToDouble(b -> (byte) b));
    }

    @Test
    void shortToDouble() {
        assertNotNull(Expressions.shortToDouble(b -> (short) b));
    }

    @Test
    void intToDouble() {
        assertNotNull(Expressions.intToDouble(b -> (int) b));
    }

    @Test
    void longToDouble() {
        assertNotNull(Expressions.longToDouble(b -> (long) b));
    }

    @Test
    void floatToDouble() {
        assertNotNull(Expressions.floatToDouble(b -> (float) b));
    }

    @Test
    void byteToDoubleNullable() {
        assertNotNull(Expressions.byteToDoubleNullable(b -> (byte) b));
    }

    @Test
    void shortToDoubleNullable() {
        assertNotNull(Expressions.shortToDoubleNullable(b -> (short) b));
    }

    @Test
    void intToDoubleNullable() {
        assertNotNull(Expressions.intToDoubleNullable(b -> (int) b));
    }

    @Test
    void longToDoubleNullable() {
        assertNotNull(Expressions.longToDoubleNullable(b -> (long) b));
    }

    @Test
    void floatToDoubleNullable() {
        assertNotNull(Expressions.floatToDoubleNullable(b -> (float) b));
    }

    @Test
    void pow() {
        assertNotNull(Expressions.pow(ToByte.of(b -> (byte) 1), ToDouble.constant(1)));
        assertNotNull(Expressions.pow(ToShort.of(s -> (short) 1), ToDouble.constant(1)));
        assertNotNull(Expressions.pow(ToInt.of(i -> 1), ToDouble.constant(1)));
        assertNotNull(Expressions.pow(ToLong.of(l -> 1), ToDouble.constant(1)));
        assertNotNull(Expressions.pow(ToFloat.of(f -> 1), ToDouble.constant(1)));
        assertNotNull(Expressions.pow(ToDouble.of(d -> 1), ToDouble.constant(1)));
    }

    @Test
    void powOrNull() {
        assertNotNull(Expressions.powOrNull(ToByteNullable.of(b -> (byte) 1), 1));
        assertNotNull(Expressions.powOrNull(ToByteNullable.of(b -> (byte) 1), ToInt.of(i -> 1)));
        assertNotNull(Expressions.powOrNull(ToByteNullable.of(b -> (byte) 1), 1d));
        assertNotNull(Expressions.powOrNull(ToByteNullable.of(b -> (byte) 1), ToDouble.of(d -> 1)));

        assertNotNull(Expressions.powOrNull(ToShortNullable.of(s -> (short) 1), 1));
        assertNotNull(Expressions.powOrNull(ToShortNullable.of(s -> (short) 1), ToInt.of(i -> 1)));
        assertNotNull(Expressions.powOrNull(ToShortNullable.of(s -> (short) 1), 1d));
        assertNotNull(Expressions.powOrNull(ToShortNullable.of(s -> (short) 1), ToDouble.of(d -> 1)));

        assertNotNull(Expressions.powOrNull(ToIntNullable.of(i -> 1), 1));
        assertNotNull(Expressions.powOrNull(ToIntNullable.of(i -> 1), ToInt.of(i -> 1)));
        assertNotNull(Expressions.powOrNull(ToIntNullable.of(i -> 1), 1d));
        assertNotNull(Expressions.powOrNull(ToIntNullable.of(i -> 1), ToDouble.of(d -> 1)));

        assertNotNull(Expressions.powOrNull(ToLongNullable.of(l -> 1L), 1));
        assertNotNull(Expressions.powOrNull(ToLongNullable.of(l -> 1L), ToInt.of(i -> 1)));
        assertNotNull(Expressions.powOrNull(ToLongNullable.of(l -> 1L), 1d));
        assertNotNull(Expressions.powOrNull(ToLongNullable.of(l -> 1L), ToDouble.of(d -> 1)));

        assertNotNull(Expressions.powOrNull(ToFloatNullable.of(f -> 1f), 1));
        assertNotNull(Expressions.powOrNull(ToFloatNullable.of(f -> 1f), ToInt.of(i -> 1)));
        assertNotNull(Expressions.powOrNull(ToFloatNullable.of(f -> 1f), 1d));
        assertNotNull(Expressions.powOrNull(ToFloatNullable.of(f -> 1f), ToDouble.of(d -> 1)));

        assertNotNull(Expressions.powOrNull(ToDoubleNullable.of(d -> 1d), 1));
        assertNotNull(Expressions.powOrNull(ToDoubleNullable.of(d -> 1d), ToInt.of(i -> 1)));
        assertNotNull(Expressions.powOrNull(ToDoubleNullable.of(d -> 1d), 1d));
        assertNotNull(Expressions.powOrNull(ToDoubleNullable.of(d -> 1d), ToDouble.of(d -> 1)));
    }

    @Test
    void negate() {
        assertNotNull(Expressions.negate(ToBoolean.of(b -> true)));
    }

    @Test
    void negateOrNull() {
        assertNotNull(Expressions.negateOrNull(ToBooleanNullable.of(b -> true)));
    }

    @Test
    void plus() {
        assertNotNull(Expressions.plus(ToShort.of(s -> (short) 1), 1));
        assertNotNull(Expressions.plus(ToShort.of(s -> (short) 1), ToShort.of(s -> (short) 1)));

        assertNotNull(Expressions.plus(ToFloat.of(f -> 1f), 1f));
        assertNotNull(Expressions.plus(ToFloat.of(f -> 1f), ToFloat.of(f -> 1f)));
    }

    @Test
    void minus() {
        assertNotNull(Expressions.minus(ToShort.of(s -> (short) 1), 1));
        assertNotNull(Expressions.minus(ToShort.of(s -> (short) 1), ToShort.of(s -> (short) 1)));

        assertNotNull(Expressions.minus(ToFloat.of(f -> 1f), 1f));
        assertNotNull(Expressions.minus(ToFloat.of(f -> 1f), ToFloat.of(f -> 1f)));
    }

    @Test
    void multiply() {
        assertNotNull(Expressions.multiply(ToShort.of(s -> (short) 1), 1));
        assertNotNull(Expressions.multiply(ToShort.of(s -> (short) 1), ToShort.of(s -> (short) 1)));

        assertNotNull(Expressions.multiply(ToFloat.of(f -> 1f), 1f));
        assertNotNull(Expressions.multiply(ToFloat.of(f -> 1f), ToFloat.of(f -> 1f)));
    }

    @Test
    void joining() {
        assertNotNull(Expressions.joining(
            ToString.of(string -> (String) string),
            ToString.of(string -> (String) string)));
        assertNotNull(Expressions.joining(
            " ",
            ToString.of(string -> (String) string),
            ToString.of(string -> (String) string)));
        assertNotNull(Expressions.joining(
            " ",
            "prefix",
            "suffix",
            ToString.of(string -> (String) string),
            ToString.of(string -> (String) string)));

        assertNotNull(Expressions.joining(
            ToString.of(string -> (String) string),
            ToString.of(string -> (String) string),
            ToString.of(string -> (String) string)));
        assertNotNull(Expressions.joining(
            " ",
            ToString.of(string -> (String) string),
            ToString.of(string -> (String) string),
            ToString.of(string -> (String) string)));
        assertNotNull(Expressions.joining(
            " ",
            "prefix",
            "suffix",
            ToString.of(string -> (String) string),
            ToString.of(string -> (String) string),
            ToString.of(string -> (String) string)));
    }
}
