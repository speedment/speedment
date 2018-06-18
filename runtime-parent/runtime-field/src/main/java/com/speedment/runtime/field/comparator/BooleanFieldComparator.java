package com.speedment.runtime.field.comparator;

import com.speedment.runtime.field.BooleanField;
import com.speedment.runtime.field.trait.HasBooleanValue;

/**
 * A {@link FieldComparator} that compares values of a {@link BooleanField}.
 *
 * @param <ENTITY> entity type
 * @param <D>      database type
 *
 * @author Emil Forslund
 * @since  3.1.4
 */
public interface BooleanFieldComparator<ENTITY, D> extends FieldComparator<ENTITY> {

    /**
     * Gets the field that is being compared.
     *
     * @return the compared field
     */
    @Override
    HasBooleanValue<ENTITY, D> getField();

    @Override
    BooleanFieldComparator<ENTITY, D> reversed();
}
