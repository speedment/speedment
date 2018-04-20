package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code double} values where a
 * default value is given if the original expression returns {@code null}.
 *
 * @param <T> the input entity type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToDoubleOrElse<T>
extends NonNullableExpression<T, ToDoubleNullable<T>>, ToDouble<T> {

    /**
     * Returns the default value used when {@link #getInnerNullable()} would
     * have returned {@code null}.
     *
     * @return  the default value
     */
    double getDefaultValue();

    @Override
    default NullStrategy getNullStrategy() {
        return NullStrategy.USE_DEFAULT_VALUE;
    }
}