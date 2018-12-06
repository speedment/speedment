package com.speedment.runtime.core.internal.stream.autoclose;

import com.speedment.runtime.core.internal.util.java9.Java9StreamUtil;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.speedment.runtime.core.internal.stream.autoclose.AutoClosingStreamTestUtil.MAX_VALUE;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

final class AutoClosingDoubleStreamTest extends AbstractAutoClosingStreamTest<Double, DoubleStream> {

    @Override
    DoubleStream createStream() {
        return DoubleStream.iterate(0, i -> i++).limit(32);
    }

    @Override
    DoubleStream createAutoclosableStream() {
        return new AutoClosingDoubleStream(createStream(), new HashSet<>(), false);
    }

    @Override
    void assertTypedArraysEquals(Object expected, Object actual) {
        assertArrayEquals((double[]) expected, (double[]) actual, 1e-6);
    }

    @Override
    Stream<NamedUnaryOperator<DoubleStream>> intermediateJava8Operations() {
        return Stream.<NamedUnaryOperator<DoubleStream>>of(
            NamedUnaryOperator.of("filter", s -> s.filter(i -> i < MAX_VALUE / 2)),
            NamedUnaryOperator.of("map", s -> s.map(i -> i + 1)),
            // mapToInt, mapToLong, mapToDouble
            NamedUnaryOperator.of("flatMap", s -> s.flatMap(i -> DoubleStream.of(i, i + 1))),
            // flatMapToInt, flatMapToLong, flatMapToDouble
            NamedUnaryOperator.of("distinct", DoubleStream::distinct),
            NamedUnaryOperator.of("sorted", DoubleStream::sorted),
            NamedUnaryOperator.of("peek", s -> s.peek(doubleBlackHole())),
            NamedUnaryOperator.of("limit", s -> s.limit(MAX_VALUE - 2)),
            NamedUnaryOperator.of("skip", s -> s.skip(3)),
            NamedUnaryOperator.of("parallel", BaseStream::parallel),
            NamedUnaryOperator.of("sequential", BaseStream::sequential),
            NamedUnaryOperator.of("unordered", BaseStream::unordered)
        );

    }

    @Override
    Stream<NamedUnaryOperator<DoubleStream>> intermediateJava9Operations() {
        return Stream.of(
            NamedUnaryOperator.of("takeWhile", s -> Java9StreamUtil.takeWhile(s, i -> i < MAX_VALUE / 3)),
            NamedUnaryOperator.of("dropWhile", s -> Java9StreamUtil.dropWhile(s, i -> i < MAX_VALUE / 3))
        );

    }

    @Override
    Stream<NamedFunction<DoubleStream, Object>> terminatingOperations() {
        return Stream.<NamedFunction<DoubleStream, Object>>of(
            NamedFunction.of("count", DoubleStream::count),
            NamedFunction.of("forEach", (DoubleStream s) -> {
                s.forEach(doubleBlackHole());
                return void.class;
            }),
            NamedFunction.of("forEachOrdered", (DoubleStream s) -> {
                s.forEachOrdered(doubleBlackHole());
                return void.class;
            }),
            NamedFunction.of("toArray", DoubleStream::toArray),
            NamedFunction.of("reduce2Arg", s -> s.reduce(0, (a, b) -> a + b)),
            NamedFunction.of("reduce1Arg", s -> s.reduce((a, b) -> a + b)),
            NamedFunction.of("collect3Arg", s -> s.collect(DoubleAdder::new, DoubleAdder::add, (a, b) -> a.add(b.longValue())).longValue()),
            NamedFunction.of("min", DoubleStream::min),
            NamedFunction.of("max", DoubleStream::max),
            NamedFunction.of("anyMatch", s -> s.anyMatch(i -> i > 2)),
            NamedFunction.of("allMatch", s -> s.allMatch(i -> i == 2)),
            NamedFunction.of("noneMatch", s -> s.noneMatch(i -> i == 2)),
            NamedFunction.of("findFirst", DoubleStream::findFirst),
            NamedFunction.of("findAny", DoubleStream::findAny),
            NamedFunction.of("summaryStatistics", s -> s.summaryStatistics().getSum()),

            // Simulated ones for mapToX and flatMapTox etc.
            NamedFunction.of(".boxed.collect", s -> s.boxed().collect(toList())),
            NamedFunction.of(".mapToInt.sum", s -> s.mapToInt(i -> (int)i).sum()),
            NamedFunction.of(".mapToLong.sum", s -> s.mapToLong(i -> (long)i).sum()),
            NamedFunction.of(".mapToObj.sum", s -> s.mapToObj(i -> i).collect(toList()))
        );


    }


}