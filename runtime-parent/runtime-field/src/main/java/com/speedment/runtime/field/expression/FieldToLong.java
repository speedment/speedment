package com.speedment.runtime.field.expression;

import com.speedment.runtime.compute.ToLongNullable;

import java.util.function.ToLongFunction;

/**
 * Specific {@link FieldMapper} implementation that also implements
 * {@link ToLongNullable}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldToLong<ENTITY, T>
extends FieldMapper<ENTITY, T, Long, ToLongFunction<T>>,
        ToLongNullable<ENTITY> {}