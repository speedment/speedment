package com.speedment.common.injector.internal.dependency.impl;

import com.speedment.common.injector.internal.dependency.Dependency;
import com.speedment.common.injector.internal.dependency.DependencyNode;
import com.speedment.common.injector.platform.State;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class DependencyImpl implements Dependency {
    
    private final DependencyNode dependingOn;
    private final State dependedState;
    
    public DependencyImpl(DependencyNode dependingOn, State dependedState) {
        this.dependingOn   = requireNonNull(dependingOn);
        this.dependedState = requireNonNull(dependedState);
    }

    @Override
    public DependencyNode getDependingOn() {
        return dependingOn;
    }

    @Override
    public State getDependedState() {
        return dependedState;
    }
}