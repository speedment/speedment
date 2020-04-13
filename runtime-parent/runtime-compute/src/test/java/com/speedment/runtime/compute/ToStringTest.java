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

import static com.speedment.runtime.compute.expression.ExpressionType.STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.speedment.runtime.compute.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Function;

class ToStringTest {

    private static final ToString<String> DEFAULT_TO = string -> string;

    @ParameterizedTest
    @ValueSource(strings = "test")
    void of(String input) {
        final Function<String, String> function = string -> string;
        final ToString<String> fromFunction = ToString.of(function);

        assertNotNull(fromFunction);
        assertEquals(input, fromFunction.apply(input));

        final ToString<String> fromToString = ToString.of(DEFAULT_TO);

        assertNotNull(fromToString);
        assertEquals(input, fromToString.apply(input));
    }

    @Test
    void expressionType() {
        assertEquals(STRING, DEFAULT_TO.expressionType());
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "TEST", "tEsT"})
    void toUpperCase(String input) {
        assertEquals(input.toUpperCase(), DEFAULT_TO.toUpperCase().apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "TEST", "tEsT"})
    void toLowerCase(String input) {
        assertEquals(input.toLowerCase(), DEFAULT_TO.toLowerCase().apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"foo", "test"})
    void hash(String input) {
        assertEquals(input.hashCode(), DEFAULT_TO.hash(input));
    }

    @Test
    void compare() {
        final Pair<String, String> pair1 = new Pair<>("foo", "foo");
        final Pair<String, String> pair2 = new Pair<>("foo1", "bar");
        final Pair<String, String> pair3 = new Pair<>("foo", "bar1");

        assertEquals(pair1.getFirst().compareTo(pair1.getSecond()),
            DEFAULT_TO.compare(pair1.getFirst(), pair1.getSecond()));
        assertEquals(pair2.getFirst().compareTo(pair2.getSecond()),
            DEFAULT_TO.compare(pair2.getFirst(), pair2.getSecond()));
        assertEquals(pair3.getFirst().compareTo(pair3.getSecond()),
            DEFAULT_TO.compare(pair3.getFirst(), pair3.getSecond()));
    }

    @Test
    void compose() {
        assertThrows(NullPointerException.class, () -> DEFAULT_TO.compose(null));

        final ToStringNullable<Boolean> composed = DEFAULT_TO.compose(Object::toString);

        assertNotNull(composed);
    }
}
