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
package com.speedment.core.config.model;

import com.speedment.core.annotations.Api;
import com.speedment.core.config.model.aspects.Enableable;
import com.speedment.core.config.model.aspects.Node;
import com.speedment.core.config.model.aspects.Parent;
import com.speedment.core.config.model.impl.ProjectManagerImpl;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = "2.0")
public interface ProjectManager extends Node, Enableable, Parent<Project> {

    enum Holder { HOLDER;
        private Supplier<ProjectManager> provider = ProjectManagerImpl::new;
    }

    static void setSupplier(Supplier<ProjectManager> provider) {
        Holder.HOLDER.provider = provider;
    }

    static ProjectManager newProjectManager() {
        return Holder.HOLDER.provider.get();
    }

    @Override
    default Class<ProjectManager> getInterfaceMainClass() {
        return ProjectManager.class;
    }

    default Project addNewProject() {
        final Project e = Project.newProject();
        add(e);
        return e;
    }
}
