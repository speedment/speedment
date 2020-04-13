/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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