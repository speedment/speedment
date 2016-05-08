/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.internal.stream.parallelstrategy;

import com.speedment.runtime.internal.stream.parallelstrategy.SingletonSpliterator;
import static com.speedment.runtime.internal.stream.parallelstrategy.BaseSpliteratorTest.DO_NOTHING;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author pemi
 */
public class SingletonSpliteratorTest extends BaseSpliteratorTest {

    private static final int VALUE = 43;

    public SingletonSpliteratorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = new SingletonSpliterator<>(VALUE, 0);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testTrySplit() {
        printTestName();
        assertNull(instance.trySplit());
    }

    @Test
    public void testForEachRemaining() {
        printTestName();
        final List<Integer> expected = Arrays.asList(VALUE);
        final List<Integer> list = new ArrayList<>();
        instance.forEachRemaining(list::add);
        assertEquals(expected, list);
        instance.forEachRemaining(list::add);
        assertEquals(expected, list);
    }

    @Test
    public void testTryAdvance() {
        printTestName();
        assertTrue(instance.tryAdvance(DO_NOTHING));
        assertFalse(instance.tryAdvance(DO_NOTHING));
    }

    @Test
    public void testEstimateSize() {
        printTestName();
        assertEquals(1, instance.estimateSize());
        instance.tryAdvance(DO_NOTHING);
        assertEquals(0, instance.estimateSize());
        instance.tryAdvance(DO_NOTHING);
        assertEquals(0, instance.estimateSize());
    }

}
