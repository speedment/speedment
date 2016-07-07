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
package com.speedment.runtime.internal.runtime.typemapping;

import org.junit.*;

import static org.junit.Assert.*;

/**
 *
 * @author pemi
 */
public class StandardJavaTypeMappingTest {

    public StandardJavaTypeMappingTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testParse() {
        assertEquals(Boolean.TRUE, StandardJavaTypeMapping.BOOLEAN.parse("true"));
        assertEquals(Boolean.FALSE, StandardJavaTypeMapping.BOOLEAN.parse("false"));
        assertEquals(Byte.valueOf((byte) -47), StandardJavaTypeMapping.BYTE.parse("-47"));
        assertEquals(Short.valueOf((byte) -47), StandardJavaTypeMapping.SHORT.parse("-47"));
        assertEquals(Integer.valueOf(-47), StandardJavaTypeMapping.INTEGER.parse("-47"));
    }

}
