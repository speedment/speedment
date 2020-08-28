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
package com.speedment.common.singletonstream;

import static com.speedment.common.singletonstream.internal.SingletonUtil.MESSAGE_STREAM_CONSUMED;
import static com.speedment.common.singletonstream.internal.SingletonUtil.STRICT;
import static java.util.Objects.requireNonNull;

import com.speedment.common.singletonstream.internal.SingletonIterator;
import com.speedment.common.singletonstream.internal.SingletonSpliterator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * An implementation of a Stream that takes exactly one element as its source.
 *
 * This implementation supports optimized implementations of most terminal
 * operations and a some number of intermediate operations.
 * <p>
 * With STRICT mode <code>true</code>, un-optimized intermediate operations just
 * returns a wrapped standard Stream implementation. With STRICT mode
 * <code>false</code>, most intermediate operation will be executed eagerly and
 * will return another optimized SingletonStream.
 *
 * For performance reasons, the Stream does not throw an IllegalStateOperation
 * if methods are called after a terminal operation has been called on the
 * Stream. This could be implemented using a boolean value set by each
 * terminating op. All other ops could then assert this flag.
 *
 * @author Per Minborg
 * @param <T> the type of the stream elements
 */
public final class SingletonStream<T> implements Stream<T> {

    private final T element;

    private boolean parallel;

    private boolean consumed;

    private List<Runnable> closeHandlers;

    private SingletonStream(T element) {
        this.element = element;
    }

    /**
     * Creates and returns an optimized sequential {@link Stream} implementation
     * with only a single element.
     *
     * @param element the single element
     * @param <T> the type of the stream elements
     * @return a singleton sequential stream
     */
    public static <T> SingletonStream<T> of(T element) {
        return new SingletonStream<>(element);
    }

    /**
     * Creates and returns an optimized sequential {@link Stream} implementation
     * with only a single element if the element is non-null. if the element is
     * null, an empty Stream is returned.
     *
     * @param element the single element or null
     * @param <T> the type of the stream elements
     * @return a singleton sequential stream
     */
    public static <T> Stream<T> ofNullable(T element) {
        return element == null ? empty() : of(element);
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        if (STRICT) {
            return toStream().filter(predicate);
        }
        return predicate.test(element) ? this : empty();
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().map(mapper);
        }
        return of(mapper.apply(element));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().mapToInt(mapper);
        }
        return SingletonIntStream.of(mapper.applyAsInt(element));
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().mapToLong(mapper);
        }
        return SingletonLongStream.of(mapper.applyAsLong(element));
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().mapToDouble(mapper);
        }
        return DoubleStream.of(mapper.applyAsDouble(element));
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().flatMap(mapper);
        }
        @SuppressWarnings("unchecked")
        final Stream<R> result = (Stream<R>) mapper.apply(element);
        return result;
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().flatMapToInt(mapper);
        }
        return mapper.apply(element);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().flatMapToLong(mapper);
        }
        return mapper.apply(element);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().flatMapToDouble(mapper);
        }
        return mapper.apply(element);
    }

    @Override
    public SingletonStream<T> distinct() {
        return this;
    }

    @Override
    public SingletonStream<T> sorted() {
        return this;
    }

    @Override
    public SingletonStream<T> sorted(Comparator<? super T> comparator) {
        return this;
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        return toStream().peek(action);
    }

    @Override
    public Stream<T> limit(long maxSize) {
        if (maxSize == 0) {
            return empty(); // Optimization
        }
        if (maxSize > 0) {
            return this;
        }
        throw new IllegalArgumentException(Long.toString(maxSize));
    }

    @Override
    public Stream<T> skip(long n) {
        if (n == 0) {
            return this; // Optimization
        }
        if (n > 0) {
            return empty();
        }
        throw new IllegalArgumentException(Long.toString(n));
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        checkConsumed();
        requireNonNull(action);
        action.accept(element);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        checkConsumed();
        requireNonNull(action);
        action.accept(element);
    }

    @Override
    public Object[] toArray() {
        return toArray(Object[]::new);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        checkConsumed();
        requireNonNull(generator);
        final A[] result = generator.apply(1);
        result[0] = (A) element;
        return result;
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        checkConsumed();
        requireNonNull(accumulator);
        return accumulator.apply(identity, element);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        checkConsumed();
        // Just one element so the accumulator is never called.
        return toOptional();
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        checkConsumed();
        requireNonNull(accumulator);
        // the combiner is never used in a non-parallell stream
        return accumulator.apply(identity, element);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        checkConsumed();
        requireNonNull(supplier);
        requireNonNull(accumulator);
        final R value = supplier.get();
        accumulator.accept(value, element);
        // the combiner is never used in a non-parallell stream
        return value;
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        checkConsumed();
        requireNonNull(collector);
        final A value = collector.supplier().get();
        collector.accumulator().accept(value, element);
        // the combiner is never used in a non-parallell stream
        return collector.finisher().apply(value);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        checkConsumed();
        return toOptional();
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        checkConsumed();
        return toOptional();
    }

    @Override
    public long count() {
        checkConsumed();
        return 1;
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        checkConsumed();
        requireNonNull(predicate);
        return predicate.test(element);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        checkConsumed();
        requireNonNull(predicate);
        return predicate.test(element);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        checkConsumed();
        requireNonNull(predicate);
        return !predicate.test(element);
    }

    @Override
    public Optional<T> findFirst() {
        checkConsumed();
        return toOptional();
    }

    @Override
    public Optional<T> findAny() {
        checkConsumed();
        return toOptional();
    }

    @Override
    public Iterator<T> iterator() {
        checkConsumed();
        return new SingletonIterator<>(element);
    }

    @Override
    public Spliterator<T> spliterator() {
        checkConsumed();
        return new SingletonSpliterator<>(element);
    }

    @Override
    public boolean isParallel() {
        return parallel;
    }

    @Override
    public SingletonStream<T> sequential() {
        this.parallel = false;
        return this;
    }

    @Override
    public Stream<T> parallel() {
        this.parallel = true;
        return this;
    }

    @Override
    public Stream<T> unordered() {
        return this;
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        checkConsumed(false);

        if (closeHandler == null) {
            return this;
        }

        if (closeHandlers == null) {
            this.closeHandlers = new ArrayList<>();
        }

        closeHandlers.add(closeHandler);
        return this;
    }

    @Override
    public void close() {
        consumed = true;

        if (closeHandlers == null || closeHandlers.isEmpty()) {
            return;
        }

        closeHandlers.forEach(Runnable::run);
        closeHandlers = null;
    }

    /**
     * Make the problem someone else's...
     *
     * @return A "real" Stream with the single element
     */
    private Stream<T> toStream() {
        final Stream<T> stream = Stream.of(element);
        return parallel ? stream.parallel() : stream;
    }

    private Optional<T> toOptional() {
        // if element is null, Optional will throw an NPE 
        // just as the standard Stream implementation does.
        return Optional.of(element);
    }

    private static <T> Stream<T> empty() {
        return Stream.empty();
    }

    private void checkConsumed() {
        checkConsumed(true);
    }

    private void checkConsumed(boolean setConsumed) {
        if (consumed) {
            throw new IllegalStateException(MESSAGE_STREAM_CONSUMED);
        }

        if (setConsumed) {
            consumed = true;
        }
    }

    // Java 9 Stream features
    /**
     * Returns, if this stream is ordered, a stream consisting of the longest
     * prefix of elements taken from this stream that match the given predicate.
     *
     * @param predicate - a non-interfering, stateless predicate to apply to
     * elements to determine the longest prefix of elements.
     * @return the new stream
     */
    public Stream<T> takeWhile(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        if (predicate.test(element)) {
            return this;
        } else {
            return empty();
        }
    }

    /**
     * Returns, if this stream is ordered, a stream consisting of the remaining
     * elements of this stream after dropping the longest prefix of elements
     * that match the given predicate. Otherwise returns, if this stream is
     * unordered, a stream consisting of the remaining elements of this stream
     * after dropping a subset of elements that match the given predicate.
     *
     * @param predicate - a non-interfering, stateless predicate to apply to
     * elements to determine the longest prefix of elements.
     *
     * @return new new stream
     */
    public Stream<T> dropWhile(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        if (predicate.test(element)) {
            return empty();
        } else {
            return this;
        }
    }
}
