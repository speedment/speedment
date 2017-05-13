/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
