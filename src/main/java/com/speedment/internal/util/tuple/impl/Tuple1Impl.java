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

import com.speedment.internal.util.tuple.Tuple1;

/**
 *
 * @author pemi
 * @param <T0> Type of 0:th argument
 */
public final class Tuple1Impl<T0> extends AbstractTuple implements Tuple1<T0> {

    public Tuple1Impl(T0 e0) {
        super(Tuple1Impl.class, e0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T0 get0() {
        return (T0) values[0];
    }

}
