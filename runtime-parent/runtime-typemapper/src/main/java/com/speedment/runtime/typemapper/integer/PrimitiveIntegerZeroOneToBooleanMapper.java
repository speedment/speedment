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
package com.speedment.runtime.typemapper.integer;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import java.lang.reflect.Type;

/**
 *
 * @author Roberts Vartins
 * @since 2.3.5
 */
public final class PrimitiveIntegerZeroOneToBooleanMapper
    implements TypeMapper<Integer, Boolean> {

    @Override
    public String getLabel() {
        return "Integer (0|1) to boolean (primitive)";
    }

    @Override
    public Type getJavaType(Column column) {
        return boolean.class;
    }

    @Override
    public Boolean toJavaType(Column column, Class<?> entityType, Integer value) {
        return value == null ? null : value != 0;
    }

    @Override
    public Integer toDatabaseType(Boolean value) {
        if (value == null) {
            return null;
        } else {
            return value ? 1 : 0;
        }
    }

    @Override
    public Ordering getOrdering() {
        return Ordering.RETAIN;
    }

}
