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
package com.speedment.internal.core.stream.autoclose;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
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
import java.util.stream.BaseStream;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
/**
 * A Stream that will call its {@link #close() ) method automatically after
 * a terminating operation has been called.
 * <p>
 * N.B. The {@link #iterator() } {@link #spliterator() } methods will throw
 * an {@link UnsupportedOperationException } because otherwise the AutoClose
 * property cannot be guaranteed.
 *
 * @param <T> Stream type
 */
public class AutoClosingReferenceStream<T> extends AbstractAutoClosingStream implements Stream<T> {

    private final Stream<T> stream;

    public AutoClosingReferenceStream(Stream<T> stream) {
        this(stream, newSet());
    }

    AutoClosingReferenceStream(Stream<T> stream, Set<BaseStream<?, ?>> streamSet) {
        super(streamSet);
        this.stream = stream;
        streamSet.add(this);
    }

    @Override
    protected Stream<T> getStream() {
        return stream;
    }
    
    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        return wrap(stream.filter(predicate));
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return wrap(stream.map(mapper));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return wrap(stream.mapToInt(mapper));
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return wrap(stream.mapToLong(mapper));
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return wrap(stream.mapToDouble(mapper));
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return wrap(stream.flatMap(mapper));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return wrap(stream.flatMapToInt(mapper));
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return wrap(stream.flatMapToLong(mapper));
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return wrap(stream.flatMapToDouble(mapper));
    }

    @Override
    public Stream<T> distinct() {
        return wrap(stream.distinct());
    }

    @Override
    public Stream<T> sorted() {
        return wrap(stream.sorted());
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return wrap(stream.sorted(comparator));
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        return wrap(stream.peek(action));
    }

    @Override
    public Stream<T> limit(long maxSize) {
        return wrap(stream.limit(maxSize));
    }

    @Override
    public Stream<T> skip(long n) {
        return wrap(stream.skip(n));
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        finallyClose(() -> stream.forEach(action));
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        finallyClose(() -> stream.forEachOrdered(action));
    }

    @Override
    public Object[] toArray() {
        return finallyClose(() -> stream.toArray());
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return finallyClose(() -> stream.toArray(generator));
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return finallyClose(() -> stream.reduce(identity, accumulator));
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return finallyClose(() -> stream.reduce(accumulator));
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return finallyClose(() -> stream.reduce(identity, accumulator, combiner));
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return finallyClose(() -> stream.collect(supplier, accumulator, combiner));
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return finallyClose(() -> stream.collect(collector));
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return finallyClose(() -> stream.min(comparator));
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return finallyClose(() -> stream.max(comparator));
    }

    @Override
    public long count() {
        return finallyClose(() -> stream.count());
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return finallyClose(() -> stream.anyMatch(predicate));
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return finallyClose(() -> stream.allMatch(predicate));
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return finallyClose(() -> stream.noneMatch(predicate));
    }

    @Override
    public Optional<T> findFirst() {
        return finallyClose(() -> stream.findFirst());
    }

    @Override
    public Optional<T> findAny() {
        return finallyClose(() -> stream.findAny());
    }

    @Override
    public Iterator<T> iterator() {
        throw newUnsupportedException("iterator");
        //return stream.iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        throw newUnsupportedException("spliterator");
        //return stream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public Stream<T> sequential() {
        return wrap(stream.sequential());
    }

    @Override
    public Stream<T> parallel() {
        return wrap(stream.parallel());
    }

    @Override
    public Stream<T> unordered() {
        return wrap(stream.unordered());
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        return wrap(stream.onClose(closeHandler));
    }

    @Override
    public void close() {
        stream.close();
    }

}
