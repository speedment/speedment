package com.speedment.runtime.core.manager;

import com.speedment.runtime.core.internal.manager.FieldSetImpl;
import com.speedment.runtime.field.Field;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public interface FieldSet<ENTITY> extends HasLabelSet<ENTITY> {
    FieldSet<ENTITY> except(Field<ENTITY> field);
    FieldSet<ENTITY> and(Field<ENTITY> field);

    @SafeVarargs
    static <ENTITY> FieldSet<ENTITY> allExcept(Field<ENTITY>... fields) {
        return new FieldSetImpl<>(Arrays.stream(fields)).negate();
    }

    @SafeVarargs
    static <ENTITY> FieldSet<ENTITY> of(Field<ENTITY>... fields) {
        return new FieldSetImpl<>(Arrays.stream(fields));
    }

    static <ENTITY> FieldSet<ENTITY> allExcept(Collection<Field<ENTITY>> fields) {
        return new FieldSetImpl<>(fields.stream()).negate();
    }

    static <ENTITY> FieldSet<ENTITY> of(Collection<Field<ENTITY>> fields) {
        return new FieldSetImpl<>(fields.stream());
    }

    static <ENTITY> FieldSet<ENTITY> of(Predicate<String> columnIds) {
        return new FieldSetImpl<>(columnIds);
    }

    static <ENTITY> FieldSet<ENTITY> allOf(Manager<ENTITY> classToken) {
        return new FieldSetImpl<>($ -> true);
    }

    static <ENTITY> FieldSet<ENTITY> noneOf(Manager<ENTITY> classToken) {
        return new FieldSetImpl<>($ -> false);
    }

}
