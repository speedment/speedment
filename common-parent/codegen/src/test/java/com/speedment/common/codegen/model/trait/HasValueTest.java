package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class HasValueTest {

    private HasValue<Field> field;

    @BeforeEach
    void setup() {
        field = Field.of("x", int.class);
    }

    @Test
    void set() {
        field.set(1);
        assertEquals(1, field.getValue().orElseThrow(NoSuchElementException::new).getValue());
    }

    @Test
    void testSet() {
        field.set("A");
        assertEquals("A", field.getValue().orElseThrow(NoSuchElementException::new).getValue());
    }

    @Test
    void testSet1() {
        field.set(true);
        assertEquals(true, field.getValue().orElseThrow(NoSuchElementException::new).getValue());
    }

    @Test
    void testSet2() {
        field.set(Value.ofReference("x"));
        assertEquals("x", field.getValue().orElseThrow(NoSuchElementException::new).getValue());
    }

    @Test
    void getValue() {
        assertEquals(Optional.empty(), field.getValue());
    }
}