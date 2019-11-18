package com.speedment.common.tuple;

import com.speedment.common.tuple.getter.TupleGetter;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.speedment.common.tuple.TuplesTestUtil.LARGE_TUPLE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class TupleExtraTest {

    public static final int MAX = 100;

    @TestFactory
    Stream<DynamicTest> stream() {
        return IntStream.range(0, MAX)
            .mapToObj(i -> DynamicTest.dynamicTest("Tuple.stream(" + i + ")", () -> streamTest(i)));
    }

    @TestFactory
    Stream<DynamicTest> streamTypeInteger() {
        return IntStream.range(0, MAX)
            .mapToObj(i -> DynamicTest.dynamicTest("Tuple.streamOfInteger(" + i + ")", () -> streamTestTyped(i, Integer.class)));
    }

    @TestFactory
    Stream<DynamicTest> streamTypeString() {
        return IntStream.range(0, MAX)
            .mapToObj(i -> DynamicTest.dynamicTest("Tuple.streamOfString(" + i + ")", () -> streamTestTyped(i, String.class)));
    }

    @TestFactory
    Stream<DynamicTest> getter() {
        return IntStream.range(0, MAX)
            .mapToObj(i -> DynamicTest.dynamicTest("Tuple.getter(" + i + ")", () -> getterTest(i)));
    }

    private void streamTest(int i) {
        final Tuple tuple = TuplesTestUtil.createTupleFilled(i);
        final Integer[] actual = tuple.stream().map(Integer.class::cast).toArray(Integer[]::new);
        assertArrayEquals(TuplesTestUtil.array(i), actual);
    }

    private void streamTestTyped(int i, Class<?> clazz) {
        final Tuple tuple = TuplesTestUtil.createTupleFilled(i);
        final Integer[] actual = tuple.streamOf(clazz).map(Integer.class::cast).toArray(Integer[]::new);
        if (Integer.class.equals(clazz)) {
            assertArrayEquals(TuplesTestUtil.array(i), actual);
        } else {
            assertEquals(0, actual.length);
        }
    }

    void getterTest(int i) {
        final TupleGetter<Tuple, Integer> getter = Tuple.getter(i);
        assertEquals(i, getter.index());
        assertEquals(i, getter.apply(LARGE_TUPLE));
    }

}
