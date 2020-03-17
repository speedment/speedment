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

import com.speedment.runtime.core.stream.java9.Java9LongStreamAdditions;
import com.speedment.runtime.core.stream.java9.Java9StreamUtil;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 *
 * @author pemi
 */
final class AutoClosingLongStream
    extends AbstractAutoClosingStream<Long, LongStream>
    implements LongStream, Java9LongStreamAdditions {

    AutoClosingLongStream(
        final LongStream stream,
        final boolean allowStreamIteratorAndSpliterator
    ) {
        super(stream, allowStreamIteratorAndSpliterator);
    }

    @Override
    public LongStream filter(LongPredicate predicate) {
        return wrap(stream().filter(predicate));
    }

    @Override
    public LongStream map(LongUnaryOperator mapper) {
        return wrap(stream().map(mapper));
    }

    @Override
    public <U> Stream<U> mapToObj(LongFunction<? extends U> mapper) {
        return wrap(stream().mapToObj(mapper));
    }

    @Override
    public IntStream mapToInt(LongToIntFunction mapper) {
        return wrap(stream().mapToInt(mapper));
    }

    @Override
    public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
        return wrap(stream().mapToDouble(mapper));
    }

    @Override
    public LongStream flatMap(LongFunction<? extends LongStream> mapper) {
        return wrap(stream().flatMap(mapper));
    }

    @Override
    public LongStream distinct() {
        return wrap(stream().distinct());
    }

    @Override
    public LongStream sorted() {
        return wrap(stream().sorted());
    }

    @Override
    public LongStream peek(LongConsumer action) {
        return wrap(stream().peek(action));
    }

    @Override
    public LongStream limit(long maxSize) {
        return wrap(stream().limit(maxSize));
    }

    @Override
    public LongStream skip(long n) {
        return wrap(stream().skip(n));
    }

    @Override
    public LongStream takeWhile​(LongPredicate predicate) {
        return wrap(Java9StreamUtil.takeWhile(stream(), predicate));
    }

    @Override
    public LongStream dropWhile​(LongPredicate predicate) {
        return wrap(Java9StreamUtil.dropWhile(stream(), predicate));
    }

    @Override
    public void forEach(LongConsumer action) {
        finallyClose(() -> stream().forEach(action));
    }

    @Override
    public void forEachOrdered(LongConsumer action) {
        finallyClose(() -> stream().forEachOrdered(action));
    }

    @Override
    public long[] toArray() {
        try {
            return stream().toArray();
        } finally {
            close();
        }
    }

    @Override
    public long reduce(long identity, LongBinaryOperator op) {
        return finallyClose(() -> stream().reduce(identity, op));
    }

    @Override
    public OptionalLong reduce(LongBinaryOperator op) {
        return finallyClose(() -> stream().reduce(op));
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjLongConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return finallyClose(() -> stream().collect(supplier, accumulator, combiner));
    }

    @Override
    public long sum() {
        return finallyClose(stream()::sum);
    }

    @Override
    public OptionalLong min() {
        return finallyClose(stream()::min);
    }

    @Override
    public OptionalLong max() {
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
    public LongSummaryStatistics summaryStatistics() {
        return finallyClose(stream()::summaryStatistics);
    }

    @Override
    public boolean anyMatch(LongPredicate predicate) {
        return finallyClose(() -> stream().anyMatch(predicate));
    }

    @Override
    public boolean allMatch(LongPredicate predicate) {
        return finallyClose(() -> stream().allMatch(predicate));
    }

    @Override
    public boolean noneMatch(LongPredicate predicate) {
        return finallyClose(() -> stream().noneMatch(predicate));
    }

    @Override
    public OptionalLong findFirst() {
        return finallyClose(stream()::findFirst);
    }

    @Override
    public OptionalLong findAny() {
        return finallyClose(stream()::findAny);
    }

    @Override
    public DoubleStream asDoubleStream() {
        return wrap(stream().asDoubleStream());
    }

    @Override
    public Stream<Long> boxed() {
        return wrap(stream().boxed());
    }

    @Override
    public LongStream sequential() {
        return wrap(stream().sequential());
    }

    @Override
    public LongStream parallel() {
        return wrap(stream().parallel());
    }

    @Override
    public PrimitiveIterator.OfLong iterator() {
        if (isAllowStreamIteratorAndSpliterator()) {
            return stream().iterator();
        }
        throw newUnsupportedException("iterator");
    }

    @Override
    public Spliterator.OfLong spliterator() {
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
    public LongStream unordered() {
        return wrap(stream().unordered());
    }

    @Override
    public LongStream onClose(Runnable closeHandler) {
        return wrap(stream().onClose(closeHandler));
    }

}
