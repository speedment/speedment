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
package com.speedment.runtime.core.internal.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public final class CollectionsUtil {

    public static <T> Optional<T> findAnyIn(Collection<T> collection) {
        final Iterator<T> iterator = collection.iterator();
        if (iterator.hasNext()) {
            return Optional.of(iterator.next());
        }
        return Optional.empty();
    }

    /**
     * Gets one element from the provided Collection. If no element is present,
     * an Exception is thrown. No guarantee is made as to which element is
     * returned from time to time.
     *
     * @param <T> Collection type
     * @param collection to use
     * @return one element from the provided Collection
     * @throws NoSuchElementException if no elements are present in the
     * Collection
     */
    public static <T> T getAnyFrom(Collection<T> collection) {
        final Iterator<T> iterator = collection.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        throw new NoSuchElementException("No value present");
    }

    private CollectionsUtil() {
    }

}
