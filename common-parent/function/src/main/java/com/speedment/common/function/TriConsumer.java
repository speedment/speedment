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
package com.speedment.common.function;

import java.util.function.Consumer;

/**
 * Represents a consumer that accepts three arguments. This is the three-arity 
 * specialization of {@link java.util.function.Consumer}.
 * 
 * @param <T>  the type of the first argument to the consumer
 * @param <U>  the type of the second argument to the consumer
 * @param <V>  the type of the third argument to the consumer
 *
 * @see Consumer
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t  the first consumer argument
     * @param u  the second consumer argument
     * @param v  the third consumer argument
     */
    void accept(T t, U u, V v);
}
