package com.speedment.runtime.compute.expression;

/**
 * An {@link Expression} that has a single operand that implements
 * {@link Expression}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface UnaryExpression<INNER extends Expression> extends Expression {

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
        ABS,
        CAST,
        NEGATE,
        SIGN,
        SQRT
    }
}