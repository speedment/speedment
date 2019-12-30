package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Javadoc;
import com.speedment.common.codegen.model.JavadocTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class HasJavadocTagsTest {

    private static final String TEXT = "A";

    private HasJavadocTags<Javadoc> javadoc;
    private JavadocTag javadocTag;

    @BeforeEach
    void setup() {
        javadoc = Javadoc.of("a");
        javadocTag = JavadocTag.of("a", "b");
    }

    @Test
    void add() {
        javadoc.add(javadocTag);
        assertEquals(singletonList(javadocTag), javadoc.getTags());
    }

    @Test
    void author() {
        javadoc.author(TEXT);
        final JavadocTag tag = javadoc.getTags().iterator().next();
        assertEquals("author", tag.getName());
        assertEquals(TEXT, tag.getValue().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void returns() {
        javadoc.returns(TEXT);
        final JavadocTag tag = javadoc.getTags().iterator().next();
        assertEquals("return", tag.getName());
        assertEquals(TEXT, tag.getValue().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void since() {
        javadoc.since(TEXT);
        final JavadocTag tag = javadoc.getTags().iterator().next();
        assertEquals("since", tag.getName());
        assertEquals(TEXT, tag.getValue().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void see() {
        javadoc.see(int.class);
        final JavadocTag tag = javadoc.getTags().iterator().next();
        assertEquals("see", tag.getName());
        assertEquals("{@link int}", tag.getValue().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void param() {
        javadoc.param("x", "x-coordinate");
        final JavadocTag tag = javadoc.getTags().iterator().next();
        assertEquals("param", tag.getName());
        assertEquals("x", tag.getValue().orElseThrow(NoSuchElementException::new));
        assertEquals("x-coordinate", tag.getText().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void getTags() {
        assertTrue(javadoc.getTags().isEmpty());
    }
}