package com.speedment.common.injector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

final class InjectorBuilderTest {


    @Test
    void logger() {
        assertNotNull(InjectorBuilder.logger());
    }
}