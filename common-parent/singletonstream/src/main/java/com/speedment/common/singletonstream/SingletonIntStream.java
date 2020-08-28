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
package com.speedment.common.singletonstream;

import static com.speedment.common.singletonstream.internal.SingletonUtil.MESSAGE_STREAM_CONSUMED;
import static com.speedment.common.singletonstream.internal.SingletonUtil.STRICT;
import static java.util.Objects.requireNonNull;

import com.speedment.common.singletonstream.internal.SingletonPrimitiveIteratorOfInt;
import com.speedment.common.singletonstream.internal.SingletonPrimitiveSpliteratorOfInt;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
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
 * An implementation of an IntStream that takes exactly one element as its
 * source.
 *
 * This implementation supports optimized implementations of most terminal
 * operations and a some number of intermediate operations. Un-optimized
 * operations just returns a wrapped standard IntStream implementation.
 *
 * For performance reasons, the IntStream does not throw an
 * IllegalStateOperation if methods are called after a terminal operation has
 * been called on the Stream. This could be implemented using a boolean value
 * set by each terminating op. All other ops could then assert this flag.
 *
 * @author Per Minborg
 * @since  1.0.0
 */
public class SingletonIntStream implements IntStream {

    private final int element;

    private boolean parallel;

    private boolean consumed;

    private List<Runnable> closeHandlers;

    private SingletonIntStream(int element) {
        this.element = element;
    }

    public static IntStream of(int element) {
        return new SingletonIntStream(element);
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        requireNonNull(predicate);
        if (STRICT) {
            return toStream().filter(predicate);
        }
        return predicate.test(element) ? this : empty();
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().map(mapper);
        }
        return of(mapper.applyAsInt(element));
    }

    @Override
    public <U> Stream<U> mapToObj(IntFunction<? extends U> mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().mapToObj(mapper);
        }
        return SingletonStream.of(mapper.apply(element));
    }

    @Override
    public LongStream mapToLong(IntToLongFunction mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().mapToLong(mapper);
        }
        return SingletonLongStream.of(mapper.applyAsLong(element));
    }

    @Override
    public DoubleStream mapToDouble(IntToDoubleFunction mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().mapToDouble(mapper);
        }
        return DoubleStream.of(mapper.applyAsDouble(element));
    }

    @Override
    public IntStream flatMap(IntFunction<? extends IntStream> mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().flatMap(mapper);
        }
        return mapper.apply(element);
    }

    @Override
    public IntStream distinct() {
        return this;
    }

    @Override
    public IntStream sorted() {
        return this;
    }

    @Override
    public IntStream peek(IntConsumer action) {
        requireNonNull(action);
        return toStream().peek(action);
    }

    @Override
    public IntStream limit(long maxSize) {
        if (maxSize == 0) {
            return empty();
        }
        if (maxSize > 0) {
            return this;
        }
        throw new IllegalArgumentException(Long.toString(maxSize));
    }

    @Override
    public IntStream skip(long n) {
        if (n == 0) {
            return this;
        }
        if (n > 0) {
            return empty();
        }
        throw new IllegalArgumentException(Long.toString(n));
    }

    @Override
    public void forEach(IntConsumer action) {
        checkConsumed();
        requireNonNull(action);
        action.accept(element);
    }

    @Override
    public void forEachOrdered(IntConsumer action) {
        checkConsumed();
        requireNonNull(action);
        action.accept(element);
    }

    @Override
    public int[] toArray() {
        checkConsumed();
        final int[] result = new int[1];
        result[0] = element;
        return result;
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        checkConsumed();
        requireNonNull(op);
        return op.applyAsInt(identity, element);
    }

    @Override
    public OptionalInt reduce(IntBinaryOperator op) {
        checkConsumed();
        // Just one element so the accumulator is never called.
        return toOptional();
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        checkConsumed();
        requireNonNull(supplier);
        requireNonNull(accumulator);
        final R value = supplier.get();
        accumulator.accept(value, element);
        // the combiner is never used in a non-parallell stream
        return value;
    }

    @Override
    public int sum() {
        checkConsumed();
        return element;
    }

    @Override
    public OptionalInt min() {
        checkConsumed();
        return toOptional();
    }

    @Override
    public OptionalInt max() {
        checkConsumed();
        return toOptional();
    }

    @Override
    public long count() {
        checkConsumed();
        return 1;
    }

    @Override
    public OptionalDouble average() {
        checkConsumed();
        return OptionalDouble.of(element);
    }

    @Override
    public IntSummaryStatistics summaryStatistics() {
        checkConsumed();
        final IntSummaryStatistics result = new IntSummaryStatistics();
        result.accept(element);
        return result;
    }

    @Override
    public boolean anyMatch(IntPredicate predicate) {
        checkConsumed();
        requireNonNull(predicate);
        return predicate.test(element);
    }

    @Override
    public boolean allMatch(IntPredicate predicate) {
        checkConsumed();
        requireNonNull(predicate);
        return predicate.test(element);
    }

    @Override
    public boolean noneMatch(IntPredicate predicate) {
        checkConsumed();
        requireNonNull(predicate);
        return !predicate.test(element);
    }

    @Override
    public OptionalInt findFirst() {
        checkConsumed();
        return toOptional();
    }

    @Override
    public OptionalInt findAny() {
        checkConsumed();
        return toOptional();
    }

    @Override
    public LongStream asLongStream() {
        return LongStream.of(element);
    }

    @Override
    public DoubleStream asDoubleStream() {
        return DoubleStream.of(element);
    }

    @Override
    public Stream<Integer> boxed() {
        return mapToObj(Integer::valueOf);
    }

    @Override
    public IntStream sequential() {
        this.parallel = false;
        return this;
    }

    @Override
    public IntStream parallel() {
        this.parallel = true;
        return this;
    }

    @Override
    public PrimitiveIterator.OfInt iterator() {
        checkConsumed();
        return new SingletonPrimitiveIteratorOfInt(element);
    }

    @Override
    public Spliterator.OfInt spliterator() {
        checkConsumed();
        return new SingletonPrimitiveSpliteratorOfInt(element);
    }

    @Override
    public boolean isParallel() {
        return parallel;
    }

    @Override
    public IntStream unordered() {
        return this;
    }

    @Override
    public IntStream onClose(Runnable closeHandler) {
        checkConsumed(false);

        if (closeHandler == null) {
            return this;
        }

        if (closeHandlers == null) {
            this.closeHandlers = new ArrayList<>();
        }

        closeHandlers.add(closeHandler);
        return this;
    }

    @Override
    public void close() {
        consumed = true;

        if (closeHandlers == null || closeHandlers.isEmpty()) {
            return;
        }

        closeHandlers.forEach(Runnable::run);
        closeHandlers = null;
    }

    private IntStream toStream() {
        final IntStream stream = IntStream.of(this.element);
        return parallel ? stream.parallel() : stream;
    }

    private OptionalInt toOptional() {
        // if element is null, Optional will throw an NPE 
        // just as the standard Stream implementation does.
        return OptionalInt.of(element);
    }

    private static IntStream empty() {
        return IntStream.empty();
    }

    private void checkConsumed() {
        checkConsumed(true);
    }

    private void checkConsumed(boolean setConsumed) {
        if (consumed) {
            throw new IllegalStateException(MESSAGE_STREAM_CONSUMED);
        }

        if (setConsumed) {
            consumed = true;
        }
    }

    // Java 9 Stream features
    /**
     * Returns, if this stream is ordered, a stream consisting of the longest
     * prefix of elements taken from this stream that match the given predicate.
     *
     * @param predicate - a non-interfering, stateless predicate to apply to
     * elements to determine the longest prefix of elements.
     * @return the new stream
     */
    public IntStream takeWhile(IntPredicate predicate) {
        requireNonNull(predicate);
        if (predicate.test(element)) {
            return this;
        } else {
            return empty();
        }
    }

    /**
     * Returns, if this stream is ordered, a stream consisting of the remaining
     * elements of this stream after dropping the longest prefix of elements
     * that match the given predicate. Otherwise returns, if this stream is
     * unordered, a stream consisting of the remaining elements of this stream
     * after dropping a subset of elements that match the given predicate.
     *
     * @param predicate - a non-interfering, stateless predicate to apply to
     * elements to determine the longest prefix of elements.
     *
     * @return new new stream
     */
    public IntStream dropWhile(IntPredicate predicate) {
        requireNonNull(predicate);
        if (predicate.test(element)) {
            return empty();
        } else {
            return this;
        }
    }

}
