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
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.compute.ToEnumNullable;
import com.speedment.runtime.compute.internal.expression.OrElseUtil.ToBigDecimalOrElseImpl;
import com.speedment.runtime.compute.internal.expression.OrElseUtil.ToBooleanOrElseImpl;
import com.speedment.runtime.compute.internal.expression.OrElseUtil.ToByteOrElseImpl;
import com.speedment.runtime.compute.internal.expression.OrElseUtil.ToCharOrElseImpl;
import com.speedment.runtime.compute.internal.expression.OrElseUtil.ToDoubleOrElseImpl;
import com.speedment.runtime.compute.internal.expression.OrElseUtil.ToEnumOrElseImpl;
import com.speedment.runtime.compute.internal.expression.OrElseUtil.ToFloatOrElseImpl;
import com.speedment.runtime.compute.internal.expression.OrElseUtil.ToIntOrElseImpl;
import com.speedment.runtime.compute.internal.expression.OrElseUtil.ToLongOrElseImpl;
import com.speedment.runtime.compute.internal.expression.OrElseUtil.ToShortOrElseImpl;
import com.speedment.runtime.compute.internal.expression.OrElseUtil.ToStringOrElseImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

final class OrElseUtilTest {

    @Test
    void toDoubleOrElse() {
        final ToDoubleOrElseImpl<Double> instance = new ToDoubleOrElseImpl<>(d -> d, 1);

        assertEquals(1, instance.defaultValue());
        assertNotEquals(0, instance.hashCode());

        final ToDoubleOrElseImpl<Double> copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToDoubleOrElseImpl<Double> sameInner = new ToDoubleOrElseImpl<>(instance.innerNullable(), 2);
        assertFalse(instance.equals(sameInner));

        final ToDoubleOrElseImpl<Double> sameValue = new ToDoubleOrElseImpl<>(d -> 1d, instance.defaultValue());
        assertFalse(instance.equals(sameValue));

        final ToDoubleOrElseImpl<Double> allDifferent = new ToDoubleOrElseImpl<>(d -> 1d, 2);
        assertFalse(instance.equals(allDifferent));

        final ToDoubleOrElseImpl<Double> allSame = new ToDoubleOrElseImpl<>(instance.innerNullable(), instance.defaultValue());
        assertTrue(instance.equals(allSame));
    }

    @Test
    void toFloatOrElse() {
        final ToFloatOrElseImpl<Float> instance = new ToFloatOrElseImpl<>(d -> d, 1);

        assertEquals(1, instance.defaultValue());
        assertNotEquals(0, instance.hashCode());

        final ToFloatOrElseImpl<Float> copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToFloatOrElseImpl<Float> sameInner = new ToFloatOrElseImpl<>(instance.innerNullable(), 2);
        assertFalse(instance.equals(sameInner));

        final ToFloatOrElseImpl<Float> sameValue = new ToFloatOrElseImpl<>(d -> 1f, instance.defaultValue());
        assertFalse(instance.equals(sameValue));

        final ToFloatOrElseImpl<Float> allDifferent = new ToFloatOrElseImpl<>(d -> 1f, 2);
        assertFalse(instance.equals(allDifferent));

        final ToFloatOrElseImpl<Float> allSame = new ToFloatOrElseImpl<>(instance.innerNullable(), instance.defaultValue());
        assertTrue(instance.equals(allSame));
    }

    @Test
    void toLongOrElse() {
        final ToLongOrElseImpl<Long> instance = new ToLongOrElseImpl<>(d -> d, 1);

        assertEquals(1, instance.defaultValue());
        assertNotEquals(0, instance.hashCode());

        final ToLongOrElseImpl<Long> copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToLongOrElseImpl<Long> sameInner = new ToLongOrElseImpl<>(instance.innerNullable(), 2);
        assertFalse(instance.equals(sameInner));

        final ToLongOrElseImpl<Long> sameValue = new ToLongOrElseImpl<>(d -> 1L, instance.defaultValue());
        assertFalse(instance.equals(sameValue));

        final ToLongOrElseImpl<Long> allDifferent = new ToLongOrElseImpl<>(d -> 1L, 2);
        assertFalse(instance.equals(allDifferent));

        final ToLongOrElseImpl<Long> allSame = new ToLongOrElseImpl<>(instance.innerNullable(), instance.defaultValue());
        assertTrue(instance.equals(allSame));
    }

    @Test
    void toIntOrElse() {
        final ToIntOrElseImpl<Integer> instance = new ToIntOrElseImpl<>(d -> d, 1);

        assertEquals(1, instance.defaultValue());
        assertNotEquals(0, instance.hashCode());

        final ToIntOrElseImpl<Integer> copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToIntOrElseImpl<Integer> sameInner = new ToIntOrElseImpl<>(instance.innerNullable(), 2);
        assertFalse(instance.equals(sameInner));

        final ToIntOrElseImpl<Integer> sameValue = new ToIntOrElseImpl<>(d -> 1, instance.defaultValue());
        assertFalse(instance.equals(sameValue));

        final ToIntOrElseImpl<Integer> allDifferent = new ToIntOrElseImpl<>(d -> 1, 2);
        assertFalse(instance.equals(allDifferent));

        final ToIntOrElseImpl<Integer> allSame = new ToIntOrElseImpl<>(instance.innerNullable(), instance.defaultValue());
        assertTrue(instance.equals(allSame));
    }

    @Test
    void toShortOrElse() {
        final ToShortOrElseImpl<Short> instance = new ToShortOrElseImpl<>(d -> (short) d, (short) 1);

        assertEquals(1, instance.defaultValue());
        assertNotEquals(0, instance.hashCode());

        final ToShortOrElseImpl<Short> copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToShortOrElseImpl<Short> sameInner = new ToShortOrElseImpl<>(instance.innerNullable(), (short) 2);
        assertFalse(instance.equals(sameInner));

        final ToShortOrElseImpl<Short> sameValue = new ToShortOrElseImpl<>(d -> (short) 1, instance.defaultValue());
        assertFalse(instance.equals(sameValue));

        final ToShortOrElseImpl<Short> allDifferent = new ToShortOrElseImpl<>(d -> (short) 1, (short) 2);
        assertFalse(instance.equals(allDifferent));

        final ToShortOrElseImpl<Short> allSame = new ToShortOrElseImpl<>(instance.innerNullable(), instance.defaultValue());
        assertTrue(instance.equals(allSame));
    }

    @Test
    void toByteOrElse() {
        final ToByteOrElseImpl<Byte> instance = new ToByteOrElseImpl<>(d -> d, (byte) 1);

        assertEquals(1, instance.defaultValue());
        assertNotEquals(0, instance.hashCode());

        final ToByteOrElseImpl<Byte> copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToByteOrElseImpl<Byte> sameInner = new ToByteOrElseImpl<>(instance.innerNullable(), (byte) 2);
        assertFalse(instance.equals(sameInner));

        final ToByteOrElseImpl<Byte> sameValue = new ToByteOrElseImpl<>(d -> (byte) 1, instance.defaultValue());
        assertFalse(instance.equals(sameValue));

        final ToByteOrElseImpl<Byte> allDifferent = new ToByteOrElseImpl<>(d -> (byte) 1, (byte) 2);
        assertFalse(instance.equals(allDifferent));

        final ToByteOrElseImpl<Byte> allSame = new ToByteOrElseImpl<>(instance.innerNullable(), instance.defaultValue());
        assertTrue(instance.equals(allSame));
    }

    @Test
    void toCharOrElse() {
        final ToCharOrElseImpl<Character> instance = new ToCharOrElseImpl<>(d -> d, (char) 1);

        assertEquals(1, instance.defaultValue());
        assertNotEquals(0, instance.hashCode());

        final ToCharOrElseImpl<Character> copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToCharOrElseImpl<Character> sameInner = new ToCharOrElseImpl<>(instance.innerNullable(), (char) 2);
        assertFalse(instance.equals(sameInner));

        final ToCharOrElseImpl<Character> sameValue = new ToCharOrElseImpl<>(d -> (char) 1, instance.defaultValue());
        assertFalse(instance.equals(sameValue));

        final ToCharOrElseImpl<Character> allDifferent = new ToCharOrElseImpl<>(d -> (char) 1, (char) 2);
        assertFalse(instance.equals(allDifferent));

        final ToCharOrElseImpl<Character> allSame = new ToCharOrElseImpl<>(instance.innerNullable(), instance.defaultValue());
        assertTrue(instance.equals(allSame));
    }

    @Test
    void toBooleanOrElse() {
        final ToBooleanOrElseImpl<Boolean> instance = new ToBooleanOrElseImpl<>(d -> d, true);

        assertEquals(true, instance.defaultValue());
        assertNotEquals(0, instance.hashCode());

        final ToBooleanOrElseImpl<Boolean> copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToBooleanOrElseImpl<Boolean> sameInner = new ToBooleanOrElseImpl<>(instance.innerNullable(), false);
        assertFalse(instance.equals(sameInner));

        final ToBooleanOrElseImpl<Boolean> sameValue = new ToBooleanOrElseImpl<>(d -> true, instance.defaultValue());
        assertFalse(instance.equals(sameValue));

        final ToBooleanOrElseImpl<Boolean> allDifferent = new ToBooleanOrElseImpl<>(d -> true, false);
        assertFalse(instance.equals(allDifferent));

        final ToBooleanOrElseImpl<Boolean> allSame = new ToBooleanOrElseImpl<>(instance.innerNullable(), instance.defaultValue());
        assertTrue(instance.equals(allSame));
    }

    @Test
    void toStringOrElse() {
        final ToStringOrElseImpl<String> instance = new ToStringOrElseImpl<>(d -> d, "Default");

        assertEquals("Default", instance.defaultValue());
        assertNotEquals(0, instance.hashCode());

        final ToStringOrElseImpl<String> copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToStringOrElseImpl<String> sameInner = new ToStringOrElseImpl<>(instance.innerNullable(), "Different Default");
        assertFalse(instance.equals(sameInner));

        final ToStringOrElseImpl<String> sameValue = new ToStringOrElseImpl<>(d -> "String", instance.defaultValue());
        assertFalse(instance.equals(sameValue));

        final ToStringOrElseImpl<String> allDifferent = new ToStringOrElseImpl<>(d -> "String", "Different Default");
        assertFalse(instance.equals(allDifferent));

        final ToStringOrElseImpl<String> allSame = new ToStringOrElseImpl<>(instance.innerNullable(), instance.defaultValue());
        assertTrue(instance.equals(allSame));
    }

    @Test
    void toEnumOrElse() {
        final ToEnumOrElseImpl<String, TestEnum> instance = new ToEnumOrElseImpl<>(ToEnumNullable.of(TestEnum.class,
                string -> string == null ? null : TestEnum.valueOf(string)), TestEnum.DEFAULT);

        assertEquals(TestEnum.DEFAULT, instance.defaultValue());
        assertNotEquals(0, instance.hashCode());
        assertEquals(TestEnum.class, instance.enumClass());
        assertEquals(TestEnum.DIFFERENT_DEFAULT, instance.apply("DIFFERENT_DEFAULT"));
        assertEquals(TestEnum.DEFAULT, instance.apply(null));

        final ToEnumOrElseImpl<String, TestEnum> copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToEnumOrElseImpl<String, TestEnum> sameInner = new ToEnumOrElseImpl<>(instance.innerNullable(), TestEnum.DIFFERENT_DEFAULT);
        assertFalse(instance.equals(sameInner));

        final ToEnumOrElseImpl<String, TestEnum> sameValue = new ToEnumOrElseImpl<>(ToEnumNullable.of(TestEnum.class,
                s -> TestEnum.DEFAULT), instance.defaultValue());
        assertFalse(instance.equals(sameValue));

        final ToEnumOrElseImpl<String, TestEnum> allDifferent = new ToEnumOrElseImpl<>(ToEnumNullable.of(TestEnum.class,
                s -> TestEnum.DEFAULT), TestEnum.DIFFERENT_DEFAULT);
        assertFalse(instance.equals(allDifferent));

        final ToEnumOrElseImpl<String, TestEnum> allSame = new ToEnumOrElseImpl<>(instance.innerNullable(), instance.defaultValue());
        assertTrue(instance.equals(allSame));
    }

    @Test
    void toBigDecimalOrElse() {
        final ToBigDecimalOrElseImpl<BigDecimal> instance = new ToBigDecimalOrElseImpl<>(d -> d, BigDecimal.ONE);

        assertEquals(BigDecimal.ONE, instance.defaultValue());
        assertNotEquals(0, instance.hashCode());

        final ToBigDecimalOrElseImpl<BigDecimal> copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToBigDecimalOrElseImpl<BigDecimal> sameInner = new ToBigDecimalOrElseImpl<>(instance.innerNullable(), BigDecimal.TEN);
        assertFalse(instance.equals(sameInner));

        final ToBigDecimalOrElseImpl<BigDecimal> sameValue = new ToBigDecimalOrElseImpl<>(d -> BigDecimal.ONE, instance.defaultValue());
        assertFalse(instance.equals(sameValue));

        final ToBigDecimalOrElseImpl<BigDecimal> allDifferent = new ToBigDecimalOrElseImpl<>(d -> BigDecimal.ONE, BigDecimal.TEN);
        assertFalse(instance.equals(allDifferent));

        final ToBigDecimalOrElseImpl<BigDecimal> allSame = new ToBigDecimalOrElseImpl<>(instance.innerNullable(), instance.defaultValue());
        assertTrue(instance.equals(allSame));
    }

    private enum TestEnum {
        DEFAULT,
        DIFFERENT_DEFAULT
    }
}
