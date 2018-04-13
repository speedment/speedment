package com.speedment.runtime.compute.expression;

/**
 * Specialized {@link Expression} that is not nullable, but that wraps an
 * expression that is and that has some routine for dealing with {@code null}
 * values determined by {@link #getNullStrategy()}.
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