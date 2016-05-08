/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.util.tuple;

import com.speedment.runtime.internal.util.tuple.impl.Tuple0Impl;
import com.speedment.runtime.internal.util.tuple.impl.Tuple0OfNullablesImpl;
import com.speedment.runtime.internal.util.tuple.impl.Tuple1Impl;
import com.speedment.runtime.internal.util.tuple.impl.Tuple1OfNullablesImpl;
import com.speedment.runtime.internal.util.tuple.impl.Tuple2Impl;
import com.speedment.runtime.internal.util.tuple.impl.Tuple2OfNullablesImpl;
import com.speedment.runtime.internal.util.tuple.impl.Tuple3Impl;
import com.speedment.runtime.internal.util.tuple.impl.Tuple3OfNullablesImpl;
import com.speedment.runtime.internal.util.tuple.impl.Tuple4Impl;
import com.speedment.runtime.internal.util.tuple.impl.Tuple4OfNullablesImpl;
import com.speedment.runtime.internal.util.tuple.impl.Tuple5Impl;
import com.speedment.runtime.internal.util.tuple.impl.Tuple5OfNullablesImpl;
import com.speedment.runtime.internal.util.tuple.impl.Tuple6Impl;
import com.speedment.runtime.internal.util.tuple.impl.Tuple6OfNullablesImpl;

/**
 * Basic convenience factory for Tuples.
 *
 * @author pemi
 */
public interface Tuples {

    /**
     * Creates and returns an empty Tuple
     *
     * @return a {@link Tuple0}
     *
     * @see Tuple0
     * @see Tuple
     */
    static Tuple0 of() {
        return Tuple0Impl.EMPTY_TUPLE;
    }

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
     * Creates and returns a Tuple3 with the given parameters.
     *
     * @param <T0> type of element 0
     * @param <T1> type of element 1
     * @param <T2> type of element 2
     * @param <T3> type of element 3
     * @param e0 element 0
     * @param e1 element 1
     * @param e2 element 2
     * @param e3 element 3
     *
     * @return a {@link Tuple4} with the given parameters
     *
     * @see Tuple4
     * @see Tuple
     */
    static <T0, T1, T2, T3> Tuple4<T0, T1, T2, T3> of(T0 e0, T1 e1, T2 e2, T3 e3) {
        return new Tuple4Impl<>(e0, e1, e2, e3);
    }

    /**
     * Creates and returns a Tuple3 with the given parameters.
     *
     * @param <T0> type of element 0
     * @param <T1> type of element 1
     * @param <T2> type of element 2
     * @param <T3> type of element 3
     * @param <T4> type of element 3
     * @param e0 element 0
     * @param e1 element 1
     * @param e2 element 2
     * @param e3 element 3
     * @param e4 element 4
     *
     * @return a {@link Tuple5} with the given parameters
     *
     * @see Tuple5
     * @see Tuple
     */
    static <T0, T1, T2, T3, T4> Tuple5<T0, T1, T2, T3, T4> of(T0 e0, T1 e1, T2 e2, T3 e3, T4 e4) {
        return new Tuple5Impl<>(e0, e1, e2, e3, e4);
    }

    /**
     * Creates and returns a Tuple3 with the given parameters.
     *
     * @param <T0> type of element 0
     * @param <T1> type of element 1
     * @param <T2> type of element 2
     * @param <T3> type of element 3
     * @param <T4> type of element 3
     * @param <T5> type of element 4
     * @param e0 element 0
     * @param e1 element 1
     * @param e2 element 2
     * @param e3 element 3
     * @param e4 element 4
     * @param e5 element 5
     *
     * @return a {@link Tuple6} with the given parameters
     *
     * @see Tuple6
     * @see Tuple
     */
    static <T0, T1, T2, T3, T4, T5> Tuple6<T0, T1, T2, T3, T4, T5> of(T0 e0, T1 e1, T2 e2, T3 e3, T4 e4, T5 e5) {
        return new Tuple6Impl<>(e0, e1, e2, e3, e4, e5);
    }

    /**
     * Creates and returns a Tuple0OfNullables .
     *
     * @return a {@link Tuple0OfNullables}
     *
     * @see Tuple0OfNullables
     * @see Tuple
     */
    static Tuple0OfNullables ofNullables() {
        return Tuple0OfNullablesImpl.EMPTY_TUPLE;
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

    /**
     * Creates and returns a Tuple4OfNullables with the given parameters.
     *
     * @param <T0> type of element 0
     * @param <T1> type of element 1
     * @param <T2> type of element 2
     * @param <T3> type of element 3
     * @param e0 element 0
     * @param e1 element 1
     * @param e2 element 2
     * @param e3 element 3
     *
     * @return a {@link Tuple4OfNullables} with the given parameters
     *
     * @see Tuple4OfNullables
     * @see Tuple
     */
    static <T0, T1, T2, T3> Tuple4OfNullables<T0, T1, T2, T3> ofNullables(T0 e0, T1 e1, T2 e2, T3 e3) {
        return new Tuple4OfNullablesImpl<>(e0, e1, e2, e3);
    }

    /**
     * Creates and returns a Tuple5OfNullables with the given parameters.
     *
     * @param <T0> type of element 0
     * @param <T1> type of element 1
     * @param <T2> type of element 2
     * @param <T3> type of element 3
     * @param <T4> type of element 4
     * @param e0 element 0
     * @param e1 element 1
     * @param e2 element 2
     * @param e3 element 3
     * @param e4 element 4
     *
     * @return a {@link Tuple5OfNullables} with the given parameters
     *
     * @see Tuple5OfNullables
     * @see Tuple
     */
    static <T0, T1, T2, T3, T4> Tuple5OfNullables<T0, T1, T2, T3, T4> ofNullables(T0 e0, T1 e1, T2 e2, T3 e3, T4 e4) {
        return new Tuple5OfNullablesImpl<>(e0, e1, e2, e3, e4);
    }

    /**
     * Creates and returns a Tuple6OfNullables with the given parameters.
     *
     * @param <T0> type of element 0
     * @param <T1> type of element 1
     * @param <T2> type of element 2
     * @param <T3> type of element 3
     * @param <T4> type of element 4
     * @param <T5> type of element 5
     * @param e0 element 0
     * @param e1 element 1
     * @param e2 element 2
     * @param e3 element 3
     * @param e4 element 4
     * @param e5 element 5
     *
     * @return a {@link Tuple6OfNullables} with the given parameters
     *
     * @see Tuple5OfNullables
     * @see Tuple
     */
    static <T0, T1, T2, T3, T4, T5> Tuple6OfNullables<T0, T1, T2, T3, T4, T5> ofNullables(T0 e0, T1 e1, T2 e2, T3 e3, T4 e4, T5 e5) {
        return new Tuple6OfNullablesImpl<>(e0, e1, e2, e3, e4, e5);
    }

    // Todo: Add builder that makes .add(e0) -> Tuple1  .add(e0).add(e2) -> Tuple2   .set(3, e3) -> Tuple3
}
