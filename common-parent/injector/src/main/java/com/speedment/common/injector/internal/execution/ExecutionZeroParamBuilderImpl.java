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
import com.speedment.common.injector.dependency.DependencyGraph;
import com.speedment.common.injector.execution.Execution;
import com.speedment.common.injector.execution.ExecutionBuilder;
import com.speedment.common.injector.execution.ExecutionOneParamBuilder;
import com.speedment.common.injector.execution.ExecutionZeroParamBuilder;

import java.util.function.Consumer;

import static java.util.Collections.emptySet;
import static java.util.Objects.requireNonNull;

/**
 * Zeroth step of an {@link ExecutionBuilder}-chain.
 * 
 * @param <T>  the component to execute on
 * 
 * @author Emil Forslund
 * @since  1.2.0
 */
public final class ExecutionZeroParamBuilderImpl<T> 
    extends AbstractExecutionBuilder<T>
    implements ExecutionZeroParamBuilder<T> {

    private Consumer<T> executeAction;
    
    public ExecutionZeroParamBuilderImpl(Class<T> component, State state) {
        super(component, state);
    }

    @Override
    public <P0> ExecutionOneParamBuilder<T, P0> withState(
            State state0, Class<P0> param0) {
        
        return new ExecutionOneParamBuilderImpl<>(
            getComponent(), getState(), 
            param0, state0
        );
    }

    @Override
    public ExecutionBuilder<T> withExecute(Consumer<T> executeAction) {
        this.executeAction = requireNonNull(executeAction);
        return this;
    }

    @Override
    public Execution<T> build(DependencyGraph graph) {
        
        requireNonNull(executeAction, "No execution has been specified.");
        
        return new AbstractExecution<T>(
                getComponent(), getState(), emptySet(),
                MissingArgumentStrategy.THROW_EXCEPTION) {
                    
            @Override
            public boolean invoke(T component, ClassMapper classMapper) {
                executeAction.accept(component);
                return true;
            }
        };
    }
}