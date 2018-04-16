package com.speedment.runtime.field.internal.expression;

import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldToInt;

import java.util.function.ToIntFunction;

/**
 * Default implementation of {@link FieldToInt}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToIntImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, Integer, ToIntFunction<V>>
implements FieldToInt<ENTITY, V> {

    public FieldToIntImpl(ReferenceField<ENTITY, ?, V> field,
                          ToIntFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Integer apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsInt(value);
    }

    @Override
    public int applyAsInt(ENTITY object) throws NullPointerException {
        return mapper.applyAsInt(field.get(object));
    }
}