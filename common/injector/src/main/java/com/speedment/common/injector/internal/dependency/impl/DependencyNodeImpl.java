package com.speedment.common.injector.internal.dependency.impl;

import com.speedment.common.injector.internal.dependency.Dependency;
import com.speedment.common.injector.internal.dependency.DependencyNode;
import com.speedment.common.injector.platform.State;
import static java.util.Collections.newSetFromMap;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class DependencyNodeImpl implements DependencyNode {

    private final Class<?> representedType;
    private final Set<Dependency> dependencies;
    private final State currentState;

    public DependencyNodeImpl(Class<?> representedType) {
        this.representedType = requireNonNull(representedType);
        this.dependencies    = newSetFromMap(new ConcurrentHashMap<>());
        this.currentState    = State.CREATED;
    }
    
    @Override
    public Class<?> getRepresentedType() {
        return representedType;
    }

    @Override
    public Set<Dependency> getDependencies() {
        return dependencies;
    }

    @Override
    public State getCurrentState() {
        return currentState;
    }
}
