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
package com.speedment.runtime.compute.internal;

import static org.junit.jupiter.api.Assertions.*;

import com.speedment.runtime.compute.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

final class ToEnumImplTest {

    private final ToEnumImpl<String, TestEnum> instance =
            new ToEnumImpl<>(TestEnum.class, TestEnum::valueOf);

    @Test
    void enumClass() {
        assertEquals(TestEnum.class, instance.enumClass());
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void apply(String input) {
        assertEquals(TestEnum.valueOf(input), instance.apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void asOrdinal(String input) {
        assertEquals(TestEnum.valueOf(input).ordinal(), instance.asOrdinal().applyAsInt(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void asName(String input) {
        assertEquals(TestEnum.valueOf(input).name(), instance.asName().apply(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "SECOND"})
    void hash(String input) {
        assertNotEquals(0, instance.hash(input));
    }

    @Test
    void compare() {
        Pair<String, String> pair1 = new Pair<>("FIRST", "FIRST");
        Pair<String, String> pair2 = new Pair<>("FIRST", "SECOND");
        Pair<String, String> pair3 = new Pair<>("SECOND", "FIRST");
        Pair<String, String> pair4 = new Pair<>("SECOND", "SECOND");

        assertEquals(0, instance.compare(pair1.getFirst(), pair1.getSecond()));
        assertEquals(-1, instance.compare(pair2.getFirst(), pair2.getSecond()));
        assertEquals(1, instance.compare(pair3.getFirst(), pair3.getSecond()));
        assertEquals(0, instance.compare(pair4.getFirst(), pair4.getSecond()));
    }

    private enum TestEnum {
        FIRST,
        SECOND
    }
}
