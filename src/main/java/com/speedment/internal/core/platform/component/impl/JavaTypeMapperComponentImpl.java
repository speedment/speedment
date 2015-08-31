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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.config.parameters.DbmsType;
import com.speedment.internal.core.platform.component.JavaTypeMapperComponent;
import com.speedment.internal.core.runtime.typemapping.JavaTypeMapping;
import com.speedment.internal.core.runtime.typemapping.StandardJavaTypeMapping;
import com.speedment.internal.util.tuple.Tuple2;
import com.speedment.internal.util.tuple.Tuples;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class JavaTypeMapperComponentImpl implements JavaTypeMapperComponent {

    private final Map<Class<?>, JavaTypeMapping<?>> map;
    private final Map<DbmsType, Map<Class<?>, JavaTypeMapping<?>>> dbmsTypeMap;

    public JavaTypeMapperComponentImpl() {
        map = newConcurrentMap();
        dbmsTypeMap = newConcurrentMap();
        StandardJavaTypeMapping.stream().forEach(this::put);
    }

    public JavaTypeMapping<?> put(JavaTypeMapping<?> item) {
        requireNonNull(item);
        return map.put(item.getJavaClass(), item);
    }

    public JavaTypeMapping<?> put(DbmsType dbmsType, JavaTypeMapping<?> item) {
        requireNonNull(dbmsType);
        requireNonNull(item);
        return dbmsTypeMap.computeIfAbsent(dbmsType, k -> new ConcurrentHashMap<>()).put(item.getJavaClass(), item);
    }

    @Override
    public <T> JavaTypeMapping<T> apply(DbmsType dbmsType, Class<T> javaClass) {
        requireNonNull(dbmsType);
        requireNonNull(javaClass);
        return getFromMapOrThrow(dbmsTypeMap.getOrDefault(dbmsType, map), javaClass, () -> dbmsType + ", " + javaClass.getName());
    }

    @Override
    public <T> JavaTypeMapping<T> apply(Class<T> javaClass) {
        requireNonNull(javaClass);
        return getFromMapOrThrow(map, javaClass, javaClass::getName);
    }

    @SuppressWarnings("unchecked")
    private <T> JavaTypeMapping<T> getFromMapOrThrow(Map<Class<?>, JavaTypeMapping<?>> map, Class<T> javaClass, Supplier<String> throwMessageSupplier) {
                requireNonNull(map);
                requireNonNull(javaClass);
                requireNonNull(throwMessageSupplier);
        return Optional.ofNullable((JavaTypeMapping<T>) map.get(javaClass))
            .orElseThrow(() -> new NullPointerException("The " + JavaTypeMapperComponent.class.getSimpleName() + " does not have a mapping for " + throwMessageSupplier.get()));
    }

    private <K, V> Map<K, V> newConcurrentMap() {
        return new ConcurrentHashMap<>();
    }

    /**
     * Returns a {@link Stream} of the current mappings that are registered with
     * this class. Mappings that are not associated to any particular DbmsType
     * will have their {@code Optional<DbmsType>} set to
     * {@code Optional.empty()} whereas specific DbmsType mappings will have the
     * {@code Optional<DbmsType>} field set accordingly.
     *
     * @return a {@link Stream} of the current mappings that are registered with
     * this class
     */
    public Stream<Tuple2<Optional<DbmsType>, JavaTypeMapping<?>>> stream() {

        final Stream<Tuple2<Optional<DbmsType>, JavaTypeMapping<?>>> s0 = map.values().stream().map(v -> Tuples.of(Optional.empty(), v));

        final Stream.Builder<Stream<Tuple2<Optional<DbmsType>, JavaTypeMapping<?>>>> sb = Stream.builder();
        sb.add(s0);

        dbmsTypeMap.entrySet().stream().forEach(e -> {
            final DbmsType dbmsType = e.getKey();
            Stream<Tuple2<Optional<DbmsType>, JavaTypeMapping<?>>> sn = e.getValue().values().stream().map(v -> Tuples.of(Optional.of(dbmsType), v));
            sb.add(sn);
        });

        return sb.build().flatMap(Function.identity());
    }

}
