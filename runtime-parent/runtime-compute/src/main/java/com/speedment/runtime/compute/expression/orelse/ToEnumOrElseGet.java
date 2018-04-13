package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToEnum;
import com.speedment.runtime.compute.ToEnumNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code double} values where a
 * getter function is applied if the original expression returns {@code null}.
 *
 * @param <T> the input entity type
 * @param <E> the enum type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToEnumOrElseGet<T, E extends Enum<E>>
    extends OrElseGetExpression<ToEnumNullable<T, E>, ToEnum<T, E>>, ToEnum<T, E> {}