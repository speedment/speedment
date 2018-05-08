package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.ToDouble;

/**
 * Trait for expressions that can be mapped to a new expression of the same type
 * by supplying a mapping function.
 *
 * @param <T>       the input entity type
 * @param <MAPPER>  the functional interface used for mapping values
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasMapToDouble<T, MAPPER> {

    /**
     * Maps the result of this expression into a {@code double} by passing it to
     * the specified {@code mapper}, producing a new expression that implements
     * {@link ToDouble}.
     *
     * @param mapper  the mapper to use on the result of this expression
     * @return        the new mapped expression
     */
    ToDouble<T> mapToDouble(MAPPER mapper);

}