/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.core.internal.stream.parallel;

import org.junit.*;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.*;

/**
 *
 * @author pemi
 */
public class ArraySpliteratorTest extends BaseSpliteratorTest {

    private static final int SIZE = 2048;

    private static final Supplier<Stream<Integer>> STREAM_SUPPLIER
        = () -> IntStream.range(0, SIZE)
        .boxed();

    private Integer[] array;
    private Set<Integer> expectedSet;

    public ArraySpliteratorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        array = STREAM_SUPPLIER.get().toArray(Integer[]::new);
        instance = new ArraySpliterator<>(array, 0);
        expectedSet = STREAM_SUPPLIER.get().collect(toSet());
    }

    @After
    public void tearDown() {
    }

    @Test
    @Ignore
    public void testTrySplit() {
        printTestName();
        Set<String> threadNames = new HashSet<>();
        final Set<Integer> set = StreamSupport.stream(instance, true)
                .peek(i -> threadNames.add(Thread.currentThread().getName()))
                .collect(toSet());
        assertEquals(expectedSet, set);
        //System.out.println("Threads used:" + threadNames);
        assertTrue(threadNames.size() > Runtime.getRuntime().availableProcessors()/2-1);
    }

    @Test
    public void testForEachRemaining() {
        printTestName();
        final Set<Integer> set = new HashSet<>();
        assertTrue(instance.tryAdvance(set::add));
        instance.forEachRemaining(set::add);
        assertEquals(expectedSet, set);
    }

    @Test
    public void testTryAdvance() {
        printTestName();
        IntStream.range(0, SIZE).forEach(i -> assertTrue("error for:" + i, instance.tryAdvance(DO_NOTHING)));
        assertFalse(instance.tryAdvance(DO_NOTHING));
    }

    @Test
    public void testEstimateSize() {
        printTestName();
        int remains = SIZE;
        for (int i = 0; i < SIZE; i++) {
            assertEquals(remains--, instance.estimateSize());
            instance.tryAdvance(DO_NOTHING);
        }
    }

}
