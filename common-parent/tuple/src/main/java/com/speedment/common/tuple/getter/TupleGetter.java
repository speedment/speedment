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
package com.speedment.common.tuple.getter;

import com.speedment.common.tuple.Tuple;

import java.util.function.Function;

/**
 * Function that given a {@link Tuple} returns the element at the
 * {@link #index() ordinal} position.
 *
 * @author Emil Forslund
 * @param <T> Tuple type
 * @param <R> return type
 * @since  1.0.8
 */
public interface TupleGetter<T, R> extends Function<T, R> {

    /**
     * Returns the index of the tuple element that this getter returns. 
     *
     * @return  the index of the tuple element that this getter returns
     */
    int index();
}