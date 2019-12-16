package com.speedment.common.singletonstream;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.speedment.common.singletonstream.StreamOperator.create;

final class StreamCombined2Test extends AbstractStreamTester {

    @Override
    Stream<StreamOperator<Integer, Stream<Integer>>> streamOperators() {
        return Stream.of(
            create("filter", s -> s.filter(t -> t % 3 != 0)),
            create("map", s -> s.map(t -> t + 2)),
            create("mapToInt", s -> s.mapToInt(t -> t + 3).boxed()),
            create("flatMapToInt", s -> s.flatMapToInt(t -> IntStream.range(0, t)).boxed()),
            create("sorted", Stream::sorted),
            create("distinct", Stream::distinct)
        );
    }

}