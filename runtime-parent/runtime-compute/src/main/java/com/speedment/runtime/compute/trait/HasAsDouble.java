package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.ToDouble;

/**
 * Trait for expressions that can be casted to a {@code double}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasAsDouble<T> {

    /**
     * Returns an expression that casts the result of the current expression
     * into a {@code double}.
     *
     * @return  the casted expression
     */
    ToDouble<T> asDouble();
}