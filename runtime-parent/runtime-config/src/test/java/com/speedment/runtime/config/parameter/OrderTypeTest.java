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

package com.speedment.runtime.config.parameter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.stream.Stream;

final class OrderTypeTest {

    @TestFactory
    Stream<DynamicTest> selectLazily() {
        return Arrays.stream(OrderType.values()).map(orderType -> dynamicTest(orderType.name(),
            () -> assertDoesNotThrow(() -> orderType.selectLazily(() -> "ascending", () -> "descending"))));
    }

    @TestFactory
    Stream<DynamicTest> select() {
        return Arrays.stream(OrderType.values()).map(orderType -> dynamicTest(orderType.name(),
            () -> assertDoesNotThrow(() -> orderType.select("ascending","descending"))));
    }

    @TestFactory
    Stream<DynamicTest> selectRunnable() {
        return Arrays.stream(OrderType.values()).map(orderType -> dynamicTest(orderType.name(),
            () -> assertDoesNotThrow(() -> orderType.selectRunnable(() -> {}, () -> {}))));
    }
}
