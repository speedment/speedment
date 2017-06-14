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
import com.speedment.common.mutablestream.action.LimitAction;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T>   the filtered type
 * @param <TS>  the main stream interface
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class LimitActionImpl<T, TS extends BaseStream<T, TS>> 
extends AbstractAction<T, TS, T, TS> 
implements LimitAction<T, TS> {

    private final long limit;
    
    public LimitActionImpl(HasNext<T, TS> previous, long limit) {
        super(previous);
        this.limit = requireNonNull(limit);
    }
    
    @Override
    public long getLimit() {
        return limit;
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
            return (TS) ((Stream<T>) built).limit(limit);
        } else if (built instanceof IntStream) {
            return (TS) ((IntStream) built).limit(limit);
        } else if (built instanceof LongStream) {
            return (TS) ((LongStream) built).limit(limit);
        } else if (built instanceof DoubleStream) {
            return (TS) ((DoubleStream) built).limit(limit);
        } else {
            throw new UnsupportedOperationException(
                "Built stream did not match any known stream interface."
            );
        }
    }
}