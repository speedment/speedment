/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.stream;

import com.speedment.runtime.core.internal.stream.autoclose.AutoClosingReferenceStream;

import java.util.stream.Stream;

public interface AutoClosingStream<T> extends Stream<T> {

    /**
     * Creates an returns a new Stream that automatically will invoke its {@link Stream#close()} method
     * whenever a terminating operation is invoked and where the stream elements are obtained from the given
     * {@code stream}.
     *
     * @param <T> Stream type
     * @param stream to wrap
     *
     * @return a new Stream that automatically will invoke its {@link Stream#close()} method
     * whenever a terminating operation is invoked and where the stream elements are obtained from the given
     * {@code stream}
     *
     * @throws NullPointerException if the provided {@code stream} is {@code null}
     */
    static <T> Stream<T> of(Stream<T> stream) {
        return new AutoClosingReferenceStream<>(stream);
    }

    /**
     * Creates an returns a new Stream that automatically will invoke its {@link Stream#close()} method
     * whenever a terminating operation is invoked and where the stream elements are obtained from the given
     * {@code stream}.
     *
     * @param <T> Stream type
     * @param stream to wrap
     * @param allowStreamIteratorAndSpliterator if the methods {@link Stream#iterator()} and
     * {@link Stream#spliterator()} are allowed.
     *
     * @return a new Stream that automatically will invoke its {@link Stream#close()} method
     * whenever a terminating operation is invoked and where the stream elements are obtained from the given
     * {@code stream}
     *
     * @throws NullPointerException if the provided {@code stream} is {@code null}
     */
    static <T> Stream<T> of(
        final Stream<T> stream,
        final boolean allowStreamIteratorAndSpliterator
    ) {
        return new AutoClosingReferenceStream<>(stream, allowStreamIteratorAndSpliterator);
    }

}
