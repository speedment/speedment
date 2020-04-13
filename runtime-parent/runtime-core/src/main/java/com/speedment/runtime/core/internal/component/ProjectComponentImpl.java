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
package com.speedment.runtime.core.internal.component;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.ApplicationMetadata;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.component.ProjectComponent;

import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.injector.State.STARTED;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link ProjectComponent}-interface.
 *
 * @author Emil Forslund
 * @since 2.0.0
 */
public final class ProjectComponentImpl implements ProjectComponent {

    private static final Logger LOGGER = LoggerManager.getLogger(ProjectComponentImpl.class);
    private Project project;

    @ExecuteBefore(INITIALIZED)
    public void loadProjectFromMetadata(@WithState(INITIALIZED) ApplicationMetadata metadata) {
        project = requireNonNull(metadata.makeProject(),
            "Metadata has not yet been loaded! This is probably due to an "
                + "incorrect initialization order."
        );
    }

    @ExecuteBefore(STARTED)
    public void checkSpeedmentVersion(Injector injector) {
        injector.get(InfoComponent.class).ifPresent(ic -> {
            final String expected = ic.getEditionAndVersionString();
            final String actual = project.getSpeedmentVersion().orElse(null);
            if (!expected.equals(actual)) {
                LOGGER.warn(format(
                    "Code generated using '%s' and running with '%s'. Make sure "
                    + "versions and editions match to avoid performance and "
                    + "stability issues.", actual, expected));
            }
        }
        );
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
