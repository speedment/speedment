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
package com.speedment.runtime.typemapper.internal;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;

/**
 * A special implementation of {@link TypeMapper} that simply returns the 
 * same object that it received.
 * <p>
 * 
 * @param <T>  the type
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public final class IdentityTypeMapper<T> implements TypeMapper<T, T> {

    private static final IdentityTypeMapper<?> SHARED_INSTANCE = new IdentityTypeMapper<>();

    @Override
    public String getLabel() {
        return "Identity Mapper";
    }

    @Override
    public Type getJavaType(Column column) {
        return column.findDatabaseType();
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

    @Override
    public boolean isIdentity() {
        return true;
    }

    @SuppressWarnings("unchecked")
    public static <T> TypeMapper<T, T> shared() {
        return (TypeMapper<T, T>) SHARED_INSTANCE;
    }

}