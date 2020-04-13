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