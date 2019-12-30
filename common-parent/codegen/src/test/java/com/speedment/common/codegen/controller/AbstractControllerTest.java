package com.speedment.common.codegen.controller;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.constant.DefaultType;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.internal.java.JavaGenerator;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.util.Formatting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class AbstractControllerTest {

    private Generator generator;

    @BeforeEach
    void setup() {
        this.generator = new JavaGenerator();
        Formatting.tab("    ");
    }

    Generator generator() {
        return generator;
    }

    abstract File createFile();

    abstract String expected();

    @Test
    void accept() {
        final File file = createFile();
        final String expected = expected();
        final String actual = generator.on(file).orElseThrow(NoSuchElementException::new);
        assertEquals(expected, actual, "Make sure generated file matches expected:");
    }
}