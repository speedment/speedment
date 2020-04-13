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
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class InterfaceMethodImplTest extends AbstractTest<InterfaceMethod> {

    private final static String NAME = "A";
    private final static Type TYPE = int.class;

    public InterfaceMethodImplTest() {
        super(() -> new InterfaceMethodImpl(Method.of(NAME, TYPE)),
                a -> a.setName("Z"),
                a -> a.setParent(Interface.of("C")),
                a -> a.javadoc(Javadoc.of("A")),
                a -> a.imports(List.class),
                a -> a.annotate(Integer.class),
                a -> a.add(Generic.of(Integer.class)),
                a -> a.add(Integer.class),
                a -> a.field("x", int.class),
                a -> a.add(NullPointerException.class),
                a -> a.set(long.class),
                a -> a.add("a++;"),
                InterfaceMethod::default_
        );
    }

    @Test
    void setParent() {
        final Interface inter = Interface.of("V");
        instance().setParent(inter);
        assertEquals(inter, instance().getParent().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void getParent() {
        assertEquals(Optional.empty(), instance().getParent());
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
    void getFields() {
        assertTrue(instance().getFields().isEmpty());
    }

    @Test
    void getCode() {
        assertTrue(instance().getCode().isEmpty());
    }

    @Test
    void getModifiers() {
        assertTrue(instance().getModifiers().isEmpty());
    }

    @Test
    void getJavadoc() {
        assertFalse(instance().getJavadoc().isPresent());
    }

    @Test
    void getAnnotations() {
        assertTrue(instance().getAnnotations().isEmpty());
    }

    @Test
    void getImports() {
        assertTrue(instance().getImports().isEmpty());
    }

    @Test
    void setName() {
        final String name = "X";
        instance().setName(name);
        assertEquals(name, instance().getName());
    }

    @Test
    void set() {
        final Javadoc javadoc = Javadoc.of("A");
        instance().set(javadoc);
        assertEquals(javadoc, instance().getJavadoc().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void getGenerics() {
        assertTrue(instance().getGenerics().isEmpty());
    }

    @Test
    void testSet() {
        final Type type = boolean.class;
        instance().set(type);
        assertEquals(type, instance().getType());
    }

    @Test
    void getExceptions() {
        assertTrue(instance().getImports().isEmpty());
    }
}