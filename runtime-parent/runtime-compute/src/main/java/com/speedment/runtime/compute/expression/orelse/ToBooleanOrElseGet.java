package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToBoolean;
import com.speedment.runtime.compute.ToBooleanNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code boolean} values where a
 * getter function is applied if the original expression returns {@code null}.
 *
 * @param <T> the input entity type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToBooleanOrElseGet<T>
extends OrElseGetExpression<T, ToBooleanNullable<T>, ToBoolean<T>>,
        ToBoolean<T> {}