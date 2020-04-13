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
import com.speedment.runtime.compute.expression.BinaryExpression;
import com.speedment.runtime.compute.expression.BinaryObjExpression.Operator;
import com.speedment.runtime.compute.internal.expression.PowUtil.DoublePower;
import com.speedment.runtime.compute.internal.expression.PowUtil.IntPower;
import com.speedment.runtime.compute.internal.expression.PowUtil.ToDoublePower;
import com.speedment.runtime.compute.internal.expression.PowUtil.ToIntPower;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.IntStream;

final class PowUtilTest {

    @ParameterizedTest
    @ValueSource(bytes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void bytePowInt(byte input) {
        final ToByte<Byte> toByte = b -> b;

        final ToDouble<Byte> toZeroth = PowUtil.bytePowInt(toByte, 0);
        assertEquals(1, toZeroth.applyAsDouble(input));

        final ToDouble<Byte> toFirst = PowUtil.bytePowInt(toByte, 1);
        assertEquals(input, toFirst.applyAsDouble(input));

        final ToDouble<Byte> toSecond = PowUtil.bytePowInt(toByte, 2);
        assertEquals(input * input, toSecond.applyAsDouble(input));

        final ToDouble<Byte> toThird = PowUtil.bytePowInt(toByte, 3);
        assertEquals(input * input * input, toThird.applyAsDouble(input));

        final ToDouble<Byte> reciprocal = PowUtil.bytePowInt(toByte, -1);
        assertEquals(1d / input, reciprocal.applyAsDouble(input));

        IntStream.range(4, 10).forEach(n -> {
            final ToDouble<Byte> toNth = PowUtil.bytePowInt(toByte, n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });

        final ToDouble<Byte> toZeroth_ = PowUtil.bytePowInt(toByte, i -> 0);
        assertEquals(1, toZeroth_.applyAsDouble(input));

        final ToDouble<Byte> toFirst_ = PowUtil.bytePowInt(toByte, i -> 1);
        assertEquals(input, toFirst_.applyAsDouble(input));

        final ToDouble<Byte> toSecond_ = PowUtil.bytePowInt(toByte, i -> 2);
        assertEquals(input * input, toSecond_.applyAsDouble(input));

        final ToDouble<Byte> toThird_ = PowUtil.bytePowInt(toByte, i -> 3);
        assertEquals(input * input * input, toThird_.applyAsDouble(input));

        final ToDouble<Byte> reciprocal_ = PowUtil.bytePowInt(toByte, i -> -1);
        assertEquals(1d / input, reciprocal_.applyAsDouble(input));

        IntStream.range(4, 10).forEach(n -> {
            final ToDouble<Byte> toNth = PowUtil.bytePowInt(toByte, i -> n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });
    }

    @ParameterizedTest
    @ValueSource(bytes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void bytePowDouble(byte input) {
        final ToByte<Byte> toByte = b -> b;

        IntStream.range(1, 10).forEach(n -> {
            final ToDouble<Byte> toNth = PowUtil.bytePowDouble(toByte, n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });

        IntStream.range(1, 10).forEach(n -> {
            final ToDouble<Byte> toNth = PowUtil.bytePowDouble(toByte, i -> n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });
    }

    @ParameterizedTest
    @ValueSource(shorts = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void shortPowInt(short input) {
        final ToShort<Short> toShort = s -> s;

        final ToDouble<Short> toZeroth = PowUtil.shortPowInt(toShort, 0);
        assertEquals(1, toZeroth.applyAsDouble(input));

        final ToDouble<Short> toFirst = PowUtil.shortPowInt(toShort, 1);
        assertEquals(input, toFirst.applyAsDouble(input));

        final ToDouble<Short> toSecond = PowUtil.shortPowInt(toShort, 2);
        assertEquals(input * input, toSecond.applyAsDouble(input));

        final ToDouble<Short> toThird = PowUtil.shortPowInt(toShort, 3);
        assertEquals(input * input * input, toThird.applyAsDouble(input));

        final ToDouble<Short> reciprocal = PowUtil.shortPowInt(toShort, -1);
        assertEquals(1d / input, reciprocal.applyAsDouble(input));

        IntStream.range(4, 10).forEach(n -> {
            final ToDouble<Short> toNth = PowUtil.shortPowInt(toShort, n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });

        final ToDouble<Short> toZeroth_ = PowUtil.shortPowInt(toShort, i -> 0);
        assertEquals(1, toZeroth_.applyAsDouble(input));

        final ToDouble<Short> toFirst_ = PowUtil.shortPowInt(toShort, i -> 1);
        assertEquals(input, toFirst_.applyAsDouble(input));

        final ToDouble<Short> toSecond_ = PowUtil.shortPowInt(toShort, i -> 2);
        assertEquals(input * input, toSecond_.applyAsDouble(input));

        final ToDouble<Short> toThird_ = PowUtil.shortPowInt(toShort, i -> 3);
        assertEquals(input * input * input, toThird_.applyAsDouble(input));

        final ToDouble<Short> reciprocal_ = PowUtil.shortPowInt(toShort, i -> -1);
        assertEquals(1d / input, reciprocal_.applyAsDouble(input));

        IntStream.range(4, 10).forEach(n -> {
            final ToDouble<Short> toNth = PowUtil.shortPowInt(toShort, i -> n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });
    }

    @ParameterizedTest
    @ValueSource(shorts = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void shortPowDouble(short input) {
        final ToShort<Short> toShort = s -> s;

        IntStream.range(1, 10).forEach(n -> {
            final ToDouble<Short> toNth = PowUtil.shortPowDouble(toShort, n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });

        IntStream.range(1, 10).forEach(n -> {
            final ToDouble<Short> toNth = PowUtil.shortPowDouble(toShort, i -> n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void intPowInt(int input) {
        final ToInt<Integer> toInt = i -> i;

        final ToDouble<Integer> toZeroth = PowUtil.intPowInt(toInt, 0);
        assertEquals(1, toZeroth.applyAsDouble(input));

        final ToDouble<Integer> toFirst = PowUtil.intPowInt(toInt, 1);
        assertEquals(input, toFirst.applyAsDouble(input));

        final ToDouble<Integer> toSecond = PowUtil.intPowInt(toInt, 2);
        assertEquals(input * input, toSecond.applyAsDouble(input));

        final ToDouble<Integer> toThird = PowUtil.intPowInt(toInt, 3);
        assertEquals(input * input * input, toThird.applyAsDouble(input));

        final ToDouble<Integer> reciprocal = PowUtil.intPowInt(toInt, -1);
        assertEquals(1d / input, reciprocal.applyAsDouble(input));

        IntStream.range(4, 10).forEach(n -> {
            final ToDouble<Integer> toNth = PowUtil.intPowInt(toInt, n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });

        final ToDouble<Integer> toZeroth_ = PowUtil.intPowInt(toInt, i -> 0);
        assertEquals(1, toZeroth_.applyAsDouble(input));

        final ToDouble<Integer> toFirst_ = PowUtil.intPowInt(toInt, i -> 1);
        assertEquals(input, toFirst_.applyAsDouble(input));

        final ToDouble<Integer> toSecond_ = PowUtil.intPowInt(toInt, i -> 2);
        assertEquals(input * input, toSecond_.applyAsDouble(input));

        final ToDouble<Integer> toThird_ = PowUtil.intPowInt(toInt, i -> 3);
        assertEquals(input * input * input, toThird_.applyAsDouble(input));

        final ToDouble<Integer> reciprocal_ = PowUtil.intPowInt(toInt, i -> -1);
        assertEquals(1d / input, reciprocal_.applyAsDouble(input));

        IntStream.range(4, 10).forEach(n -> {
            final ToDouble<Integer> toNth = PowUtil.intPowInt(toInt, i -> n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void intPowDouble(int input) {
        final ToInt<Integer> toInt = i -> i;

        IntStream.range(1, 10).forEach(n -> {
            final ToDouble<Integer> toNth = PowUtil.intPowDouble(toInt, n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });

        IntStream.range(1, 10).forEach(n -> {
            final ToDouble<Integer> toNth = PowUtil.intPowDouble(toInt, i -> n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void longPowInt(long input) {
        final ToLong<Long> toLong = l -> l;

        final ToDouble<Long> toZeroth = PowUtil.longPowInt(toLong, 0);
        assertEquals(1, toZeroth.applyAsDouble(input));

        final ToDouble<Long> toFirst = PowUtil.longPowInt(toLong, 1);
        assertEquals(input, toFirst.applyAsDouble(input));

        final ToDouble<Long> toSecond = PowUtil.longPowInt(toLong, 2);
        assertEquals(input * input, toSecond.applyAsDouble(input));

        final ToDouble<Long> toThird = PowUtil.longPowInt(toLong, 3);
        assertEquals(input * input * input, toThird.applyAsDouble(input));

        final ToDouble<Long> reciprocal = PowUtil.longPowInt(toLong, -1);
        assertEquals(1d / input, reciprocal.applyAsDouble(input));

        IntStream.range(4, 10).forEach(n -> {
            final ToDouble<Long> toNth = PowUtil.longPowInt(toLong, n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });

        final ToDouble<Long> toZeroth_ = PowUtil.longPowInt(toLong, i -> 0);
        assertEquals(1, toZeroth_.applyAsDouble(input));

        final ToDouble<Long> toFirst_ = PowUtil.longPowInt(toLong, i -> 1);
        assertEquals(input, toFirst_.applyAsDouble(input));

        final ToDouble<Long> toSecond_ = PowUtil.longPowInt(toLong, i -> 2);
        assertEquals(input * input, toSecond_.applyAsDouble(input));

        final ToDouble<Long> toThird_ = PowUtil.longPowInt(toLong, i -> 3);
        assertEquals(input * input * input, toThird_.applyAsDouble(input));

        final ToDouble<Long> reciprocal_ = PowUtil.longPowInt(toLong, i -> -1);
        assertEquals(1d / input, reciprocal_.applyAsDouble(input));

        IntStream.range(4, 10).forEach(n -> {
            final ToDouble<Long> toNth = PowUtil.longPowInt(toLong, i -> n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void longPowDouble(long input) {
        final ToLong<Long> toInt = i -> i;

        IntStream.range(1, 10).forEach(n -> {
            final ToDouble<Long> toNth = PowUtil.longPowDouble(toInt, n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });

        IntStream.range(1, 10).forEach(n -> {
            final ToDouble<Long> toNth = PowUtil.longPowDouble(toInt, i -> n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });
    }

    @ParameterizedTest
    @ValueSource(floats = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void floatPowInt(float input) {
        final ToFloat<Float> toFloat = l -> l;

        final ToDouble<Float> toZeroth = PowUtil.floatPowInt(toFloat, 0);
        assertEquals(1, toZeroth.applyAsDouble(input));

        final ToDouble<Float> toFirst = PowUtil.floatPowInt(toFloat, 1);
        assertEquals(input, toFirst.applyAsDouble(input));

        final ToDouble<Float> toSecond = PowUtil.floatPowInt(toFloat, 2);
        assertEquals(input * input, toSecond.applyAsDouble(input));

        final ToDouble<Float> toThird = PowUtil.floatPowInt(toFloat, 3);
        assertEquals(input * input * input, toThird.applyAsDouble(input));

        final ToDouble<Float> reciprocal = PowUtil.floatPowInt(toFloat, -1);
        assertEquals(1d / input, reciprocal.applyAsDouble(input), 1e-8);

        IntStream.range(4, 10).forEach(n -> {
            final ToDouble<Float> toNth = PowUtil.floatPowInt(toFloat, n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });

        final ToDouble<Float> toZeroth_ = PowUtil.floatPowInt(toFloat, i -> 0);
        assertEquals(1, toZeroth_.applyAsDouble(input));

        final ToDouble<Float> toFirst_ = PowUtil.floatPowInt(toFloat, i -> 1);
        assertEquals(input, toFirst_.applyAsDouble(input));

        final ToDouble<Float> toSecond_ = PowUtil.floatPowInt(toFloat, i -> 2);
        assertEquals(input * input, toSecond_.applyAsDouble(input));

        final ToDouble<Float> toThird_ = PowUtil.floatPowInt(toFloat, i -> 3);
        assertEquals(input * input * input, toThird_.applyAsDouble(input));

        final ToDouble<Float> reciprocal_ = PowUtil.floatPowInt(toFloat, i -> -1);
        assertEquals(1d / input, reciprocal_.applyAsDouble(input), 1e-8);

        IntStream.range(4, 10).forEach(n -> {
            final ToDouble<Float> toNth = PowUtil.floatPowInt(toFloat, i -> n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });
    }

    @ParameterizedTest
    @ValueSource(floats = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void floatPowDouble(float input) {
        final ToFloat<Float> toFloat = i -> i;

        IntStream.range(1, 10).forEach(n -> {
            final ToDouble<Float> toNth = PowUtil.floatPowDouble(toFloat, n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });

        IntStream.range(1, 10).forEach(n -> {
            final ToDouble<Float> toNth = PowUtil.floatPowDouble(toFloat, i -> n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void doublePowInt(double input) {
        final ToDouble<Double> toDouble = l -> l;

        final ToDouble<Double> toZeroth = PowUtil.doublePowInt(toDouble, 0);
        assertEquals(1, toZeroth.applyAsDouble(input));

        final ToDouble<Double> toFirst = PowUtil.doublePowInt(toDouble, 1);
        assertEquals(input, toFirst.applyAsDouble(input));

        final ToDouble<Double> toSecond = PowUtil.doublePowInt(toDouble, 2);
        assertEquals(input * input, toSecond.applyAsDouble(input));

        final ToDouble<Double> toThird = PowUtil.doublePowInt(toDouble, 3);
        assertEquals(input * input * input, toThird.applyAsDouble(input));

        final ToDouble<Double> reciprocal = PowUtil.doublePowInt(toDouble, -1);
        assertEquals(1d / input, reciprocal.applyAsDouble(input));

        IntStream.range(4, 10).forEach(n -> {
            final ToDouble<Double> toNth = PowUtil.doublePowInt(toDouble, n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });

        final ToDouble<Double> toZeroth_ = PowUtil.doublePowInt(toDouble, i -> 0);
        assertEquals(1, toZeroth_.applyAsDouble(input));

        final ToDouble<Double> toFirst_ = PowUtil.doublePowInt(toDouble, i -> 1);
        assertEquals(input, toFirst_.applyAsDouble(input));

        final ToDouble<Double> toSecond_ = PowUtil.doublePowInt(toDouble, i -> 2);
        assertEquals(input * input, toSecond_.applyAsDouble(input));

        final ToDouble<Double> toThird_ = PowUtil.doublePowInt(toDouble, i -> 3);
        assertEquals(input * input * input, toThird_.applyAsDouble(input));

        final ToDouble<Double> reciprocal_ = PowUtil.doublePowInt(toDouble, i -> -1);
        assertEquals(1d / input, reciprocal_.applyAsDouble(input));

        IntStream.range(4, 10).forEach(n -> {
            final ToDouble<Double> toNth = PowUtil.doublePowInt(toDouble, i -> n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void doublePowDouble(double input) {
        final ToDouble<Double> toDouble = i -> i;

        IntStream.range(1, 10).forEach(n -> {
            final ToDouble<Double> toNth = PowUtil.doublePowDouble(toDouble, n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });

        IntStream.range(1, 10).forEach(n -> {
            final ToDouble<Double> toNth = PowUtil.doublePowDouble(toDouble, i -> n);
            assertEquals(Math.pow(input, n), toNth.applyAsDouble(input));
        });
    }

    @Test
    void intPower() {
        final DummyIntPower intPower = new DummyIntPower(i -> i, 1);

        assertNotNull(intPower.first());
        assertNotNull(intPower.second());
        assertEquals(Operator.POW, intPower.operator());
        assertNotEquals(0, intPower.hashCode());

        final DummyIntPower copy = intPower;

        assertTrue(intPower.equals(copy));
        assertFalse(intPower.equals(null));
        assertFalse(intPower.equals(1));

        final DummyIntPower sameInner = new DummyIntPower(intPower.first(), 2);
        assertFalse(intPower.equals(sameInner));

        final DummyIntPower samePower = new DummyIntPower(i -> 1, intPower.second());
        assertFalse(intPower.equals(samePower));

        final DummyIntPower allDifferent = new DummyIntPower(i -> 1, 2);
        assertFalse(intPower.equals(allDifferent));

        final DummyIntPower allSame = new DummyIntPower(intPower.first(), intPower.second());
        assertTrue(intPower.equals(allSame));
    }

    private static final class DummyIntPower extends IntPower<Integer, ToInt<Integer>> {

        DummyIntPower(ToInt<Integer> integerToInt, int power) {
            super(integerToInt, power);
        }

        @Override
        public double applyAsDouble(Integer object) {
            return 0;
        }
    }

    @Test
    void toIntPower() {
        final DummyToIntPower intPower = new DummyToIntPower(i -> i, i -> 1);

        assertNotNull(intPower.first());
        assertNotNull(intPower.second());
        assertEquals(BinaryExpression.Operator.POW, intPower.operator());
        assertNotEquals(0, intPower.hashCode());

        final DummyToIntPower copy = intPower;

        assertTrue(intPower.equals(copy));
        assertFalse(intPower.equals(null));
        assertFalse(intPower.equals(1));

        final DummyToIntPower sameInner = new DummyToIntPower(intPower.first(), i -> 2);
        assertFalse(intPower.equals(sameInner));

        final DummyToIntPower samePower = new DummyToIntPower(i -> 1, intPower.second());
        assertFalse(intPower.equals(samePower));

        final DummyToIntPower allDifferent = new DummyToIntPower(i -> 1, i -> 2);
        assertFalse(intPower.equals(allDifferent));

        final DummyToIntPower allSame = new DummyToIntPower(intPower.first(), intPower.second());
        assertTrue(intPower.equals(allSame));
    }

    private static final class DummyToIntPower extends ToIntPower<Integer, ToInt<Integer>> {
        DummyToIntPower(ToInt<Integer> integerToInt, ToInt<Integer> power) {
            super(integerToInt, power);
        }

        @Override
        public double applyAsDouble(Integer object) {
            return 0;
        }
    }

    @Test
    void doublePower() {
        final DummyDoublePower doublePower = new DummyDoublePower(i -> i, 1);

        assertNotNull(doublePower.first());
        assertNotNull(doublePower.second());
        assertEquals(Operator.POW, doublePower.operator());
        assertNotEquals(0, doublePower.hashCode());

        final DummyDoublePower copy = doublePower;

        assertTrue(doublePower.equals(copy));
        assertFalse(doublePower.equals(null));
        assertFalse(doublePower.equals(1));

        final DummyDoublePower sameInner = new DummyDoublePower(doublePower.first(), 2);
        assertFalse(doublePower.equals(sameInner));

        final DummyDoublePower samePower = new DummyDoublePower(i -> 1, doublePower.second());
        assertFalse(doublePower.equals(samePower));

        final DummyDoublePower allDifferent = new DummyDoublePower(i -> 1, 2);
        assertFalse(doublePower.equals(allDifferent));

        final DummyDoublePower allSame = new DummyDoublePower(doublePower.first(), doublePower.second());
        assertTrue(doublePower.equals(allSame));
    }

    private static final class DummyDoublePower extends DoublePower<Integer, ToInt<Integer>> {

        DummyDoublePower(ToInt<Integer> integerToInt, double power) {
            super(integerToInt, power);
        }

        @Override
        public double applyAsDouble(Integer object) {
            return 0;
        }
    }

    @Test
    void toDoublePower() {
        final DummyToDoublePower doublePower = new DummyToDoublePower(i -> i, i -> 1);

        assertNotNull(doublePower.first());
        assertNotNull(doublePower.second());
        assertEquals(BinaryExpression.Operator.POW, doublePower.operator());
        assertNotEquals(0, doublePower.hashCode());

        final DummyToDoublePower copy = doublePower;

        assertTrue(doublePower.equals(copy));
        assertFalse(doublePower.equals(null));
        assertFalse(doublePower.equals(1));

        final DummyToDoublePower sameInner = new DummyToDoublePower(doublePower.first(), i -> 2);
        assertFalse(doublePower.equals(sameInner));

        final DummyToDoublePower samePower = new DummyToDoublePower(i -> 1, doublePower.second());
        assertFalse(doublePower.equals(samePower));

        final DummyToDoublePower allDifferent = new DummyToDoublePower(i -> 1, i -> 2);
        assertFalse(doublePower.equals(allDifferent));

        final DummyToDoublePower allSame = new DummyToDoublePower(doublePower.first(), doublePower.second());
        assertTrue(doublePower.equals(allSame));
    }

    private static final class DummyToDoublePower extends ToDoublePower<Integer, ToInt<Integer>> {

        DummyToDoublePower(ToInt<Integer> integerToInt, ToDouble<Integer> power) {
            super(integerToInt, power);
        }

        @Override
        public double applyAsDouble(Integer object) {
            return 0;
        }
    }
}
