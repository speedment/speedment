package com.speedment.common.injector.internal.dependency.impl;

import com.speedment.common.injector.internal.dependency.Dependency;
import com.speedment.common.injector.internal.dependency.DependencyNode;
import com.speedment.common.injector.State;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class DependencyImpl implements Dependency {
    
    private final DependencyNode requiredType;
    private final State requiredState;
    
    public DependencyImpl(DependencyNode requiredType, State requiredState) {
        this.requiredType  = requireNonNull(requiredType);
        this.requiredState = requireNonNull(requiredState);
    }

    @Override
    public DependencyNode getNode() {
        return requiredType;
    }

    @Override
    public State getRequiredState() {
        return requiredState;
    }
}