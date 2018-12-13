/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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

import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Per Minborg
 */
final class IntRangeUtilTest {

    private static final IntPredicate IS_POSITIVE = l -> l > 0;
    private static final IntPredicate IS_NEGATIVE = l -> l < 0;
    private static final IntPredicate IS_ZERO = l -> l == 0;

    @Test
    void testRequirePositive() {
        testHelper(IS_POSITIVE, IntRangeUtil::requirePositive);
    }

    @Test
    void testRequireNegative() {
        testHelper(IS_NEGATIVE, IntRangeUtil::requireNegative);
    }

    @Test
    void testRequireZero() {
        testHelper(IS_ZERO, IntRangeUtil::requireZero);
    }

    @Test
    void testRequireNonPositive() {
        testHelper(IS_POSITIVE.negate(), IntRangeUtil::requireNonPositive);
    }

    @Test
    void testRequireNonNegative() {
        testHelper(IS_NEGATIVE.negate(), IntRangeUtil::requireNonNegative);
    }

    @Test
    void testRequireNonZero() {
        testHelper(IS_ZERO.negate(), IntRangeUtil::requireNonZero);
    }

    @Test
    void testRequireEquals() {
        final int otherVal = 3;
        testHelper(l -> l == otherVal, l -> IntRangeUtil.requireEquals(l, otherVal));
    }

    @Test
    void testRequireNotEquals() {
        final int otherVal = 3;
        testHelper(l -> l != otherVal, l -> IntRangeUtil.requireNotEquals(l, otherVal));
    }

    @Test
    void testRequireInRange() {
        final int first = -1;
        final int lastExclusive = 4;
        testHelper(l -> l >= first && l < lastExclusive, l -> IntRangeUtil.requireInRange(l, first, lastExclusive));
    }

    @Test
    void testRequireInRangeClosed() {
        final int first = -1;
        final int lastInclusive = 4;
        testHelper(l -> l >= first && l <= lastInclusive, l -> IntRangeUtil.requireInRangeClosed(l, first, lastInclusive));
    }

    private void testHelper(IntPredicate predicate, IntUnaryOperator validator) {
        IntStream.range(-257, 257).forEach(l -> {
            if (predicate.test(l)) {
                long expected = validator.applyAsInt(l);
                assertEquals(l, expected);
            } else {
                try {
                    long expected = validator.applyAsInt(l);
                } catch (IllegalArgumentException e) {
                    // Ignore
                }
            }
        });
    }

}
