package com.speedment.runtime.compute.expression;

/**
 * The base interface for all expressions. Some implementations of this
 * interface will be lambdas or anonymous classes and in those cases, the
 * equality is unspecified. However, there are implementations of this interface
 * that offer a specified equality contract.
 *
 * @param <T> the input entity type of this expression
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface Expression<T> {

    /**
     * Returns the expression type of this expression. It should be safe to cast
     * this instance into the corresponding interface.
     *
     * @return  the expression type
     */
    ExpressionType expressionType();

}
