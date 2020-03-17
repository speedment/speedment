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

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Enum;
import com.speedment.common.codegen.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class EnumImplTest extends AbstractTest<Enum> {

    private final static String NAME = "A";

    public EnumImplTest() {
        super(() -> new EnumImpl(NAME),
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
                Enum::public_,
                a -> a.add(Constructor.of()),
                a -> a.add(EnumConstant.of("Z"))
        );
    }

    @Test
    void getConstants() {
        assertTrue(instance().getConstants().isEmpty());
    }

    @Test
    void getConstructors() {
        assertTrue(instance().getConstructors().isEmpty());
    }

}