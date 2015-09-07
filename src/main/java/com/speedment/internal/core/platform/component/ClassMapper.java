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
package com.speedment.internal.core.platform.component;

import java.util.Map;
import java.util.stream.Stream;

/**
 * A generic Class mapper interface. I.e. a mapper that can associate a class to
 * an implementation of that class. Often, an interface is used as class key and
 * then a concrete implementing class is used as an association to that
 * interface. This creates a pluggable architecture framework.
 *
 * @author pemi
 * @param <V> The base type
 */
public interface ClassMapper<V> {

    /**
     * Adds a mapping for an item.
     *
     * @param item to add
     * @return the previous mapping that existed, or null
     */
    V put(V item);

    /**
     * Puts a new mapping and returns the mapping for the given class, or null
     * if no mapping exists.
     *
     * @param <R> The class type
     * @param clazz the class to use
     * @return the mapping for the given class, or null if no mapping exists
     */
    public <R extends V> R get(Class<R> clazz);

    /**
     * Returns a {@link Stream} of all mappings that exists.
     *
     * @return a {@link Stream} of all mappings that exists
     */
    Stream<Map.Entry<Class<?>, V>> stream();

}
