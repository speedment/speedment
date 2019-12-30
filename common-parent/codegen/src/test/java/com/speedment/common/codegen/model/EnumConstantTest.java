package com.speedment.common.codegen.model;

import com.speedment.common.codegen.model.value.NumberValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class EnumConstantTest {

    private EnumConstant enumConstant;

    @BeforeEach
    void setup() {
         enumConstant = EnumConstant.of("A");
    }

    @Test
    void add() {
        final NumberValue value = Value.ofNumber(1);
        enumConstant.add(value);
        assertEquals(singletonList(value), enumConstant.getValues());
    }

    @Test
    void getValues() {
        assertTrue(enumConstant.getValues().isEmpty());
    }

    @Test
    void of() {
        assertNotNull(EnumConstant.of("A"));
    }
}