package com.speedment.runtime.compute.expression;

import java.util.function.Predicate;

/**
 * Specific type of {@link Expression} that has an {@link #inner() inner}
 * expression that is used for elements that does not pass the
 * {@link #isNullPredicate() isNull} predicate.
 * <p>
 * Equality is determined by looking at the {@link #inner()} and
 * {@link #isNullPredicate()}.
 *
 * @param <T>      the input entity type
 * @param <INNER>  type of the inner expression
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface NullableExpression<T, INNER extends Expression<T>>
extends Expression<T> {

    /**
     * The inner predicate that is used to determine the result after the
     * {@link #isNullPredicate()} has returned {@code false}. The inner expression
     * should have the same {@link Expression#expressionType() type} as this
     * one, except that it might not be nullable. It could also be nullable,
     * however.
     *
     * @return  the inner expression
     */
    INNER inner();

    /**
     * Returns the predicate used to evaluate if an incoming element will be
     * mapped to {@code null} in this expression, or if the {@link #inner()}
     * expression should be used.
     *
     * @return  predicate that gives {@code true} if an element should result in
     *          a {@code null} value in this expression, or {@code false} if the
     *          {@link #inner()} expression should be used to determine the
     *          result
     */
    Predicate<T> isNullPredicate();
}
