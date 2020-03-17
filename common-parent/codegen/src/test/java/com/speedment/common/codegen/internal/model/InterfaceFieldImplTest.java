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
package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Enum;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class InterfaceFieldImplTest extends AbstractTest<InterfaceField> {

    private static final String NAME = "A";
    private static final String OTHER_NAME = "B";

    public InterfaceFieldImplTest() {
        super(() -> new InterfaceFieldImpl(Field.of(NAME, int.class)),
                a -> a.setParent(Interface.of("Foo")),
                a -> a.setName("Z"),
                a -> a.javadoc(Javadoc.of("A")),
                a -> a.imports(List.class),
                a -> a.annotate(Integer.class)
        );
    }

    @Test
    void setParent() {
        final Interface i = Interface.of("A");
        instance().setParent(i);
        assertEquals(i, instance().getParent().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void getParent() {
        assertFalse(instance().getParent().isPresent());
    }

    @Test
    void getName() {
        assertEquals(NAME, instance().getName());
    }

    @Test
    void getType() {
        assertEquals(int.class, instance().getType());
    }

    @Test
    void getModifiers() {
        assertTrue(instance().getModifiers().isEmpty());
    }

    @Test
    void getJavadoc() {
        assertEquals(Optional.empty(), instance().getJavadoc());
    }

    @Test
    void getValue() {
        assertEquals(Optional.empty(), instance().getValue());
    }

    @Test
    void getAnnotations() {
        assertTrue(instance().getModifiers().isEmpty());
    }

    @Test
    void getImports() {
        assertTrue(instance().getModifiers().isEmpty());
    }

    @Test
    void setName() {
        instance().setName(OTHER_NAME);
        assertEquals(OTHER_NAME, instance().getName());
    }

    @Test
    void set() {
        final Javadoc javadoc = Javadoc.of("A");
        instance().set(javadoc);
        assertEquals(javadoc, instance().getJavadoc().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void testSet() {
        final Value<?> value = Value.ofNumber(1);
        instance().set(value);
        assertEquals(value, instance().getValue().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void testSet1() {
        final Type type = Double.class;
        instance().set(type);
        assertEquals(type, instance().getType());
    }
}