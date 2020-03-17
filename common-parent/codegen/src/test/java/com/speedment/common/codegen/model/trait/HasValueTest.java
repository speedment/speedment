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

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class HasValueTest {

    private HasValue<Field> field;

    @BeforeEach
    void setup() {
        field = Field.of("x", int.class);
    }

    @Test
    void set() {
        field.set(1);
        assertEquals(1, field.getValue().orElseThrow(NoSuchElementException::new).getValue());
    }

    @Test
    void testSet() {
        field.set("A");
        assertEquals("A", field.getValue().orElseThrow(NoSuchElementException::new).getValue());
    }

    @Test
    void testSet1() {
        field.set(true);
        assertEquals(true, field.getValue().orElseThrow(NoSuchElementException::new).getValue());
    }

    @Test
    void testSet2() {
        field.set(Value.ofReference("x"));
        assertEquals("x", field.getValue().orElseThrow(NoSuchElementException::new).getValue());
    }

    @Test
    void getValue() {
        assertEquals(Optional.empty(), field.getValue());
    }
}