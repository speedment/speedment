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

import com.speedment.runtime.core.internal.stream.builder.action.longs.*;
import com.speedment.runtime.core.internal.stream.builder.pipeline.LongPipeline;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminator;
import com.speedment.runtime.core.stream.java9.Java9LongStreamAdditions;
import java.util.*;
import static java.util.Objects.requireNonNull;
import java.util.function.*;
import java.util.stream.*;

/**
 *
 * @author pemi
 */
public final class LongStreamBuilder extends AbstractStreamBuilder<LongStreamBuilder, LongPipeline> 
    implements LongStream, Java9LongStreamAdditions {

    LongStreamBuilder(final PipelineImpl<?> pipeline, final StreamTerminator streamTerminator, Set<BaseStream<?, ?>> streamSet) {
        super(pipeline, streamTerminator, streamSet);
        streamSet.add(this); // Add this new stream to the streamSet so it may be closed later
    }

    public LongStreamBuilder(final PipelineImpl<?> pipeline, final StreamTerminator streamTerminator) {
        this(pipeline, streamTerminator, newStreamSet());
    }
    
    @Override
    public LongStream filter(LongPredicate predicate) {
        requireNonNull(predicate);
        return append(new LongFilterAction(predicate));
    }

    @Override
    public LongStream map(LongUnaryOperator mapper) {
        requireNonNull(mapper);
        return append(new LongMapAction(mapper));
    }

    @Override
    public <U> Stream<U> mapToObj(LongFunction<? extends U> mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new ReferenceStreamBuilder<U>(pipeline, streamTerminator, streamSet).append(new LongMapToObjAction<>(mapper));
    }

    @Override
    public IntStream mapToInt(LongToIntFunction mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new IntStreamBuilder(pipeline, streamTerminator, streamSet).append(new LongMapToIntAction(mapper));
    }

    @Override
    public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new DoubleStreamBuilder(pipeline, streamTerminator, streamSet).append(new LongMapToDoubleAction(mapper));
    }

    @Override
    public LongStream flatMap(LongFunction<? extends LongStream> mapper) {
        requireNonNull(mapper);
        return append(new LongFlatMapAction(mapper));
    }

    @Override
    public LongStream distinct() {
        return append(new LongDistinctAction());
    }

    @Override
    public LongStream sorted() {
        return append(new LongSortedAction());
    }

    @Override
    public LongStream peek(LongConsumer action) {
        requireNonNull(action);
        return append(new LongPeekAction(action));
    }

    @Override
    public LongStream limit(long maxSize) {
        return append(new LongLimitAction(maxSize));
    }

    @Override
    public LongStream skip(long n) {
        return append(new LongSkipAction(n));
    }

    @Override
    public DoubleStream asDoubleStream() {
        assertNotLinkedOrConsumedAndSet();
        return new DoubleStreamBuilder(pipeline, streamTerminator, streamSet).append(new LongAsDoubleAction());
    }

    @Override
    public Stream<Long> boxed() {
        assertNotLinkedOrConsumedAndSet();
        return new ReferenceStreamBuilder<Long>(pipeline, streamTerminator, streamSet).append(new LongBoxedAction());
    }
    
        @Override
    public LongStream takeWhile(LongPredicate predicate) {
        return append(new LongTakeWhileAction(predicate));
    }

    @Override
    public LongStream dropWhile(LongPredicate predicate) {
        return append(new LongDropWhileAction(predicate));
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
    public void forEach(LongConsumer action) {
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
    public void forEachOrdered(LongConsumer action) {
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
    public long[] toArray() {
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
    public long reduce(long identity, LongBinaryOperator op) {
        requireNonNull(op);
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseLong(() -> streamTerminator.reduce(pipeline(), identity, op));
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
    public OptionalLong reduce(LongBinaryOperator op) {
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
    public <R> R collect(Supplier<R> supplier, ObjLongConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        requireNonNull(supplier);
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
    public long sum() {
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseLong(() -> streamTerminator.sum(pipeline()));
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
    public OptionalLong min() {
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
    public OptionalLong max() {
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
    public LongSummaryStatistics summaryStatistics() {
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
    public boolean anyMatch(LongPredicate predicate) {
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
    public boolean allMatch(LongPredicate predicate) {
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
    public boolean noneMatch(LongPredicate predicate) {
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
    public OptionalLong findFirst() {
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
    public OptionalLong findAny() {
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
    public PrimitiveIterator.OfLong iterator() {
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
    public Spliterator.OfLong spliterator() {
        assertNotLinkedOrConsumedAndSet();
        return streamTerminator.spliterator(pipeline());
    }

}
