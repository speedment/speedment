package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.ToLong;

/**
 * Trait for expressions that can be casted to a {@code long}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasAsLong<T> {

    /**
     * Returns an expression that casts the result of the current expression
     * into a {@code long}.
     *
     * @return  the casted expression
     */
    ToLong<T> asLong();
}