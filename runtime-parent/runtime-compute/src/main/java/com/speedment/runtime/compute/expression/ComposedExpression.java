package com.speedment.runtime.compute.expression;

import java.util.function.Function;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ComposedExpression<T, A> extends Expression<T> {

    Function<T, A> firstStep();

    Expression<A> secondStep();

}
