package com.speedment.common.codegen.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

final class InterfaceTest {

    @Test
    void of() {
        assertNotNull(Interface.of("A"));
    }
}