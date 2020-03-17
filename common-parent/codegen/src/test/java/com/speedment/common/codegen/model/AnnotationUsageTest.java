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

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

final class AnnotationUsageTest {

    private static final String A = "A";
    private static final String B = "B";
    private AnnotationUsage instance;

    @BeforeEach
    void setup() {
        instance = AnnotationUsage.of(Integer.class);
    }

    @Test
    void put() {
        instance.put(A, B);
        final Map.Entry<String, Value<?>> entry = instance.getValues().iterator().next();
        assertEquals(A, entry.getKey());
        assertEquals(B, entry.getValue().getValue());
    }

    @Test
    void testPut() {
        instance.put(A, Value.ofText(B));
        final Map.Entry<String, Value<?>> entry = instance.getValues().iterator().next();
        assertEquals(A, entry.getKey());
        assertEquals(B, entry.getValue().getValue());
    }

    @Test
    void testPut1() {
        instance.put(A, 1);
        final Map.Entry<String, Value<?>> entry = instance.getValues().iterator().next();
        assertEquals(A, entry.getKey());
        assertEquals(1, entry.getValue().getValue());
    }

    @Test
    void getValues() {
        assertTrue(instance.getValues().isEmpty());
    }

    @Test
    void of() {
        assertNotNull(AnnotationUsage.of(Integer.class));
    }
}