/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment;

import com.speedment.internal.core.platform.component.Component;
import com.speedment.annotation.Api;
import com.speedment.exception.SpeedmentException;

/**
 * The {@code Platform} class acts as a generic holder of different system
 * {@link Component Components}. Using its pluggable architecture, one can
 * replace existing default implementations of existing Components or plug in
 * custom made implementation of any Interface.
 * <p>
 * Pluggable instances must implement the {@link Component} interface.
 *
 * <img src="{@docRoot}/doc-files/hare.png" alt="Hare">
 *
 * @author pemi
 */
@Api(version = "2.1")
public interface Speedment {

    /**
     * Gets a {@link Speedment} {@link Component} based on its interface class.
     * <p>
     * The supported standard interfaces types are:
     * <ul>
     * <li>{@link com.speedment.internal.core.platform.component.EntityManager EntityManager}</li>
     * <li>{@link com.speedment.internal.core.platform.component.DbmsHandlerComponent DbmsHandlerComponent}</li>
     * <li>{@link com.speedment.internal.core.platform.component.ManagerComponent ManagerComponent}</li>
     * <li>{@link com.speedment.internal.core.platform.component.PrimaryKeyFactoryComponent PrimaryKeyFactoryComponent}</li>
     * <li>{@link com.speedment.internal.core.platform.component.ProjectComponent ProjectComponent}</li>
     * <li>{@link com.speedment.internal.core.platform.component.SqlTypeMapperComponent SqlTypeMapperComponent}</li>
     * <li>{@link com.speedment.internal.core.platform.component.LoggerFactoryComponent LoggerFactoryComponent}</li>
     * <li>{@link com.speedment.internal.core.platform.component.JavaTypeMapperComponent JavaTypeMapperComponent}</li>
     * <li>{@link com.speedment.internal.core.platform.component.ConnectionPoolComponent ConnectionPoolComponent}</li>
     * </ul>
     *
     * @param <R> The intended return type
     * @param interfaceClass The interface class of the intended return type
     * @return The currently mapped instance
     */
    <R extends Component> R get(Class<R> interfaceClass);

    /**
     * Puts a new Component in the Speedment platform and returns the previous
     * Compoment (if any) with the same interface class.
     *
     * @param item the new Component to put
     * @return the previous Component registered using that interface class, or
     * null of no one existed before
     */
    Component put(Component item);

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
     * Stops the Speedment instance and deallocates any allocated resources.
     * After stop() has been called, the Speedment instance can not be called
     * any more.
     */
    public void stop();

}
