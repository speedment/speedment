package com.speedment.runtime.compute.trait;

/**
 * Trait for expressions that has a {@link #sqrt()} method for getting the
 * square root of the result from the current expression.
 *
 * @param <E>  the expression type returned by the {@link #sqrt()} method
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasSqrt<E> {

    /**
     * Creates and returns an expression that returns the square root of the
     * result from the current expression.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @return  the new expression
     */
    E sqrt();

}
