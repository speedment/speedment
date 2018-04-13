package com.speedment.runtime.field.expression;

import com.speedment.common.function.ToBooleanFunction;
import com.speedment.runtime.compute.ToBooleanNullable;

/**
 * Specific {@link FieldMapper} implementation that also implements
 * {@link ToBooleanNullable}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldToBoolean<ENTITY, T>
extends FieldMapper<ENTITY, T, ToBooleanFunction<T>>,
        ToBooleanNullable<ENTITY> {}