package com.speedment.common.function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class BooleanUnaryOperatorTest {

    private static final BooleanUnaryOperator INVERT = b -> !b;
    private static final BooleanUnaryOperator IDENTITY = b -> b;


    @Test
    void applyAsBoolean() {
        assertFalse(INVERT.applyAsBoolean(true));
        assertTrue(INVERT.applyAsBoolean(false));
    }

    @Test
    void compose() {
        assertTrue(INVERT.compose(INVERT).applyAsBoolean(true));
        assertTrue(INVERT.compose(IDENTITY).applyAsBoolean(false));
    }

    @Test
    void andThen() {
        assertTrue(INVERT.compose(INVERT).applyAsBoolean(true));
        assertTrue(INVERT.compose(IDENTITY).applyAsBoolean(false));
    }
}