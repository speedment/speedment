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

import com.speedment.common.injector.State;
import com.speedment.common.injector.execution.ExecutionBuilder;
import static java.util.Objects.requireNonNull;

/**
 * Abstract base implementation for an {@link ExecutionBuilder}.
 *
 * @param <T>  the component to execute on
 * 
 * @author Emil Forslund
 * @since  1.2.0
 */
abstract class AbstractExecutionBuilder<T> implements ExecutionBuilder<T> {

    private final Class<T> component;
    private final State state;

    AbstractExecutionBuilder(Class<T> component, State state) {
        this.component = requireNonNull(component);
        this.state     = requireNonNull(state);
    }

    protected final Class<T> getComponent() {
        return component;
    }

    protected final State getState() {
        return state;
    }
}