package com.speedment.runtime.compute.trait;

/**
 * Trait for expressions that has a {@link #sign()} method for getting the
 * sign (positive, negative or zero) of the result from the current expression.
 *
 * @param <E>  the expression type returned by the {@link #sign()} method
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasSign<E> {

    /**
     * Creates and returns an expression that returns {@code 1} if the result of
     * the this expression is positive, {@code -1} if the result of this
     * expression is negative and {@code 0} if the result of this expression is
     * {@code 0}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @return  the new expression
     */
    E sign();

}
