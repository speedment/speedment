package com.speedment.common.codegen.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

final class AnnotationUsageTest {

    private static final String A = "A";
    private static final String B = "B";
    private AnnotationUsage instance;

    @BeforeEach
    void setup() {
        instance = AnnotationUsage.of(Integer.class);
    }

    @Test
    void put() {
        instance.put(A, B);
        final Map.Entry<String, Value<?>> entry = instance.getValues().iterator().next();
        assertEquals(A, entry.getKey());
        assertEquals(B, entry.getValue().getValue());
    }

    @Test
    void testPut() {
        instance.put(A, Value.ofText(B));
        final Map.Entry<String, Value<?>> entry = instance.getValues().iterator().next();
        assertEquals(A, entry.getKey());
        assertEquals(B, entry.getValue().getValue());
    }

    @Test
    void testPut1() {
        instance.put(A, 1);
        final Map.Entry<String, Value<?>> entry = instance.getValues().iterator().next();
        assertEquals(A, entry.getKey());
        assertEquals(1, entry.getValue().getValue());
    }

    @Test
    void getValues() {
        assertTrue(instance.getValues().isEmpty());
    }

    @Test
    void of() {
        assertNotNull(AnnotationUsage.of(Integer.class));
    }
}