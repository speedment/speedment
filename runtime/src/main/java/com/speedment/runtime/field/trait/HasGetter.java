package com.speedment.runtime.field.trait;

import com.speedment.runtime.field.method.Getter;

/**
 *
 * @param <ENTITY>  the entity type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public interface HasGetter<ENTITY> {
    
    /**
     * Returns a reference to the getter for this field.
     * 
     * @return  the getter
     */
    Getter<ENTITY> getter();
    
}
