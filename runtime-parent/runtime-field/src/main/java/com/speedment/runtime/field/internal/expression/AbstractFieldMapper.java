package com.speedment.runtime.field.internal.expression;

import com.speedment.runtime.compute.trait.ToNullable;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldMapper;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * Abstract base implementation of {@link FieldMapper}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
abstract class AbstractFieldMapper<ENTITY, V, T, MAPPER>
implements FieldMapper<ENTITY, V, T, MAPPER>,
           ToNullable<ENTITY, T> {

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

    @Override
    public Predicate<ENTITY> isNull() {
        return field.isNull();
    }

    @Override
    public Predicate<ENTITY> isNotNull() {
        return field.isNotNull();
    }
}
