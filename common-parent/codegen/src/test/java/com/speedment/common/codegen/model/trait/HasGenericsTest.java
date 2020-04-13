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

import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class HasGenericsTest {

    private static final String T = "T";
    private static final Type TYPE = SimpleType.create(T);

    private HasGenerics<Method> method;
    private Field field;

    @BeforeEach
    void setup() {
        method = Method.of("x", int.class);
        field  = Field.of("x", int.class);
    }

    @Test
    void add() {
        method.add(Generic.of(TYPE));
        final Generic generic = method.getGenerics().iterator().next();
        assertEquals(T, generic.asType().getTypeName());
    }

    @Test
    void generic() {
        method.generic(T);
        final Generic generic = method.getGenerics().iterator().next();
        assertEquals(T, generic.asType().getTypeName());
    }

    @Test
    void testGeneric() {
        method.generic(T);
        final Generic generic = method.getGenerics().iterator().next();
        assertEquals(T, generic.asType().getTypeName());
    }

    @Test
    void testGeneric1() {
        method.generic("T", TYPE);
        final Generic generic = method.getGenerics().iterator().next();
        assertEquals(T, generic.asType().getTypeName());
    }

    @Test
    void testGeneric2() {
        method.generic(TYPE);
        final Generic generic = method.getGenerics().iterator().next();
        assertEquals(T, generic.asType().getTypeName());
    }

    @Test
    void getGenerics() {
        assertTrue(method.getGenerics().isEmpty());
    }
}