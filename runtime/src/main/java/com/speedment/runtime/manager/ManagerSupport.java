/*
 * Copyright (c) Emil Forslund, 2016.
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Emil Forslund and his suppliers, if any. 
 * The intellectual and technical concepts contained herein 
 * are proprietary to Emil Forslund and his suppliers and may 
 * be covered by U.S. and Foreign Patents, patents in process, 
 * and are protected by trade secret or copyright law. 
 * Dissemination of this information or reproduction of this 
 * material is strictly forbidden unless prior written 
 * permission is obtained from Emil Forslund himself.
 */
package com.speedment.runtime.manager;

import com.speedment.runtime.annotation.Api;
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
@Api(version = "3.0")
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
    String fullColumnName(Field<ENTITY, ?> field);
    
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