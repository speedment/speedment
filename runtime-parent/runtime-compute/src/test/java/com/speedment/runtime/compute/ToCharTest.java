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

import static com.speedment.runtime.compute.expression.ExpressionType.CHAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.speedment.common.function.ToCharFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ToCharTest {

    private static final ToChar<String> DEFAULT_TO = string -> string.toCharArray()[0];

    @ParameterizedTest
    @ValueSource(strings = "test")
    void of(String input) {
        ToCharFunction<String> function = string -> string.toCharArray()[0];
        ToChar<String> fromFunction = ToChar.of(function);

        assertNotNull(fromFunction);
        assertEquals(function.applyAsChar(input), fromFunction.applyAsChar(input));

        ToChar<String> fromToChar = ToChar.of(DEFAULT_TO);

        assertNotNull(fromFunction);
        assertEquals(DEFAULT_TO.applyAsChar(input), fromToChar.applyAsChar(input));
    }

    @Test
    void expressionType() {
        assertEquals(CHAR, DEFAULT_TO.expressionType());
    }

    @Test
    void asDouble() {
        assertNotNull(DEFAULT_TO.asDouble());
    }

    @Test
    void asInt() {
        assertNotNull(DEFAULT_TO.asInt());
    }

    @Test
    void asLong() {
        assertNotNull(DEFAULT_TO.asLong());
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "TEST", "tEsT"})
    void toUpperCase(String input) {
        assertEquals(Character.toUpperCase(input.toCharArray()[0]),
                DEFAULT_TO.toUpperCase().applyAsChar(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "TEST", "tEsT"})
    void toLowerCase(String input) {
        assertEquals(Character.toLowerCase(input.toCharArray()[0]),
                DEFAULT_TO.toLowerCase().applyAsChar(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void hash(String input) {
        assertNotEquals(0, DEFAULT_TO.hash(input));
    }

    @Test
    void compare() {
        assertEquals(0, DEFAULT_TO.compare("input", "input"));
    }

    @Test
    void compose() {
        assertThrows(NullPointerException.class, () -> DEFAULT_TO.compose(null));

        ToCharNullable<Boolean> composed = DEFAULT_TO.compose(Object::toString);

        assertNotNull(composed);
    }
}
