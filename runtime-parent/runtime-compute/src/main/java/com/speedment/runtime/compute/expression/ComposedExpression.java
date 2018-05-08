package com.speedment.runtime.compute.expression;

import java.util.function.Function;

/**
 * Specialized {@link Expression} interface used when a {@link #firstStep()}
 * function is first applied to an incomming entity, before a
 * {@link #secondStep()} expression is applied to get the result. This is
 * typically used to implement operations like
 * {@link Function#compose(Function)}.
 *
 * @param <T> initial type
 * @param <A> intermediate type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ComposedExpression<T, A> extends Expression<T> {

    /**
     * The function that is applied to incoming entities to get the value that
     * is to be passed to {@link #secondStep()}.
     *
     * @return  the first step function
     */
    Function<T, A> firstStep();

    /**
     * The inner expression that is applied to the result of
     * {@link #firstStep()} to get the result of this full expression.
     *
     * @return  the second step expression
     */
    Expression<A> secondStep();

}
