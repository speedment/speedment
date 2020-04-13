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

import static com.speedment.runtime.compute.expression.ExpressionType.CHAR_NULLABLE;
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

final class ToCharNullableTest {

    private static final ToCharNullable<String> DEFAULT_NULLABLE = string -> string.toCharArray()[0];

    @ParameterizedTest
    @ValueSource(strings = "test")
    void of(String input) {
        Function<String, Character> function = string -> string.toCharArray()[0];
        ToCharNullable<String> fromFunction = ToCharNullable.of(function);

        assertNotNull(fromFunction);
        assertEquals(function.apply(input), fromFunction.apply(input));

        ToCharNullable<String> raw = DEFAULT_NULLABLE;
        ToCharNullable<String> fromRaw = ToCharNullable.of(raw);

        assertNotNull(fromFunction);
        assertEquals(raw.apply(input), fromRaw.apply(input));
    }

    @Test
    void expressionType() {
        ToCharNullable<String> toCharNullable = string -> null;

        Assertions.assertEquals(CHAR_NULLABLE, toCharNullable.expressionType());
    }

    @Test
    void orThrow() {
        ToCharNullable<String> nullValue = string -> null;
        assertDoesNotThrow(nullValue::orThrow);

        ToChar<String> toChar = nullValue.orThrow();
        assertThrows(NullPointerException.class, () -> toChar.applyAsChar(""));

        assertDoesNotThrow(DEFAULT_NULLABLE::orThrow);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "foo", "test"})
    void orElseGet(String input) {
        ToCharNullable<String> nullValue = string -> null;
        ToChar<String> toChar = nullValue.orElseGet(string -> string.toCharArray()[0]);

        assertEquals(input.toCharArray()[0], toChar.applyAsChar(input));

        toChar = DEFAULT_NULLABLE.orElseGet(string -> (char) -1);

        assertEquals(input.toCharArray()[0], toChar.applyAsChar(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void orElse(String input) {
        ToCharNullable<String> nullValue = string -> null;
        ToChar<String> toChar = nullValue.orElse((char) 1);

        assertEquals((char) 1, toChar.applyAsChar(input));

        toChar = DEFAULT_NULLABLE.orElse((char) -1);

        assertEquals(input.toCharArray()[0], toChar.applyAsChar(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void hash(String input) {
        ToCharNullable<String> nullValue = string -> null;
        assertEquals(0, nullValue.hash(input));

        assertNotEquals(0, DEFAULT_NULLABLE.hash(input));
    }

    @Test
    void compare() {
        ToCharNullable<String> raw = string -> string.length() > 4 ? (char) 1 : null;

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

        ToCharNullable<Boolean> composed = DEFAULT_NULLABLE.compose(Object::toString);

        assertNotNull(composed);
    }
}
