/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.manager;


import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.field.Field;

import java.util.stream.Stream;

/**
 * A support class that is used to perform operations on the database.
 * 
 * @param <ENTITY>  the entity type
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */

public interface ManagerSupport<ENTITY> {

    /**
     * Returns the manager that this was created for.
     * 
     * @return  the manager
     */
    Manager<ENTITY> getManager();
    
    /**
     * Returns the fully qualified name for this field.
     * 
     * @param field  the field
     * @return       the fully qualified name
     */
    String fullColumnName(Field<ENTITY> field);
    
    /**
     * Creates a stream of entities. For full documentation, see 
     * {@link Manager#stream()}.
     * 
     * @return  stream of entities.
     */
    Stream<ENTITY> stream();
    
    /**
     * Persists an entity in the database. For full documentation, see 
     * {@link Manager#persist(Object)}.
     * 
     * @param entity  the entity to persist
     * @return        the new entity as persisted
     */
    ENTITY persist(ENTITY entity) throws SpeedmentException;

    /**
     * Updates an entity in the database. For full documentation, see 
     * {@link Manager#update(Object)}.
     * 
     * @param entity  the entity to update
     * @return        the new entity as updated
     */
    ENTITY update(ENTITY entity) throws SpeedmentException;

    /**
     * Removes an entity from the database. For full documentation, see 
     * {@link Manager#remove(Object)}.
     * 
     * @param entity  the entity to remove
     * @return        the entity as it was before it was removed
     */
    ENTITY remove(ENTITY entity) throws SpeedmentException;
    
}