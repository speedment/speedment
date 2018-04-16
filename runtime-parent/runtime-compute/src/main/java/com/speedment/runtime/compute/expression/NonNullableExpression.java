package com.speedment.runtime.compute.expression;

import com.speedment.runtime.compute.expression.orelse.OrElseGetExpression;

/**
 * Specialized {@link Expression} that is not nullable, but that wraps an
 * expression that is and that has some routine for dealing with {@code null}
 * values determined by {@link #getNullStrategy()}.
 * <p>
 * Equality is determined by looking at {@link #getInnerNullable()} and
 * {@link #getNullStrategy()}, and additionally by the
 * {@link OrElseGetExpression#getDefaultValueGetter()} or the
 * {@code getDefaultValue()} if the strategy is
 * {@link NullStrategy#APPLY_DEFAULT_METHOD} or
 * {@link NullStrategy#USE_DEFAULT_VALUE} respectively.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface NonNullableExpression<INNER extends Expression>
extends Expression {

    /**
     * Returns the inner nullable expression.
     *
     * @return  the inner nullable expression
     */
    INNER getInnerNullable();

    /**
     * Returns the strategy used by this expression to deal with the case when
     * {@link #getInnerNullable()} would have returned {@code null}.
     *
     * @return  the null-strategy used
     */
    NullStrategy getNullStrategy();

    /**
     * The strategies possible when dealing with {@code null}-values.
     */
    enum NullStrategy {
        USE_DEFAULT_VALUE,
        APPLY_DEFAULT_METHOD,
        THROW_EXCEPTION
    }
}