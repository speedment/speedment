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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.exception.SpeedmentException;
import com.speedment.Manager;
import com.speedment.Speedment;
import com.speedment.internal.core.platform.component.EntityManager;
import com.speedment.internal.core.platform.component.ManagerComponent;
import java.util.Optional;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class EntityManagerImpl implements EntityManager {

    private final Speedment speedment;

    public EntityManagerImpl(Speedment speedment) {
        this.speedment = requireNonNull(speedment);
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

    private <ENTITY> Manager<ENTITY> managerOf(ENTITY entity) {
        requireNonNull(entity);
        final ManagerComponent managerComponent = speedment.get(ManagerComponent.class);
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
        requireNonNull(entityInterface);
        requireNonNull(managerComponent);
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
