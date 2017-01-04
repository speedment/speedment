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
package com.speedment.common.mutablestream.internal.action;

import java.util.stream.Stream;
import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.action.Action;
import com.speedment.common.mutablestream.action.SortedAction;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 *
 * @param <T>  the filtered type
 * @param <TS> the stream type
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class SortedActionImpl<T, TS extends BaseStream<T, TS>> 
extends AbstractAction<T, TS, T, TS> 
implements SortedAction<T, TS> {

    private final Comparator<T> comparator;
    
    public SortedActionImpl(HasNext<T, TS> previous, Comparator<T> comparator) {
        super(previous);
        this.comparator = comparator; // Can be null.
    }
    
    @Override
    public Optional<Comparator<T>> getComparator() {
        return Optional.ofNullable(comparator);
    }
    
    @Override
    public <Q, QS extends BaseStream<Q, QS>> HasNext<Q, QS> append(Action<T, TS, Q, QS> next) {
        return next;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TS build(boolean parallel) {
        final TS built = previous().build(parallel);
        if (built instanceof Stream<?>) {
            if (comparator == null) {
                return (TS) ((Stream<T>) built).sorted();
            } else {
                return (TS) ((Stream<T>) built).sorted(comparator);
            }
        } else if (built instanceof IntStream) {
            return (TS) ((IntStream) built).sorted();
        } else if (built instanceof LongStream) {
            return (TS) ((LongStream) built).sorted();
        } else if (built instanceof DoubleStream) {
            return (TS) ((DoubleStream) built).sorted();
        } else {
            throw new UnsupportedOperationException(
                "Built stream did not match any known stream interface."
            );
        }
    }
}