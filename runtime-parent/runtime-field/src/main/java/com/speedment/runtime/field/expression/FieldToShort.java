package com.speedment.runtime.field.expression;

import com.speedment.common.function.ToShortFunction;
import com.speedment.runtime.compute.ToShortNullable;

/**
 * Specific {@link FieldMapper} implementation that also implements
 * {@link ToShortNullable}.
 *
 * @param <ENTITY>  the entity type
 * @param <T>       the column type before mapped
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldToShort<ENTITY, T>
extends FieldMapper<ENTITY, T, Short, ToShortFunction<T>>,
        ToShortNullable<ENTITY> {}