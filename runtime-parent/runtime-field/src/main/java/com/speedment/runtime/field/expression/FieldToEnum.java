package com.speedment.runtime.field.expression;

import com.speedment.runtime.compute.ToEnumNullable;
import com.speedment.runtime.compute.ToStringNullable;

import java.util.function.Function;

/**
 * Specific {@link FieldMapper} implementation that also implements
 * {@link ToStringNullable}.
 *
 * @param <ENTITY>  the entity type
 * @param <T>       the column type before mapped
 * @param <E>       the enum type mapped to
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldToEnum<ENTITY, T, E extends Enum<E>>
extends FieldMapper<ENTITY, T, E, Function<T, E>>,
        ToEnumNullable<ENTITY, E> {}