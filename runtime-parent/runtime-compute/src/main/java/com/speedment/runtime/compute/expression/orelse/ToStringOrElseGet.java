package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToString;
import com.speedment.runtime.compute.ToStringNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code String} values where a
 * getter function is applied if the original expression returns {@code null}.
 *
 * @param <T> the input entity type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToStringOrElseGet<T>
    extends OrElseGetExpression<ToStringNullable<T>, ToString<T>>, ToString<T> {}