/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.common.invariant;

import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class LongRangeUtilTest {

    private static final Predicate<Long> IS_POSITIVE = l -> l > 0;
    private static final Predicate<Long> IS_NEGATIVE = l -> l < 0;
    private static final Predicate<Long> IS_ZERO = l -> l == 0;

    @Test
    public void testRequirePositive() {
        System.out.println("requirePositive");
        testHelper(IS_POSITIVE, LongRangeUtil::requirePositive);
    }

    @Test
    public void testRequireNegative() {
        System.out.println("requireNegative");
        testHelper(IS_NEGATIVE, LongRangeUtil::requireNegative);
    }

    @Test
    public void testRequireZero() {
        System.out.println("requireZero");
        testHelper(IS_ZERO, LongRangeUtil::requireZero);
    }

    @Test
    public void testRequireNonPositive() {
        System.out.println("requireNonPositive");
        testHelper(IS_POSITIVE.negate(), LongRangeUtil::requireNonPositive);
    }

    @Test
    public void testRequireNonNegative() {
        System.out.println("requireNonNegative");
        testHelper(IS_NEGATIVE.negate(), LongRangeUtil::requireNonNegative);
    }

    @Test
    public void testRequireNonZero() {
        System.out.println("requireNonZero");
        testHelper(IS_ZERO.negate(), LongRangeUtil::requireNonZero);
    }

    @Test
    public void testRequireInRange() {
        System.out.println("requireInRange");
        final long first = -1;
        final long lastExclusive = 4;
        testHelper(l -> l >= first && l < lastExclusive, l -> LongRangeUtil.requireInRange(l, first, lastExclusive));
    }

    @Test
    public void testRequireInRangeClosed() {
        System.out.println("requireInRangeClosed");
        final long first = -1;
        final long lastInclusive = 4;
        testHelper(l -> l >= first && l <= lastInclusive, l -> LongRangeUtil.requireInRangeClosed(l, first, lastInclusive));
    }

    private void testHelper(Predicate<Long> predicate, LongUnaryOperator validator) {
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
