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
package com.speedment.common.codegen.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class EnumTest {

    private Enum instance;
    private EnumConstant enumConstant = EnumConstant.of("A");

    @BeforeEach
    void setup() {
        instance = Enum.of("E");
    }

    @Test
    void add() {
        instance.add(enumConstant);
        assertEquals(singletonList(enumConstant), instance.getConstants());
    }

    @Test
    void getConstants() {
        assertTrue(instance.getConstants().isEmpty());
    }

    @Test
    void of() {
        assertNotNull(Enum.of("E"));
    }
}