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
package com.speedment.runtime.config;

import com.speedment.common.function.OptionalBoolean;
import com.speedment.runtime.config.util.DocumentUtil;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * The {@code Document} is the base structure for storing configuration 
 * parameters and can be seen as a hierarchical key-value store. Parameters can
 * be retrieved using the different typed {@code getAsXXX()} methods listed
 * here:
 * <ul>
 *      <li>{@link #get(String)}
 *      <li>{@link #getAsBoolean(String)}
 *      <li>{@link #getAsLong(String)}
 *      <li>{@link #getAsDouble(String)}
 *      <li>{@link #getAsInt(String)}
 *      <li>{@link #getAsString(String)}
 * </ul>
 * <p>
 * To traverse the document tree, the {@link #children(String, BiFunction)}
 * method can be used. It takes a key and a constructor method reference and
 * returns a stream of constructed children.
 * <p>
 * {@code Document} instances are typically short-lived objects that are 
 * recreated every time they are used. References to other instances than the
 * root should therefore not be stored outside the tree.
 * 
 * @author  Emil Forsund
 * @since   2.3.0
 */

public interface Document {

    /**
     * Returns the parent of this Document or {@link Optional#empty()} if the
     * Document does not have a parent.
     *
     * @return the parent
     */
    Optional<? extends Document> getParent();

    /**
     * Returns the raw data-map used in this {@code Document}.
     * 
     * @return  the raw map
     */
    Map<String, Object> getData();

    /**
     * Returns the value mapped to the specified key, or an empty 
     * {@code Optional} if no value was found.
     * 
     * @param key  the key
     * @return     the mapped value or an empty {@code Optional}
     */
    Optional<Object> get(String key);

    /**
     * Returns the {@code boolean} mapped to the specified key, or an empty 
     * {@code OptionalBoolean} if no value was found. If a value was found but
     * couldn't be parsed into a {@code boolean}, a {@code ClassCastException}
     * will be thrown.
     * 
     * @param key  the key
     * @return     the mapped value or an empty {@code OptionalBoolean}
     * @throws ClassCastException  if the mapped value was not a {@code Boolean}
     */
    OptionalBoolean getAsBoolean(String key);

    /**
     * Returns the {@code long} mapped to the specified key, or an empty 
     * {@code OptionalLong} if no value was found. If a value was found but
     * couldn't be parsed into a {@code long}, a {@code ClassCastException}
     * will be thrown.
     * 
     * @param key  the key
     * @return     the mapped value or an empty {@code OptionalLong}
     * @throws ClassCastException  if the mapped value was not a {@code Long}
     */
    OptionalLong getAsLong(String key);

    /**
     * Returns the {@code double} mapped to the specified key, or an empty 
     * {@code OptionalDouble} if no value was found. If a value was found but
     * couldn't be parsed into a {@code double}, a {@code ClassCastException}
     * will be thrown.
     * 
     * @param key  the key
     * @return     the mapped value or an empty {@code OptionalDouble}
     * @throws ClassCastException  if the mapped value was not a {@code Double}
     */
    OptionalDouble getAsDouble(String key);

    /**
     * Returns the {@code int} mapped to the specified key, or an empty 
     * {@code OptionalInt} if no value was found. If a value was found but
     * couldn't be parsed into a {@code int}, a {@code ClassCastException}
     * will be thrown.
     * 
     * @param key  the key
     * @return     the mapped value or an empty {@code OptionalInt}
     * @throws ClassCastException  if the mapped value was not a {@code Integer}
     */
    OptionalInt getAsInt(String key);

    /**
     * Returns the {@code String} mapped to the specified key, or an empty 
     * {@code Optional} if no value was found. If a value was found but
     * couldn't be parsed into a {@code String}, a {@code ClassCastException}
     * will be thrown.
     * 
     * @param key  the key
     * @return     the mapped value or an empty {@code Optional}
     * @throws ClassCastException  if the mapped value was not a {@code String}
     */
    Optional<String> getAsString(String key);

    /**
     * Stores the specified value on the specified key in this document. If a 
     * value was already mapped to that key, it will be overwritten.
     * 
     * @param key    the key to store the value on
     * @param value  the value to store
     */
    void put(String key, Object value);

    /**
     * Returns the children on the specified key, instantiated using the 
     * specified constructor. This instance will be used as parent to the
     * constructor.
     * 
     * @param <P>          the parent type
     * @param <T>          the child type
     * @param key          the key
     * @param constructor  the child constructor to use
     * @return             a stream of constructed children
     * 
     * @see  #children()
     */
    default <P extends Document, T extends Document> Stream<T> children(
            String key, BiFunction<P, Map<String, Object>, T> constructor) {

        final List<Map<String, Object>> list = get(key)
            .map(DocumentUtil::castToDocumentList)
            .orElse(null);

        if (list == null) {
            return Stream.empty();
        } else {
            @SuppressWarnings("unchecked")
            final P thizz = (P)this;
            return list.stream().map(map -> constructor.apply(thizz, map));
        }
    }

    /**
     * Returns a {@code Stream} of child documents, instantiated using the
     * default document implementation. This method will consider every value
     * that is a {@code List} of {@code Map} instances a child.
     * 
     * @return  a stream of every child
     * @see  #children(String, BiFunction)
     */
    Stream<? extends Document> children();

    /**
     * Returns a {@code Stream} of every ancestor to this {@code Document}, 
     * beginning with the first ancestor (the root) and ending with the parent 
     * of this {@code Document}. 
     * <p>
     * This stream can typically not be short-circuited and operations on it 
     * will therefore have a complexity of {@code O(n)}.
     * 
     * @return  a stream of ancestors, from the root to the parent
     */
    default Stream<Document> ancestors() {
        final List<Document> ancestors = new ArrayList<>();
        Document parent = this;
        while ((parent = parent.getParent().orElse(null)) != null) {
            ancestors.add(parent);
        }
        Collections.reverse(ancestors);
        return ancestors.stream();
    }
}