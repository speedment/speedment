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
package com.speedment.orm.config.model.impl;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.ConfigEntityFactory;
import com.speedment.orm.config.model.Dbms;
import com.speedment.orm.config.model.Index;
import com.speedment.orm.config.model.Project;
import com.speedment.orm.config.model.ProjectManager;
import com.speedment.orm.config.model.Schema;
import com.speedment.orm.config.model.Table;

/**
 *
 * @author pemi
 */
public class ConfigEntityFactoryImpl implements ConfigEntityFactory {

    @Override
    public ProjectManager newProjectManager() {
        return new ProjectManagerImpl();
    }

    @Override
    public Project newProject() {
        return new ProjectImpl();
    }

    @Override
    public Dbms newDbms() {
        return new DbmsImpl();
    }

    @Override
    public Schema newSchema() {
        return new SchemaImpl();
    }

    @Override
    public Table newTable() {
        return new TableImpl();
    }

    @Override
    public Column newColumn() {
        return new ColumnImpl();
    }

    @Override
    public Index newIndex() {
        return new IndexImpl();
    }

}
