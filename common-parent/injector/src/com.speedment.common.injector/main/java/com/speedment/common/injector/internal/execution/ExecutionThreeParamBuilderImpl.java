/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.injector.State;
import com.speedment.common.injector.dependency.Dependency;
import com.speedment.common.injector.dependency.DependencyGraph;
import com.speedment.common.injector.dependency.DependencyNode;
import com.speedment.common.injector.exception.NotInjectableException;
import com.speedment.common.injector.execution.Execution;
import com.speedment.common.injector.execution.ExecutionBuilder;
import com.speedment.common.injector.execution.ExecutionThreeParamBuilder;
import com.speedment.common.injector.execution.ExecutionThreeParamBuilder.QuadConsumer;
import com.speedment.common.injector.internal.dependency.DependencyImpl;
import static com.speedment.common.injector.internal.util.SetUtil.unmodifiableSet;
import java.lang.reflect.InvocationTargetException;
import static java.util.Objects.requireNonNull;

/**
 * 
 * @param <T>   the component to withExecute on
 * @param <P0>  the first parameter type
 * @param <P1>  the second parameter type
 * @param <P2>  the third parameter type
 * 
 * @author Emil Forslund
 * @since  1.2.0
 */
public final class ExecutionThreeParamBuilderImpl<T, P0, P1, P2> 
extends AbstractExecutionBuilder<T>
implements ExecutionThreeParamBuilder<T, P0, P1, P2> {
    
    private final Class<P0> param0;
    private final Class<P1> param1;
    private final Class<P2> param2;
    private final State state0, state1, state2;

    private QuadConsumer<T, P0, P1, P2> executeAction;
    
    public ExecutionThreeParamBuilderImpl(
            Class<T> component, State state,
            Class<P0> param0, State state0,
            Class<P1> param1, State state1,
            Class<P2> param2, State state2) {
        
        super(component, state);
        this.param0 = requireNonNull(param0);
        this.state0 = requireNonNull(state0);
        this.param1 = requireNonNull(param1);
        this.state1 = requireNonNull(state1);
        this.param2 = requireNonNull(param2);
        this.state2 = requireNonNull(state2);
    }

    @Override
    public ExecutionBuilder<T> withExecute(QuadConsumer<T, P0, P1, P2> executeAction) {
        this.executeAction = requireNonNull(executeAction);
        return this;
    }

    @Override
    public Execution<T> build(DependencyGraph graph) {
        
        requireNonNull(executeAction, "No execution has been specified.");
        
        final DependencyNode node0 = graph.get(param0);
        final Dependency dep0 = new DependencyImpl(node0, state0);
        
        final DependencyNode node1 = graph.get(param1);
        final Dependency dep1 = new DependencyImpl(node1, state1);
        
        final DependencyNode node2 = graph.get(param2);
        final Dependency dep2 = new DependencyImpl(node2, state2);
        
        return new AbstractExecution<T>(
                getComponent(), getState(), unmodifiableSet(dep0, dep1, dep2)) {
                    
            @Override
            public void invoke(T component, Execution.ClassMapper classMapper) 
            throws IllegalAccessException, IllegalArgumentException, 
                   InvocationTargetException, NotInjectableException {
                
                final P0 arg0 = classMapper.apply(param0);
                final P1 arg1 = classMapper.apply(param1);
                final P2 arg2 = classMapper.apply(param2);
                executeAction.accept(component, arg0, arg1, arg2);
            }
        };
    }
}