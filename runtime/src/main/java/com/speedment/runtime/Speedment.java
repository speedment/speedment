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
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.component.EntityManager;
import com.speedment.runtime.component.EventComponent;
import com.speedment.runtime.component.InfoComponent;
import com.speedment.runtime.component.ManagerComponent;
import com.speedment.runtime.component.PasswordComponent;
import com.speedment.runtime.component.PrimaryKeyFactoryComponent;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.component.StreamSupplierComponent;
import com.speedment.runtime.component.TypeMapperComponent;
import com.speedment.runtime.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.manager.Manager;
import java.util.stream.Stream;

/**
 * The {@code Platform} class acts as a generic holder of different system
 * {@link Component Components}. Using its pluggable architecture, one can
 * replace existing default implementations of existing Components or plug in
 * custom made implementation of any Interface.
 * <p>
 * Pluggable instances must implement the {@link Component} interface.
 *
 * @author pemi
 */
@Api(version = "2.3")
public interface Speedment {

    /**
     * Gets a {@link Speedment} {@link Component} based on its interface class.
     * <p>
     * The supported standard interfaces types are:
     * <ul>
     * <li>{@link com.speedment.runtime.component.EntityManager EntityManager}</li>
     * <li>{@link com.speedment.runtime.component.EventComponent EventComponent}</li>
     * <li>{@link com.speedment.runtime.component.DbmsHandlerComponent DbmsHandlerComponent}</li>
     * <li>{@link com.speedment.runtime.component.ManagerComponent ManagerComponent}</li>
     * <li>{@link com.speedment.runtime.component.PrimaryKeyFactoryComponent PrimaryKeyFactoryComponent}</li>
     * <li>{@link com.speedment.runtime.component.ProjectComponent ProjectComponent}</li>
     * <li>{@link com.speedment.runtime.component.resultset.ResultSetMapperComponent ResultSetMapperComponent}</li>
     * <li>{@link com.speedment.runtime.component.connectionpool.ConnectionPoolComponent ConnectionPoolComponent}</li>
     * <li>{@link com.speedment.runtime.component.StreamSupplierComponent StreamSupplierComponent}</li>
     * <li>{@link com.speedment.runtime.component.TypeMapperComponent TypeMapperComponent}</li>
     * <li>{@link com.speedment.runtime.component.PasswordComponent PasswordComponent}</li>
     * <li>{@link com.speedment.runtime.component.InfoComponent InfoComponent}</li>
     * </ul>
     *
     * @param <R> The intended return type
     * @param interfaceClass The interface class of the intended return type
     * @return The currently mapped instance
     */
    <R extends Component> R get(Class<R> interfaceClass);

    /**
     * Puts a new Component in the Speedment platform and returns the previous
     * Component (if any) with the same interface class.
     *
     * @param item the new Component to put
     * @return the previous Component registered using that interface class, or
     * null of no one existed before
     */
    Component put(Component item);

    //Component put(Component item);
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
     * Creates and returns a new Stream of all installed Components.
     *
     * @return a new Stream of all installed Components
     */
    Stream<Component> components();

    /**
     * Stops the Speedment instance and deallocates any allocated resources.
     * After stop() has been called, the Speedment instance can not be called
     * any more.
     */
    void stop();

    /**
     * Creates a new speedment instance and loads a new instance of each
     * component that this speedment instance has.
     *
     * @return the new instance
     */
    Speedment copyWithSameTypeOfComponents();

    /**
     * A shortcut for {@code get(EntityManager.class)}.
     * 
     * @return  the {@link EntityManager}
     */
    default EntityManager getEntityManager() {
        return get(EntityManager.class);
    }

    /**
     * A shortcut for {@code get(DbmsHandlerComponent.class)}.
     * 
     * @return  the {@link DbmsHandlerComponent}
     */
    default DbmsHandlerComponent getDbmsHandlerComponent() {
        return get(DbmsHandlerComponent.class);
    }

    /**
     * A shortcut for {@code get(ManagerComponent.class)}.
     * 
     * @return  the {@link ManagerComponent}
     */
    default ManagerComponent getManagerComponent() {
        return get(ManagerComponent.class);
    }

    /**
     * A shortcut for {@code get(PrimaryKeyFactoryComponent.class)}.
     * 
     * @return  the {@link PrimaryKeyFactoryComponent}
     */
    default PrimaryKeyFactoryComponent getPrimaryKeyFactoryComponent() {
        return get(PrimaryKeyFactoryComponent.class);
    }

    /**
     * A shortcut for {@code get(ProjectComponent.class)}.
     * 
     * @return  the {@link ProjectComponent}
     */
    default ProjectComponent getProjectComponent() {
        return get(ProjectComponent.class);
    }

    /**
     * A shortcut for {@code get(ResultSetMapperComponent.class)}.
     * 
     * @return  the {@link ResultSetMapperComponent}
     */
    default ResultSetMapperComponent getResultSetMapperComponent() {
        return get(ResultSetMapperComponent.class);
    }

    /**
     * A shortcut for {@code get(ConnectionPoolComponent.class)}.
     * 
     * @return  the {@link ConnectionPoolComponent}
     */
    default ConnectionPoolComponent getConnectionPoolComponent() {
        return get(ConnectionPoolComponent.class);
    }

    /**
     * A shortcut for {@code get(StreamSupplierComponent.class)}.
     * 
     * @return  the {@link StreamSupplierComponent}
     */
    default StreamSupplierComponent getStreamSupplierComponent() {
        return get(StreamSupplierComponent.class);
    }

    /**
     * A shortcut for {@code get(TypeMapperComponent.class)}.
     * 
     * @return  the {@link TypeMapperComponent}
     */
    default TypeMapperComponent getTypeMapperComponent() {
        return get(TypeMapperComponent.class);
    }

    /**
     * A shortcut for {@code get(EventComponent.class)}.
     * 
     * @return  the {@link EventComponent}
     */
    default EventComponent getEventComponent() {
        return get(EventComponent.class);
    }
    
    /**
     * A shortcut for {@code get(PasswordComponent.class)}.
     * 
     * @return  the {@link PasswordComponent}
     */
    default PasswordComponent getPasswordComponent() {
        return get(PasswordComponent.class);
    }
    
    /**
     * A shortcut for {@code get(InfoComponent.class)}.
     * 
     * @return  the {@link InfoComponent}
     */
    default InfoComponent getInfoComponent() {
        return get(InfoComponent.class);
    }
}