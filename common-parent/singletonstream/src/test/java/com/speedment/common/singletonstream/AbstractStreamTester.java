package com.speedment.common.singletonstream;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class AbstractStreamTester extends AbstractTester<Integer, Stream<Integer>> {

    @Override
    Function<Stream<Integer>, List<Integer>> finisher() {
        return s -> s.collect(Collectors.toList());
    }

    @Override
    Stream<Integer> referenceStream() {
        return Stream.of(INITIAL_VALUE);
    }

    @Override
    Stream<Integer> initialStream() {
        return SingletonStream.of(INITIAL_VALUE);
    }
}