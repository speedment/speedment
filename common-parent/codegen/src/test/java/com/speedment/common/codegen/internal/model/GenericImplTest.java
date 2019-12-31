package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.Generic;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

final class GenericImplTest extends AbstractTest<Generic> {

    private final static String NAME = "A";

    public GenericImplTest() {
        super(GenericImpl::new,
                a -> a.setBoundType(Generic.BoundType.SUPER),
                a -> a.add(Integer.class)
        );
    }

    @Test
    void setLowerBound() {
        final String low = "A";
        instance().setLowerBound(low);
        assertEquals(low, instance().getLowerBound().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void getLowerBound() {
        assertFalse(instance().getLowerBound().isPresent());
    }

    @Test
    void setBoundType() {
        instance().setBoundType(Generic.BoundType.SUPER);
        assertEquals(Generic.BoundType.SUPER, instance().getBoundType());
    }

    @Test
    void getBoundType() {
        assertEquals(Generic.BoundType.EXTENDS, instance().getBoundType());
    }

    @Test
    void getUpperBounds() {
        assertTrue(instance().getUpperBounds().isEmpty());
    }

    @Test
    void asTypeIllegal() {
        assertThrows(UnsupportedOperationException.class, instance()::asType);
    }

    @Test
    void asTypeIllegal2() {
        instance().add(Integer.class);
        instance().add(List.class);
        assertThrows(UnsupportedOperationException.class, instance()::asType);
    }

    @Test
    void asType() {
        instance().setLowerBound("T");
        instance().add(Integer.class);
        instance().add(List.class);
        final Type type = instance().asType();
        assertTrue(type.getTypeName().contains("T"));
    }
}