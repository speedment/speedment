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
package com.speedment.runtime.core.internal.component;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.ApplicationMetadata;
import com.speedment.runtime.core.component.ProjectComponent;

import static com.speedment.common.injector.State.INITIALIZED;
import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link ProjectComponent}-interface.
 * 
 * @author  Emil Forslund
 * @since   2.0.0
 */
public final class ProjectComponentImpl implements ProjectComponent {

    private Project project;
    
    @ExecuteBefore(INITIALIZED)
    void loadProjectFromMetadata(@WithState(INITIALIZED) ApplicationMetadata metadata) {
        project = metadata.makeProject();
    }

    @Override
    public Project getProject() {
        requireNonNull(project, 
            "Metadata has not yet been loaded! This is probably due to an " + 
            "incorrect initialization order."
        );
        
        return project;
    }

    @Override
    public void setProject(Project project) {
        this.project = requireNonNull(project);
    }
}