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
package com.speedment.runtime.core.internal.util;

import com.speedment.runtime.core.util.StaticClassUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @since  3.0.0
 */
public final class ImmutableListUtil {

    public static <E> List<E> of() {
        return Collections.emptyList();
    }

    public static <E> List<E> of(E element) {
        return Collections.singletonList(element);
    }

    @SafeVarargs // Creating a Stream of an array is safe.
    @SuppressWarnings({"unchecked", "varargs"})
    public static <E> List<E> of(E... elements) {
        return Stream.of(elements).collect(toImmutableList());
    }

    public static <E> List<E> of(Collection<E> elements) {
        return elements.stream().collect(toImmutableList());
    }

    public static <E> List<E> concat(Collection<E> elements, E additionalElement) {
        return concat(elements, of(additionalElement));
    }

    public static <E> List<E> concat(Collection<E> left, Collection<E> right) {
        return Stream.concat(
            left.stream(),
            right.stream()
        ).collect(toImmutableList());
    }

    public static <T> Collector<T, ?, List<T>> toImmutableList() {
        return collectingAndThen(toList(), Collections::unmodifiableList);
    }

    /**
     * Should not be instantiated.
     */
    private ImmutableListUtil() {
        StaticClassUtil.instanceNotAllowed(ImmutableListUtil.class);
    }
}
