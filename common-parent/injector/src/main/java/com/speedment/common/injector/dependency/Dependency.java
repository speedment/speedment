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
package com.speedment.common.injector.dependency;

import com.speedment.common.injector.State;

/**
 * A dependency in the configuration graph.
 * 
 * @author  Emil Forslund
 * @since   1.2.0
 */
public interface Dependency {
    
    /**
     * The node that is depended on.
     * 
     * @return  the depended node
     */
    DependencyNode getNode();
    
    /**
     * The state that the node returned by {@link #getNode()} must be in for
     * this dependency to be satisfied.
     * 
     * @return  the state that the dependency must be in
     */
    State getRequiredState();
    
    /**
     * Returns {@code true} if this dependency is satisfied, else {@code false}.
     * 
     * @return  {@code true} if satisfied, else {@code false}
     */
    default boolean isSatisfied() {
        return getNode().is(getRequiredState());
    }
}