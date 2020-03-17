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
import com.speedment.common.injector.dependency.DependencyGraph;
import com.speedment.common.injector.dependency.DependencyNode;
import com.speedment.common.injector.execution.Execution;
import com.speedment.common.injector.execution.ExecutionBuilder;
import com.speedment.common.injector.execution.ExecutionOneParamBuilder;
import com.speedment.common.injector.execution.ExecutionTwoParamBuilder;
import com.speedment.common.injector.internal.dependency.DependencyImpl;

import java.util.function.BiConsumer;

import static java.util.Collections.singleton;
import static java.util.Objects.requireNonNull;

/**
 * First step of an {@link ExecutionBuilder}-chain.
 * 
 * @param <T>   the component to withExecute on
 * @param <P0>  the first parameter type
 * 
 * @author Emil Forslund
 * @since  1.2.0
 */
public final class ExecutionOneParamBuilderImpl<T, P0>
    extends AbstractExecutionBuilder<T>
    implements ExecutionOneParamBuilder<T, P0> {
    
    private final Class<P0> param0;
    private final State state0;

    private BiConsumer<T, P0> executeAction;
    
    ExecutionOneParamBuilderImpl(
            Class<T> component, State state,
            Class<P0> param0, State state0) {
        
        super(component, state);
        this.param0 = requireNonNull(param0);
        this.state0 = requireNonNull(state0);
    }

    @Override
    public <P1> ExecutionTwoParamBuilder<T, P0, P1> withState(
            State state1, Class<P1> param1) {
        
        return new ExecutionTwoParamBuilderImpl<>(
            getComponent(), getState(), 
            param0, state0,
            param1, state1
        );
    }

    @Override
    public ExecutionBuilder<T> withExecute(BiConsumer<T, P0> executeAction) {
        this.executeAction = requireNonNull(executeAction);
        return this;
    }

    @Override
    public Execution<T> build(DependencyGraph graph) {
        
        requireNonNull(executeAction, "No execution has been specified.");
        
        final DependencyNode node0 = graph.get(param0);
        final Dependency dep0 = new DependencyImpl(node0, state0);
        
        return new AbstractExecution<T>(
                getComponent(), getState(), singleton(dep0),
                MissingArgumentStrategy.THROW_EXCEPTION) {
                    
            @Override
            public boolean invoke(T component, ClassMapper classMapper) {
                final P0 arg0 = classMapper.apply(param0);
                executeAction.accept(component, arg0);
                return true;
            }
        };
    }
}