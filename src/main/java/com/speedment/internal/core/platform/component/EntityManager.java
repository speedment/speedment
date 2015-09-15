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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.internal.core.platform.component;

import com.speedment.Speedment;
import com.speedment.annotation.Api;
import com.speedment.exception.SpeedmentException;
import static java.util.Objects.requireNonNull;

/**
 * An Entity Manager is be used to handle persistence for any Entity. This
 * Component provides an interface similar to JPA but is not used for any other
 * purpose. Thus, it acts as a delegator.
 *
 * @author pemi
 * @since 2.0
 */
@Api(version = "2.0")
public interface EntityManager extends Component {

    @Override
    default Class<EntityManager> getComponentClass() {
        return EntityManager.class;
    }

    // Persistence
    /**
     * Persists the given Entity and returns a new {@code Optional<Entity>} that
     * was the result of the persistence, or Optional.empty() if the method
     * failed.
     *
     * @param <ENTITY> the type of the Entity
     * @param entity to persist
     */
    <ENTITY> void persist(ENTITY entity) throws SpeedmentException;

    /**
     * Updates the given Entity and returns a new {@code Optional<Entity>} that
     * was the result of the update, or Optional.empty() if the method failed.
     *
     * @param <ENTITY> the type of the Entity
     * @param entity to update
     */
    <ENTITY> void update(ENTITY entity) throws SpeedmentException;

    /**
     * Updates the given Entity and returns a new {@code Optional<Entity>} that
     * was the result of the update, or Optional.empty() if the method failed.
     *
     * @param <ENTITY> the type of the Entity
     * @param entity to remove
     */
    <ENTITY> void remove(ENTITY entity) throws SpeedmentException;

    /**
     * Returns the default EntityManager from the Platform. This method is there
     * for JPA compatibility.
     *
     * @param speedment instance to use
     * @return the default EntityManager from the Platform
     */
    public static EntityManager get(Speedment speedment) {
        requireNonNull(speedment);
        return speedment.get(EntityManager.class);
    }
}
