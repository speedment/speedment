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

import com.speedment.runtime.compute.ToFloat;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.expression.BinaryExpression.Operator;
import com.speedment.runtime.compute.expression.BinaryObjExpression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.internal.expression.MultiplyUtil.AbstractMultiply;
import com.speedment.runtime.compute.internal.expression.MultiplyUtil.AbstractMultiplyByte;
import com.speedment.runtime.compute.internal.expression.MultiplyUtil.AbstractMultiplyDouble;
import com.speedment.runtime.compute.internal.expression.MultiplyUtil.AbstractMultiplyFloat;
import com.speedment.runtime.compute.internal.expression.MultiplyUtil.AbstractMultiplyInt;
import com.speedment.runtime.compute.internal.expression.MultiplyUtil.AbstractMultiplyLong;
import org.junit.jupiter.api.Test;

final class MultiplyUtilTest {

    @Test
    void shortMultiplyInt() {
        final ToInt<Short> instance = MultiplyUtil.shortMultiplyInt(s -> s, 1);
        assertEquals(1, instance.applyAsInt((short) 1));
    }

    @Test
    void shortMultiplyShort() {
        final ToInt<Short> instance = MultiplyUtil.shortMultiplyShort(s -> s, s -> s);
        assertEquals(1, instance.applyAsInt((short) 1));
    }

    @Test
    void floatMultiplyFloat() {
        ToFloat<Float> instance = MultiplyUtil.floatMultiplyFloat(f -> f, f -> f);
        assertEquals(1, instance.applyAsFloat((float) 1));

        instance = MultiplyUtil.floatMultiplyFloat(f -> f, 1);
        assertEquals(1, instance.applyAsFloat((float) 1));
    }

    @Test
    void abstractMultiply() {
        final DummyMultiply instance = new DummyMultiply(i -> i, i -> i);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(Operator.MULTIPLY, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyMultiply copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyMultiply sameFirst = new DummyMultiply(instance.first(), i -> i + 1);

        assertFalse(instance.equals(sameFirst));

        final DummyMultiply sameSecond = new DummyMultiply(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyMultiply allSame = new DummyMultiply(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyMultiply allDifferent = new DummyMultiply(i -> i + 1, i -> i + 1);

        assertFalse(instance.equals(allDifferent));
    }

    @Test
    void abstractMultiplyByte() {
        final DummyMultiplyByte instance = new DummyMultiplyByte(i -> i, (byte) 1);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(BinaryObjExpression.Operator.MULTIPLY, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyMultiplyByte copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyMultiplyByte sameFirst = new DummyMultiplyByte(instance.first(), (byte) 2);

        assertFalse(instance.equals(sameFirst));

        final DummyMultiplyByte sameSecond = new DummyMultiplyByte(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyMultiplyByte allSame = new DummyMultiplyByte(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyMultiplyByte allDifferent = new DummyMultiplyByte(i -> i + 1, (byte) 2);

        assertFalse(instance.equals(allDifferent));
    }

    @Test
    void abstractMultiplyInt() {
        final DummyMultiplyInt instance = new DummyMultiplyInt(i -> i, 1);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(BinaryObjExpression.Operator.MULTIPLY, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyMultiplyInt copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyMultiplyInt sameFirst = new DummyMultiplyInt(instance.first(), 2);

        assertFalse(instance.equals(sameFirst));

        final DummyMultiplyInt sameSecond = new DummyMultiplyInt(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyMultiplyInt allSame = new DummyMultiplyInt(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyMultiplyInt allDifferent = new DummyMultiplyInt(i -> i + 1, 2);

        assertFalse(instance.equals(allDifferent));
    }

    @Test
    void abstractMultiplyLong() {
        final DummyMultiplyLong instance = new DummyMultiplyLong(i -> i, 1);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(BinaryObjExpression.Operator.MULTIPLY, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyMultiplyLong copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyMultiplyLong sameFirst = new DummyMultiplyLong(instance.first(), 2);

        assertFalse(instance.equals(sameFirst));

        final DummyMultiplyLong sameSecond = new DummyMultiplyLong(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyMultiplyLong allSame = new DummyMultiplyLong(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyMultiplyLong allDifferent = new DummyMultiplyLong(i -> i + 1, 2);

        assertFalse(instance.equals(allDifferent));
    }

    @Test
    void abstractMultiplyFloat() {
        final DummyMultiplyFloat instance = new DummyMultiplyFloat(i -> i, 1);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(BinaryObjExpression.Operator.MULTIPLY, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyMultiplyFloat copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyMultiplyFloat sameFirst = new DummyMultiplyFloat(instance.first(), 2);

        assertFalse(instance.equals(sameFirst));

        final DummyMultiplyFloat sameSecond = new DummyMultiplyFloat(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyMultiplyFloat allSame = new DummyMultiplyFloat(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyMultiplyFloat allDifferent = new DummyMultiplyFloat(i -> i + 1, 2);

        assertFalse(instance.equals(allDifferent));
    }

    @Test
    void abstractMultiplyDouble() {
        final DummyMultiplyDouble instance = new DummyMultiplyDouble(i -> i, 1);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(BinaryObjExpression.Operator.MULTIPLY, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyMultiplyDouble copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyMultiplyDouble sameFirst = new DummyMultiplyDouble(instance.first(), 2);

        assertFalse(instance.equals(sameFirst));

        final DummyMultiplyDouble sameSecond = new DummyMultiplyDouble(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyMultiplyDouble allSame = new DummyMultiplyDouble(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyMultiplyDouble allDifferent = new DummyMultiplyDouble(i -> i + 1, 2);

        assertFalse(instance.equals(allDifferent));
    }

    private static final class DummyMultiply extends
            AbstractMultiply<Integer, ToInt<Integer>, ToInt<Integer>> {

        DummyMultiply(ToInt<Integer> integerToInt,
                ToInt<Integer> integerToInt2) {
            super(integerToInt, integerToInt2);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

    private static final class DummyMultiplyByte extends
            AbstractMultiplyByte<Integer, ToInt<Integer>> {

        DummyMultiplyByte(ToInt<Integer> first, byte second) {
            super(first, second);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

    private static final class DummyMultiplyInt extends
            AbstractMultiplyInt<Integer, ToInt<Integer>> {

        DummyMultiplyInt(ToInt<Integer> first, int second) {
            super(first, second);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

    private static final class DummyMultiplyLong extends
            AbstractMultiplyLong<Integer, ToInt<Integer>> {
        DummyMultiplyLong(ToInt<Integer> first, long second) {
            super(first, second);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

    private static final class DummyMultiplyFloat extends
            AbstractMultiplyFloat<Integer, ToInt<Integer>> {
        DummyMultiplyFloat(ToInt<Integer> first, float second) {
            super(first, second);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

    private static final class DummyMultiplyDouble extends
            AbstractMultiplyDouble<Integer, ToInt<Integer>> {
        DummyMultiplyDouble(ToInt<Integer> first, double second) {
            super(first, second);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

}
