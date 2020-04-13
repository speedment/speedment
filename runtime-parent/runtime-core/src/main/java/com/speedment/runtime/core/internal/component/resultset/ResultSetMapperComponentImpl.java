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

import com.speedment.runtime.core.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.core.component.resultset.ResultSetMapping;
import com.speedment.runtime.core.db.DbmsType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;

public final class ResultSetMapperComponentImpl implements ResultSetMapperComponent {

    private final Map<Class<?>, ResultSetMapping<?>> map;
    private final Map<DbmsType, Map<Class<?>, ResultSetMapping<?>>> dbmsTypeMap;

    public ResultSetMapperComponentImpl() {
        map         = newConcurrentMap();
        dbmsTypeMap = newConcurrentMap();
        StandardJavaTypeMapping.stream().forEach(this::put);
    }

    public ResultSetMapping<?> put(ResultSetMapping<?> item) {
        requireNonNull(item);
        return map.put(item.getJavaClass(), item);
    }

    public ResultSetMapping<?> put(DbmsType dbmsType, ResultSetMapping<?> item) {
        requireNonNulls(dbmsType, item);
        return dbmsTypeMap.computeIfAbsent(dbmsType, k -> new ConcurrentHashMap<>()).put(item.getJavaClass(), item);
    }

    @Override
    public <T> ResultSetMapping<T> apply(DbmsType dbmsType, Class<T> javaClass) {
        requireNonNulls(dbmsType, javaClass);
        return getFromMapOrThrow(dbmsTypeMap.getOrDefault(dbmsType, map), javaClass, () -> dbmsType + ", " + javaClass.getName());
    }

    @Override
    public <T> ResultSetMapping<T> apply(Class<T> javaClass) {
        requireNonNull(javaClass);
        return getFromMapOrThrow(map, javaClass, javaClass::getName);
    }

    @SuppressWarnings("unchecked")
    private <T> ResultSetMapping<T> getFromMapOrThrow(Map<Class<?>, ResultSetMapping<?>> map, Class<T> javaClass, Supplier<String> throwMessageSupplier) {
        requireNonNulls(map, javaClass, throwMessageSupplier);
        return Optional.ofNullable((ResultSetMapping<T>) map.get(javaClass))
            .orElseThrow(() -> new NullPointerException("The " + ResultSetMapperComponent.class.getSimpleName() + " does not have a mapping for " + throwMessageSupplier.get()));
    }

    private <K, V> Map<K, V> newConcurrentMap() {
        return new ConcurrentHashMap<>();
    }

}