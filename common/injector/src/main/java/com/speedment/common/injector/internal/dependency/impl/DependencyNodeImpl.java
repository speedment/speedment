/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.common.injector.internal.dependency.impl;

import com.speedment.common.injector.State;
import com.speedment.common.injector.internal.dependency.Dependency;
import com.speedment.common.injector.internal.dependency.DependencyNode;
import com.speedment.common.injector.internal.dependency.Execution;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.newSetFromMap;
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
        return executions.stream()
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
