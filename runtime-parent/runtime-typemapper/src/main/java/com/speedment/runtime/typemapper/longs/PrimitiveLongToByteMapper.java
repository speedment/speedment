package com.speedment.runtime.typemapper.longs;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import java.lang.reflect.Type;

/**
 * Maps between {@code Long} and {@code byte} values by simply casting to
 * lower precision.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class PrimitiveLongToByteMapper implements TypeMapper<Long, Byte> {

    @Override
    public String getLabel() {
        return "Long to byte (primitive)";
    }

    @Override
    public Type getJavaType(Column column) {
        return byte.class;
    }

    @Override
    public Byte toJavaType(Column column, Class<?> entityType, Long value) {
        return value == null ? null : (byte) (long) value;
    }

    @Override
    public Long toDatabaseType(Byte value) {
        return value == null ? null : (long) (byte) value;
    }

}