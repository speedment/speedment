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
package com.speedment.maven.component;

import com.speedment.generator.component.PathComponent;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.internal.license.AbstractSoftware;
import static com.speedment.runtime.license.OpenSourceLicense.APACHE_2;
import com.speedment.runtime.license.Software;
import java.nio.file.Path;
import org.apache.maven.project.MavenProject;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class MavenPathComponent implements PathComponent {

    private MavenProject mavenProject;
    private @Inject ProjectComponent projectComponent;
    
    public void setMavenProject(MavenProject mavenProject) {
        this.mavenProject = mavenProject;
    }
    
    @Override
    public Path baseDir() {
        return mavenProject.getBasedir().toPath();
    }

    @Override
    public Path packageLocation() {
        final Project project = projectComponent.getProject();
        return baseDir().resolve(project.getPackageLocation());
    }

    @Override
    public Software asSoftware() {
        return AbstractSoftware.with("Maven Path Component", "3.0.0", APACHE_2);
    }

    @Override
    public boolean isInternal() {
        return true;
    }
}