package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Import;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class HasImportsTest {

    private static final Import IMPORT = Import.of(List.class);

    private HasImports<Class> clazz;

    @BeforeEach
    void setup() {
        clazz = Class.of("Foo");
    }

    @Test
    void add() {
        clazz.add(IMPORT);
        assertContainsImport();
    }

    @Test
    void imports() {
        clazz.imports(IMPORT);
        assertContainsImport();
    }

    @Test
    void testImports() {
        clazz.imports(List.class);
        final Import import_ = clazz.getImports().iterator().next();
        assertTrue(import_.getType().getTypeName().contains(List.class.getSimpleName()));
    }

    @Test
    void testImports1() {
        clazz.imports(List.class, "add");
        final Import import_ = clazz.getImports().iterator().next();
        assertTrue(import_.getType().getTypeName().contains(List.class.getSimpleName()));
    }

    @Test
    void getImports() {
        assertTrue(clazz.getImports().isEmpty());
    }

    private void assertContainsImport() {
        assertEquals(singletonList(IMPORT), clazz.getImports());
    }

}