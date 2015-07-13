/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.util.tuple;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pemi
 */
public class Tuple2NullableTest {

    private static final int FIRST = 8;
    private static final int SECOND = 16;

    private Tuple2Nullable<Integer, Integer> instance;

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
        instance = new Tuple2Nullable<>(Integer.class, Integer.class, FIRST, SECOND);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGet0() {
        System.out.println("get0");
        assertEquals(FIRST, instance.get0().get().intValue());
    }

    @Test
    public void testGet1() {
        System.out.println("get1");
        assertEquals(SECOND, instance.get1().get().intValue());
    }

    @Test
    public void testGet() {
        System.out.println("get");
        assertEquals(FIRST, instance.get(0).get());
        assertEquals(SECOND, instance.get(1).get());
    }

    @Test
    public void testSet0() {
        System.out.println("set0");
        instance.set1(32);
        assertEquals(32, instance.get1().get().intValue());
    }

    @Test
    public void testSet1() {
        System.out.println("set1");
        instance.set1(64);
        assertEquals(64, instance.get1().get().intValue());
    }

    @Test
    public void testDefConstructor() {
        System.out.println("Default constructor");
        final Tuple2Nullable<Integer, Integer> newInstance = new Tuple2Nullable<>(Integer.class, Integer.class);
        assertFalse(newInstance.get0().isPresent());
        assertFalse(newInstance.get1().isPresent());
    }

    @Test
    public void testHash() {
        System.out.println("hashCode");
        int hashCodeInstance = instance.hashCode();
        final Tuple2Nullable<Integer, Integer> newInstance = new Tuple2Nullable<>(Integer.class, Integer.class, FIRST, SECOND);
        int hashCodenewInstance = newInstance.hashCode();
        assertEquals(hashCodeInstance, hashCodenewInstance);
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        final Tuple2Nullable<Integer, Integer> newInstance = new Tuple2Nullable<>(Integer.class, Integer.class, FIRST, SECOND);
        assertEquals(instance, newInstance);
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        System.out.println(instance);
        final Tuple2Nullable<Integer, Integer> newInstance = new Tuple2Nullable<>(Integer.class, Integer.class);
        System.out.println(newInstance);
    }

    @Test
    public void testStream() {
        System.out.println("Stream");
        List<Object> content = instance.stream().collect(toList());
        List<Optional<Integer>> expected = Arrays.asList(Optional.of(FIRST), Optional.of(SECOND));
        assertEquals(expected, content);

        final Tuple2Nullable<Integer, Integer> newInstance = new Tuple2Nullable<>(Integer.class, Integer.class, FIRST, null);
        List<Object> content2 = newInstance.stream().collect(toList());
        List<Optional<Integer>> expected2 = Arrays.asList(Optional.of(FIRST), Optional.empty());
        assertEquals(expected2, content2);
    }

    @Test
    public void testStreamOf() {
        System.out.println("StreamOf");
        final Tuple2Nullable<Integer, Integer> newInstance = new Tuple2Nullable<>(Integer.class, Integer.class, FIRST, null);
        List<Optional<Integer>> content = newInstance.streamOf(Integer.class).collect(toList());
        List<Optional<Integer>> expected = Arrays.asList(Optional.of(FIRST), Optional.empty());
        assertEquals(expected, content);
    }
    
    @Test
    public void testStreamOf2() {
        System.out.println("StreamOf2");
        final Tuple2Nullable<Integer, String> newInstance = new Tuple2Nullable<>(Integer.class, String.class, FIRST, "Tryggve");
        List<Optional<Integer>> content = newInstance.streamOf(Integer.class).collect(toList());
        List<Optional<Integer>> expected = Arrays.asList(Optional.of(FIRST));
        assertEquals(expected, content);
    }

}
