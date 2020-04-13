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
package com.speedment.runtime.core.internal.component.resultset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

final class JavaTypeMappingImplTest {

    private final JavaTypeMappingImpl<String> instance = new JavaTypeMappingImpl<>(
            String.class,
            "stringResultSet",
            Function.identity(),
            Long::toString
    );

    @Test
    void getJavaClass() {
        assertNotNull(instance.getJavaClass());
    }

    @Test
    void getResultSetMethodName() {
        assertNotNull(instance.getResultSetMethodName(null));
    }

    @Test
    void parse() {
        final String testString = "test";
        final long testLong = 1L;

        assertEquals(testString, instance.parse(testString));
        assertEquals(Long.toString(testLong), instance.parse(testLong));
    }

    @Test
    void _toString() {
        assertNotNull(instance.toString());
    }
}
