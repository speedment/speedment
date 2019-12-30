package com.speedment.common.codegen.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class AnnotationTest {

    @Test
    void of() {
        assertNotNull(Annotation.of("a"));
    }
}