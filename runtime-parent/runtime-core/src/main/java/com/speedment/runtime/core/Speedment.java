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
package com.speedment.runtime.core;

import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.manager.ManagerConfigurator;
import java.util.Optional;

/**
 * The {@code Platform} class acts as a generic holder of different system
 * Components. Using its pluggable architecture, one can
 * replace existing default implementations of existing Components or plug in
 * custom made implementation of any type.
 * <p>
 * Pluggable components must have a default constructor.
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.0.0
 */
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
    <T> T getOrThrow(Class<T> type);
    

    /**
     * Creates and Returns a manager configurator that can be used to configure
     * an existing manager. When the manager configurator's build() method is 
     * called, a new configured manager will be returned.
     * 
     * @param <ENTITY>    the entity type
     * @param manager     to configure
     * @return            a manager configurator 
     */
    <ENTITY> ManagerConfigurator<ENTITY> configure(Class<? extends Manager<ENTITY>> manager);
    
   /**
     * Gracefully stops the Speedment instance and de-allocates any allocated resources.
     * After stop() has been called, the Speedment instance can not be called
     * any more.
     */
    void stop();
    
}