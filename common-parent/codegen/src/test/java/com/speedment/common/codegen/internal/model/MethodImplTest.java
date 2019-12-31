package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class MethodImplTest extends AbstractTest<Method> {

    private final static String NAME = "A";
    private final static Type TYPE = int.class;

    public MethodImplTest() {
        super(() -> new MethodImpl(Method.of(NAME, TYPE)),
                a -> a.setName("Z"),
                a -> a.setParent(Interface.of("C")),
                a -> a.javadoc(Javadoc.of("A")),
                a -> a.imports(List.class),
                a -> a.annotate(Integer.class),
                a -> a.add(Generic.of(Integer.class)),
                a -> a.add(Integer.class),
                a -> a.field("x", int.class),
                a -> a.add(NullPointerException.class),
                a -> a.set(long.class),
                a -> a.add("a++;"),
                Method::default_
        );
    }

/*    @Test
    void constructor() {
        final Method method = new MethodImpl()
    }*/

    @Test
    void setParent() {
        final Interface inter = Interface.of("V");
        instance().setParent(inter);
        assertEquals(inter, instance().getParent().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void getParent() {
        assertEquals(Optional.empty(), instance().getParent());
    }

    @Test
    void getName() {
        assertEquals(NAME, instance().getName());
    }

    @Test
    void getType() {
        assertEquals(int.class, instance().getType());
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
    void getJavadoc() {
        assertFalse(instance().getJavadoc().isPresent());
    }

    @Test
    void getAnnotations() {
        assertTrue(instance().getAnnotations().isEmpty());
    }

    @Test
    void getImports() {
        assertTrue(instance().getImports().isEmpty());
    }

    @Test
    void setName() {
        final String name = "X";
        instance().setName(name);
        assertEquals(name, instance().getName());
    }

    @Test
    void set() {
        final Javadoc javadoc = Javadoc.of("A");
        instance().set(javadoc);
        assertEquals(javadoc, instance().getJavadoc().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void getGenerics() {
        assertTrue(instance().getGenerics().isEmpty());
    }

    @Test
    void testSet() {
        final Type type = boolean.class;
        instance().set(type);
        assertEquals(type, instance().getType());
    }

    @Test
    void getExceptions() {
        assertTrue(instance().getImports().isEmpty());
    }
}