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
package com.speedment.common.invariant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author Per Minborg
 */
@Execution(ExecutionMode.CONCURRENT)
final class LongRangeUtilTest {

    private static final LongPredicate IS_POSITIVE = l -> l > 0;
    private static final LongPredicate IS_NEGATIVE = l -> l < 0;
    private static final LongPredicate IS_ZERO = l -> l == 0;

    @Test
    void RequirePositive() {
        testHelper(IS_POSITIVE, LongRangeUtil::requirePositive);
    }

    @Test
    void RequireNegative() {
        testHelper(IS_NEGATIVE, LongRangeUtil::requireNegative);
    }

    @Test
    void RequireZero() {
        testHelper(IS_ZERO, LongRangeUtil::requireZero);
    }

    @Test
    void RequireNonPositive() {
        testHelper(IS_POSITIVE.negate(), LongRangeUtil::requireNonPositive);
    }

    @Test
    void RequireNonNegative() {
        testHelper(IS_NEGATIVE.negate(), LongRangeUtil::requireNonNegative);
    }

    @Test
    void RequireNonZero() {
        testHelper(IS_ZERO.negate(), LongRangeUtil::requireNonZero);
    }

    @Test
    void RequireEquals() {
        final long otherVal = 3;
        testHelper(l -> l == otherVal, l -> LongRangeUtil.requireEquals(l, otherVal));
    }

    @Test
    void RequireNotEquals() {
        final long otherVal = 3;
        testHelper(l -> l != otherVal, l -> LongRangeUtil.requireNotEquals(l, otherVal));
    }

    @Test
    void RequireInRange() {
        final long first = -1;
        final long lastExclusive = 4;
        testHelper(l -> l >= first && l < lastExclusive, l -> LongRangeUtil.requireInRange(l, first, lastExclusive));
    }

    @Test
    void RequireInRangeClosed() {
        final long first = -1;
        final long lastInclusive = 4;
        testHelper(l -> l >= first && l <= lastInclusive, l -> LongRangeUtil.requireInRangeClosed(l, first, lastInclusive));
    }

    private void testHelper(LongPredicate predicate, LongUnaryOperator validator) {
        LongStream.range(-257, 257).forEach(l -> {
            if (predicate.test(l)) {
                long expected = validator.applyAsLong(l);
                assertEquals(l, expected);
            } else {
                try {
                    long expected = validator.applyAsLong(l);
                } catch (IllegalArgumentException e) {
                    // Ignore
                }
            }
        });
    }
    @Test
    void testRequirePositive() {
        assertEquals(1, LongRangeUtil.requirePositive(1, RuntimeException::new));
    }
    @Test
    void testRequirePositive2() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requirePositive(-1, RuntimeException::new));
    }
    @Test
    void testRequirePositive3() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requirePositive(0, RuntimeException::new));
    }

    @Test
    void testRequireNegative() {
        assertEquals(-1, LongRangeUtil.requireNegative(-1, RuntimeException::new));
    }
    @Test
    void testRequireNegative2() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireNegative(1, RuntimeException::new));
    }
    @Test
    void testRequireNegative3() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireNegative(0, RuntimeException::new));
    }

    @Test
    void testRequireZero() {
        assertEquals(0, LongRangeUtil.requireZero(0, RuntimeException::new));
    }
    @Test
    void testRequireZero2() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireZero(1, RuntimeException::new));
    }
    @Test
    void testRequireZero3() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireZero(-1, RuntimeException::new));
    }
    @Test
    void testRequireNonPositive() {
        assertEquals(-1.0, LongRangeUtil.requireNonPositive(-1, RuntimeException::new));
    }
    @Test
    void testRequireNonPositive2() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireNonPositive(1, RuntimeException::new));
    }
    @Test
    void testRequireNonPositive3() {
        assertEquals(0.0, LongRangeUtil.requireNonPositive(0, RuntimeException::new));
    }

    @Test
    void testRequireNonNegative() {
        assertEquals(1, LongRangeUtil.requireNonNegative(1, RuntimeException::new));
    }
    @Test
    void testRequireNonNegative2() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireNonNegative(-1, RuntimeException::new));
    }
    @Test
    void testRequireNonNegative3() {
        assertEquals(0.0, LongRangeUtil.requireNonNegative(0, RuntimeException::new));
    }

    @Test
    void testRequireNonZero() {
        assertEquals(1, LongRangeUtil.requireNonZero(1, RuntimeException::new));
    }
    @Test
    void testRequireNonZero2() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireNonZero(0, RuntimeException::new));
    }
    @Test
    void testRequireNonZero3() {
        assertEquals(-1, LongRangeUtil.requireNonZero(-1, RuntimeException::new));
    }

    @Test
    void testRequireEquals() {
        assertEquals(0, LongRangeUtil.requireEquals(0, 0, RuntimeException::new));
    }
    @Test
    void testRequireEquals2() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireEquals(0,-1, RuntimeException::new));
    }
    @Test
    void testRequireEquals3() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireEquals(0,1, RuntimeException::new));
    }
    @Test
    void testRequireEquals4() {
        assertEquals(1.0, LongRangeUtil.requireEquals(1, 1, RuntimeException::new));
    }
    @Test
    void testRequireEquals5() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireEquals(1,0, RuntimeException::new));
    }
    @Test
    void testRequireEquals6() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireEquals(1,-1, RuntimeException::new));
    }
    @Test
    void testRequireEquals7() {
        assertEquals(-1.0, LongRangeUtil.requireEquals(-1, -1, RuntimeException::new));
    }
    @Test
    void testRequireEquals8() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireEquals(-1,0, RuntimeException::new));
    }
    @Test
    void testRequireEquals9() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireEquals(-1,1, RuntimeException::new));
    }

    @Test
    void testRestRequireNotEquals() {
        assertEquals(0.0, LongRangeUtil.requireNotEquals(0, -1, RuntimeException::new));
    }
    @Test
    void testRestRequireNotEquals2() {
        assertEquals(0.0, LongRangeUtil.requireNotEquals(0, 1, RuntimeException::new));
    }
    @Test
    void testRestRequireNotEquals3() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireNotEquals(0,0, RuntimeException::new));
    }
    @Test
    void testRestRequireNotEquals4() {
        assertEquals(1.0, LongRangeUtil.requireNotEquals(1, 0, RuntimeException::new));
    }
    @Test
    void testRestRequireNotEquals5() {
        assertEquals(1.0, LongRangeUtil.requireNotEquals(1, -1, RuntimeException::new));
    }
    @Test
    void testRestRequireNotEquals6() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireNotEquals(1,1, RuntimeException::new));
    }
    @Test
    void testRestRequireNotEquals7() {
        assertEquals(-1.0, LongRangeUtil.requireNotEquals(-1, 0, RuntimeException::new));
    }
    @Test
    void testRestRequireNotEquals8() {
        assertEquals(-1.0, LongRangeUtil.requireNotEquals(-1, 1, RuntimeException::new));
    }
    @Test
    void testRestRequireNotEquals9() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireNotEquals(-1,-1, RuntimeException::new));
    }

    @Test
    void testRequireInRange() {
        assertEquals(0.0, LongRangeUtil.requireInRange(0, -1,1, RuntimeException::new));
    }
    @Test
    void testRequireInRange2() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireInRange(-1,0,1, RuntimeException::new));
    }
    @Test
    void testRequireInRange3() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireInRange(1,-1,0, RuntimeException::new));
    }
    @Test
    void testRequireInRange4() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireInRange(0,-1,0, RuntimeException::new));
    }

    @Test
    void testRequireInRangeClosed() {
        assertEquals(0.0, LongRangeUtil.requireInRangeClosed(0, -1,1, RuntimeException::new));
    }
    @Test
    void testRequireInRangeClosed2() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireInRangeClosed(-1,0,1, RuntimeException::new));
    }
    @Test
    void testRequireInRangeClosed3() {
        assertThrows(RuntimeException.class, () -> LongRangeUtil.requireInRangeClosed(1,-1,0, RuntimeException::new));
    }
    @Test
    void testRequireInRangeClosed4() {
        assertEquals(0.0, LongRangeUtil.requireInRangeClosed(0, -1,0, RuntimeException::new));
    }
}
