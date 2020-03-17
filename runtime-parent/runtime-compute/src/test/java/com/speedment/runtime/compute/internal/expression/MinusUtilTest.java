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
import com.speedment.runtime.compute.ToShort;
import com.speedment.runtime.compute.expression.BinaryExpression.Operator;
import com.speedment.runtime.compute.expression.BinaryObjExpression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.internal.expression.MinusUtil.AbstractMinus;
import com.speedment.runtime.compute.internal.expression.MinusUtil.AbstractMinusByte;
import com.speedment.runtime.compute.internal.expression.MinusUtil.AbstractMinusDouble;
import com.speedment.runtime.compute.internal.expression.MinusUtil.AbstractMinusFloat;
import com.speedment.runtime.compute.internal.expression.MinusUtil.AbstractMinusInt;
import com.speedment.runtime.compute.internal.expression.MinusUtil.AbstractMinusLong;
import org.junit.jupiter.api.Test;

final class MinusUtilTest {

    @Test
    void shortMinusInt() {
        final ToInt<Short> instance = MinusUtil.shortMinusInt(s -> s, 1);
        assertEquals(0, instance.applyAsInt((short) 1));
    }

    @Test
    void shortMinusShort() {
        final ToShort<Short> instance = MinusUtil.shortMinusShort(s -> s, s -> s);
        assertEquals(0, instance.applyAsShort((short) 1));
    }

    @Test
    void floatMinusFloat() {
        ToFloat<Float> instance = MinusUtil.floatMinusFloat(f -> f, f -> f);
        assertEquals(0, instance.applyAsFloat((float) 1));

        instance = MinusUtil.floatMinusFloat(f -> f, 1);
        assertEquals(0, instance.applyAsFloat((float) 1));
    }

    @Test
    void abstractMinus() {
        final DummyMinus instance = new DummyMinus(i -> i, i -> i);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(Operator.MINUS, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyMinus copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyMinus sameFirst = new DummyMinus(instance.first(), i -> i + 1);

        assertFalse(instance.equals(sameFirst));

        final DummyMinus sameSecond = new DummyMinus(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyMinus allSame = new DummyMinus(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyMinus allDifferent = new DummyMinus(i -> i + 1, i -> i + 1);

        assertFalse(instance.equals(allDifferent));
    }

    @Test
    void abstractMinusByte() {
        final DummyMinusByte instance = new DummyMinusByte(i -> i, (byte) 1);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(BinaryObjExpression.Operator.MINUS, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyMinusByte copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyMinusByte sameFirst = new DummyMinusByte(instance.first(), (byte) 2);

        assertFalse(instance.equals(sameFirst));

        final DummyMinusByte sameSecond = new DummyMinusByte(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyMinusByte allSame = new DummyMinusByte(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyMinusByte allDifferent = new DummyMinusByte(i -> i + 1, (byte) 2);

        assertFalse(instance.equals(allDifferent));
    }

    @Test
    void abstractMinusInt() {
        final DummyMinusInt instance = new DummyMinusInt(i -> i, 1);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(BinaryObjExpression.Operator.MINUS, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyMinusInt copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyMinusInt sameFirst = new DummyMinusInt(instance.first(), 2);

        assertFalse(instance.equals(sameFirst));

        final DummyMinusInt sameSecond = new DummyMinusInt(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyMinusInt allSame = new DummyMinusInt(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyMinusInt allDifferent = new DummyMinusInt(i -> i + 1, 2);

        assertFalse(instance.equals(allDifferent));
    }

    @Test
    void abstractMinusLong() {
        final DummyMinusLong instance = new DummyMinusLong(i -> i, 1);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(BinaryObjExpression.Operator.MINUS, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyMinusLong copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyMinusLong sameFirst = new DummyMinusLong(instance.first(), 2);

        assertFalse(instance.equals(sameFirst));

        final DummyMinusLong sameSecond = new DummyMinusLong(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyMinusLong allSame = new DummyMinusLong(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyMinusLong allDifferent = new DummyMinusLong(i -> i + 1, 2);

        assertFalse(instance.equals(allDifferent));
    }

    @Test
    void abstractMinusFloat() {
        final DummyMinusFloat instance = new DummyMinusFloat(i -> i, 1);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(BinaryObjExpression.Operator.MINUS, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyMinusFloat copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyMinusFloat sameFirst = new DummyMinusFloat(instance.first(), 2);

        assertFalse(instance.equals(sameFirst));

        final DummyMinusFloat sameSecond = new DummyMinusFloat(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyMinusFloat allSame = new DummyMinusFloat(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyMinusFloat allDifferent = new DummyMinusFloat(i -> i + 1, 2);

        assertFalse(instance.equals(allDifferent));
    }

    @Test
    void abstractMinusDouble() {
        final DummyMinusDouble instance = new DummyMinusDouble(i -> i, 1);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(BinaryObjExpression.Operator.MINUS, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyMinusDouble copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyMinusDouble sameFirst = new DummyMinusDouble(instance.first(), 2);

        assertFalse(instance.equals(sameFirst));

        final DummyMinusDouble sameSecond = new DummyMinusDouble(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyMinusDouble allSame = new DummyMinusDouble(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyMinusDouble allDifferent = new DummyMinusDouble(i -> i + 1, 2);

        assertFalse(instance.equals(allDifferent));
    }

    private static final class DummyMinus extends
            AbstractMinus<Integer, ToInt<Integer>, ToInt<Integer>> {

        DummyMinus(ToInt<Integer> integerToInt,
                ToInt<Integer> integerToInt2) {
            super(integerToInt, integerToInt2);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

    private static final class DummyMinusByte extends AbstractMinusByte<Integer, ToInt<Integer>> {

        DummyMinusByte(ToInt<Integer> first, byte second) {
            super(first, second);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

    private static final class DummyMinusInt extends AbstractMinusInt<Integer, ToInt<Integer>> {

        DummyMinusInt(ToInt<Integer> first, int second) {
            super(first, second);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

    private static final class DummyMinusLong extends AbstractMinusLong<Integer, ToInt<Integer>> {
        DummyMinusLong(ToInt<Integer> first, long second) {
            super(first, second);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

    private static final class DummyMinusFloat extends AbstractMinusFloat<Integer, ToInt<Integer>> {
        DummyMinusFloat(ToInt<Integer> first, float second) {
            super(first, second);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

    private static final class DummyMinusDouble extends
            AbstractMinusDouble<Integer, ToInt<Integer>> {
        DummyMinusDouble(ToInt<Integer> first, double second) {
            super(first, second);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

}
