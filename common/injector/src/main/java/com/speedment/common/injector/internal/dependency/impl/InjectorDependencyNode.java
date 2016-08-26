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

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.internal.dependency.Dependency;
import com.speedment.common.injector.internal.dependency.DependencyNode;
import com.speedment.common.injector.internal.dependency.Execution;

import java.util.Collections;
import java.util.Set;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
final class InjectorDependencyNode implements DependencyNode {

    private boolean running = true;
    
    @Override
    public Class<?> getRepresentedType() {
        return Injector.class;
    }

    @Override
    public Set<Dependency> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public Set<Execution> getExecutions() {
        return Collections.emptySet();
    }

    @Override
    public State getCurrentState() {
        return running ? State.STARTED : State.STOPPED;
    }

    @Override
    public void setState(State newState) {
        if (newState == State.STOPPED) {
            running = false;
        }
    }

    @Override
    public boolean canBe(State state) {
        return (state == State.STOPPED && running);
    }

    @Override
    public boolean is(State state) {
        if (state == State.STOPPED) {
            return !running;
        } else return true;
    }
}