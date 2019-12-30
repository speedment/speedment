package com.speedment.common.codegen.model;

import com.speedment.common.codegen.model.modifier.Modifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;

final class ConstructorTest {

    @Test
    void of() {
        assertCorrect(Constructor.of());
    }

    @Test
    void newPublic() {
        assertCorrect(Constructor.newPublic(), Modifier.PUBLIC);
    }

    @Test
    void newPrivate() {
        assertCorrect(Constructor.newPrivate(), Modifier.PRIVATE);
    }

    @Test
    void newProtected() {
        assertCorrect(Constructor.newProtected(), Modifier.PROTECTED);
    }

    void assertCorrect(Constructor constructor, Modifier... modifiers) {
        assertTrue(constructor.getFields().isEmpty());
        assertTrue(constructor.getCode().isEmpty());
        assertEquals(Stream.of(modifiers).collect(toSet()), constructor.getModifiers());
    }

}