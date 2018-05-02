package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToBoolean;
import com.speedment.runtime.compute.ToBooleanNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code boolean} values where a
 * default value is given if the original expression returns {@code null}.
 *
 * @param <T> the input entity type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToBooleanOrElse<T>
extends NonNullableExpression<T, ToBooleanNullable<T>>, ToBoolean<T> {

    /**
     * Returns the default value used when {@link #innerNullable()} would
     * have returned {@code null}.
     *
     * @return  the default value
     */
    boolean defaultValue();

    @Override
    default NullStrategy nullStrategy() {
        return NullStrategy.USE_DEFAULT_VALUE;
    }
}