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

import java.util.function.Function;
import java.util.stream.Stream;
import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.action.FlatMapAction;
import com.speedment.common.mutablestream.action.Action;
import java.util.stream.BaseStream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T>  the ingoing type
 * @param <R>  inner type of the outgoing stream
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class FlatMapActionImpl<T, R> 
extends AbstractAction<T, Stream<T>, R, Stream<R>> 
implements FlatMapAction<T, R> {

    private final Function<T, Stream<R>> mapper;
    
    public FlatMapActionImpl(HasNext<T, Stream<T>> previous, Function<T, Stream<R>> mapper) {
        super(previous);
        this.mapper = requireNonNull(mapper);
    }
    
    @Override
    public Function<T, Stream<R>> getMapper() {
        return mapper;
    }

    @Override
    public <Q, QS extends BaseStream<Q, QS>> HasNext<Q, QS> append(Action<R, Stream<R>, Q, QS> next) {
        return next;
    }

    @Override
    public Stream<R> build(boolean parallel) {
        return previous().build(parallel).flatMap(mapper);
    }
}