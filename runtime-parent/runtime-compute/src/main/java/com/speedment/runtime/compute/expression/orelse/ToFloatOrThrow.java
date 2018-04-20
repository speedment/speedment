package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToFloat;
import com.speedment.runtime.compute.ToFloatNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code float} values where a
 * {@link NullPointerException} is thrown if the original expression returns
 * {@code null}.
 *
 * @param <T> the input entity type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToFloatOrThrow<T>
extends OrElseThrowExpression<T, ToFloatNullable<T>>, ToFloat<T> {}
