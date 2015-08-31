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

import com.speedment.config.Table;
import com.speedment.exception.SpeedmentException;
import com.speedment.Manager;
import com.speedment.internal.core.platform.component.ManagerComponent;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class ManagerComponentImpl implements ManagerComponent {

    private final Map<Class<?>, Manager<?>> managersByEntity;
    private final Map<Table, Manager<?>> tableMap;

    public ManagerComponentImpl() {
        managersByEntity = new ConcurrentHashMap<>();
        tableMap = new ConcurrentHashMap<>();
    }

    @Override
    public <ENTITY> void put(Manager<ENTITY> manager) {
        requireNonNull(manager);
        managersByEntity.put(manager.getEntityClass(), manager);
        tableMap.put(manager.getTable(), manager);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> Manager<E> managerOf(Class<E> entityClass) throws SpeedmentException {
        requireNonNull(entityClass);
        return (Manager<E>) Optional.ofNullable(managersByEntity.get(entityClass)).orElseThrow(() -> new SpeedmentException("No manager exists for " + entityClass));
    }

    @Override
    public Stream<Manager<?>> stream() {
        return managersByEntity.values().stream();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> Manager<ENTITY> findByTable(Table table) {
        requireNonNull(table);
        return (Manager<ENTITY>) tableMap.get(table);
    }

}
