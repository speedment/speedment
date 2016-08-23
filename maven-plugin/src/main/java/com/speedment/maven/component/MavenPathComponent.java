package com.speedment.maven.component;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.component.PathComponent;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.internal.license.AbstractSoftware;
import static com.speedment.runtime.internal.license.OpenSourceLicense.APACHE_2;
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