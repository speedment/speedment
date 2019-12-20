package com.speedment.common.logger.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class SystemOutLoggerFactoryTest {

    @Test
    void make() {
        // Tested indirectly
    }

    @Test
    void loggerClass() {
        assertEquals(SystemOutLogger.class,  new SystemOutLoggerFactory().loggerClass());
    }
}