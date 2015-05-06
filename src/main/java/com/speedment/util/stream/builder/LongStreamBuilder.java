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

import com.speedment.util.stream.builder.action.longs.LongAsDoubleAction;
import com.speedment.util.stream.builder.action.longs.LongBoxedAction;
import com.speedment.util.stream.builder.action.longs.LongDistinctAction;
import com.speedment.util.stream.builder.action.longs.LongFilterAction;
import com.speedment.util.stream.builder.action.longs.LongFlatMapAction;
import com.speedment.util.stream.builder.action.longs.LongLimitAction;
import com.speedment.util.stream.builder.action.longs.LongMapAction;
import com.speedment.util.stream.builder.action.longs.LongMapToDoubleAction;
import com.speedment.util.stream.builder.action.longs.LongMapToIntAction;
import com.speedment.util.stream.builder.action.longs.LongMapToObjAction;
import com.speedment.util.stream.builder.action.longs.LongPeekAction;
import com.speedment.util.stream.builder.action.longs.LongSkipAction;
import com.speedment.util.stream.builder.action.longs.LongSortedAction;
import com.speedment.util.stream.builder.pipeline.BasePipeline;
import com.speedment.util.stream.builder.pipeline.IntPipeline;
import com.speedment.util.stream.builder.pipeline.LongPipeline;
import com.speedment.util.stream.builder.streamterminator.StreamTerminator;
import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator;
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
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class LongStreamBuilder extends BaseStreamBuilder<LongStreamBuilder, LongPipeline> implements LongStream {

    public LongStreamBuilder(final BasePipeline<?> pipeline, final StreamTerminator streamTerminator) {
        super(pipeline, streamTerminator);
    }

    @Override
    public LongStream filter(LongPredicate predicate) {
        return append(new LongFilterAction(predicate));
    }

    @Override
    public LongStream map(LongUnaryOperator mapper) {
        return append(new LongMapAction(mapper));
    }

    @Override
    public <U> Stream<U> mapToObj(LongFunction<? extends U> mapper) {
        return new ReferenceStreamBuilder<U>(pipeline, streamTerminator).append(new LongMapToObjAction<>(mapper));
    }

    @Override
    public IntStream mapToInt(LongToIntFunction mapper) {
        return new IntStreamBuilder(pipeline, streamTerminator).append(new LongMapToIntAction(mapper));
    }

    @Override
    public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
        return new DoubleStreamBuilder(pipeline, streamTerminator).append(new LongMapToDoubleAction(mapper));
    }

    @Override
    public LongStream flatMap(LongFunction<? extends LongStream> mapper) {
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
        return new DoubleStreamBuilder(pipeline, streamTerminator).append(new LongAsDoubleAction());
    }

    @Override
    public Stream<Long> boxed() {
        return new ReferenceStreamBuilder<Long>(pipeline, streamTerminator).append(new LongBoxedAction());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public void forEach(LongConsumer action) {
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
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public void forEachOrdered(LongConsumer action) {
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
     * closes the stream automatically when a terminating action is performed.
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
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public long reduce(long identity, LongBinaryOperator op) {
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
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public OptionalLong reduce(LongBinaryOperator op) {
        return finallyClose(streamTerminator.reduce(pipeline(), op));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public <R> R collect(Supplier<R> supplier, ObjLongConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return finallyClose(streamTerminator.collect(pipeline(), supplier, accumulator, combiner));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public long sum() {
        try {
            return streamTerminator.sum(pipeline());
        } finally {
            close();
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public OptionalLong min() {
        return finallyClose(streamTerminator.min(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public OptionalLong max() {
        return finallyClose(streamTerminator.max(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public long count() {
        try {
            return streamTerminator.count(pipeline());
        } finally {
            close();
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public OptionalDouble average() {
        return finallyClose(streamTerminator.average(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public LongSummaryStatistics summaryStatistics() {
        return finallyClose(streamTerminator.summaryStatistics(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public boolean anyMatch(LongPredicate predicate) {
        try {
            return streamTerminator.anyMatch(pipeline(), predicate);
        } finally {
            close();
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public boolean allMatch(LongPredicate predicate) {
        try {
            return streamTerminator.allMatch(pipeline(), predicate);
        } finally {
            close();
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public boolean noneMatch(LongPredicate predicate) {
        try {
            return streamTerminator.noneMatch(pipeline(), predicate);
        } finally {
            close();
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public OptionalLong findFirst() {
        return finallyClose(streamTerminator.findFirst(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminating action is performed.
     *
     */
    @Override
    public OptionalLong findAny() {
        return finallyClose(streamTerminator.findAny(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminating action is performed.
     *
     * @return iterator
     */
    @Override
    public PrimitiveIterator.OfLong iterator() {
        return finallyClose(streamTerminator.iterator(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminating action is performed.
     *
     * @return spliterator
     */
    @Override
    public Spliterator.OfLong spliterator() {
        return finallyClose(streamTerminator.spliterator(pipeline()));
    }

}
