package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialization of {@link NonNullableExpression} that throws an exception if
 * the wrapped expression returns {@code null}.
 *
 * @param <INNER> the wrapped nullable expression
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface OrElseThrowExpression<INNER extends Expression>
extends NonNullableExpression<INNER> {
    @Override
    default NullStrategy getNullStrategy() {
        return NullStrategy.THROW_EXCEPTION;
    }
}