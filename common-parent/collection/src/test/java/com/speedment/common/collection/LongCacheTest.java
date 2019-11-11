/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.collection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class LongCacheTest {

    @Test
    void getOrCompute() {
        final LongCache<String> lc = new LongCache<>(10);
        final long actual = lc.getOrCompute("one", () -> 1L);
        assertEquals(1L, actual);
    }

    @Test
    void testToString() {
        final LongCache<String> lc = new LongCache<>(10);
        final long actual = lc.getOrCompute("Olle", () -> 42L);
        assertTrue(lc.toString().contains("Olle"));
        assertTrue(lc.toString().contains("42"));
    }
}