package com.speedment.runtime.typemapper.shorts;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import java.lang.reflect.Type;

/**
 * Maps between {@code Short} and {@code Byte} values by simply casting to
 * lower precision.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class ShortToByteMapper implements TypeMapper<Short, Byte> {

    @Override
    public String getLabel() {
        return "Short to Byte";
    }

    @Override
    public Type getJavaType(Column column) {
        return Byte.class;
    }

    @Override
    public Byte toJavaType(Column column, Class<?> entityType, Short value) {
        return value == null ? null : ((byte) (short) value);
    }

    @Override
    public Short toDatabaseType(Byte value) {
        return value == null ? null : ((short) (byte) value);
    }

}