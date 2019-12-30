package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class HasMethodsTest {

    private HasMethods<Class> clazz;
    private Method method;

    @BeforeEach
    void setup() {
        clazz = Class.of("A");
        method = Method.of("x", int.class);
    }

    @Test
    void add() {
        clazz.add(method);
        assertEquals(singletonList(method), clazz.getMethods());
    }

    @Test
    void getMethods() {
        assertTrue(clazz.getMethods().isEmpty());
    }
}