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
package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.expression.Expression;

import java.util.function.Function;

/**
 * Trait for expressions that has a {@link #compose(Function)} method similar to
 * {@link Function#compose(Function)}, but even primitive expressions might
 * implement this trait.
 *
 * @author Emil Forslund
 * @since  1.2.0
 */
public interface HasCompose<T> {

    /**
     * Returns a composed expression that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> the type of input to the {@code before} function, and to the
     *            composed expression
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     *         function and then applies this function
     * @throws NullPointerException if before is null
     */
    <V> Expression<V> compose(Function<? super V, ? extends T> before);
}
