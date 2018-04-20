package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToShort;
import com.speedment.runtime.compute.ToShortNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code short} values where a
 * getter function is applied if the original expression returns {@code null}.
 *
 * @param <T> the input entity type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToShortOrElseGet<T>
extends OrElseGetExpression<T, ToShortNullable<T>, ToShort<T>>, ToShort<T> {}