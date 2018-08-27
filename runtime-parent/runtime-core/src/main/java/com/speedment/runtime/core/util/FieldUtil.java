package com.speedment.runtime.core.util;

import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.Field;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.speedment.runtime.core.util.StaticClassUtil.instanceNotAllowed;

public final class FieldUtil {
    public static <ENTITY> Collection<Field<ENTITY>> allOf(Manager<ENTITY> manager) {
        return manager.fields().collect(Collectors.toList());
    }

    @SafeVarargs
    public static <ENTITY> Collection<Field<ENTITY>> include(Field<ENTITY>... fields) {
        return Arrays.asList(fields);
    }

    private FieldUtil() {
        instanceNotAllowed(getClass());
    }
}
