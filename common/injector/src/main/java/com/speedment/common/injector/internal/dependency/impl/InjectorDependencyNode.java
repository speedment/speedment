package com.speedment.common.injector.internal.dependency.impl;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.internal.dependency.Dependency;
import com.speedment.common.injector.internal.dependency.DependencyNode;
import com.speedment.common.injector.internal.dependency.Execution;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Emil Forslund
 */
final class InjectorDependencyNode implements DependencyNode {

    private boolean running = true;
    
    @Override
    public Class<?> getRepresentedType() {
        return Injector.class;
    }

    @Override
    public Set<Dependency> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public Set<Execution> getExecutions() {
        return Collections.emptySet();
    }

    @Override
    public State getCurrentState() {
        return State.STARTED;
    }

    @Override
    public void setState(State newState) {
        if (newState == State.STOPPED) {
            running = false;
        }
    }

    @Override
    public boolean canBe(State state) {
        return (state == State.STOPPED && running);
    }

    @Override
    public boolean is(State state) {
        if (state == State.STOPPED) {
            return !running;
        } else return true;
    }
}