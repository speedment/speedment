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

import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.manager.Manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link ManagerComponent}-interface.
 * 
 * @author Emil Forslund
 */
public final class ManagerComponentImpl implements ManagerComponent {

    private final Map<Class<?>, Manager<?>> managersByEntity;

    public ManagerComponentImpl() {
        managersByEntity = new ConcurrentHashMap<>();
    }

    @Override
    public <ENTITY> void put(Manager<ENTITY> manager) {
        requireNonNull(manager);
        managersByEntity.put(manager.getEntityClass(), manager);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> Manager<E> managerOf(Class<E> entityClass) {
        
        requireNonNull(entityClass);
        
        @SuppressWarnings("unchecked")
        final Manager<E> manager = (Manager<E>) 
            managersByEntity.get(entityClass);
        
        if (manager == null) {
            throw new SpeedmentException(
                "No manager exists for " + entityClass
            );
        }
        
        return manager;
    }

    @Override
    public Stream<Manager<?>> stream() {
        return managersByEntity.values().stream();
    }
}