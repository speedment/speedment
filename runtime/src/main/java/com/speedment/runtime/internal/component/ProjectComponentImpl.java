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

import com.speedment.runtime.Speedment;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.config.db.Project;
import com.speedment.runtime.internal.license.AbstractSoftware;
import com.speedment.runtime.internal.license.OpenSourceLicense;
import com.speedment.runtime.license.Software;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;

public final class ProjectComponentImpl extends InternalOpenSourceComponent implements ProjectComponent {

    private Project project;

    public ProjectComponentImpl(Speedment speedment) {
        super(speedment);
    }

    @Override
    public Project getProject() {
        return project;
    }

    @Override
    public void setProject(Project project) {
        this.project = requireNonNull(project);
    }

    @Override
    public Stream<Software> getDependencies() {
        return Stream.of(DEPENDENCIES);
    }

    private final static Software[] DEPENDENCIES = {
        AbstractSoftware.with("Gson", "2.6.2", OpenSourceLicense.APACHE_2)
    };

    @Override
    public ProjectComponent defaultCopy(Speedment speedment) {
        return new ProjectComponentImpl(speedment);
    }

}
