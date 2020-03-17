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
import com.speedment.runtime.compute.internal.expression.PlusUtil.AbstractPlus;
import com.speedment.runtime.compute.internal.expression.PlusUtil.AbstractPlusByte;
import com.speedment.runtime.compute.internal.expression.PlusUtil.AbstractPlusDouble;
import com.speedment.runtime.compute.internal.expression.PlusUtil.AbstractPlusFloat;
import com.speedment.runtime.compute.internal.expression.PlusUtil.AbstractPlusInt;
import com.speedment.runtime.compute.internal.expression.PlusUtil.AbstractPlusLong;
import org.junit.jupiter.api.Test;

final class PlusUtilTest {

    @Test
    void shortPlusInt() {
        final ToInt<Short> instance = PlusUtil.shortPlusInt(s -> s, 1);
        assertEquals(1, instance.applyAsInt((short) 0));
    }

    @Test
    void shortPlusShort() {
        final ToShort<Short> instance = PlusUtil.shortPlusShort(s -> s, s -> s);
        assertEquals(2, instance.applyAsShort((short) 1));
    }

    @Test
    void floatPlusFloat() {
        ToFloat<Float> instance = PlusUtil.floatPlusFloat(f -> f, f -> f);
        assertEquals(2, instance.applyAsFloat((float) 1));

        instance = PlusUtil.floatPlusFloat(f -> f, 1);
        assertEquals(1, instance.applyAsFloat((float) 0));
    }

    @Test
    void abstractPlus() {
        final DummyPlus instance = new DummyPlus(i -> i, i -> i);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(Operator.PLUS, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyPlus copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyPlus sameFirst = new DummyPlus(instance.first(), i -> i + 1);

        assertFalse(instance.equals(sameFirst));

        final DummyPlus sameSecond = new DummyPlus(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyPlus allSame = new DummyPlus(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyPlus allDifferent = new DummyPlus(i -> i + 1, i -> i + 1);

        assertFalse(instance.equals(allDifferent));
    }

    @Test
    void abstractPlusByte() {
        final DummyPlusByte instance = new DummyPlusByte(i -> i, (byte) 1);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(BinaryObjExpression.Operator.PLUS, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyPlusByte copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyPlusByte sameFirst = new DummyPlusByte(instance.first(), (byte) 2);

        assertFalse(instance.equals(sameFirst));

        final DummyPlusByte sameSecond = new DummyPlusByte(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyPlusByte allSame = new DummyPlusByte(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyPlusByte allDifferent = new DummyPlusByte(i -> i + 1, (byte) 2);

        assertFalse(instance.equals(allDifferent));
    }

    @Test
    void abstractPlusInt() {
        final DummyPlusInt instance = new DummyPlusInt(i -> i, 1);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(BinaryObjExpression.Operator.PLUS, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyPlusInt copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyPlusInt sameFirst = new DummyPlusInt(instance.first(), 2);

        assertFalse(instance.equals(sameFirst));

        final DummyPlusInt sameSecond = new DummyPlusInt(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyPlusInt allSame = new DummyPlusInt(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyPlusInt allDifferent = new DummyPlusInt(i -> i + 1, 2);

        assertFalse(instance.equals(allDifferent));
    }

    @Test
    void abstractPlusLong() {
        final DummyPlusLong instance = new DummyPlusLong(i -> i, 1);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(BinaryObjExpression.Operator.PLUS, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyPlusLong copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyPlusLong sameFirst = new DummyPlusLong(instance.first(), 2);

        assertFalse(instance.equals(sameFirst));

        final DummyPlusLong sameSecond = new DummyPlusLong(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyPlusLong allSame = new DummyPlusLong(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyPlusLong allDifferent = new DummyPlusLong(i -> i + 1, 2);

        assertFalse(instance.equals(allDifferent));
    }

    @Test
    void abstractPlusFloat() {
        final DummyPlusFloat instance = new DummyPlusFloat(i -> i, 1);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(BinaryObjExpression.Operator.PLUS, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyPlusFloat copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyPlusFloat sameFirst = new DummyPlusFloat(instance.first(), 2);

        assertFalse(instance.equals(sameFirst));

        final DummyPlusFloat sameSecond = new DummyPlusFloat(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyPlusFloat allSame = new DummyPlusFloat(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyPlusFloat allDifferent = new DummyPlusFloat(i -> i + 1, 2);

        assertFalse(instance.equals(allDifferent));
    }

    @Test
    void abstractPlusDouble() {
        final DummyPlusDouble instance = new DummyPlusDouble(i -> i, 1);

        assertNotNull(instance.first());
        assertNotNull(instance.second());
        assertEquals(BinaryObjExpression.Operator.PLUS, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyPlusDouble copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        final DummyPlusDouble sameFirst = new DummyPlusDouble(instance.first(), 2);

        assertFalse(instance.equals(sameFirst));

        final DummyPlusDouble sameSecond = new DummyPlusDouble(i -> i + 1, instance.second());

        assertFalse(instance.equals(sameSecond));

        final DummyPlusDouble allSame = new DummyPlusDouble(instance.first(), instance.second());

        assertTrue(instance.equals(allSame));

        final DummyPlusDouble allDifferent = new DummyPlusDouble(i -> i + 1, 2);

        assertFalse(instance.equals(allDifferent));
    }

    private static final class DummyPlus extends
            AbstractPlus<Integer, ToInt<Integer>, ToInt<Integer>> {

        DummyPlus(ToInt<Integer> integerToInt,
                ToInt<Integer> integerToInt2) {
            super(integerToInt, integerToInt2);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }
    
    private static final class DummyPlusByte extends AbstractPlusByte<Integer, ToInt<Integer>> {

        DummyPlusByte(ToInt<Integer> first, byte second) {
            super(first, second);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

    private static final class DummyPlusInt extends AbstractPlusInt<Integer, ToInt<Integer>> {

        DummyPlusInt(ToInt<Integer> first, int second) {
            super(first, second);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

    private static final class DummyPlusLong extends AbstractPlusLong<Integer, ToInt<Integer>> {
        DummyPlusLong(ToInt<Integer> first, long second) {
            super(first, second);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

    private static final class DummyPlusFloat extends AbstractPlusFloat<Integer, ToInt<Integer>> {
        DummyPlusFloat(ToInt<Integer> first, float second) {
            super(first, second);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }
    
    private static final class DummyPlusDouble extends AbstractPlusDouble<Integer, ToInt<Integer>> {
        DummyPlusDouble(ToInt<Integer> first, double second) {
            super(first, second);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

}
