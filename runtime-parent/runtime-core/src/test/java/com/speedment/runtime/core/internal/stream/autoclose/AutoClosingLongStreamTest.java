package com.speedment.runtime.core.internal.stream.autoclose;

import com.speedment.runtime.core.internal.util.java9.Java9StreamUtil;
import java.util.HashSet;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.BaseStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.speedment.runtime.core.internal.stream.autoclose.AutoClosingStreamTestUtil.MAX_VALUE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

final class AutoClosingLongStreamTest extends AbstractAutoClosingStreamTest<Long, LongStream> {

    @Override
    LongStream createStream() {
        return LongStream.iterate(0, i -> i++).limit(32);
    }

    @Override
    LongStream createAutoclosableStream() {
        return new AutoClosingLongStream(createStream(), new HashSet<>(), false);
    }

    @Override
    void assertTypedArraysEquals(Object expected, Object actual) {
        assertArrayEquals((long[]) expected, (long[]) actual);
    }

    @Override
    Stream<NamedUnaryOperator<LongStream>> intermediateJava8Operations() {
        return Stream.<NamedUnaryOperator<LongStream>>of(
            NamedUnaryOperator.of("filter", s -> s.filter(i -> i < MAX_VALUE / 2)),
            NamedUnaryOperator.of("map", s -> s.map(i -> i + 1)),
            // mapToInt, mapToLong, mapToDouble
            NamedUnaryOperator.of("flatMap", s -> s.flatMap(i -> LongStream.of(i, i + 1))),
            // flatMapToInt, flatMapToLong, flatMapToDouble
            NamedUnaryOperator.of("distinct", LongStream::distinct),
            NamedUnaryOperator.of("sorted", LongStream::sorted),
            NamedUnaryOperator.of("peek", s -> s.peek(longBlackHole())),
            NamedUnaryOperator.of("limit", s -> s.limit(MAX_VALUE - 2)),
            NamedUnaryOperator.of("skip", s -> s.skip(3)),
            NamedUnaryOperator.of("parallel", BaseStream::parallel),
            NamedUnaryOperator.of("sequential", BaseStream::sequential),
            NamedUnaryOperator.of("unordered", BaseStream::unordered)
        );

    }

    @Override
    Stream<NamedUnaryOperator<LongStream>> intermediateJava9Operations() {
        return Stream.of(
            NamedUnaryOperator.of("takeWhile", s -> Java9StreamUtil.takeWhile(s, i -> i < MAX_VALUE / 3)),
            NamedUnaryOperator.of("dropWhile", s -> Java9StreamUtil.dropWhile(s, i -> i < MAX_VALUE / 3))
        );

    }

    @Override
    Stream<NamedFunction<LongStream, Object>> terminatingOperations() {
        return Stream.<NamedFunction<LongStream, Object>>of(
            NamedFunction.of("count", LongStream::count),
            NamedFunction.of("forEach", (LongStream s) -> {
                s.forEach(longBlackHole());
                return void.class;
            }),
            NamedFunction.of("forEachOrdered", (LongStream s) -> {
                s.forEachOrdered(longBlackHole());
                return void.class;
            }),
            NamedFunction.of("toArray", LongStream::toArray),
            NamedFunction.of("reduce2Arg", s -> s.reduce(0L, (a, b) -> a + b)),
            NamedFunction.of("reduce1Arg", s -> s.reduce((a, b) -> a + b)),
            NamedFunction.of("collect3Arg", s -> s.collect(LongAdder::new, LongAdder::add, (a, b) -> a.add(b.longValue())).longValue()),
            NamedFunction.of("min", LongStream::min),
            NamedFunction.of("max", LongStream::max),
            NamedFunction.of("anyMatch", s -> s.anyMatch(i -> i > 2)),
            NamedFunction.of("allMatch", s -> s.allMatch(i -> i == 2)),
            NamedFunction.of("noneMatch", s -> s.noneMatch(i -> i == 2)),
            NamedFunction.of("findFirst", LongStream::findFirst),
            NamedFunction.of("findAny", LongStream::findAny)
        );
    }

}