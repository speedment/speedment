package com.speedment.runtime.field.method;

import com.speedment.runtime.annotation.Api;
import java.util.function.ToLongFunction;

/**
 * A short-cut functional reference to the {@code getXXX(value)} method for a
 * particular field in an entity.
 * <p>
 * A {@code LongGetter<ENTITY>} has the following signature:
 * {@code
 *     interface ENTITY {
 *         long getXXX();
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
public interface LongGetter<ENTITY> extends Getter<ENTITY>, ToLongFunction<ENTITY> {
    
    /**
     * Returns the member represented by this getter in the specified instance.
     * 
     * @param instance the instance to get from
     * @return         the value
     */
    @Override
    long applyAsLong(ENTITY instance);
    
    @Override
    default Long apply(ENTITY instance) {
        return applyAsLong(instance);
    }
}