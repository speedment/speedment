package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Class;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class HasImplementsTest {

    private static final String T = "T";

    private HasImplements<Class> clazz;

    @BeforeEach
    void setup() {
        clazz = Class.of("Foo");
    }

    @Test
    void add() {
        clazz.add(List.class);
        final Type type = clazz.getInterfaces().iterator().next();
        assertEquals(List.class, type);
    }

    @Test
    void implement() {
        clazz.implement(List.class, String.class);
        final Type type = clazz.getInterfaces().iterator().next();
        assertTrue(type.getTypeName().contains(List.class.getSimpleName()));
    }

    @Test
    void testImplement() {
        clazz.implement(List.class, T);
        final Type type = clazz.getInterfaces().iterator().next();
        assertTrue(type.getTypeName().contains(List.class.getSimpleName()));
    }

    @Test
    void testImplement1() {
        clazz.implement(List.class);
        final Type type = clazz.getInterfaces().iterator().next();
        assertTrue(type.getTypeName().contains(List.class.getSimpleName()));
    }

    @Test
    void getInterfaces() {
        assertTrue(clazz.getInterfaces().isEmpty());
    }
}