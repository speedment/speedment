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
package com.speedment.common.function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class BooleanPredicateTest {

    private static final BooleanPredicate INVERT = b -> !b;
    private static final BooleanPredicate IDENTITY = b -> b;

    @Test
    void test() {
        assertFalse(INVERT.test(true));
        assertTrue(INVERT.test(false));
    }

    @Test
    void and() {
        assertFalse(INVERT.and(IDENTITY).test(true));
        assertFalse(INVERT.and(IDENTITY).test(false));
    }

    @Test
    void negate() {
        assertTrue(INVERT.negate().test(true));
        assertFalse(INVERT.negate().test(false));
    }

    @Test
    void or() {
        assertTrue(INVERT.or(IDENTITY).test(true));
        assertTrue(INVERT.or(IDENTITY).test(false));
    }
}