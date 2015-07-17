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
package com.speedment.util.tuple;

import java.util.Objects;

/**
 *
 * @author pemi
 * @param <T0> Type of 0:th argument
 * @param <T1> Type of 1:st argument
 * @param <T2> Type of 2:nd argument
 */
public class Tuple3<T0, T1, T2> extends BasicTuple<Tuple3<T0, T1, T2>> {

    public Tuple3() {
        super(Tuple3.class, 2);
    }

    public Tuple3(T0 t0, T1 t1, T2 t2) {
        super(Tuple3.class,
            Objects.requireNonNull(t0),
            Objects.requireNonNull(t1),
            Objects.requireNonNull(t2)
        );
    }

    @SuppressWarnings("unchecked")
    public T0 get0() {
        return (T0) values[0];
    }

    @SuppressWarnings("unchecked")
    public T1 get1() {
        return (T1) values[1];
    }

    @SuppressWarnings("unchecked")
    public T2 get2() {
        return (T2) values[2];
    }

    public Tuple3<T0, T1, T2> set0(T0 t0) {
        values[0] = Objects.requireNonNull(t0);
        return this;
    }

    public Tuple3<T0, T1, T2> set1(T1 t1) {
        values[1] = Objects.requireNonNull(t1);
        return this;
    }

    public Tuple3<T0, T1, T2> set2(T2 t2) {
        values[2] = Objects.requireNonNull(t2);
        return this;
    }

}
