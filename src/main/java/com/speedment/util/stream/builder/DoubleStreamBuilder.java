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

import com.speedment.util.stream.builder.action.doubles.DoubleDistinctAction;
import com.speedment.util.stream.builder.action.doubles.DoubleFilterAction;
import com.speedment.util.stream.builder.pipeline.BasePipeline;
import com.speedment.util.stream.builder.pipeline.DoublePipeline;
import com.speedment.util.stream.builder.streamterminator.StreamTerminator;
import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
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
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class DoubleStreamBuilder extends BaseStreamBuilder<DoubleStreamBuilder, DoublePipeline> implements DoubleStream {

    public DoubleStreamBuilder(final BasePipeline<?> pipeline, final StreamTerminator streamTerminator) {
        super(pipeline, streamTerminator);
    }

    @Override
    public DoubleStream filter(DoublePredicate predicate) {
        return append(new DoubleFilterAction(predicate));
    }

    @Override
    public DoubleStream map(DoubleUnaryOperator mapper) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IntStream mapToInt(DoubleToIntFunction mapper) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LongStream mapToLong(DoubleToLongFunction mapper) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DoubleStream distinct() {
        return append(new DoubleDistinctAction());
    }

    @Override
    public DoubleStream sorted() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DoubleStream peek(DoubleConsumer action) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DoubleStream limit(long maxSize) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DoubleStream skip(long n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     *
     */
    @Override
    public void forEach(DoubleConsumer action) {
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
    public void forEachOrdered(DoubleConsumer action) {
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
    public double[] toArray() {
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
    public double reduce(double identity, DoubleBinaryOperator op) {
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
    public OptionalDouble reduce(DoubleBinaryOperator op) {
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
    public <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator, BiConsumer<R, R> combiner) {
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
    public double sum() {
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
    public OptionalDouble min() {
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
    public OptionalDouble max() {
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
    public DoubleSummaryStatistics summaryStatistics() {
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
    public boolean anyMatch(DoublePredicate predicate) {
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
    public boolean allMatch(DoublePredicate predicate) {
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
    public boolean noneMatch(DoublePredicate predicate) {
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
    public OptionalDouble findFirst() {
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
    public OptionalDouble findAny() {
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
    public Stream<Double> boxed() {
        return streamTerminator.boxed(pipeline());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
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
     *
     */
    @Override
    public Spliterator.OfDouble spliterator() {
        return streamTerminator.spliterator(pipeline());
    }

}
