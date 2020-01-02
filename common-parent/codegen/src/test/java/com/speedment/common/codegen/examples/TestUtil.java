package com.speedment.common.codegen.examples;

import com.speedment.common.codegen.util.Formatting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

final class TestUtil {
    private TestUtil() {}

    static String code(Class<?> testClass) {
        requireNonNull(testClass);
        final Path path = Paths.get("src", "test", "resources", testClass.getSimpleName() + ".java");
        try {
            return Files.lines(path).collect(Collectors.joining(Formatting.nl()));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}