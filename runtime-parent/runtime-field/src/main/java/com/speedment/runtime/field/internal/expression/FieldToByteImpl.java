package com.speedment.runtime.field.internal.expression;

import com.speedment.common.function.ToByteFunction;
import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldToByte;

/**
 * Default implementation of {@link FieldToByte}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToByteImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, Byte, ToByte<ENTITY>, ToByteFunction<V>>
implements FieldToByte<ENTITY, V> {

    public FieldToByteImpl(ReferenceField<ENTITY, ?, V> field,
                           ToByteFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Byte apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsByte(value);
    }

    @Override
    public byte applyAsByte(ENTITY object) throws NullPointerException {
        return mapper.applyAsByte(field.get(object));
    }
}