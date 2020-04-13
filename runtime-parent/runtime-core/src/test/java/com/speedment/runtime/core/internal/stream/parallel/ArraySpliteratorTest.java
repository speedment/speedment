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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author pemi
 */
final class ArraySpliteratorTest extends BaseSpliteratorTest {

    private static final int SIZE = 2048;

    private static final Supplier<Stream<Integer>> STREAM_SUPPLIER = () -> IntStream.range(0, SIZE).boxed();

    private Integer[] array;
    private Set<Integer> expectedSet;

    @BeforeEach
    void setUp() {
        array = STREAM_SUPPLIER.get().toArray(Integer[]::new);
        instance = new ArraySpliterator<>(array, 0);
        expectedSet = STREAM_SUPPLIER.get().collect(toSet());
    }

    @Test
    void testTrySplit() {
        Set<String> threadNames = new HashSet<>();
        final Set<Integer> set = StreamSupport.stream(instance, true)
                .peek(i -> threadNames.add(Thread.currentThread().getName()))
                .collect(toSet());
        assertEquals(expectedSet, set);
    }

    @Test
    void testForEachRemaining() {
        final Set<Integer> set = new HashSet<>();
        assertTrue(instance.tryAdvance(set::add));
        instance.forEachRemaining(set::add);
        assertEquals(expectedSet, set);
    }

    @Test
    void testTryAdvance() {
        IntStream.range(0, SIZE).forEach(i -> assertTrue( instance.tryAdvance(DO_NOTHING), "error for:" + i));
        assertFalse(instance.tryAdvance(DO_NOTHING));
    }

    @Test
    void testEstimateSize() {
        int remains = SIZE;
        for (int i = 0; i < SIZE; i++) {
            assertEquals(remains--, instance.estimateSize());
            instance.tryAdvance(DO_NOTHING);
        }
    }

}
