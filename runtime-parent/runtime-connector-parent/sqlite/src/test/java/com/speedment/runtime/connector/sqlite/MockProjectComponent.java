package com.speedment.runtime.connector.sqlite;

import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.component.ProjectComponent;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.10
 */
public final class MockProjectComponent implements ProjectComponent {

    private Project project;

    @Override
    public Project getProject() {
        return requireNonNull(project);
    }

    @Override
    public void setProject(Project project) {
        this.project = requireNonNull(project);
    }
}
