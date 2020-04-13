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
package com.speedment.runtime.core.manager;

import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.core.internal.manager.ManagerConfiguratorImpl;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;

/**
 * This class makes it possible to decorate a manager with a special 
 * parallelization strategy by using a builder pattern.
 * 
 * @param <ENTITY>  the entity type
 * 
 * @author  Per Minborg
 * @since   3.0.1
 */
public interface ManagerConfigurator<ENTITY> {

    /**
     * Set the parallelization strategy to use in the built manager.
     * 
     * @param parallelStrategy  the parallelization strategy
     * @return                  a reference to this instance
     */
    ManagerConfigurator<ENTITY> withParallelStrategy(ParallelStrategy parallelStrategy);

    /**
     * Builds a new manager that might delegate some methods to the pre-existing 
     * manager, but where the specified settings will be applied upon execution.
     * 
     * @return  the built manager
     */
    Manager<ENTITY> build();

    /**
     * Creates and returns a new standard implementation of a ManagerConfigurator.
     *
     * @param streams StreamSupplierComponent to use as stream source
     * @param manager to configure
     * @param <ENTITY> type
     * @return a new standard implementation of a ManagerConfigurator
     */

    static <ENTITY> ManagerConfigurator<ENTITY> create(
        final StreamSupplierComponent streams,
        final Manager<ENTITY> manager
    ) {
        return new ManagerConfiguratorImpl<>(streams, manager);
    }

}