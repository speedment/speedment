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

import static com.speedment.runtime.compute.expression.ExpressionType.FLOAT_NULLABLE;
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

final class ToFloatNullableTest {

    private static final ToFloatNullable<String> DEFAULT_NULLABLE = string -> string == null ?
            null : (float) string.length();

    @ParameterizedTest
    @ValueSource(strings = "test")
    void of(String input) {
        final Function<String, Float> function = string -> (float) string.length();
        final ToFloatNullable<String> fromFunction = ToFloatNullable.of(function);

        assertNotNull(fromFunction);
        assertEquals(function.apply(input), fromFunction.apply(input));

        final ToFloatNullable<String> raw = DEFAULT_NULLABLE;
        final ToFloatNullable<String> fromRaw = ToFloatNullable.of(raw);

        assertNotNull(fromFunction);
        assertEquals(raw.apply(input), fromRaw.apply(input));
    }

    @Test
    void expressionType() {
        Assertions.assertEquals(FLOAT_NULLABLE, DEFAULT_NULLABLE.expressionType());
    }

    @Test
    void orThrow() {
        final ToFloatNullable<String> nullValue = string -> null;
        assertDoesNotThrow(nullValue::orThrow);

        final ToFloat<String> toFloat = nullValue.orThrow();
        assertThrows(NullPointerException.class, () -> toFloat.applyAsFloat(""));

        assertDoesNotThrow(DEFAULT_NULLABLE::orThrow);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "foo", "test"})
    void orElseGet(String input) {
        final ToFloatNullable<String> nullValue = string -> null;
        ToFloat<String> toFloat = nullValue.orElseGet(String::length);

        assertEquals(input.length(), toFloat.applyAsFloat(input));

        toFloat = DEFAULT_NULLABLE.orElseGet(String::length);

        assertEquals(input.length(), toFloat.applyAsFloat(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void orElse(String input) {
        final ToFloatNullable<String> nullValue = string -> null;
        ToFloat<String> toFloat = nullValue.orElse(0f);

        assertEquals(0d, toFloat.applyAsFloat(input));

        toFloat = DEFAULT_NULLABLE.orElse( 0f);

        assertEquals(input.length(), toFloat.applyAsFloat(input));
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
                .mapToDoubleIfPresent(f -> 1);

        assertNotNull(toDoubleNullable);
        assertEquals(1, toDoubleNullable.applyAsDouble("test"));

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
        final ToFloatNullable<String> toFloatNullable = DEFAULT_NULLABLE.mapIfPresent(f -> 1);

        assertNotNull(toFloatNullable);
        assertEquals(1, toFloatNullable.applyAsFloat("test"));

        assertNull(toFloatNullable.apply(null));
        assertEquals(1, toFloatNullable.apply("test"));

        final ToFloat<String> orElseGet = toFloatNullable.orElseGet(d -> 0);
        assertEquals(1, orElseGet.applyAsFloat("test"));
        assertEquals(0, orElseGet.applyAsFloat(null));

        final ToFloat<String> orElse = toFloatNullable.orElse((float) 0);
        assertEquals(1, orElse.applyAsFloat("test"));
        assertEquals(0, orElse.applyAsFloat(null));

        assertTrue(toFloatNullable.isNotNull("test"));
        assertFalse(toFloatNullable.isNotNull(null));

        assertTrue(toFloatNullable.isNull(null));
        assertFalse(toFloatNullable.isNull("test"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void hash(String input) {
        assertEquals(0, DEFAULT_NULLABLE.hash(null));
        assertEquals(0, DEFAULT_NULLABLE.hash(""));
        assertNotEquals(0, DEFAULT_NULLABLE.hash(input));
    }

    @Test
    void compare() {
        final ToFloatNullable<String> raw = string -> string.length() > 4 ? 1f : null;

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

        final ToFloatNullable<Boolean> composed = DEFAULT_NULLABLE.compose(Object::toString);

        assertNotNull(composed);
    }
}
