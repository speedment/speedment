package com.speedment.common.injector.internal.dependency.impl;

import com.speedment.common.injector.internal.dependency.Dependency;
import com.speedment.common.injector.internal.dependency.Execution;
import com.speedment.common.injector.State;
import java.lang.reflect.Method;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import java.util.Set;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class ExecutionImpl implements Execution {

    private final State state;
    private final Method method;
    private final Set<Dependency> dependencies;

    public ExecutionImpl(State state, Method method, Set<Dependency> dependencies) {
        this.state        = requireNonNull(state);
        this.method       = requireNonNull(method);
        this.dependencies = unmodifiableSet(dependencies);
    }
    
    @Override
    public State getState() {
        return state;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Set<Dependency> getDependencies() {
        return dependencies;
    }
}