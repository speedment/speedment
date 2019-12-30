package com.speedment.common.codegen.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class InterfaceFieldTest {

    @Test
    void of() {
        assertNotNull(InterfaceField.of(Field.of("x", int.class)));
    }
}