package com.speedment.common.mapstream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class MapStreamTest {

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
        assertEquals(MapStream.of(intToString), ms);
    }

    @Test
    void filter() {
        final Map<String, Integer> expected = refStream().filter(KEY_STARTS_WITH_D).collect(TO_MAP);


        final MapStream<String, Integer> ms = MapStream.of(stringToint);

        final Map<String, Integer> actual = instance.filter(KEY_STARTS_WITH_D).toMap();
        assertEquals(expected, actual);
    }

    @Test
    void testFilter() {
    }

    @Test
    void filterKey() {
    }

    @Test
    void filterValue() {
    }

    @Test
    void map() {
    }

    @Test
    void testMap() {
    }

    @Test
    void mapKey() {
    }

    @Test
    void testMapKey() {
    }

    @Test
    void mapValue() {
    }

    @Test
    void testMapValue() {
    }

    @Test
    void mapToInt() {
    }

    @Test
    void testMapToInt() {
    }

    @Test
    void mapToLong() {
    }

    @Test
    void testMapToLong() {
    }

    @Test
    void mapToDouble() {
    }

    @Test
    void testMapToDouble() {
    }

    @Test
    void flatMap() {
    }

    @Test
    void testFlatMap() {
    }

    @Test
    void flatMapKey() {
    }

    @Test
    void testFlatMapKey() {
    }

    @Test
    void flatMapValue() {
    }

    @Test
    void testFlatMapValue() {
    }

    @Test
    void flatMapToInt() {
    }

    @Test
    void testFlatMapToInt() {
    }

    @Test
    void flatMapToLong() {
    }

    @Test
    void testFlatMapToLong() {
    }

    @Test
    void flatMapToDouble() {
    }

    @Test
    void testFlatMapToDouble() {
    }

    @Test
    void keys() {
    }

    @Test
    void values() {
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