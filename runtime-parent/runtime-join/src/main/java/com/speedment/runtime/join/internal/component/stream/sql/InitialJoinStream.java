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
package com.speedment.runtime.join.internal.component.stream.sql;

import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.stream.AutoClosingStream;
import com.speedment.runtime.core.stream.ComposeRunnableUtil;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static com.speedment.common.invariant.LongRangeUtil.requireNonNegative;
import static java.util.Objects.requireNonNull;

final class InitialJoinStream<T> implements Stream<T>/*, Java9StreamAdditions<T>*/ {

    private final AsynchronousQueryResult<T> asynchronousQueryResult;
    private final SqlInfo sqlInfo;
    private final boolean allowStreamIteratorAndSpliterator;
    private boolean parallel;
    private boolean unordered;
    private boolean consumed;
    private List<Runnable> closeHandlers;
    private long skip;
    private long limit;

    InitialJoinStream(
        final AsynchronousQueryResult<T> asynchronousQueryResult,
        final SqlInfo sqlInfo,
        final boolean allowStreamIteratorAndSpliterator
    ) {
        this.asynchronousQueryResult = requireNonNull(asynchronousQueryResult);
        this.sqlInfo = requireNonNull(sqlInfo);
        this.allowStreamIteratorAndSpliterator = allowStreamIteratorAndSpliterator;
        this.closeHandlers = new ArrayList<>();
        this.skip = 0;
        this.limit = Long.MAX_VALUE;
    }

/*    @Override
    public Stream<T> takeWhile(Predicate<? super T> predicate) {
        requireNonNull(predicate)
        return materialize().takeWhile(predicate);
    }

    @Override
    public Stream<T> dropWhile(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return materialize().dropWhile(predicate);
    }*/

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return materialize().filter(predicate);
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        requireNonNull(mapper);
        return materialize().map(mapper);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        requireNonNull(mapper);
        return materialize().mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        requireNonNull(mapper);
        return materialize().mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        requireNonNull(mapper);
        return materialize().mapToDouble(mapper);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        requireNonNull(mapper);
        return materialize().flatMap(mapper);
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        requireNonNull(mapper);
        return materialize().flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        requireNonNull(mapper);
        return materialize().flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        requireNonNull(mapper);
        return materialize().flatMapToDouble(mapper);
    }

    @Override
    public Stream<T> distinct() {
        return materialize().distinct();
    }

    // Is this really applicable for a Tuple stream?
    @Override
    public Stream<T> sorted() {
        return materialize().sorted();
    }

    // Todo: Render ORDER BY if possible
    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        requireNonNull(comparator);
        return materialize().sorted(comparator);
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        requireNonNull(action);
        return materialize().peek(action);
    }

    @Override
    public Stream<T> limit(long maxSize) {
        requireNonNegative(maxSize);
        limit = Math.min(limit, maxSize);
        return this;
    }

    @Override
    public Stream<T> skip(long n) {
        requireNonNegative(n);
        if (n == 0) {
            return this; // Noop
        }
        skip += n;
        return this;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        requireNonNull(action);
        materialize().forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        requireNonNull(action);
        materialize().forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return materialize().toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        requireNonNull(generator);
        return materialize().toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        requireNonNull(accumulator);
        return materialize().reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        requireNonNull(accumulator);
        return materialize().reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        requireNonNull(identity);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        return materialize().reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        requireNonNull(supplier);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        return materialize().collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        requireNonNull(collector);
        return materialize().collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        requireNonNull(comparator);
        return materialize().min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        requireNonNull(comparator);
        return materialize().max(comparator);
    }

    // Todo: Optimize this by doing a select count(*) from (sub select)
    @Override
    public long count() {
        return materialize().count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return materialize().anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return materialize().allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return materialize().noneMatch(predicate);
    }

    // Todo
    @Override
    public Optional<T> findFirst() {
        limit(1); // This will optimize SQL
        return materialize().findFirst();
    }

    // Todo
    @Override
    public Optional<T> findAny() {
        limit(1); // This will optimize SQL
        return materialize().findAny();
    }

    @Override
    public Iterator<T> iterator() {
        return materialize().iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return materialize().spliterator();
    }

    @Override
    public boolean isParallel() {
        return parallel;
    }

    @Override
    public Stream<T> sequential() {
        parallel = false;
        return this;
    }

    @Override
    public Stream<T> parallel() {
        parallel = true;
        return this;
    }

    @Override
    public Stream<T> unordered() {
        unordered = true;
        return this;
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        requireNonNull(closeHandler);
        closeHandlers.add(closeHandler);
        return this;
    }

    @Override
    public void close() {
        consumed = true;
        try {
            ComposeRunnableUtil.composedRunnable(closeHandlers);
        } finally {
            asynchronousQueryResult.close();
        }
    }


    private Stream<T> materialize() {
        assertNotConsumed();
        consumed = true;

        final boolean skipLimitInSql = sqlInfo.dbmsType().getSkipLimitSupport() == DbmsType.SkipLimitSupport.STANDARD;
        if (skipLimitInSql) {
            // Render SKIP and LIMIT to SQL
            @SuppressWarnings("unchecked")
            final List<Object> values = (List<Object>) asynchronousQueryResult.getValues();
            final String newSql = sqlInfo.dbmsType().applySkipLimit(asynchronousQueryResult.getSql(), values, skip, limit);
            asynchronousQueryResult.setSql(newSql);
        }

        Stream<T> result = AutoClosingStream.of(asynchronousQueryResult.stream(), allowStreamIteratorAndSpliterator)
            .onClose(this::close);

        if (parallel) {
            result = result.parallel();
        }
        if (unordered) {
            result = result.unordered();
        }
        if (!skipLimitInSql) {
            // Fall-back to just skip in stream if the
            // database type does not support skip/limit
            if (skip > 0) {
                result = result.skip(skip);
            }
            if (limit < Long.MAX_VALUE) {
                result = result.limit(limit);
            }
        }

        return result;
    }

    private void assertNotConsumed() {
        if (consumed) {
            throw new IllegalStateException("This stream has already been consumed");
        }
    }

}
