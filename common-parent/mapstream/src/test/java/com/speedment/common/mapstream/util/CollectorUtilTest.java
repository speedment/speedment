package com.speedment.common.mapstream.util;

import com.speedment.common.mapstream.MapStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class CollectorUtilTest {

    private Collector<String, ?, MapStream<Integer, List<String>>> collector;

    @BeforeEach
    void setup() {
        collector = CollectorUtil.groupBy(String::length);
    }

    @Test
    void groupBy() {
        final Map<Integer, List<String>> expected = new HashMap<>();
        expected.put(1, Stream.of("A", "A").collect(toList()));
        expected.put(2, Collections.singletonList("BB"));
        final Map<Integer, List<String>> actual = Stream.of("A", "BB", "A").collect(collector).toMap();
        assertEquals(expected, actual);
    }

    @Test
    void groupHolder() {
        final CollectorUtil.GroupHolder<Integer, String> holder1 = new CollectorUtil.GroupHolder<>(String::length);
        final CollectorUtil.GroupHolder<Integer, String> holder2 = new CollectorUtil.GroupHolder<>(String::length);
        holder1.add("A");
        holder2.add("B");
        holder1.merge(holder2);

        final Map<Integer, List<String>> expected = new HashMap<>();
        expected.put(1, Stream.of("A", "B").collect(toList()));
        final Map<Integer, List<String>> actual = holder1.finisher().toMap();
        assertEquals(expected, actual);
    }

}