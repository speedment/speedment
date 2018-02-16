package com.speedment.runtime.join.trait;

import com.speedment.runtime.field.trait.HasComparableOperators;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type of entity
 */
public interface HasOn<ENTITY> {

    /**
     * Adds the given {@code originalField} to an operation where the
     * {@code originalField} belongs to a previous entered manager that are used
     * for joining elements.
     *
     * @param <V> type that the field can cold
     * @param <FIELD> type of the field
     * @param originalField
     * @return
     *
     * @throws NullPointerException if the provided {@code originalField } is
     * {@code null}
     * @throws IllegalArgumentException if the provided {@code originalField}
     * does not belong to a manager that is previously entered into the builder.
     */
    <V extends Comparable<? super V>, FIELD extends HasComparableOperators<ENTITY, V>> Object on(FIELD originalField);

}
