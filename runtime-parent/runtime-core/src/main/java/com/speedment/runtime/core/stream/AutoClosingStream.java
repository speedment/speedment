package com.speedment.runtime.core.stream;

import com.speedment.runtime.core.internal.stream.autoclose.AutoClosingReferenceStream;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

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
        return new AutoClosingReferenceStream<>(requireNonNull(stream));
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
        return new AutoClosingReferenceStream<>(requireNonNull(stream), allowStreamIteratorAndSpliterator);
    }

}
