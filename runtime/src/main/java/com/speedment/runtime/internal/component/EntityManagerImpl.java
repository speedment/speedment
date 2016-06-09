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
package com.speedment.runtime.internal.component;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.component.EntityManager;
import com.speedment.runtime.component.ManagerComponent;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.license.Software;
import com.speedment.runtime.manager.Manager;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class EntityManagerImpl extends InternalOpenSourceComponent implements EntityManager {

    private @Inject ManagerComponent managerComponent;
    
    @Override
    protected String getDescription() {
        return "Handles persistence for any Entity. This Component provides an interface similar " + 
            "to JPA but is not used for any other purpose.";
    }

    @Override
    public <ENTITY> void persist(ENTITY entity) throws SpeedmentException {
        requireNonNull(entity);
        managerOf(entity).persist(entity);
    }

    @Override
    public <ENTITY> void update(ENTITY entity) throws SpeedmentException {
        requireNonNull(entity);
        managerOf(entity).update(entity);
    }

    @Override
    public <ENTITY> void remove(ENTITY entity) throws SpeedmentException {
        requireNonNull(entity);
        managerOf(entity).remove(entity);
    }

    @Override
    public Stream<Software> getDependencies() {
        return Stream.empty();
    }

    private <ENTITY> Manager<ENTITY> managerOf(ENTITY entity) {
        requireNonNull(entity);
        
        @SuppressWarnings("rawtypes")
        final Optional<Manager> manager = managerOf(entity.getClass(), managerComponent);
        
        if (manager.isPresent()) {
            @SuppressWarnings("unchecked")
            final Manager<ENTITY> result = (Manager<ENTITY>) manager.get();
            return result;
        }
        throw new IllegalStateException("There is no registered Manager for the class " + entity.getClass().getName());
    }

    @SuppressWarnings("rawtypes")
    private static Optional<Manager> managerOf(Class<?> entityInterface, ManagerComponent managerComponent) {
        requireNonNulls(entityInterface, managerComponent);
        
        final Manager manager = managerComponent.managerOf(entityInterface);
        if (manager != null) {
            return Optional.of(manager);
        }
        
        for (final Class<?> parentEntityInterface : entityInterface.getInterfaces()) {
            // Recuresively explore...
            final Optional<Manager> parentManager = managerOf(parentEntityInterface, managerComponent);
            if (parentManager.isPresent()) {
                return parentManager;
            }
        }
        
        return Optional.empty();
    }

}
