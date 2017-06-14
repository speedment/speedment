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
package com.speedment.common.mutablestream.internal.source;

import com.speedment.common.mutablestream.action.Action;
import com.speedment.common.mutablestream.source.EmptySource;
import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.terminate.Terminator;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T>  the streamed type
 * @param <TS> the type of the stream itself
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class EmptySourceImpl<T, TS extends BaseStream<T, TS>> implements EmptySource<T, TS> {
    
    private final Class<TS> streamType;
    
    public EmptySourceImpl(Class<TS> streamType) {
        this.streamType = requireNonNull(streamType);
    }

    @Override
    public <Q, QS extends BaseStream<Q, QS>> HasNext<Q, QS> append(Action<T, TS, Q, QS> next) {
        @SuppressWarnings("unchecked") // An empty stream is an empty stream.
        final HasNext<Q, QS> casted = (HasNext<Q, QS>) this;
        return casted;
    }

    @Override
    public <X> X execute(Terminator<T, TS, X> terminator) {
        return terminator.execute();
    }

    @Override
    public TS build(boolean parallel) {
        if (Stream.class.equals(streamType)) {
            @SuppressWarnings("unchecked")
            final TS result = (TS) Stream.empty();
            return result;
        } else if (IntStream.class.equals(streamType)) {
            @SuppressWarnings("unchecked")
            final TS result = (TS) IntStream.empty();
            return result;
        } else if (LongStream.class.equals(streamType)) {
            @SuppressWarnings("unchecked")
            final TS result = (TS) LongStream.empty();
            return result;
        } else if (DoubleStream.class.equals(streamType)) {
            @SuppressWarnings("unchecked")
            final TS result = (TS) DoubleStream.empty();
            return result;
        } else {
            throw new UnsupportedOperationException(
                "Unknown stream type '" + streamType.getName() + "'."
            );
        }
    }
}