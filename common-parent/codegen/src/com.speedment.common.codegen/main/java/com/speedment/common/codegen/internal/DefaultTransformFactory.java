/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.internal;

import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.TransformFactory;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

/**
 * The default implementation of the {@link TransformFactory} interface.
 *
 * @author Emil Forslund
 */
public class DefaultTransformFactory implements TransformFactory {

    private final Map<Class<?>, Set<Map.Entry<Class<?>, Class<? extends Transform<?, ?>>>>> transforms;
    private final String name;

    /**
     * Instantiates the factory.
     *
     * @param name the unique name to use
     */
    public DefaultTransformFactory(String name) {
        this.name = requireNonNull(name);
        this.transforms = new ConcurrentHashMap<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public <A, B, T extends Transform<A, B>> TransformFactory install(Class<A> from, Class<B> to, Class<T> transform) {
        requireNonNull(from);
        requireNonNull(to);
        requireNonNull(transform);

        transforms.computeIfAbsent(from, f -> new HashSet<>())
            .add(new AbstractMap.SimpleEntry<>(to, transform));

        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A, T extends Transform<A, ?>> Set<Map.Entry<Class<?>, T>> allFrom(Class<A> model) {
        requireNonNull(model);

        return transforms.entrySet().stream()
            .filter(e -> e.getKey().isAssignableFrom(model))
            .flatMap(e -> e.getValue().stream())
            .map(e -> toEntry(e.getKey(), (T) TransformFactory.create(e.getValue())))
            .collect(toSet());

    }

    private static <A, T extends Transform<A, ?>> Map.Entry<Class<?>, T> toEntry(Class<?> key, T value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }
}
