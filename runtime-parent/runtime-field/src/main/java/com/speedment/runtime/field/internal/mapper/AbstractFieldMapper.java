package com.speedment.runtime.field.internal.mapper;

import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldMapper;

import static java.util.Objects.requireNonNull;

/**
 * Abstract base implementation of {@link FieldMapper}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
abstract class AbstractFieldMapper<ENTITY, V, MAPPER>
implements FieldMapper<ENTITY, V, MAPPER> {

    final ReferenceField<ENTITY, ?, V> field;
    final MAPPER mapper;

    AbstractFieldMapper(ReferenceField<ENTITY, ?, V> field, MAPPER mapper) {
        this.field  = requireNonNull(field);
        this.mapper = requireNonNull(mapper);
    }

    @Override
    public ReferenceField<ENTITY, ?, V> getField() {
        return field;
    }

    @Override
    public MAPPER getMapper() {
        return mapper;
    }
}
