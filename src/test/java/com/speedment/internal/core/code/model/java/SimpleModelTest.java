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
package com.speedment.internal.core.code.model.java;

import com.speedment.Speedment;
import com.speedment.config.Column;
import com.speedment.config.Dbms;
import com.speedment.config.PrimaryKeyColumn;
import com.speedment.config.Project;
import com.speedment.config.Schema;
import com.speedment.config.Table;
import com.speedment.internal.core.config.ProjectImpl;
import com.speedment.internal.core.platform.SpeedmentFactory;
import org.junit.Before;

/**
 *
 * @author pemi
 */
public abstract class SimpleModelTest {
    protected static final String TABLE_NAME = "user";
    protected static final String COLUMN_NAME = "first_name";

    protected Speedment speedment;
    protected Project project;
    protected Dbms dbms;
    protected Schema schema;
    protected Table table;
    protected Column column;
    protected PrimaryKeyColumn pkColumn;

    @Before
    public void setUp() {
        speedment = SpeedmentFactory.newSpeedmentInstance();
        project = new ProjectImpl(speedment);
        dbms = project.addNewDbms(speedment);
        schema = dbms.addNewSchema();
        table = schema.addNewTable();
        column = table.addNewColumn();
        pkColumn = table.addNewPrimaryKeyColumn();
        
        project.setName("myProject");
        dbms.setName("myDbms");
        schema.setName("myCoolApp");
        table.setName(TABLE_NAME);
        column.setName(COLUMN_NAME);
        pkColumn.setName(COLUMN_NAME);
    }
}
