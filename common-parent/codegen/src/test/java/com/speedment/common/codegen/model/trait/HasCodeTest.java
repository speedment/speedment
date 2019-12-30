package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class HasCodeTest {

    private static final String A = "A";
    private static final String B = "B";

    private HasCode<Method> method;

    @BeforeEach
    void setup() {
        method = Method.of("x", int.class);
    }

    @Test
    void add() {
        method.add(A, B);
        assertEquals(Arrays.asList(A, B), method.getCode());
    }

    @Test
    void testAdd() {
        method.add(A);
        assertEquals(singletonList(A), method.getCode());
    }

    @Test
    void testAdd1() {
        method.add(Stream.of(A, B));
        assertEquals(Arrays.asList(A, B), method.getCode());
    }

    @Test
    void getCode() {
        assertTrue(method.getCode().isEmpty());
    }
}