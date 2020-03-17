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
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Class;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class HasImplementsTest {

    private static final String T = "T";

    private HasImplements<Class> clazz;

    @BeforeEach
    void setup() {
        clazz = Class.of("Foo");
    }

    @Test
    void add() {
        clazz.add(List.class);
        final Type type = clazz.getInterfaces().iterator().next();
        assertEquals(List.class, type);
    }

    @Test
    void implement() {
        clazz.implement(List.class, String.class);
        final Type type = clazz.getInterfaces().iterator().next();
        assertTrue(type.getTypeName().contains(List.class.getSimpleName()));
    }

    @Test
    void testImplement() {
        clazz.implement(List.class, T);
        final Type type = clazz.getInterfaces().iterator().next();
        assertTrue(type.getTypeName().contains(List.class.getSimpleName()));
    }

    @Test
    void testImplement1() {
        clazz.implement(List.class);
        final Type type = clazz.getInterfaces().iterator().next();
        assertTrue(type.getTypeName().contains(List.class.getSimpleName()));
    }

    @Test
    void getInterfaces() {
        assertTrue(clazz.getInterfaces().isEmpty());
    }
}