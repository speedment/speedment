package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Initializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class HasInitializersTest {

    private HasInitializers<Class> clazz;
    private Initializer initializer;

    @BeforeEach
    void setup() {
        clazz = Class.of("Foo");
        initializer = Initializer.of();
    }

    @Test
    void add() {
        clazz.add(initializer);
        assertEquals(singletonList(initializer), clazz.getInitializers());
    }

    @Test
    void getInitializers() {
        assertTrue(clazz.getInitializers().isEmpty());
    }
}