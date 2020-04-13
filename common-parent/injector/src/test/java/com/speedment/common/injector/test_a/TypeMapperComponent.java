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
package com.speedment.common.injector.test_a;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class TypeMapperComponent {
    
    private final Map<Class<?>, TypeMapper<?, ?>> toDatabase;
    private final Map<Class<?>, TypeMapper<?, ?>> toJava;

    public TypeMapperComponent() {
        this.toDatabase = new ConcurrentHashMap<>();
        this.toJava     = new ConcurrentHashMap<>();
    }
    
    public <DB_TYPE, JAVA_TYPE> void install(
            Class<DB_TYPE> databaseType, 
            Class<JAVA_TYPE> javaType, 
            TypeMapper<DB_TYPE, JAVA_TYPE> mapper) {
        
        toDatabase.put(databaseType, mapper);
        toJava.put(javaType, mapper);
    }
    
    public Map<Class<?>, TypeMapper<?, ?>> toDatabaseTypeMappers() {
        return toDatabase;
    }
    
    public Map<Class<?>, TypeMapper<?, ?>> toJavaTypeMappers() {
        return toJava;
    }
}