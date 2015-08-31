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
package com.speedment.internal.util;

import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import static groovy.json.internal.Chr.array;
import java.util.Collection;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public class NullUtil {

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
    public static <T> T[] requireNonNulls(T[] array) {
        requireNonNull(array, "The provided array is null.");
        int len = array.length;
        for (int i = 0; i < len; i++) {
            if (array[i] == null) {
                throw new NullPointerException("Item " + i + " in the array " + array + " is null");
            }
        }
        return array;
    }

    /**
     * Checks if this collection is non null and also that all members are
     * non-null. If a null is detected a NullPointerException is thrown.
     *
     * @param <T> collection type
     * @param collection to check
     * @return the same collection instance as provided
     * @throws NullPointerException if a null is found in the collection or if
     * the array itself is null
     */
    public static <E, T extends Collection<E>> T requireNonNulls(T collection) {
        requireNonNull(collection, "The provided collection is null.");
        int i = 0;
        for (E item : collection) {
            if (item == null) {
                throw new NullPointerException("Element " + i + " in the collection " + collection + " is null");
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
    public static <T> T[] requireNonNulls(T[] array, String msg) {
        requireNonNull(array, msg);
        int len = array.length;
        for (int i = 0; i < len; i++) {
            if (array[i] == null) {
                throw new NullPointerException(msg + ", item " + i + " in the array " + array + " is null");
            }
        }
        return array;
    }

    public static void requireNonNulls(Object o0, Object o1) {
        if (o0 == null) {
            throwNpeFor(2, 0);
        }
        if (o1 == null) {
            throwNpeFor(2, 1);
        }
    }

    public static void requireNonNulls(Object o0, Object o1, Object o2) {
        if (o0 == null) {
            throwNpeFor(3, 0);
        }
        if (o1 == null) {
            throwNpeFor(3, 1);
        }
        if (o2 == null) {
            throwNpeFor(3, 2);
        }
    }

    public static void requireNonNulls(Object o0, Object o1, Object o2, Object o3) {
        if (o0 == null) {
            throwNpeFor(4, 0);
        }
        if (o1 == null) {
            throwNpeFor(4, 1);
        }
        if (o2 == null) {
            throwNpeFor(4, 2);
        }
        if (o3 == null) {
            throwNpeFor(4, 3);
        }
    }

    public static void requireNonNulls(Object o0, Object o1, Object o2, Object o3, Object o4) {
        if (o0 == null) {
            throwNpeFor(5, 0);
        }
        if (o1 == null) {
            throwNpeFor(5, 1);
        }
        if (o2 == null) {
            throwNpeFor(5, 2);
        }
        if (o3 == null) {
            throwNpeFor(5, 3);
        }
        if (o4 == null) {
            throwNpeFor(5, 4);
        }
    }

    public static void requireNonNulls(Object o0, Object o1, Object o2, Object o3, Object o4, Object o5) {
        if (o0 == null) {
            throwNpeFor(6, 0);
        }
        if (o1 == null) {
            throwNpeFor(6, 1);
        }
        if (o2 == null) {
            throwNpeFor(6, 2);
        }
        if (o3 == null) {
            throwNpeFor(6, 3);
        }
        if (o4 == null) {
            throwNpeFor(6, 4);
        }
        if (o5 == null) {
            throwNpeFor(6, 5);
        }
    }

    public static void requireNonNulls(Object o0, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        if (o0 == null) {
            throwNpeFor(7, 0);
        }
        if (o1 == null) {
            throwNpeFor(7, 1);
        }
        if (o2 == null) {
            throwNpeFor(7, 2);
        }
        if (o3 == null) {
            throwNpeFor(7, 3);
        }
        if (o4 == null) {
            throwNpeFor(7, 4);
        }
        if (o5 == null) {
            throwNpeFor(7, 5);
        }
        if (o6 == null) {
            throwNpeFor(7, 6);
        }
    }

    public static void requireNonNulls(Object o0, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        if (o0 == null) {
            throwNpeFor(8, 0);
        }
        if (o1 == null) {
            throwNpeFor(8, 1);
        }
        if (o2 == null) {
            throwNpeFor(8, 2);
        }
        if (o3 == null) {
            throwNpeFor(8, 3);
        }
        if (o4 == null) {
            throwNpeFor(8, 4);
        }
        if (o5 == null) {
            throwNpeFor(8, 5);
        }
        if (o6 == null) {
            throwNpeFor(8, 6);
        }
        if (o7 == null) {
            throwNpeFor(8, 7);
        }
    }

    private static void throwNpeFor(int count, int i) {
        if (count == 1) {
            throw new NullPointerException("The argument was null.");
        } else {
            throw new NullPointerException("The " + Pluralis.INSTANCE.ordinalize(i + 1) + " of the " + count + " arguments was null.");
        }
    }

    private NullUtil() {
        instanceNotAllowed(getClass());
    }

}
