package com.speedment.runtime.field.internal.mapper;

import com.speedment.common.function.ToByteFunction;
import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.NullableFieldToByte;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class NullableFieldToByteImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, ToByteFunction<V>>
implements NullableFieldToByte<ENTITY, V> {

    public NullableFieldToByteImpl(ReferenceField<ENTITY, ?, V> field,
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

    @Override
    public ToByte<ENTITY> orThrow() throws NullPointerException {
        return new FieldToByteImpl<>(field, mapper);
    }

    @Override
    public ToByte<ENTITY> orElseGet(ToByte<ENTITY> getter) {
        return entity -> {
            final V value = field.get(entity);
            return value == null
                ? getter.applyAsByte(entity)
                : mapper.applyAsByte(value);
        };
    }

    @Override
    public ToByte<ENTITY> orElse(byte other) {
        return entity -> {
            final V value = field.get(entity);
            return value == null ? other
                : mapper.applyAsByte(value);
        };
    }
}
