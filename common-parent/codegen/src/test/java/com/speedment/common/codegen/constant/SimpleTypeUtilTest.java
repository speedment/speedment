package com.speedment.common.codegen.constant;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.File;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class SimpleTypeUtilTest {

    @Test
    void nameOfIllegalFileName() {
        final File file = File.of("com.company.module.Foo.c++");
        final Class clazz = Class.of("Bar");
        assertThrows(RuntimeException.class, () -> SimpleTypeUtil.nameOf(file, clazz));
    }

    @Test
    void nameOfNoClass() {
        final File file = File.of("com.company.module.Foo.java");
        final Class clazz = Class.of("Bar");
        assertThrows(RuntimeException.class, () -> SimpleTypeUtil.nameOf(file, clazz));
    }

    @Test
    void nameOf() {
        final File file = File.of("com.company.module.Foo.java");
        final Class clazz = Class.of("Foo");
        file.add(clazz);
        assertDoesNotThrow(() -> SimpleTypeUtil.nameOf(file, clazz));
    }

}