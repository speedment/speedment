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
package com.speedment.common.mapstream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;

final class MapStreamTest {

    private final double EPSILON = 1e-9;

    private  Map<String, Integer> stringToInt;
    private  Map<Integer, String> intToString;
    private final Collector<Map.Entry<String, Integer>, ?, Map<String, Integer>> TO_MAP = Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    private final Predicate<Map.Entry<String, Integer>> KEY_STARTS_WITH_D = e -> e.getKey().startsWith("d");
    private MapStream<String, Integer> instance;

    @BeforeEach
    void setup() {
        stringToInt = refStream().collect(TO_MAP);
        intToString = refStream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        instance    = MapStream.of(refStream());
    }

    @Test
    @SuppressWarnings("unchecked")
    void of() {
        final MapStream<String, Integer> ms = MapStream.of(refStream().toArray(Map.Entry[]::new));
        assertEquals(stringToInt, ms.toMap());
    }

    @Test
    void testOf() {
        final MapStream<String, Integer> ms = MapStream.of(stringToInt);
        assertEquals(stringToInt, ms.toMap());
    }

    @Test
    void testOf1() {
        final Map.Entry<String, Integer> e = entry("pet", 5);
        final MapStream<String, Integer> ms = MapStream.of(e);
        final Map<String, Integer> expected = Stream.of(e).collect(TO_MAP);
        assertEquals(expected, ms.toMap());
    }

    @Test
    void testOf2() {
        final MapStream<String, Integer> ms = MapStream.of(refStream());
        assertEquals(stringToInt, ms.toMap());
    }

    @Test
    void testOf3() {
        final MapStream<String, Integer> msSequential = MapStream.of(stringToInt, false);
        assertEquals(stringToInt, msSequential.toMap());
        final MapStream<String, Integer> msParallel = MapStream.of(stringToInt, true);
        assertEquals(stringToInt, msParallel.toMap());
    }

    @Test
    void fromKeys() {
        final MapStream<String, Integer> ms = MapStream.fromKeys(stringToInt.keySet().stream(), stringToInt::get);
        assertEquals(stringToInt, ms.toMap());
    }

    @Test
    void fromValues() {
        final MapStream<String, Integer> ms = MapStream.fromValues(stringToInt.values().stream(), intToString::get);
        assertEquals(stringToInt, ms.toMap());
    }

    @Test
    void fromStream() {
        final MapStream<String, Integer> ms = MapStream.fromStream(stringToInt.keySet().stream(), Function.identity(), stringToInt::get);
        assertEquals(stringToInt, ms.toMap());
    }

    @Test
    void empty() {
        final MapStream<String, Integer> empty = MapStream.empty();
        assertEquals(0, empty.values().count());
    }

    @Test
    void flip() {
        final MapStream<Integer, String> ms = MapStream.flip(instance);
        assertEquals(MapStream.of(intToString).toMap(), ms.toMap());
    }

    @Test
    void filter() {
        final Map<String, Integer> expected = refStream().filter(KEY_STARTS_WITH_D).collect(TO_MAP);
        final Map<String, Integer> actual = instance.filter(KEY_STARTS_WITH_D).toMap();
        assertEquals(expected, actual);
    }

    @Test
    void testFilter() {
        final Map<String, Integer> expected = refStream().filter(KEY_STARTS_WITH_D).collect(TO_MAP);
        final Map<String, Integer> actual = instance.filter((k, v) -> k.startsWith("d")).toMap();
        assertEquals(expected, actual);
    }

    @Test
    void filterKey() {
        final Map<String, Integer> expected = refStream().filter(KEY_STARTS_WITH_D).collect(TO_MAP);
        final Map<String, Integer> actual = instance.filterKey(k -> k.startsWith("d")).toMap();
        assertEquals(expected, actual);
    }

    @Test
    void filterValue() {
        final Map<String, Integer> expected = refStream().filter(KEY_STARTS_WITH_D).collect(TO_MAP);
        final Map<String, Integer> actual = instance.filterValue(k -> k.equals(2)).toMap();
        assertEquals(expected, actual);
    }

    @Test
    void map() {
        final Set<String> expected = new HashSet<>(stringToInt.keySet());
        final Set<String> actual = instance.map(Map.Entry::getKey).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    void testMap() {
        final Set<String> expected = new HashSet<>(stringToInt.keySet());
        final Set<String> actual = instance.map((k, v) -> k).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    void mapKey() {
        final Map<String, Integer> expected = refStream().map(e -> new AbstractMap.SimpleImmutableEntry<>(e.getKey() + "A", e.getValue())).collect(TO_MAP);
        final Map<String, Integer> actual = instance.mapKey(s -> s + "A").toMap();
        assertEquals(expected, actual);
    }

    @Test
    void testMapKey() {
        final Map<String, Integer> expected = refStream().map(e -> new AbstractMap.SimpleImmutableEntry<>(e.getKey() + "A", e.getValue())).collect(TO_MAP);
        final Map<String, Integer> actual = instance.mapKey((k, v) -> k + "A").toMap();
        assertEquals(expected, actual);
    }

    @Test
    void mapValue() {
        final Map<String, Integer> expected = refStream().map(e -> new AbstractMap.SimpleImmutableEntry<>(e.getKey(), e.getValue() + 1)).collect(TO_MAP);
        final Map<String, Integer> actual = instance.mapValue(v -> v + 1).toMap();
        assertEquals(expected, actual);
    }

    @Test
    void testMapValue() {
        final Map<String, Integer> expected = refStream().map(e -> new AbstractMap.SimpleImmutableEntry<>(e.getKey(), e.getValue() + 1)).collect(TO_MAP);
        final Map<String, Integer> actual = instance.mapValue((k, v) -> v + 1).toMap();
        assertEquals(expected, actual);
    }

    @Test
    void mapToInt() {
        final int expected = refStream().mapToInt(Map.Entry::getValue).map(i -> i + 1).sum();
        final int actual = instance.mapToInt(e -> e.getValue() + 1).sum();
        assertEquals(expected, actual);
    }

    @Test
    void testMapToInt() {
        final int expected = refStream().mapToInt(Map.Entry::getValue).map(i -> i + 1).sum();
        final int actual = instance.mapToInt((k, v) -> v + 1).sum();
        assertEquals(expected, actual);
    }

    @Test
    void mapToLong() {
        final long expected = refStream().mapToLong(Map.Entry::getValue).map(i -> i + 1).sum();
        final long actual = instance.mapToLong(e -> e.getValue() + 1).sum();
        assertEquals(expected, actual);
    }

    @Test
    void testMapToLong() {
        final long expected = refStream().mapToLong(Map.Entry::getValue).map(i -> i + 1).sum();
        final long actual = instance.mapToLong((k, v) -> v + 1).sum();
        assertEquals(expected, actual);
    }

    @Test
    void mapToDouble() {
        final double expected = refStream().mapToDouble(Map.Entry::getValue).map(i -> i + 1).sum();
        final double actual = instance.mapToDouble(e -> e.getValue() + 1).sum();
        assertEquals(expected, actual);
    }

    @Test
    void testMapToDouble() {
        final double expected = refStream().mapToDouble(Map.Entry::getValue).map(i -> i + 1).sum();
        final double actual = instance.mapToDouble((k, v) -> v + 1).sum();
        assertEquals(expected, actual);
    }

    @Test
    void flatMap() {
        final long expected = refStream().flatMap(e -> e.getKey().chars().boxed()).count();
        final long actual = instance.flatMap(e -> e.getKey().chars().boxed()).count();
        assertEquals(expected, actual);
    }

    @Test
    void testFlatMap() {
        final long expected = refStream().flatMap(e -> e.getKey().chars().boxed()).count();
        final long actual = instance.flatMap((k, v) -> k.chars().boxed()).count();
        assertEquals(expected, actual);
    }

    @Test
    void flatMapKey() {
        final long expected = refStream().flatMap(e -> e.getKey().chars().boxed()).count();
        final long actual = instance.flatMapKey(e -> e.chars().boxed()).count();
        assertEquals(expected, actual);
    }

    @Test
    void testFlatMapKey() {
        final long expected = refStream().flatMap(e -> e.getKey().chars().boxed()).count();
        final long actual = instance.flatMapKey((k, v) -> k.chars().boxed()).count();
        assertEquals(expected, actual);
    }

    @Test
    void flatMapValue() {
        final long expected = refStream().flatMap(e -> IntStream.range(0, e.getValue()).boxed()).count();
        final long actual = instance.flatMapValue(v -> IntStream.range(0, v).boxed()).count();
        assertEquals(expected, actual);
    }

    @Test
    void testFlatMapValue() {
        final long expected = refStream().flatMap(e -> IntStream.range(0, e.getValue()).boxed()).count();
        final long actual = instance.flatMapValue((k, v) -> IntStream.range(0, v).boxed()).count();
        assertEquals(expected, actual);
    }

    @Test
    void flatMapToInt() {
        final int expected = refStream().flatMapToInt(e -> IntStream.range(0, e.getValue())).sum();
        final int actual = instance.flatMapToInt(e -> IntStream.range(0, e.getValue())).sum();
        assertEquals(expected, actual);
    }

    @Test
    void testFlatMapToInt() {
        final int expected = refStream().flatMapToInt(e -> IntStream.range(0, e.getValue())).sum();
        final int actual = instance.flatMapToInt((k, v) -> IntStream.range(0, v)).sum();
        assertEquals(expected, actual);
    }

    @Test
    void flatMapToLong() {
        final long expected = refStream().flatMapToLong(e -> LongStream.range(0, e.getValue())).sum();
        final long actual = instance.flatMapToLong(e -> LongStream.range(0, e.getValue())).sum();
        assertEquals(expected, actual);
    }

    @Test
    void testFlatMapToLong() {
        final long expected = refStream().flatMapToLong(e -> LongStream.range(0, e.getValue())).sum();
        final long actual = instance.flatMapToLong((k, v) -> LongStream.range(0, v)).sum();
        assertEquals(expected, actual);
    }

    @Test
    void flatMapToDouble() {
        final double expected = refStream().flatMapToDouble(e -> LongStream.range(0, e.getValue()).mapToDouble(l -> l)).sum();
        final double actual = instance.flatMapToDouble(e -> LongStream.range(0, e.getValue()).mapToDouble(l -> l)).sum();
        assertEquals(expected, actual, EPSILON);
    }

    @Test
    void testFlatMapToDouble() {
        final double expected = refStream().flatMapToDouble(e -> LongStream.range(0, e.getValue()).mapToDouble(l -> l)).sum();
        final double actual = instance.flatMapToDouble((k, v) -> LongStream.range(0, v).mapToDouble(l -> l)).sum();
        assertEquals(expected, actual, EPSILON);
    }

    @Test
    void keys() {
        final List<String> expected = refStream().map(Map.Entry::getKey).collect(Collectors.toList());
        final List<String> actual = instance.keys().collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @Test
    void values() {
        final List<Integer> expected = refStream().map(Map.Entry::getValue).collect(Collectors.toList());
        final List<Integer> actual = instance.values().collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @Test
    void distinct() {
        final MapStream<String, Integer> ms = MapStream.of(entry("Eins", 1), entry("Eins", 1));
        assertEquals(1, ms.distinct().count());
    }

    @Test
    void distinctKeys() {
        final MapStream<String, Integer> ms = MapStream.of(entry("Car", 1), entry("Car", 2));
        assertEquals(1, ms.distinctKeys().count());
        final MapStream<String, Integer> ms2 = MapStream.of(entry("Car", 1), entry("Car", 2)).parallel();
        assertEquals(1, ms2.distinctKeys().count());
    }

    @Test
    void distinctKeysParallel() {
        final MapStream<String, Integer> ms = MapStream.of(entry("Car", 1), entry("Car", 2)).parallel();
        assertEquals(1, ms.distinctKeys().count());
    }


    @Test
    void distinctValues() {
        final MapStream<String, Integer> ms = MapStream.of(entry("jedan", 1), entry("Eins", 1));
        assertEquals(1, ms.distinctValues().count());
    }

    @Test
    void testDistinctKeys() {
        final MapStream<String, Integer> ms = MapStream.of(entry("Car", 1), entry("Car", 2));
        assertEquals(3, ms.distinctKeys(Integer::sum).values().mapToInt(i -> i).sum());
    }

    @Test
    void testDistinctKeysParallel() {
        final MapStream<String, Integer> ms = MapStream.of(entry("Car", 1), entry("Car", 2)).parallel();
        assertEquals(3, ms.distinctKeys(Integer::sum).values().mapToInt(i -> i).sum());
    }

    @Test
    void testDistinctValues() {
        final Map<String, Integer> expected = MapStream.of(entry("Einsjedan", 1)).toMap();
        final MapStream<String, Integer> ms = MapStream.of(entry("jedan", 1), entry("Eins", 1));
        final Map<String, Integer> actual = ms.distinctValues((a, b) -> a + b).toMap();
        assertEquals(expected, actual);
    }

    @Test
    void sorted() {
        final List<Map.Entry<String, Integer>> expected = refStream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList());
        final List<Map.Entry<String, Integer>> actual = instance.sorted().toList();
        assertEquals(expected, actual);

        final MapStream<Object, Integer> ms = MapStream.of(entry(new Object(), 1), entry(new Object(), 2));
        assertThrows(UnsupportedOperationException.class, () -> ms.sorted().forEach(e -> {}));

        final MapStream<String, Integer> ms2 = MapStream.of(entry(null, 1), entry(null, 2));
        assertDoesNotThrow(() -> ms2.sorted().forEach(e -> {}));

        // Todo: Test values with both null and string values

    }

    @Test
    void testSorted() {
        final List<Map.Entry<String, Integer>> expected = refStream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
        final List<Map.Entry<String, Integer>> actual = instance.sorted(Map.Entry.comparingByValue()).toList();
        assertEquals(expected, actual);
    }

    @Test
    void sortedByKey() {
        final Comparator<Map.Entry<String, Integer>> comparator = Map.Entry.comparingByKey(); // Java's type inference can be improved...
        final List<Map.Entry<String, Integer>> expected = refStream().sorted(comparator.reversed()).collect(Collectors.toList());
        final List<Map.Entry<String, Integer>> actual = instance.sortedByKey(Comparator.reverseOrder()).toList();
        assertEquals(expected, actual);
    }

    @Test
    void sortedByValue() {
        final Comparator<Map.Entry<String, Integer>> comparator = Map.Entry.comparingByValue(); // Java's type inference can be improved...
        final List<Map.Entry<String, Integer>> expected = refStream().sorted(comparator.reversed()).collect(Collectors.toList());
        final List<Map.Entry<String, Integer>> actual = instance.sortedByValue(Comparator.reverseOrder()).toList();
        assertEquals(expected, actual);
    }

    @Test
    void peek() {
        final List<Map.Entry<String, Integer>> entries = new ArrayList<>();
        instance
            .peek((Consumer<Map.Entry<String, Integer>>) entries::add)
            .forEach(unused -> {});
        assertEquals(refStream().collect(Collectors.toList()), entries);
    }

    @Test
    void testPeek() {
        final List<Map.Entry<String, Integer>> entries = new ArrayList<>();
        instance
            .peek((k, v) -> entries.add(entry(k, v)))
            .forEach(unused -> {});
        assertEquals(refStream().collect(Collectors.toList()), entries);
    }

    @Test
    void limit() {
        assertEquals(1L, instance.limit(1).count());
    }

    @Test
    void skip() {
        assertEquals(refStream().skip(1).count(), instance.skip(1).count());
    }

    @Test
    void forEach() {
        final Set<Map.Entry<String, Integer>> entries = new HashSet<>();
        instance
            .forEach((Consumer<Map.Entry<String, Integer>>) entries::add);
        assertEquals(refStream().collect(Collectors.toSet()), entries);
    }

    @Test
    void testForEach() {
        final Set<Map.Entry<String, Integer>> entries = new HashSet<>();
        instance
            .forEach((k, v) -> entries.add(entry(k, v)));
        assertEquals(refStream().collect(Collectors.toSet()), entries);
    }

    @Test
    void forEachOrdered() {
        final List<Map.Entry<String, Integer>> entries = new ArrayList<>();
        instance
            .forEachOrdered((Consumer<Map.Entry<String, Integer>>) entries::add);
        assertEquals(refStream().collect(Collectors.toList()), entries);
    }

    @Test
    void testForEachOrdered() {
        final List<Map.Entry<String, Integer>> entries = new ArrayList<>();
        instance
            .forEachOrdered((k, v) -> entries.add(entry(k, v)));
        assertEquals(refStream().collect(Collectors.toList()), entries);
    }

    @Test
    void toArray() {
        final Object[] expected = refStream().toArray();
        final Object[] actual = instance.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("rawtypes")
    void testToArray() {
        final Map.Entry[] expected = refStream().toArray(Map.Entry[]::new);
        final Map.Entry[] actual = instance.toArray(Map.Entry[]::new);
        assertArrayEquals(expected, actual);
    }

    @Test
    void reduce() {
        final Optional<Map.Entry<String, Integer>> expected = Optional.of(entry("jedandvatri", 1 + 2 + 3));
        final Optional<Map.Entry<String, Integer>> actual = instance.reduce((e0, e1) -> entry(e0.getKey()+e1.getKey() , e0.getValue()+e1.getValue()));
        assertEquals(expected, actual);
    }

    @Test
    void testReduce() {
        final Map.Entry<String, Integer> expected = entry("jedandvatri", 1 + 2 + 3);
        final Map.Entry<String, Integer> actual = instance.reduce(entry("", 0), (e0, e1) -> entry(e0.getKey()+e1.getKey() , e0.getValue()+e1.getValue()));
        assertEquals(expected, actual);
    }

    @Test
    void testReduce1() {
        final Map.Entry<String, Integer> expected = entry("jedandvatri", 1 + 2 + 3);
        final Map.Entry<String, Integer> actual = instance.reduce(entry("", 0), (e0, e1) -> entry(e0.getKey()+e1.getKey() , e0.getValue()+e1.getValue()), (e0, e1) -> entry(e0.getKey()+e1.getKey() , e0.getValue()+e1.getValue()));
        assertEquals(expected, actual);
    }

    @Test
    void collect() {
        final List<Map.Entry<String, Integer>> expected = refStream().collect(Collectors.toList());
        final List<Map.Entry<String, Integer>> actual = instance.collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @Test
    void testCollect() {
        final Supplier<List<Map.Entry<String, Integer>>> supplier =  ArrayList::new;
        final BiConsumer<List<Map.Entry<String, Integer>>, Map.Entry<String, Integer>> accumulator = List::add;
        final BiConsumer<List<Map.Entry<String, Integer>>, List<Map.Entry<String, Integer>>> combiner = List::addAll;
        final List<Map.Entry<String, Integer>> expected = refStream().collect(supplier, accumulator, combiner);
        final List<Map.Entry<String, Integer>> actual = instance.collect(supplier, accumulator, combiner);
        assertEquals(expected, actual);
    }

    @Test
    void groupingBy() {
        final Function<Map.Entry<String, Integer>, Integer> classifier = (Map.Entry<String, Integer> e) -> e.getValue() % 2;
        final Map<Integer, List<Integer>> expected = refStream()
            .collect(Collectors.groupingBy(
                classifier,
                Collectors.mapping(Map.Entry::getValue, Collectors.toList()))
            );
        final Map<Integer, List<Integer>> actual = instance.groupingBy(v -> v % 2).toMap();
        assertEquals(expected, actual);
    }

    @Test
    void min() {
        final Optional<Map.Entry<String, Integer>> expected = refStream().min(Map.Entry.comparingByValue());
        final Optional<Map.Entry<String, Integer>> actual = instance.min(Map.Entry.comparingByValue());
        assertEquals(expected, actual);
    }

    @Test
    void minByKey() {
        final Optional<Map.Entry<String, Integer>> expected = refStream().min(Map.Entry.comparingByKey());
        final Optional<Map.Entry<String, Integer>> actual = instance.minByKey(Comparator.naturalOrder());
        assertEquals(expected, actual);
    }

    @Test
    void minByValue() {
        final Optional<Map.Entry<String, Integer>> expected = refStream().min(Map.Entry.comparingByValue());
        final Optional<Map.Entry<String, Integer>> actual = instance.minByValue(Comparator.naturalOrder());
        assertEquals(expected, actual);
    }

    @Test
    void max() {
        final Optional<Map.Entry<String, Integer>> expected = refStream().max(Map.Entry.comparingByValue());
        final Optional<Map.Entry<String, Integer>> actual = instance.max(Map.Entry.comparingByValue());
        assertEquals(expected, actual);
    }

    @Test
    void maxByKey() {
        final Optional<Map.Entry<String, Integer>> expected = refStream().max(Map.Entry.comparingByKey());
        final Optional<Map.Entry<String, Integer>> actual = instance.maxByKey(Comparator.naturalOrder());
        assertEquals(expected, actual);
    }

    @Test
    void maxByValue() {
        final Optional<Map.Entry<String, Integer>> expected = refStream().max(Map.Entry.comparingByValue());
        final Optional<Map.Entry<String, Integer>> actual = instance.maxByValue(Comparator.naturalOrder());
        assertEquals(expected, actual);
    }

    @Test
    void count() {
        final long expected = refStream().count();
        final long actual = instance.count();
        assertEquals(expected, actual);
    }

    @Test
    void anyMatch() {
        final Predicate<Map.Entry<String, Integer>> predicate = e -> e.getKey().contains("a");
        final boolean expected = refStream().anyMatch(predicate);
        final boolean actual = instance.anyMatch(predicate);
        assertEquals(expected, actual);
    }

    @Test
    void testAnyMatch() {
        final Predicate<Map.Entry<String, Integer>> predicate = e -> e.getKey().contains("a");
        final boolean expected = refStream().anyMatch(predicate);
        final boolean actual = instance.anyMatch((k, v) -> k.contains("a"));
        assertEquals(expected, actual);
    }

    @Test
    void allMatch() {
        final Predicate<Map.Entry<String, Integer>> predicate = e -> !e.getKey().isEmpty();
        final boolean expected = refStream().allMatch(predicate);
        final boolean actual = instance.allMatch(predicate);
        assertEquals(expected, actual);
    }

    @Test
    void testAllMatch() {
        final Predicate<Map.Entry<String, Integer>> predicate = e -> !e.getKey().isEmpty();
        final boolean expected = refStream().allMatch(predicate);
        final boolean actual = instance.allMatch((k, v) -> !k.isEmpty());
        assertEquals(expected, actual);
    }

    @Test
    void noneMatch() {
        final Predicate<Map.Entry<String, Integer>> predicate = e -> e.getKey().isEmpty();
        final boolean expected = refStream().noneMatch(predicate);
        final boolean actual = instance.noneMatch(predicate);
        assertEquals(expected, actual);
    }

    @Test
    void testNoneMatch() {
        final Predicate<Map.Entry<String, Integer>> predicate = e -> e.getKey().isEmpty();
        final boolean expected = refStream().noneMatch(predicate);
        final boolean actual = instance.noneMatch((k, v) -> k.isEmpty());
        assertEquals(expected, actual);
    }

    @Test
    void findFirst() {
        final Optional<Map.Entry<String, Integer>> expected = refStream().findFirst();
        final Optional<Map.Entry<String, Integer>> actual = instance.findFirst();
        assertEquals(expected, actual);
    }

    @Test
    void findAny() {
        assertTrue(instance.findAny().isPresent());
    }

    @Test
    void iterator() {
        final List<Map.Entry<String, Integer>> expected = refStream().collect(Collectors.toList());
        final List<Map.Entry<String, Integer>> actual = new ArrayList<>();
        final Iterator<Map.Entry<String, Integer>> iterator = instance.iterator();
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }
        assertEquals(expected, actual);
    }

    @Test
    void spliterator() {
        final List<Map.Entry<String, Integer>> expected = refStream().collect(Collectors.toList());
        final List<Map.Entry<String, Integer>> actual = new ArrayList<>();
        final Spliterator<Map.Entry<String, Integer>> spliterator = instance.spliterator();
        spliterator.forEachRemaining(actual::add);
        assertEquals(expected, actual);
    }

    @Test
    void isParallel() {
        assertFalse(instance.isParallel());
        assertTrue(MapStream.of(stringToInt, true).isParallel());
    }

    @Test
    void sequential() {
        assertNotNull(instance.sequential());
    }

    @Test
    void parallel() {
        assertFalse(instance.isParallel());
        assertTrue(instance.parallel().isParallel());
    }

    @Test
    void unordered() {
        assertNotNull(instance.unordered());
    }

    @Test
    void onClose() {
        final AtomicInteger cnt = new AtomicInteger();
        instance.onClose(cnt::incrementAndGet);
        instance.close();
        assertEquals(1, cnt.get());
    }

    @Test
    void close() {
        assertDoesNotThrow(instance::close);
    }

    @Test
    void toMap() {
        final Map<String, Integer> expected = refStream().collect(TO_MAP);
        final Map<String, Integer> actual = instance.toMap();
        assertEquals(expected, actual);
    }

    @Test
    void testToMap() {
        final Map<String, Integer> expected = refStream().collect(TO_MAP);
        final Map<String, Integer> actual = instance.toMap(MapStream.throwingMerger());
        assertEquals(expected, actual);
        assertThrows(IllegalStateException.class, () -> {
            final Object o = MapStream.of(entry("Olle", 1), entry("Olle", 2))
                .toMap(MapStream.throwingMerger());
        });
    }

    @Test
    void toConcurrentMap() {
        final Map<String, Integer> expected = refStream().collect(TO_MAP);
        final Map<String, Integer> actual = instance.toConcurrentMap();
        assertEquals(expected, actual);
        assertTrue(actual instanceof ConcurrentMap);
    }

    @Test
    void testToConcurrentMap() {
        final Map<String, Integer> expected = refStream().collect(TO_MAP);
        final Map<String, Integer> actual = instance.toConcurrentMap(MapStream.throwingMerger());
        assertEquals(expected, actual);
        assertTrue(actual instanceof ConcurrentMap);
        assertThrows(IllegalStateException.class, () -> {
            final Object o = MapStream.of(entry("Olle", 1), entry("Olle", 2))
                .toConcurrentMap(MapStream.throwingMerger());
        });
    }

    @Test
    void toSortedMap() {
        final Map<String, Integer> expected = refStream().collect(TO_MAP);
        final Map<String, Integer> actual = instance.toSortedMap();
        assertEquals(expected, actual);
        assertTrue(actual instanceof NavigableMap);
    }

    @Test
    void toSortedMapByKey() {
        final Comparator<String> keyComparator = Comparator.reverseOrder();
        final Map<String, Integer> expected = refStream().map(Map.Entry::getKey).sorted(keyComparator).map(k -> entry(k, stringToInt.get(k))).collect(TO_MAP);
        final Map<String, Integer> actual = instance.toSortedMapByKey(keyComparator);
        assertEquals(expected, actual);
        assertTrue(actual instanceof NavigableMap);
    }

    @Test
    void testToSortedMap() {
        final Map<String, Integer> expected = refStream().collect(TO_MAP);
        final Map<String, Integer> actual = instance.toSortedMap(MapStream.throwingMerger());
        assertEquals(expected, actual);
        assertTrue(actual instanceof NavigableMap);
        assertThrows(IllegalStateException.class, () -> {
            final Object o = MapStream.of(entry("Olle", 1), entry("Olle", 2))
                .toSortedMap(MapStream.throwingMerger());
        });
    }

    @Test
    void testToSortedMap1() {
        final Comparator<String> keyComparator = Comparator.reverseOrder();
        final Map<String, Integer> expected = refStream().map(Map.Entry::getKey).sorted(keyComparator).map(k -> entry(k, stringToInt.get(k))).collect(TO_MAP);
        final Map<String, Integer> actual = instance.toSortedMap(keyComparator, MapStream.throwingMerger());
        assertEquals(expected, actual);
        assertTrue(actual instanceof NavigableMap);
        assertThrows(IllegalStateException.class, () -> {
            final Object o = MapStream.of(entry("Olle", 1), entry("Olle", 2))
                .toSortedMap(keyComparator, MapStream.throwingMerger());
        });
    }

    @Test
    void toConcurrentNavigableMap() {
        final Map<String, Integer> expected = refStream().collect(TO_MAP);
        final Map<String, Integer> actual = instance.toConcurrentNavigableMap();
        assertEquals(expected, actual);
    }

    @Test
    void toConcurrentNavigableMapByKey() {
        final Comparator<String> keyComparator = Comparator.reverseOrder();
        final Map<String, Integer> expected = refStream().map(Map.Entry::getKey).sorted(keyComparator).map(k -> entry(k, stringToInt.get(k))).collect(TO_MAP);
        final Map<String, Integer> actual = instance.toConcurrentNavigableMapByKey(keyComparator);
        assertEquals(expected, actual);
    }

    @Test
    void testToConcurrentNavigableMap() {
        final Map<String, Integer> expected = refStream().collect(TO_MAP);
        final Map<String, Integer> actual = instance.toConcurrentNavigableMap(MapStream.throwingMerger());
        assertEquals(expected, actual);
        assertThrows(IllegalStateException.class, () -> {
            final Object o = MapStream.of(entry("Olle", 1), entry("Olle", 2))
                .toConcurrentNavigableMap(MapStream.throwingMerger());
        });
    }

    @Test
    void testToConcurrentNavigableMap1() {
        final Comparator<String> keyComparator = Comparator.reverseOrder();
        final Map<String, Integer> expected = refStream().map(Map.Entry::getKey).sorted(keyComparator).map(k -> entry(k, stringToInt.get(k))).collect(TO_MAP);
        final Map<String, Integer> actual = instance.toConcurrentNavigableMap(keyComparator, MapStream.throwingMerger());
        assertEquals(expected, actual);
        assertThrows(IllegalStateException.class, () -> {
            final Object o = MapStream.of(entry("Olle", 1), entry("Olle", 2))
                .toConcurrentNavigableMap(keyComparator, MapStream.throwingMerger());
        });
    }

    @Test
    void toList() {
        final List<Map.Entry<String, Integer>> expected = refStream().collect(Collectors.toList());
        final List<Map.Entry<String, Integer>> actual = instance.toList();
        assertEquals(expected, actual);
    }

    @Test
    void comparing() {
        final Comparator<String> comparator = MapStream.comparing(s -> s.charAt(0), s -> s.charAt(1), s -> s.charAt(2));
        final List<Map.Entry<String, Integer>> expected = refStream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList());
        final List<Map.Entry<String, Integer>> actual = instance.sortedByKey(comparator).collect(Collectors.toList());
        assertEquals(expected, actual);

        assertThrows(NullPointerException.class, () -> MapStream.comparing(Function.identity(), null));
    }

    @Test
    void throwingMerger() {
        final BinaryOperator<Integer> merger = MapStream.throwingMerger();
        assertThrows(IllegalStateException.class, () -> merger.apply(42, 42));
    }

    private static Stream<Map.Entry<String, Integer>> refStream() {
        return Stream.of(
            entry("jedan", 1),
            entry("dva", 2),
            entry("tri", 3)
        );
    }

    private static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }

}