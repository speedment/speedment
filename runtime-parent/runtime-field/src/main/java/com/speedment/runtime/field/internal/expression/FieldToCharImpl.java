package com.speedment.runtime.field.internal.expression;

import com.speedment.common.function.ToCharFunction;
import com.speedment.runtime.compute.ToChar;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldToChar;

/**
 * Default implementation of {@link FieldToChar}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToCharImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, Character, ToChar<ENTITY>, ToCharFunction<V>>
implements FieldToChar<ENTITY, V> {

    public FieldToCharImpl(ReferenceField<ENTITY, ?, V> field,
                           ToCharFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Character apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsChar(value);
    }

    @Override
    public char applyAsChar(ENTITY object) throws NullPointerException {
        return mapper.applyAsChar(field.get(object));
    }
}