package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.ToEnum;
import com.speedment.runtime.compute.ToEnumNullable;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialized {@link NonNullableExpression} for {@code Enum} values where a
 * {@link NullPointerException} is thrown if the original expression returns
 * {@code null}.
 *
 * @param <T> the input entity type
 * @param <E> the enum type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToEnumOrThrow<T, E extends Enum<E>>
extends OrElseThrowExpression<ToEnumNullable<T, E>>, ToEnum<T, E> {

    @Override
    default Class<E> enumClass() {
        return getInnerNullable().enumClass();
    }
}
