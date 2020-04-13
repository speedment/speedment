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
package com.speedment.runtime.core.internal.stream.builder;

import com.speedment.runtime.core.internal.stream.builder.action.ints.*;
import com.speedment.runtime.core.internal.stream.builder.pipeline.IntPipeline;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminator;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Objects.requireNonNull;
import com.speedment.runtime.core.stream.java9.Java9IntStreamAdditions;

/**
 *
 * @author pemi
 */
public final class IntStreamBuilder extends AbstractStreamBuilder<IntStreamBuilder, IntPipeline>
    implements IntStream, Java9IntStreamAdditions {

    IntStreamBuilder(final PipelineImpl<?> pipeline, final StreamTerminator streamTerminator, Set<BaseStream<?, ?>> streamSet) {
        super(pipeline, streamTerminator, streamSet);
        streamSet.add(this); // Add this new stream to the streamSet so it may be closed later
    }

    public IntStreamBuilder(final PipelineImpl<?> pipeline, final StreamTerminator streamTerminator) {
        this(pipeline, streamTerminator, newStreamSet());
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        requireNonNull(predicate);
        return append(new IntFilterAction(predicate));
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        requireNonNull(mapper);
        return append(new IntMapAction(mapper));
    }

    @Override
    public <U> Stream<U> mapToObj(IntFunction<? extends U> mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new ReferenceStreamBuilder<U>(pipeline, streamTerminator, streamSet).append(new IntMapToObjAction<>(mapper));
    }

    @Override
    public LongStream mapToLong(IntToLongFunction mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new LongStreamBuilder(pipeline, streamTerminator, streamSet).append(new IntMapToLongAction(mapper));
    }

    @Override
    public DoubleStream mapToDouble(IntToDoubleFunction mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new DoubleStreamBuilder(pipeline, streamTerminator, streamSet).append(new IntMapToDoubleAction(mapper));
    }

    @Override
    public IntStream flatMap(IntFunction<? extends IntStream> mapper) {
        requireNonNull(mapper);
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
        requireNonNull(action);
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
        assertNotLinkedOrConsumedAndSet();
        return new LongStreamBuilder(pipeline, streamTerminator, streamSet).append(new IntAsLongAction());
    }

    @Override
    public DoubleStream asDoubleStream() {
        assertNotLinkedOrConsumedAndSet();
        return new DoubleStreamBuilder(pipeline, streamTerminator, streamSet).append(new IntAsDoubleAction());
    }

    @Override
    public Stream<Integer> boxed() {
        assertNotLinkedOrConsumedAndSet();
        return new ReferenceStreamBuilder<Integer>(pipeline, streamTerminator, streamSet).append(new IntBoxedAction());
    }

    @Override
    public IntStream takeWhile(IntPredicate predicate) {
        return append(new IntTakeWhileAction(predicate));
    }

    @Override
    public IntStream dropWhile(IntPredicate predicate) {
        return append(new IntDropWhileAction(predicate));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public void forEach(IntConsumer action) {
        requireNonNull(action);
        assertNotLinkedOrConsumedAndSet();
        try {
            streamTerminator.forEach(pipeline(), action);
        } finally {
            close();
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public void forEachOrdered(IntConsumer action) {
        requireNonNull(action);
        assertNotLinkedOrConsumedAndSet();
        try {
            streamTerminator.forEachOrdered(pipeline(), action);
        } finally {
            close();
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public int[] toArray() {
        assertNotLinkedOrConsumedAndSet();
        try {
            return streamTerminator.toArray(pipeline());
        } finally {
            close();
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        requireNonNull(op);
        assertNotLinkedOrConsumedAndSet();
        try {
            return streamTerminator.reduce(pipeline(), identity, op);
        } finally {
            close();
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public OptionalInt reduce(IntBinaryOperator op) {
        requireNonNull(op);
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.reduce(pipeline(), op));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        requireNonNull(supplier);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.collect(pipeline(), supplier, accumulator, combiner));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public int sum() {
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseInt(() -> streamTerminator.sum(pipeline()));

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public OptionalInt min() {
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.min(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public OptionalInt max() {
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.max(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public long count() {
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseLong(() -> streamTerminator.count(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public OptionalDouble average() {
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.average(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public IntSummaryStatistics summaryStatistics() {
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.summaryStatistics(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public boolean anyMatch(IntPredicate predicate) {
        requireNonNull(predicate);
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseBoolean(() -> streamTerminator.anyMatch(pipeline(), predicate));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public boolean allMatch(IntPredicate predicate) {
        requireNonNull(predicate);
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseBoolean(() -> streamTerminator.allMatch(pipeline(), predicate));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public boolean noneMatch(IntPredicate predicate) {
        requireNonNull(predicate);
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseBoolean(() -> streamTerminator.noneMatch(pipeline(), predicate));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public OptionalInt findFirst() {
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.findFirst(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public OptionalInt findAny() {
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.findAny(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     * <p>
     * If you call this method, you <em>must</em> ensure to call the stream's 
     * {@link #close() } method or else resources may not be released properly.
     *
     * @return iterator
     */
    @Override
    public PrimitiveIterator.OfInt iterator() {
        assertNotLinkedOrConsumedAndSet();
        return streamTerminator.iterator(pipeline());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     * <p>
     * If you call this method, you <em>must</em> ensure to call the stream's 
     * {@link #close() } method or else resources may not be released properly.
     *
     */
    @Override
    public Spliterator.OfInt spliterator() {
        assertNotLinkedOrConsumedAndSet();
        return streamTerminator.spliterator(pipeline());
    }

}
