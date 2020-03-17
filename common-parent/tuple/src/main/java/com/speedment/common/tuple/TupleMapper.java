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
package com.speedment.common.tuple;

import java.util.function.Function;

/**
 * A BasicTupleMapper may be used to map from an object of type T to a Tuple
 *
 * @author pemi
 * @param <R> The return type of the apply method
 */
public interface TupleMapper<T, R> extends Function<T, R> {

    /**
     * Returns the degree of the Tuple. For example, a Tuple2 has a degree of 2
     * whereas a Tuple3 has a degree of 3.
     *
     * @return the degree of the Tuple
     */
    int degree();

    /**
     * Gets the mapper at the given index. For example, get(0) will return the
     * first mapper and get(1) will return the second etc.
     *
     * @param index of the mapper to get
     * @return the mapper at the given index
     * @throws IndexOutOfBoundsException if
     * {@code index < 0 || index >= length()}
     */
    Function<T, ?> get(int index);

}
