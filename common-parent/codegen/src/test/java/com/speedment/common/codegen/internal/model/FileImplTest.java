package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Class;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

final class FileImplTest extends AbstractTest<File> {

    private final static String NAME = "A";

    public FileImplTest() {
        super(() -> new FileImpl(NAME),
                a -> a.setName("Z"),
                a -> a.javadoc(Javadoc.of("A")),
                a -> a.licenseTerm("B"),
                a -> a.imports(List.class),
                a -> a.add(Class.of("B"))
        );
    }

    @Test
    void getName() {
        assertEquals(NAME, instance().getName());
    }

    @Test
    void setName() {
        instance().setName("Z");
        assertNotEquals(NAME, instance().getName());
    }

    @Test
    void set() {
        final Javadoc javadoc = Javadoc.of("Z");
        instance().set(javadoc);
        assertEquals(javadoc, instance().getJavadoc().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void testSet() {
        final LicenseTerm licenseTerm = LicenseTerm.of("Z");
        instance().set(licenseTerm);
        assertEquals(licenseTerm, instance().getLicenseTerm().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void getJavadoc() {
        assertFalse(instance().getJavadoc().isPresent());
    }

    @Test
    void getLicenseTerm() {
        assertFalse(instance().getJavadoc().isPresent());
    }

    @Test
    void getImports() {
        assertTrue(instance().getImports().isEmpty());
    }

    @Test
    void getClasses() {
        assertTrue(instance().getClasses().isEmpty());
    }
}