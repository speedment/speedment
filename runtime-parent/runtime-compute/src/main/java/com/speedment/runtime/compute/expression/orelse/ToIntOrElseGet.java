package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToIntNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code int} values where a
 * getter function is applied if the original expression returns {@code null}.
 *
 * @param <T> the input entity type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToIntOrElseGet<T>
    extends OrElseGetExpression<ToIntNullable<T>, ToInt<T>>, ToInt<T> {}