package com.speedment.runtime.core.internal.manager;

import com.speedment.runtime.core.manager.FieldSet;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.Field;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FieldSetImpl<ENTITY> implements FieldSet<ENTITY> {
    private final Set<String> includedIds;

    private FieldSetImpl(Stream<Field<ENTITY>> fieldStream) {
        includedIds = fieldStream.map(f -> f.identifier().getColumnId()).collect(Collectors.toSet());
    }

    public FieldSetImpl(Manager<ENTITY> manager) {
        this(manager.fields());
    }

    public FieldSetImpl(Field<ENTITY>[] fields) {
        this(Arrays.stream(fields));
    }

    @Override
    public boolean contains(String id) {
        return includedIds.contains(id);
    }

    @Override
    public FieldSet<ENTITY> except(Field<ENTITY> field) {
        includedIds.remove(field.identifier().getColumnId());
        return this;
    }
}
