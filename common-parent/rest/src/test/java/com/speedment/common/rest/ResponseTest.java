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
package com.speedment.common.rest;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final class ResponseTest {

    private final Response instance = new Response(200, "{}", new HashMap<>());

    @Test
    void getStatus() {
        assertNotEquals(0, instance.getStatus());
    }

    @Test
    void getText() {
        assertNotNull(instance.getText());
    }

    @Test
    void getHeaders() {
        assertNotNull(instance.getHeaders());
    }

    @TestFactory
    Stream<DynamicTest> success() {
        final Stream<DynamicTest> successful = IntStream.range(200, 207)
            .mapToObj(status -> new Response(status, "{}", new HashMap<>()))
            .map(response -> dynamicTest(
                format("testing status '%s'", response.getStatus()),
                () -> assertTrue(response.success())));

        final Stream<DynamicTest> unsuccessful = IntStream.range(400, 419)
            .mapToObj(status -> new Response(status, "{}", new HashMap<>()))
            .map(response -> dynamicTest(
                format("testing status '%s'", response.getStatus()),
                () -> assertFalse(response.success())
            ));

        return Stream.concat(successful, unsuccessful);
    }

    @Test
    void decodeJson() {
        assertNotEquals(Optional.empty(), instance.decodeJson());

        final Response fail = new Response(400, "{}", new HashMap<>());
        assertEquals(Optional.empty(), fail.decodeJson());
    }

    @Test
    void decodeJsonArray() {
        final Response array = new Response(200, "[]", new HashMap<>());
        assertNotNull(array.decodeJsonArray());
    }

}
