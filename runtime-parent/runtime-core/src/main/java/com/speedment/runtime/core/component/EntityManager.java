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
package com.speedment.runtime.core.component;

import com.speedment.common.injector.annotation.InjectKey;

/**
 * An Entity Manager is be used to handle persistence for any Entity. This
 * Component provides an interface similar to JPA but is not used for any other
 * purpose. Thus, it acts as a delegator.
 *
 * @author  Per Minborg
 * @since   2.0.0
 */
@InjectKey(EntityManager.class)
public interface EntityManager  {

    // Persistence
    /**
     * Persists the given Entity and returns a new {@code Optional<Entity>} that
     * was the result of the persistence, or Optional.empty() if the method
     * failed.
     *
     * @param <ENTITY> the type of the Entity
     * @param entity to persist
     */
    <ENTITY> void persist(ENTITY entity);

    /**
     * Updates the given Entity and returns a new {@code Optional<Entity>} that
     * was the result of the update, or Optional.empty() if the method failed.
     *
     * @param <ENTITY> the type of the Entity
     * @param entity to update
     */
    <ENTITY> void update(ENTITY entity);

    /**
     * Updates the given Entity and returns a new {@code Optional<Entity>} that
     * was the result of the update, or Optional.empty() if the method failed.
     *
     * @param <ENTITY> the type of the Entity
     * @param entity to remove
     */
    <ENTITY> void remove(ENTITY entity);
}
