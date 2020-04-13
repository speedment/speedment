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
package com.speedment.runtime.compute.trait;

import static org.junit.jupiter.api.Assertions.*;

import com.speedment.runtime.compute.ToBooleanNullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

final class ToNullableTest {

    private final ToBooleanNullable<String> instance = string -> string == null ?
            null : string.length() > 2;

    @Test
    void isNull() {
        assertNotNull(instance.isNull());
        assertTrue(instance.isNull(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "bc", "foo", "test"})
    void isNotNull(String input) {
        assertNotNull(instance.isNotNull());
        assertTrue(instance.isNotNull(input));
    }
}
