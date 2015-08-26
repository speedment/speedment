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
package com.speedment.core.platform.component.impl;

import com.speedment.core.config.model.Table;
import com.speedment.core.exception.SpeedmentException;
import com.speedment.core.manager.Manager;
import com.speedment.core.platform.component.ManagerComponent;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public class ManagerComponentImpl implements ManagerComponent {

    private final Map<Class<?>, Manager<?>> managersByEntity/*, managersByManager*/;
    private final Map<Table, Manager<?>> tableMap;

    public ManagerComponentImpl() {
        managersByEntity = new ConcurrentHashMap<>();
//        managersByManager = new ConcurrentHashMap<>();
        tableMap = new ConcurrentHashMap<>();
    }

    @Override
    public <ENTITY> void put(Manager<ENTITY> manager) {
        managersByEntity.put(manager.getEntityClass(), manager);
//        managersByManager.put(manager.getManagerClass(), manager);
        tableMap.put(manager.getTable(), manager);
    }

//    @Override
//    @SuppressWarnings("unchecked")
//    public <ENTITY, M extends Manager<ENTITY>> M manager(Class<M> managerClass) {
//        return (M) managersByManager.get(managerClass);
//    }
    @Override
    @SuppressWarnings("unchecked")
    public <E> Manager<E> managerOf(Class<E> entityClass) throws SpeedmentException {
        return (Manager<E>) Optional.ofNullable(managersByEntity.get(entityClass)).orElseThrow(() -> new SpeedmentException("No manager exists for " + entityClass));
    }

    @Override
    public Stream<Manager<?>> stream() {
        return managersByEntity.values().stream();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> Manager<ENTITY> findByTable(Table table) {
        return (Manager<ENTITY>) tableMap.get(table);
    }

}
