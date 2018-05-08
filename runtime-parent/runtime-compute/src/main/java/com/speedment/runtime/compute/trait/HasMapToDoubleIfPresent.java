package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToDoubleNullable;

/**
 * Trait for nullable expressions that can be mapped to a new expression of the
 * same type by supplying a mapping function. If the value is present in the
 * original expression, then it will be present in the new one as well. If the
 * result of the original expression is {@code null}, then the new expression
 * will evaluate to {@code null} also.
 *
 * @param <T>       the input entity type
 * @param <MAPPER>  the functional interface used for mapping values
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasMapToDoubleIfPresent<T, MAPPER> {

    /**
     * Maps the result of this expression into a {@code double} by passing it to
     * the specified {@code mapper}, producing a new expression that implements
     * {@link ToDouble}. If this expression would have evaluated to
     * {@code null}, then the new expression will as well.
     *
     * @param mapper  the mapper to use on the result of this expression
     * @return        the new mapped expression
     */
    ToDoubleNullable<T> mapToDoubleIfPresent(MAPPER mapper);

}