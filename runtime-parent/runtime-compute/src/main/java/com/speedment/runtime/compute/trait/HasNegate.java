package com.speedment.runtime.compute.trait;

/**
 * Trait for expressions that has a {@link #negate()} method for getting the
 * negative value of the result from the current expression. For an example, a
 * positive value will become negative and a negative value will become
 * positive.
 *
 * @param <E>  the expression type returned by the {@link #negate()} method
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasNegate<E extends HasNegate<E>> {

    /**
     * Returns a new expression that returns the absolute value of the result
     * of the current expression. For an example, a positive value will become
     * negative and a negative value will become positive.
     *
     * @return  the new expression
     */
    E negate();

}
