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
package com.speedment.internal.util.tuple.impl;

import com.speedment.util.tuple.Tuple5;

/**
 *
 * @author pemi
 * @param <T0> Type of 0:th argument
 * @param <T1> Type of 1:st argument
 * @param <T2> Type of 2:nd argument
 * @param <T3> Type of 3:rd argument
 * @param <T4> Type of 4:th argument
 */
public final class Tuple5Impl<T0, T1, T2, T3, T4> extends AbstractTuple implements Tuple5<T0, T1, T2, T3, T4> {

    public Tuple5Impl(T0 v0, T1 v1, T2 v2, T3 v3, T4 v4) {
        super(Tuple5Impl.class, v0, v1, v2, v3, v4);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T0 get0() {
        return (T0) values[0];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T1 get1() {
        return (T1) values[1];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T2 get2() {
        return (T2) values[2];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T3 get3() {
        return (T3) values[3];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T4 get4() {
        return (T4) values[4];
    }

}
