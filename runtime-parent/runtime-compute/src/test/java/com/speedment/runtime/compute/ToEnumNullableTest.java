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

import static com.speedment.runtime.compute.expression.ExpressionType.ENUM_NULLABLE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.compute.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

final class ToEnumNullableTest {

    private static final ToEnumNullable<String, TestEnum> DEFAULT_NULLABLE =
            ToEnumNullable.of(TestEnum.class, string -> string == null ? null : TestEnum.valueOf(string));

    @Test
    void enumClass() {
        assertEquals(TestEnum.class, DEFAULT_NULLABLE.enumClass());
    }

    @Test
    void expressionType() {
        assertEquals(ENUM_NULLABLE, DEFAULT_NULLABLE.expressionType());
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void asOrdinal(String input) {
        ToIntNullable<String> ordinal = DEFAULT_NULLABLE.asOrdinal();

        assertEquals(TestEnum.valueOf(input).ordinal(), ordinal.applyAsInt(input));
        assertNull(ordinal.apply(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void asName(String input) {
        ToStringNullable<String> name = DEFAULT_NULLABLE.asName();

        assertEquals(TestEnum.valueOf(input).name(), name.apply(input));
        assertNull(name.apply(null));
    }

    @Test
    void orThrow() {
        assertDoesNotThrow(DEFAULT_NULLABLE::orThrow);

        final ToEnum<String, TestEnum> toEnum = DEFAULT_NULLABLE.orThrow();
        assertThrows(NullPointerException.class, () -> toEnum.apply(null));

    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void orElseGet(String input) {
        final ToEnumNullable<String, TestEnum> nullValue =
                ToEnumNullable.of(TestEnum.class, string -> null);
        ToEnum<String, TestEnum> toEnum = nullValue.orElseGet(
                ToEnum.of(TestEnum.class, string -> TestEnum.FIRST));

        assertEquals(TestEnum.FIRST, toEnum.apply(input));

        toEnum = DEFAULT_NULLABLE.orElseGet(ToEnum.of(TestEnum.class, string -> TestEnum.FIRST));

        assertEquals(TestEnum.valueOf(input), toEnum.apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void orElse(String input) {
        final ToEnumNullable<String, TestEnum> nullValue =
                ToEnumNullable.of(TestEnum.class, string -> null);
        ToEnum<String, TestEnum> toEnum = nullValue.orElse(TestEnum.FIRST);

        assertEquals(TestEnum.FIRST, toEnum.apply(input));

        toEnum = DEFAULT_NULLABLE.orElseGet(ToEnum.of(TestEnum.class, string -> TestEnum.FIRST));

        assertEquals(TestEnum.valueOf(input), toEnum.apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void mapToDoubleIfPresent(String input) {
        final ToDoubleNullable<String> toDoubleNullable = DEFAULT_NULLABLE
                .mapToDoubleIfPresent(f -> 1);

        assertNotNull(toDoubleNullable);
        assertEquals(1, toDoubleNullable.applyAsDouble(input));

        assertNull(toDoubleNullable.apply(null));
        assertEquals(1, toDoubleNullable.apply(input));

        final ToDouble<String> orElseGet = toDoubleNullable.orElseGet(d -> 0);
        assertEquals(1, orElseGet.applyAsDouble(input));
        assertEquals(0, orElseGet.applyAsDouble(null));

        final ToDouble<String> orElse = toDoubleNullable.orElse((double) 0);
        assertEquals(1, orElse.applyAsDouble(input));
        assertEquals(0, orElse.applyAsDouble(null));

        assertTrue(toDoubleNullable.isNotNull(input));
        assertFalse(toDoubleNullable.isNotNull(null));

        assertTrue(toDoubleNullable.isNull(null));
        assertFalse(toDoubleNullable.isNull(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void mapIfPresent(String input) {
        final ToEnumNullable<String, TestEnum> toEnumNullable =
                DEFAULT_NULLABLE.mapIfPresent(e -> TestEnum.FIRST);

        assertEquals(TestEnum.class, toEnumNullable.enumClass());

        assertNotNull(toEnumNullable);
        assertEquals(TestEnum.FIRST, toEnumNullable.apply(input));

        assertNull(toEnumNullable.apply(null));
        assertEquals(TestEnum.FIRST, toEnumNullable.apply(input));

        final ToEnum<String, TestEnum> orElseGet =
                toEnumNullable.orElseGet(ToEnum.of(TestEnum.class, e -> TestEnum.SECOND));
        assertEquals(TestEnum.FIRST, orElseGet.apply(input));
        assertEquals(TestEnum.SECOND, orElseGet.apply(null));

        final ToEnum<String, TestEnum> orElse = toEnumNullable.orElse(TestEnum.SECOND);
        assertEquals(TestEnum.FIRST, orElse.apply(input));
        assertEquals(TestEnum.SECOND, orElse.apply(null));

        assertTrue(toEnumNullable.isNotNull(input));
        assertFalse(toEnumNullable.isNotNull(null));

        assertTrue(toEnumNullable.isNull(null));
        assertFalse(toEnumNullable.isNull(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void hash(String input) {
        assertNotEquals(0, DEFAULT_NULLABLE.hash(input));
        assertEquals(0, DEFAULT_NULLABLE.hash(null));
    }

    @Test
    void compare() {
        final Pair<String, String> pair1 = new Pair<>(null, null);
        final Pair<String, String> pair2 = new Pair<>("FIRST", null);
        final Pair<String, String> pair3 = new Pair<>(null, "SECOND");
        final Pair<String, String> pair4 = new Pair<>("FIRST", "SECOND");

        assertEquals(0, DEFAULT_NULLABLE.compare(pair1.getFirst(), pair1.getSecond()));
        assertEquals(-1, DEFAULT_NULLABLE.compare(pair2.getFirst(), pair2.getSecond()));
        assertEquals(1, DEFAULT_NULLABLE.compare(pair3.getFirst(), pair3.getSecond()));
        assertEquals(-1, DEFAULT_NULLABLE.compare(pair4.getFirst(), pair4.getSecond()));
    }

    @Test
    void compose() {
        assertThrows(NullPointerException.class, () -> DEFAULT_NULLABLE.compose(null));

        ToEnumNullable<String, TestEnum> composed = DEFAULT_NULLABLE.compose(Object::toString);

        assertNotNull(composed);
    }

    private enum TestEnum {
        FIRST,
        SECOND
    }
}
