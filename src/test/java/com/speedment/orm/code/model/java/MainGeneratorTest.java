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
package com.speedment.orm.code.model.java;

import com.speedment.orm.config.model.Project;
import com.speedment.orm.config.model.impl.ProjectImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author pemi
 */
public class MainGeneratorTest {

    private static final String TABLE_NAME = "user";

    Project project = new ProjectImpl().setName("myProject")
            .addNewDbms().setName("myDbms")
            .addNewSchema().setName("myCoolApp")
            .addNewTable().setName(TABLE_NAME)
            .addNewColumn().setName("first_name").getParent(Project.class).get();

    public MainGeneratorTest() {
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
    public void testAccept() {
        System.out.println("accept");
        final MainGenerator instance = new MainGenerator();
        instance.accept(project);
    }

}
