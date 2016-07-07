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
package com.speedment.runtime.util.tuple;

import com.speedment.common.tuple.Tuple2OfNullables;
import com.speedment.common.tuple.Tuples;
import org.junit.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

/**
 *
 * @author pemi
 */
public class Tuple2NullableTest {

    private static final int FIRST = 8;
    private static final int SECOND = 16;

    private Tuple2OfNullables<Integer, Integer> instance;

    public Tuple2NullableTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = Tuples.ofNullables(FIRST, SECOND);
        //instance = new Tuple2OfNullables<>(Integer.class, Integer.class, FIRST, SECOND);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGet0() {
        assertEquals(FIRST, instance.get0().get().intValue());
    }

    @Test
    public void testGet1() {
        assertEquals(SECOND, instance.get1().get().intValue());
    }

    @Test
    public void testGet() {
        assertEquals(FIRST, instance.get(0).get());
        assertEquals(SECOND, instance.get(1).get());
    }

    @Test
    public void testCasting() {
        Tuple2OfNullables<Integer, String> t2 = Tuples.ofNullables(1, null);
        assertEquals(Optional.of(1), t2.get0());
        assertEquals(Optional.empty(), t2.get1());
    }

//    @Test
//    public void testSet0() {
//        System.out.println("set0");
//        instance.set1(32);
//        assertEquals(32, instance.get1().get().intValue());
//    }
//
//    @Test
//    public void testSet1() {
//        System.out.println("set1");
//        instance.set1(64);
//        assertEquals(64, instance.get1().get().intValue());
//    }
    @Test
    public void testDefConstructor() {
        final Tuple2OfNullables<Integer, Integer> newInstance = Tuples.ofNullables(1, 2);
        assertTrue(newInstance.get0().isPresent());
        assertTrue(newInstance.get1().isPresent());
    }

    @Test
    public void testHash() {
        int hashCodeInstance = instance.hashCode();
        final Tuple2OfNullables<Integer, Integer> newInstance = Tuples.ofNullables(FIRST, SECOND);
        int hashCodenewInstance = newInstance.hashCode();
        assertEquals(hashCodeInstance, hashCodenewInstance);
    }

    @Test
    public void testEquals() {
        final Tuple2OfNullables<Integer, Integer> newInstance = Tuples.ofNullables(FIRST, SECOND);
        assertEquals(instance, newInstance);
    }

    @Test
    public void testToString() {
        final Tuple2OfNullables<Integer, Integer> newInstance = Tuples.ofNullables(null, null);
    }

    @Test
    public void testStream() {
        final List<Object> content = instance.stream().collect(toList());
        final List<Optional<Integer>> expected = Arrays.asList(Optional.of(FIRST), Optional.of(SECOND));
        assertEquals(expected, content);

        final Tuple2OfNullables<Integer, Integer> newInstance = Tuples.ofNullables(FIRST, null);
        final List<Object> content2 = newInstance.stream().collect(toList());
        final List<Optional<Integer>> expected2 = Arrays.asList(Optional.of(FIRST), Optional.empty());
        assertEquals(expected2, content2);
    }

    @Test
    public void testStreamOf() {
        final Tuple2OfNullables<Integer, Integer> newInstance = Tuples.ofNullables(FIRST, null);
        List<Integer> content = newInstance.streamOf(Integer.class).collect(toList());
        List<Integer> expected = Arrays.asList(FIRST);
        assertEquals(expected, content);
    }

    @Test
    public void testStreamOf2() {
        final Tuple2OfNullables<Integer, String> newInstance = Tuples.ofNullables(FIRST, "Tryggve");
        List<Integer> content = newInstance.streamOf(Integer.class).collect(toList());
        List<Integer> expected = Arrays.asList(FIRST);
        assertEquals(expected, content);
    }

}
