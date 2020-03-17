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

import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.NoSuchElementException;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class HasFieldsTest {

    private HasFields<Method> method;
    private Field field;

    @BeforeEach
    void setup() {
        method = Method.of("x", int.class);
        field  = Field.of("x", int.class);
    }

    @Test
    void add() {
        method.add(field);
        assertEquals(singletonList(field), method.getFields());
    }

    @Test
    void field() {
        final String name = "y";
        final Type type = int.class;
        method.field(name, type);
        final Field field = method.getFields().iterator().next();
        assertEquals(name, field.getName());
        assertEquals(type, field.getType());
    }

    @Test
    void testField() {
        final String name = "y";
        final String type = "int.class";
        method.field(name, type);
        final Field field = method.getFields().iterator().next();
        assertEquals(name, field.getName());
        assertEquals(type, field.getType().getTypeName());
    }

    @Test
    void constant() {
        final String name = "y";
        final Type type = int.class;
        final Number value = 1;
        method.constant(name, type, value);
        final Field field = method.getFields().iterator().next();
        final Value<?> actualValue = field.getValue().orElseThrow(NoSuchElementException::new);
        final Object val = actualValue.getValue();
        assertEquals(value, val);
    }

    @Test
    void testConstant() {
        final String name = "y";
        final Type type = int.class;
        final Number value = 1;
        method.constant(name, type, Value.ofNumber(value));
        final Field field = method.getFields().iterator().next();
        final Value<?> actualValue = field.getValue().orElseThrow(NoSuchElementException::new);
        final Object val = actualValue.getValue();
        assertEquals(value, val);
    }

    @Test
    void testConstant1() {
        final String name = "y";
        final Type type = String.class;
        final String value = "A";
        method.constant(name, type, value);
        final Field field = method.getFields().iterator().next();
        final Value<?> actualValue = field.getValue().orElseThrow(NoSuchElementException::new);
        final Object val = actualValue.getValue();
        assertEquals(value, val);
    }

    @Test
    void testConstant2() {
        final String name = "y";
        final Type type = boolean.class;
        final boolean value = true;
        method.constant(name, type, value);
        final Field field = method.getFields().iterator().next();
        final Value<?> actualValue = field.getValue().orElseThrow(NoSuchElementException::new);
        final Object val = actualValue.getValue();
        assertEquals(value, val);
    }

    @Test
    void addAllFields() {
        method.addAllFields(singletonList(field));
        assertEquals(singletonList(field), method.getFields());
    }

    @Test
    void getFields() {
        assertTrue(method.getFields().isEmpty());
    }
}