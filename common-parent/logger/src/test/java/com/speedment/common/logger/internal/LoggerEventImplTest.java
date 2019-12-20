package com.speedment.common.logger.internal;

import com.speedment.common.logger.Level;
import com.speedment.common.logger.LoggerEvent;
import org.junit.jupiter.api.Test;

import javax.print.attribute.standard.MediaSize;

import static org.junit.jupiter.api.Assertions.*;

final class LoggerEventImplTest {

    private static final Level LEVEL = Level.FATAL;
    private static final String NAME = "c.s.e.Foo";
    private static final String MESSAGE = "John";

    private final LoggerEvent loggerEvent = new LoggerEventImpl(LEVEL, NAME, MESSAGE);

    @Test
    void getLevel() {
        assertEquals(LEVEL, loggerEvent.getLevel());
    }

    @Test
    void getName() {
        assertEquals(NAME, loggerEvent.getName());
    }

    @Test
    void getMessage() {
        assertEquals(MESSAGE, loggerEvent.getMessage());
    }
}