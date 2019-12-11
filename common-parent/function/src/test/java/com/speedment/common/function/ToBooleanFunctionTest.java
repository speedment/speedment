package com.speedment.common.function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class ToBooleanFunctionTest {

    private static final ToBooleanFunction<Integer> IS_EVEN = i -> i % 2 == 0;

    @Test
    void applyAsBoolean() {
        assertTrue(IS_EVEN.applyAsBoolean(2));
        assertFalse(IS_EVEN.applyAsBoolean(1));
    }

    @Test
    void test() {
        assertTrue(IS_EVEN.test(2));
        assertFalse(IS_EVEN.test(1));
    }
}