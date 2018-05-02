package com.speedment.runtime.compute.expression;

import java.util.function.Function;

/**
 * @author Emil Forslund
 * @param <T> initial type
 * @param <A> intermediate type
 * 
 * @since  3.1.0
 */
public interface ComposedExpression<T, A> extends Expression<T> {

    Function<T, A> firstStep();

    Expression<A> secondStep();

}
