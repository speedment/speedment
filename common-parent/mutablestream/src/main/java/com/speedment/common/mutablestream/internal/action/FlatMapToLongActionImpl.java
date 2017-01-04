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
import com.speedment.common.mutablestream.action.Action;
import com.speedment.common.mutablestream.action.FlatMapToLongAction;
import java.util.stream.BaseStream;
import java.util.stream.LongStream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T>  the ingoing type
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class FlatMapToLongActionImpl<T> 
extends AbstractAction<T, Stream<T>, Long, LongStream> 
implements FlatMapToLongAction<T> {

    private final Function<T, LongStream> mapper;
    
    public FlatMapToLongActionImpl(HasNext<T, Stream<T>> previous, Function<T, LongStream> mapper) {
        super(previous);
        this.mapper = requireNonNull(mapper);
    }
    
    @Override
    public Function<T, LongStream> getMapper() {
        return mapper;
    }

    @Override
    public <Q, QS extends BaseStream<Q, QS>> HasNext<Q, QS> append(Action<Long, LongStream, Q, QS> next) {
        return next;
    }

    @Override
    public LongStream build(boolean parallel) {
        return previous().build(parallel).flatMapToLong(mapper);
    }
}