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

import java.util.Optional;

/**
 *
 * @author pemi
 * @param <T0>
 * @param <T1>
 */
public class Tuple2Nullable<T0, T1> extends BasicTupleNullable<Tuple2Nullable<T0, T1>> {

    @SuppressWarnings("rawtypes")
    public Tuple2Nullable(Class<T0> c0, Class<T1> c1) {
        super(Tuple2Nullable.class, new Class[]{c0, c1});
    }

    @SuppressWarnings("rawtypes")
    public Tuple2Nullable(Class<T0> c0, Class<T1> c1, T0 v0, T1 v1) {
        super(Tuple2Nullable.class,
            new Class[]{c0, c1},
            v0, v1
        );
    }

    @SuppressWarnings("unchecked")
    public Optional<T0> get0() {
        return Optional.ofNullable((T0) values[0]);
    }

    @SuppressWarnings("unchecked")
    public Optional<T1> get1() {
        return Optional.ofNullable((T1) values[1]);
    }

    public Tuple2Nullable<T0, T1> set0(T0 t0) {
        values[0] = t0;
        return this;
    }

    public Tuple2Nullable<T0, T1> set1(T1 t1) {
        values[1] = t1;
        return this;
    }

}
