/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.util.stream;

import static com.speedment.internal.util.stream.SingletonUtil.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
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
        return LongStream.of(mapper.applyAsLong(element));
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            toStream().mapToDouble(mapper);
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
            return empty();
        }
        if (maxSize > 0) {
            return this;
        }
        throw new IllegalArgumentException(Long.toString(maxSize));
    }

    @Override
    public Stream<T> skip(long n) {
        if (n == 0) {
            return this;
        }
        if (n > 0) {
            return empty();
        }
        throw new IllegalArgumentException(Long.toString(n));
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        requireNonNull(action);
        action.accept(element);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
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
        requireNonNull(generator);
        final A[] result = generator.apply(SIZE);
        result[0] = (A) element;
        return result;
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        requireNonNull(accumulator);
        return accumulator.apply(identity, element);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        // Just one element so the accumulator is never called.
        return toOptional();
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        requireNonNull(accumulator);
        // the combiner is never used in a non-parallell stream
        return accumulator.apply(identity, element);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        requireNonNull(supplier);
        requireNonNull(accumulator);
        final R value = supplier.get();
        accumulator.accept(value, element);
        // the combiner is never used in a non-parallell stream
        return value;
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        requireNonNull(collector);
        final A value = collector.supplier().get();
        collector.accumulator().accept(value, element);
        // the combiner is never used in a non-parallell stream
        return collector.finisher().apply(value);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return toOptional();
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return toOptional();
    }

    @Override
    public long count() {
        return SIZE;
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return predicate.test(element);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return predicate.test(element);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return !predicate.test(element);
    }

    @Override
    public Optional<T> findFirst() {
        return toOptional();
    }

    @Override
    public Optional<T> findAny() {
        return toOptional();
    }

    @Override
    public Iterator<T> iterator() {
        return singletonIterator(element);
    }

    @Override
    public Spliterator<T> spliterator() {
        return singletonSpliterator(element);
    }

    @Override
    public boolean isParallel() {
        return false;
    }

    @Override
    public SingletonStream<T> sequential() {
        return this;
    }

    @Override
    public Stream<T> parallel() {
        return toStream().parallel();
    }

    @Override
    public Stream<T> unordered() {
        return this; // Todo: may convey to singletonSpliterator()
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        return toStream().onClose(closeHandler);
    }

    @Override
    public void close() {
        // do nothing. OnClose createa a real Stream
    }

    /**
     * Make the problem someone else's...
     *
     * @return A "real" Stream with the single element
     */
    private Stream<T> toStream() {
        return Stream.of(element);
    }

    private Optional<T> toOptional() {
        // if element is null, Optional will throw an NPE 
        // just as the standard Stream implementation does.
        return Optional.of(element);
    }

    private static <T> Stream<T> empty() {
        return Stream.empty();
    }

    private static <E> Iterator<E> singletonIterator(final E e) {
        return new Iterator<E>() {
            private boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public E next() {
                if (hasNext) {
                    hasNext = false;
                    return e;
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void forEachRemaining(Consumer<? super E> action) {
                Objects.requireNonNull(action);
                if (hasNext) {
                    action.accept(e);
                    hasNext = false;
                }
            }
        };
    }

    private static <T> Spliterator<T> singletonSpliterator(final T element) {
        return new Spliterator<T>() {
            long estimatedSize = SIZE;

            @Override
            public Spliterator<T> trySplit() {
                return null;
            }

            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                if (estimatedSize > 0) {
                    estimatedSize--;
                    consumer.accept(element);
                    return true;
                }
                return false;
            }

            @Override
            public void forEachRemaining(Consumer<? super T> consumer) {
                tryAdvance(consumer);
            }

            @Override
            public long estimateSize() {
                return estimatedSize;
            }

            @Override
            public int characteristics() {
                int value = (element != null) ? Spliterator.NONNULL : 0;
                // ORDERED can be derived from member flag 
                return value | Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.IMMUTABLE
                        | Spliterator.DISTINCT | Spliterator.ORDERED;
            }
        };
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
