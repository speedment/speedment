/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.internal.component;

import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.config.Project;
import static java.util.Objects.requireNonNull;

public final class ProjectComponentImpl extends InternalOpenSourceComponent implements ProjectComponent {

    private Project project;

    @Override
    protected String getDescription() {
        return "Holds a reference to the project node where all the project-specific " + 
            "configuration data is stored.";
    }

    @Override
    public Project getProject() {
        return project;
    }

    @Override
    public void setProject(Project project) {
        this.project = requireNonNull(project);
    }
}