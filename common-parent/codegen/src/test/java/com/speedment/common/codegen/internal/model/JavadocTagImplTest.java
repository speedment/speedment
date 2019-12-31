package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.JavadocTag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

final class JavadocTagImplTest extends AbstractTest<JavadocTag> {

    private final static String NAME = "A";

    public JavadocTagImplTest() {
        super(() -> new JavadocTagImpl(NAME),
                a -> a.imports(List.class),
                a -> a.setText("V"),
                a -> a.setValue("V"),
                a -> a.imports(Integer.class),
                a -> a.setText("V"),
                a -> a.setName("B")
        );
    }

    @Test
    void constructor() {
        final String text = "T";
        final JavadocTag tag = new JavadocTagImpl(NAME, text);
        assertEquals(text, tag.getText().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void constructor2() {
        final String text = "T";
        final String value = "V";
        final JavadocTag tag = new JavadocTagImpl(NAME, value, text);
        assertEquals(value, tag.getValue().orElseThrow(NoSuchElementException::new));
        assertEquals(text, tag.getText().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void imports() {
        assertTrue(instance().getImports().isEmpty());
    }

    @Test
    void getValue() {
        assertFalse(instance().getValue().isPresent());
    }

}