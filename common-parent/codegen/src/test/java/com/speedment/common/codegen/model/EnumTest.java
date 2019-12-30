package com.speedment.common.codegen.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class EnumTest {

    private Enum instance;
    private EnumConstant enumConstant = EnumConstant.of("A");

    @BeforeEach
    void setup() {
        instance = Enum.of("E");
    }

    @Test
    void add() {
        instance.add(enumConstant);
        assertEquals(singletonList(enumConstant), instance.getConstants());
    }

    @Test
    void getConstants() {
        assertTrue(instance.getConstants().isEmpty());
    }

    @Test
    void of() {
        assertNotNull(Enum.of("E"));
    }
}