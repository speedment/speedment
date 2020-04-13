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

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.IntStream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

final class SingletonIntStreamTest {

    private static final Double EPSILON = 1e-9;
    private static int ELEMENT = 1;
    private static int OTHER_ELEMENT = 2;
    private static IntUnaryOperator PLUS_ONE = i -> i + 1;

    private SingletonIntStream instance;
    private IntStream reference;
    private AtomicInteger cnt;

    @BeforeEach
    void setUp() {
        instance = (SingletonIntStream) SingletonIntStream.of(ELEMENT);
        reference = IntStream.of(ELEMENT);
        cnt = new AtomicInteger();
    }

    @Test
    void of() {
        final IntStream ss = SingletonIntStream.of(OTHER_ELEMENT);
        final List<Integer> s = ss.boxed().collect(toList());
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
        assertEqualsApplying(s -> s.mapToObj(Integer::valueOf).mapToInt(i -> i));
    }

    @Test
    void mapToLong() {
        assertEqualsApplying(s -> s.mapToLong(i -> i).mapToInt(l -> (int) l));
    }

    @Test
    void mapToDouble() {
        final double actual = instance.mapToDouble(i -> i).findFirst().orElseThrow(NoSuchElementException::new);
        assertEquals(ELEMENT, actual, EPSILON);
    }

    @Test
    void flatMap() {
        assertEqualsApplying(s -> s.flatMap(i -> IntStream.of(0, i)));
    }

    @Test
    void distinct() {
        assertEqualsApplying(IntStream::distinct);
    }

    @Test
    void sorted() {
        assertEqualsApplying(IntStream::sorted);
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
        assertEqualsReducing(s -> s.reduce(Integer::sum));
    }

    @Test
    void testReduce() {
        assertEqualsReducing(s -> s.reduce(3, Integer::sum));
    }

    @Test
    void collect() {
        assertEqualsReducing(s -> s.collect(ArrayList::new, ArrayList::add, ArrayList::addAll));
    }

    @Test
    void sum() {
        assertEqualsReducing(IntStream::sum);
    }

    @Test
    void min() {
        assertEqualsReducing(IntStream::min);
    }

    @Test
    void max() {
        assertEqualsReducing(IntStream::max);
    }

    @Test
    void count() {
        assertEqualsReducing(IntStream::count);
    }

    @Test
    void average() {
        assertEqualsReducing(IntStream::average);
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
        assertEqualsReducing(IntStream::findFirst);
    }

    @Test
    void findAny() {
        assertEqualsReducing(IntStream::findAny);
    }

    @Test
    void asLongStream() {
        assertEqualsApplying(s -> s.asLongStream().mapToInt(l -> (int) l));
    }

    @Test
    void asDoubleStream() {
        assertEqualsApplying(s -> s.asDoubleStream().mapToInt(l -> (int) l));
    }

    @Test
    void boxed() {
        assertEqualsApplying(s -> s.boxed().mapToInt(i -> i));
    }

    @Test
    void sequential() {
        assertEqualsApplying(IntStream::sequential);
    }

    @Test
    void parallel() {
        assertEqualsApplying(IntStream::parallel);
    }

    @Test
    void iteratorIntConsumer() {
        instance.iterator().forEachRemaining((IntConsumer) i -> cnt.incrementAndGet());
    }

    @Test
    void iteratorConsumer() {
        instance.iterator().forEachRemaining((Consumer<Integer>) i -> cnt.incrementAndGet());
    }

    @Test
    void spliteratorIntConsumer() {
        instance.spliterator().forEachRemaining((IntConsumer) i -> cnt.incrementAndGet());
    }

    @Test
    void spliteratorConsumer() {
        instance.spliterator().forEachRemaining((Consumer<Integer>) i -> cnt.incrementAndGet());
    }

    @Test
    void isParallel() {
        assertFalse(instance.isParallel());
        final IntStream newStream = instance.parallel();
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
        assertEqualsApplying(IntStream::unordered);
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
        final IntStream newStream = instance.onClose(cnt::incrementAndGet);
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

    private void assertEqualsApplying(UnaryOperator<IntStream> operator) {
        final IntStream expected = operator.apply(reference);
        final IntStream actual = operator.apply(instance);
        assertEquals(expected.isParallel(), actual.isParallel());
        assertEqualsReducing(expected, actual, s -> s.boxed().collect(toList()));
    }

    private <R> void assertEqualsReducing(Function<IntStream, R> reducer) {
        assertEqualsReducing(reference, instance, reducer);
    }

    private <R> void assertEqualsReducing(IntStream referenceToTest, IntStream instanceToTest, Function<IntStream, R> reducer) {
        final R expected = reducer.apply(referenceToTest);
        final R actual = reducer.apply(instanceToTest);
        if (expected instanceof Double && actual instanceof Double) {
            assertEquals((Double) expected, (Double) actual, EPSILON);
        }
        assertEquals(expected, actual);
    }

    private void assertSummaryStatisticsEquals(IntSummaryStatistics expected, IntSummaryStatistics actual) {
        assertEquals(expected.getCount(), actual.getCount());
        assertEquals(expected.getAverage(), actual.getAverage(), EPSILON);
        assertEquals(expected.getAverage(), actual.getAverage(), EPSILON);
    }

}