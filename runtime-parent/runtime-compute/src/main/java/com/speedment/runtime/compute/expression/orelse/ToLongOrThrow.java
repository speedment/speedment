package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToLong;
import com.speedment.runtime.compute.ToLongNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code long} values where a
 * {@link NullPointerException} is thrown if the original expression returns
 * {@code null}.
 *
 * @param <T> the input entity type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToLongOrThrow<T>
extends OrElseThrowExpression<T, ToLongNullable<T>>, ToLong<T> {}
