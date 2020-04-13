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

import static com.speedment.runtime.compute.expression.ExpressionType.ENUM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.speedment.runtime.compute.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

final class ToEnumTest {

    private static final ToEnum<String, TestEnum> DEFAULT_TO = ToEnum.of(TestEnum.class, TestEnum::valueOf);

    @Test
    void enumClass() {
        assertEquals(TestEnum.class, DEFAULT_TO.enumClass());
    }

    @Test
    void expressionType() {
        assertEquals(ENUM, DEFAULT_TO.expressionType());
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void apply(String input) {
        assertEquals(TestEnum.valueOf(input), DEFAULT_TO.apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void asOrdinal(String input) {
        ToInt<String> ordinal = DEFAULT_TO.asOrdinal();

        assertEquals(TestEnum.valueOf(input).ordinal(), ordinal.applyAsInt(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void asName(String input) {
        ToString<String> name = DEFAULT_TO.asName();

        assertEquals(TestEnum.valueOf(input).name(), name.apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void map(String input) {
        ToEnum<String, TestEnum> toEnum = DEFAULT_TO.map(clazz -> clazz);

        assertEquals(DEFAULT_TO.apply(input), toEnum.apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void hash(String input) {
        assertNotEquals(0, DEFAULT_TO.hash(input));
    }

    @Test
    void compare() {
        Pair<String, String> pair1 = new Pair<>("FIRST", "FIRST");
        Pair<String, String> pair2 = new Pair<>("FIRST", "SECOND");
        Pair<String, String> pair3 = new Pair<>("SECOND", "FIRST");
        Pair<String, String> pair4 = new Pair<>("SECOND", "SECOND");

        assertEquals(0, DEFAULT_TO.compare(pair1.getFirst(), pair1.getSecond()));
        assertEquals(-1, DEFAULT_TO.compare(pair2.getFirst(), pair2.getSecond()));
        assertEquals(1, DEFAULT_TO.compare(pair3.getFirst(), pair3.getSecond()));
        assertEquals(0, DEFAULT_TO.compare(pair4.getFirst(), pair4.getSecond()));
    }

    @Test
    void compose() {
        assertThrows(NullPointerException.class, () -> DEFAULT_TO.compose(null));

        ToEnumNullable<String, TestEnum> composed = DEFAULT_TO.compose(Object::toString);

        assertNotNull(composed);
    }

    private enum TestEnum {
        FIRST,
        SECOND
    }
}
