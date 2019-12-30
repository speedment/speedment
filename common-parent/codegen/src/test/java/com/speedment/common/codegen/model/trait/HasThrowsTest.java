package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class HasThrowsTest {

    private HasThrows<Method> method;

    @BeforeEach
    void setup() {
        method = Method.of("x", int.class);
    }

    @Test
    void add() {
        method.add(NullPointerException.class);
        assertEquals(singleton(NullPointerException.class), method.getExceptions());
    }

    @Test
    void throwing() {
        method.throwing(NullPointerException.class);
        assertEquals(singleton(NullPointerException.class), method.getExceptions());
    }

    @Test
    void getExceptions() {
        assertTrue(method.getExceptions().isEmpty());
    }
}