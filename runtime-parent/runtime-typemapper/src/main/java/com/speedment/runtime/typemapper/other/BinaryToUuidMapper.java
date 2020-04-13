/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.typemapper.other;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.exception.SpeedmentTypeMapperException;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.UUID;

import static java.lang.String.format;

/**
 * Speedment {@link TypeMapper} that maps from a MySQL {@code BINARY} type to
 * a regular java {@code java.util.UUID}.
 *
 * @author Emil Forslund
 * @since  3.0.9
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
        if (binary == null) return null;

        final byte[] data;
        try {
            data = (byte[]) binary;
        } catch (final ClassCastException ex) {
            throw new SpeedmentTypeMapperException(format(
                "Expected database input to be a byte[] but was a %s.",
                binary.getClass()
            ), ex);
        }

        long msb = 0;
        long lsb = 0;

        if (data.length != 16) {
            throw new SpeedmentTypeMapperException(
                "Binary UUID is expected to be 16 bytes in length"
            );
        }

        for (int i = 0; i < 8; i++)
            msb = (msb << 8) | (data[i] & 0xff);
        for (int i = 8; i < 16; i++)
            lsb = (lsb << 8) | (data[i] & 0xff);

        return new UUID(msb, lsb);
    }

    @Override
    public byte[] toDatabaseType(UUID uuid) {
        return ByteBuffer.allocate(16)
            .putLong(uuid.getMostSignificantBits())
            .putLong(uuid.getLeastSignificantBits())
            .array();
    }
}