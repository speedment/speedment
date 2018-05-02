package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToBigDecimal;
import com.speedment.runtime.compute.ToBigDecimalNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

import java.math.BigDecimal;

/**
 * Specialized {@link NonNullableExpression} for {@code BigDecimal} values where a
 * default value is given if the original expression returns {@code null}.
 *
 * @param <T> the input entity type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToBigDecimalOrElse<T>
extends NonNullableExpression<T, ToBigDecimalNullable<T>>, ToBigDecimal<T> {

    /**
     * Returns the default value used when {@link #innerNullable()} would
     * have returned {@code null}.
     *
     * @return  the default value
     */
    BigDecimal defaultValue();

    @Override
    default NullStrategy nullStrategy() {
        return NullStrategy.USE_DEFAULT_VALUE;
    }
}