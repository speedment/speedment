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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Function;

class ToStringNullableTest {

    private static final ToStringNullable<String> DEFAULT_NULLABLE = string -> string;

    @ParameterizedTest
    @ValueSource(strings = "test")
    void of(String input) {
        final Function<String, String> function = string -> string;
        final ToStringNullable<String> fromFunction = ToStringNullable.of(function);

        assertNotNull(fromFunction);
        assertEquals(input, fromFunction.apply(input));

        final ToStringNullable<String> fromToString = ToStringNullable.of(DEFAULT_NULLABLE);

        assertNotNull(fromToString);
        assertEquals(input, fromToString.apply(input));
    }

    @Test
    void expressionType() {
        assertEquals(ExpressionType.STRING_NULLABLE, DEFAULT_NULLABLE.expressionType());
    }

    @Test
    void orThrow() {
        assertDoesNotThrow(DEFAULT_NULLABLE::orThrow);

        final ToString<String> toString = DEFAULT_NULLABLE.orThrow();
        assertThrows(NullPointerException.class, () -> toString.apply(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"foo", "test"})
    void orElseGet(String input) {
        final ToStringNullable<String> nullValue = string -> null;
        ToString<String> toString = nullValue.orElseGet(string -> input);

        assertEquals(input, toString.apply(null));

        toString = DEFAULT_NULLABLE.orElseGet(string -> null);

        assertEquals(input, toString.apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"foo", "test"})
    void orElse(String input) {
        final ToStringNullable<String> nullValue = string -> null;
        ToString<String> toString = nullValue.orElse(input);

        assertEquals(input, toString.apply(null));

        toString = DEFAULT_NULLABLE.orElse(null);

        assertEquals(input, toString.apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void hash(String input) {
        assertEquals(0, DEFAULT_NULLABLE.hash(null));
        assertNotEquals(0, DEFAULT_NULLABLE.hash(input));
    }

    @Test
    void compare() {
        final Pair<String, String> pair1 = new Pair<>(null, null);
        final Pair<String, String> pair2 = new Pair<>(null, "bar");
        final Pair<String, String> pair3 = new Pair<>("foo", null);
        final Pair<String, String> pair4 = new Pair<>("foo", "bar");

        assertEquals(0, DEFAULT_NULLABLE.compare(pair1.getFirst(), pair1.getSecond()));
        assertEquals(1, DEFAULT_NULLABLE.compare(pair2.getFirst(), pair2.getSecond()));
        assertEquals(-1, DEFAULT_NULLABLE.compare(pair3.getFirst(), pair3.getSecond()));
        assertEquals(pair4.getFirst().compareTo(pair4.getSecond()),
                DEFAULT_NULLABLE.compare(pair4.getFirst(), pair4.getSecond()));
    }

    @Test
    void compose() {
        assertThrows(NullPointerException.class, () -> DEFAULT_NULLABLE.compose(null));

        final ToStringNullable<Boolean> composed = DEFAULT_NULLABLE.compose(Object::toString);

        assertNotNull(composed);
    }
}
