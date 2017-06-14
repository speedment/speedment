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

import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.action.Action;
import com.speedment.common.mutablestream.action.LimitAction;
import com.speedment.common.mutablestream.action.MapToDoubleAction;
import com.speedment.common.mutablestream.action.SkipAction;
import java.util.function.ToDoubleFunction;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T>  the ingoing type
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class MapToDoubleActionImpl<T> 
extends AbstractAction<T, Stream<T>, Double, DoubleStream> 
implements MapToDoubleAction<T> {
    
    private final ToDoubleFunction<T> mapper;

    public MapToDoubleActionImpl(HasNext<T, Stream<T>> previous, ToDoubleFunction<T> mapper) {
        super(previous);
        this.mapper = requireNonNull(mapper);
    }
    
    @Override
    public ToDoubleFunction<T> getMapper() {
        return mapper;
    }

    @Override
    public <Q, QS extends BaseStream<Q, QS>> HasNext<Q, QS> append(Action<Double, DoubleStream, Q, QS> next) {
        // If the next builder is a LimitBuilder, flip our positions so that it
        // is executed first.
        return next.ifLimit().map(limit -> {

            final LimitAction<T, Stream<T>> newLimit = LimitAction.create(previous(), limit.getLimit());
            final HasNext<T, Stream<T>> newPrevious = previous().append(newLimit);
            final MapToDoubleAction<T> newMap = MapToDoubleAction.create(newPrevious, mapper);
            
            @SuppressWarnings("unchecked")
            final HasNext<Q, QS> result = (HasNext<Q, QS>) newLimit.append(newMap);
            
            return result;
            
        // If the next builder is a SkipBuilder, flip our positions so that it
        // is executed first.
        }).orElseGet(() -> next.ifSkip().map(skip -> {

            final SkipAction<T, Stream<T>> newSkip = SkipAction.create(previous(), skip.getSkip());
            final HasNext<T, Stream<T>> newPrevious = previous().append(newSkip);
            final MapToDoubleAction<T> newMap = MapToDoubleAction.create(newPrevious, mapper);

            @SuppressWarnings("unchecked")
            final HasNext<Q, QS> result = (HasNext<Q, QS>) newSkip.append(newMap);

            return result;
            
        // The next builder is not either skip or limit. Return it so that the
        // order is preserved.
        }).orElse(next));
    }

    @Override
    public DoubleStream build(boolean parallel) {
        return previous().build(parallel).mapToDouble(mapper);
    }
}