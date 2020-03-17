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

import java.math.BigDecimal;
import java.util.function.Function;

final class ToBigDecimalNullableTest {

    @ParameterizedTest
    @ValueSource(strings = "test")
    void of(String input) {
        Function<String, BigDecimal> function = string -> BigDecimal.valueOf(string.length());
        ToBigDecimalNullable<String> fromFunction = ToBigDecimalNullable.of(function);

        assertNotNull(fromFunction);
        assertEquals(function.apply(input), fromFunction.apply(input));

        ToBigDecimalNullable<String> raw = string -> BigDecimal.valueOf(string.length());
        ToBigDecimalNullable<String> fromRaw = ToBigDecimalNullable.of(raw);

        assertNotNull(fromFunction);
        assertEquals(raw.apply(input), fromRaw.apply(input));
    }

    @Test
    void orThrow() {
        ToBigDecimalNullable<String> nullValue = string -> null;
        assertDoesNotThrow(nullValue::orThrow);

        ToBigDecimal<String> toBigDecimal = nullValue.orThrow();
        assertThrows(NullPointerException.class, () -> toBigDecimal.apply(""));

        ToBigDecimalNullable<String> numberValue = string -> BigDecimal.valueOf(string.length());
        assertDoesNotThrow(numberValue::orThrow);
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo", ""})
    void orElseGet(String input) {
        ToBigDecimalNullable<String> nullValue = string -> null;
        ToBigDecimal<String> toBigDecimal = nullValue.
                orElseGet(string -> BigDecimal.valueOf(string.length()));

        assertEquals(BigDecimal.valueOf(input.length()), toBigDecimal.apply(input));

        ToBigDecimalNullable<String> hasValue = string -> BigDecimal.valueOf(string.length());
        toBigDecimal = hasValue.orElseGet(string -> BigDecimal.valueOf(1));

        assertEquals(BigDecimal.valueOf(input.length()), toBigDecimal.apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo", ""})
    void orElse(String input) {
        ToBigDecimalNullable<String> nullValue = string -> null;
        ToBigDecimal<String> toBigDecimal = nullValue.orElse(BigDecimal.valueOf(1));

        assertEquals(BigDecimal.valueOf(1), toBigDecimal.apply(input));

        ToBigDecimalNullable<String> hasValue = string -> BigDecimal.valueOf(string.length());
        toBigDecimal = hasValue.orElse(BigDecimal.valueOf(1));

        assertEquals(BigDecimal.valueOf(input.length()), toBigDecimal.apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "foo"})
    void hash(String input) {
        ToBigDecimalNullable<String> nullValue = string -> null;
        assertEquals(0, nullValue.hash(input));

        ToBigDecimalNullable<String> hasValue = string -> BigDecimal.valueOf(string.length());
        assertNotEquals(0, hasValue.hash(input));
    }

    @Test
    void compare() {
        ToBigDecimalNullable<String> raw = string ->
                string.length() > 4 ? BigDecimal.valueOf(string.length()) : null;

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
        ToBigDecimalNullable<String> raw = string -> BigDecimal.valueOf(string.length());

        assertThrows(NullPointerException.class, () -> raw.compose(null));

        ToBigDecimalNullable<BigDecimal> composed = raw.compose(BigDecimal::toString);

        assertNotNull(composed);
    }

    @Test
    void expressionType() {
        ToBigDecimalNullable<String> nullValue = string -> null;

        assertEquals(ExpressionType.BIG_DECIMAL_NULLABLE, nullValue.expressionType());
    }

}
