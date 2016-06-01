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
package com.speedment;

import com.speedment.annotation.Api;
import com.speedment.component.CodeGenerationComponent;
import com.speedment.component.Component;
import com.speedment.component.DbmsHandlerComponent;
import com.speedment.component.DocumentPropertyComponent;
import com.speedment.component.EntityManager;
import com.speedment.component.EventComponent;
import com.speedment.component.ManagerComponent;
import com.speedment.component.PasswordComponent;
import com.speedment.component.PrimaryKeyFactoryComponent;
import com.speedment.component.ProjectComponent;
import com.speedment.component.StreamSupplierComponent;
import com.speedment.component.TypeMapperComponent;
import com.speedment.component.UserInterfaceComponent;
import com.speedment.component.connectionpool.ConnectionPoolComponent;
import com.speedment.component.resultset.ResultSetMapperComponent;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.logging.Level;
import com.speedment.manager.Manager;
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
     * <li>{@link com.speedment.component.EntityManager EntityManager}</li>
     * <li>{@link com.speedment.component.EventComponent EventComponent}</li>
     * <li>{@link com.speedment.component.DbmsHandlerComponent DbmsHandlerComponent}</li>
     * <li>{@link com.speedment.component.DocumentPropertyComponent DocumentPropertyComponent}</li>
     * <li>{@link com.speedment.component.CodeGenerationComponent CodeGenerationComponent}</li>
     * <li>{@link com.speedment.component.ManagerComponent ManagerComponent}</li>
     * <li>{@link com.speedment.component.PrimaryKeyFactoryComponent PrimaryKeyFactoryComponent}</li>
     * <li>{@link com.speedment.component.ProjectComponent ProjectComponent}</li>
     * <li>{@link com.speedment.component.resultset.ResultSetMapperComponent ResultSetMapperComponent}</li>
     * <li>{@link com.speedment.component.connectionpool.ConnectionPoolComponent ConnectionPoolComponent}</li>
     * <li>{@link com.speedment.component.StreamSupplierComponent StreamSupplierComponent}</li>
     * <li>{@link com.speedment.component.TypeMapperComponent TypeMapperComponent}</li>
     * <li>{@link com.speedment.component.PasswordComponent PasswordComponent}</li>
     * <li>{@link com.speedment.component.UserInterfaceComponent UserInterfaceComponent}</li>
     *
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
     * Sets the log level for this speedment instance.
     *
     * @param level new log level to use
     */
    void setLogLevel(Level level);

    default EntityManager getEntityManager() {
        return get(EntityManager.class);
    }

    default DbmsHandlerComponent getDbmsHandlerComponent() {
        return get(DbmsHandlerComponent.class);
    }

    default ManagerComponent getManagerComponent() {
        return get(ManagerComponent.class);
    }

    default PrimaryKeyFactoryComponent getPrimaryKeyFactoryComponent() {
        return get(PrimaryKeyFactoryComponent.class);
    }

    default ProjectComponent getProjectComponent() {
        return get(ProjectComponent.class);
    }

    default ResultSetMapperComponent getResultSetMapperComponent() {
        return get(ResultSetMapperComponent.class);
    }

    default ConnectionPoolComponent getConnectionPoolComponent() {
        return get(ConnectionPoolComponent.class);
    }

    default StreamSupplierComponent getStreamSupplierComponent() {
        return get(StreamSupplierComponent.class);
    }

    default TypeMapperComponent getTypeMapperComponent() {
        return get(TypeMapperComponent.class);
    }

    default EventComponent getEventComponent() {
        return get(EventComponent.class);
    }

    default UserInterfaceComponent getUserInterfaceComponent() {
        return get(UserInterfaceComponent.class);
    }

    default PasswordComponent getPasswordComponent() {
        return get(PasswordComponent.class);
    }

    default CodeGenerationComponent getCodeGenerationComponent() {
        return get(CodeGenerationComponent.class);
    }

    default DocumentPropertyComponent getDocumentPropertyComponent() {
        return get(DocumentPropertyComponent.class);
    }
}
