package com.speedment.common.singletonstream;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.speedment.common.singletonstream.StreamOperator.create;

final class IntStreamCombined2Test extends AbstractIntStreamTester {

    @Override
    Stream<StreamOperator<Integer, IntStream>> streamOperators() {
        return Stream.of(
            create("filter", s -> s.filter(t -> t % 3 != 0)),
            create("map", s -> s.map(t -> t + 2)),
            create("mapToLong", s -> s.mapToLong(t -> t + 3).mapToInt(i -> (int) i)),
            create("flatMap", s -> s.flatMap(t -> IntStream.range(0, t))),
            create("sorted", IntStream::sorted),
            create("distinct", IntStream::distinct)
        );
    }

}