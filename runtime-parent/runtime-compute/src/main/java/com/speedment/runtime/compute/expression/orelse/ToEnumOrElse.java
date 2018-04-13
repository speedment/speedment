package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToEnum;
import com.speedment.runtime.compute.ToEnumNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code Enum} values where a
 * default value is given if the original expression returns {@code null}.
 *
 * @param <T> the input entity type
 * @param <E> the enum type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToEnumOrElse<T, E extends Enum<E>>
extends NonNullableExpression<ToEnumNullable<T, E>>, ToEnum<T, E> {

    /**
     * Returns the default value used when {@link #getInnerNullable()} would
     * have returned {@code null}.
     *
     * @return  the default value
     */
    E getDefaultValue();

    @Override
    default NullStrategy getNullStrategy() {
        return NullStrategy.USE_DEFAULT_VALUE;
    }
}