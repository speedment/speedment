package com.speedment.runtime.compute.expression;

import com.speedment.runtime.compute.expression.orelse.OrElseGetExpression;
import com.speedment.runtime.compute.expression.orelse.OrElseThrowExpression;

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
 * @param <T>      the input entity type
 * @param <INNER>  the type of the inner expression
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface NonNullableExpression<T, INNER extends Expression<T>>
extends Expression<T> {

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

        /**
         * Whenever the {@link #getInnerNullable() inner} expression returns a
         * {@code null} value, a specific constant value will be returned by
         * this expression. If this strategy is used, then the class should also
         * implement the corresponding {@code To__OrElseExpression} depending on
         * the {@link Expression#getExpressionType() type}.
         */
        USE_DEFAULT_VALUE,

        /**
         * Whenever the {@link #getInnerNullable() inner} expression returns a
         * {@code null} value, the
         * {@link OrElseGetExpression#getDefaultValueGetter() other} expression
         * will be invoked to determine the value instead. If this strategy is
         * used, then the class should also implement
         * {@link OrElseGetExpression}.
         */
        APPLY_DEFAULT_METHOD,

        /**
         * Whenever the {@link #getInnerNullable() inner} expression returns a
         * {@code null} value, a {@code NullPointerException} is thrown. If this
         * strategy is used, then the class should also implement
         * {@link OrElseThrowExpression}.
         */
        THROW_EXCEPTION
    }
}