package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Enum;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class EnumConstantImplTest extends AbstractTest<EnumConstant> {

    private static final String NAME = "A";
    private static final String OTHER_NAME = "B";

    public EnumConstantImplTest() {
        super(() -> new EnumConstantImpl(NAME),
                a -> a.setParent(Enum.of("A")),
                a -> a.setName("Z"),
                a -> a.javadoc(Javadoc.of("A")),
                a -> a.add(Class.of("C")),
                a -> a.add(Initializer.of()),
                a -> a.add(Method.of("a", int.class)),
                a -> a.add(Field.of("x", int.class)),
                a -> a.add(Value.ofNumber(1)),
                a -> a.imports(List.class),
                a -> a.annotate(Integer.class)
        );
    }

    @Test
    void setParent() {
        instance().setParent(Enum.of("B"));
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
    void setName() {
        instance().setName(OTHER_NAME);
        assertEquals(OTHER_NAME, instance().getName());
    }

    @Test
    void getName() {
        assertEquals(NAME, instance().getName());
    }

    @Test
    void getValues() {
        assertTrue(instance().getValues().isEmpty());
    }

    @Test
    void set() {
        instance().set(Javadoc.of("C"));
        assertTrue(instance().getJavadoc().isPresent());
    }

    @Test
    void getJavadoc() {
        assertFalse(instance().getJavadoc().isPresent());
    }

    @Test
    void getClasses() {
        assertTrue(instance().getClasses().isEmpty());
    }

    @Test
    void getInitializers() {
        assertTrue(instance().getInitializers().isEmpty());
    }

    @Test
    void getMethods() {
        assertTrue(instance().getMethods().isEmpty());
    }

    @Test
    void getFields() {
        assertTrue(instance().getFields().isEmpty());
    }

    @Test
    void getAnnotations() {
        assertTrue(instance().getAnnotations().isEmpty());
    }
}