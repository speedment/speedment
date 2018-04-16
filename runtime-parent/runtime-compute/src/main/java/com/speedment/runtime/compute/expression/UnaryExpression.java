package com.speedment.runtime.compute.expression;

/**
 * An {@link Expression} that has a single operand that implements
 * {@link Expression}.
 * <p>
 * Equality is determined by looking at {@link #getInner()} and
 * {@link #getOperator()}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface UnaryExpression<INNER extends Expression> extends Expression {

    /**
     * The inner expression that this wraps and applies a unary operator to.
     *
     * @return  the inner expression
     */
    INNER getInner();

    /**
     * Returns the binary operator that this expression represents.
     *
     * @return  the operator
     */
    Operator getOperator();

    /**
     * The unary expression operator type.
     */
    enum Operator {

        /**
         * This expression is the absolute value of the result from the inner
         * expression. That is, the negation sign is ignored, returning only
         * positive values.
         */
        ABS,

        /**
         * This expression is the result of the inner expression casted to a
         * different type
         */
        CAST,

        /**
         * This expression is the negation of the inner expression. That is, the
         * negation sign is flipped.
         */
        NEGATE,

        /**
         * The result of this expression is {@code 0} if the result of the inner
         * expression is {@code 0}, {@code 1} of the inner returned something
         * positive and {@code -1} if the inner returned something negative.
         */
        SIGN,

        /**
         * The result of this expression is the positive square root of the
         * result of the inner expression.
         */
        SQRT
    }
}