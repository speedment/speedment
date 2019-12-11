package com.speedment.common.function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class ShortUnaryOperatorTest {

    private static final ShortUnaryOperator PLUS_ONE = s -> (short) (s + (short) 1);
    private static final ShortUnaryOperator TIMES_TWO = s -> (short) (s * (short) 2);
    private static final Short ZERO = (short) 0;

    @Test
    void applyAsShort() {
        assertEquals(1, PLUS_ONE.applyAsShort(ZERO));
    }

    @Test
    void compose() {
        assertEquals(2, TIMES_TWO.compose(PLUS_ONE).applyAsShort(ZERO));
    }

    @Test
    void andThen() {
        assertEquals(2, PLUS_ONE.andThen(TIMES_TWO).applyAsShort(ZERO));
    }
}