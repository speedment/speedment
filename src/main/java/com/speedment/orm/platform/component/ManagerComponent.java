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
package com.speedment.orm.platform.component;

import com.speedment.orm.config.model.Table;
import com.speedment.orm.core.Buildable;
import com.speedment.orm.core.manager.Manager;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public interface ManagerComponent extends Component {

    @Override
    default Class<ManagerComponent> getComponentClass() {
        return ManagerComponent.class;
    }

    <PK, E, B extends Buildable<E>> void put(Manager<PK, E, B> manager);

    <PK, E, B extends Buildable<E>, M extends Manager<PK, E, B>> M manager(Class<M> managerClass);

    <PK, E, B extends Buildable<E>> Manager<PK, E, B> managerOf(Class<E> entityClass);

    Stream<Manager<?, ?, ?>> stream();
    
    <PK, E, B extends Buildable<E>> Manager<PK, E, B> findByTable(Table table);

}
