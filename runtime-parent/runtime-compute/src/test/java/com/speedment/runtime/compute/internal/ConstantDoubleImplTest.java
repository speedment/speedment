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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

final class ConstantDoubleImplTest {

    private static final double CONSTANT = 1;

    private final ConstantDoubleImpl<String> instance = new ConstantDoubleImpl<>(CONSTANT);

    @ParameterizedTest
    @ValueSource(strings = {"", "foo", "test"})
    void applyAsDouble(String input) {
        assertEquals(CONSTANT, instance.applyAsDouble(input));
    }

    @Test
    void value() {
        assertEquals(CONSTANT, instance.value());
    }
}
