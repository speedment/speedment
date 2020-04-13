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
package com.speedment.runtime.compute;

import static com.speedment.runtime.compute.expression.ExpressionType.LONG_NULLABLE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.compute.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Function;

final class ToLongNullableTest {

    private static final ToLongNullable<String> DEFAULT_NULLABLE = string -> string == null ?
            null : (long) string.length();

    @ParameterizedTest
    @ValueSource(strings = "test")
    void of(String input) {
        final Function<String, Long> function = string -> (long) string.length();
        final ToLongNullable<String> fromFunction = ToLongNullable.of(function);

        assertNotNull(fromFunction);
        assertEquals(function.apply(input), fromFunction.apply(input));

        final ToLongNullable<String> raw = DEFAULT_NULLABLE;
        final ToLongNullable<String> fromRaw = ToLongNullable.of(raw);

        assertNotNull(fromFunction);
        assertEquals(raw.apply(input), fromRaw.apply(input));
    }

    @Test
    void expressionType() {
        final ToLongNullable<String> toLongNullable = string -> null;

        Assertions.assertEquals(LONG_NULLABLE, toLongNullable.expressionType());
    }

    @Test
    void orThrow() {
        final ToLongNullable<String> nullValue = string -> null;
        assertDoesNotThrow(nullValue::orThrow);

        final ToLong<String> toFloat = nullValue.orThrow();
        assertThrows(NullPointerException.class, () -> toFloat.applyAsLong(""));

        assertDoesNotThrow(DEFAULT_NULLABLE::orThrow);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "foo", "test"})
    void orElseGet(String input) {
        final ToLongNullable<String> nullValue = string -> null;
        ToLong<String> toFloat = nullValue.orElseGet(String::length);

        assertEquals(input.length(), toFloat.applyAsLong(input));

        toFloat = DEFAULT_NULLABLE.orElseGet(String::length);

        assertEquals(input.length(), toFloat.applyAsLong(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void orElse(String input) {
        final ToLongNullable<String> nullValue = string -> null;
        ToLong<String> toFloat = nullValue.orElse(0L);

        assertEquals(0d, toFloat.applyAsLong(input));

        toFloat = DEFAULT_NULLABLE.orElse( 0L);

        assertEquals(input.length(), toFloat.applyAsLong(input));
    }

    @Test
    void abs() {
        assertNotNull(DEFAULT_NULLABLE.abs());
    }

    @Test
    void negate() {
        assertNotNull(DEFAULT_NULLABLE.negate());
    }

    @Test
    void sign() {
        assertNotNull(DEFAULT_NULLABLE.sign());
    }

    @Test
    void sqrt() {
        assertNotNull(DEFAULT_NULLABLE.sqrt());
    }

    @Test
    void mapToDoubleIfPresent() {
        final ToDoubleNullable<String> toDoubleNullable = DEFAULT_NULLABLE
                .mapToDoubleIfPresent(l -> 1);

        assertNotNull(toDoubleNullable);
        assertEquals(1, toDoubleNullable.applyAsDouble("three"));

        assertNull(toDoubleNullable.apply(null));
        assertEquals(1, toDoubleNullable.apply("test"));

        final ToDouble<String> orElseGet = toDoubleNullable.orElseGet(d -> 0);
        assertEquals(1, orElseGet.applyAsDouble("test"));
        assertEquals(0, orElseGet.applyAsDouble(null));

        final ToDouble<String> orElse = toDoubleNullable.orElse((double) 0);
        assertEquals(1, orElse.applyAsDouble("test"));
        assertEquals(0, orElse.applyAsDouble(null));

        assertTrue(toDoubleNullable.isNotNull("test"));
        assertFalse(toDoubleNullable.isNotNull(null));

        assertTrue(toDoubleNullable.isNull(null));
        assertFalse(toDoubleNullable.isNull("test"));
    }
    
    @Test
    void mapIfPresent() {
        final ToLongNullable<String> toLongNullable = DEFAULT_NULLABLE.mapIfPresent(l -> 1);

        assertNotNull(toLongNullable);
        assertEquals(1, toLongNullable.applyAsLong("test"));

        assertNull(toLongNullable.apply(null));
        assertEquals(1, toLongNullable.apply("test"));

        final ToLong<String> orElseGet = toLongNullable.orElseGet(l -> 0);
        assertEquals(1, orElseGet.applyAsLong("test"));
        assertEquals(0, orElseGet.applyAsLong(null));

        final ToLong<String> orElse = toLongNullable.orElse((long) 0);
        assertEquals(1, orElse.applyAsLong("test"));
        assertEquals(0, orElse.applyAsLong(null));

        assertTrue(toLongNullable.isNotNull("test"));
        assertFalse(toLongNullable.isNotNull(null));

        assertTrue(toLongNullable.isNull(null));
        assertFalse(toLongNullable.isNull("test"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void hash(String input) {
        final ToLongNullable<String> nullValue = string -> null;
        assertEquals(0, nullValue.hash(input));

        assertNotEquals(0, DEFAULT_NULLABLE.hash(input));
    }

    @Test
    void compare() {
        final ToLongNullable<String> raw = string -> string.length() > 4 ? 1L : null;

        final Pair<String, String> nullNull = new Pair<>("foo", "bar");
        final Pair<String, String> nullHas = new Pair<>("foo", "longer");
        final Pair<String, String> hasNull = new Pair<>("longer", "foo");
        final Pair<String, String> hasHas = new Pair<>("longer", "longer");

        assertEquals(0, raw.compare(nullNull.getFirst(), nullNull.getSecond()));
        assertEquals(1, raw.compare(nullHas.getFirst(), nullHas.getSecond()));
        assertEquals(-1, raw.compare(hasNull.getFirst(), hasNull.getSecond()));
        assertEquals(0, raw.compare(hasHas.getFirst(), hasHas.getSecond()));
    }

    @Test
    void compose() {
        assertThrows(NullPointerException.class, () -> DEFAULT_NULLABLE.compose(null));

        final ToLongNullable<Boolean> composed = DEFAULT_NULLABLE.compose(Object::toString);

        assertNotNull(composed);
    }
}
