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

import com.speedment.internal.core.stream.builder.action.doubles.DoubleBoxedAction;
import com.speedment.internal.core.stream.builder.action.doubles.DoubleDistinctAction;
import com.speedment.internal.core.stream.builder.action.doubles.DoubleFilterAction;
import com.speedment.internal.core.stream.builder.action.doubles.DoubleFlatMapAction;
import com.speedment.internal.core.stream.builder.action.doubles.DoubleLimitAction;
import com.speedment.internal.core.stream.builder.action.doubles.DoubleMapAction;
import com.speedment.internal.core.stream.builder.action.doubles.DoubleMapToIntAction;
import com.speedment.internal.core.stream.builder.action.doubles.DoubleMapToLongAction;
import com.speedment.internal.core.stream.builder.action.doubles.DoubleMapToObjAction;
import com.speedment.internal.core.stream.builder.action.doubles.DoublePeekAction;
import com.speedment.internal.core.stream.builder.action.doubles.DoubleSkipAction;
import com.speedment.internal.core.stream.builder.action.doubles.DoubleSortedAction;
import com.speedment.internal.core.stream.builder.pipeline.PipelineImpl;
import com.speedment.internal.core.stream.builder.pipeline.DoublePipeline;
import com.speedment.internal.core.stream.builder.streamterminator.StreamTerminator;
import java.util.DoubleSummaryStatistics;
import static java.util.Objects.requireNonNull;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.ObjDoubleConsumer;
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
public final class DoubleStreamBuilder extends AbstractStreamBuilder<DoubleStreamBuilder, DoublePipeline> implements DoubleStream {

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
        return new ReferenceStreamBuilder<U>(pipeline, streamTerminator, streamSet).append(new DoubleMapToObjAction<>(mapper));
    }

    @Override
    public IntStream mapToInt(DoubleToIntFunction mapper) {
        requireNonNull(mapper);
        return new IntStreamBuilder(pipeline, streamTerminator, streamSet).append(new DoubleMapToIntAction(mapper));
    }

    @Override
    public LongStream mapToLong(DoubleToLongFunction mapper) {
        requireNonNull(mapper);
        return new LongStreamBuilder(pipeline, streamTerminator, streamSet).append(new DoubleMapToLongAction(mapper));
    }

    @Override
    public DoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        requireNonNull(mapper);
        return append(new DoubleFlatMapAction(mapper));
        //return new DoubleStreamBuilder(pipeline, streamTerminator).append(new DoubleFlatMapAction(mapper));
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
        return new ReferenceStreamBuilder<Double>(pipeline, streamTerminator, streamSet).append(new DoubleBoxedAction());
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
    public <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        requireNonNull(supplier);
        requireNonNull(accumulator);
        requireNonNull(combiner);
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
    public double sum() {
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
    public OptionalDouble min() {
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
    public OptionalDouble max() {
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
    public DoubleSummaryStatistics summaryStatistics() {
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
    public boolean anyMatch(DoublePredicate predicate) {
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
    public boolean allMatch(DoublePredicate predicate) {
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
    public boolean noneMatch(DoublePredicate predicate) {
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
    public OptionalDouble findFirst() {
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
    public OptionalDouble findAny() {
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
    public PrimitiveIterator.OfDouble iterator() {
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
        return streamTerminator.spliterator(pipeline());
    }

}
