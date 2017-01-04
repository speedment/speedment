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
import com.speedment.common.mutablestream.action.MapIntAction;
import com.speedment.common.mutablestream.action.SkipAction;
import java.util.function.IntFunction;
import java.util.stream.BaseStream;
import java.util.stream.IntStream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <R>  the outgoing type
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class MapIntActionImpl<R> 
extends AbstractAction<Integer, IntStream, R, Stream<R>> 
implements MapIntAction<R> {
    
    private final IntFunction<R> mapper;

    public MapIntActionImpl(HasNext<Integer, IntStream> previous, IntFunction<R> mapper) {
        super(previous);
        this.mapper = requireNonNull(mapper);
    }
    
    @Override
    public IntFunction<R> getMapper() {
        return mapper;
    }

    @Override
    public <Q, QS extends BaseStream<Q, QS>> HasNext<Q, QS> append(Action<R, Stream<R>, Q, QS> next) {
        // If the next action is a LimitAction, flip our positions so that it
        // is executed first.
        return next.ifLimit().map(limit -> {
            
            final LimitAction<Integer, IntStream> newLimit = LimitAction.create(previous(), limit.getLimit());
            final HasNext<Integer, IntStream> newPrevious = previous().append(newLimit);
            final MapIntAction<R> newMap = MapIntAction.create(newPrevious, mapper);
            
            @SuppressWarnings("unchecked")
            final HasNext<Q, QS> result = (HasNext<Q, QS>) newPrevious.append(newMap);
            
            return result;
            
        // If the next action is a SkipAction, flip our positions so that it
        // is executed first.
        }).orElseGet(() -> next.ifSkip().map(skip -> {
            
            final SkipAction<Integer, IntStream> newSkip = SkipAction.create(previous(), skip.getSkip());
            final HasNext<Integer, IntStream> newPrevious = previous().append(newSkip);
            final MapIntAction<R> newMap = MapIntAction.create(newPrevious, mapper);

            @SuppressWarnings("unchecked")
            final HasNext<Q, QS> result = (HasNext<Q, QS>) newPrevious.append(newMap);

            return result;

        // The next action is not either skip or limit. Return it so that the 
        // order is preserved.
        }).orElse(next));
    }

    @Override
    public Stream<R> build(boolean parallel) {
        return previous().build(parallel).mapToObj(mapper);
    }
}