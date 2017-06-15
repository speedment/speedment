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

import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.generator.translator.namer.JavaLanguageNamer;
import com.speedment.runtime.config.Table;
import org.junit.*;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 * @author Per Minborg
 */
public class TranslatorSupportTest extends SimpleModel {

    private static final String PATH = "com.company.myproject.mydbms.myschema.user";

    private TranslatorSupport<Table> instance;

    public TranslatorSupportTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = new TranslatorSupport<>(
            speedment.getOrThrow(Injector.class), table
        );
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testNamer() {
        assertEquals(speedment.getOrThrow(JavaLanguageNamer.class), instance.namer());
    }

    @Test
    public void testDocument() {
        assertEquals(table, instance.tableOrThrow());
    }

    @Test
    public void testEntityName() {
        assertEquals("User", instance.entityName());
    }

    @Test
    public void testEntityImplName() {
        assertEquals("UserImpl", instance.entityImplName());
    }

    @Test
    public void testGeneratedEntityName() {
        assertEquals("GeneratedUser", instance.generatedEntityName());
    }

    @Test
    public void testGeneratedEntityImplName() {
        assertEquals("GeneratedUserImpl", instance.generatedEntityImplName());
    }

    @Test
    public void testManagerName() {
        assertEquals("UserManager", instance.managerName());
    }

    @Test
    public void testManagerImplName() {
        assertEquals("UserManagerImpl", instance.managerImplName());
    }

    @Test
    public void testGeneratedManagerName() {
        assertEquals("GeneratedUserManager", instance.generatedManagerName());
    }

    @Test
    public void testGeneratedManagerImplName() {
        assertEquals("GeneratedUserManagerImpl", instance.generatedManagerImplName());
    }

    @Test
    public void testEntityType() {
        assertEquals(fullName("User"), instance.entityType().getTypeName());
    }

    @Test
    public void testEntityImplType() {
        assertEquals(fullName("UserImpl"), instance.entityImplType().getTypeName());
    }

    @Test
    public void testGeneratedEntityType() {
        assertEquals(fullNameGen("GeneratedUser"), instance.generatedEntityType().getTypeName());
    }

    @Test
    public void testGeneratedEntityImplType() {
        assertEquals(fullNameGen("GeneratedUserImpl"), instance.generatedEntityImplType().getTypeName());
    }

    @Test
    public void testManagerType() {
        assertEquals(fullName("UserManager"), instance.managerType().getTypeName());
    }

    @Test
    public void testManagerImplType() {
        assertEquals(fullName("UserManagerImpl"), instance.managerImplType().getTypeName());
    }

    @Test
    public void testGeneratedManagerType() {
        assertEquals(fullNameGen("GeneratedUserManager"), instance.generatedManagerType().getTypeName());
    }

    @Test
    public void testGeneratedManagerImplType() {
        assertEquals(fullNameGen("GeneratedUserManagerImpl"), instance.generatedManagerImplType().getTypeName());
    }

    @Test
    public void testVariableName_0args() {
        assertEquals("user", instance.variableName());
    }

    @Test
    public void testVariableName_HasAlias() {
        assertEquals("mySchema", instance.variableName(schema));
    }

    @Test
    public void testTypeName_0args() {
        assertEquals("User", instance.typeName());
    }

    @Test
    public void testTypeName_HasAlias() {
        assertEquals("MySchema", instance.typeName(schema));
    }

    @Test
    public void testTypeName_Project() {
        assertEquals("MyProject", instance.typeName(project));
    }

    @Test
    public void testManagerTypeName_0args() {
        assertEquals("UserManager", instance.managerTypeName());
    }

    @Test
    public void testManagerTypeName_HasAlias() {
        assertEquals("UserManager", instance.managerTypeName(table));
    }

    @Test
    public void testFullyQualifiedTypeName_0args() {
        assertEquals(fullName("User"), instance.fullyQualifiedTypeName());
    }

    @Test
    public void testFullyQualifiedTypeName_String() {
        assertEquals(fullName("olle.User"), instance.fullyQualifiedTypeName("olle"));
    }

    @Test
    @Ignore
    public void testFullyQualifiedTypeName_String_String() {
        // Todo: implement this test
        String result = instance.fullyQualifiedTypeName("subPath", "filePrefix");
        fail("The test case is a prototype.");
    }

    @Test
    public void testBasePackageName() {
        assertEquals(PATH, instance.basePackageName());
    }

    @Test
    public void testBaseDirectoryName() {
        assertEquals(PATH.replace('.', '/'), instance.baseDirectoryName());
    }

    @Test
    public void testProject() {
        assertEquals(project, instance.project().get());
    }

    @Test
    public void testDbms() {
        assertEquals(dbms, instance.dbms().get());
    }

    @Test
    public void testSchema() {
        assertEquals(schema, instance.schema().get());
    }

    @Test
    public void testTable() {
        assertEquals(table, instance.table().get());
    }

    @Test
    public void testColumn() {
        assertEquals(Optional.empty(), instance.column());
    }

    @Test
    public void testProjectOrThrow() {
        assertEquals(project, instance.projectOrThrow());
    }

    @Test
    public void testDbmsOrThrow() {
        assertEquals(dbms, instance.dbmsOrThrow());
    }

    @Test
    public void testSchemaOrThrow() {
        assertEquals(schema, instance.schemaOrThrow());
    }

    @Test
    public void testTableOrThrow() {
        assertEquals(table, instance.tableOrThrow());
    }

    @Test(expected = java.lang.IllegalStateException.class)
    public void testColumnOrThrow() {
        instance.columnOrThrow();
    }

    @Test
    public void testShortTableName() {
        final TranslatorSupport<Table> support = new TranslatorSupport<>(
            speedment.getOrThrow(Injector.class), table2
        );
        
        assertEquals("sP", support.variableName());
    }

    private String fullName(String s) {
        return PATH + "." + s;
    }

    private String fullNameGen(String s) {
        return PATH + ".generated." + s;
    }
}