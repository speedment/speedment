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

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.Set;
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
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class AutoClosingIntStream extends AbstractAutoClosingStream implements IntStream {

    private final IntStream stream;

    public AutoClosingIntStream(IntStream stream) {
        this(stream, newSet());
    }

    AutoClosingIntStream(IntStream stream, Set<BaseStream<?, ?>> streamSet) {
        super(streamSet);
        this.stream = stream;
    }

    @Override
    protected IntStream getStream() {
        return stream;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        return wrap(stream.filter(predicate));
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        return wrap(stream.map(mapper));
    }

    @Override
    public <U> Stream<U> mapToObj(IntFunction<? extends U> mapper) {
        return wrap(stream.mapToObj(mapper));
    }

    @Override
    public LongStream mapToLong(IntToLongFunction mapper) {
        return wrap(stream.mapToLong(mapper));
    }

    @Override
    public DoubleStream mapToDouble(IntToDoubleFunction mapper) {
        return wrap(stream.mapToDouble(mapper));
    }

    @Override
    public IntStream flatMap(IntFunction<? extends IntStream> mapper) {
        return wrap(stream.flatMap(mapper));
    }

    @Override
    public IntStream distinct() {
        return wrap(stream.distinct());
    }

    @Override
    public IntStream sorted() {
        return wrap(stream.sorted());
    }

    @Override
    public IntStream peek(IntConsumer action) {
        return wrap(stream.peek(action));
    }

    @Override
    public IntStream limit(long maxSize) {
        return wrap(stream.limit(maxSize));
    }

    @Override
    public IntStream skip(long n) {
        return wrap(stream.skip(n));
    }

    @Override
    public void forEach(IntConsumer action) {
        finallyClose(() -> stream.forEach(action));
    }

    @Override
    public void forEachOrdered(IntConsumer action) {
        finallyClose(() -> stream.forEachOrdered(action));
    }

    @Override
    public int[] toArray() {
        try {
            return stream.toArray();
        } finally {
            stream.close();
        }
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        return finallyClose(() -> stream.reduce(identity, op));
    }

    @Override
    public OptionalInt reduce(IntBinaryOperator op) {
        return finallyClose(() -> stream.reduce(op));
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return finallyClose(() -> stream.collect(supplier, accumulator, combiner));
    }

    @Override
    public int sum() {
        return finallyClose(() -> stream.sum());
    }

    @Override
    public OptionalInt min() {
        return finallyClose(() -> stream.min());
    }

    @Override
    public OptionalInt max() {
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
    public IntSummaryStatistics summaryStatistics() {
        return finallyClose(() -> stream.summaryStatistics());
    }

    @Override
    public boolean anyMatch(IntPredicate predicate) {
        return finallyClose(() -> stream.anyMatch(predicate));
    }

    @Override
    public boolean allMatch(IntPredicate predicate) {
        return finallyClose(() -> stream.allMatch(predicate));
    }

    @Override
    public boolean noneMatch(IntPredicate predicate) {
        return finallyClose(() -> stream.noneMatch(predicate));
    }

    @Override
    public OptionalInt findFirst() {
        return finallyClose(() -> stream.findFirst());
    }

    @Override
    public OptionalInt findAny() {
        return finallyClose(() -> stream.findAny());
    }

    @Override
    public LongStream asLongStream() {
        return finallyClose(() -> stream.asLongStream());
    }

    @Override
    public DoubleStream asDoubleStream() {
        return finallyClose(() -> stream.asDoubleStream());
    }

    @Override
    public Stream<Integer> boxed() {
        return wrap(stream.boxed());
    }

    @Override
    public IntStream sequential() {
        return wrap(stream.sequential());
    }

    @Override
    public IntStream parallel() {
        return wrap(stream.parallel());
    }

    @Override
    public PrimitiveIterator.OfInt iterator() {
        throw newUnsupportedException("iterator");
        //return stream.iterator();
    }

    @Override
    public Spliterator.OfInt spliterator() {
        throw newUnsupportedException("spliterator");
        //return stream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public IntStream unordered() {
        return wrap(stream.unordered());
    }

    @Override
    public IntStream onClose(Runnable closeHandler) {
        return wrap(stream.onClose(closeHandler));
    }

    @Override
    public void close() {
        stream.close();
    }

}
