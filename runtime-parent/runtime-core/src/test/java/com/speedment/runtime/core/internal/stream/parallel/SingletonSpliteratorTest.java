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
package com.speedment.runtime.core.internal.stream.parallel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author pemi
 */
final class SingletonSpliteratorTest extends BaseSpliteratorTest {

    private static final int VALUE = 43;

    @BeforeEach
    void setUp() {
        instance = new SingletonSpliterator<>(VALUE, 0);
    }

    @Test
    void testTrySplit() {
        printTestName();
        assertNull(instance.trySplit());
    }

    @Test
    void testForEachRemaining() {
        printTestName();
        final List<Integer> expected = Collections.singletonList(VALUE);
        final List<Integer> list = new ArrayList<>();
        instance.forEachRemaining(list::add);
        assertEquals(expected, list);
        instance.forEachRemaining(list::add);
        assertEquals(expected, list);
    }

    @Test
    void testTryAdvance() {
        printTestName();
        assertTrue(instance.tryAdvance(DO_NOTHING));
        assertFalse(instance.tryAdvance(DO_NOTHING));
    }

    @Test
    void testEstimateSize() {
        printTestName();
        assertEquals(1, instance.estimateSize());
        instance.tryAdvance(DO_NOTHING);
        assertEquals(0, instance.estimateSize());
        instance.tryAdvance(DO_NOTHING);
        assertEquals(0, instance.estimateSize());
    }

}
