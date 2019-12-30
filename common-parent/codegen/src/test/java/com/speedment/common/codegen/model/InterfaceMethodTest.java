package com.speedment.common.codegen.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class InterfaceMethodTest {

    @Test
    void of() {
       assertNotNull(InterfaceMethod.of(Method.of("x", int.class)));
    }
}