package com.speedment.common.function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FloatUnaryOperatorTest {

    private static final FloatUnaryOperator PLUS_ONE = f -> f +  1.0f;
    private static final FloatUnaryOperator TIMES_TWO = f -> f * 2.0f;


    @Test
    void applyAsFloat() {
        assertEquals(1, PLUS_ONE.applyAsFloat(0f));
    }

    @Test
    void compose() {
        assertEquals(2, TIMES_TWO.compose(PLUS_ONE).applyAsFloat(0f));
    }

    @Test
    void andThen() {
        assertEquals(2, PLUS_ONE.andThen(TIMES_TWO).applyAsFloat(0f));
    }
}