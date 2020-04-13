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
import com.speedment.common.codegen.model.Import;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class HasImportsTest {

    private static final Import IMPORT = Import.of(List.class);

    private HasImports<Class> clazz;

    @BeforeEach
    void setup() {
        clazz = Class.of("Foo");
    }

    @Test
    void add() {
        clazz.add(IMPORT);
        assertContainsImport();
    }

    @Test
    void imports() {
        clazz.imports(IMPORT);
        assertContainsImport();
    }

    @Test
    void testImports() {
        clazz.imports(List.class);
        final Import import_ = clazz.getImports().iterator().next();
        assertTrue(import_.getType().getTypeName().contains(List.class.getSimpleName()));
    }

    @Test
    void testImports1() {
        clazz.imports(List.class, "add");
        final Import import_ = clazz.getImports().iterator().next();
        assertTrue(import_.getType().getTypeName().contains(List.class.getSimpleName()));
    }

    @Test
    void getImports() {
        assertTrue(clazz.getImports().isEmpty());
    }

    private void assertContainsImport() {
        assertEquals(singletonList(IMPORT), clazz.getImports());
    }

}