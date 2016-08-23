package com.speedment.generator.internal.component;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.component.PathComponent;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.internal.component.InternalOpenSourceComponent;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The default implementation of the {@link PathComponent} interface.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class PathComponentImpl extends InternalOpenSourceComponent implements PathComponent {

    private @Inject ProjectComponent projectComponent;
    
    @Override
    public Path baseDir() {
        return Paths.get("");
    }

    @Override
    public Path packageLocation() {
        final Project project = projectComponent.getProject();
        return Paths.get("", project.getPackageLocation().split("/"));
    }
    
    @Override
    protected String getDescription() {
        return "A component that can be used to determine the path to the project.";
    }
}