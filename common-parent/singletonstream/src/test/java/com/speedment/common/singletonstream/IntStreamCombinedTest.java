package com.speedment.common.singletonstream;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.speedment.common.singletonstream.StreamOperator.create;

final class IntStreamCombinedTest extends AbstractIntStreamTester {

    @Override
    Stream<StreamOperator<Integer, IntStream>> streamOperators() {
        return Stream.of(
            create("filter", s -> s.filter(t -> t % 3 != 0)),
            create("map", s -> s.map(t -> t + 2)),
            create("flatMap", s -> s.flatMap(t -> IntStream.range(0, t))),
            create("limit", s -> s.limit(1)),
            create("skip", s -> s.skip(1))
        );
    }

}