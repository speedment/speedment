package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Class;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

final class HasSupertypeTest {

    private HasSupertype<Class> clazz;

    @BeforeEach
    void setup() {
        clazz = Class.of("A");
    }

    @Test
    void setSupertype() {
        clazz.setSupertype(List.class);
        assertEquals(List.class, clazz.getSupertype().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void extend() {
        clazz.extend(List.class, String.class);
        assertTrue(clazz.getSupertype().orElseThrow(NoSuchElementException::new).getTypeName().contains(List.class.getSimpleName()));
    }

    @Test
    void testExtend() {
        clazz.extend(List.class, "T");
        assertTrue(clazz.getSupertype().orElseThrow(NoSuchElementException::new).getTypeName().contains(List.class.getSimpleName()));
    }

    @Test
    void testExtend1() {
        clazz.extend(List.class);
        assertTrue(clazz.getSupertype().orElseThrow(NoSuchElementException::new).getTypeName().contains(List.class.getSimpleName()));
    }

    @Test
    void getSupertype() {
        assertFalse(clazz.getSupertype().isPresent());
    }
}