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
package com.speedment.generator.core.event;

import com.speedment.generator.core.event.trait.ProjectEvent;
import com.speedment.runtime.config.Project;

import static java.util.Objects.requireNonNull;

/**
 * A special event that happens when a project has been fully loaded.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */

public final class ProjectLoaded implements ProjectEvent {

    private final Project project;
    
    public ProjectLoaded(Project project) {
        this.project = requireNonNull(project);
    }
    
    @Override
    public Project project() {
        return project;
    }
}