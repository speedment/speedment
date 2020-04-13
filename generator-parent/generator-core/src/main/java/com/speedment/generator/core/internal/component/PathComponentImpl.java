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
package com.speedment.generator.core.internal.component;

import com.speedment.generator.core.component.PathComponent;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.component.ProjectComponent;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link PathComponent} interface.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class PathComponentImpl implements PathComponent {

    private final ProjectComponent projectComponent;

    public PathComponentImpl(ProjectComponent projectComponent) {
        this.projectComponent = requireNonNull(projectComponent);
    }

    @Override
    public Path baseDir() {
        return Paths.get("");
    }

    @Override
    public Path packageLocation() {
        final Project project = projectComponent.getProject();
        return Paths.get("", project.getPackageLocation().split("/"));
    }
}