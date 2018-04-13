package com.speedment.runtime.field.internal.expression;

import com.speedment.common.function.ToShortFunction;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldToShort;

/**
 * Default implementation of {@link FieldToShort}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToShortImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, ToShortFunction<V>>
implements FieldToShort<ENTITY, V> {

    public FieldToShortImpl(ReferenceField<ENTITY, ?, V> field,
                            ToShortFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Short apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsShort(value);
    }

    @Override
    public short applyAsShort(ENTITY object) throws NullPointerException {
        return mapper.applyAsShort(field.get(object));
    }
}