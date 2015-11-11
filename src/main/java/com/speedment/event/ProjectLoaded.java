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
package com.speedment.event;

import com.speedment.annotation.Api;
import com.speedment.config.Project;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @since 2.3
 */
@Api(version="2.3")
public final class ProjectLoaded implements Event {
    
    private final static String NAME = "PROJECT_LOADED";
    private final Project project;
    
    public static ProjectLoaded EVENT = new ProjectLoaded();
    
    private ProjectLoaded() {
        this.project = null;
    }
    
    public ProjectLoaded(Project project) {
        this.project = requireNonNull(project);
    }

    @Override
    public String name() {
        return NAME;
    }
    
    public Project project() {
        return project;
    }
}