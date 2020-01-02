package com.speedment.common.codegen.examples;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.controller.AutoImports;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.codegen.util.Formatting;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class AbstractExample {

    private static final Generator generator = Generator.forJava();

    abstract void onFile(File file);

    @Test
    void test() {
        final File file = File.of(simpleName());
        onFile(file);
        file.call(new AutoImports(generator.getDependencyMgr()));
        final String actual  = generator.on(file).orElseThrow(NoSuchElementException::new);

        final String actualTailTrimmed = Stream.of(actual.split(Formatting.nl()))
                .map(this::tailTrim)
                .collect(Collectors.joining(Formatting.nl()));

        final String expected = TestUtil.code(getClass());
        assertEquals(expected, actualTailTrimmed);
    }

    String simpleName() {
        return getClass().getSimpleName();
    }

    String name() {
        return getClass().getName();
    }

    private String tailTrim(String s) {
        for (int i = s.length(); i > 0; i--) {
            if (!Character.isWhitespace(s.charAt(i - 1)))
                return s.substring(0, i);
        }
        return "";
    }

}