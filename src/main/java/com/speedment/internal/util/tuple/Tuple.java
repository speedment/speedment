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
 * This interface defines a generic Tuple of any order that can hold non-null
 * values. A Tuple is type safe, immutable and thread safe. For null value
 * elements see {@link TupleOfNullables}
 *
 * @author pemi
 * @see TupleOfNullables
 */
public interface Tuple extends BasicTuple<Object> {

    /**
     * Returns a {@link Stream} of all values for this Tuple. If sequential, the
     * Stream will start with the 0:th tuple and progress upwards.
     *
     * @return a {@link Stream} of all values for this Tuple
     */
    Stream<Object> stream();

}
