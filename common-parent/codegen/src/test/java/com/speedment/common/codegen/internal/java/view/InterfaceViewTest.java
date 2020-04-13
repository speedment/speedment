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
package com.speedment.common.codegen.internal.java.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.provider.StandardJavaGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class InterfaceViewTest {

    private static final InterfaceView INSTANCE = new InterfaceView();
    private static final Generator GENERATOR = new StandardJavaGenerator();

    @Test
    void renderDeclarationType() {
        assertEquals("interface ", INSTANCE.renderDeclarationType());
    }

    @Test
    void extendsOrImplementsInterfaces() {
        assertEquals("extends ", INSTANCE.extendsOrImplementsInterfaces());
    }

    @Test
    void renderSupertype() {
        assertEquals("", INSTANCE.renderSupertype(GENERATOR, Interface.of("Foo")));
    }

    @Test
    void wrapMethod() {
        assertNotNull(INSTANCE.wrapMethod(Method.of("x", int.class)));
    }

    @Test
    void wrapField() {
        assertNotNull(INSTANCE.wrapField(Field.of("x", int.class)));
    }

    @Test
    void renderConstructors() {
        assertEquals("", INSTANCE.renderConstructors(GENERATOR, Interface.of("A")));
    }
}