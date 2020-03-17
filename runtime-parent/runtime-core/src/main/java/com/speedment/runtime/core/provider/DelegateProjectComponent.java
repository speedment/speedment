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
package com.speedment.runtime.core.provider;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.ApplicationMetadata;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.internal.component.ProjectComponentImpl;

import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.injector.State.STARTED;

/**
 *
 * @author Per Minborg
 * @since 3.2.0
 */
public final class DelegateProjectComponent implements ProjectComponent {

    private ProjectComponentImpl inner;

    public DelegateProjectComponent() {
        this.inner = new ProjectComponentImpl();
    }

    @ExecuteBefore(INITIALIZED)
    public void loadProjectFromMetadata(@WithState(INITIALIZED) ApplicationMetadata metadata) {
        inner.loadProjectFromMetadata(metadata);
    }

    @ExecuteBefore(STARTED)
    public void checkSpeedmentVersion(Injector injector) {
        inner.checkSpeedmentVersion(injector);
    }

    @Override
    public Project getProject() {
        return inner.getProject();
    }

    @Override
    public void setProject(Project project) {
        inner.setProject(project);
    }
}
