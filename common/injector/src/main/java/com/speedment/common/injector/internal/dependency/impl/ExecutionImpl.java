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