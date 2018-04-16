package com.speedment.runtime.field.internal.expression;

import com.speedment.common.function.ToBooleanFunction;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldToBoolean;

/**
 * Default implementation of {@link FieldToBoolean}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToBooleanImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, Boolean, ToBooleanFunction<V>>
implements FieldToBoolean<ENTITY, V> {

    public FieldToBooleanImpl(ReferenceField<ENTITY, ?, V> field,
                              ToBooleanFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Boolean apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsBoolean(value);
    }

    @Override
    public boolean applyAsBoolean(ENTITY object) throws NullPointerException {
        return mapper.applyAsBoolean(field.get(object));
    }
}