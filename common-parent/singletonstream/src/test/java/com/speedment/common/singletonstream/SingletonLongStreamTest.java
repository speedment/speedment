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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.LongStream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

final class SingletonLongStreamTest {

    private static final Double EPSILON = 1e-9;
    private static long ELEMENT = 1;
    private static long OTHER_ELEMENT = 2;
    private static LongUnaryOperator PLUS_ONE = i -> i + 1;

    private SingletonLongStream instance;
    private LongStream reference;
    private AtomicInteger cnt;

    @BeforeEach
    void setUp() {
        instance = (SingletonLongStream) SingletonLongStream.of(ELEMENT);
        reference = LongStream.of(ELEMENT);
        cnt = new AtomicInteger();
    }

    @Test
    void of() {
        final LongStream ss = SingletonLongStream.of(OTHER_ELEMENT);
        final List<Long> s = ss.boxed().collect(toList());
        assertEquals(singletonList(OTHER_ELEMENT), s);
    }

    @Test
    void filter() {
        assertEqualsApplying(s -> s.filter(i -> i == ELEMENT));
    }

    @Test
    void filter2() {
        assertEqualsApplying(s -> s.filter(i -> i != ELEMENT));
    }

    @Test
    void map() {
        assertEqualsApplying(s -> s.map(PLUS_ONE));
    }

    @Test
    void mapToObj() {
        assertEqualsApplying(s -> s.mapToObj(Long::valueOf).mapToLong(i -> i));
    }

    @Test
    void mapToInt() {
        assertEqualsApplying(s -> s.mapToInt(i -> (int) i).mapToLong(l -> (int) l));
    }

    @Test
    void mapToDouble() {
        final double actual = instance.mapToDouble(i -> i).findFirst().orElseThrow(NoSuchElementException::new);
        assertEquals(ELEMENT, actual, EPSILON);
    }

    @Test
    void flatMap() {
        assertEqualsApplying(s -> s.flatMap(i -> LongStream.of(0, i)));
    }

    @Test
    void distinct() {
        assertEqualsApplying(LongStream::distinct);
    }

    @Test
    void sorted() {
        assertEqualsApplying(LongStream::sorted);
    }

    @Test
    void peek() {
        instance.peek(i -> cnt.incrementAndGet()).forEach(i -> {});
        assertEquals(1, cnt.get());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, 1L, 2L})
    void limit(long limit) {
        assertEqualsApplying(s -> s.limit(limit));
    }

    @Test
    void limitNegative() {
        assertThrows(IllegalArgumentException.class, () -> instance.limit(-1));
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, 1L, 2L})
    void skip(long skip) {
        assertEqualsApplying(s -> s.skip(skip));
    }

    @Test
    void skipNegative() {
        assertThrows(IllegalArgumentException.class, () -> instance.skip(-1));
    }

    @Test
    void forEach() {
        instance.forEach(i -> cnt.incrementAndGet());
        assertEquals(1, cnt.get());
    }

    @Test
    void forEachOrdered() {
        instance.forEachOrdered(i -> cnt.incrementAndGet());
        assertEquals(1, cnt.get());
    }

    @Test
    void toArray() {
        assertArrayEquals(reference.toArray(), instance.toArray());
    }

    @Test
    void reduce() {
        assertEqualsReducing(s -> s.reduce(Long::sum));
    }

    @Test
    void testReduce() {
        assertEqualsReducing(s -> s.reduce(3, Long::sum));
    }

    @Test
    void collect() {
        assertEqualsReducing(s -> s.collect(ArrayList::new, ArrayList::add, ArrayList::addAll));
    }

    @Test
    void sum() {
        assertEqualsReducing(LongStream::sum);
    }

    @Test
    void min() {
        assertEqualsReducing(LongStream::min);
    }

    @Test
    void max() {
        assertEqualsReducing(LongStream::max);
    }

    @Test
    void count() {
        assertEqualsReducing(LongStream::count);
    }

    @Test
    void average() {
        assertEqualsReducing(LongStream::average);
    }

    @Test
    void summaryStatistics() {
        assertSummaryStatisticsEquals(reference.summaryStatistics(), instance.summaryStatistics());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void anyMatch(int value) {
        assertEqualsReducing(s -> s.anyMatch(i -> i == value));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void allMatch(int value) {
        assertEqualsReducing(s -> s.allMatch(i -> i == value));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void noneMatch(int value) {
        assertEqualsReducing(s -> s.noneMatch(i -> i == value));
    }

    @Test
    void findFirst() {
        assertEqualsReducing(LongStream::findFirst);
    }

    @Test
    void findAny() {
        assertEqualsReducing(LongStream::findAny);
    }


    @Test
    void asDoubleStream() {
        assertEqualsApplying(s -> s.asDoubleStream().mapToLong(l -> (int) l));
    }

    @Test
    void boxed() {
        assertEqualsApplying(s -> s.boxed().mapToLong(i -> i));
    }

    @Test
    void sequential() {
        assertEqualsApplying(LongStream::sequential);
    }

    @Test
    void parallel() {
        assertEqualsApplying(LongStream::parallel);
    }

    @Test
    void iteratorIntConsumer() {
        instance.iterator().forEachRemaining((LongConsumer) i -> cnt.incrementAndGet());
    }

    @Test
    void iteratorConsumer() {
        instance.iterator().forEachRemaining((Consumer<Long>) i -> cnt.incrementAndGet());
    }

    @Test
    void spliteratorIntConsumer() {
        instance.spliterator().forEachRemaining((LongConsumer) i -> cnt.incrementAndGet());
    }

    @Test
    void spliteratorConsumer() {
        instance.spliterator().forEachRemaining((Consumer<Long>) i -> cnt.incrementAndGet());
    }

    @Test
    void isParallel() {
        assertFalse(instance.isParallel());
        final LongStream newStream = instance.parallel();
        assertTrue(newStream.isParallel());
    }

    @Test
    @Disabled("https://github.com/speedment/speedment/issues/851")
    void mutableParallel() {
        instance.parallel();
        assertTrue(instance.isParallel());
    }

    @Test
    void unordered() {
        assertEqualsApplying(LongStream::unordered);
    }

    @Test
    @Disabled("https://github.com/speedment/speedment/issues/851")
    void mutableOnClose() {
        instance.onClose(cnt::incrementAndGet);
        instance.close();
        assertEquals(1, cnt.get());
    }

    @Test
    void onClose() {
        final LongStream newStream = instance.onClose(cnt::incrementAndGet);
        newStream.close();
        assertEquals(1, cnt.get());
    }

    @Test
    void close() {
        assertDoesNotThrow(instance::close);
    }

    @Test
    void takeWhile() {
        assertEquals(1, instance.takeWhile(i -> ELEMENT == i).count());
        assertEquals(0, instance.takeWhile(i -> OTHER_ELEMENT == i).count());
    }

    @Test
    void dropWhile() {
        assertEquals(0, instance.dropWhile(i -> ELEMENT == i).count());
        assertEquals(1, instance.dropWhile(i -> OTHER_ELEMENT == i).count());
    }

    private void assertEqualsApplying(UnaryOperator<LongStream> operator) {
        final LongStream expected = operator.apply(reference);
        final LongStream actual = operator.apply(instance);
        assertEquals(expected.isParallel(), actual.isParallel());
        assertEqualsReducing(expected, actual, s -> s.boxed().collect(toList()));
    }

    private <R> void assertEqualsReducing(Function<LongStream, R> reducer) {
        assertEqualsReducing(reference, instance, reducer);
    }

    private <R> void assertEqualsReducing(LongStream referenceToTest, LongStream instanceToTest, Function<LongStream, R> reducer) {
        final R expected = reducer.apply(referenceToTest);
        final R actual = reducer.apply(instanceToTest);
        if (expected instanceof Double && actual instanceof Double) {
            assertEquals((Double) expected, (Double) actual, EPSILON);
        }
        assertEquals(expected, actual);
    }

    private void assertSummaryStatisticsEquals(LongSummaryStatistics expected, LongSummaryStatistics actual) {
        assertEquals(expected.getCount(), actual.getCount());
        assertEquals(expected.getAverage(), actual.getAverage(), EPSILON);
        assertEquals(expected.getAverage(), actual.getAverage(), EPSILON);
    }

}