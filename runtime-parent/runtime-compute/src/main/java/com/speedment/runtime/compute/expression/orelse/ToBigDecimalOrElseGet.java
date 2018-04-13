package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToBigDecimal;
import com.speedment.runtime.compute.ToBigDecimalNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code BigDecimal} values where
 * a getter function is applied if the original expression returns {@code null}.
 *
 * @param <T> the input entity type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToBigDecimalOrElseGet<T>
    extends OrElseGetExpression<ToBigDecimalNullable<T>, ToBigDecimal<T>>, ToBigDecimal<T> {}