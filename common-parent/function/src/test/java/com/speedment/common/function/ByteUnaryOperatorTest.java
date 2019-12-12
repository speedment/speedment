package com.speedment.common.function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class ByteUnaryOperatorTest {

    private static final ByteUnaryOperator PLUS_ONE = b -> (byte) (b + (byte) 1);
    private static final ByteUnaryOperator TIMES_TWO = b -> (byte) (b * (byte) 2);

    @Test
    void applyAsByte() {
        assertEquals(1, PLUS_ONE.applyAsByte((byte) 0));
    }

    @Test
    void compose() {
        assertEquals(2, TIMES_TWO.compose(PLUS_ONE).applyAsByte((byte) 0));
    }

    @Test
    void andThen() {
        assertEquals(2, PLUS_ONE.andThen(TIMES_TWO).applyAsByte((byte) 0));
    }
}