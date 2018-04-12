package com.speedment.runtime.compute.expression;

/**
 * An {@link Expression} that has two operands, one is another
 * {@link Expression} and the other is a primitive {@code int}.
 *
 * @param <FIRST>  the type of the first operand, an expression
 * @param <T>      the type of the second operand, a constant value
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface BinaryObjExpression<FIRST extends Expression, T>
extends Expression {

    /**
     * Returns the first operand, an inner expression.
     *
     * @return  the first operand
     */
    FIRST getFirst();

    /**
     * Returns the second operand, a constant value.
     *
     * @return  the second operand
     */
    T getSecond();

    /**
     * Returns the binary operator that this expression represents.
     *
     * @return  the operator
     */
    Operator getOperator();

    /**
     * Operator types that could be returned by {@link #getOperator()}.
     */
    enum Operator {
        /**
         * The result of the first operand raised to the power of the second.
         */
        POW,

        /**
         * The result of the first operand added to the second (addition).
         */
        PLUS,

        /**
         * The result of the first operand minus the second (subtraction).
         */
        MINUS,

        /**
         * The result of the first operand multiplied by the second
         * (multiplication).
         */
        MULTIPLY,

        /**
         * The result of the first operand divided by the second (division).
         */
        DIVIDE,

        /**
         * The floored result of the first operand divided by the second. This
         * will always return some integer type.
         */
        DIVIDE_FLOOR
    }
}