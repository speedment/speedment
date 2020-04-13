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
package com.speedment.runtime.typemapper.primitive;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;

/**
 * 
 * @param <T>  the database type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class PrimitiveTypeMapper<T> implements TypeMapper<T, T> {

    @Override
    public String getLabel() {
        return "(To Primitive)";
    }

    @Override
    public Type getJavaType(Column column) {
        final String type = column.getDatabaseType();
        switch (type) {
            case "java.lang.Byte"      : return byte.class;
            case "java.lang.Short"     : return short.class;
            case "java.lang.Integer"   : return int.class;
            case "java.lang.Long"      : return long.class;
            case "java.lang.Float"     : return float.class;
            case "java.lang.Double"    : return double.class;
            case "java.lang.Boolean"   : return boolean.class;
            case "java.lang.Character" : return char.class;
            default : throw new UnsupportedOperationException(
                "Type " + type + " is not a wrapper for a primitive type."
            );
        }
    }

    @Override
    public T toJavaType(Column column, Class<?> entityType, T value) {
        return value;
    }

    @Override
    public T toDatabaseType(T value) {
        return value;
    }

    @Override
    public Ordering getOrdering() {
        return Ordering.RETAIN;
    }
    
}
