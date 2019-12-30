package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Initializer;
import com.speedment.common.codegen.model.Javadoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class HasJavadocTest {

    private HasJavadoc<Class> clazz;
    private Javadoc javadoc;

    @BeforeEach
    void setup() {
        clazz = Class.of("Foo");
        javadoc = Javadoc.of("a");
    }

    @Test
    void set() {
        clazz.set(javadoc);
        assertEquals(javadoc, clazz.getJavadoc().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void javadoc() {
        clazz.javadoc(javadoc);
        assertEquals(javadoc, clazz.getJavadoc().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void getJavadoc() {
        assertEquals(Optional.empty(), clazz.getJavadoc());
    }
}