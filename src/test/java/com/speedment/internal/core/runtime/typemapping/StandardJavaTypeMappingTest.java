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
package com.speedment.internal.core.runtime.typemapping;

import com.speedment.internal.core.runtime.typemapping.StandardJavaTypeMapping;
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
        System.out.println("parse");
        assertEquals(Boolean.TRUE, StandardJavaTypeMapping.BOOLEAN.parse("true"));
        assertEquals(Boolean.FALSE, StandardJavaTypeMapping.BOOLEAN.parse("false"));
        assertEquals(Byte.valueOf((byte) -47), StandardJavaTypeMapping.BYTE.parse("-47"));
        assertEquals(Short.valueOf((byte) -47), StandardJavaTypeMapping.SHORT.parse("-47"));
        assertEquals(Integer.valueOf(-47), StandardJavaTypeMapping.INTEGER.parse("-47"));
    }

}
