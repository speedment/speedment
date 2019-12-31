package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.*;

import java.util.List;

final class InterfaceImplTest extends AbstractTest<Interface> {

    private final static String NAME = "A";

    public InterfaceImplTest() {
        super(() -> new InterfaceImpl(NAME),
                a -> a.setName("Z"),
                a -> a.javadoc(Javadoc.of("A")),
                a -> a.imports(List.class),
                a -> a.annotate(Integer.class),
                a -> a.add(Generic.of(Integer.class)),
                a -> a.add(Integer.class),
                a -> a.field("x", int.class),
                a -> a.add(Method.of("x", int.class)),
                a -> a.add(Initializer.of()),
                a -> a.add(com.speedment.common.codegen.model.Class.of("c")),
                Interface::public_
        );
    }

}