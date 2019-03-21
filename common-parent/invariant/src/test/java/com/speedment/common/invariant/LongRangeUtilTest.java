/**
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
    void testRequirePositive() {
        testHelper(IS_POSITIVE, LongRangeUtil::requirePositive);
    }

    @Test
    void testRequireNegative() {
        testHelper(IS_NEGATIVE, LongRangeUtil::requireNegative);
    }

    @Test
    void testRequireZero() {
        testHelper(IS_ZERO, LongRangeUtil::requireZero);
    }

    @Test
    void testRequireNonPositive() {
        testHelper(IS_POSITIVE.negate(), LongRangeUtil::requireNonPositive);
    }

    @Test
    void testRequireNonNegative() {
        testHelper(IS_NEGATIVE.negate(), LongRangeUtil::requireNonNegative);
    }

    @Test
    void testRequireNonZero() {
        testHelper(IS_ZERO.negate(), LongRangeUtil::requireNonZero);
    }

    @Test
    void testRequireEquals() {
        final long otherVal = 3;
        testHelper(l -> l == otherVal, l -> LongRangeUtil.requireEquals(l, otherVal));
    }

    @Test
    void testRequireNotEquals() {
        final long otherVal = 3;
        testHelper(l -> l != otherVal, l -> LongRangeUtil.requireNotEquals(l, otherVal));
    }

    @Test
    void testRequireInRange() {
        final long first = -1;
        final long lastExclusive = 4;
        testHelper(l -> l >= first && l < lastExclusive, l -> LongRangeUtil.requireInRange(l, first, lastExclusive));
    }

    @Test
    void testRequireInRangeClosed() {
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

}
