package com.speedment.common.singletonstream;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.speedment.common.singletonstream.StreamOperator.create;

final class LongStreamCombined2Test extends AbstractLongStreamTester {

    @Override
    Stream<StreamOperator<Long, LongStream>> streamOperators() {
        return Stream.of(
            create("filter", s -> s.filter(t -> t % 3 != 0)),
            create("map", s -> s.map(t -> t + 2)),
            create("mapToInt", s -> s.mapToInt(t -> ((int)t) + 3).mapToLong(i -> i)),
            create("flatMap", s -> s.flatMap(t -> LongStream.range(0, t))),
            create("sorted", LongStream::sorted),
            create("distinct", LongStream::distinct)
        );
    }

}