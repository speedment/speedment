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

import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToLong;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.expression.UnaryExpression.Operator;
import com.speedment.runtime.compute.internal.expression.CastUtil.AbstractCast;
import org.junit.jupiter.api.Test;

import java.util.Objects;

final class CastUtilTest {

    @Test
    void castBooleanToDouble() {
        final ToDouble<String> toDouble = CastUtil.castBooleanToDouble(Objects::nonNull);

        assertNotNull(toDouble);
        assertEquals(1, toDouble.applyAsDouble(""));
        assertEquals(0, toDouble.applyAsDouble(null));
    }

    @Test
    void castBooleanToInt() {
        final ToInt<String> toInt = CastUtil.castBooleanToInt(Objects::nonNull);

        assertNotNull(toInt);
        assertEquals(1, toInt.applyAsInt(""));
        assertEquals(0, toInt.applyAsInt(null));
    }

    @Test
    void castBooleanToLong() {
        final ToLong<String> toLong = CastUtil.castBooleanToLong(Objects::nonNull);

        assertNotNull(toLong);
        assertEquals(1, toLong.applyAsLong(""));
        assertEquals(0, toLong.applyAsLong(null));
    }

    @Test
    void castCharToDouble() {
        final ToDouble<String> toDouble = CastUtil.castCharToDouble(string -> string.toCharArray()[0]);

        assertNotNull(toDouble);
        assertNotEquals(0, toDouble.applyAsDouble("test"));
    }

    @Test
    void castCharToInt() {
        final ToInt<String> toInt = CastUtil.castCharToInt(string -> string.toCharArray()[0]);

        assertNotNull(toInt);
        assertNotEquals(0, toInt.applyAsInt("test"));
    }

    @Test
    void castCharToLong() {
        final ToLong<String> toLong = CastUtil.castCharToLong(string -> string.toCharArray()[0]);

        assertNotNull(toLong);
        assertNotEquals(0, toLong.applyAsLong("test"));
    }

    @Test
    void abstractCast() {
        DummyCast abstractCast = new DummyCast(String::length);

        assertTrue(abstractCast.inner() instanceof ToInt);
        assertEquals(Operator.CAST, abstractCast.operator());

        final DummyCast copy = abstractCast;

        assertTrue(abstractCast.equals(copy));
        assertFalse(abstractCast.equals(null));

        final DummyCast another1 = new DummyCast(string -> 1);
        final DummyCast another2 = new DummyCast(abstractCast.inner());

        assertFalse(abstractCast.equals(another1));
        assertTrue(abstractCast.equals(another2));

        assertNotEquals(0, abstractCast.hashCode());
    }

    private static final class DummyCast extends AbstractCast<String, ToInt<String>> {

        DummyCast(ToInt<String> stringToInt) {
            super(stringToInt);
        }

        @Override
        public ExpressionType expressionType() {
            return ExpressionType.INT;
        }
    }
}
