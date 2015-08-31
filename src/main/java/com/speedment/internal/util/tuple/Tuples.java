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

import com.speedment.internal.util.tuple.impl.Tuple1Impl;
import com.speedment.internal.util.tuple.impl.Tuple1OfNullablesImpl;
import com.speedment.internal.util.tuple.impl.Tuple2Impl;
import com.speedment.internal.util.tuple.impl.Tuple2OfNullablesImpl;
import com.speedment.internal.util.tuple.impl.Tuple3Impl;
import com.speedment.internal.util.tuple.impl.Tuple3OfNullablesImpl;

/**
 * Basic convenience factory for Tuples.
 *
 * @author pemi
 */
public interface Tuples {

    /**
     * Creates and returns a Tuple1 with the given parameters.
     *
     * @param <T0> type of element 0
     * @param e0 element 0
     *
     * @return a {@link Tuple1} with the given parameters
     *
     * @see Tuple1
     * @see Tuple
     */
    static <T0> Tuple1<T0> of(T0 e0) {
        return new Tuple1Impl<>(e0);
    }

    /**
     * Creates and returns a Tuple2 with the given parameters.
     *
     * @param <T0> type of element 0
     * @param <T1> type of element 1
     * @param e0 element 0
     * @param e1 element 1
     *
     * @return a {@link Tuple2} with the given parameters
     *
     * @see Tuple2
     * @see Tuple
     */
    static <T0, T1> Tuple2<T0, T1> of(T0 e0, T1 e1) {
        return new Tuple2Impl<>(e0, e1);
    }

    /**
     * Creates and returns a Tuple3 with the given parameters.
     *
     * @param <T0> type of element 0
     * @param <T1> type of element 1
     * @param <T2> type of element 2
     * @param e0 element 0
     * @param e1 element 1
     * @param e2 element 2
     *
     * @return a {@link Tuple3} with the given parameters
     *
     * @see Tuple3
     * @see Tuple
     */
    static <T0, T1, T2> Tuple3<T0, T1, T2> of(T0 e0, T1 e1, T2 e2) {
        return new Tuple3Impl<>(e0, e1, e2);
    }

    /**
     * Creates and returns a Tuple1OfNullables with the given parameters.
     *
     * @param <T0> type of element 0
     * @param e0 element 0
     *
     * @return a {@link Tuple1OfNullables} with the given parameters
     *
     * @see Tuple1OfNullables
     * @see Tuple
     */
    static <T0> Tuple1OfNullables<T0> ofNullables(T0 e0) {
        return new Tuple1OfNullablesImpl<>(e0);
    }

    /**
     * Creates and returns a Tuple2OfNullables with the given parameters.
     *
     * @param <T0> type of element 0
     * @param <T1> type of element 1
     * @param e0 element 0
     * @param e1 element 1
     *
     * @return a {@link Tuple2OfNullables} with the given parameters
     *
     * @see Tuple2OfNullables
     * @see Tuple
     */
    static <T0, T1> Tuple2OfNullables<T0, T1> ofNullables(T0 e0, T1 e1) {
        return new Tuple2OfNullablesImpl<>(e0, e1);
    }

    /**
     * Creates and returns a Tuple3OfNullables with the given parameters.
     *
     * @param <T0> type of element 0
     * @param <T1> type of element 1
     * @param <T2> type of element 2
     * @param e0 element 0
     * @param e1 element 1
     * @param e2 element 2
     *
     * @return a {@link Tuple3OfNullables} with the given parameters
     *
     * @see Tuple3OfNullables
     * @see Tuple
     */
    static <T0, T1, T2> Tuple3OfNullables<T0, T1, T2> ofNullables(T0 e0, T1 e1, T2 e2) {
        return new Tuple3OfNullablesImpl<>(e0, e1, e2);
    }

    // Todo: Add builder that makes .add(e0) -> Tuple1  .add(e0).add(e2) -> Tuple2   .set(3, e3) -> Tuple3
}