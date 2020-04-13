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
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToIntNullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Objects;

final class ToIntNullableImplTest {

    private final ToIntNullableImpl<String> instance = new ToIntNullableImpl<>(
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
    void applyAsInt(String input) {
        assertEquals(input.length(), instance.applyAsInt(input));
    }

    @Test
    void orThrow() {
        assertNotNull(instance.orThrow());
    }


    @Test
    void orElseGet() {
        final ToInt<String> toInt = instance.orElseGet(string -> 0);

        assertNotNull(toInt);
        assertEquals("three".length(), toInt.applyAsInt("three"));
        assertEquals(0, toInt.applyAsInt(null));
    }

    @Test
    void orElse() {
        final ToInt<String> toInt = instance.orElse(0);

        assertNotNull(toInt);
        assertEquals("three".length(), toInt.applyAsInt("three"));
        assertEquals(0, toInt.applyAsInt(null));
    }

    @Test
    void mapToDoubleIfPresent() {
        final ToDoubleNullable<String> toDoubleNullable = instance
                .mapToDoubleIfPresent(i -> 1);

        assertNotNull(toDoubleNullable);
        assertEquals(1, toDoubleNullable.applyAsDouble("three"));
        assertNull(toDoubleNullable.apply(null));
    }

    @Test
    void mapIfPresent() {
        final ToIntNullable<String> toIntNullable = instance.mapIfPresent(i -> 0);

        assertNotNull(toIntNullable);
        assertEquals(0, toIntNullable.applyAsInt("1"));
        assertNull(toIntNullable.apply(null));
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
        final ToIntNullable<String> copy = instance;
        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToIntNullable<String> another = new ToIntNullableImpl<>(
            instance.inner(),
            instance.isNullPredicate()
        );

        final ToIntNullable<String> originalSame = new ToIntNullableImpl<>(
            instance.inner(),
            Objects::isNull
        );

        final ToIntNullable<String> isNullSame = new ToIntNullableImpl<>(
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
