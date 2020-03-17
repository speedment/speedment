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
package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Initializer;
import com.speedment.common.codegen.model.Javadoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class HasJavadocTest {

    private HasJavadoc<Class> clazz;
    private Javadoc javadoc;

    @BeforeEach
    void setup() {
        clazz = Class.of("Foo");
        javadoc = Javadoc.of("a");
    }

    @Test
    void set() {
        clazz.set(javadoc);
        assertEquals(javadoc, clazz.getJavadoc().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void javadoc() {
        clazz.javadoc(javadoc);
        assertEquals(javadoc, clazz.getJavadoc().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void getJavadoc() {
        assertEquals(Optional.empty(), clazz.getJavadoc());
    }
}