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
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.expression.UnaryExpression.Operator;
import com.speedment.runtime.compute.internal.expression.AbsUtil.AbstractAbs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

final class AbsUtilTest {

    @ParameterizedTest
    @ValueSource(strings = {"a", "bc", "foo", "test"})
    void absByte(String input) {
        final ToByte<String> toByte = string -> (byte) (string.length() > 3 ?
                -string.length() : string.length());

        final ToByte<String> absToByte = AbsUtil.absByte(toByte);

        assertNotNull(absToByte);
        assertEquals(input.length(), absToByte.applyAsByte(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "bc", "foo", "test"})
    void absShort(String input) {
        final ToShort<String> toShort = string -> (short) (string.length() > 3 ?
                -string.length() : string.length());

        final ToShort<String> absToShort = AbsUtil.absShort(toShort);

        assertNotNull(absToShort);
        assertEquals(input.length(), absToShort.applyAsShort(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "bc", "foo", "test"})
    void absInt(String input) {
        final ToInt<String> toInt = string -> string.length() > 3 ?
                -string.length() : string.length();

        final ToInt<String> absToInt = AbsUtil.absInt(toInt);

        assertNotNull(absToInt);
        assertEquals(input.length(), absToInt.applyAsInt(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "bc", "foo", "test"})
    void absLong(String input) {
        final ToLong<String> toLong = string -> string.length() > 3 ?
                -string.length() : string.length();

        final ToLong<String> absToLong = AbsUtil.absLong(toLong);

        assertNotNull(absToLong);
        assertEquals(input.length(), absToLong.applyAsLong(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "bc", "foo", "test"})
    void absFloat(String input) {
        final ToFloat<String> toFloat = string -> string.length() > 3 ?
                -string.length() : string.length();

        final ToFloat<String> absToFloat = AbsUtil.absFloat(toFloat);

        assertNotNull(absToFloat);
        assertEquals(input.length(), absToFloat.applyAsFloat(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "bc", "foo", "test"})
    void absDouble(String input) {
        final ToDouble<String> toDouble = string -> string.length() > 3 ?
                -string.length() : string.length();

        final ToDouble<String> absToDouble = AbsUtil.absDouble(toDouble);

        assertNotNull(absToDouble);
        assertEquals(input.length(), absToDouble.applyAsDouble(input));
    }

    @Test
    void abstractAbs() {
        DummyAbs abstractAbs = new DummyAbs(String::length);

        assertTrue(abstractAbs.inner() instanceof ToInt);
        assertEquals(Operator.ABS, abstractAbs.operator());

        final DummyAbs copy = abstractAbs;

        assertTrue(abstractAbs.equals(copy));
        assertFalse(abstractAbs.equals(null));

        final DummyAbs another1 = new DummyAbs(string -> 1);
        final DummyAbs another2 = new DummyAbs(abstractAbs.inner());

        assertFalse(abstractAbs.equals(another1));
        assertTrue(abstractAbs.equals(another2));

        assertNotEquals(0, abstractAbs.hashCode());
    }

    private static final class DummyAbs extends AbstractAbs<String, ToInt<String>> {

        DummyAbs(ToInt<String> stringToInt) {
            super(stringToInt);
        }

        @Override
        public ExpressionType expressionType() {
            return ExpressionType.INT;
        }
    }
}
