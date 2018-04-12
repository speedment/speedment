package com.speedment.runtime.compute.trait;

/**
 * Trait for expressions that has a {@link #abs()} method for getting the
 * absolute value of the result from the current expression.
 *
 * @param <E>  the expression type returned by the {@link #abs()} method
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasAbs<E extends HasAbs<E>> {

    /**
     * Returns a new expression that returns the absolute value of the result
     * of the current expression.
     *
     * @return  the new expression
     */
    E abs();

}
