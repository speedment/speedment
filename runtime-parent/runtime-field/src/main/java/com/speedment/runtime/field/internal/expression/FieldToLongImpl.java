package com.speedment.runtime.field.internal.expression;

import com.speedment.runtime.compute.ToLong;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldToLong;

import java.util.function.ToLongFunction;

/**
 * Default implementation of {@link FieldToLong}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToLongImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, Long, ToLong<ENTITY>, ToLongFunction<V>>
implements FieldToLong<ENTITY, V> {

    public FieldToLongImpl(ReferenceField<ENTITY, ?, V> field,
                           ToLongFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Long apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsLong(value);
    }

    @Override
    public long applyAsLong(ENTITY object) throws NullPointerException {
        return mapper.applyAsLong(field.get(object));
    }
}