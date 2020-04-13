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
package com.speedment.runtime.compute.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.ToFloat;
import com.speedment.runtime.compute.ToFloatNullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Objects;

final class ToFloatNullableImplTest {

    private final ToFloatNullableImpl<String> instance = new ToFloatNullableImpl<>(
            String::length,
            Objects::isNull
    );

    @Test
    void inner() {
        assertNotNull(instance.inner());
    }

    @Test
    void isNullPredicate() {
        assertNotNull(instance.isNullPredicate());
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void apply(String input) {
        assertNull(instance.apply(null));
        assertEquals(input.length(), instance.apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void applyAsFloat(String input) {
        assertEquals(input.length(), instance.applyAsFloat(input));
    }

    @Test
    void orThrow() {
        assertNotNull(instance.orThrow());
    }


    @Test
    void orElseGet() {
        final ToFloat<String> toFloat = instance.orElseGet(string -> 0);

        assertNotNull(toFloat);
        assertEquals("three".length(), toFloat.applyAsFloat("three"));
        assertEquals(0, toFloat.applyAsFloat(null));
    }

    @Test
    void orElse() {
        final ToFloat<String> toFloat = instance.orElse((float) 0);

        assertNotNull(toFloat);
        assertEquals("three".length(), toFloat.applyAsFloat("three"));
        assertEquals(0, toFloat.applyAsFloat(null));
    }

    @Test
    void mapToDoubleIfPresent() {
        final ToDoubleNullable<String> toDoubleNullable = instance
                .mapToDoubleIfPresent(f -> 1);

        assertNotNull(toDoubleNullable);
        assertEquals(1, toDoubleNullable.applyAsDouble("three"));
        assertNull(toDoubleNullable.apply(null));
    }

    @Test
    void mapIfPresent() {
        final ToFloatNullable<String> toFloatNullable = instance.mapIfPresent(f -> 0);

        assertNotNull(toFloatNullable);
        assertEquals(0, toFloatNullable.applyAsFloat("1"));
        assertNull(toFloatNullable.apply(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void hash(String input) {
        assertTrue(instance.hash(null) > 0);
        assertNotEquals(0, instance.hash(input));
    }

    @Test
    void compare() {
        assertEquals(0, instance.compare(null, null));
        assertEquals(1, instance.compare(null, "test"));
        assertEquals(-1, instance.compare("test", null));
        assertEquals(0, instance.compare("test", "test"));
        assertEquals(1, instance.compare("test", "a"));
        assertEquals(-1, instance.compare("a", "test"));
    }

    @Test
    void isNull() {
        assertTrue(instance.isNull(null));
        assertFalse(instance.isNull("test"));
    }

    @Test
    void isNotNull() {
        assertTrue(instance.isNotNull("test"));
        assertFalse(instance.isNotNull(null));
    }

    @Test
    void testEquals() {
        final ToFloatNullable<String> copy = instance;
        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToFloatNullable<String> another = new ToFloatNullableImpl<>(
            instance.inner(),
            instance.isNullPredicate()
        );

        final ToFloatNullable<String> originalSame = new ToFloatNullableImpl<>(
            instance.inner(),
            Objects::isNull
        );

        final ToFloatNullable<String> isNullSame = new ToFloatNullableImpl<>(
            String::length,
            instance.isNullPredicate()
        );

        assertTrue(instance.equals(another));
        assertFalse(instance.equals(originalSame));
        assertFalse(instance.equals(isNullSame));
    }

    @Test
    void testHashCode() {
        assertNotEquals(0, instance.hashCode());
    }
}
