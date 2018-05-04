package com.speedment.runtime.field.expression;

import com.speedment.common.function.ToBooleanFunction;
import com.speedment.runtime.compute.ToBoolean;
import com.speedment.runtime.compute.ToBooleanNullable;

/**
 * Specific {@link FieldMapper} implementation that also implements
 * {@link ToBooleanNullable}.
 *
 * @param <ENTITY>  the entity type
 * @param <T>       the column type before mapped
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldToBoolean<ENTITY, T>
extends FieldMapper<ENTITY, T, Boolean, ToBoolean<ENTITY>, ToBooleanFunction<T>>,
        ToBooleanNullable<ENTITY> {}