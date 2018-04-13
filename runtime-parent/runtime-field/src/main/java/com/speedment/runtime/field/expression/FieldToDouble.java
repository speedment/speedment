package com.speedment.runtime.field.expression;

import com.speedment.runtime.compute.ToDoubleNullable;

import java.util.function.ToDoubleFunction;

/**
 * Specific {@link FieldMapper} implementation that also implements
 * {@link ToDoubleNullable}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldToDouble<ENTITY, T>
extends FieldMapper<ENTITY, T, ToDoubleFunction<T>>,
        ToDoubleNullable<ENTITY> {}