package com.speedment.runtime.typemapper.integer;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import java.lang.reflect.Type;

/**
 * Maps between {@code Integer} and {@code Short} values by simply casting to
 * lower precision.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class IntegerToShortMapper implements TypeMapper<Integer, Short> {

    @Override
    public String getLabel() {
        return "Integer to Short";
    }

    @Override
    public Type getJavaType(Column column) {
        return Short.class;
    }

    @Override
    public Short toJavaType(Column column, Class<?> entityType, Integer value) {
        return value == null ? null : ((short) (int) value);
    }

    @Override
    public Integer toDatabaseType(Short value) {
        return value == null ? null : ((int) (short) value);
    }

}