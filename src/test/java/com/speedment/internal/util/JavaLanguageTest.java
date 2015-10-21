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
package com.speedment.internal.util;

import static com.speedment.internal.util.JavaLanguage.javaNameFromExternal;
import static com.speedment.internal.util.JavaLanguage.javaObjectName;
import static com.speedment.internal.util.JavaLanguage.javaStaticFieldName;
import static com.speedment.internal.util.JavaLanguage.javaTypeName;
import static com.speedment.internal.util.JavaLanguage.javaVariableName;
import static com.speedment.internal.util.JavaLanguage.replaceIfJavaUsedWord;
import static com.speedment.internal.util.JavaLanguage.toHumanReadable;
import static com.speedment.internal.util.JavaLanguage.toUnderscoreSeparated;
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
public class JavaLanguageTest {

    public JavaLanguageTest() {
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

    /**
     * Test of javaTypeName method, of class JavaLanguage.
     */
    @Test
    public void testJavaTypeName() {
        System.out.println("javaTypeName");
        assertEquals("MyObject", javaTypeName("my_object"));
        assertEquals("MyObject", javaTypeName("my.object"));
        assertEquals("MyObject", javaTypeName("my object"));
    }

    /**
     * Test of javaVariableName method, of class JavaLanguage.
     */
    @Test
    public void testJavaVariableName() {
        System.out.println("javaVariableName");
        assertEquals("myObject", javaVariableName("my_object"));
        assertEquals("myObject", javaVariableName("my.object"));
        assertEquals("myObject", javaVariableName("my object"));
    }

    /**
     * Test of javaStaticFieldName method, of class JavaLanguage.
     */
    @Test
    public void testJavaStaticFieldName() {
        System.out.println("javaStaticFieldName");
        assertEquals("MY_OBJECT", javaStaticFieldName("myObject"));
        assertEquals("MY_OBJECT", javaStaticFieldName("my.object"));
        assertEquals("MY_OBJECT", javaStaticFieldName("my object"));
    }

    /**
     * Test of javaNameFromExternal method, of class JavaLanguage.
     */
    @Test
    public void testJavaNameFromExternal() {
        System.out.println("javaNameFromExternal");
        assertEquals("MyObject", javaNameFromExternal("my_object"));
        assertEquals("MyObject", javaNameFromExternal("my.object"));
        assertEquals("MyObject", javaNameFromExternal("my object"));
    }

    /**
     * Test of replaceIfJavaUsedWord method, of class JavaLanguage.
     */
    @Test
    public void testReplaceIfJavaUsedWord() {
        System.out.println("replaceIfJavaUsedWord");
        assertEquals("integer_", replaceIfJavaUsedWord("integer"));
    }

    /**
     * Test of javaObjectName method, of class JavaLanguage.
     */
    @Test
    public void testJavaObjectName() {
        System.out.println("javaObjectName");
        assertEquals("Integer", javaObjectName("int"));
        assertEquals("Integer[]", javaObjectName("int[]"));
    }

    /**
     * Test of toUnderscoreSeparated method, of class JavaLanguage.
     */
    @Test
    public void testToUnderscoreSeparated() {
        System.out.println("toUnderscoreSeparated");
        assertEquals("my_variable_name", toUnderscoreSeparated("myVariableName"));

    }

    /**
     * Test of toHumanReadable method, of class JavaLanguage.
     */
    @Test
    public void testToHumanReadable() {
        System.out.println("toHumanReadable");
        assertEquals("My Variable Name", toHumanReadable("myVariableName"));
    }

}
