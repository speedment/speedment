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
package com.speedment.internal.core.runtime.typemapping;

import com.speedment.config.Dbms;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;

/**
 *
 * @author pemi
 * @param <T> the Java Class to map
 */
public final class JavaTypeMappingImpl<T> implements JavaTypeMapping<T> {

    private final Class<T> clazz;
    private final String resultSetMethodName;
    private final Function<String, T> stringMapper;
    private final Function<Long, T> longMapper;

    public JavaTypeMappingImpl(Class<T> clazz, String resultSetMethodName, Function<String, T> stringMapper, Function<Long, T> longMapper) {
        this.clazz = requireNonNull(clazz);
        this.resultSetMethodName = requireNonNull(resultSetMethodName);
        this.stringMapper = requireNonNull(stringMapper);
        this.longMapper = requireNonNull(longMapper);
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
        return JavaTypeMappingImpl.class.getSimpleName() + " {class=" + getJavaClass().getName() + ", rsmName=" + resultSetMethodName + "}";
    }

}
