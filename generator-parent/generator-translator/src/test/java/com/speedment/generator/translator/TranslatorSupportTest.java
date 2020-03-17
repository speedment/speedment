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

import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.namer.JavaLanguageNamer;
import com.speedment.generator.translator.provider.StandardJavaLanguageNamer;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.core.exception.SpeedmentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author Per Minborg
 */
final class TranslatorSupportTest extends SimpleModel {

    private static final String PATH = "com.company.myproject.mydbms.myschema.user";
    private static final Injector INJECTOR = injector();


    private TranslatorSupport<Table> instance;

    @BeforeEach
    void setUp() throws InstantiationException {
        instance = new TranslatorSupport<>(INJECTOR, table);
    }

    @Test
    void testNamer() {
        assertEquals(INJECTOR.getOrThrow(JavaLanguageNamer.class), instance.namer());
    }

    @Test
    void testDocument() {
        assertEquals(table, instance.tableOrThrow());
    }

    @Test
    void testEntityName() {
        assertEquals("User", instance.entityName());
    }

    @Test
    void testEntityImplName() {
        assertEquals("UserImpl", instance.entityImplName());
    }

    @Test
    void testGeneratedEntityName() {
        assertEquals("GeneratedUser", instance.generatedEntityName());
    }

    @Test
    void testGeneratedEntityImplName() {
        assertEquals("GeneratedUserImpl", instance.generatedEntityImplName());
    }

    @Test
    void testManagerName() {
        assertEquals("UserManager", instance.managerName());
    }

    @Test
    void testManagerImplName() {
        assertEquals("UserManagerImpl", instance.managerImplName());
    }

    @Test
    void testGeneratedManagerName() {
        assertEquals("GeneratedUserManager", instance.generatedManagerName());
    }

    @Test
    void testGeneratedManagerImplName() {
        assertEquals("GeneratedUserManagerImpl", instance.generatedManagerImplName());
    }

    @Test
    void testEntityType() {
        assertEquals(fullName("User"), instance.entityType().getTypeName());
    }

    @Test
    void testEntityImplType() {
        assertEquals(fullName("UserImpl"), instance.entityImplType().getTypeName());
    }

    @Test
     void testGeneratedEntityType() {
        assertEquals(fullNameGen("GeneratedUser"), instance.generatedEntityType().getTypeName());
    }

    @Test
    void testGeneratedEntityImplType() {
        assertEquals(fullNameGen("GeneratedUserImpl"), instance.generatedEntityImplType().getTypeName());
    }

    @Test
    void testManagerType() {
        assertEquals(fullName("UserManager"), instance.managerType().getTypeName());
    }

    @Test
    void testManagerImplType() {
        assertEquals(fullName("UserManagerImpl"), instance.managerImplType().getTypeName());
    }

    @Test
    void testGeneratedManagerType() {
        assertEquals(fullNameGen("GeneratedUserManager"), instance.generatedManagerType().getTypeName());
    }

    @Test
    void testGeneratedManagerImplType() {
        assertEquals(fullNameGen("GeneratedUserManagerImpl"), instance.generatedManagerImplType().getTypeName());
    }

    @Test
    void testVariableName_0args() {
        assertEquals("user", instance.variableName());
    }

    @Test
    void testVariableName_HasAlias() {
        assertEquals("mySchema", instance.variableName(schema));
    }

    @Test
    void testTypeName_0args() {
        assertEquals("User", instance.typeName());
    }

    @Test
    void testTypeName_HasAlias() {
        assertEquals("MySchema", instance.typeName(schema));
    }

    @Test
    void testTypeName_Project() {
        assertEquals("MyProject", instance.typeName(project));
    }

    @Test
    void testManagerTypeName_0args() {
        assertEquals("UserManager", instance.managerTypeName());
    }

    @Test
    void testManagerTypeName_HasAlias() {
        assertEquals("UserManager", instance.managerTypeName(table));
    }

    @Test
    void testFullyQualifiedTypeName_0args() {
        assertEquals(fullName("User"), instance.fullyQualifiedTypeName());
    }

    @Test
    void testFullyQualifiedTypeName_String() {
        assertEquals(fullName("olle.User"), instance.fullyQualifiedTypeName("olle"));
    }

    @Test
    void testFullyQualifiedTypeName_String_String() {
        final String result = instance.fullyQualifiedTypeName("subPath", "filePrefix");
        assertEquals(PATH + ".subPath." + "FilePrefix" + instance.typeName(table), result);
    }

    @Test
    void testBasePackageName() {
        assertEquals(PATH, instance.basePackageName());
    }

    @Test
    void testBaseDirectoryName() {
        assertEquals(PATH.replace('.', '/'), instance.baseDirectoryName());
    }

    @Test
    void testProject() {
        assertEquals(project, instance.project().get());
    }

    @Test
    void testDbms() {
        assertEquals(dbms, instance.dbms().get());
    }

    @Test
    void testSchema() {
        assertEquals(schema, instance.schema().get());
    }

    @Test
    void testTable() {
        assertEquals(table, instance.table().get());
    }

    @Test
    void testColumn() {
        assertEquals(Optional.empty(), instance.column());
    }

    @Test
    void testProjectOrThrow() {
        assertEquals(project, instance.projectOrThrow());
    }

    @Test
    void testDbmsOrThrow() {
        assertEquals(dbms, instance.dbmsOrThrow());
    }

    @Test
    void testSchemaOrThrow() {
        assertEquals(schema, instance.schemaOrThrow());
    }

    @Test
    void testTableOrThrow() {
        assertEquals(table, instance.tableOrThrow());
    }

    @Test
    void testColumnOrThrow() {
        assertThrows(IllegalStateException.class, () -> instance.columnOrThrow());
    }

    @Test
    void testShortTableName() {
        final TranslatorSupport<Table> support = new TranslatorSupport<>(injector(), table2);
        assertEquals("sP", support.variableName());
    }

    private String fullName(String s) {
        return PATH + "." + s;
    }

    private String fullNameGen(String s) {
        return PATH + ".generated." + s;
    }

    private static Injector injector() {
        try {
            return Injector.builder()
                .withComponent(StandardJavaLanguageNamer.class)
                .build();
        } catch (InstantiationException e) {
            throw new SpeedmentException(e);
        }
    }

}