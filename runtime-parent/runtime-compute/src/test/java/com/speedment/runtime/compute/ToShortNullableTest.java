/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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

import static com.speedment.runtime.compute.expression.ExpressionType.SHORT_NULLABLE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.speedment.runtime.compute.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Function;

final class ToShortNullableTest {

    private static final ToShortNullable<String> DEFAULT_NULLABLE = string -> (short) string.length();

    @ParameterizedTest
    @ValueSource(strings = "test")
    void of(String input) {
        Function<String, Short> function = string -> (short) string.length();
        ToShortNullable<String> fromFunction = ToShortNullable.of(function);

        assertNotNull(fromFunction);
        assertEquals(function.apply(input), fromFunction.apply(input));

        ToShortNullable<String> raw = DEFAULT_NULLABLE;
        ToShortNullable<String> fromRaw = ToShortNullable.of(raw);

        assertNotNull(fromFunction);
        assertEquals(raw.apply(input), fromRaw.apply(input));
    }

    @Test
    void expressionType() {
        ToShortNullable<String> toLongNullable = string -> null;

        Assertions.assertEquals(SHORT_NULLABLE, toLongNullable.expressionType());
    }

    @Test
    void orThrow() {
        ToShortNullable<String> nullValue = string -> null;
        assertDoesNotThrow(nullValue::orThrow);

        ToShort<String> toFloat = nullValue.orThrow();
        assertThrows(NullPointerException.class, () -> toFloat.applyAsShort(""));

        assertDoesNotThrow(DEFAULT_NULLABLE::orThrow);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "foo", "test"})
    void orElseGet(String input) {
        ToShortNullable<String> nullValue = string -> null;
        ToShort<String> toFloat = nullValue.orElseGet(string -> (short) string.length());

        assertEquals(input.length(), toFloat.applyAsShort(input));

        toFloat = DEFAULT_NULLABLE.orElseGet(string -> (short) string.length());

        assertEquals(input.length(), toFloat.applyAsShort(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void orElse(String input) {
        ToShortNullable<String> nullValue = string -> null;
        ToShort<String> toFloat = nullValue.orElse((short) 0);

        assertEquals(0d, toFloat.applyAsShort(input));

        toFloat = DEFAULT_NULLABLE.orElse((short) 0);

        assertEquals(input.length(), toFloat.applyAsShort(input));
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
        ToDoubleNullable<String> toDoubleNullable = DEFAULT_NULLABLE
                .mapToDoubleIfPresent(c -> 1);

        assertNotNull(toDoubleNullable);
        assertEquals(1, toDoubleNullable.applyAsDouble("three"));
    }
    
    @Test
    void mapIfPresent() {
        ToShortNullable<String> toFloatNullable = DEFAULT_NULLABLE.mapIfPresent(f -> f);

        assertNotNull(toFloatNullable);
        assertEquals("1".length(), toFloatNullable.applyAsShort("1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void hash(String input) {
        ToShortNullable<String> nullValue = string -> null;
        assertEquals(0, nullValue.hash(input));

        assertNotEquals(0, DEFAULT_NULLABLE.hash(input));
    }

    @Test
    void compare() {
        ToShortNullable<String> raw = string -> string.length() > 4 ? (short) 1 : null;

        Pair<String, String> nullNull = new Pair<>("foo", "bar");
        Pair<String, String> nullHas = new Pair<>("foo", "longer");
        Pair<String, String> hasNull = new Pair<>("longer", "foo");
        Pair<String, String> hasHas = new Pair<>("longer", "longer");

        assertEquals(0, raw.compare(nullNull.getFirst(), nullNull.getSecond()));
        assertEquals(1, raw.compare(nullHas.getFirst(), nullHas.getSecond()));
        assertEquals(-1, raw.compare(hasNull.getFirst(), hasNull.getSecond()));
        assertEquals(0, raw.compare(hasHas.getFirst(), hasHas.getSecond()));
    }

    @Test
    void compose() {
        assertThrows(NullPointerException.class, () -> DEFAULT_NULLABLE.compose(null));

        ToShortNullable<Boolean> composed = DEFAULT_NULLABLE.compose(Object::toString);

        assertNotNull(composed);
    }
}
