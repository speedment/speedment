package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.expression.Expression;

import java.util.function.Function;

/**
 * Trait for expressions that has a {@link #compose(Function)} method similar to
 * {@link Function#compose(Function)}, but even primitive expressions might
 * implement this trait.
 *
 * @author Emil Forslund
 * @since  1.2.0
 */
public interface HasCompose<T> {

    /**
     * Returns a composed expression that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> the type of input to the {@code before} function, and to the
     *            composed expression
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     *         function and then applies this function
     * @throws NullPointerException if before is null
     */
    <V> Expression<V> compose(Function<? super V, ? extends T> before);
}
