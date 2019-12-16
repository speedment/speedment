package com.speedment.common.singletonstream;

import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.speedment.common.singletonstream.StreamOperator.create;

final class StreamCombinedTest extends AbstractStreamTester {

    @Override
    Stream<StreamOperator<Integer, Stream<Integer>>> streamOperators() {
        return Stream.of(
            create("filter", s -> s.filter(t -> t % 3 != 0)),
            create("map", s -> s.map(t -> t + 2)),
            create("flatMapToInt", s -> s.flatMapToInt(t -> IntStream.range(0, t)).boxed()),
            create("flatMapToLong", s -> s.flatMapToLong(t -> LongStream.range(0, t)).mapToInt(i -> (int) i).boxed()),
            create("limit", s -> s.limit(1)),
            create("skip", s -> s.skip(1))
        );
    }

}