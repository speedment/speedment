package com.speedment.common.singletonstream;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.speedment.common.singletonstream.StreamOperator.create;

final class LongStreamCombined3Test extends AbstractLongStreamTester {

    @Override
    Stream<StreamOperator<Long, LongStream>> streamOperators() {
        return Stream.of(
            create("filter", s -> s.filter(t -> t % 3 != 0)),
            create("flatMap", s -> s.flatMap(t -> LongStream.range(0, t))),
            create("parallel", LongStream::parallel),
            create("sequential", LongStream::sequential),
            create("unordered", LongStream::unordered)
        );
    }

}