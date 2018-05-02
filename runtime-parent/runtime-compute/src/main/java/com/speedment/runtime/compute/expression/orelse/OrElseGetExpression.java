package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialization of {@link NonNullableExpression} that has a
 * {@link #defaultValueGetter()} method.
 *
 * @param <T>        the input entity type
 * @param <INNER>    the wrapped nullable expression
 * @param <DEFAULT>  the getter expression for the default value
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface OrElseGetExpression
    <T, INNER extends Expression<T>, DEFAULT extends Expression<T>>
extends NonNullableExpression<T, INNER> {

    /**
     * Returns the default value used when {@link #innerNullable()} would
     * have returned {@code null}.
     *
     * @return  the default value
     */
    DEFAULT defaultValueGetter();

    @Override
    default NullStrategy nullStrategy() {
        return NullStrategy.APPLY_DEFAULT_METHOD;
    }
}
