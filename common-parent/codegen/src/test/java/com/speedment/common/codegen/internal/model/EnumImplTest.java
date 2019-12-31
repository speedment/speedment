package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Enum;
import com.speedment.common.codegen.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class EnumImplTest extends AbstractTest<Enum> {

    private final static String NAME = "A";

    public EnumImplTest() {
        super(() -> new EnumImpl(NAME),
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
                Enum::public_,
                a -> a.add(Constructor.of())
        );
    }

    @Test
    void getConstants() {
        assertTrue(instance().getConstants().isEmpty());
    }

    @Test
    void getConstructors() {
        assertTrue(instance().getConstructors().isEmpty());
    }

}