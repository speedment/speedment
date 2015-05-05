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
package com.speedment.orm.platform.component.impl;

import com.speedment.orm.core.Buildable;
import com.speedment.orm.core.manager.Manager;
import com.speedment.orm.platform.Platform;
import com.speedment.orm.platform.component.EntityManager;
import com.speedment.orm.platform.component.ManagerComponent;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public class EntityManagerImpl implements EntityManager {

    @Override
    public <ENTITY> Optional<ENTITY> persist(ENTITY entity) {
        return managerOf(entity).persist(entity);
    }

    @Override
    public <ENTITY> Optional<ENTITY> update(ENTITY entity) {
        return managerOf(entity).update(entity);
    }

    @Override
    public <ENTITY> Optional<ENTITY> remove(ENTITY entity) {
        return managerOf(entity).remove(entity);
    }

    private static <ENTITY, BUILDER extends Buildable<ENTITY>> Manager<?, ENTITY, BUILDER> managerOf(ENTITY entity) {
        final ManagerComponent managerComponent = Platform.get().get(ManagerComponent.class);
        @SuppressWarnings("rawtypes")
        final Optional<Manager> manager = managerOf(entity.getClass(), managerComponent);
        if (manager.isPresent()) {
            @SuppressWarnings("unchecked")
            final Manager<?, ENTITY, BUILDER> result = (Manager<?, ENTITY, BUILDER>) manager.get();
            return result;
        }
        throw new IllegalStateException("There is no registered Manager for the class " + entity.getClass().getName());
    }

    @SuppressWarnings("rawtypes")
    private static Optional<Manager> managerOf(Class<?> entityInterface, ManagerComponent managerComponent) {
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
