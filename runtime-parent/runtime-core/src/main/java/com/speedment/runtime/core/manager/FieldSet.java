package com.speedment.runtime.core.manager;

import com.speedment.runtime.core.internal.manager.FieldSetImpl;
import com.speedment.runtime.field.Field;

import java.util.Arrays;

public interface FieldSet<ENTITY> extends HasLabelSet<ENTITY> {
    FieldSet<ENTITY> except(Field<ENTITY> field);

    @SafeVarargs
    static <ENTITY> FieldSet<ENTITY> of(Field<ENTITY>... fields) {
        return new FieldSetImpl<>(Arrays.stream(fields));
    }

    static <ENTITY> FieldSet<ENTITY> fieldsOf(Manager<ENTITY> manager) {
        return new FieldSetImpl<>(manager.fields());
    }
}
