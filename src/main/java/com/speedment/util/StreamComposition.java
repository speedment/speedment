/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.util;

import com.speedment.annotation.Api;
import static com.speedment.internal.core.stream.autoclose.AbstractAutoClosingStream.composedClose;
import com.speedment.internal.core.stream.autoclose.AutoClosingDoubleStream;
import com.speedment.internal.core.stream.autoclose.AutoClosingIntStream;
import com.speedment.internal.core.stream.autoclose.AutoClosingLongStream;
import com.speedment.internal.core.stream.autoclose.AutoClosingReferenceStream;
import com.speedment.internal.util.StaticClassUtil;
import java.util.function.Function;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import static com.speedment.internal.util.NullUtil.requireNonNulls;

/**
 *
 * @author pemi
 */
@Api(version = "2.1")
public class StreamComposition {

    /**
     * Creates a lazily concatenated Stream whose elements are are all the
     * elements of the streams in sequential order. The resulting Stream is
     * ordered if all of the input streams are ordered, and parallel if at least
     * one of the input streams are parallel. When a terminating operation is
     * called, all the streams are automatically and explicitly closed, whereby
     * their close handlers are invoked. AutoClosing is performed in sequential
     * order too. If a Stream throws an Exception, it is still guaranteed that
     * all Streams' close methods are called.
     *
     * <p>
     * Streams are processed one at at time. When one is exhausted, a new one
     * will start. Parallelism is only supported within the individual streams
     * provided.
     * <p>
     * N.B. a stream's close method may be called even through that stream never
     * produced any output, if a preceding stream threw an Exception.
     * <p>
     * N.B. an UnsupportedOperationException will be thrown if the concatenated
     * stream's {@link Stream#iterator()
     * } or {@link Stream#spliterator() } methods are called, because they could
     * potentially violate the resulting Stream's AutoClose property.
     * <p>
     * N.B. Use caution when constructing streams from repeated concatenation.
     * Accessing an element of a deeply concatenated stream can result in deep
     * call chains, or even {@code StackOverflowException}.
     *
     * @param <T> The type of stream elements
     * @param streams to concatenate
     * @return the concatenation of the input streams
     * @throws UnsupportedOperationException if the concatenated stream's {@link Stream#iterator()
     * } or {@link Stream#spliterator() } methods are called, because they could
     * potentially violate the resulting Stream's AutoClose property.
     */
    @SuppressWarnings("varargs")
    @SafeVarargs // Creating a Stream of an array is safe.
    public static <T> Stream<T> concatAndAutoClose(Stream<T>... streams) {
        requireNonNulls(streams);
        return configureAutoCloseStream(new AutoClosingReferenceStream<>(Stream.of(streams).flatMap(Function.identity())), streams);
    }

    @SuppressWarnings("varargs")
    @SafeVarargs // Creating a Stream of an array is safe.
    public static IntStream concatAndAutoClose(IntStream... streams) {
        requireNonNulls(streams);
        return configureAutoCloseStream(new AutoClosingIntStream(Stream.of(streams).flatMapToInt(Function.identity())), streams);
    }

    @SuppressWarnings("varargs")
    @SafeVarargs // Creating a Stream of an array is safe.
    public static LongStream concatAndAutoClose(LongStream... streams) {
        requireNonNulls(streams);
        return configureAutoCloseStream(new AutoClosingLongStream(Stream.of(streams).flatMapToLong(Function.identity())), streams);
    }

    @SuppressWarnings("varargs")
    @SafeVarargs // Creating a Stream of an array is safe.
    public static DoubleStream concatAndAutoClose(DoubleStream... streams) {
        requireNonNulls(streams);
        return configureAutoCloseStream(new AutoClosingDoubleStream(Stream.of(streams).flatMapToDouble(Function.identity())), streams);
    }

    @SuppressWarnings("varargs")
    @SafeVarargs // Creating a Stream of an array is safe.
    private static <T extends BaseStream<?, ?>> T configureAutoCloseStream(T concatStream, T... streams) {
        final boolean parallel = Stream.of(streams).anyMatch(T::isParallel);
        if (parallel) {
            concatStream.parallel();
        }
        concatStream.onClose(() -> composedClose(streams));
        return concatStream;
    }

    public StreamComposition() {
        StaticClassUtil.instanceNotAllowed(getClass());
    }

}
