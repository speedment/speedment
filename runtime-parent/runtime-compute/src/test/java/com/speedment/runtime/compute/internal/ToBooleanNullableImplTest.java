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

import com.speedment.runtime.compute.ToBoolean;
import com.speedment.runtime.compute.ToBooleanNullable;
import com.speedment.runtime.compute.ToDoubleNullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Objects;

final class ToBooleanNullableImplTest {

    private final ToBooleanNullableImpl<String> instance = new ToBooleanNullableImpl<>(
        string -> string.length() > 2,
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

    @Test
    void apply() {
        assertNull(instance.apply(null));
        assertTrue(instance.apply("test"));
        assertFalse(instance.apply("ab"));
    }

    @Test
    void applyAsBoolean() {
        assertTrue(instance.applyAsBoolean("test"));
        assertFalse(instance.applyAsBoolean("ab"));
    }

    @Test
    void orThrow() {
        assertNotNull(instance.orThrow());
    }

    @Test
    void orElseGet() {
        final ToBoolean<String> toBoolean = instance.orElseGet(string -> true);

        assertNotNull(toBoolean);
        assertTrue(toBoolean.applyAsBoolean("three"));
        assertTrue(toBoolean.applyAsBoolean(null));
    }

    @Test
    void orElse() {
        final ToBoolean<String> toBoolean = instance.orElse(true);

        assertNotNull(toBoolean);
        assertTrue(toBoolean.applyAsBoolean("three"));
        assertTrue(toBoolean.applyAsBoolean(null));
    }

    @Test
    void mapToDoubleIfPresent() {
        final ToDoubleNullable<String> toDoubleNullable = instance
                .mapToDoubleIfPresent(bool -> bool ? 1 : 0);

        assertNotNull(toDoubleNullable);
        assertEquals(1, toDoubleNullable.applyAsDouble("three"));
        assertEquals(0, toDoubleNullable.applyAsDouble("1"));
        assertNull(toDoubleNullable.apply(null));
    }

    @Test
    void mapIfPresent() {
        final ToBooleanNullable<String> toBooleanNullable = instance.mapIfPresent(bool -> !bool);

        assertNotNull(toBooleanNullable);
        assertTrue(toBooleanNullable.applyAsBoolean("1"));
        assertFalse(toBooleanNullable.applyAsBoolean("three"));
        assertNull(toBooleanNullable.apply(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void hash(String input) {
        assertEquals(2, instance.hash(null));
        assertEquals(1, instance.hash(input));
        assertEquals(0, instance.hash("a"));
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
        final ToBooleanNullable<String> copy = instance;
        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final ToBooleanNullable<String> another = new ToBooleanNullableImpl<>(
            instance.inner(),
            instance.isNullPredicate()
        );

        final ToBooleanNullable<String> originalSame = new ToBooleanNullableImpl<>(
            instance.inner(),
            Objects::isNull
        );

        final ToBooleanNullable<String> isNullSame = new ToBooleanNullableImpl<>(
            string -> string.length() > 2,
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
