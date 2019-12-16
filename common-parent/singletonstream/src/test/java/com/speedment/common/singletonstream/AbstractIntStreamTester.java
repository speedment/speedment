package com.speedment.common.singletonstream;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

abstract class AbstractIntStreamTester extends AbstractTester<Integer, IntStream> {

    @Override
    Function<IntStream, List<Integer>> finisher() {
        return s -> s.boxed().collect(Collectors.toList());
    }

    @Override
    IntStream referenceStream() {
        return IntStream.of(INITIAL_VALUE);
    }

    @Override
    IntStream initialStream() {
        return SingletonIntStream.of(INITIAL_VALUE);
    }
}