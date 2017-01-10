package com.speedment.runtime.typemapper.integer;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import java.lang.reflect.Type;

/**
 * Maps between {@code Integer} and {@code Byte} values by simply casting to
 * lower precision.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class IntegerToByteMapper implements TypeMapper<Integer, Byte> {

    @Override
    public String getLabel() {
        return "Integer to Byte";
    }

    @Override
    public Type getJavaType(Column column) {
        return Byte.class;
    }

    @Override
    public Byte toJavaType(Column column, Class<?> entityType, Integer value) {
        return value == null ? null : ((byte) (int) value);
    }

    @Override
    public Integer toDatabaseType(Byte value) {
        return value == null ? null : ((int) (byte) value);
    }

}