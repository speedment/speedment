package com.speedment.common.logger;

import org.junit.jupiter.api.Test;

import static com.speedment.common.logger.Level.*;
import static org.junit.jupiter.api.Assertions.*;

final class LevelTest {

    @Test
    void defaultLevel() {
        assertEquals(INFO, Level.defaultLevel());
    }

    @Test
    void isEqualOrLowerThan() {
        assertTrue(INFO.isEqualOrLowerThan(FATAL));
        assertFalse(FATAL.isEqualOrLowerThan(INFO));
        assertTrue(INFO.isEqualOrLowerThan(INFO));
    }

    @Test
    void isEqualOrHigherThan() {
        assertFalse(INFO.isEqualOrHigherThan(FATAL));
        assertTrue(FATAL.isEqualOrHigherThan(INFO));
        assertTrue(INFO.isEqualOrHigherThan(INFO));
    }

    @Test
    void toText() {
        for (Level level: Level.values()) {
            assertEquals(5, level.toText().length());
        }
    }
}