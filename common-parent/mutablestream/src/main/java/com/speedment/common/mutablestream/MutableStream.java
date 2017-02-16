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
package com.speedment.common.mutablestream;

import java.util.Comparator;
import java.util.Iterator;
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
import com.speedment.common.mutablestream.action.DistinctAction;
import com.speedment.common.mutablestream.action.FilterAction;
import com.speedment.common.mutablestream.action.FlatMapAction;
import com.speedment.common.mutablestream.action.FlatMapToDoubleAction;
import com.speedment.common.mutablestream.action.FlatMapToIntAction;
import com.speedment.common.mutablestream.action.FlatMapToLongAction;
import com.speedment.common.mutablestream.action.LimitAction;
import com.speedment.common.mutablestream.action.MapAction;
import com.speedment.common.mutablestream.action.MapToDoubleAction;
import com.speedment.common.mutablestream.action.MapToIntAction;
import com.speedment.common.mutablestream.action.MapToLongAction;
import com.speedment.common.mutablestream.action.SkipAction;
import com.speedment.common.mutablestream.action.SortedAction;
import com.speedment.common.mutablestream.terminate.AllMatchTerminator;
import com.speedment.common.mutablestream.terminate.AnyMatchTerminator;
import com.speedment.common.mutablestream.terminate.CollectTerminator;
import com.speedment.common.mutablestream.terminate.CountTerminator;
import com.speedment.common.mutablestream.terminate.FindAnyTerminator;
import com.speedment.common.mutablestream.terminate.FindFirstTerminator;
import com.speedment.common.mutablestream.terminate.ForEachOrderedTerminator;
import com.speedment.common.mutablestream.terminate.ForEachTerminator;
import com.speedment.common.mutablestream.terminate.IteratorTerminator;
import com.speedment.common.mutablestream.terminate.MaxTerminator;
import com.speedment.common.mutablestream.terminate.MinTerminator;
import com.speedment.common.mutablestream.terminate.NoneMatchTerminator;
import com.speedment.common.mutablestream.terminate.ReduceTerminator;
import com.speedment.common.mutablestream.terminate.SpliteratorTerminator;
import com.speedment.common.mutablestream.terminate.ToArrayTerminator;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T>  the type of the stream
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class MutableStream<T> implements Stream<T> {

    public static <T> Stream<T> wrap(HasNext<T, Stream<T>> pipeline) {
        return internalWrap(pipeline, false);
    }
    
    static <T> Stream<T> internalWrap(HasNext<T, Stream<T>> pipeline, boolean parallel) {
        return new MutableStream<>(pipeline, parallel);
    }
    
    /**************************************************************************/
    /*                          Intermediate Actions                          */
    /**************************************************************************/

    @Override
    @SuppressWarnings("unchecked")
    public Stream<T> filter(Predicate<? super T> filter) {
        return internalWrap(pipeline.append(FilterAction.create(pipeline, (Predicate<T>) filter)), parallel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return internalWrap(pipeline.append(MapAction.create(pipeline, (Function<T, R>) mapper)), parallel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return MutableIntStream.internalWrap(pipeline.append(MapToIntAction.create(pipeline, (ToIntFunction<T>) mapper)), parallel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return MutableLongStream.internalWrap(pipeline.append(MapToLongAction.create(pipeline, (ToLongFunction<T>) mapper)), parallel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return MutableDoubleStream.internalWrap(pipeline.append(MapToDoubleAction.create(pipeline, (ToDoubleFunction<T>) mapper)), parallel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return internalWrap(pipeline.append(FlatMapAction.create(pipeline, (Function<T, Stream<R>>) mapper)), parallel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return MutableIntStream.internalWrap(pipeline.append(FlatMapToIntAction.create(pipeline, (Function<T, IntStream>) mapper)), parallel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return MutableLongStream.internalWrap(pipeline.append(FlatMapToLongAction.create(pipeline, (Function<T, LongStream>) mapper)), parallel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return MutableDoubleStream.internalWrap(pipeline.append(FlatMapToDoubleAction.create(pipeline, (Function<T, DoubleStream>) mapper)), parallel);
    }

    @Override
    public Stream<T> distinct() {
        return internalWrap(pipeline.append(DistinctAction.create(pipeline)), parallel);
    }

    @Override
    public Stream<T> sorted() {
        return internalWrap(pipeline.append(SortedAction.create(pipeline)), parallel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return internalWrap(pipeline.append(SortedAction.create(pipeline, (Comparator<T>) comparator)), parallel);
    }

    @Override
    public Stream<T> peek(Consumer<? super T> cnsmr) {
        // Mutable Streams can not be peeked inside since they might not be
        // resolved as a stream at all.
        return this;
    }

    @Override
    public Stream<T> limit(long limit) {
        return internalWrap(pipeline.append(LimitAction.create(pipeline, limit)), parallel);
    }

    @Override
    public Stream<T> skip(long skip) {
        return internalWrap(pipeline.append(SkipAction.create(pipeline, skip)), parallel);
    }
    
    /**************************************************************************/
    /*                          Terminating Actions                           */
    /**************************************************************************/

    @Override
    @SuppressWarnings("unchecked")
    public void forEach(Consumer<? super T> consumer) {
        pipeline.execute(ForEachTerminator.create(pipeline, parallel, (Consumer<T>) consumer));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void forEachOrdered(Consumer<? super T> consumer) {
        pipeline.execute(ForEachOrderedTerminator.create(pipeline, parallel, (Consumer<T>) consumer));
    }

    @Override
    public Object[] toArray() {
        return pipeline.execute(ToArrayTerminator.create(pipeline, parallel, Object[]::new));
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> instantiator) {
        return pipeline.execute(ToArrayTerminator.create(pipeline, parallel, instantiator));
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> combiner) {
        return pipeline.execute(ReduceTerminator.create(pipeline, parallel, identity, combiner));
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> combiner) {
        return Optional.ofNullable(pipeline.execute(ReduceTerminator.create(pipeline, parallel, combiner)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return pipeline.execute(ReduceTerminator.create(pipeline, parallel, identity, (BiFunction<U, T, U>) accumulator, combiner));
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return pipeline.execute(CollectTerminator.create(pipeline, parallel, supplier, accumulator, combiner));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return pipeline.execute(CollectTerminator.create(pipeline, parallel, (Collector<T, A, R>) collector));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<T> min(Comparator<? super T> comparator) {
        return pipeline.execute(MinTerminator.create(pipeline, parallel, (Comparator<T>) comparator));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<T> max(Comparator<? super T> comparator) {
        return pipeline.execute(MaxTerminator.create(pipeline, parallel, (Comparator<T>) comparator));
    }

    @Override
    public long count() {
        return pipeline.execute(CountTerminator.create(pipeline, parallel));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean anyMatch(Predicate<? super T> predicate) {
        return pipeline.execute(AnyMatchTerminator.create(pipeline, parallel, (Predicate<T>) predicate));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean allMatch(Predicate<? super T> predicate) {
        return pipeline.execute(AllMatchTerminator.create(pipeline, parallel, (Predicate<T>) predicate));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean noneMatch(Predicate<? super T> predicate) {
        return pipeline.execute(NoneMatchTerminator.create(pipeline, parallel, (Predicate<T>) predicate));
    }

    @Override
    public Optional<T> findFirst() {
        return pipeline.execute(FindFirstTerminator.create(pipeline, parallel));
    }

    @Override
    public Optional<T> findAny() {
        return pipeline.execute(FindAnyTerminator.create(pipeline, parallel));
    }

    @Override
    public Iterator<T> iterator() {
        return pipeline.execute(IteratorTerminator.create(pipeline, parallel));
    }

    @Override
    public Spliterator<T> spliterator() {
        return pipeline.execute(SpliteratorTerminator.create(pipeline, parallel));
    }

    /**************************************************************************/
    /*                   Inherited Methods from Base Stream                   */
    /**************************************************************************/

    @Override
    public boolean isParallel() {
        return parallel;
    }

    @Override
    public Stream<T> sequential() {
        return parallel ? internalWrap(pipeline, false) : this;
    }

    @Override
    public Stream<T> parallel() {
        return parallel ? this : internalWrap(pipeline, true);
    }

    @Override
    public Stream<T> unordered() {
        return this;
    }

    @Override
    public Stream<T> onClose(Runnable r) {
        throw new UnsupportedOperationException(
            "Close listeners are not supported by this stream implementation."
        );
    }

    @Override
    public void close() {
        // Do nothing since close listeners are not supported by this 
        // implementation of the stream API.
    }

    /**************************************************************************/
    /*                             Constructor                                */
    /**************************************************************************/
    
    private MutableStream(HasNext<T, Stream<T>> pipeline, boolean parallel) {
        this.pipeline = requireNonNull(pipeline);
        this.parallel = parallel;
    }
    
    private final HasNext<T, Stream<T>> pipeline;
    private final boolean parallel;
}