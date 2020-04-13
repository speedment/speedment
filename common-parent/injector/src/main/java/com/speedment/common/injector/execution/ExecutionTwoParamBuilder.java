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

/**
 *
 * @param <T>   the component to withExecute on
 * @param <P0>  the first parameter type
 * @param <P1>  the second parameter type
 * 
 * @author Emil Forslund
 * @since  1.2.0
 */
public interface ExecutionTwoParamBuilder<T, P0, P1> extends ExecutionBuilder<T> {
    
    @FunctionalInterface
    interface TriConsumer<T, P0, P1> {
        void accept(T component, P0 first, P1 second);
    }

    default <P2> ExecutionThreeParamBuilder<T, P0, P1, P2> withStateInitialized(Class<P2> component) {
        return withState(INITIALIZED, component);
    }
    
    default <P2> ExecutionThreeParamBuilder<T, P0, P1, P2> withStateResolved(Class<P2> component) {
        return withState(RESOLVED, component);
    }
    
    default <P2> ExecutionThreeParamBuilder<T, P0, P1, P2> withStateStarted(Class<P2> component) {
        return withState(STARTED, component);
    }
    
    default <P2> ExecutionThreeParamBuilder<T, P0, P1, P2> withStateStopped(Class<P2> component) {
        return withState(STOPPED, component);
    }
    
    <P2> ExecutionThreeParamBuilder<T, P0, P1, P2> withState(State state, Class<P2> component);
    
    ExecutionBuilder<T> withExecute(TriConsumer<T, P0, P1> executeAction);
    
}