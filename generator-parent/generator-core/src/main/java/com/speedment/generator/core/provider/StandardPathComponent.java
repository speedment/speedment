package com.speedment.generator.core.provider;

import com.speedment.generator.core.component.PathComponent;
import com.speedment.generator.core.internal.component.PathComponentImpl;
import com.speedment.runtime.core.component.ProjectComponent;

import java.nio.file.Path;

public final class StandardPathComponent implements PathComponent {

    private final PathComponent inner;

    public StandardPathComponent(ProjectComponent projectComponent) {
        this.inner = new PathComponentImpl(projectComponent);
    }

    @Override
    public Path baseDir() {
        return inner.baseDir();
    }

    @Override
    public Path packageLocation() {
        return inner.packageLocation();
    }
}
