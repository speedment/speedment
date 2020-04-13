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

import java.util.function.BiConsumer;

/**
 * Represents an operation that accepts an object-valued and a {@code
 * byte}-valued argument, and returns no result. This is the {@code (reference,
 * byte)} specialization of {@link BiConsumer}. Unlike most other functional
 * interfaces, {@code ObjByteConsumer} is expected to operate via side-effects.
 * <p>
 * This is a <a href="package-summary.html">functional interface</a> whose
 * functional method is {@link #accept(Object, byte)}.
 * 
 * @param <T> the type of the object argument to the operation
 * 
 * @author Emil Forslund
 * @since  1.0.3
 * 
 * @see BiConsumer
 */
@FunctionalInterface
public interface ObjByteConsumer<T> {
    
    /**
     * Performs this operation on the given arguments.
     * 
     * @param t     the first input argument
     * @param value the second input argument
     */
    void accept(T t, byte value);
}