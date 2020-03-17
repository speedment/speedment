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

import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToByteNullable;
import com.speedment.runtime.compute.ToDoubleNullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Objects;

final class ToByteNullableImplTest {

    private final ToByteNullableImpl<String> instance = new ToByteNullableImpl<>(
            string -> string.getBytes()[0],
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
        assertEquals(input.getBytes()[0], instance.apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void applyAsByte(String input) {
        assertEquals(input.getBytes()[0], instance.applyAsByte(input));
    }

    @Test
    void orThrow() {
        assertNotNull(instance.orThrow());
    }

    @Test
    void orElseGet() {
        ToByte<String> toByte = instance.orElseGet(string -> (byte) 0);

        assertNotNull(toByte);
        assertEquals("tree".getBytes()[0], toByte.applyAsByte("three"));
        assertEquals((byte) 0, toByte.applyAsByte(null));
    }

    @Test
    void orElse() {
        ToByte<String> toByte = instance.orElse((byte) 0);

        assertNotNull(toByte);
        assertEquals("tree".getBytes()[0], toByte.applyAsByte("three"));
        assertEquals((byte) 0, toByte.applyAsByte(null));
    }

    @Test
    void mapToDoubleIfPresent() {
        ToDoubleNullable<String> toDoubleNullable = instance
                .mapToDoubleIfPresent(b -> 1);

        assertNotNull(toDoubleNullable);
        assertEquals(1, toDoubleNullable.applyAsDouble("three"));
        assertNull(toDoubleNullable.apply(null));
    }

    @Test
    void mapIfPresent() {
        ToByteNullable<String> toByteNullable = instance.mapIfPresent(b -> (byte) 0);

        assertNotNull(toByteNullable);
        assertEquals(0, toByteNullable.applyAsByte("1"));
        assertNull(toByteNullable.apply(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void hash(String input) {
        assertEquals(256, instance.hash(null));
        assertNotEquals(0, instance.hash(input));
    }

    @Test
    void compare() {
        assertEquals(0, instance.compare(null, null));
        assertEquals(1, instance.compare(null, "test"));
        assertEquals(-1, instance.compare("test", null));
        assertEquals(0, instance.compare("test", "test"));
        assertEquals(Byte.compare("test".getBytes()[0], "a".getBytes()[0]),
                instance.compare("test", "a"));
        assertEquals(Byte.compare("a".getBytes()[0], "test".getBytes()[0]),
                instance.compare("a", "test"));
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
        final ToByteNullable<String> copy = instance;
        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToByteNullable<String> another = new ToByteNullableImpl<>(
            instance.inner(),
            instance.isNullPredicate()
        );

        final ToByteNullable<String> originalSame = new ToByteNullableImpl<>(
            instance.inner(),
            Objects::isNull
        );

        final ToByteNullable<String> isNullSame = new ToByteNullableImpl<>(
            string -> string.getBytes()[0],
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
