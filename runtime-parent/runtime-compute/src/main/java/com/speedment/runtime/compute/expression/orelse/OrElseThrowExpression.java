package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialization of {@link NonNullableExpression} that throws an exception if
 * the wrapped expression returns {@code null}.
 *
 * @param <T>     the input entity type
 * @param <INNER> the wrapped nullable expression
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface OrElseThrowExpression<T, INNER extends Expression<T>>
extends NonNullableExpression<T, INNER> {
    @Override
    default NullStrategy nullStrategy() {
        return NullStrategy.THROW_EXCEPTION;
    }
}