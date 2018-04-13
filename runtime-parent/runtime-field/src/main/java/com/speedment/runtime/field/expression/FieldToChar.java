package com.speedment.runtime.field.expression;

import com.speedment.common.function.ToCharFunction;
import com.speedment.runtime.compute.ToCharNullable;

/**
 * Specific {@link FieldMapper} implementation that also implements
 * {@link ToCharNullable}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldToChar<ENTITY, T>
extends FieldMapper<ENTITY, T, ToCharFunction<T>>,
        ToCharNullable<ENTITY> {}