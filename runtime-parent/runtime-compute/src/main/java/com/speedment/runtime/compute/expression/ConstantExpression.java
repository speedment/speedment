package com.speedment.runtime.compute.expression;

/**
 * Specialized {@link Expression} that always returns the same value, regardless
 * of the input.
 * <p>
 * Equality is determined by looking at the {@link #value()}.
 *
 * @param <T>  the input entity type
 * @param <V>  the resulting type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ConstantExpression<T, V> extends Expression<T> {

    /**
     * Returns the constant value of this expression. The constant value is the
     * result of the expression, regardless of what the input it.
     *
     * @return  the constant value
     */
    V value();

}
