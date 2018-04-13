package com.speedment.runtime.field.internal.expression;

import com.speedment.common.function.ToFloatFunction;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldToFloat;

/**
 * Default implementation of {@link FieldToFloat}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToFloatImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, ToFloatFunction<V>>
implements FieldToFloat<ENTITY, V> {

    public FieldToFloatImpl(ReferenceField<ENTITY, ?, V> field,
                            ToFloatFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Float apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsFloat(value);
    }

    @Override
    public float applyAsFloat(ENTITY object) throws NullPointerException {
        return mapper.applyAsFloat(field.get(object));
    }
}