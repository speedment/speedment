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

import com.speedment.internal.core.stream.builder.action.ints.IntAsDoubleAction;
import com.speedment.internal.core.stream.builder.action.ints.IntAsLongAction;
import com.speedment.internal.core.stream.builder.action.ints.IntBoxedAction;
import com.speedment.internal.core.stream.builder.action.ints.IntDistinctAction;
import com.speedment.internal.core.stream.builder.action.ints.IntFilterAction;
import com.speedment.internal.core.stream.builder.action.ints.IntFlatMapAction;
import com.speedment.internal.core.stream.builder.action.ints.IntLimitAction;
import com.speedment.internal.core.stream.builder.action.ints.IntMapAction;
import com.speedment.internal.core.stream.builder.action.ints.IntMapToDoubleAction;
import com.speedment.internal.core.stream.builder.action.ints.IntMapToLongAction;
import com.speedment.internal.core.stream.builder.action.ints.IntMapToObjAction;
import com.speedment.internal.core.stream.builder.action.ints.IntPeekAction;
import com.speedment.internal.core.stream.builder.action.ints.IntSkipAction;
import com.speedment.internal.core.stream.builder.action.ints.IntSortedAction;
import com.speedment.internal.core.stream.builder.pipeline.IntPipeline;
import com.speedment.internal.core.stream.builder.pipeline.PipelineImpl;
import com.speedment.internal.core.stream.builder.streamterminator.StreamTerminator;
import java.util.IntSummaryStatistics;
import static java.util.Objects.requireNonNull;
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
public final class IntStreamBuilder extends AbstractStreamBuilder<IntStreamBuilder, IntPipeline> implements IntStream {

    public IntStreamBuilder(final PipelineImpl<?> pipeline, final StreamTerminator streamTerminator) {
        super(pipeline, streamTerminator);
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
        return new ReferenceStreamBuilder<U>(pipeline, streamTerminator).append(new IntMapToObjAction<>(mapper));
    }

    @Override
    public LongStream mapToLong(IntToLongFunction mapper) {
        requireNonNull(mapper);
        return new LongStreamBuilder(pipeline, streamTerminator).append(new IntMapToLongAction(mapper));
    }

    @Override
    public DoubleStream mapToDouble(IntToDoubleFunction mapper) {
        requireNonNull(mapper);
        return new DoubleStreamBuilder(pipeline, streamTerminator).append(new IntMapToDoubleAction(mapper));
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
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public void forEach(IntConsumer action) {
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
    public void forEachOrdered(IntConsumer action) {
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
    public int[] toArray() {
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
        return finallyClose(streamTerminator.reduce(pipeline(), op));
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
        return finallyClose(streamTerminator.collect(pipeline(), supplier, accumulator, combiner));
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
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public OptionalInt min() {
        return finallyClose(streamTerminator.min(pipeline()));
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
        return finallyClose(streamTerminator.max(pipeline()));
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
     * closes the stream automatically when a terminal operation is performed.
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
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public IntSummaryStatistics summaryStatistics() {
        return finallyClose(streamTerminator.summaryStatistics(pipeline()));
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
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public boolean allMatch(IntPredicate predicate) {
        requireNonNull(predicate);
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
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public boolean noneMatch(IntPredicate predicate) {
        requireNonNull(predicate);
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
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public OptionalInt findFirst() {
        return finallyClose(streamTerminator.findFirst(pipeline()));
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
        return finallyClose(streamTerminator.findAny(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     * @return iterator
     */
    @Override
    public PrimitiveIterator.OfInt iterator() {
        return finallyClose(streamTerminator.iterator(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     * @return spliterator
     */
    @Override
    public Spliterator.OfInt spliterator() {
        return finallyClose(streamTerminator.spliterator(pipeline()));
    }

}
