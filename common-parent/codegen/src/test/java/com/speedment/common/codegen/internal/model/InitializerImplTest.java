package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Initializer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class InitializerImplTest extends AbstractTest<Initializer> {

    public InitializerImplTest() {
        super(InitializerImpl::new,
                a -> a.setParent(Class.of("A")),
                a -> a.add(Import.of(List.class)),
                a -> a.add("a = 1;"),
                Initializer::static_
        );
    }

    @Test
    void setParent() {
        final Class c = Class.of("A");
        instance().setParent(c);
        assertEquals(c, instance().getParent().orElseThrow(NoSuchElementException::new));
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
    void getCode() {
        final String code = "a++;";
        assertTrue(instance().getCode().isEmpty());
        instance().add(code);
        assertEquals(singletonList(code), instance().getCode());
    }

    @Test
    void getModifiers() {
        assertTrue(instance().getModifiers().isEmpty());
    }
}