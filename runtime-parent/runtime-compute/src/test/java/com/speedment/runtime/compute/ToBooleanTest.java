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

import static com.speedment.runtime.compute.expression.ExpressionType.BOOLEAN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.speedment.runtime.compute.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Predicate;

final class ToBooleanTest {

    private static final ToBoolean<String> DEFAULT_TO = string -> string.length() > 2;

    @ParameterizedTest
    @ValueSource(strings = "test")
    void of(String input) {
        Predicate<String> function = string -> string.length() > 2;
        ToBoolean<String> fromFunction = ToBoolean.of(function);

        assertNotNull(fromFunction);
        assertEquals(function.test(input), fromFunction.applyAsBoolean(input));

        ToBoolean<String> raw = DEFAULT_TO;
        ToBoolean<String> fromRaw = ToBoolean.of(raw);

        assertNotNull(fromFunction);
        assertEquals(raw.test(input), fromRaw.applyAsBoolean(input));
    }

    @Test
    void expressionType() {
        ToBoolean<String> toBooleanNullable = string -> true;

        Assertions.assertEquals(BOOLEAN, toBooleanNullable.expressionType());
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void asDouble(String input) {
        assertEquals(1, DEFAULT_TO.asDouble().applyAsDouble(input));
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void asInt(String input) {
        assertEquals(1, DEFAULT_TO.asInt().applyAsInt(input));
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void asLong(String input) {
        assertEquals(1, DEFAULT_TO.asLong().applyAsLong(input));
    }

    @ParameterizedTest
    @ValueSource(strings = "test")
    void mapToDouble(String input) {
        ToDouble<String> toDouble = DEFAULT_TO.mapToDouble(bool -> bool ? 1 : 0);

        assertEquals(1, toDouble.applyAsDouble(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo", "ab"})
    void map(String input) {
        ToBoolean<String> inverted = DEFAULT_TO.map(bool -> !bool);

        assertNotEquals(inverted.applyAsBoolean(input), DEFAULT_TO.applyAsBoolean(input));
    }

    @Test
    void hash() {
        assertEquals(1, DEFAULT_TO.hash("test"));
        assertEquals(0, DEFAULT_TO.hash("a"));
    }

    @Test
    void compare() {
        Pair<String, String> nullNull = new Pair<>("foo", "bar");
        Pair<String, String> nullHas = new Pair<>("ab", "longer");
        Pair<String, String> hasNull = new Pair<>("longer", "ab");
        Pair<String, String> hasHas = new Pair<>("longer", "longer");

        assertEquals(0, DEFAULT_TO.compare(nullNull.getFirst(), nullNull.getSecond()));
        assertEquals(-1, DEFAULT_TO.compare(nullHas.getFirst(), nullHas.getSecond()));
        assertEquals(1, DEFAULT_TO.compare(hasNull.getFirst(), hasNull.getSecond()));
        assertEquals(0, DEFAULT_TO.compare(hasHas.getFirst(), hasHas.getSecond()));
    }

    @Test
    void compose() {
        assertThrows(NullPointerException.class, () -> DEFAULT_TO.compose(null));

        ToBoolean<Boolean> composed = DEFAULT_TO.compose(Object::toString);

        assertNotNull(composed);
    }

    @Test
    void composeNullable() {
        assertThrows(NullPointerException.class, () -> DEFAULT_TO.composeNullable(null));

        ToBooleanNullable<Boolean> composed = DEFAULT_TO.composeNullable(Object::toString);

        assertNotNull(composed);
    }
}
