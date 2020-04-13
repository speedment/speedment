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
package com.speedment.common.injector.execution;

import com.speedment.common.injector.State;
import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.injector.State.RESOLVED;
import static com.speedment.common.injector.State.STARTED;
import static com.speedment.common.injector.State.STOPPED;
import java.util.function.Consumer;

/**
 * Builder for a {@link Execution} that can be applied for a particular 
 * injectable component in a specific {@link State}.
 * 
 * @param <T>  the component to execute on
 * 
 * @author Emil Forslund
 * @since  1.2.0
 */
public interface ExecutionZeroParamBuilder<T> extends ExecutionBuilder<T> {

    default <P0> ExecutionOneParamBuilder<T, P0> withStateInitialized(Class<P0> component) {
        return withState(INITIALIZED, component);
    }
    
    default <P0> ExecutionOneParamBuilder<T, P0> withStateResolved(Class<P0> component) {
        return withState(RESOLVED, component);
    }
    
    default <P0> ExecutionOneParamBuilder<T, P0> withStateStarted(Class<P0> component) {
        return withState(STARTED, component);
    }
    
    default <P0> ExecutionOneParamBuilder<T, P0> withStateStopped(Class<P0> component) {
        return withState(STOPPED, component);
    }
    
    <P0> ExecutionOneParamBuilder<T, P0> withState(State state, Class<P0> component);
    
    ExecutionBuilder<T> withExecute(Consumer<T> executeAction);
    
}