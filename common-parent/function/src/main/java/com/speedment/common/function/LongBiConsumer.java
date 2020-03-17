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

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Represents an operation that accepts two {@code long}-valued arguments and
 * returns no result.  This is the primitive type specialization of
 * {@link BiConsumer} for {@code long}.  Unlike most other functional interfaces,
 * {@code LongBiConsumer} is expected to operate via side-effects.
 *
 * <p>This is a functional interface whose functional method is 
 * {@link #accept(long, long)}.
 * 
 * @author Per Minborg
 * @since  1.0.1
 */
@FunctionalInterface
public interface LongBiConsumer {

    /**
     * Performs this operation on the given argument.
     * 
     * @param first   the first input argument
     * @param second  the second input argument
     */
    void accept(long first, long second);

    /**
     * Returns a composed {@code LongBiConsumer} that performs, in sequence, 
     * this operation followed by the {@code after} operation. If performing 
     * either operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after  the operation to perform after this operation
     * @return       a composed {@code LongBiConsumer} that performs in sequence 
     *               this operation followed by the {@code after} operation
     * 
     * @throws NullPointerException  if {@code after} is null
     */
    default LongBiConsumer andThen(LongBiConsumer after) {
        Objects.requireNonNull(after);
        return (long a, long b) -> { accept(a, b); after.accept(a, b); };
    }
}