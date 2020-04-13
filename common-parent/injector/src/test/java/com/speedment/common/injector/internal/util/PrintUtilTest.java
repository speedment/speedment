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
package com.speedment.common.injector.internal.util;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class PrintUtilTest {

    private static final String CHARS = IntStream.rangeClosed('a', 'z').mapToObj(i -> (char) i).map(Object::toString).collect(joining());

    @Test
    void limitSmall() {
        assertEquals("a", PrintUtil.limit(CHARS, 1));
    }

    @Test
    void limitLarge() {
        assertEquals(CHARS, PrintUtil.limit(CHARS, CHARS.length()));
    }

    @Test
    void limit() {
        assertEquals("abc...wxyz", PrintUtil.limit(CHARS, 10));
        assertEquals("abcd...wxyz", PrintUtil.limit(CHARS, 11));
        assertEquals("abcd...vwxyz", PrintUtil.limit(CHARS, 12));
    }

}