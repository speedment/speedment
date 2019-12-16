package com.speedment.common.singletonstream;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

abstract class AbstractLongStreamTester extends AbstractTester<Long, LongStream> {

    @Override
    Function<LongStream, List<Long>> finisher() {
        return s -> s.boxed().collect(Collectors.toList());
    }

    @Override
    LongStream referenceStream() {
        return LongStream.of(INITIAL_VALUE);
    }

    @Override
    LongStream initialStream() {
        return SingletonLongStream.of(INITIAL_VALUE);
    }
}