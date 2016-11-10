package com.speedment.runtime.field.method;

import com.speedment.runtime.field.trait.HasReferenceValue;

/**
 *
 * @param <ENTITY> the entity type
 * @param <D>      the database type
 * @param <T>      the java type
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public interface GetReference<ENTITY, D, T> 
extends ReferenceGetter<ENTITY, T> {
    
    /**
     * Returns the field that created the {@code get()}-operation.
     * 
     * @return  the field
     */
    HasReferenceValue<ENTITY, D, T> getField();
    
}