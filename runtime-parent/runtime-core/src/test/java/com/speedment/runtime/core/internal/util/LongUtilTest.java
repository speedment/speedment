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

package com.speedment.runtime.core.internal.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

final class LongUtilTest {

    @Test
    void cast() {
        assertThrows(NullPointerException.class, () -> LongUtil.cast(1L ,null));
        assertNull(LongUtil.cast(null, Long.class));
        assertNotNull(LongUtil.cast(1L, Long.class));

        assertCast(Byte.MIN_VALUE, Byte.MAX_VALUE, Byte.class);
        assertCast(Short.MIN_VALUE, Short.MAX_VALUE, Short.class);
        assertCast(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.class);
        assertCast((long) Float.MIN_VALUE, (long) Float.MAX_VALUE, Float.class, false);
        assertCast((long) Double.MIN_VALUE, (long) Double.MAX_VALUE, Double.class, false);
        assertCast(Long.MIN_VALUE, Long.MAX_VALUE, BigInteger.class, false);
        assertCast(Long.MIN_VALUE, Long.MAX_VALUE, BigDecimal.class, false);
        assertCast(Integer.MIN_VALUE, Integer.MAX_VALUE, AtomicInteger.class);
        assertCast(Long.MIN_VALUE, Long.MAX_VALUE, AtomicLong.class, false);

        assertCast(1L, LongAdder.class, true);
    }

    private <T extends Number> void assertCast(long minAllowedValue, long maxAllowedValue, Class<T> targetClass) {
        assertCast(minAllowedValue, maxAllowedValue, targetClass, true);
    }

    private <T extends Number> void assertCast(long minAllowedValue, long maxAllowedValue, Class<T> targetClass, boolean rangeCheck) {
        assertCast(1L, targetClass);
        assertCast(minAllowedValue, targetClass);
        assertCast(maxAllowedValue, targetClass);
        if (rangeCheck) {
            assertCast(minAllowedValue - 1, targetClass, true);
            assertCast(maxAllowedValue + 1, targetClass, true);
        }
    }

    private <T extends Number> void assertCast(long value, Class<T> targetClass) {
        assertCast(value, targetClass, false);
    }

    private <T extends Number> void assertCast(long value, Class<T> targetClass, boolean illegal) {
        if (illegal) {
            assertThrows(IllegalArgumentException.class, () -> LongUtil.cast(value, targetClass));
        } else {
            assertNotNull(LongUtil.cast(value, targetClass));
        }
    }
}
