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
package com.speedment.common.injector.internal.dependency;

import com.speedment.common.injector.State;
import com.speedment.common.injector.dependency.Dependency;
import com.speedment.common.injector.dependency.DependencyNode;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class DependencyImpl implements Dependency {
    
    private final DependencyNode requiredType;
    private final State requiredState;
    
    public DependencyImpl(DependencyNode requiredType, State requiredState) {
        this.requiredType  = requireNonNull(requiredType);
        this.requiredState = requireNonNull(requiredState);
    }

    @Override
    public DependencyNode getNode() {
        return requiredType;
    }

    @Override
    public State getRequiredState() {
        return requiredState;
    }
}