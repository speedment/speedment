package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class HasClassesTest {

    private HasClasses<File> file;
    private Class clazz;

    @BeforeEach
    void setup() {
        file = File.of("A");
        clazz = Class.of("A");
    }

    @Test
    void add() {
        file.add(clazz);
        assertEquals(singletonList(clazz), file.getClasses());
    }

    @Test
    void addAllClasses() {
        file.addAllClasses(singleton(clazz));
        assertEquals(singletonList(clazz), file.getClasses());
    }

    @Test
    void getClasses() {
        assertTrue(file.getClasses().isEmpty());
    }
}