package com.speedment.runtime.field.method;

import com.speedment.runtime.annotation.Api;
import java.util.function.ToDoubleFunction;
import javax.annotation.Generated;

/**
 * A short-cut functional reference to the {@code getXXX(value)} method for a
 * particular field in an entity.
 * <p>
 * A {@code DoubleGetter<ENTITY>} has the following signature:
 * {@code
 *     interface ENTITY {
 *         double getXXX();
 *     }
 * }
 * 
 * @param <ENTITY> the entity
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Api(version = "3.0")
@Generated(value = "Speedment")
@FunctionalInterface
public interface DoubleGetter<ENTITY> extends Getter<ENTITY>, ToDoubleFunction<ENTITY> {
    
    /**
     * Returns the member represented by this getter in the specified instance.
     * 
     * @param instance the instance to get from
     * @return         the value
     */
    @Override
    double applyAsDouble(ENTITY instance);
    
    @Override
    default Double apply(ENTITY instance) {
        return applyAsDouble(instance);
    }
}