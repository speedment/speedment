package com.speedment.runtime.core.util;

import com.speedment.runtime.core.HasLabelSet;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.Field;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.speedment.runtime.core.util.StaticClassUtil.instanceNotAllowed;

public final class FieldUtil {
    public static <ENTITY> HasLabelSet<ENTITY> allOf(Manager<ENTITY> manager) {
        Set<String> ids = manager.fields().map(f -> f.identifier().getColumnId()).collect(Collectors.toSet());
        return ids::contains;
    }

    @SafeVarargs
    public static <ENTITY> HasLabelSet<ENTITY> include(Field<ENTITY>... fields) {
        final List<Field<ENTITY>> filedList = Arrays.asList(fields);
        Set<String> ids = filedList.stream().map(f -> f.identifier().getColumnId()).collect(Collectors.toSet());
        return ids::contains;
    }

    private FieldUtil() {
        instanceNotAllowed(getClass());
    }
}
