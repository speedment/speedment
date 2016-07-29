package com.speedment.runtime.field.method;

import com.speedment.runtime.annotation.Api;

/**
 * A short-cut functional reference to the {@code getXXX(value)} method for a
 * particular field in an entity.
 * <p>
 * A {@code ByteGetter<ENTITY>} has the following signature:
 * {@code
 *     interface ENTITY {
 *         byte getXXX();
 *     }
 * }
 * 
 * @param <ENTITY> the entity
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Api(version = "3.0")
@FunctionalInterface
public interface ByteGetter<ENTITY>  extends Getter<ENTITY> {
    
    /**
     * Returns the member represented by this getter in the specified instance.
     * 
     * @param instance the instance to get from
     * @return         the value
     */
    byte getAsByte(ENTITY instance);
}