package com.speedment.common.codegen.model;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

final class MethodTest {

    @Test
    void of() {
        assertNotNull(Method.of("x", int.class));
    }

    @Test
    void override() {
        final Method method = Method.of("x", int.class);
        method.override();
        final AnnotationUsage annotationUsage = method.getAnnotations().iterator().next();
        assertEquals(Override.class, annotationUsage.getType());
    }
}