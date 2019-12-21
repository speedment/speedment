package com.speedment.common.injector.internal.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

final class ReflectionUtilTest {

    private static final class Foo {
        public int a;
    }

    @Test
    void traverseFields() throws NoSuchFieldException {
        final Field field = Foo.class.getField("a");
        final List<Field> fields = ReflectionUtil.traverseFields(Foo.class)
                .filter(f -> !f.getName().contains("jacocoData")) // Jaccoco intrumentation
                .collect(toList());
        assertEquals(1, fields.size(), fields.toString());
    }

    @Test
    void traverseMethods() {
        
    }

    @Test
    void traverseAncestors() {
    }

    @Test
    void missingArgumentStrategy() {
    }

    @Test
    void tryToCreate() {
    }

    @Test
    void errorMsg() {
    }

    @Test
    void parse() {
    }
}