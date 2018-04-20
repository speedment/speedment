package com.speedment.runtime.field.expression;

import com.speedment.common.function.ToFloatFunction;
import com.speedment.runtime.compute.ToFloatNullable;

/**
 * Specific {@link FieldMapper} implementation that also implements
 * {@link ToFloatNullable}.
 *
 * @param <ENTITY>  the entity type
 * @param <T>       the column type before mapped
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldToFloat<ENTITY, T>
extends FieldMapper<ENTITY, T, Float, ToFloatFunction<T>>,
        ToFloatNullable<ENTITY> {}