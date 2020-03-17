/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.generator.translator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.speedment.generator.translator.namer.JavaLanguageNamer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author pemi
 */
@DisplayName("Test class JavaLanguageNamer")
final class DefaultJavaLanguageNamerTest {

    private final JavaLanguageNamer instance = JavaLanguageNamer.create();

    @DisplayName("Test of javaTypeName method")
    @Test
    void testJavaTypeName() {
        assertEquals("MyObject", instance.javaTypeName("my_object"));
        assertEquals("MyObject", instance.javaTypeName("my.object"));
        assertEquals("MyObject", instance.javaTypeName("my object"));
    }

    @DisplayName("Test of javaVariableName method")
    @Test
    void testJavaVariableName() {
        assertEquals("myObject", instance.javaVariableName("my_object"));
        assertEquals("myObject", instance.javaVariableName("my.object"));
        assertEquals("myObject", instance.javaVariableName("my object"));
    }


    @DisplayName("Test of javaStaticFieldName method")
    @Test
    void testJavaStaticFieldName() {
        assertEquals("MY_OBJECT", instance.javaStaticFieldName("myObject"));
        assertEquals("MY_OBJECT", instance.javaStaticFieldName("my.object"));
        assertEquals("MY_OBJECT", instance.javaStaticFieldName("my object"));
    }

    @DisplayName("Test of javaNameFromExternal method")
    @Test
    void testJavaNameFromExternal() {
        assertEquals("MyObject", instance.javaNameFromExternal("my_object"));
        assertEquals("MyObject", instance.javaNameFromExternal("my.object"));
        assertEquals("MyObject", instance.javaNameFromExternal("my object"));
    }

    @DisplayName("Test of replaceIfJavaUsedWord method")
    @Test
    void testReplaceIfJavaUsedWord() {
        assertEquals("integer_", instance.replaceIfJavaUsedWord("integer"));
    }

    @DisplayName("Test of javaObjectName method")
    @Test
    void testJavaObjectName() {
        assertEquals("Integer", instance.javaObjectName("int"));
        assertEquals("Integer[]", instance.javaObjectName("int[]"));
    }

    @DisplayName("Test of toUnderscoreSeparated method")
    @Test
    void testToUnderscoreSeparated() {
        assertEquals("my_variable_name", instance.toUnderscoreSeparated("myVariableName"));

    }

    @DisplayName("Test of replaceIfIllegalJavaIdentifierCharacter method")
    @Test
    void testReplaceIfIllegalJavaIdentifierCharacter() {
        assertEquals("_2my", instance.replaceIfIllegalJavaIdentifierCharacter("2my"));
        assertEquals("_2my_test_case_one", instance.replaceIfIllegalJavaIdentifierCharacter("2my test+case.one"));
    }
    
    @DisplayName("Test of toHumanReadable method")
    @Test
    void testToHumanReadable() {
        assertEquals("My Variable Name", JavaLanguageNamer.toHumanReadable("myVariableName"));
    }

}
