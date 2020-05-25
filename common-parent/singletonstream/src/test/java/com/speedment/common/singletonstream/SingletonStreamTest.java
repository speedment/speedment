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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.common.singletonstream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
final class SingletonStreamTest {

    private static final Double EPSILON = 1e-9;
    private static String ELEMENT = "A";
    private static String OTHER_ELEMENT = "B";

    private SingletonStream<String> instance;

    @BeforeEach
    void setUp() {
        instance = SingletonStream.of(ELEMENT);
    }

    @Test
    void testSome() {
        final List<Integer> expected = singletonList(0);
        final List<Integer> actual = instance.map(ELEMENT::indexOf).distinct().unordered().collect(toList());
        assertEquals(expected, actual);
    }

    /**
     * Test of of method, of class SingletonStream.
     */
    @Test
    void testOf() {
        final SingletonStream<String> ss = SingletonStream.of(OTHER_ELEMENT);
        final List<String> s = ss.collect(toList());
        assertEquals(singletonList(OTHER_ELEMENT), s);
    }

    /**
     * Test of ofNullable method, of class SingletonStream.
     */
    @Test
    void testOfNullableElement() {
        final Stream<String> ss = SingletonStream.ofNullable(OTHER_ELEMENT);
        final List<String> s = ss.collect(toList());
        assertEquals(singletonList(OTHER_ELEMENT), s);
    }

    @Test
    void testOfNullableNull() {
        final Stream<String> ss = SingletonStream.ofNullable(null);
        final List<String> s = ss.collect(toList());
        assertEquals(Collections.emptyList(), s);
    }

    /**
     * Test of filter method, of class SingletonStream.
     */
    @Test
    void testFilter() {
        assertEquals(1L, instance.filter(ELEMENT::equals).count());
        assertEquals(1L, instance.filter(ELEMENT::equals).filter(Objects::nonNull).count());
    }

    /**
     * Test of map method, of class SingletonStream.
     */
    @Test
    void testMap() {
        final Optional<String> binLen = instance.map(String::length).map(Integer::toBinaryString).findFirst();
        assertEquals(Optional.of("1"), binLen);

        Optional<String> r = SingletonStream.of("C").map(s -> null).map(a -> "Olle").findAny();
        assertEquals(Optional.of("Olle"), r);

    }

    /**
     * Test of mapToInt method, of class SingletonStream.
     */
    @Test
    void mapToInt() {
        assertEquals(1, instance.mapToInt(String::length).sum());
    }


    @Test
    void mapToLong() {
        assertEquals(1, instance.mapToLong(String::length).sum());
    }

    @Test
    void mapToDouble() {
        assertEquals(1, instance.mapToDouble(String::length).sum(), EPSILON);
    }

    @Test
    void flatMap() {
        assertEquals(1, instance.flatMap(s -> s.chars().boxed()).count());
    }

    @Test
    void flatMapToInt() {
        assertEquals(1, instance.flatMapToInt(s -> IntStream.range(0, s.length())).count());
    }

    @Test
    void flatMapToLong() {
        assertEquals(1, instance.flatMapToLong(s -> LongStream.range(0, s.length())).count());
    }

    @Test
    void flatMapToDouble() {
        assertEquals(2, instance.flatMapToDouble(s -> DoubleStream.of(0, s.length())).count());
    }


    @Test
    void distinct() {
        assertEquals(1, instance.distinct().count());
    }

    @Test
    void sorted() {
        assertEquals(1, instance.sorted().count());
    }

    @Test
    void sortedArg() {
        assertEquals(1, instance.sorted(Comparator.reverseOrder()).count());
    }

    @Test
    void peek() {
        final AtomicInteger cnt = new AtomicInteger();
        instance.peek(unused -> cnt.getAndIncrement()).forEach(blackHole());
        assertEquals(1, cnt.get());
    }

    @Test
    void limit() {
        assertEquals(1, instance.limit(1).count());
    }

    @Test
    void limit0() {
        assertEquals(0, instance.limit(0).count());
    }

    @Test
    void limitNegative() {
        assertThrows(IllegalArgumentException.class, () -> instance.limit(-1).count());
    }

    @Test
    void skip() {
        assertEquals(0, instance.skip(1).count());
    }

    @Test
    void skip0() {
        assertEquals(1, instance.skip(0).count());
    }

    @Test
    void skipNegative() {
        assertThrows(IllegalArgumentException.class, () -> instance.skip(-1).count());
    }


    @Test
    void forEach() {
        final List<String> strings = new ArrayList<>();
        instance.forEach(strings::add);
        assertEquals(1, strings.size());
    }

    @Test
    void forEachOrdered() {
        final List<String> strings = new ArrayList<>();
        instance.forEachOrdered(strings::add);
        assertEquals(1, strings.size());
    }

    @Test
    void toArray() {
        assertEquals(1, instance.toArray().length);
    }

    @Test
    void toArrayGenerator() {
        assertEquals(1, instance.toArray(String[]::new).length);
    }

    @Test
    void reduce() {
        assertEquals(Optional.of(ELEMENT), instance.reduce((a, b) -> a + b));
    }

    @Test
    void reduce2Arg() {
        assertEquals(OTHER_ELEMENT + ELEMENT, instance.reduce(OTHER_ELEMENT, (a, b) -> a + b));
    }

    @Test
    void reduce3Arg() {
        assertEquals(OTHER_ELEMENT + ELEMENT, instance.reduce(OTHER_ELEMENT, (a, b) -> a + b, (a, b) -> a + b));
    }

    @Test
    void collect() {
        assertEquals(ELEMENT, instance.collect(Collectors.joining()));
    }

    @Test
    void collect3Arg() {
        assertEquals(singletonList(ELEMENT), instance.collect(ArrayList::new, List::add, ArrayList::addAll));
    }

    @Test
    void min() {
        assertEquals(Optional.of(ELEMENT), instance.min(Comparator.naturalOrder()));
    }

    @Test
    void max() {
        assertEquals(Optional.of(ELEMENT), instance.max(Comparator.naturalOrder()));
    }

    @Test
    void count() {
        assertEquals(1, instance.count());
    }

    @Test
    void anyMatch() {
        assertTrue(instance.anyMatch(ELEMENT::equals));
        assertFalse(SingletonStream.of(OTHER_ELEMENT).anyMatch(ELEMENT::equals));
    }

    @Test
    void allMatch() {
        assertTrue(instance.allMatch(ELEMENT::equals));
        assertFalse(SingletonStream.of(OTHER_ELEMENT).allMatch(ELEMENT::equals));
    }

    @Test
    void noneMatch() {
        assertFalse(instance.noneMatch(ELEMENT::equals));
        assertTrue(SingletonStream.of(OTHER_ELEMENT).noneMatch(ELEMENT::equals));
    }

    @Test
    void findFirst() {
        assertEquals(Optional.of(ELEMENT), instance.findFirst());
    }

    @Test
    void findAny() {
        assertEquals(Optional.of(ELEMENT), instance.findAny());
    }

    @Test
    void iterator() {
        final AtomicInteger cnt = new AtomicInteger();
        instance.iterator().forEachRemaining(s -> cnt.incrementAndGet());
        assertEquals(1, cnt.get());
    }

    @Test
    void iteratorNextAndThenForEachRemaining() {
        final AtomicInteger cnt = new AtomicInteger();
        final Iterator<String> iterator = instance.iterator();
        iterator.next();
        iterator.forEachRemaining(s -> cnt.incrementAndGet());
        assertEquals(0, cnt.get());
    }

    @Test
    void iteratorNext() {
        final AtomicInteger cnt = new AtomicInteger();
        final Iterator<String> iterator = instance.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            cnt.incrementAndGet();
        }
        assertEquals(1, cnt.get());
    }

    @Test
    void iteratorOutOfRange() {
        final Iterator<String> iterator = instance.iterator();
        String element = iterator.next();
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratorRemove() {
        assertThrows(UnsupportedOperationException.class, () -> instance.iterator().remove());
    }

    @Test
    void spliterator() {
        final AtomicInteger cnt = new AtomicInteger();
        instance.spliterator().forEachRemaining(s -> cnt.incrementAndGet());
        assertEquals(1, cnt.get());
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    void spliteratorCharacteristicsOfNullElement() {
        final SingletonStream containsNull = SingletonStream.of(null);
        final Spliterator<String> spliterator = containsNull.spliterator();
        assertEquals(0, spliterator.characteristics() & Spliterator.NONNULL);
    }

    @Test
    void spliteratorCharacteristics() {
        final int c = instance.spliterator().characteristics();
        assertHasFlag(c, Spliterator.DISTINCT);
        assertHasFlag(c, Spliterator.IMMUTABLE);
        assertHasFlag(c, Spliterator.ORDERED);
        assertHasFlag(c, Spliterator.SIZED);
        assertHasFlag(c, Spliterator.SUBSIZED);
        assertHasFlag(c, Spliterator.NONNULL);
    }

    private void assertHasFlag(int c, int flag) {
        assertTrue((c & flag) > 0);
    }

    @Test
    void spliteratorEstimateSize() {
        final AtomicInteger cnt = new AtomicInteger();
        final Spliterator<String> spliterator = instance.spliterator();
        assertEquals(1, spliterator.estimateSize());
        spliterator.tryAdvance(e -> cnt.incrementAndGet());
        assertEquals(0, spliterator.estimateSize());
        assertEquals(1, cnt.get());
    }

    @Test
    void spliteratorTrySplit() {
        assertNull(instance.spliterator().trySplit());
    }

    @Test
    void spliteratorTryAdvance() {
        final Spliterator<String> spliterator = instance.spliterator();
        final AtomicInteger cnt = new AtomicInteger();
        assertTrue(spliterator.tryAdvance(e -> cnt.incrementAndGet()));
        assertFalse(spliterator.tryAdvance(e -> cnt.incrementAndGet()));
    }


    @Test
    void isParallel() {
        assertFalse(instance.isParallel());
        final Stream<String> newStream = instance.parallel();
        assertTrue(newStream.isParallel());
    }

    @Test
    void mutableParallel() {
        instance.parallel();
        assertTrue(instance.isParallel());
    }

    @Test
    void parallel() {
        final Stream<String> newStream = instance.parallel();
        assertTrue(newStream.isParallel());
    }

    @Test
    void sequential() {
        assertSame(instance, instance.sequential());
    }

    @Test
    void unordered() {
        assertSame(instance, instance.unordered());
    }

    @Test
    void mutableOnClose() {
        final AtomicInteger cnt = new AtomicInteger();
        instance.onClose(cnt::incrementAndGet);
        instance.close();
        assertEquals(1, cnt.get());
    }

    @Test
    void onClose() {
        final AtomicInteger cnt = new AtomicInteger();
        final Stream<String> newStream = instance.onClose(cnt::incrementAndGet);
        newStream.close();
        assertEquals(1, cnt.get());
    }

    @Test
    void close() {
        assertDoesNotThrow(instance::close);
    }

    @Test
    void takeWhile() {
        assertEquals(1, instance.takeWhile(ELEMENT::equals).count());
        assertEquals(0, instance.takeWhile(OTHER_ELEMENT::equals).count());
    }

    @Test
    void dropWhile() {
        assertEquals(0, instance.dropWhile(ELEMENT::equals).count());
        assertEquals(1, instance.dropWhile(OTHER_ELEMENT::equals).count());
    }

    private <T> Consumer<T> blackHole() {
        return (T t) -> {};
    }

}
