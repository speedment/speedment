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
package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.model.Initializer;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.model.value.AnonymousValue;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class AnonymousValueImplTest extends AbstractValueTest<Type, AnonymousValue> {

    private final static String NAME = "A";

    public AnonymousValueImplTest() {
        super(AnonymousValueImpl::new,
                a -> a.add(Value.ofNumber(1)),
                a -> a.imports(List.class),
                a -> a.add(Integer.class),
                a -> a.field("x", int.class),
                a -> a.add(Method.of("x", int.class)),
                a -> a.add(Initializer.of()),
                a -> a.add(com.speedment.common.codegen.model.Class.of("c")),
                a -> a.setValue(long.class)
        );
    }

    @Test
    void getValue() {
        assertNull(instance().getValue());
    }

    @Test
    void getValues() {
        assertTrue(instance().getValues().isEmpty());
    }

    @Test
    void getImports() {
        assertTrue(instance().getImports().isEmpty());
    }

    @Test
    void getTypeParameters() {
        assertTrue(instance().getTypeParameters().isEmpty());
    }

    @Test
    void getFields() {
        assertTrue(instance().getFields().isEmpty());
    }

    @Test
    void getMethods() {
        assertTrue(instance().getMethods().isEmpty());
    }

    @Test
    void getInitializers() {
        assertTrue(instance().getInitializers().isEmpty());
    }

    @Test
    void getClasses() {
        assertTrue(instance().getClasses().isEmpty());
    }

    @Test
    void setValue() {
        final Type type = Double.class;
        instance().setValue(type);
        assertEquals(type, instance().getValue());
    }
}