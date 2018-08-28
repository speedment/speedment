package com.speedment.runtime.core.manager;

import com.speedment.runtime.core.internal.manager.FieldSetImpl;
import com.speedment.runtime.field.Field;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public interface FieldSet<ENTITY> extends HasLabelSet<ENTITY> {

    /**
     * Creates a new FieldSet with the given field removed from the original set.
     *
     * @param field the field to remove
     * @return a new FieldSet with the given field removed
     */
    FieldSet<ENTITY> except(Field<ENTITY> field);

    /**
     * Creates a new FieldSet with the given field added to the original set.
     *
     * @param field the field to add
     * @return a new FieldSet with an added field
     */
    FieldSet<ENTITY> and(Field<ENTITY> field);


    /**
     * Creates a new FieldSet that represents the set of all fields of an entity except for the given ones
     *
     * @param fields the fields to exclude from the set
     * @param <ENTITY> the type of entity
     * @return a new FieldSet that represents the set of all fields of an entity except for the given ones
     */
    @SafeVarargs
    static <ENTITY> FieldSet<ENTITY> allExcept(Field<ENTITY>... fields) {
        return new FieldSetImpl<>(Arrays.stream(fields)).negate();
    }

    /**
     * Creates a new FieldSet that represents the given set of fields
     *
     * @param fields the fields to include in the set
     * @param <ENTITY> the type of entity
     * @return a new FieldSet that represents the set of the given fields
     */
    @SafeVarargs
    static <ENTITY> FieldSet<ENTITY> of(Field<ENTITY>... fields) {
        return new FieldSetImpl<>(Arrays.stream(fields));
    }

    /**
     * Creates a new FieldSet that represents the set of all fields of an entity except for the given ones
     *
     * @param fields the fields to exclude from the set
     * @param <ENTITY> the type of entity
     * @return a new FieldSet that represents the set of all fields of an entity except for the given ones
     */
    static <ENTITY> FieldSet<ENTITY> allExcept(Collection<Field<ENTITY>> fields) {
        return new FieldSetImpl<>(fields.stream()).negate();
    }

    /**
     * Creates a new FieldSet that represents the given set of fields
     *
     * @param fields the fields to include in the set
     * @param <ENTITY> the type of entity
     * @return a new FieldSet that represents the set of the given fields
     */
    static <ENTITY> FieldSet<ENTITY> of(Collection<Field<ENTITY>> fields) {
        return new FieldSetImpl<>(fields.stream());
    }

    /**
     * Creates a new FieldSet that represents the fields with column IDs for which the given predicate holds true
     *
     * @param columnIds A predicate that holds true for the column IDs of the fields of the set
     * @param <ENTITY> the type of entity
     * @return a new FieldSet that represents the fields with column IDs for which the given predicate holds true
     */
    static <ENTITY> FieldSet<ENTITY> of(Predicate<String> columnIds) {
        return new FieldSetImpl<>(columnIds);
    }

    /**
     * Returns a FieldSet that represents all the fields of the given entity
     * @param classToken manager class token used to determine entity type
     * @param <ENTITY> the type of entity
     * @return a FieldSet that represents all the fields of the given entity
     */
    @SuppressWarnings("unchecked")
    static <ENTITY> FieldSet<ENTITY> allOf(Manager<ENTITY> classToken) {
        return (FieldSet<ENTITY>) FieldSetImpl.ALL;
    }

    /**
     * Returns a FieldSet that represents the empty set of fields of the given entity
     * @param classToken manager class token used to determine entity type
     * @param <ENTITY> the type of entity
     * @return a FieldSet that represents the empty set of fields of the given entity
     */
    @SuppressWarnings("unchecked")
    static <ENTITY> FieldSet<ENTITY> noneOf(Manager<ENTITY> classToken) {
        return (FieldSet<ENTITY>) FieldSetImpl.NONE;
    }

}
