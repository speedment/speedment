package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.DEPRECATED;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class HasAnnotationUsageTest {

    private HasAnnotationUsage<Field> field;

    @BeforeEach
    void setup() {
        field = Field.of("x", int.class);
    }

    @Test
    void add() {
        field.add(DEPRECATED);
        assertEquals(singletonList(DEPRECATED), field.getAnnotations());
    }

    @Test
    void annotate() {
        field.annotate(DEPRECATED);
        assertEquals(singletonList(DEPRECATED), field.getAnnotations());
    }

    @Test
    void testAnnotate() {
        field.annotate(Deprecated.class);
        assertEquals(1, field.getAnnotations().size());
    }

    @Test
    void testAnnotate1() {
        field.annotate(Deprecated.class, "Never used");
        assertEquals(1, field.getAnnotations().size());

    }

    @Test
    void getAnnotations() {
        assertTrue(field.getAnnotations().isEmpty());
    }
}