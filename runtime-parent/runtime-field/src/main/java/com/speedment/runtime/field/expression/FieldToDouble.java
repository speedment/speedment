package com.speedment.runtime.field.expression;

import com.speedment.runtime.compute.ToDoubleNullable;

import java.util.function.ToDoubleFunction;

/**
 * Specific {@link FieldMapper} implementation that also implements
 * {@link ToDoubleNullable}.
 *
 * @param <ENTITY>  the entity type
 * @param <T>       the column type before mapped
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldToDouble<ENTITY, T>
extends FieldMapper<ENTITY, T, Double, ToDoubleFunction<T>>,
        ToDoubleNullable<ENTITY> {}