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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.ToEnumNullable;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.expression.orelse.ToBigDecimalOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToBooleanOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToByteOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToCharOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToDoubleOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToEnumOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToFloatOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToLongOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToShortOrThrow;
import com.speedment.runtime.compute.expression.orelse.ToStringOrThrow;
import com.speedment.runtime.compute.internal.expression.OrElseThrowUtil.AbstractNonNullable;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

final class OrElseThrowUtilTest {

    @Test
    void doubleOrElseThrow() {
        final ToDoubleOrThrow<Double> toDoubleOrThrow = OrElseThrowUtil.doubleOrElseThrow(d -> d);

        assertEquals(1, toDoubleOrThrow.applyAsDouble(1d));
        assertThrows(NullPointerException.class, () -> toDoubleOrThrow.applyAsDouble(null));
    }

    @Test
    void floatOrElseThrow() {
        final ToFloatOrThrow<Float> toFloatOrThrow = OrElseThrowUtil.floatOrElseThrow(d -> d);

        assertEquals(1, toFloatOrThrow.applyAsFloat(1f));
        assertThrows(NullPointerException.class, () -> toFloatOrThrow.applyAsFloat(null));
    }

    @Test
    void longOrElseThrow() {
        final ToLongOrThrow<Long> toLongOrThrow = OrElseThrowUtil.longOrElseThrow(d -> d);

        assertEquals(1, toLongOrThrow.applyAsLong(1L));
        assertThrows(NullPointerException.class, () -> toLongOrThrow.applyAsLong(null));
    }

    @Test
    void shortOrElseThrow() {
        final ToShortOrThrow<Short> toShortOrThrow = OrElseThrowUtil.shortOrElseThrow(d -> d);

        assertEquals(1, toShortOrThrow.applyAsShort((short) 1));
        assertThrows(NullPointerException.class, () -> toShortOrThrow.applyAsShort(null));
    }

    @Test
    void byteOrElseThrow() {
        final ToByteOrThrow<Byte> toByteOrThrow = OrElseThrowUtil.byteOrElseThrow(d -> d);

        assertEquals(1, toByteOrThrow.applyAsByte((byte) 1));
        assertThrows(NullPointerException.class, () -> toByteOrThrow.applyAsByte(null));
    }

    @Test
    void charOrElseThrow() {
        final ToCharOrThrow<Character> toCharOrThrow = OrElseThrowUtil.charOrElseThrow(d -> d);

        assertEquals(1, toCharOrThrow.applyAsChar((char) 1));
        assertThrows(NullPointerException.class, () -> toCharOrThrow.applyAsChar(null));
    }

    @Test
    void booleanOrElseThrow() {
        final ToBooleanOrThrow<Boolean> toBooleanOrThrow = OrElseThrowUtil.booleanOrElseThrow(d -> d);

        assertTrue(toBooleanOrThrow.applyAsBoolean(true));
        assertThrows(NullPointerException.class, () -> toBooleanOrThrow.applyAsBoolean(null));
    }

    @Test
    void stringOrElseThrow() {
        final ToStringOrThrow<String> toStringOrThrow = OrElseThrowUtil.stringOrElseThrow(d -> d);

        assertEquals("string", toStringOrThrow.apply("string"));
        assertThrows(NullPointerException.class, () -> toStringOrThrow.apply(null));
    }

    @Test
    void bigDecimalOrElseThrow() {
        final ToBigDecimalOrThrow<BigDecimal> toBigDecimalOrThrow = OrElseThrowUtil.bigDecimalOrElseThrow(d -> d);

        assertEquals(BigDecimal.valueOf(1), toBigDecimalOrThrow.apply(BigDecimal.valueOf(1)));
        assertThrows(NullPointerException.class, () -> toBigDecimalOrThrow.apply(null));
    }

    @Test
    void enumOrElseThrow() {
        final ToEnumOrThrow<String, TestEnum> toEnumOrThrow = OrElseThrowUtil.enumOrElseThrow(
                ToEnumNullable.of(TestEnum.class, string -> string == null ?
                        null : TestEnum.valueOf(string)));

        assertEquals(TestEnum.A, toEnumOrThrow.apply("A"));
        assertThrows(NullPointerException.class, () -> toEnumOrThrow.apply(null));
    }

    @Test
    void abstractNonNullable() {
        final DummyNonNullable instance = new DummyNonNullable(d -> d);

        assertNotNull(instance.innerNullable());
        assertNotEquals(0, instance.hashCode());

        final DummyNonNullable copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final DummyNonNullable sameInner = new DummyNonNullable(instance.innerNullable());

        assertTrue(instance.equals(sameInner));

        final DummyNonNullable differentInner = new DummyNonNullable(d -> d + 1);

        assertFalse(instance.equals(differentInner));
    }

    private static final class DummyNonNullable extends AbstractNonNullable<Double, ToDoubleNullable<Double>> {

        DummyNonNullable(ToDoubleNullable<Double> doubleToDouble) {
            super(doubleToDouble);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }
    
    private enum TestEnum {
        A
    }

}
