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
import com.speedment.common.injector.dependency.DependencyGraph;
import com.speedment.common.injector.internal.execution.ExecutionZeroParamBuilderImpl;

import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.injector.State.RESOLVED;
import static com.speedment.common.injector.State.STARTED;
import static com.speedment.common.injector.State.STOPPED;

/**
 * Builder for a {@link Execution} that can be applied for a particular 
 * injectable component in a specific {@link State}. The returned execution will
 * always execute the method or throw an exception.
 * 
 * @param <T>  the component to execute on
 * 
 * @author Emil Forslund
 * @since  1.2.0
 */
public interface ExecutionBuilder<T> {
    
    static <T> ExecutionZeroParamBuilder<T> initialized(Class<T> component) {
        return on(INITIALIZED, component);
    }
    
    static <T> ExecutionZeroParamBuilder<T> resolved(Class<T> component) {
        return on(RESOLVED, component);
    }
    
    static <T> ExecutionZeroParamBuilder<T> started(Class<T> component) {
        return on(STARTED, component);
    }
    
    static <T> ExecutionZeroParamBuilder<T> stopped(Class<T> component) {
        return on(STOPPED, component);
    }
    
    static <T> ExecutionZeroParamBuilder<T> on(State state, Class<T> component) {
        return new ExecutionZeroParamBuilderImpl<>(component, state);
    }
    
    Execution<T> build(DependencyGraph graph);
}