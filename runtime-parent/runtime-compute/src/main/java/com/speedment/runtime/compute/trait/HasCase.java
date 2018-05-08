package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.expression.Expression;

/**
 * Trait for expressions that has {@link #toUpperCase()} and
 * {@link #toLowerCase()} operations.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasCase<T, RESULT extends Expression<T>> {

    /**
     * Returns an expression that is equivalent to this expression, except that
     * results are always in upper-case letters.
     *
     * @return  the upper case expression
     */
    RESULT toUpperCase();

    /**
     * Returns an expression that is equivalent to this expression, except that
     * results are always in lower-case letters.
     *
     * @return  the lower case expression
     */
    RESULT toLowerCase();

}
