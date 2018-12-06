package com.speedment.runtime.core.internal.stream.autoclose;

import com.speedment.runtime.core.internal.util.java9.Java9StreamUtil;

import java.util.Comparator;
import java.util.stream.*;

import static com.speedment.runtime.core.internal.stream.autoclose.AutoClosingStreamTestUtil.MAX_VALUE;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

final class AutoClosingReferenceStreamTest extends AbstractAutoClosingStreamTest<Integer, Stream<Integer>> {

    @Override
    Stream<Integer> createStream() {
        return Stream.iterate(0, i -> i++).limit(32);
    }

    @Override
    Stream<Integer> createAutoclosableStream() {
        return new AutoClosingReferenceStream<>(createStream());
    }

    @Override
    void assertTypedArraysEquals(Object expected, Object actual) {
        assertArrayEquals((Object[]) expected, (Object[]) actual);
    }

    @Override
    Stream<NamedUnaryOperator<Stream<Integer>>> intermediateJava8Operations() {
        return Stream.of(
            NamedUnaryOperator.of("filter", s -> s.filter(i -> i < MAX_VALUE / 2)),
            NamedUnaryOperator.of("map", s -> s.map(i -> i + 1)),
            // mapToInt, mapToLong, mapToDouble
            NamedUnaryOperator.of("flatMap", s -> s.flatMap(i -> Stream.of(i, i + 1))),
            // flatMapToInt, flatMapToLong, flatMapToDouble
            NamedUnaryOperator.of("distinct", Stream::distinct),
            NamedUnaryOperator.of("sorted", Stream::sorted),
            NamedUnaryOperator.of("sorted1Arg", s -> s.sorted(Comparator.reverseOrder())),
            NamedUnaryOperator.of("peek", s -> s.peek(blackHole())),
            NamedUnaryOperator.of("limit", s -> s.limit(MAX_VALUE - 2)),
            NamedUnaryOperator.of("skip", s -> s.skip(3)),
            NamedUnaryOperator.of("parallel", BaseStream::parallel),
            NamedUnaryOperator.of("sequential", BaseStream::sequential),
            NamedUnaryOperator.of("unordered", BaseStream::unordered)
        );

    }

    @Override
    Stream<NamedUnaryOperator<Stream<Integer>>> intermediateJava9Operations() {
        return Stream.of(
            NamedUnaryOperator.of("takeWhile", s -> Java9StreamUtil.takeWhile(s, i -> i < MAX_VALUE / 3)),
            NamedUnaryOperator.of("dropWhile", s -> Java9StreamUtil.dropWhile(s, i -> i < MAX_VALUE / 3))
        );

    }

    @Override
    Stream<NamedFunction<Stream<Integer>, Object>> terminatingOperations() {
        return Stream.<NamedFunction<Stream<Integer>, Object>>of(
            NamedFunction.of("count", Stream::count),
            NamedFunction.of("forEach", (Stream<Integer> s) -> {
                s.forEach(blackHole());
                return void.class;
            }),
            NamedFunction.of("forEachOrdered", (Stream<Integer> s) -> {
                s.forEachOrdered(blackHole());
                return void.class;
            }),
            NamedFunction.of("toArray", Stream::toArray),
            NamedFunction.of("toArray1Arg", s -> s.toArray(Integer[]::new)),
            NamedFunction.of("reduce2Arg", s -> s.reduce(0, Integer::sum)),
            NamedFunction.of("reduce1Arg", s -> s.reduce(Integer::sum)),
            NamedFunction.of("reduce3Arg", s -> s.reduce(0, Integer::sum, Integer::sum)),
            NamedFunction.of("collect3Arg", s -> s.collect(() -> 0, Integer::sum, Integer::sum)),
            NamedFunction.of("collect1Arg", s -> s.collect(toList())),
            NamedFunction.of("min", s -> s.min(Integer::compareTo)),
            NamedFunction.of("max", s -> s.max(Integer::compareTo)),
            NamedFunction.of("anyMatch", s -> s.anyMatch(i -> i > 2)),
            NamedFunction.of("allMatch", s -> s.allMatch(i -> i == 2)),
            NamedFunction.of("noneMatch", s -> s.noneMatch(i -> i == 2)),
            NamedFunction.of("findFirst", Stream::findFirst),
            NamedFunction.of("findAny", Stream::findAny),
            // Simulated ones for mapToX and flatMapTox
            NamedFunction.of(".mapToInt.sum", s -> s.mapToInt(i -> i).sum()),
            NamedFunction.of(".mapToLong.sum", s -> s.mapToLong(i -> i).sum()),
            NamedFunction.of(".mapToDouble.sum", s -> s.mapToDouble(i -> i).sum()),
            NamedFunction.of(".flatMapToInt.sum", s -> s.flatMapToInt(i -> IntStream.of(i , i+1)).sum()),
            NamedFunction.of(".flatMapToLong.sum", s -> s.flatMapToLong(i -> LongStream.of(i , i+1)).sum()),
            NamedFunction.of(".flatMapToDouble.sum", s -> s.flatMapToDouble(i -> DoubleStream.of(i , i+1)).sum())
        );
    }

}