package com.speedment.runtime.field.method;

import com.speedment.runtime.annotation.Api;
import static java.util.Objects.requireNonNull;

/**
 * A short-cut functional reference to the {@code setXXX(value)} method for a
 * particular field in an entity. The referenced method should return a
 * reference to itself.
 * <p>
 * A {@code ByteSetter<ENTITY>} has the following signature:
 * {@code
 *     interface ENTITY {
 *         ENTITY setXXX(byte value);
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
public interface ByteSetter<ENTITY> extends Setter<ENTITY> {
    
    /**
     * Sets the member represented by this setter in the specified instance to
     * the specified value, returning a reference to the same instance as
     * result.
     * 
     * @param instance the instance to set it in
     * @param value    the new value
     * @return         a reference to that instance
     */
    ENTITY setAsByte(ENTITY instance, byte value);
    
    @Override
    default ENTITY set(ENTITY instance, Object value) {
        requireNonNull(value, "Attempting to set primitive byte field to null.");
        @SuppressWarnings("unchecked")
        final Byte casted = (Byte) value;
        return setAsByte(instance, casted);
    }
}