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
package com.speedment.common.codegen.internal.util;

import java.util.Arrays;
import java.util.Collection;

import static com.speedment.common.codegen.internal.util.StaticClassUtil.instanceNotAllowed;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @author  Per Minborg
 * @since   2.1
 */
public final class NullUtil {
    
    /**
     * Checks if this set is non null and also that all members are non-null.
     * If a null is detected a NullPointerException is thrown.
     *
     * @param <T>         the item type
     * @param <C>         the collection type
     * @param collection  to check
     * @return            the same array instance as provided
     * 
     * @throws NullPointerException  if a null is found in the set or if the
     *                               set itself is null
     */
    public static <T, C extends Collection<T>> C requireNonNullElements(C collection) {
        requireNonNull(collection, "The provided collection is null.");
        
        collection.forEach(t -> {
            if (t == null) {
                throw new NullPointerException(
                    "An item in the collection is null."
                );
            }
        });
        
        return collection;
    }
    
    /**
     * Checks if this array is non null and also that all members are non-null.
     * If a null is detected a NullPointerException is thrown.
     *
     * @param <T>    array type
     * @param array  to check
     * @return       the same array instance as provided
     * 
     * @throws NullPointerException  if a null is found in the array or if the
     *                               array itself is null
     */
    public static <T> T[] requireNonNullElements(T[] array) {
        requireNonNull(array, "The provided array is null.");
        int len = array.length;
        for (int i = 0; i < len; i++) {
            if (array[i] == null) {
                throw new NullPointerException("Item " + i + " in the array " + Arrays.toString(array) + " is null");
            }
        }
        return array;
    }
    
    /**
     * Checks if all parameters are non-null. If a null is detected a 
     * {@code NullPointerException} is thrown.
     *
     * @param <T>  array type
     * @param t0   to check
     * 
     * @throws NullPointerException  if a null is found in the parameters
     */
    public static <T> void requireNonNulls(T t0) {
        requireNonNull(t0, "Parameter 0 is null.");
    }
    
    /**
     * Checks if all parameters are non-null. If a null is detected a 
     * {@code NullPointerException} is thrown.
     *
     * @param <T>  array type
     * @param t0   to check
     * @param t1   to check
     * 
     * @throws NullPointerException  if a null is found in the parameters
     */
    public static <T> void requireNonNulls(T t0, T t1) {
        requireNonNull(t0, "Parameter 0 is null.");
        requireNonNull(t1, "Parameter 1 is null.");
    }
    
    /**
     * Checks if all parameters are non-null. If a null is detected a 
     * {@code NullPointerException} is thrown.
     *
     * @param <T>  array type
     * @param t0   to check
     * @param t1   to check
     * @param t2   to check
     * 
     * @throws NullPointerException  if a null is found in the parameters
     */
    public static <T> void requireNonNulls(T t0, T t1, T t2) {
        requireNonNull(t0, "Parameter 0 is null.");
        requireNonNull(t1, "Parameter 1 is null.");
        requireNonNull(t2, "Parameter 2 is null.");
    }
    
    /**
     * Utility classes should not be instantiated.
     */
    private NullUtil() { instanceNotAllowed(getClass()); }
}