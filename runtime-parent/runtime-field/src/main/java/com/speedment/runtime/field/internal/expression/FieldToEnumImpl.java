package com.speedment.runtime.field.internal.expression;

import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldToEnum;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link FieldToEnum}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToEnumImpl<ENTITY, V, E extends Enum<E>>
extends AbstractFieldMapper<ENTITY, V, E, Function<V, E>>
implements FieldToEnum<ENTITY, V, E> {

    private final Class<E> enumClass;

    public FieldToEnumImpl(ReferenceField<ENTITY, ?, V> field,
                           Function<V, E> mapper,
                           Class<E> enumClass) {
        super(field, mapper);
        this.enumClass = requireNonNull(enumClass);
    }

    @Override
    public Class<E> enumClass() {
        return enumClass;
    }

    @Override
    public E apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.apply(value);
    }
}