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

import com.speedment.common.codegen.model.value.NumberValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class EnumConstantTest {

    private EnumConstant enumConstant;

    @BeforeEach
    void setup() {
         enumConstant = EnumConstant.of("A");
    }

    @Test
    void add() {
        final NumberValue value = Value.ofNumber(1);
        enumConstant.add(value);
        assertEquals(singletonList(value), enumConstant.getValues());
    }

    @Test
    void getValues() {
        assertTrue(enumConstant.getValues().isEmpty());
    }

    @Test
    void of() {
        assertNotNull(EnumConstant.of("A"));
    }
}