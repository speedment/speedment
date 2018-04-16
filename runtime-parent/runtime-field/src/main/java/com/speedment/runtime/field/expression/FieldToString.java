package com.speedment.runtime.field.expression;

import com.speedment.runtime.compute.ToStringNullable;

import java.util.function.Function;

/**
 * Specific {@link FieldMapper} implementation that also implements
 * {@link ToStringNullable}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldToString<ENTITY, T>
extends FieldMapper<ENTITY, T, String, Function<T, String>>,
        ToStringNullable<ENTITY> {}