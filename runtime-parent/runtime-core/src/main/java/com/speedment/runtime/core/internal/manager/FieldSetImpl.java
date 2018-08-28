package com.speedment.runtime.core.internal.manager;

import com.speedment.runtime.core.manager.FieldSet;
import com.speedment.runtime.field.Field;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FieldSetImpl<ENTITY> implements FieldSet<ENTITY> {
    public static final FieldSetImpl ALL = new FieldSetImpl<>($ -> true);
    public static final FieldSetImpl NONE = new FieldSetImpl<>($ -> false);

    private final Predicate<String> includedId;

    public FieldSetImpl(Predicate<String> includedId) {
        this.includedId = includedId;
    }

    public FieldSetImpl(Stream<Field<ENTITY>> fieldStream) {
        Set<String> ids = fieldStream.map(f -> f.identifier().getColumnId()).collect(Collectors.toSet());
        switch (ids.size()) {
            case 0:
                includedId = $ -> false;
                break;
            case 1:
                final String id = ids.iterator().next();
                includedId = id::equals;
                break;
            default:
                includedId = ids::contains;
        }
    }

    @Override
    public boolean test(String id) {
        return includedId.test(id);
    }

    public FieldSetImpl<ENTITY> negate() {
        return new FieldSetImpl<>(includedId.negate());
    }

    @Override
    public FieldSet<ENTITY> except(Field<ENTITY> field) {
        String id = field.identifier().getColumnId();
        return new FieldSetImpl<>(s -> !id.equals(s) && test(s));
    }

    @Override
    public FieldSet<ENTITY> and(Field<ENTITY> field) {
        String id = field.identifier().getColumnId();
        return new FieldSetImpl<>(s -> id.equals(s) && test(s));
    }
}
