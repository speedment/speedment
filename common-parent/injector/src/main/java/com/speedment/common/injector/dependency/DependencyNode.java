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
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.execution.Execution;
import java.util.List;
import java.util.Set;

/**
 * A node in the configuration graph.
 * 
 * @author  Emil Forslund
 * @since   1.2.0
 */
public interface DependencyNode {
    
    /**
     * The java type that this dependency represents.
     * 
     * @return  the represented type
     */
    Class<?> getRepresentedType();
    
    /**
     * Returns the set of dependencies that must be satisfied for this node to
     * be created. These are typically the fields annotated with the 
     * {@link Inject}-annotation and should therefore be set before the actual 
     * configuration phase is initiated.
     * 
     * @return  the set of dependencies
     */
    Set<Dependency> getDependencies();
    
    /**
     * Returns the set of executions that are part of this node. These must all
     * be invoked sometime during the configuration phase for this node to reach
     * a certain state.
     * 
     * @return  the set of executions
     */
    List<Execution<?>> getExecutions();
    
    /**
     * Returns the current {@link State} of this node.
     * 
     * @return  the current state
     */
    State getCurrentState();
    
    /**
     * Sets the state of this node. This should only be called as part of the 
     * internal configuration process, never externally.
     * 
     * @param newState  the new state to be in
     */
    void setState(State newState);
    
    /**
     * Returns {@code true} if all requirements are satisfied for this node to
     * be upgraded to a certain state.
     * 
     * @param state  the state to check
     * @return       {@code true} if it is safe to call {@link #setState(State)} 
     *               and invoke every {@link #getExecutions()}, else 
     *               {@code false}
     */
    boolean canBe(State state);
    
    /**
     * Returns {@code true} if this node is in the specified {@link State} or 
     * a higher state.
     * 
     * @param state  the state to check
     * @return       {@code true} if that state has been reached, else 
     *               {@code false}
     */
    boolean is(State state);
    
}