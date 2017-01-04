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
package com.speedment.common.mutablestream.source;

import com.speedment.common.mutablestream.internal.source.EmptySourceImpl;
import com.speedment.common.mutablestream.HasNext;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @param <T>  the streamed type
 * @param <TS> the type of the stream itself
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface EmptySource<T, TS extends BaseStream<T, TS>> extends HasNext<T, TS> {
    
    static <T> EmptySource<T, Stream<T>> create() {
        @SuppressWarnings("unchecked")
        final Class<Stream<T>> streamType = (Class<Stream<T>>) (Class<?>) Stream.class;
        return new EmptySourceImpl<>(streamType);
    }
    
    static EmptySource<Integer, IntStream> createInt() {
        return new EmptySourceImpl<>(IntStream.class);
    }
    
    static EmptySource<Long, LongStream> createLong() {
        return new EmptySourceImpl<>(LongStream.class);
    }
    
    static EmptySource<Double, DoubleStream> createDouble() {
        return new EmptySourceImpl<>(DoubleStream.class);
    }
}