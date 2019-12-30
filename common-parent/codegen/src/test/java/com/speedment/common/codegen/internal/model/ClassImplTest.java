package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Class;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ClassImplTest extends AbstractTest<Class> {

    private final static String NAME = "A";

    public ClassImplTest() {
        super(() -> new ClassImpl(NAME),
                a -> a.setName("Z"),
                a -> a.javadoc(Javadoc.of("A")),
                a -> a.imports(List.class),
                a -> a.annotate(Integer.class),
                a -> a.add(Generic.of(Integer.class)),
                a -> a.add(Integer.class),
                a -> a.field("x", int.class),
                a -> a.add(Method.of("x", int.class)),
                a -> a.add(Initializer.of()),
                a -> a.add(Class.of("c")),
                Class::public_,
                a -> a.setSupertype(List.class),
                a -> a.add(Constructor.of())
        );
    }

    @Test
    void setSupertype() {
        instance().setSupertype(List.class);
        assertEquals(List.class, instance().getSupertype().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void getSupertype() {
        assertEquals(Optional.empty(), instance().getSupertype());
    }

    @Test
    void getConstructors() {
        assertTrue(instance().getConstructors().isEmpty());
    }
}