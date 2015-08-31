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
package com.speedment.internal.util.tuple.impl;

import com.speedment.internal.util.tuple.Tuple3;

/**
 *
 * @author pemi
 * @param <T0> Type of 0:th argument
 * @param <T1> Type of 1:st argument
 * @param <T2> Type of 2:nd argument
 */
public final class Tuple3Impl<T0, T1, T2> extends AbstractTuple implements Tuple3<T0, T1, T2> {

    public Tuple3Impl(T0 v0, T1 v1, T2 v2) {
        super(Tuple3Impl.class, v0, v1, v2);
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

}
