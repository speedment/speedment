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
package com.speedment.runtime.core.internal.stream.autoclose;

import com.speedment.runtime.core.stream.java9.Java9StreamAdditions;
import com.speedment.runtime.core.stream.java9.Java9StreamUtil;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Objects.requireNonNull;

/**
 * A Stream that will call its {@link #close()} method automatically after
 * a terminating operation has been called.
 * <p>
 * N.B. The {@link #iterator()} {@link #spliterator()} methods will throw
 * an {@link UnsupportedOperationException} because otherwise the AutoClose
 * property cannot be guaranteed. This can be unlocked by setting the
 * allowStreamIteratorAndSpliterator flag
 *
 * @param <T>  Stream type
 * @author     Per Minborg
 */
public final class AutoClosingReferenceStream<T>
    extends AbstractAutoClosingStream<T, Stream<T>>
    implements Stream<T>, Java9StreamAdditions<T> {

    public AutoClosingReferenceStream(Stream<T> stream) {
        this(stream, false);
    }

    public AutoClosingReferenceStream(
        final Stream<T> stream,
        final boolean allowStreamIteratorAndSpliterator
    ) {
        super(stream, allowStreamIteratorAndSpliterator);
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        return wrap(stream().filter(predicate));
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return wrap(stream().map(mapper));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return wrap(stream().mapToInt(mapper));
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return wrap(stream().mapToLong(mapper));
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return wrap(stream().mapToDouble(mapper));
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return wrap(stream().flatMap(mapper));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return wrap(stream().flatMapToInt(mapper));
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return wrap(stream().flatMapToLong(mapper));
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return wrap(stream().flatMapToDouble(mapper));
    }

    @Override
    public Stream<T> distinct() {
        return wrap(stream().distinct());
    }

    @Override
    public Stream<T> sorted() {
        return wrap(stream().sorted());
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return wrap(stream().sorted(comparator));
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        return wrap(stream().peek(action));
    }

    @Override
    public Stream<T> limit(long maxSize) {
        return wrap(stream().limit(maxSize));
    }

    @Override
    public Stream<T> skip(long n) {
        return wrap(stream().skip(n));
    }

    @Override
    public Stream<T> takeWhile(Predicate<? super T> predicate) {
        return wrap(Java9StreamUtil.takeWhile(stream(), predicate));
    }

    @Override
    public Stream<T> dropWhile(Predicate<? super T> predicate) {
        return wrap(Java9StreamUtil.dropWhile(stream(), predicate));
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        finallyClose(() -> stream().forEach(action));
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        finallyClose(() -> stream().forEachOrdered(action));
    }

    @Override
    public Object[] toArray() {
        return finallyClose((Supplier<Object[]>) stream()::toArray);
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return finallyClose(() -> stream().toArray(generator));
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return finallyClose(() -> stream().reduce(identity, accumulator));
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return finallyClose(() -> stream().reduce(accumulator));
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return finallyClose(() -> stream().reduce(identity, accumulator, combiner));
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return finallyClose(() -> stream().collect(supplier, accumulator, combiner));
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return finallyClose(() -> stream().collect(collector));
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return finallyClose(() -> stream().min(comparator));
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return finallyClose(() -> stream().max(comparator));
    }

    @Override
    public long count() {
        return finallyClose(stream()::count);
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return finallyClose(() -> stream().anyMatch(predicate));
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return finallyClose(() -> stream().allMatch(predicate));
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return finallyClose(() -> stream().noneMatch(predicate));
    }

    @Override
    public Optional<T> findFirst() {
        return finallyClose(stream()::findFirst);
    }

    @Override
    public Optional<T> findAny() {
        return finallyClose(stream()::findAny);
    }

    @Override
    public Iterator<T> iterator() {
        if (isAllowStreamIteratorAndSpliterator()) {
            return stream().iterator();
        }
        throw newUnsupportedException("iterator");
    }

    @Override
    public Spliterator<T> spliterator() {
        if (isAllowStreamIteratorAndSpliterator()) {
            return stream().spliterator();
        }
        throw newUnsupportedException("spliterator");
    }

    @Override
    public boolean isParallel() {
        return stream().isParallel();
    }

    @Override
    public Stream<T> sequential() {
        return wrap(stream().sequential());
    }

    @Override
    public Stream<T> parallel() {
        return wrap(stream().parallel());
    }

    @Override
    public Stream<T> unordered() {
        return wrap(stream().unordered());
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        return wrap(stream().onClose(closeHandler));
    }

}
