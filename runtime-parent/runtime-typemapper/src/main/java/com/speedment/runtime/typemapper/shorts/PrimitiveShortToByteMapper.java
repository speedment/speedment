package com.speedment.runtime.typemapper.shorts;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import java.lang.reflect.Type;

/**
 * Maps between {@code Short} and {@code byte} values by simply casting to
 * lower precision.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class PrimitiveShortToByteMapper implements TypeMapper<Short, Byte> {

    @Override
    public String getLabel() {
        return "Short to byte (primitive)";
    }

    @Override
    public Type getJavaType(Column column) {
        return byte.class;
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