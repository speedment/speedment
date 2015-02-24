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
package com.speedment.orm.code.model.java.entity;

import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.java.JavaGenerator;
import com.speedment.codegen.java.JavaInstaller;
import com.speedment.codegen.lang.controller.AutoImports;
import com.speedment.codegen.lang.models.ClassOrInterface;
import com.speedment.codegen.lang.models.File;
import com.speedment.orm.config.model.Project;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.config.model.impl.ProjectImpl;
import java.util.Optional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author pemi
 */
public class EntityTranslatorTest {
    
    private static final String TABLE_NAME = "user";
    
    Project project = new ProjectImpl().setName("myProject")
            .addNewDbms().setName("myDbms")
            .addNewSchema().setName("myCoolApp")
            .addNewTable().setName(TABLE_NAME)
            .addNewColumn().setName("first_name").getParent(Project.class).get();
    
    public EntityTranslatorTest() {
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
    public void testApply() {
        System.out.println("apply");
        
        final CodeGenerator cg = new JavaGenerator(
                new JavaInstaller()
        );
        
        Table table = project.traversalOf(Table.class)
                .filter(e -> TABLE_NAME.equals(e.getName()))
                .findAny().get();
        
        final EntityTranslator instance = new EntityTranslator(table);
        final File file = instance.get();
        
        file.call(new AutoImports(cg.getDependencyMgr()));
        
        final Optional<String> code = cg.on(file);
        
        System.out.println(code.get());
        
    }
    
}
