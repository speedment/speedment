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