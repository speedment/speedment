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
import com.speedment.runtime.compute.ToShort;
import com.speedment.runtime.compute.ToShortNullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Objects;

final class ToShortNullableImplTest {

    private final ToShortNullableImpl<String> instance = new ToShortNullableImpl<>(
            string -> (short) string.length(),
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
        assertEquals((short) input.length(), instance.apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void applyAsInt(String input) {
        assertEquals(input.length(), instance.applyAsShort(input));
    }

    @Test
    void orThrow() {
        assertNotNull(instance.orThrow());
    }


    @Test
    void orElseGet() {
        final ToShort<String> toShort = instance.orElseGet(string -> (short) 0);

        assertNotNull(toShort);
        assertEquals("three".length(), toShort.applyAsShort("three"));
        assertEquals(0, toShort.applyAsShort(null));
    }

    @Test
    void orElse() {
        final ToShort<String> toShort = instance.orElse((short) 0);

        assertNotNull(toShort);
        assertEquals("three".length(), toShort.applyAsShort("three"));
        assertEquals(0, toShort.applyAsShort(null));
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
        final ToShortNullable<String> toShortNullable = instance.mapIfPresent(i -> (short) 0);

        assertNotNull(toShortNullable);
        assertEquals(0, toShortNullable.applyAsShort("1"));
        assertNull(toShortNullable.apply(null));
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
        assertEquals("test".length() - "a".length(), instance.compare("test", "a"));
        assertEquals("a".length() - "test".length(), instance.compare("a", "test"));
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
        final ToShortNullable<String> copy = instance;
        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToShortNullable<String> another = new ToShortNullableImpl<>(
            instance.inner(),
            instance.isNullPredicate()
        );

        final ToShortNullable<String> originalSame = new ToShortNullableImpl<>(
            instance.inner(),
            Objects::isNull
        );

        final ToShortNullable<String> isNullSame = new ToShortNullableImpl<>(
            string -> (short) string.length(),
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
