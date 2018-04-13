package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code double} values where a
 * getter function is applied if the original expression returns {@code null}.
 *
 * @param <T> the input entity type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToDoubleOrElseGet<T>
extends NonNullableExpression<ToDoubleNullable<T>>, ToDouble<T> {

    /**
     * Returns the default value used when {@link #getInnerNullable()} would
     * have returned {@code null}.
     *
     * @return  the default value
     */
    ToDouble<T> getDefaultValueGetter();

    @Override
    default NullStrategy getNullStrategy() {
        return NullStrategy.APPLY_DEFAULT_METHOD;
    }
}