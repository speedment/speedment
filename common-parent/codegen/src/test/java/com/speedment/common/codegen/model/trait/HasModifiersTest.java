package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.modifier.Modifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class HasModifiersTest {

    private HasModifiers<Class> clazz;

    @BeforeEach
    void setup() {
        clazz = Class.of("A");
    }

    @Test
    void getModifiers() {
        assertTrue(clazz.getModifiers().isEmpty());
        ((Class)clazz).private_();
        assertEquals(singleton(Modifier.PRIVATE), clazz.getModifiers());
    }
}