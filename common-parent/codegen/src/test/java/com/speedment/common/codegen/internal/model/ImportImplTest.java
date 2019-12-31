package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.internal.java.view.InterfaceView;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Import;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class ImportImplTest extends AbstractTest<Import> {

    private final static String NAME = "A";

    public ImportImplTest() {
        super(() -> new ImportImpl(Long.class),
                a -> a.setParent(Class.of("A")),
                a -> a.setStaticMember("a"),
                a -> a.set(Integer.class),
                Import::static_
        );
    }

    @Test
    void setParent() {
        instance().setParent(Class.of(NAME));
        assertTrue(instance().getParent().isPresent());
    }

    @Test
    void getParent() {
        assertFalse(instance().getParent().isPresent());
    }

    @Test
    void set() {
        instance().set(String.class);
        assertEquals(String.class, instance().getType());
    }

    @Test
    void getType() {
        assertEquals(Long.class, instance().getType());
    }

    @Test
    void getModifiers() {
        assertTrue(instance().getModifiers().isEmpty());
    }

    @Test
    void getStaticMember() {
        assertEquals(Optional.empty(), instance().getStaticMember());
    }

    @Test
    void setStaticMember() {
        final String memeber = "A";
        instance().setStaticMember(memeber);
        assertEquals(memeber, instance().getStaticMember().orElseThrow(NoSuchElementException::new));
    }
}