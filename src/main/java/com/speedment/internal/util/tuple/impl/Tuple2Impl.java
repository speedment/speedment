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

import com.speedment.internal.util.tuple.Tuple2;

/**
 *
 * @author pemi
 * @param <T0> Type of 0:th argument
 * @param <T1> Type of 1:st argument
 */
public final class Tuple2Impl<T0, T1> extends AbstractTuple implements Tuple2<T0, T1> {

    public Tuple2Impl(T0 e0, T1 e1) {
        super(Tuple2Impl.class, e0, e1);
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

}
