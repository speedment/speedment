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

import static com.speedment.internal.util.NullUtil.requireNonNulls;
import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import java.util.NoSuchElementException;
import java.util.Optional;
import static com.speedment.internal.util.NullUtil.requireNonNulls;

/**
 *
 * @author pemi
 */
public final class Cast {

    /**
     * Casts and returns the provided object if it is assignable from the given
     * class, otherwise returns an Optional.empty().
     *
     * @param <T> the type to return
     * @param object to cast
     * @param clazz to cast to
     * @return An Optional of the casted element or Optional.empty()
     */
    public static <T> Optional<T> cast(Object object, Class<T> clazz) {
        requireNonNulls(object, clazz);
        if (clazz.isAssignableFrom(object.getClass())) {
            final T result = clazz.cast(object);
            return Optional.of(result);
        }
        return Optional.empty();
    }

    /**
     * Casts and returns the provided object to the provided class.
     *
     * @param <T> the type to return
     * @param object to cast
     * @param clazz to cast to
     * @return the casted element
     * @throws NoSuchElementException if the object could not be casted to the
     * provided class
     */
    public static <T> T castOrFail(Object object, Class<T> clazz) {
        requireNonNulls(object, clazz);
        return Optional.ofNullable(object)
            .filter(o -> clazz.isAssignableFrom(o.getClass()))
            .map(clazz::cast)
            .get();
    }

    /**
     * Utility classes should not be instantiated.
     */
    private Cast() {
        instanceNotAllowed(getClass());
    }
}
