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
package com.speedment.runtime.typemapper.bytes;


import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;

/**
 *
 * @author  Roberts Vartins
 * @since   2.3.5
 */

public final class ByteZeroOneToBooleanMapper implements TypeMapper<Byte, Boolean> {

    @Override
    public String getLabel() {
        return "Byte (0|1) to Boolean";
    }
    
    @Override
    public Type getJavaType(Column column) {
        if (column.isNullable()) {
            return Boolean.class;
        } else {
            return boolean.class;
        }
    }

    @Override
    public Boolean toJavaType(Column column, Class<?> entityType, Byte value) {
        return value == null ? null : value != 0;
    }

    @Override
    public Byte toDatabaseType(Boolean value) {
        if (value == null) {
            return null;
        } else {
            return value ? (byte) 1 : (byte) 0;
        }
    }
}