package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.Javadoc;
import com.speedment.common.codegen.model.Value;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

final class FieldImplTest extends AbstractTest<Field> {

    private final static String NAME = "A";

    public FieldImplTest() {
        super(() -> new FieldImpl(NAME, int.class),
                a -> a.setName("Z"),
                a -> a.set(Long.class),
                a -> a.set(Value.ofNumber(1)),
                a -> a.javadoc(Javadoc.of("A")),
                a -> a.imports(List.class),
                a -> a.annotate(Integer.class),
                Field::public_
        );
    }
    
    @Test
    void setParent() {
        instance().setParent(Class.of(NAME));
        assertTrue(instance().getParent().isPresent());
    }

    @Test
    void getParent() {
        assertFalse(instance().getParent().isPresent());
    }

    @Test
    void getImports() {
        assertTrue(instance().getImports().isEmpty());
    }

    @Test
    void getName() {
        assertEquals(NAME, instance().getName());
    }

    @Test
    void setName() {
        instance().setName("Z");
        assertNotEquals(NAME, instance().getName());
    }

    @Test
    void getType() {
        assertEquals(int.class, instance().getType());
    }

    @Test
    void set() {
        instance().set(long.class);
        assertEquals(long.class, instance().getType());
    }

    @Test
    void getModifiers() {
        assertTrue(instance().getModifiers().isEmpty());
    }

    @Test
    void testSet() {
        instance().set(Javadoc.of("A"));
        assertTrue(instance().getJavadoc().isPresent());
    }

    @Test
    void getJavadoc() {
        assertFalse(instance().getJavadoc().isPresent());
    }

    @Test
    void testSet1() {
        final Value<?> v = Value.ofNumber(1);
        instance().set(v);
        assertEquals(v, instance().getValue().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void getValue() {
        assertFalse(instance().getJavadoc().isPresent());
    }

    @Test
    void getAnnotations() {
        assertTrue(instance().getAnnotations().isEmpty());
    }
}