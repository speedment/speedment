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

import com.speedment.common.mutablestream.action.DistinctAction;
import com.speedment.common.mutablestream.action.FlatMapIntAction;
import com.speedment.common.mutablestream.action.IntFilterAction;
import com.speedment.common.mutablestream.action.LimitAction;
import com.speedment.common.mutablestream.action.MapIntAction;
import com.speedment.common.mutablestream.action.MapIntToDoubleAction;
import com.speedment.common.mutablestream.action.MapIntToIntAction;
import com.speedment.common.mutablestream.action.MapIntToLongAction;
import com.speedment.common.mutablestream.action.SkipAction;
import com.speedment.common.mutablestream.action.SortedAction;
import com.speedment.common.mutablestream.terminate.AllMatchIntTerminator;
import com.speedment.common.mutablestream.terminate.AnyMatchIntTerminator;
import com.speedment.common.mutablestream.terminate.AverageTerminator;
import com.speedment.common.mutablestream.terminate.CollectIntTerminator;
import com.speedment.common.mutablestream.terminate.CountTerminator;
import com.speedment.common.mutablestream.terminate.FindAnyIntTerminator;
import com.speedment.common.mutablestream.terminate.FindFirstIntTerminator;
import com.speedment.common.mutablestream.terminate.ForEachIntOrderedTerminator;
import com.speedment.common.mutablestream.terminate.ForEachIntTerminator;
import com.speedment.common.mutablestream.terminate.IntIteratorTerminator;
import com.speedment.common.mutablestream.terminate.IntSpliteratorTerminator;
import com.speedment.common.mutablestream.terminate.IntSummaryStatisticsTerminator;
import com.speedment.common.mutablestream.terminate.MaxIntTerminator;
import com.speedment.common.mutablestream.terminate.MinIntTerminator;
import com.speedment.common.mutablestream.terminate.NoneMatchIntTerminator;
import com.speedment.common.mutablestream.terminate.ReduceIntTerminator;
import com.speedment.common.mutablestream.terminate.ToIntArrayTerminator;
import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import com.speedment.common.mutablestream.terminate.SumIntTerminator;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @since   1.0.0
 */
public final class MutableIntStream implements IntStream {
    
    public static IntStream wrap(HasNext<Integer, IntStream> pipeline) {
        return internalWrap(pipeline, false);
    }
    
    static IntStream internalWrap(HasNext<Integer, IntStream> pipeline, boolean parallel) {
        return new MutableIntStream(pipeline, parallel);
    }
    
    /**************************************************************************/
    /*                          Intermediate Actions                          */
    /**************************************************************************/

    @Override
    public IntStream filter(IntPredicate filter) {
        return internalWrap(pipeline.append(IntFilterAction.create(pipeline, filter)), parallel);
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        return internalWrap(pipeline.append(MapIntToIntAction.create(pipeline, mapper)), parallel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> Stream<U> mapToObj(IntFunction<? extends U> mapper) {
        return MutableStream.internalWrap(pipeline.append(MapIntAction.create(pipeline, (IntFunction<U>) mapper)), parallel);
    }

    @Override
    public LongStream mapToLong(IntToLongFunction mapper) {
        return MutableLongStream.internalWrap(pipeline.append(MapIntToLongAction.create(pipeline, mapper)), parallel);
    }

    @Override
    public DoubleStream mapToDouble(IntToDoubleFunction mapper) {
        return MutableDoubleStream.internalWrap(pipeline.append(MapIntToDoubleAction.create(pipeline, mapper)), parallel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public IntStream flatMap(IntFunction<? extends IntStream> mapper) {
        return internalWrap(pipeline.append(FlatMapIntAction.create(pipeline, (IntFunction<IntStream>) mapper)), parallel);
    }

    @Override
    public IntStream distinct() {
        return internalWrap(pipeline.append(DistinctAction.create(pipeline)), parallel);
    }

    @Override
    public IntStream sorted() {
        return internalWrap(pipeline.append(SortedAction.create(pipeline)), parallel);
    }

    @Override
    public IntStream peek(IntConsumer ic) {
        // Mutable Streams can not be peeked inside since they might not be
        // resolved as a stream at all.
        return this;
    }

    @Override
    public IntStream limit(long limit) {
        return internalWrap(pipeline.append(LimitAction.create(pipeline, limit)), parallel);
    }

    @Override
    public IntStream skip(long skip) {
        return internalWrap(pipeline.append(SkipAction.create(pipeline, skip)), parallel);
    }

    @Override
    public LongStream asLongStream() {
        return mapToLong(i -> i);
    }

    @Override
    public DoubleStream asDoubleStream() {
        return mapToDouble(i -> i);
    }

    @Override
    public Stream<Integer> boxed() {
        return mapToObj(i -> i);
    }
    
    /**************************************************************************/
    /*                          Terminating Actions                           */
    /**************************************************************************/

    @Override
    public void forEach(IntConsumer action) {
        pipeline.execute(ForEachIntTerminator.create(pipeline, parallel, action));
    }

    @Override
    public void forEachOrdered(IntConsumer action) {
        pipeline.execute(ForEachIntOrderedTerminator.create(pipeline, parallel, action));
    }

    @Override
    public int[] toArray() {
        return pipeline.execute(ToIntArrayTerminator.create(pipeline, parallel));
    }

    @Override
    public int reduce(int initialValue, IntBinaryOperator combiner) {
        return pipeline.execute(ReduceIntTerminator.create(pipeline, parallel, initialValue, combiner)).getAsInt();
    }

    @Override
    public OptionalInt reduce(IntBinaryOperator combiner) {
        return pipeline.execute(ReduceIntTerminator.create(pipeline, parallel, combiner));
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator, BiConsumer<R, R> merger) {
        return pipeline.execute(CollectIntTerminator.create(pipeline, parallel, supplier, accumulator, merger));
    }

    @Override
    public int sum() {
        return pipeline.execute(SumIntTerminator.create(pipeline, parallel));
    }

    @Override
    public OptionalInt min() {
        return pipeline.execute(MinIntTerminator.create(pipeline, parallel));
    }

    @Override
    public OptionalInt max() {
        return pipeline.execute(MaxIntTerminator.create(pipeline, parallel));
    }

    @Override
    public long count() {
        return pipeline.execute(CountTerminator.create(pipeline, parallel));
    }

    @Override
    public OptionalDouble average() {
        return pipeline.execute(AverageTerminator.create(pipeline, parallel));
    }

    @Override
    public IntSummaryStatistics summaryStatistics() {
        return pipeline.execute(IntSummaryStatisticsTerminator.create(pipeline, parallel));
    }

    @Override
    public boolean anyMatch(IntPredicate predicate) {
        return pipeline.execute(AnyMatchIntTerminator.create(pipeline, parallel, predicate));
    }

    @Override
    public boolean allMatch(IntPredicate predicate) {
        return pipeline.execute(AllMatchIntTerminator.create(pipeline, parallel, predicate));
    }

    @Override
    public boolean noneMatch(IntPredicate predicate) {
        return pipeline.execute(NoneMatchIntTerminator.create(pipeline, parallel, predicate));
    }

    @Override
    public OptionalInt findFirst() {
        return pipeline.execute(FindFirstIntTerminator.create(pipeline, parallel));
    }

    @Override
    public OptionalInt findAny() {
        return pipeline.execute(FindAnyIntTerminator.create(pipeline, parallel));
    }

    @Override
    public PrimitiveIterator.OfInt iterator() {
        return pipeline.execute(IntIteratorTerminator.create(pipeline, parallel));
    }

    @Override
    public Spliterator.OfInt spliterator() {
        return pipeline.execute(IntSpliteratorTerminator.create(pipeline, parallel));
    }

    
    /**************************************************************************/
    /*                   Inherited Methods from Base Stream                   */
    /**************************************************************************/

    @Override
    public boolean isParallel() {
        return parallel;
    }

    @Override
    public IntStream sequential() {
        return parallel ? internalWrap(pipeline, false) : this;
    }

    @Override
    public IntStream parallel() {
        return parallel ? this : internalWrap(pipeline, true);
    }

    @Override
    public IntStream unordered() {
        return this;
    }

    @Override
    public IntStream onClose(Runnable r) {
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
    
    private MutableIntStream(HasNext<Integer, IntStream> pipeline, boolean parallel) {
        this.pipeline = requireNonNull(pipeline);
        this.parallel = parallel;
    }
    
    private final HasNext<Integer, IntStream> pipeline;
    private final boolean parallel;
}
