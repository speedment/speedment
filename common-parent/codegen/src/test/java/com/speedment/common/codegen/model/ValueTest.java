package com.speedment.common.codegen.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class ValueTest {

    private enum EnumExample {A, B}

    @Test
    void ofArray() {
        assertNotNull(Value.ofArray());
    }

    @Test
    void testOfArray() {
        assertNotNull(Value.ofArray(Arrays.asList(Value.ofText("A"))));
    }

    @Test
    void ofBoolean() {
        assertNotNull(Value.ofBoolean(true));
    }

    @Test
    void ofEnum() {
        assertNotNull(Value.ofEnum(EnumExample.class, "A"));
    }

    @Test
    void ofNull() {
        assertNotNull(Value.ofNull());
    }

    @Test
    void ofNumber() {
        assertNotNull(Value.ofNumber(1));
    }

    @Test
    void ofReference() {
        assertNotNull(Value.ofReference("A"));
    }

    @Test
    void ofText() {
        assertNotNull(Value.ofText("A"));
    }

    @Test
    void ofAnonymous() {
        assertNotNull(Value.ofAnonymous(int.class));
    }

    @Test
    void ofInvocation() {
        assertNotNull(Value.ofInvocation("add", Value.ofReference("A")));
    }

    @Test
    void testOfInvocation() {
        assertNotNull(Value.ofInvocation(List.class, "add", Value.ofReference("A")));
    }
}