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
package com.speedment.util.stream.builder;

import com.speedment.util.stream.builder.action.ints.IntAsDoubleAction;
import com.speedment.util.stream.builder.action.ints.IntAsLongAction;
import com.speedment.util.stream.builder.action.ints.IntBoxedAction;
import com.speedment.util.stream.builder.action.ints.IntDistinctAction;
import com.speedment.util.stream.builder.action.ints.IntFilterAction;
import com.speedment.util.stream.builder.action.ints.IntFlatMapAction;
import com.speedment.util.stream.builder.action.ints.IntLimitAction;
import com.speedment.util.stream.builder.action.ints.IntMapAction;
import com.speedment.util.stream.builder.action.ints.IntMapToDoubleAction;
import com.speedment.util.stream.builder.action.ints.IntMapToLongAction;
import com.speedment.util.stream.builder.action.ints.IntMapToObjAction;
import com.speedment.util.stream.builder.action.ints.IntPeekAction;
import com.speedment.util.stream.builder.action.ints.IntSkipAction;
import com.speedment.util.stream.builder.action.ints.IntSortedAction;
import com.speedment.util.stream.builder.pipeline.IntPipeline;
import com.speedment.util.stream.builder.pipeline.BasePipeline;
import com.speedment.util.stream.builder.streamterminator.StreamTerminator;
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

/**
 *
 * @author pemi
 */
public class IntStreamBuilder extends BaseStreamBuilder<IntStreamBuilder, IntPipeline> implements IntStream {

    public IntStreamBuilder(final BasePipeline<?> pipeline, final StreamTerminator streamTerminator) {
        super(pipeline, streamTerminator);
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        return append(new IntFilterAction(predicate));
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        return append(new IntMapAction(mapper));
    }

    @Override
    public <U> Stream<U> mapToObj(IntFunction<? extends U> mapper) {
        return new ReferenceStreamBuilder<U>(pipeline, streamTerminator).append(new IntMapToObjAction(mapper));
    }

    @Override
    public LongStream mapToLong(IntToLongFunction mapper) {
        return new LongStreamBuilder(pipeline, streamTerminator).append(new IntMapToLongAction(mapper));
    }

    @Override
    public DoubleStream mapToDouble(IntToDoubleFunction mapper) {
        return new DoubleStreamBuilder(pipeline, streamTerminator).append(new IntMapToDoubleAction(mapper));
    }

    @Override
    public IntStream flatMap(IntFunction<? extends IntStream> mapper) {
        return append(new IntFlatMapAction(mapper));
    }

    @Override
    public IntStream distinct() {
        return append(new IntDistinctAction());
    }

    @Override
    public IntStream sorted() {
        return append(new IntSortedAction());
    }

    @Override
    public IntStream peek(IntConsumer action) {
        return append(new IntPeekAction(action));
    }

    @Override
    public IntStream limit(long maxSize) {
        return append(new IntLimitAction(maxSize));
    }

    @Override
    public IntStream skip(long n) {
        return append(new IntSkipAction(n));
    }

    @Override
    public LongStream asLongStream() {
        return new LongStreamBuilder(pipeline, streamTerminator).append(new IntAsLongAction());
    }

    @Override
    public DoubleStream asDoubleStream() {
        return new DoubleStreamBuilder(pipeline, streamTerminator).append(new IntAsDoubleAction());
    }

    @Override
    public Stream<Integer> boxed() {
        return new ReferenceStreamBuilder<Integer>(pipeline, streamTerminator).append(new IntBoxedAction());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public void forEach(IntConsumer action) {
        streamTerminator.forEach(pipeline(), action);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public void forEachOrdered(IntConsumer action) {
        streamTerminator.forEachOrdered(pipeline(), action);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public int[] toArray() {
        return streamTerminator.toArray(pipeline());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        return streamTerminator.reduce(pipeline(), identity, op);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public OptionalInt reduce(IntBinaryOperator op) {
        return streamTerminator.reduce(pipeline(), op);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return streamTerminator.collect(pipeline(), supplier, accumulator, combiner);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public int sum() {
        return streamTerminator.sum(pipeline());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public OptionalInt min() {
        return streamTerminator.min(pipeline());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public OptionalInt max() {
        return streamTerminator.max(pipeline());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public long count() {
        return streamTerminator.count(pipeline());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public OptionalDouble average() {
        return streamTerminator.average(pipeline());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public IntSummaryStatistics summaryStatistics() {
        return streamTerminator.summaryStatistics(pipeline());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public boolean anyMatch(IntPredicate predicate) {
        return streamTerminator.anyMatch(pipeline(), predicate);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public boolean allMatch(IntPredicate predicate) {
        return streamTerminator.allMatch(pipeline(), predicate);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public boolean noneMatch(IntPredicate predicate) {
        return streamTerminator.noneMatch(pipeline(), predicate);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public OptionalInt findFirst() {
        return streamTerminator.findFirst(pipeline());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public OptionalInt findAny() {
        return streamTerminator.findAny(pipeline());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public PrimitiveIterator.OfInt iterator() {
        return streamTerminator.iterator(pipeline());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public Spliterator.OfInt spliterator() {
        return streamTerminator.spliterator(pipeline());
    }

}
