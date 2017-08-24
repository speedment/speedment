package com.speedment.runtime.typemapper.other;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.exception.SpeedmentTypeMapperException;

import java.lang.reflect.Type;

import static java.lang.String.format;

/**
 * Converts between a database type that is known to be a {@code byte[]} masked
 * as an {@code java.lang.Object} and a properly typed {@code byte[]}.
 *
 * @author Emil Forslund
 * @since  3.0.13
 */
public final class BinaryToByteArrayMapper implements TypeMapper<Object, byte[]> {

    @Override
    public String getLabel() {
        return "BINARY to byte[] Mapper";
    }

    @Override
    public Type getJavaType(Column column) {
        return byte[].class;
    }

    @Override
    public Category getJavaTypeCategory(Column column) {
        return Category.REFERENCE;
    }

    @Override
    public byte[] toJavaType(Column column, Class<?> entityType, Object binary) {
        if (binary == null) return null;

        try {
            return (byte[]) binary;
        } catch (final ClassCastException ex) {
            throw new SpeedmentTypeMapperException(format(
                "Expected database input to be a byte[] but was a %s.",
                binary.getClass()
            ), ex);
        }
    }

    @Override
    public Object toDatabaseType(byte[] array) {
        return array;
    }
}