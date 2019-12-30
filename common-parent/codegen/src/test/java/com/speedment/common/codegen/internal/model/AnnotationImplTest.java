package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.Annotation;
import com.speedment.common.codegen.model.Javadoc;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class AnnotationImplTest extends AbstractTest<Annotation> {

    private final static String NAME = "A";
    private final static String OTHER_NAME = "B";

    public AnnotationImplTest() {
        super(() -> new AnnotationImpl(NAME),
                a -> a.setName("Z"),
                a -> a.javadoc(Javadoc.of("A")),
                a -> a.annotate(Integer.class),
                a -> a.field("x", int.class),
                a -> a.imports(List.class),
                Annotation::public_
        );
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
    void getFields() {
        assertTrue(instance().getFields().isEmpty());
    }

    @Test
    void set() {
        instance().set(Javadoc.of("A"));
        assertEquals("A", instance().getJavadoc().orElseThrow(NoSuchElementException::new).getText());
    }

    @Test
    void getJavadoc() {
        assertEquals(Optional.empty(), instance().getJavadoc());
    }

    @Test
    void getImports() {
        assertTrue(instance().getFields().isEmpty());
    }

    @Test
    void getModifiers() {
        assertTrue(instance().getFields().isEmpty());
    }

    @Test
    void getAnnotations() {
        assertTrue(instance().getFields().isEmpty());
    }

}