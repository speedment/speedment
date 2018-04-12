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

    Operator getOperator();

    enum Operator {
        ABS,
        NEGATE,
        SIGN,
        SQRT
    }
}