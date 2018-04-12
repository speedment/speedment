package com.speedment.runtime.compute.expression;

import java.util.function.Predicate;

/**
 * Specific type of {@link Expression} that has an {@link #getInner() inner}
 * expression that is used for elements that does not pass the
 * {@link #getIsNull() isNull} predicate.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface NullableExpression<T, INNER extends Expression>
extends Expression {

    /**
     * The inner predicate that is used to determine the result after the
     * {@link #getIsNull()} has returned {@code false}. The inner expression
     * should have the same {@link Expression#getExpressionType() type} as this
     * one, except that it might not be nullable. It could also be nullable,
     * however.
     *
     * @return  the inner expression
     */
    INNER getInner();

    /**
     * Returns the predicate used to evaluate if an incoming element will be
     * mapped to {@code null} in this expression, or if the {@link #getInner()}
     * expression should be used.
     *
     * @return  predicate that gives {@code true} if an element should result in
     *          a {@code null} value in this expression, or {@code false} if the
     *          {@link #getInner()} expression should be used to determine the
     *          result
     */
    Predicate<T> getIsNull();

}
