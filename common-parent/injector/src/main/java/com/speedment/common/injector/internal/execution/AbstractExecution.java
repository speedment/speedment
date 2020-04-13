/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.injector.internal.execution;

import com.speedment.common.injector.MissingArgumentStrategy;
import com.speedment.common.injector.State;
import com.speedment.common.injector.dependency.Dependency;
import com.speedment.common.injector.execution.Execution;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * An abstract base implementation of the {@link Execution} interface.
 * 
 * @param <T> the type to execute on
 * 
 * @author Emil Forslund
 * @since  1.2.0
 */
abstract class AbstractExecution<T> implements Execution<T> {

    private final Class<T> type;
    private final State state;
    private final Set<Dependency> dependencies;
    private final MissingArgumentStrategy missingArgumentStrategy;

    AbstractExecution(
            Class<T> type, 
            State state, 
            Set<Dependency> dependencies,
            MissingArgumentStrategy missingArgumentStrategy) {
        
        this.type         = requireNonNull(type);
        this.state        = requireNonNull(state);
        this.dependencies = requireNonNull(dependencies);
        this.missingArgumentStrategy = requireNonNull(missingArgumentStrategy);
    }

    public String getName() {
        return "abstractExecution" + dependencies.stream()
            .map(dep -> dep.getNode().getRepresentedType().getSimpleName())
            .collect(joining(", ", "(", ")"));
    }

    @Override
    public final Class<T> getType() {
        return type;
    }
    
    @Override
    public final State getState() {
        return state;
    }

    @Override
    public final Set<Dependency> getDependencies() {
        return dependencies;
    }

    @Override
    public MissingArgumentStrategy getMissingArgumentStrategy() {
        return missingArgumentStrategy;
    }

    @Override
    public String toString() {
        return "<execute>(" +
            dependencies.stream().map(d -> 
                d.getNode().getRepresentedType().getSimpleName() + "[" +
                d.getRequiredState().name().toLowerCase() + "]"
            ).collect(joining(", ")) + ")";
    }
}