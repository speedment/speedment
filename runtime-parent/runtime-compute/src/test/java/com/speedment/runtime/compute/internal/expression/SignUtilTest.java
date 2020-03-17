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

import static org.junit.jupiter.api.Assertions.*;

import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.internal.expression.SignUtil.AbstractSign;
import org.junit.jupiter.api.Test;

final class SignUtilTest {

    @Test
    void signFloat() {
        final ToByte<Float> signFloat = SignUtil.signFloat(f -> f);

        assertEquals(-1, signFloat.applyAsByte(-1f));
        assertEquals(0, signFloat.applyAsByte(0f));
        assertEquals(1, signFloat.applyAsByte(1f));
    }

    @Test
    void signLong() {
        final ToByte<Long> signLong = SignUtil.signLong(l -> l);

        assertEquals(-1, signLong.applyAsByte(-1L));
        assertEquals(0, signLong.applyAsByte(0L));
        assertEquals(1, signLong.applyAsByte(1L));
    }

    @Test
    void signInt() {
        final ToByte<Integer> signInt = SignUtil.signInt(i -> i);

        assertEquals(-1, signInt.applyAsByte(-1));
        assertEquals(0, signInt.applyAsByte(0));
        assertEquals(1, signInt.applyAsByte(1));
    }

    @Test
    void signShort() {
        final ToByte<Short> signShort = SignUtil.signShort(s -> s);

        assertEquals(-1, signShort.applyAsByte((short) -1));
        assertEquals(0, signShort.applyAsByte((short) 0));
        assertEquals(1, signShort.applyAsByte((short) 1));
    }

    @Test
    void signByte() {
        final ToByte<Byte> signByte = SignUtil.signByte(b -> b);

        assertEquals(-1, signByte.applyAsByte((byte) -1));
        assertEquals(0, signByte.applyAsByte((byte) 0));
        assertEquals(1, signByte.applyAsByte((byte) 1));
    }

    @Test
    void abstractSign() {
        final DummySign instance = new DummySign(i -> i);

        assertNotNull(instance.inner());
        assertNotEquals(0, instance.hashCode());

        final DummySign copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final DummySign sameInner = new DummySign(instance.inner());

        assertTrue(instance.equals(sameInner));

        final DummySign differentInner = new DummySign(i -> i + 1);

        assertFalse(instance.equals(differentInner));
    }

    private static final class DummySign extends AbstractSign<Integer, ToInt<Integer>> {

        DummySign(ToInt<Integer> integerToByte) {
            super(integerToByte);
        }

        @Override
        public byte applyAsByte(Integer object) {
            return 0;
        }
    }
}
