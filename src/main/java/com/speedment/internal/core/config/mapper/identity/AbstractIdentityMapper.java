/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.config.mapper.identity;

import com.speedment.config.mapper.TypeMapper;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @param <T> type
 */
public abstract class AbstractIdentityMapper<T> implements TypeMapper<T, T> {
    
    private final Class<T> type;
    
    protected AbstractIdentityMapper(Class<T> type) {
        this.type = requireNonNull(type);
    }

    @Override
    public final Class<T> getJavaType() {
        return type;
    }

    @Override
    public final Class<T> getDatabaseType() {
        return type;
    }

    @Override
    public final T toJavaType(T value) {
        return value;
    }

    @Override
    public final T toDatabaseType(T value) {
        return value;
    }
}