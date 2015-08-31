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
package com.speedment.internal.util.tuple;

import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <R> The return type of {@link #get(int)}
 */
public interface BasicTuple<R> {

    /**
     * Returns the order of the Tuple. For example, a Tuple2 has an order of 2
     * whereas a Tuple3 has an order of 3.
     *
     * @return the order of the Tuple
     */
    int order();

    /**
     * Gets the tuple element at the given index. For example, get(0) will
     * return the first element and get(1) will return the second etc.
     *
     * @param index of the element to get
     * @return the tuple element at the given index
     * @throws IndexOutOfBoundsException if
     * {@code index < 0 || index >= order()}
     */
    R get(int index);

    /**
     * Returns a {@link Stream} of all non-null values for this Tuple of the
     * given class. I.e. all non-null members of a Tuple that can be cast to the
     * given class are included in the Stream. If sequential, the Stream will
     * start with the 0:th tuple and progress upwards.
     *
     * @param <T> The type of stream
     * @param clazz The class of the type of the stream
     * @return a {@link Stream} of all values for this Tuple of the given class
     */
    <T> Stream<T> streamOf(Class<T> clazz);

}
