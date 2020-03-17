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
package com.speedment.runtime.core.internal.stream.autoclose;

import com.speedment.runtime.core.stream.java9.Java9DoubleStreamAdditions;
import com.speedment.runtime.core.stream.java9.Java9StreamUtil;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 *
 * @author pemi
 */
final class AutoClosingDoubleStream
    extends AbstractAutoClosingStream<Double, DoubleStream>
    implements DoubleStream, Java9DoubleStreamAdditions {

    AutoClosingDoubleStream(
        final DoubleStream stream,
        final boolean allowStreamIteratorAndSpliterator
    ) {
        super(stream, allowStreamIteratorAndSpliterator);
    }

    @Override
    public DoubleStream filter(DoublePredicate predicate) {
        return wrap(stream().filter(predicate));
    }

    @Override
    public DoubleStream map(DoubleUnaryOperator mapper) {
        return wrap(stream().map(mapper));
    }

    @Override
    public <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper) {
        return wrap(stream().mapToObj(mapper));
    }

    @Override
    public IntStream mapToInt(DoubleToIntFunction mapper) {
        return wrap(stream().mapToInt(mapper));
    }

    @Override
    public LongStream mapToLong(DoubleToLongFunction mapper) {
        return wrap(stream().mapToLong(mapper));
    }

    @Override
    public DoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        return wrap(stream().flatMap(mapper));
    }

    @Override
    public DoubleStream distinct() {
        return wrap(stream().distinct());
    }

    @Override
    public DoubleStream sorted() {
        return wrap(stream().sorted());
    }

    @Override
    public DoubleStream peek(DoubleConsumer action) {
        return wrap(stream().peek(action));
    }

    @Override
    public DoubleStream limit(long maxSize) {
        return wrap(stream().limit(maxSize));
    }

    @Override
    public DoubleStream skip(long n) {
        return wrap(stream().skip(n));
    }

    @Override
    public DoubleStream takeWhile​(DoublePredicate predicate) {
        return wrap(Java9StreamUtil.takeWhile(stream(), predicate));
    }

    @Override
    public DoubleStream dropWhile(DoublePredicate predicate) {
        return wrap(Java9StreamUtil.dropWhile(stream(), predicate));
    }

    @Override
    public void forEach(DoubleConsumer action) {
        finallyClose(() -> stream().forEach(action));
    }

    @Override
    public void forEachOrdered(DoubleConsumer action) {
        finallyClose(() -> stream().forEachOrdered(action));
    }

    @Override
    public double[] toArray() {
        try {
            return stream().toArray();
        } finally {
            close();
        }
    }

    @Override
    public double reduce(double identity, DoubleBinaryOperator op) {
        return finallyClose(() -> stream().reduce(identity, op));
    }

    @Override
    public OptionalDouble reduce(DoubleBinaryOperator op) {
        return finallyClose(() -> stream().reduce(op));
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return finallyClose(() -> stream().collect(supplier, accumulator, combiner));
    }

    @Override
    public double sum() {
        return finallyClose(stream()::sum);
    }

    @Override
    public OptionalDouble min() {
        return finallyClose(stream()::min);
    }

    @Override
    public OptionalDouble max() {
        return finallyClose(stream()::max);
    }

    @Override
    public long count() {
        return finallyClose(stream()::count);
    }

    @Override
    public OptionalDouble average() {
        return finallyClose(stream()::average);
    }

    @Override
    public DoubleSummaryStatistics summaryStatistics() {
        return finallyClose(stream()::summaryStatistics);
    }

    @Override
    public boolean anyMatch(DoublePredicate predicate) {
        return finallyClose(() -> stream().anyMatch(predicate));
    }

    @Override
    public boolean allMatch(DoublePredicate predicate) {
        return finallyClose(() -> stream().allMatch(predicate));
    }

    @Override
    public boolean noneMatch(DoublePredicate predicate) {
        return finallyClose(() -> stream().noneMatch(predicate));
    }

    @Override
    public OptionalDouble findFirst() {
        return finallyClose(stream()::findFirst);
    }

    @Override
    public OptionalDouble findAny() {
        return finallyClose(stream()::findAny);
    }

    @Override
    public Stream<Double> boxed() {
        return wrap(stream().boxed());
    }

    @Override
    public DoubleStream sequential() {
        return wrap(stream().sequential());
    }

    @Override
    public DoubleStream parallel() {
        return wrap(stream().parallel());
    }

    @Override
    public PrimitiveIterator.OfDouble iterator() {
        if (isAllowStreamIteratorAndSpliterator()) {
            return stream().iterator();
        }
        throw newUnsupportedException("iterator");
    }

    @Override
    public Spliterator.OfDouble spliterator() {
        if (isAllowStreamIteratorAndSpliterator()) {
            return stream().spliterator();
        }
        throw newUnsupportedException("spliterator");
    }

    @Override
    public boolean isParallel() {
        return stream().isParallel();
    }

    @Override
    public DoubleStream unordered() {
        return wrap(stream().unordered());
    }

    @Override
    public DoubleStream onClose(Runnable closeHandler) {
        return wrap(stream().onClose(closeHandler));
    }


}
