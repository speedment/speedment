package com.speedment.runtime.field.method;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.util.ToBooleanFunction;

/**
 * A short-cut functional reference to the {@code getXXX(value)} method for a
 * particular field in an entity.
 * <p>
 * A {@code BooleanGetter<ENTITY>} has the following signature:
 * {@code
 *     interface ENTITY {
 *         boolean getXXX();
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
public interface BooleanGetter<ENTITY> extends Getter<ENTITY, Boolean>, ToBooleanFunction<ENTITY> {
    
    /**
     * Returns the member represented by this getter in the specified instance.
     * 
     * @param instance the instance to get from
     * @return         the value
     */
    @Override
    boolean applyAsBoolean(ENTITY instance);
    
    @Override
    default Boolean apply(ENTITY instance) {
        return applyAsBoolean(instance);
    }
}