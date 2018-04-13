package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToByteNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code byte} values where a
 * default value is given if the original expression returns {@code null}.
 *
 * @param <T> the input entity type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToByteOrElse<T>
extends NonNullableExpression<ToByteNullable<T>>, ToByte<T> {

    /**
     * Returns the default value used when {@link #getInnerNullable()} would
     * have returned {@code null}.
     *
     * @return  the default value
     */
    byte getDefaultValue();

    @Override
    default NullStrategy getNullStrategy() {
        return NullStrategy.USE_DEFAULT_VALUE;
    }
}