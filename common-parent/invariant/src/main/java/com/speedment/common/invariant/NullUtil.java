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
package com.speedment.common.invariant;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

/**
 * Utility class for checking invariants on {@code null}-values.
 *
 * @author Emil Forslund
 */
public final class NullUtil {

    private NullUtil() {}

    private static final String IS_NULL = "is null";
    private static final String FIRST_ARG_IS_NULL = "First argument is null";
    private static final String SECOND_ARG_IS_NULL = "Second argument is null";
    private static final String THIRD_ARG_IS_NULL = "Third argument is null";
    private static final String FOURTH_ARG_IS_NULL = "Fourth argument is null";
    private static final String FIFTH_ARG_IS_NULL = "Fifth argument is null";
    private static final String SIXTH_ARG_IS_NULL = "Sixth argument is null";
    private static final String SEVENTH_ARG_IS_NULL = "Seventh argument is null";
    private static final String EIGHT_ARG_IS_NULL = "Eight argument is null";

    /**
     * Checks if this array is non null and also that all members are non-null.
     * If a null is detected a NullPointerException is thrown.
     *
     * @param <T> array type
     * @param array to check
     * @return the same array instance as provided
     * @throws NullPointerException if a null is found in the array or if the
     * array itself is null
     */
    public static <T> T[] requireNonNullElements(T[] array) {
        requireNonNull(array, "The provided array " + IS_NULL + ".");
        int len = array.length;
        for (int i = 0; i < len; i++) {
            if (array[i] == null) {
                throw new NullPointerException("Item " + i + " in the array " + Arrays.toString(array) + " " + IS_NULL);
            }
        }
        return array;
    }

    /**
     * Checks if this collection is non null and also that all members are
     * non-null. If a null is detected a NullPointerException is thrown.
     *
     * @param <E> the inner type of the collection
     * @param <T> collection type
     * @param collection to check
     * @return the same collection instance as provided
     * @throws NullPointerException if a null is found in the collection or if
     * the array itself is null
     */
    public static <E, T extends Collection<E>> T requireNonNullElements(T collection) {
        requireNonNull(collection, "The provided collection " + IS_NULL + ".");
        int i = 0;
        for (E item : collection) {
            if (item == null) {
                throw new NullPointerException("Element " + i + " in the collection " + collection + " " + IS_NULL);
            }
            i++;
        }
        return collection;
    }

    /**
     * Checks if this array is non null and also that all members are non-null.
     * If a null is detected a NullPointerException is thrown.
     *
     * @param <T> array type
     * @param array to check
     * @param msg to show if a null is detected
     * @return the same array instance as provided
     * @throws NullPointerException if a null is found in the array or if the
     * array itself is null
     */
    public static <T> T[] requireNonNullElements(T[] array, String msg) {
        requireNonNull(array, msg);
        int len = array.length;
        for (int i = 0; i < len; i++) {
            if (array[i] == null) {
                throw new NullPointerException(msg + ", item " + i + " in the array " + Arrays.toString(array) + " " + IS_NULL);
            }
        }
        return array;
    }

    /**
     * Checks if this element is non null. If a null is detected a
     * NullPointerException is thrown.
     *
     * @param o0 object to check
     * @throws NullPointerException if the element is null
     */
    public static void requireNonNulls(Object o0) {
        if (o0 == null) {
            throw new NullPointerException(FIRST_ARG_IS_NULL);
        }
    }

    /**
     * Checks if the provided elements all are non null. If a null is detected a
     * NullPointerException is thrown.
     *
     * @param o0 object to check
     * @param o1 object to check
     * @throws NullPointerException if at least one of the elements are null
     */
    public static void requireNonNulls(Object o0, Object o1) {
        if (o0 == null) {
            throw new NullPointerException(FIRST_ARG_IS_NULL);
        }
        if (o1 == null) {
            throw new NullPointerException(SECOND_ARG_IS_NULL);
        }
    }

    /**
     * Checks if the provided elements all are non null. If a null is detected a
     * NullPointerException is thrown.
     *
     * @param o0 object to check
     * @param o1 object to check
     * @param o2 object to check
     * @throws NullPointerException if at least one of the elements are null
     */
    public static void requireNonNulls(Object o0, Object o1, Object o2) {
        if (o0 == null) {
            throw new NullPointerException(FIRST_ARG_IS_NULL);
        }
        if (o1 == null) {
            throw new NullPointerException(SECOND_ARG_IS_NULL);
        }
        if (o2 == null) {
            throw new NullPointerException(THIRD_ARG_IS_NULL);
        }
    }

    /**
     * Checks if the provided elements all are non null. If a null is detected a
     * NullPointerException is thrown.
     *
     * @param o0 object to check
     * @param o1 object to check
     * @param o2 object to check
     * @param o3 object to check
     * @throws NullPointerException if at least one of the elements are null
     */
    public static void requireNonNulls(Object o0, Object o1, Object o2, Object o3) {
        if (o0 == null) {
            throw new NullPointerException(FIRST_ARG_IS_NULL);
        }
        if (o1 == null) {
            throw new NullPointerException(SECOND_ARG_IS_NULL);
        }
        if (o2 == null) {
            throw new NullPointerException(THIRD_ARG_IS_NULL);
        }
        if (o3 == null) {
            throw new NullPointerException(FOURTH_ARG_IS_NULL);
        }
    }

    /**
     * Checks if the provided elements all are non null. If a null is detected a
     * NullPointerException is thrown.
     *
     * @param o0 object to check
     * @param o1 object to check
     * @param o2 object to check
     * @param o3 object to check
     * @param o4 object to check
     * @throws NullPointerException if at least one of the elements are null
     */
    public static void requireNonNulls(Object o0, Object o1, Object o2, Object o3, Object o4) {
        if (o0 == null) {
            throw new NullPointerException(FIRST_ARG_IS_NULL);
        }
        if (o1 == null) {
            throw new NullPointerException(SECOND_ARG_IS_NULL);
        }
        if (o2 == null) {
            throw new NullPointerException(THIRD_ARG_IS_NULL);
        }
        if (o3 == null) {
            throw new NullPointerException(FOURTH_ARG_IS_NULL);
        }
        if (o4 == null) {
            throw new NullPointerException(FIFTH_ARG_IS_NULL);
        }
    }

    /**
     * Checks if the provided elements all are non null. If a null is detected a
     * NullPointerException is thrown.
     *s
     * @param o0 object to check
     * @param o1 object to check
     * @param o2 object to check
     * @param o3 object to check
     * @param o4 object to check
     * @param o5 object to check
     * @throws NullPointerException if at least one of the elements are null
     */
    public static void requireNonNulls(Object o0, Object o1, Object o2, Object o3, Object o4, Object o5) {
        requireNonNulls(o0, o1, o2, o3, o4);
        if (o5 == null) {
            throw new NullPointerException(SIXTH_ARG_IS_NULL);
        }
    }

    /**
     * Checks if the provided elements all are non null. If a null is detected a
     * NullPointerException is thrown.
     *
     * @param o0 object to check
     * @param o1 object to check
     * @param o2 object to check
     * @param o3 object to check
     * @param o4 object to check
     * @param o5 object to check
     * @param o6 object to check
     * @throws NullPointerException if at least one of the elements are null
     */
    public static void requireNonNulls(Object o0, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        requireNonNulls(o0, o1, o2, o3, o4);
        if (o5 == null) {
            throw new NullPointerException(SIXTH_ARG_IS_NULL);
        }
        if (o6 == null) {
            throw new NullPointerException(SEVENTH_ARG_IS_NULL);
        }
    }

    /**
     * Checks if the provided elements all are non null. If a null is detected a
     * NullPointerException is thrown.
     *
     * @param o0 object to check
     * @param o1 object to check
     * @param o2 object to check
     * @param o3 object to check
     * @param o4 object to check
     * @param o5 object to check
     * @param o6 object to check
     * @param o7 object to check
     * @throws NullPointerException if at least one of the elements are null
     */
    public static void requireNonNulls(Object o0, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        requireNonNulls(o0, o1, o2, o3, o4);
        if (o5 == null) {
            throw new NullPointerException(SIXTH_ARG_IS_NULL);
        }
        if (o6 == null) {
            throw new NullPointerException(SEVENTH_ARG_IS_NULL);
        }
        if (o7 == null) {
            throw new NullPointerException(EIGHT_ARG_IS_NULL);
        }
    }

    /**
     * Checks if the provided map contains all the provided required non-null
     * key(s).
     *
     * @param <K> key type
     * @param <V> value type
     * @param map to check
     * @param requiredKeys to check for existence
     * @return the same instance of the provided map
     * @throws NoSuchElementException if at least one required key is not a key
     * in the provided Map
     * @throws NullPointerException if the provided Map is null or if at least
     * one of the required key are null
     */
    @SafeVarargs // Iterating over an array is safe
    @SuppressWarnings("varargs")
    public static <K, V> Map<K, V> requireKeys(Map<K, V> map, K... requiredKeys) {
        requireNonNull(map);
        requireNonNullElements(requiredKeys);

        for (final K key : requiredKeys) {
            if (!map.containsKey(key)) {
                throw new NoSuchElementException("The Map does not contain the key " + key);
            }
        }

        return map;
    }

    /**
     * Checks if the provided map contains all the provided required non-null
     * key(s).
     *
     * @param <K> key type
     * @param <V> value type
     * @param map to check
     * @param requiredKey to check for existence
     * @return the same instance of the provided map
     * @throws NoSuchElementException if the required key is not a key in the
     * provided Map
     * @throws NullPointerException if the provided Map is null or if the
     * required key is null
     */
    public static <K, V> Map<K, V> requireKeys(Map<K, V> map, K requiredKey) {
        requireNonNull(map);
        requireNonNull(requiredKey);

        if (!map.containsKey(requiredKey)) {
            throw new NoSuchElementException("The Map does not contain the key " + requiredKey);
        }
        return map;
    }

    /**
     * Checks if the provided map contains all the provided required key(s).
     *
     * @param <K> key type
     * @param <V> value type
     * @param map to check
     * @param requiredKeyA to check for existence
     * @param requiredKeyB to check for existence
     *
     * @return the same instance of the provided map
     * @throws NoSuchElementException if the required keys are not keys in the
     * provided Map
     * @throws NullPointerException if the provided Map is null
     */
    public static <K, V> Map<K, V> requireKeys(Map<K, V> map, K requiredKeyA, K requiredKeyB) {
        requireKeys(map, requiredKeyA);
        requireKeys(map, requiredKeyB);
        return map;
    }

    /**
     * Checks if the provided map contains all the provided required key(s).
     *
     * @param <K> key type
     * @param <V> value type
     * @param map to check
     * @param requiredKeyA to check for existence
     * @param requiredKeyB to check for existence
     * @param requiredKeyC to check for existence
     * @return the same instance of the provided map
     * @throws NoSuchElementException if the required keys are not keys in the
     * provided Map
     * @throws NullPointerException if the provided Map is null
     */
    public static <K, V> Map<K, V> requireKeys(Map<K, V> map, K requiredKeyA, K requiredKeyB, K requiredKeyC) {
        requireKeys(map, requiredKeyA);
        requireKeys(map, requiredKeyB);
        requireKeys(map, requiredKeyC);
        return map;
    }

}
