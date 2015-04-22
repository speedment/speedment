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
package com.speedment.util.stream.builder;

import com.speedment.util.stream.builder.demo.CollectionsStreamTerminator;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
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
public class CollectionsStreamTerminatorTest {

    private final static String TEST_VECTOR = "CBADEFGA";

    private Collection<String> collection;
    private CollectionsStreamTerminator<String> instance;

    public CollectionsStreamTerminatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        collection = TEST_VECTOR.chars().mapToObj(c -> Character.toString((char) c)).collect(toList());
        instance = new CollectionsStreamTerminator<>(collection);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCount() {
        System.out.println("count");
        long expResult = TEST_VECTOR.length();
        long result = instance.stream().sorted().count();
        assertEquals(expResult, result);
    }

    @Test
    public void testFindAny() {
        System.out.println("findAny");
        Optional<String> result = instance.stream().sorted().map(Function.identity()).distinct().findAny();
        assertTrue(result.isPresent());
        assertTrue(TEST_VECTOR.contains(result.get()));
    }

    @Test
    public void testCountComplicated() {
        long result = instance.stream().sorted().mapToInt(String::length).count();
        assertEquals(TEST_VECTOR.length(), result);
    }

}
