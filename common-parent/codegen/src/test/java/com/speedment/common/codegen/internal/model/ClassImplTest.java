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
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ClassImplTest extends AbstractTest<Class> {

    private final static String NAME = "A";

    public ClassImplTest() {
        super(() -> new ClassImpl(NAME),
                a -> a.setName("Z"),
                a -> a.javadoc(Javadoc.of("A")),
                a -> a.imports(List.class),
                a -> a.annotate(Integer.class),
                a -> a.add(Generic.of(Integer.class)),
                a -> a.add(Integer.class),
                a -> a.field("x", int.class),
                a -> a.add(Method.of("x", int.class)),
                a -> a.add(Initializer.of()),
                a -> a.add(Class.of("c")),
                Class::public_,
                a -> a.setSupertype(List.class),
                a -> a.add(Constructor.of())
        );
    }

    @Test
    void setSupertype() {
        instance().setSupertype(List.class);
        assertEquals(List.class, instance().getSupertype().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void getSupertype() {
        assertEquals(Optional.empty(), instance().getSupertype());
    }

    @Test
    void getConstructors() {
        assertTrue(instance().getConstructors().isEmpty());
    }
}