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
package com.speedment.plugins.enums.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class GeneratedEnumTypeTest {

    private static final List<String> ENUM_CONSTANTS = Arrays.asList("A", "B", "C");
    private static final String TYPE_NAME = "a.b.c.Foo";
    private GeneratedEnumType instance;

    @BeforeEach
    void setup() {
        instance = new GeneratedEnumType(TYPE_NAME, ENUM_CONSTANTS);
    }

    @Test
    void getEnumConstants() {
        assertEquals(ENUM_CONSTANTS, instance.getEnumConstants());
    }

    @Test
    void getTypeName() {
        assertEquals(TYPE_NAME, instance.getTypeName());
    }

    @Test
    void testEquals() {
        final List<String> otherConstants = new ArrayList<>(ENUM_CONSTANTS);
        otherConstants.add("Z");
        final GeneratedEnumType other = new GeneratedEnumType(TYPE_NAME, otherConstants);
        assertEquals(instance, instance);
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));
        assertFalse(instance.equals(Integer.class));
        assertNotEquals(instance, other);
        assertNotEquals(other, instance);
    }

    @Test
    void testEquals2() {
        final GeneratedEnumType other = new GeneratedEnumType(TYPE_NAME + "Z", ENUM_CONSTANTS);
        assertNotEquals(instance, other);
        assertNotEquals(other, instance);
    }

    @Test
    void testHashCode() {
        final List<String> otherConstants = new ArrayList<>(ENUM_CONSTANTS);
        otherConstants.add("Z");
        final GeneratedEnumType other = new GeneratedEnumType(TYPE_NAME, otherConstants);
        assertNotEquals(other.hashCode(), instance.hashCode());
    }
}