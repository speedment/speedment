package com.speedment.common.function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class CharUnaryOperatorTest {

    private final CharUnaryOperator TO_UPPERCASE = Character::toUpperCase;
    private final CharUnaryOperator TO_LOWERCASE = Character::toLowerCase;

    @Test
    void applyAsChar() {
        assertEquals('A', TO_UPPERCASE.applyAsChar('a'));
    }

    @Test
    void compose() {
        assertEquals('a', TO_LOWERCASE.compose(TO_UPPERCASE).applyAsChar('a'));
    }

    @Test
    void andThen() {
        assertEquals('A', TO_LOWERCASE.andThen(TO_UPPERCASE).applyAsChar('a'));
    }
}