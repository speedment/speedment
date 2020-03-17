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
package com.speedment.runtime.core.internal.component.resultset;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.resultset.ResultSetMapping;

import java.util.function.Function;
import java.util.function.LongFunction;

import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T> the Java class to map
 * 
 * @author  Per Minborg
 * @since   2.0.0
 */
final class JavaTypeMappingImpl<T> implements ResultSetMapping<T> {

    private final Class<T> clazz;
    private final String resultSetMethodName;
    private final Function<String, ? extends T> stringMapper;
    private final LongFunction<? extends T> longMapper;

    JavaTypeMappingImpl(
            Class<T> clazz,
            String resultSetMethodName,
            Function<String, ? extends T> stringMapper,
            LongFunction<? extends T> longMapper) {
        
        this.clazz               = requireNonNull(clazz);
        this.resultSetMethodName = requireNonNull(resultSetMethodName);
        this.stringMapper        = requireNonNull(stringMapper);
        this.longMapper          = requireNonNull(longMapper);
    }
    
    @Override
    public Class<T> getJavaClass() {
        return clazz;
    }

    @Override
    public String getResultSetMethodName(Dbms dbms) {
        return resultSetMethodName;
    }

    @Override
    public T parse(String s) {
        return stringMapper.apply(s);
    }

    @Override
    public T parse(long l) {
        return longMapper.apply(l);
    }

    @Override
    public String toString() {
        return JavaTypeMappingImpl.class.getSimpleName() + 
            " {class=" + getJavaClass().getName() + 
            ", rsmName=" + resultSetMethodName + "}";
    }
}