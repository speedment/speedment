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
package com.speedment.orm.config.model;

import com.speedment.orm.annotations.Api;
import com.speedment.orm.platform.Component;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public interface ConfigEntityFactory extends Component {

    default ProjectManager newProjectManager() {
        return newOf(ProjectManager.class);
    }

    default Project newProject() {
        return newOf(Project.class);
    }

    default Dbms newDbms() {
        return newOf(Dbms.class);
    }

    default public Schema newSchema() {
        return newOf(Schema.class);
    }

    default Table newTable() {
        return newOf(Table.class);
    }

    default Column newColumn() {
        return newOf(Column.class);
    }

    default Index newIndex() {
        return newOf(Index.class);
    }

    default <T extends ConfigEntity<T, ?, ?>> T newOf(T rawModel) {
        return newOf(rawModel.getInterfaceMainClass());
    }

    <T extends ConfigEntity<T, ?, ?>> T newOf(Class<T> interfaceMainClass);

}
