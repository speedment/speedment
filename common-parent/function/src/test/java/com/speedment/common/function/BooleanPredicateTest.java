package com.speedment.common.function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class BooleanPredicateTest {

    private static final BooleanPredicate INVERT = b -> !b;
    private static final BooleanPredicate IDENTITY = b -> b;

    @Test
    void test() {
        assertFalse(INVERT.test(true));
        assertTrue(INVERT.test(false));
    }

    @Test
    void and() {
        assertFalse(INVERT.and(IDENTITY).test(true));
        assertFalse(INVERT.and(IDENTITY).test(false));
    }

    @Test
    void negate() {
        assertTrue(INVERT.negate().test(true));
        assertFalse(INVERT.negate().test(false));
    }

    @Test
    void or() {
        assertTrue(INVERT.or(IDENTITY).test(true));
        assertTrue(INVERT.or(IDENTITY).test(false));
    }
}