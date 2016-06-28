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
import java.util.Optional;

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
     * Returns the specified component from the platform, or if it does not
     * exist, an empty {@code Optional}.
     * 
     * @param <T>   the type to search for
     * @param type  the interface type
     * @return      the implementation or empty
     */
    <T> Optional<T> get(Class<T> type);

    /**
     * Returns the specified component from the platform, or if it does not
     * exist, throws a {@code SpeedmentException}.
     * 
     * @param <T>   the component interface type
     * @param type  the component interface class
     * @return      the component
     * 
     * @throws SpeedmentException  if it was not installed
     */
    <T> T getOrThrow(Class<T> type) throws SpeedmentException;
    
    /**
     * Returns the manager for the specified entity type.
     * 
     * @param <ENTITY>    the entity type
     * @param entityType  class for the entity to manage
     * @return            the manager for that entity type
     */
    <ENTITY> Manager<ENTITY> managerOf(Class<ENTITY> entityType);
    
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