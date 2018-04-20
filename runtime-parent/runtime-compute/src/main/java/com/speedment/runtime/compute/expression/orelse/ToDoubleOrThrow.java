package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code double} values where a
 * {@link NullPointerException} is thrown if the original expression returns
 * {@code null}.
 *
 * @param <T> the input entity type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToDoubleOrThrow<T>
extends OrElseThrowExpression<T, ToDoubleNullable<T>>, ToDouble<T> {}
