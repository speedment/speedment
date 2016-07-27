package com.speedment.runtime.field.trait;

import com.speedment.runtime.field.method.Setter;

/**
 *
 * @param <ENTITY>  the entity type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public interface HasSetter<ENTITY> {
    
    /**
     * Returns a reference to the setter for this field.
     * 
     * @return  the setter
     */
    Setter<ENTITY> setter();
    
}
