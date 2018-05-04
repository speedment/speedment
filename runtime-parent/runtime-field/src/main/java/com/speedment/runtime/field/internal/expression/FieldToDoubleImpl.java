package com.speedment.runtime.field.internal.expression;

import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldToDouble;

import java.util.function.ToDoubleFunction;

/**
 * Default implementation of {@link FieldToDouble}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToDoubleImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, Double, ToDouble<ENTITY>, ToDoubleFunction<V>>
implements FieldToDouble<ENTITY, V> {

    public FieldToDoubleImpl(ReferenceField<ENTITY, ?, V> field,
                             ToDoubleFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Double apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsDouble(value);
    }

    @Override
    public double applyAsDouble(ENTITY object) throws NullPointerException {
        return mapper.applyAsDouble(field.get(object));
    }
}