package com.speedment.common.injector.internal.dependency.impl;

import com.speedment.common.injector.internal.dependency.Dependency;
import com.speedment.common.injector.internal.dependency.DependencyNode;
import com.speedment.common.injector.internal.dependency.Execution;
import com.speedment.common.injector.State;
import static java.util.Collections.newSetFromMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class DependencyNodeImpl implements DependencyNode {

    private final Class<?> representedType;
    private final Set<Dependency> dependencies;
    private final Set<Execution> executions;
    private State currentState;

    public DependencyNodeImpl(Class<?> representedType) {
        this.representedType = requireNonNull(representedType);
        this.dependencies    = newSetFromMap(new ConcurrentHashMap<>());
        this.executions      = newSetFromMap(new ConcurrentHashMap<>());
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
    public Set<Execution> getExecutions() {
        return executions;
    }

    @Override
    public State getCurrentState() {
        return currentState;
    }

    @Override
    public void setState(State newState) {
        currentState = requireNonNull(newState);
    }

    @Override
    public boolean canBe(State state) {
        // Make sure all dependencies of the executions have been satisfied.
        return 
            executions.stream()
                .filter(e -> e.getState().ordinal() <= state.ordinal())
                .flatMap(e -> e.getDependencies().stream())
                .map(Dependency::getNode)
                .allMatch(node -> node.is(state));
    }

    @Override
    public boolean is(State state) {
        return currentState.ordinal() >= state.ordinal();
    }

    
}
