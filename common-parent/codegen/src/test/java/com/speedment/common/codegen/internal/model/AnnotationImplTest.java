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

import com.speedment.common.codegen.model.Annotation;
import com.speedment.common.codegen.model.Javadoc;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class AnnotationImplTest extends AbstractTest<Annotation> {

    private final static String NAME = "A";
    private final static String OTHER_NAME = "B";

    public AnnotationImplTest() {
        super(() -> new AnnotationImpl(NAME),
                a -> a.setName("Z"),
                a -> a.javadoc(Javadoc.of("A")),
                a -> a.annotate(Integer.class),
                a -> a.field("x", int.class),
                a -> a.imports(List.class),
                Annotation::public_
        );
    }

    @Test
    void setName() {
        instance().setName(OTHER_NAME);
        assertEquals(OTHER_NAME, instance().getName());
    }

    @Test
    void getName() {
        assertEquals(NAME, instance().getName());
    }

    @Test
    void getFields() {
        assertTrue(instance().getFields().isEmpty());
    }

    @Test
    void set() {
        instance().set(Javadoc.of("A"));
        assertEquals("A", instance().getJavadoc().orElseThrow(NoSuchElementException::new).getText());
    }

    @Test
    void getJavadoc() {
        assertEquals(Optional.empty(), instance().getJavadoc());
    }

    @Test
    void getImports() {
        assertTrue(instance().getFields().isEmpty());
    }

    @Test
    void getModifiers() {
        assertTrue(instance().getFields().isEmpty());
    }

    @Test
    void getAnnotations() {
        assertTrue(instance().getFields().isEmpty());
    }

}