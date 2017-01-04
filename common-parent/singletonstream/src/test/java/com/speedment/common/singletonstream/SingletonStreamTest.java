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
package com.speedment.common.singletonstream;

import org.junit.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Per Minborg
 */
public class SingletonStreamTest {

    private SingletonStream<String> instance;

    public SingletonStreamTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = SingletonStream.of("A");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSome() {
        instance.map("A"::indexOf).distinct().unordered().forEach(this::consume);
    }

    private <T> void consume(T t) {

    }

    /**
     * Test of of method, of class SingletonStream.
     */
    @Test
    public void testOf() {
        final SingletonStream<String> ss = SingletonStream.of("B");
        final List<String> s = ss.collect(toList());
        assertEquals(Collections.singletonList("B"), s);
    }

    /**
     * Test of ofNullable method, of class SingletonStream.
     */
    @Test
    @Ignore
    public void testOfNullable() {


    }

    /**
     * Test of filter method, of class SingletonStream.
     */
    @Test
    public void testFilter() {
        assertEquals(1L, instance.filter("A"::equals).count());
        assertEquals(1L, instance.filter("A"::equals).filter(Objects::nonNull).count());
    }

    /**
     * Test of map method, of class SingletonStream.
     */
    @Test
    public void testMap() {
        final Optional<String> binLen = instance.map(String::length).map(Integer::toBinaryString).findFirst();
        assertEquals(Optional.of("1"), binLen);

        Optional<String> r = SingletonStream.of("C").map(s -> null).map(a -> "Olle").findAny();
        assertEquals(Optional.of("Olle"), r);

    }

    /**
     * Test of mapToInt method, of class SingletonStream.
     */
    @Test
    public void testMapToInt() {
        assertEquals(1, instance.mapToInt(String::length).sum());
    }

    // TODO: Implement the test cases below!
//    @Test
//    public void testMapToLong() {
//        System.out.println("mapToLong");
//        ToLongFunction mapper = null;
//        SingletonStream instance = null;
//        LongStream expResult = null;
//        LongStream result = instance.mapToLong(mapper);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of mapToDouble method, of class SingletonStream.
//     */
//    @Test
//    public void testMapToDouble() {
//        System.out.println("mapToDouble");
//        ToDoubleFunction mapper = null;
//        SingletonStream instance = null;
//        DoubleStream expResult = null;
//        DoubleStream result = instance.mapToDouble(mapper);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of flatMap method, of class SingletonStream.
//     */
//    @Test
//    public void testFlatMap() {
//        System.out.println("flatMap");
//        Function mapper = null;
//        SingletonStream instance = null;
//        Stream expResult = null;
//        Stream result = instance.flatMap(mapper);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of flatMapToInt method, of class SingletonStream.
//     */
//    @Test
//    public void testFlatMapToInt() {
//        System.out.println("flatMapToInt");
//        Function mapper = null;
//        SingletonStream instance = null;
//        IntStream expResult = null;
//        IntStream result = instance.flatMapToInt(mapper);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of flatMapToLong method, of class SingletonStream.
//     */
//    @Test
//    public void testFlatMapToLong() {
//        System.out.println("flatMapToLong");
//        Function mapper = null;
//        SingletonStream instance = null;
//        LongStream expResult = null;
//        LongStream result = instance.flatMapToLong(mapper);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of flatMapToDouble method, of class SingletonStream.
//     */
//    @Test
//    public void testFlatMapToDouble() {
//        System.out.println("flatMapToDouble");
//        Function mapper = null;
//        SingletonStream instance = null;
//        DoubleStream expResult = null;
//        DoubleStream result = instance.flatMapToDouble(mapper);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of distinct method, of class SingletonStream.
//     */
//    @Test
//    public void testDistinct() {
//        System.out.println("distinct");
//        SingletonStream instance = null;
//        SingletonStream expResult = null;
//        SingletonStream result = instance.distinct();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of sorted method, of class SingletonStream.
//     */
//    @Test
//    public void testSorted_0args() {
//        System.out.println("sorted");
//        SingletonStream instance = null;
//        SingletonStream expResult = null;
//        SingletonStream result = instance.sorted();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of sorted method, of class SingletonStream.
//     */
//    @Test
//    public void testSorted_Comparator() {
//        System.out.println("sorted");
//        Comparator comparator = null;
//        SingletonStream instance = null;
//        SingletonStream expResult = null;
//        SingletonStream result = instance.sorted(comparator);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of peek method, of class SingletonStream.
//     */
//    @Test
//    public void testPeek() {
//        System.out.println("peek");
//        Consumer action = null;
//        SingletonStream instance = null;
//        Stream expResult = null;
//        Stream result = instance.peek(action);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of limit method, of class SingletonStream.
//     */
//    @Test
//    public void testLimit() {
//        System.out.println("limit");
//        long maxSize = 0L;
//        SingletonStream instance = null;
//        Stream expResult = null;
//        Stream result = instance.limit(maxSize);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of skip method, of class SingletonStream.
//     */
//    @Test
//    public void testSkip() {
//        System.out.println("skip");
//        long n = 0L;
//        SingletonStream instance = null;
//        Stream expResult = null;
//        Stream result = instance.skip(n);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of forEach method, of class SingletonStream.
//     */
//    @Test
//    public void testForEach() {
//        System.out.println("forEach");
//        Consumer action = null;
//        SingletonStream instance = null;
//        instance.forEach(action);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of forEachOrdered method, of class SingletonStream.
//     */
//    @Test
//    public void testForEachOrdered() {
//        System.out.println("forEachOrdered");
//        Consumer action = null;
//        SingletonStream instance = null;
//        instance.forEachOrdered(action);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of toArray method, of class SingletonStream.
//     */
//    @Test
//    public void testToArray_0args() {
//        System.out.println("toArray");
//        SingletonStream instance = null;
//        Object[] expResult = null;
//        Object[] result = instance.toArray();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of toArray method, of class SingletonStream.
//     */
//    @Test
//    public void testToArray_IntFunction() {
//        System.out.println("toArray");
//        IntFunction generator = null;
//        SingletonStream instance = null;
//        Object[] expResult = null;
//        Object[] result = instance.toArray(generator);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of reduce method, of class SingletonStream.
//     */
//    @Test
//    public void testReduce_GenericType_BinaryOperator() {
//        System.out.println("reduce");
//        SingletonStream instance = null;
//        Object expResult = null;
//        Object result = instance.reduce(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of reduce method, of class SingletonStream.
//     */
//    @Test
//    public void testReduce_BinaryOperator() {
//        System.out.println("reduce");
//        SingletonStream instance = null;
//        Optional expResult = null;
//        Optional result = instance.reduce(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of reduce method, of class SingletonStream.
//     */
//    @Test
//    public void testReduce_3args() {
//        System.out.println("reduce");
//        SingletonStream instance = null;
//        Object expResult = null;
//        Object result = instance.reduce(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of collect method, of class SingletonStream.
//     */
//    @Test
//    public void testCollect_3args() {
//        System.out.println("collect");
//        SingletonStream instance = null;
//        Object expResult = null;
//        Object result = instance.collect(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of collect method, of class SingletonStream.
//     */
//    @Test
//    public void testCollect_Collector() {
//        System.out.println("collect");
//        SingletonStream instance = null;
//        Object expResult = null;
//        Object result = instance.collect(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of min method, of class SingletonStream.
//     */
//    @Test
//    public void testMin() {
//        System.out.println("min");
//        Comparator comparator = null;
//        SingletonStream instance = null;
//        Optional expResult = null;
//        Optional result = instance.min(comparator);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of max method, of class SingletonStream.
//     */
//    @Test
//    public void testMax() {
//        System.out.println("max");
//        Comparator comparator = null;
//        SingletonStream instance = null;
//        Optional expResult = null;
//        Optional result = instance.max(comparator);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of count method, of class SingletonStream.
//     */
//    @Test
//    public void testCount() {
//        System.out.println("count");
//        SingletonStream instance = null;
//        long expResult = 0L;
//        long result = instance.count();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of anyMatch method, of class SingletonStream.
//     */
//    @Test
//    public void testAnyMatch() {
//        System.out.println("anyMatch");
//        Predicate predicate = null;
//        SingletonStream instance = null;
//        boolean expResult = false;
//        boolean result = instance.anyMatch(predicate);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of allMatch method, of class SingletonStream.
//     */
//    @Test
//    public void testAllMatch() {
//        System.out.println("allMatch");
//        Predicate predicate = null;
//        SingletonStream instance = null;
//        boolean expResult = false;
//        boolean result = instance.allMatch(predicate);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of noneMatch method, of class SingletonStream.
//     */
//    @Test
//    public void testNoneMatch() {
//        System.out.println("noneMatch");
//        Predicate predicate = null;
//        SingletonStream instance = null;
//        boolean expResult = false;
//        boolean result = instance.noneMatch(predicate);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findFirst method, of class SingletonStream.
//     */
//    @Test
//    public void testFindFirst() {
//        System.out.println("findFirst");
//        SingletonStream instance = null;
//        Optional expResult = null;
//        Optional result = instance.findFirst();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findAny method, of class SingletonStream.
//     */
//    @Test
//    public void testFindAny() {
//        System.out.println("findAny");
//        SingletonStream instance = null;
//        Optional expResult = null;
//        Optional result = instance.findAny();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of iterator method, of class SingletonStream.
//     */
//    @Test
//    public void testIterator() {
//        System.out.println("iterator");
//        SingletonStream instance = null;
//        Iterator expResult = null;
//        Iterator result = instance.iterator();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of spliterator method, of class SingletonStream.
//     */
//    @Test
//    public void testSpliterator() {
//        System.out.println("spliterator");
//        SingletonStream instance = null;
//        Spliterator expResult = null;
//        Spliterator result = instance.spliterator();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isParallel method, of class SingletonStream.
//     */
//    @Test
//    public void testIsParallel() {
//        System.out.println("isParallel");
//        SingletonStream instance = null;
//        boolean expResult = false;
//        boolean result = instance.isParallel();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of sequential method, of class SingletonStream.
//     */
//    @Test
//    public void testSequential() {
//        System.out.println("sequential");
//        SingletonStream instance = null;
//        SingletonStream expResult = null;
//        SingletonStream result = instance.sequential();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of parallel method, of class SingletonStream.
//     */
//    @Test
//    public void testParallel() {
//        System.out.println("parallel");
//        SingletonStream instance = null;
//        Stream expResult = null;
//        Stream result = instance.parallel();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of unordered method, of class SingletonStream.
//     */
//    @Test
//    public void testUnordered() {
//        System.out.println("unordered");
//        SingletonStream instance = null;
//        Stream expResult = null;
//        Stream result = instance.unordered();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of onClose method, of class SingletonStream.
//     */
//    @Test
//    public void testOnClose() {
//        System.out.println("onClose");
//        Runnable closeHandler = null;
//        SingletonStream instance = null;
//        Stream expResult = null;
//        Stream result = instance.onClose(closeHandler);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of close method, of class SingletonStream.
//     */
//    @Test
//    public void testClose() {
//        System.out.println("close");
//        SingletonStream instance = null;
//        instance.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of get method, of class SingletonStream.
//     */
//    @Test
//    public void testGet() {
//        System.out.println("get");
//        SingletonStream instance = null;
//        Object expResult = null;
//        Object result = instance.get();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isPresent method, of class SingletonStream.
//     */
//    @Test
//    public void testIsPresent() {
//        System.out.println("isPresent");
//        SingletonStream instance = null;
//        boolean expResult = false;
//        boolean result = instance.isPresent();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of ifPresent method, of class SingletonStream.
//     */
//    @Test
//    public void testIfPresent() {
//        System.out.println("ifPresent");
//        Consumer consumer = null;
//        SingletonStream instance = null;
//        instance.ifPresent(consumer);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getOrElse method, of class SingletonStream.
//     */
//    @Test
//    public void testGetOrElse() {
//        System.out.println("getOrElse");
//        Object other = null;
//        SingletonStream instance = null;
//        Object expResult = null;
//        Object result = instance.getOrElse(other);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getOrElseFrom method, of class SingletonStream.
//     */
//    @Test
//    public void testGetOrElseFrom() {
//        System.out.println("getOrElseFrom");
//        Supplier other = null;
//        SingletonStream instance = null;
//        Object expResult = null;
//        Object result = instance.getOrElseFrom(other);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getOrElseThrow method, of class SingletonStream.
//     */
//    @Test
//    public void testGetOrElseThrow() throws Exception {
//        System.out.println("getOrElseThrow");
//        Supplier exceptionSupplier = null;
//        SingletonStream instance = null;
//        Object expResult = null;
//        Object result = instance.getOrElseThrow(exceptionSupplier);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
