package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToByteNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code byte} values where a
 * getter function is applied if the original expression returns {@code null}.
 *
 * @param <T> the input entity type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToByteOrElseGet<T>
extends OrElseGetExpression<T, ToByteNullable<T>, ToByte<T>>, ToByte<T> {}