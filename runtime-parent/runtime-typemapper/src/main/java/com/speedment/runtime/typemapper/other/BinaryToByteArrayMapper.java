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