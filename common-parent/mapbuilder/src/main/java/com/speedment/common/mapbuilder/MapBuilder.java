/**
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.mapbuilder;

import com.speedment.common.mapbuilder.internal.MapBuilderImpl;

import java.util.Map;

/**
 * Builder pattern used to construct immutable instances of regular java
 * {@link Map}.
 * <p>
 * <em>Example usage:</em>
 * <pre>{@code
 *     Map<String, Integer> map = MapBuilder.mapBuilder()
 *         .key("foo").value(1)
 *         .key("bar").value(2)
 *         .key("baz").value(3)
 *         .build();
 * }</pre>
 * <p>
 * Implementations of this interface are not necessarily thread safe, but the
 * returned instances are immutable so they are safe to use concurrently.
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public interface MapBuilder<K, V> {

    /**
     * Creates and returns a new instance of the builder. This method uses a
     * series of stages to get the type information about the key and value
     * correct.
     *
     * @return  the first stage in the builder
     */
    static ExpectFirstKey mapBuilder() {
        return MapBuilderImpl.expectFirstKey();
    }

    /**
     * Creates and returns a new instance of the builder, where the type of the
     * key and class is defined by the first key- and value pair, which is
     * given when the builder is constructed.
     *
     * @param key    the first key (not {@code null})
     * @param value  the first value (not {@code null})
     * @param <K>    the key type
     * @param <V>    the value type
     * @return       the created builder
     */
    static <K, V> MapBuilder<K, V>
    mapBuilder(K key, V value) {
        return MapBuilderImpl.create(key, value);
    }

    /**
     * Creates and returns a new instance of the builder, where the type of the
     * key and class is given using a regular java {@code Class}.
     *
     * @param keyType    the key class object (or {@code null})
     * @param valueType  the value class object (or {@code null})
     * @param <K>        the key type
     * @param <V>        the value type
     * @return           the created builder
     */
    static <K, V> MapBuilder<K, V>
    mapBuilderTyped(Class<K> keyType, Class<V> valueType) {
        return MapBuilderImpl.createTyped();
    }

    /**
     * Appends a key to the builder, returning a stage that has all the
     * information previously added to the builder as well as the key. That
     * stage needs to be completed with a value before a new builder is
     * obtained.
     * <p>
     * Note that it is never safe to assume that a returned builder is the same
     * instance as the existing builder. Always use the returned instance!
     *
     * @param key  the key object (not {@code null})
     * @return     the next stage of the builder that expects a value
     */
    ExpectValue<K, V> key(K key);

    /**
     * Appends a key-value pair to the builder, returning either this or a
     * completely new builder.
     * <p>
     * Note that it is never safe to assume that a returned builder is the same
     * instance as the existing builder. Always use the returned instance!
     *
     * @param key    the key (not {@code null})
     * @param value  the value (not {@code null})
     * @return       the builder with the key-value pair added
     */
    MapBuilder<K, V> entry(K key, V value);

    /**
     * Builds all the key-value pairs added to the builder into a new immutable
     * instance. It is safe to reuse the builder again after this.
     *
     * @return  the built, immutable instance
     */
    Map<K, V> build();

    /**
     * The first stage of the builder, used before any type information has been
     * specified. The next stage is {@link ExpectFirstValue}.
     */
    interface ExpectFirstKey {
        <K> ExpectFirstValue<K> key(K key);
    }

    /**
     * The second stage of the builder, used when only a key has been given, but
     * the value-type is still unknown. The next stage is {@link MapBuilder}.
     *
     * @param <K>  the key type
     */
    interface ExpectFirstValue<K> {
        <V> MapBuilder<K, V> value(V value);
    }

    /**
     * Intermediate stage in the builder, used when all types are known and a
     * new key has been specified, but the value part has not yet been added.
     *
     * @param <K>  the key type
     * @param <V>  the value type
     */
    interface ExpectValue<K, V> {
        MapBuilder<K, V> value(V value);
    }
}
