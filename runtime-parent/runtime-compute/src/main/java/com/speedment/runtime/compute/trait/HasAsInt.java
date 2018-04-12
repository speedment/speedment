package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.ToInt;

/**
 * Trait for expressions that can be casted to an {@code int}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasAsInt<T> {

    /**
     * Returns an expression that casts the result of the current expression
     * into an {@code int}.
     *
     * @return  the casted expression
     */
    ToInt<T> asInt();
}