package com.speedment.runtime.field.expression;

import com.speedment.common.function.ToCharFunction;
import com.speedment.runtime.compute.ToCharNullable;

/**
 * Specific {@link FieldMapper} implementation that also implements
 * {@link ToCharNullable}.
 *
 * @param <ENTITY>  the entity type
 * @param <T>       the column type before mapped
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldToChar<ENTITY, T>
extends FieldMapper<ENTITY, T, Character, ToCharFunction<T>>,
        ToCharNullable<ENTITY> {}