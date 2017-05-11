package com.speedment.runtime.typemapper.other;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Speedment {@link TypeMapper} that maps from a MySQL {@code BINARY} type to
 * a regular java {@code java.util.UUID}.
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class BinaryToUuidMapper implements TypeMapper<Object, UUID> {

    @Override
    public String getLabel() {
        return "BINARY to UUID Mapper";
    }

    @Override
    public Type getJavaType(Column column) {
        return UUID.class;
    }

    @Override
    public UUID toJavaType(Column column, Class<?> aClass, Object binary) {
        return binary == null ? null : UUID.nameUUIDFromBytes((byte[]) binary);
    }

    @Override
    public byte[] toDatabaseType(UUID uuid) {
        return ByteBuffer.allocate(16)
            .putLong(uuid.getMostSignificantBits())
            .putLong(uuid.getLeastSignificantBits())
            .array();
    }
}
