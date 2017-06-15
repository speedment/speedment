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
package com.speedment.generator;

import com.speedment.generator.translator.internal.namer.JavaLanguageNamerImpl;
import com.speedment.generator.translator.namer.JavaLanguageNamer;
import org.junit.*;

import static com.speedment.generator.translator.namer.JavaLanguageNamer.toHumanReadable;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author pemi
 */
public class DefaultJavaLanguageNamerTest {

    JavaLanguageNamer instance = new JavaLanguageNamerImpl();
    
    public DefaultJavaLanguageNamerTest() {
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
        assertEquals("MyObject", instance.javaTypeName("my_object"));
        assertEquals("MyObject", instance.javaTypeName("my.object"));
        assertEquals("MyObject", instance.javaTypeName("my object"));
    }

    /**
     * Test of javaVariableName method, of class JavaLanguage.
     */
    @Test
    public void testJavaVariableName() {
        assertEquals("myObject", instance.javaVariableName("my_object"));
        assertEquals("myObject", instance.javaVariableName("my.object"));
        assertEquals("myObject", instance.javaVariableName("my object"));
    }


    @Test
    @Ignore
    public void testJavaStaticFieldName() {
        // Fix me
        assertEquals("MY_OBJECT", instance.javaStaticFieldName("myObject"));
        assertEquals("MY_OBJECT", instance.javaStaticFieldName("my.object"));
        assertEquals("MY_OBJECT", instance.javaStaticFieldName("my object"));
    }

    /**
     * Test of javaNameFromExternal method, of class JavaLanguage.
     */
    @Test
    public void testJavaNameFromExternal() {
        assertEquals("MyObject", instance.javaNameFromExternal("my_object"));
        assertEquals("MyObject", instance.javaNameFromExternal("my.object"));
        assertEquals("MyObject", instance.javaNameFromExternal("my object"));
    }

    /**
     * Test of replaceIfJavaUsedWord method, of class JavaLanguage.
     */
    @Test
    public void testReplaceIfJavaUsedWord() {
        assertEquals("integer_", instance.replaceIfJavaUsedWord("integer"));
    }

    /**
     * Test of javaObjectName method, of class JavaLanguage.
     */
    @Test
    public void testJavaObjectName() {
        assertEquals("Integer", instance.javaObjectName("int"));
        assertEquals("Integer[]", instance.javaObjectName("int[]"));
    }

    /**
     * Test of toUnderscoreSeparated method, of class JavaLanguage.
     */
    @Test
    public void testToUnderscoreSeparated() {
        assertEquals("my_variable_name", instance.toUnderscoreSeparated("myVariableName"));

    }
    
    @Test
    public void testreplaceIfIllegalJavaIdentifierCharacter() {
        assertEquals("_2my", instance.replaceIfIllegalJavaIdentifierCharacter("2my"));
        assertEquals("_2my_test_case_one", instance.replaceIfIllegalJavaIdentifierCharacter("2my test+case.one"));
    }
    
    /**
     * Test of toHumanReadable method, of class JavaLanguage.
     */
    @Test
    public void testToHumanReadable() {        
        assertEquals("My Variable Name", toHumanReadable("myVariableName"));
    }

}
