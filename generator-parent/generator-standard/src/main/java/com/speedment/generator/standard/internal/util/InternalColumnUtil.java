package com.speedment.generator.standard.internal.util;

import com.speedment.generator.translator.component.TypeMapperComponent;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.trait.HasNullable;

import java.lang.reflect.Type;
import java.util.Optional;

public final class InternalColumnUtil {

    private InternalColumnUtil() {}

    public static boolean usesOptional(Column col) {
        return col.isNullable()
            && HasNullable.ImplementAs.OPTIONAL
            == col.getNullableImplementation();
    }

    public static Optional<String> optionalGetterName(TypeMapperComponent typeMappers, Column column) {
        final Type colType = typeMappers.get(column).getJavaType(column);
        final String getterName;

        if (usesOptional(column)) {
            if (Double.class.getName().equals(colType.getTypeName())) {
                getterName = ".getAsDouble()";
            } else if (Integer.class.getName().equals(colType.getTypeName())) {
                getterName = ".getAsInt()";
            } else if (Long.class.getName().equals(colType.getTypeName())) {
                getterName = ".getAsLong()";
            } else {
                getterName = ".get()";
            }
        } else {
            return Optional.empty();
        }

        return Optional.of(getterName);
    }


}
