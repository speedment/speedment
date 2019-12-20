package com.speedment.common.logger.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class SystemOutLoggerTest {

    @Test
    void output() {
        new NoOpLogger().output("Olle");
    }
}