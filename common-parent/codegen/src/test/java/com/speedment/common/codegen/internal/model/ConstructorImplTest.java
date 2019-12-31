package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Class;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class ConstructorImplTest extends AbstractTest<Constructor> {

    public ConstructorImplTest() {
        super(ConstructorImpl::new,
                a -> a.setParent(Class.of("A")),
                a -> a.javadoc(Javadoc.of("A")),
                a -> a.imports(List.class),
                a -> a.annotate(Integer.class),
                a -> a.add(Generic.of(Integer.class)),
                a -> a.add(Field.of("x", int.class)),
                a -> a.add("x = 1;"),
                a -> a.add(Integer.class),
                a -> a.field("x", int.class),
                Constructor::public_
        );
    }

    @Test
    void setParent() {
        instance().setParent(Class.of("B"));
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
    void getFields() {
        assertTrue(instance().getFields().isEmpty());
    }

    @Test
    void getCode() {
        assertTrue(instance().getCode().isEmpty());
    }

    @Test
    void getModifiers() {
        assertTrue(instance().getModifiers().isEmpty());
    }

    @Test
    void set() {
        instance().set(Javadoc.of("A"));
        assertTrue(instance().getJavadoc().isPresent());
    }

    @Test
    void getJavadoc() {
        assertFalse(instance().getJavadoc().isPresent());
    }

    @Test
    void getAnnotations() {
        assertTrue(instance().getAnnotations().isEmpty());
    }

    @Test
    void getGenerics() {
        assertTrue(instance().getGenerics().isEmpty());
    }

    @Test
    void getExceptions() {
        assertTrue(instance().getExceptions().isEmpty());
    }

}