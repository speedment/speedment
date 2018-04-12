package com.speedment.runtime.compute.expression;

/**
 * The base interface for all expressions.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface Expression {

    /**
     * Returns the expression type of this expression. It should be safe to cast
     * this instance into the corresponding interface.
     *
     * @return  the expression type
     */
    ExpressionType getExpressionType();

}
