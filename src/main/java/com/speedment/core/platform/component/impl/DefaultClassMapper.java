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

import com.speedment.core.platform.component.ClassMapper;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <V>
 */
public abstract class DefaultClassMapper<V> implements ClassMapper<V> {

    protected final Consumer<V> NOTHING = (V v) -> {
    };

    private final Map<Class<?>, V> map;

    public DefaultClassMapper() {
        this.map = new ConcurrentHashMap<>();
    }

    protected <T extends V> T add(T newItem, Function<V, Class<?>> keyMapper) {
        return add(newItem, NOTHING, NOTHING, keyMapper);

    }

    protected <T extends V> T add(T newItem, Consumer<V> added, Consumer<V> removed, Function<V, Class<?>> keyMapper) {
        Objects.requireNonNull(newItem);
        added.accept(newItem);

        @SuppressWarnings("unchecked") // Must be same type!
        final T oldItem = (T) map.put(keyMapper.apply(newItem), newItem);
        if (oldItem != null) {
            removed.accept(oldItem);
        }

        return oldItem;
    }

    /**
     * Gets a Platform Component based on its interface class.
     * <p>
     * Supported interfaces:
     *   DbmsHandlerFactoryComponent
     *   ManagerComponent
     *   PrimaryKeyFactoryComponent
     *   ProjectComponent
     *   SqlTypeMapperComponent
     * 
     * @param <R>
     * @param clazz
     * @return 
     */
    @SuppressWarnings("unchecked") // Must be same type!
    @Override
    public <R extends V> R get(Class<R> clazz) {
        return (R)map.get(clazz);
    }

    @Override
    public Stream<Map.Entry<Class<?>, V>> stream() {
        return map.entrySet().stream();
    }

}
