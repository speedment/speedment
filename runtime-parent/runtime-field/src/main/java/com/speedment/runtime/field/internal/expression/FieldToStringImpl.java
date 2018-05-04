package com.speedment.runtime.field.internal.expression;

import com.speedment.runtime.compute.ToString;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldToString;

import java.util.function.Function;

/**
 * Default implementation of {@link FieldToString}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToStringImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, String, ToString<ENTITY>, Function<V, String>>
implements FieldToString<ENTITY, V> {

    public FieldToStringImpl(ReferenceField<ENTITY, ?, V> field,
                             Function<V, String> mapper) {
        super(field, mapper);
    }

    @Override
    public String apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.apply(value);
    }
}