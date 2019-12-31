package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Javadoc;
import com.speedment.common.codegen.model.JavadocTag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class JavadocImplTest extends AbstractTest<Javadoc> {

    private final static String NAME = "A";

    public JavadocImplTest() {
        super(() -> new JavadocImpl(NAME),
                a -> a.imports(List.class),
                a -> a.setParent(Class.of("A")),
                a -> a.setText("V"),
                a -> a.add(JavadocTag.of("A"))
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
    void getImports() {
        assertTrue(instance().getImports().isEmpty());
    }

    @Test
    void getText() {
        assertEquals(NAME, instance().getText());
    }

    @Test
    void setText() {
        final String text = "C";
        instance().setText(text);
        assertEquals(text, instance().getText());
    }

    @Test
    void getTags() {
        assertTrue(instance().getImports().isEmpty());
    }
}