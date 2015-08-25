package com.speedment.core.field.reference;

import com.speedment.core.field.BaseFunction;
import com.speedment.core.field.Field;
import com.speedment.core.field.FunctionBuilder;

/**
 * @author Emil Forslund
 * @param <ENTITY>  the entity
 * @param <V>       the value type
 */
public class ReferenceFunctionBuilder<ENTITY, V> extends BaseFunction<ENTITY> implements FunctionBuilder<ENTITY> {

    private final ReferenceField<ENTITY, V> field;
    private final V newValue;

    public ReferenceFunctionBuilder(ReferenceField<ENTITY, V> field, V newValue) {
        this.field    = field;
        this.newValue = newValue;
    }

    @Override
    public ENTITY apply(ENTITY entity) {
        return field.setIn(entity, newValue);
    }

    @Override
    public Field<ENTITY> getField() {
        return field;
    }

    public V getValue() {
        return newValue;
    }
}