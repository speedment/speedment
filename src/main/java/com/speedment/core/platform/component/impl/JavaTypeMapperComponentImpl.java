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
package com.speedment.core.platform.component.impl;

import com.speedment.core.config.model.parameters.DbmsType;
import com.speedment.core.platform.component.JavaTypeMapperComponent;
import com.speedment.core.runtime.typemapping.JavaTypeMapping;
import com.speedment.core.runtime.typemapping.StandardJavaTypeMapping;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class JavaTypeMapperComponentImpl implements JavaTypeMapperComponent {

    private final Map<Class<?>, JavaTypeMapping> map;
    private final Map<DbmsType, Map<Class<?>, JavaTypeMapping>> dbmsTypeMap;

    public JavaTypeMapperComponentImpl() {
        map = new ConcurrentHashMap<>();
        dbmsTypeMap = new ConcurrentHashMap<>();
        Stream.of(StandardJavaTypeMapping.values()).forEach(m -> {
            put(m);
        });
    }

    public final JavaTypeMapping put(JavaTypeMapping item) {
        return map.put(item.getJavaClass(), item);
    }

    public final JavaTypeMapping put(DbmsType dbmsType, JavaTypeMapping item) {
        return dbmsTypeMap.computeIfAbsent(dbmsType, k -> new ConcurrentHashMap<>()).put(item.getJavaClass(), item);
    }

    /**
     * Gets the mapping from the javaClass to the JavaTypeMapping. If a specific
     * mapping for the given DbmsType is present, that mapping is selected over
     * the general mapping for any DbmsType.
     *
     * @param dbmsType the Dbms type
     * @param javaClass the java class to map
     * @return the mapping
     */
    @Override
    public JavaTypeMapping apply(DbmsType dbmsType, Class<?> javaClass) {
        return Optional.ofNullable(
            dbmsTypeMap.getOrDefault(dbmsType, map).get(javaClass))
            .orElseThrow(() -> new NullPointerException(
                "The " + JavaTypeMapperComponent.class.getSimpleName() + 
                " does not have a mapping for " + dbmsType + ", " + javaClass
            ));
    }
}