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
package com.speedment.runtime.core.internal.component;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.core.component.EntityManager;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.manager.Manager;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.speedment.common.injector.State.RESOLVED;
import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class EntityManagerImpl implements EntityManager {

    private final Map<Class<?>, Manager<?>> managers;

    public EntityManagerImpl() {
        this.managers = new ConcurrentHashMap<>();
    }

    @ExecuteBefore(RESOLVED)
    public void installManagers(ManagerComponent managerComponent) {
        managerComponent.stream()
            .forEach(m -> managers.put(m.getEntityClass(), m));
    }

    @Override
    public <ENTITY> void persist(ENTITY entity) {
        requireNonNull(entity);
        managerOf(entity).persist(entity);
    }

    @Override
    public <ENTITY> void update(ENTITY entity) {
        requireNonNull(entity);
        managerOf(entity).update(entity);
    }

    @Override
    public <ENTITY> void remove(ENTITY entity) {
        requireNonNull(entity);
        managerOf(entity).remove(entity);
    }

    private <ENTITY> Manager<ENTITY> managerOf(ENTITY entity) {
        requireNonNull(entity);

        @SuppressWarnings("rawtypes")
        final Optional<Manager> manager = lookup(entity.getClass());

        if (manager.isPresent()) {
            @SuppressWarnings("unchecked")
            final Manager<ENTITY> result = (Manager<ENTITY>) manager.get();
            return result;
        }
        throw new IllegalStateException("There is no registered Manager for the class " + entity.getClass().getName());
    }

    @SuppressWarnings("rawtypes")
    private <ENTITY> Optional<Manager> lookup(Class<ENTITY> entityClass) {
        requireNonNulls(entityClass);

        // Base case in recursion
        @SuppressWarnings("unchecked")
        final Manager<ENTITY> manager = (Manager<ENTITY>)managers.get(entityClass);
        if (manager != null) {
            return Optional.of(manager);
        }

        for (final Class<?> parentEntityInterface : entityClass.getInterfaces()) {
            // Recuresively explore...
            final Optional<Manager> parentManager = lookup(parentEntityInterface);
            if (parentManager.isPresent()) {
                return parentManager;
            }
        }
        return Optional.empty();
    }

}
