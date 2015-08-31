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
package com.speedment.internal.core.platform.component;

import com.speedment.config.Table;
import com.speedment.exception.SpeedmentException;
import com.speedment.Manager;
import java.util.stream.Stream;

/**
 * The ManagerComponent provides the mapping between entities and their
 * corresponding managers. Custom managers may be plugged into the Speedment
 * framework.
 *
 * @author Emil Forslund
 * @since 2.0
 */
public interface ManagerComponent extends Component {

    @Override
    default Class<ManagerComponent> getComponentClass() {
        return ManagerComponent.class;
    }

    /**
     * Puts (associates) a {@link Manager} implementation into the
     * {@code ManagerComponent}. If a previous {@link Manager} was associated
     * with an Entity class, table or interface, that association(s) is/are
     * replaced.
     *
     * @param <E> the Entity interface type
     * @param manager to associate
     */
    <E> void put(Manager<E> manager);

//    /**
//     * Obtains and returns the currently associated {@link Manager}
//     * implementation for the given Manager interface Class.
//     *
//     * @param <E> the Entity interface type
//     * @param <M> the {@link Manager} interface type
//     * @param managerClass the {@link Manager} interface {@code Class}
//     * @return the currently associated {@link Manager} implementation for the
//     * given Manager interface Class
//     */
//    <E, M extends Manager<E>> M manager(Class<M> managerClass);
    /**
     * Obtains and returns the currently associated {@link Manager}
     * implementation for the given Entity interface Class. If no Manager exists
     * for the given entityClass, a SpeedmentException will be thrown.
     *
     * @param <E> the Entity interface type
     * @param entityClass the Entity interface {@code Class}
     * @return the currently associated {@link Manager} implementation for the
     * given Entity interface Class
     * @throws SpeedmentException if no Manager exists for the given entityClass
     */
    <E> Manager<E> managerOf(Class<E> entityClass) throws SpeedmentException;

    /**
     * Obtains and returns the currently associated {@link Manager}
     * implementation for the given Table.
     *
     * @param <E> the Entity interface type
     * @param table the table to use
     * @return the currently associated {@link Manager} implementation for the
     * given table
     */
    <E> Manager<E> findByTable(Table table);

    /**
     * Returns a {@link Stream} of all {@link Manager Managers} associated with
     * this ManagerComponent.
     *
     * @return a {@link Stream} of all {@link Manager Managers} associated with
     * this ManagerComponent
     */
    Stream<Manager<?>> stream();
}
