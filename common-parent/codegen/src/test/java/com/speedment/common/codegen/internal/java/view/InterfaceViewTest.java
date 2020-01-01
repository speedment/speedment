package com.speedment.common.codegen.internal.java.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.provider.StandardJavaGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class InterfaceViewTest {

    private static final InterfaceView INSTANCE = new InterfaceView();
    private static final Generator GENERATOR = new StandardJavaGenerator();

    @Test
    void renderDeclarationType() {
        assertEquals("interface ", INSTANCE.renderDeclarationType());
    }

    @Test
    void extendsOrImplementsInterfaces() {
        assertEquals("extends ", INSTANCE.extendsOrImplementsInterfaces());
    }

    @Test
    void renderSupertype() {
        assertEquals("", INSTANCE.renderSupertype(GENERATOR, Interface.of("Foo")));
    }

    @Test
    void wrapMethod() {
        assertNotNull(INSTANCE.wrapMethod(Method.of("x", int.class)));
    }

    @Test
    void wrapField() {
        assertNotNull(INSTANCE.wrapField(Field.of("x", int.class)));
    }

    @Test
    void renderConstructors() {
        assertEquals("", INSTANCE.renderConstructors(GENERATOR, Interface.of("A")));
    }
}