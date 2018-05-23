package com.speedment.runtime.field.internal.predicate.string;

import com.speedment.runtime.compute.trait.ToNullable;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.field.predicate.FieldIsNotNullPredicate;
import com.speedment.runtime.field.predicate.FieldIsNullPredicate;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.2
 */
public final class StringIsNullPredicate<ENTITY, D>
implements FieldIsNullPredicate<ENTITY, String> {

    private final StringField<ENTITY, D> field;

    public StringIsNullPredicate(StringField<ENTITY, D> field) {
        this.field = requireNonNull(field);
    }

    @Override
    public boolean test(ENTITY value) {
        return field.apply(value) == null;
    }

    @Override
    public FieldIsNotNullPredicate<ENTITY, String> negate() {
        return new StringIsNotNullPredicate<>(field);
    }

    @Override
    public ToNullable<ENTITY, String, ?> expression() {
        return field;
    }

    @Override
    public Field<ENTITY> getField() {
        return field;
    }
}
