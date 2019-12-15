package com.speedment.common.mapstream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class MapStreamTest {

    private final double EPSILON = 1e-9;

    private  Map<String, Integer> stringToint;
    private  Map<Integer, String> intToString;
    private final Collector<Map.Entry<String, Integer>, ?, Map<String, Integer>> TO_MAP = Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    private final Predicate<Map.Entry<String, Integer>> KEY_STARTS_WITH_D = e -> e.getKey().startsWith("d");
    private MapStream<String, Integer> instance;

    @BeforeEach
    void setup() {
        stringToint = refStream().collect(TO_MAP);
        intToString = refStream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        instance    = MapStream.of(refStream());
    }

    @Test
    void of() {
        final MapStream<String, Integer> ms = MapStream.of(refStream().toArray(Map.Entry[]::new));
        assertEquals(stringToint, ms.toMap());
    }

    @Test
    void testOf() {
        final MapStream<String, Integer> ms = MapStream.of(stringToint);
        assertEquals(stringToint, ms.toMap());
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
        assertEquals(stringToint, ms.toMap());
    }

    @Test
    void testOf3() {
        final MapStream<String, Integer> msSequential = MapStream.of(stringToint, false);
        assertEquals(stringToint, msSequential.toMap());
        final MapStream<String, Integer> msParallel = MapStream.of(stringToint, true);
        assertEquals(stringToint, msParallel.toMap());
    }

    @Test
    void fromKeys() {
        final MapStream<String, Integer> ms = MapStream.fromKeys(stringToint.keySet().stream(), stringToint::get);
        assertEquals(stringToint, ms.toMap());
    }

    @Test
    void fromValues() {
        final MapStream<String, Integer> ms = MapStream.fromValues(stringToint.values().stream(), intToString::get);
        assertEquals(stringToint, ms.toMap());
    }

    @Test
    void fromStream() {
        final MapStream<String, Integer> ms = MapStream.fromStream(stringToint.keySet().stream(), Function.identity(), stringToint::get);
        assertEquals(stringToint, ms.toMap());
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
        final Set<String> expected = new HashSet<>(stringToint.keySet());
        final Set<String> actual = instance.map(Map.Entry::getKey).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    void testMap() {
        final Set<String> expected = new HashSet<>(stringToint.keySet());
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
    }

    @Test
    void distinctKeys() {
    }

    @Test
    void distinctValues() {
    }

    @Test
    void testDistinctKeys() {
    }

    @Test
    void testDistinctValues() {
    }

    @Test
    void sorted() {
    }

    @Test
    void testSorted() {
    }

    @Test
    void sortedByKey() {
    }

    @Test
    void sortedByValue() {
    }

    @Test
    void peek() {
    }

    @Test
    void testPeek() {
    }

    @Test
    void limit() {
    }

    @Test
    void skip() {
    }

    @Test
    void forEach() {
    }

    @Test
    void testForEach() {
    }

    @Test
    void forEachOrdered() {
    }

    @Test
    void testForEachOrdered() {
    }

    @Test
    void toArray() {
    }

    @Test
    void testToArray() {
    }

    @Test
    void reduce() {
    }

    @Test
    void testReduce() {
    }

    @Test
    void testReduce1() {
    }

    @Test
    void collect() {
    }

    @Test
    void testCollect() {
    }

    @Test
    void groupingBy() {
    }

    @Test
    void min() {
    }

    @Test
    void minByKey() {
    }

    @Test
    void minByValue() {
    }

    @Test
    void max() {
    }

    @Test
    void maxByKey() {
    }

    @Test
    void maxByValue() {
    }

    @Test
    void count() {
    }

    @Test
    void anyMatch() {
    }

    @Test
    void testAnyMatch() {
    }

    @Test
    void allMatch() {
    }

    @Test
    void testAllMatch() {
    }

    @Test
    void noneMatch() {
    }

    @Test
    void testNoneMatch() {
    }

    @Test
    void findFirst() {
    }

    @Test
    void findAny() {
    }

    @Test
    void iterator() {
    }

    @Test
    void spliterator() {
    }

    @Test
    void isParallel() {
    }

    @Test
    void sequential() {
    }

    @Test
    void parallel() {
    }

    @Test
    void unordered() {
    }

    @Test
    void onClose() {
    }

    @Test
    void close() {
    }

    @Test
    void toMap() {
    }

    @Test
    void testToMap() {
    }

    @Test
    void toConcurrentMap() {
    }

    @Test
    void testToConcurrentMap() {
    }

    @Test
    void toSortedMap() {
    }

    @Test
    void toSortedMapByKey() {
    }

    @Test
    void testToSortedMap() {
    }

    @Test
    void testToSortedMap1() {
    }

    @Test
    void toConcurrentNavigableMap() {
    }

    @Test
    void toConcurrentNavigableMapByKey() {
    }

    @Test
    void testToConcurrentNavigableMap() {
    }

    @Test
    void testToConcurrentNavigableMap1() {
    }

    @Test
    void toList() {
    }

    @Test
    void comparing() {
    }

    @Test
    void throwingMerger() {
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