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
package com.speedment.runtime;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.component.Component;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.manager.Manager;

/**
 * The {@code Platform} class acts as a generic holder of different system
 * {@link Component Components}. Using its pluggable architecture, one can
 * replace existing default implementations of existing Components or plug in
 * custom made implementation of any Interface.
 * <p>
 * Pluggable instances must implement the {@link Component} interface.
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.0.0
 */
@Api(version = "2.4")
public interface Speedment {
    
    /**
     * Obtains and returns the currently associated {@link Manager}
     * implementation for the given Entity interface Class. If no Manager exists
     * for the given entityClass, a SpeedmentException will be thrown.
     * <p>
     * N.B.This conveniency method is a pure delegator to the ManagerComponent
     * and is exactly equivalent to the code:
     * <p>
     * {@code get(ManagerComponent.class).managerOf(entityClass) }
     *
     * @param <ENTITY> the Entity interface type
     * @param entityClass the Entity interface {@code Class}
     * @return the currently associated {@link Manager} implementation for the
     * given Entity interface Class
     * @throws SpeedmentException if no Manager exists for the given entityClass
     */
    <ENTITY> Manager<ENTITY> managerOf(Class<ENTITY> entityClass) throws SpeedmentException;
    
    /**
     * Returns the specified component from the platform, or if it does not
     * exist, throws a {@code SpeedmentException}.
     * 
     * @param <C>             the component interface type
     * @param componentClass  the component interface class
     * @return                the component
     * 
     * @throws SpeedmentException  if it was not installed
     */
    <C extends Component> C getOrThrow(Class<C> componentClass) throws SpeedmentException;
    
    /**
     * Returns the project node.
     * 
     * @return  the project.
     */
    Project project();

    /**
     * Stops the Speedment instance and deallocates any allocated resources.
     * After stop() has been called, the Speedment instance can not be called
     * any more.
     */
    void stop();
}