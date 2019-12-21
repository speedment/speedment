package com.speedment.common.injector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class InjectorTest {

    @Test
    void builder() {
        assertNotNull(Injector.builder());
    }

    @Test
    void testBuilder() {
        assertNotNull(Injector.builder(InjectorTest.class.getClassLoader()));
    }

    @Test
    void logger() {
        assertNotNull(Injector.logger());
    }
}