package com.speedment.common.singletonstream;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.speedment.common.singletonstream.StreamOperator.create;

final class StreamCombined3Test extends AbstractStreamTester {

    @Override
    Stream<StreamOperator<Integer, Stream<Integer>>> streamOperators() {
        return Stream.of(
            create("filter", s -> s.filter(t -> t % 3 != 0)),
            create("flatMapToInt", s -> s.flatMapToInt(t -> IntStream.range(0, t)).boxed()),
            create("sorted()", s -> s.sorted(Comparator.reverseOrder())),
            create("parallel", Stream::parallel),
            create("sequential", Stream::sequential),
            create("unordered", Stream::unordered)
        );
    }

}