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
package com.speedment.runtime.core.internal.component.sql;

import com.speedment.runtime.core.component.sql.SqlPersistenceComponent;
import com.speedment.runtime.core.exception.SpeedmentException;

/**
 * The common interface for table specific persisting handlers that is managed 
 * by a {@link SqlPersistenceComponent}.
 * 
 * @param <ENTITY>  the entity type
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
interface SqlPersistence<ENTITY> {

    /**
     * Persists the specified entity in the table managed by this handler.
     * 
     * @param entity  the entity to persist
     * @return        the new persisted entity
     * 
     * @throws SpeedmentException  if the entity could not be persisted
     */
    ENTITY persist(ENTITY entity);

    /**
     * Updates the specified entity in the table managed by this handler.
     * 
     * @param entity  the entity to update
     * @return        the new updated entity
     * 
     * @throws SpeedmentException  if the entity could not be updated
     */
    ENTITY update(ENTITY entity);

    /**
     * Removes the specified entity from the table managed by this handler.
     * 
     * @param entity  the entity to remove
     * @return        the entity as it was before it was removed
     * 
     * @throws SpeedmentException  if the entity could not be removed
     */
    ENTITY remove(ENTITY entity);

    
}