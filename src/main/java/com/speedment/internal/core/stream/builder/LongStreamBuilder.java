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
package com.speedment.internal.core.stream.builder;

import com.speedment.internal.core.stream.builder.action.longs.LongAsDoubleAction;
import com.speedment.internal.core.stream.builder.action.longs.LongBoxedAction;
import com.speedment.internal.core.stream.builder.action.longs.LongDistinctAction;
import com.speedment.internal.core.stream.builder.action.longs.LongFilterAction;
import com.speedment.internal.core.stream.builder.action.longs.LongFlatMapAction;
import com.speedment.internal.core.stream.builder.action.longs.LongLimitAction;
import com.speedment.internal.core.stream.builder.action.longs.LongMapAction;
import com.speedment.internal.core.stream.builder.action.longs.LongMapToDoubleAction;
import com.speedment.internal.core.stream.builder.action.longs.LongMapToIntAction;
import com.speedment.internal.core.stream.builder.action.longs.LongMapToObjAction;
import com.speedment.internal.core.stream.builder.action.longs.LongPeekAction;
import com.speedment.internal.core.stream.builder.action.longs.LongSkipAction;
import com.speedment.internal.core.stream.builder.action.longs.LongSortedAction;
import com.speedment.internal.core.stream.builder.pipeline.PipelineImpl;
import com.speedment.internal.core.stream.builder.pipeline.LongPipeline;
import com.speedment.internal.core.stream.builder.streamterminator.StreamTerminator;
import java.util.LongSummaryStatistics;
import static java.util.Objects.requireNonNull;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public final class LongStreamBuilder extends AbstractStreamBuilder<LongStreamBuilder, LongPipeline> implements LongStream {

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
        return new ReferenceStreamBuilder<U>(pipeline, streamTerminator, streamSet).append(new LongMapToObjAction<>(mapper));
    }

    @Override
    public IntStream mapToInt(LongToIntFunction mapper) {
        requireNonNull(mapper);
        return new IntStreamBuilder(pipeline, streamTerminator, streamSet).append(new LongMapToIntAction(mapper));
    }

    @Override
    public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
        requireNonNull(mapper);
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
        return new DoubleStreamBuilder(pipeline, streamTerminator, streamSet).append(new LongAsDoubleAction());
    }

    @Override
    public Stream<Long> boxed() {
        return new ReferenceStreamBuilder<Long>(pipeline, streamTerminator, streamSet).append(new LongBoxedAction());
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
        return finallyClose(() -> streamTerminator.reduce(pipeline(), identity, op));
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
        return finallyClose(() -> streamTerminator.reduce(pipeline(), op));
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
        return finallyClose(() -> streamTerminator.collect(pipeline(), supplier, accumulator, combiner));
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
        return finallyClose(() -> streamTerminator.sum(pipeline()));
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
        return finallyClose(() -> streamTerminator.min(pipeline()));
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
        return finallyClose(() -> streamTerminator.max(pipeline()));
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
        return finallyClose(() -> streamTerminator.count(pipeline()));
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
        return finallyClose(() -> streamTerminator.average(pipeline()));
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
        return finallyClose(() -> streamTerminator.summaryStatistics(pipeline()));
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
        return finallyClose(() -> streamTerminator.anyMatch(pipeline(), predicate));
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
        return finallyClose(() -> streamTerminator.allMatch(pipeline(), predicate));
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
        return finallyClose(() -> streamTerminator.noneMatch(pipeline(), predicate));
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
        return finallyClose(() -> streamTerminator.findFirst(pipeline()));
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
        return finallyClose(() -> streamTerminator.findAny(pipeline()));
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
        return streamTerminator.spliterator(pipeline());
    }

}
