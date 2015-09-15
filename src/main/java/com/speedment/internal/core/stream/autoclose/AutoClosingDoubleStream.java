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
package com.speedment.internal.core.stream.autoclose;

import static com.speedment.internal.core.stream.autoclose.AbstractAutoClosingStream.newUnsupportedException;
import java.util.DoubleSummaryStatistics;
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
public class AutoClosingDoubleStream extends AbstractAutoClosingStream implements DoubleStream {

    private final DoubleStream stream;

    public AutoClosingDoubleStream(DoubleStream stream) {
        this(stream, newSet());
    }

    AutoClosingDoubleStream(DoubleStream stream, Set<BaseStream<?, ?>> streamSet) {
        super(streamSet);
        this.stream = stream;
    }

    @Override
    protected DoubleStream getStream() {
        return stream;
    }

    @Override
    public DoubleStream filter(DoublePredicate predicate) {
        return wrap(stream.filter(predicate));
    }

    @Override
    public DoubleStream map(DoubleUnaryOperator mapper) {
        return wrap(stream.map(mapper));
    }

    @Override
    public <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper) {
        return wrap(stream.mapToObj(mapper));
    }

    @Override
    public IntStream mapToInt(DoubleToIntFunction mapper) {
        return wrap(stream.mapToInt(mapper));
    }

    @Override
    public LongStream mapToLong(DoubleToLongFunction mapper) {
        return wrap(stream.mapToLong(mapper));
    }

    @Override
    public DoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        return wrap(stream.flatMap(mapper));
    }

    @Override
    public DoubleStream distinct() {
        return wrap(stream.distinct());
    }

    @Override
    public DoubleStream sorted() {
        return wrap(stream.sorted());
    }

    @Override
    public DoubleStream peek(DoubleConsumer action) {
        return wrap(stream.peek(action));
    }

    @Override
    public DoubleStream limit(long maxSize) {
        return wrap(stream.limit(maxSize));
    }

    @Override
    public DoubleStream skip(long n) {
        return wrap(stream.skip(n));
    }

    @Override
    public void forEach(DoubleConsumer action) {
        finallyClose(() -> stream.forEach(action));
    }

    @Override
    public void forEachOrdered(DoubleConsumer action) {
        finallyClose(() -> stream.forEachOrdered(action));
    }

    @Override
    public double[] toArray() {
        try {
            return stream.toArray();
        } finally {
            close();
        }
    }

    @Override
    public double reduce(double identity, DoubleBinaryOperator op) {
        return stream.reduce(identity, op);
    }

    @Override
    public OptionalDouble reduce(DoubleBinaryOperator op) {
        return finallyClose(() -> stream.reduce(op));
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return finallyClose(() -> stream.collect(supplier, accumulator, combiner));
    }

    @Override
    public double sum() {
        return finallyClose(() -> stream.sum());
    }

    @Override
    public OptionalDouble min() {
        return finallyClose(() -> stream.min());
    }

    @Override
    public OptionalDouble max() {
        return finallyClose(() -> stream.max());
    }

    @Override
    public long count() {
        return finallyClose(() -> stream.count());
    }

    @Override
    public OptionalDouble average() {
        return finallyClose(() -> stream.average());
    }

    @Override
    public DoubleSummaryStatistics summaryStatistics() {
        return finallyClose(() -> stream.summaryStatistics());
    }

    @Override
    public boolean anyMatch(DoublePredicate predicate) {
        return finallyClose(() -> stream.anyMatch(predicate));
    }

    @Override
    public boolean allMatch(DoublePredicate predicate) {
        return finallyClose(() -> stream.allMatch(predicate));
    }

    @Override
    public boolean noneMatch(DoublePredicate predicate) {
        return finallyClose(() -> stream.noneMatch(predicate));
    }

    @Override
    public OptionalDouble findFirst() {
        return finallyClose(() -> stream.findFirst());
    }

    @Override
    public OptionalDouble findAny() {
        return finallyClose(() -> stream.findAny());
    }

    @Override
    public Stream<Double> boxed() {
        return finallyClose(() -> stream.boxed());
    }

    @Override
    public DoubleStream sequential() {
        return finallyClose(() -> stream.sequential());
    }

    @Override
    public DoubleStream parallel() {
        return finallyClose(() -> stream.parallel());
    }

    @Override
    public PrimitiveIterator.OfDouble iterator() {
        throw newUnsupportedException("iterator");
//            return stream.iterator();
    }

    @Override
    public Spliterator.OfDouble spliterator() {
        throw newUnsupportedException("spliterator");
//            return stream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public DoubleStream unordered() {
        return wrap(stream.unordered());
    }

    @Override
    public DoubleStream onClose(Runnable closeHandler) {
        return wrap(stream.onClose(closeHandler));
    }

    @Override
    public void close() {
        stream.close();
    }

}
