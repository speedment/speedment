/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.mutablestream.internal.terminate;

import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.terminate.ReduceTerminator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T>   the streamed type
 * @param <U>   the resulting type after the reduction
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class ReduceTerminatorImpl<T, U> 
extends AbstractTerminator<T, Stream<T>, U> 
implements ReduceTerminator<T, U> {
    
    private final U identity;
    private final BiFunction<U, T, U> accumulator;
    private final BinaryOperator<U> combiner;

    public ReduceTerminatorImpl(
            HasNext<T, Stream<T>> previous, 
            boolean parallel,
            U identity, 
            BiFunction<U, T, U> accumulator, 
            BinaryOperator<U> combiner) {
        
        super(previous, parallel);
        this.identity    = identity;     // Can be null.
        this.accumulator = accumulator;  // Can be null.
        this.combiner    = requireNonNull(combiner); 
    }
    
    @Override
    public Optional<U> getIdentity() {
        return Optional.ofNullable(identity);
    }
    
    @Override
    public Optional<BiFunction<U, T, U>> getAccumulator() {
        return Optional.ofNullable(accumulator);
    }
    
    @Override
    public BinaryOperator<U> getCombiner() {
        return combiner;
    }

    @Override
    @SuppressWarnings("unchecked")
    public U execute() {
        try (final Stream<T> stream = buildPrevious()) {
            if (accumulator == null) { // T and U are the same.
                final BinaryOperator<T> tCombiner = (BinaryOperator<T>) combiner;
                
                if (identity == null) {
                    return (U) stream.reduce(tCombiner);
                } else {
                    return (U) stream.reduce((T) identity, tCombiner);
                }
            } else {
                return stream.reduce(identity, accumulator, combiner);
            }
        }
    }
}