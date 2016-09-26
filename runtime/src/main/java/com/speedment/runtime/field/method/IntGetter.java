package com.speedment.runtime.field.method;

import com.speedment.runtime.annotation.Api;
import java.util.function.ToIntFunction;

/**
 * A short-cut functional reference to the {@code getXXX(value)} method for a
 * particular field in an entity.
 * <p>
 * A {@code IntegerGetter<ENTITY>} has the following signature:
 * {@code
 *     interface ENTITY {
 *         int getXXX();
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
public interface IntGetter<ENTITY> extends Getter<ENTITY, Integer>, ToIntFunction<ENTITY> {
    
    /**
     * Returns the member represented by this getter in the specified instance.
     * 
     * @param instance the instance to get from
     * @return         the value
     */
    @Override
    int applyAsInt(ENTITY instance);
    
    @Override
    default Integer apply(ENTITY instance) {
        return applyAsInt(instance);
    }
}