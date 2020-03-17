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

import com.speedment.runtime.core.internal.stream.builder.action.doubles.*;
import com.speedment.runtime.core.internal.stream.builder.pipeline.DoublePipeline;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminator;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Objects.requireNonNull;
import com.speedment.runtime.core.stream.java9.Java9DoubleStreamAdditions;

/**
 *
 * @author pemi
 */
public final class DoubleStreamBuilder extends AbstractStreamBuilder<DoubleStreamBuilder, DoublePipeline> 
    implements DoubleStream, Java9DoubleStreamAdditions {

    DoubleStreamBuilder(final PipelineImpl<?> pipeline, final StreamTerminator streamTerminator, Set<BaseStream<?, ?>> streamSet) {
        super(pipeline, streamTerminator, streamSet);
        streamSet.add(this); // Add this new stream to the streamSet so it may be closed later
    }

    public DoubleStreamBuilder(final PipelineImpl<?> pipeline, final StreamTerminator streamTerminator) {
        this(pipeline, streamTerminator, newStreamSet());
    }

    @Override
    public DoubleStream filter(DoublePredicate predicate) {
        requireNonNull(predicate);
        return append(new DoubleFilterAction(predicate));
    }

    @Override
    public DoubleStream map(DoubleUnaryOperator mapper) {
        requireNonNull(mapper);
        return append(new DoubleMapAction(mapper));
    }

    @Override
    public <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new ReferenceStreamBuilder<U>(pipeline, streamTerminator, streamSet).append(new DoubleMapToObjAction<>(mapper));
    }

    @Override
    public IntStream mapToInt(DoubleToIntFunction mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new IntStreamBuilder(pipeline, streamTerminator, streamSet).append(new DoubleMapToIntAction(mapper));
    }

    @Override
    public LongStream mapToLong(DoubleToLongFunction mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new LongStreamBuilder(pipeline, streamTerminator, streamSet).append(new DoubleMapToLongAction(mapper));
    }

    @Override
    public DoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return append(new DoubleFlatMapAction(mapper));
    }

    @Override
    public DoubleStream distinct() {
        return append(new DoubleDistinctAction());
    }

    @Override
    public DoubleStream sorted() {
        return append(new DoubleSortedAction());
    }

    @Override
    public DoubleStream peek(DoubleConsumer action) {
        requireNonNull(action);
        return append(new DoublePeekAction(action));
    }

    @Override
    public DoubleStream limit(long maxSize) {
        return append(new DoubleLimitAction(maxSize));
    }

    @Override
    public DoubleStream skip(long n) {
        return append(new DoubleSkipAction(n));
    }

    @Override
    public Stream<Double> boxed() {
        assertNotLinkedOrConsumedAndSet();
        return new ReferenceStreamBuilder<Double>(pipeline, streamTerminator, streamSet).append(new DoubleBoxedAction());
    }

    @Override
    public DoubleStream takeWhile​(DoublePredicate predicate) {
        return append(new DoubleTakeWhileAction(predicate));
    }

    @Override
    public DoubleStream dropWhile(DoublePredicate predicate) {
        return append(new DoubleDropWhileAction(predicate));
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
    public void forEach(DoubleConsumer action) {
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
    public void forEachOrdered(DoubleConsumer action) {
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
    public double[] toArray() {
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
    public double reduce(double identity, DoubleBinaryOperator op) {
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
    public OptionalDouble reduce(DoubleBinaryOperator op) {
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
    public <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator, BiConsumer<R, R> combiner) {
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
    public double sum() {
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseDouble(() -> streamTerminator.sum(pipeline()));
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
    public OptionalDouble min() {
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
    public OptionalDouble max() {
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
    public DoubleSummaryStatistics summaryStatistics() {
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
    public boolean anyMatch(DoublePredicate predicate) {
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
    public boolean allMatch(DoublePredicate predicate) {
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
    public boolean noneMatch(DoublePredicate predicate) {
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
    public OptionalDouble findFirst() {
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
    public OptionalDouble findAny() {
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
     */
    @Override
    public PrimitiveIterator.OfDouble iterator() {
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
    public Spliterator.OfDouble spliterator() {
        assertNotLinkedOrConsumedAndSet();
        return streamTerminator.spliterator(pipeline());
    }

}
