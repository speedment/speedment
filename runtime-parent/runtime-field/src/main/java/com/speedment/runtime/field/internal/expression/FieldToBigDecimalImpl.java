package com.speedment.runtime.field.internal.expression;

import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldToBigDecimal;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Default implementation of {@link FieldToBigDecimal}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToBigDecimalImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, BigDecimal, Function<V, BigDecimal>>
implements FieldToBigDecimal<ENTITY, V> {

    public FieldToBigDecimalImpl(ReferenceField<ENTITY, ?, V> field,
                                 Function<V, BigDecimal> mapper) {
        super(field, mapper);
    }

    @Override
    public BigDecimal apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.apply(value);
    }
}