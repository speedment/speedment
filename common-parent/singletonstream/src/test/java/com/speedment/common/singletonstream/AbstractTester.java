/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.common.singletonstream;

import com.speedment.common.combinatorics.Combination;
import com.speedment.common.combinatorics.Permutation;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractTester<T, S extends BaseStream<T, S>> {

    final int INITIAL_VALUE = 1;

    abstract Stream<StreamOperator<T, S>> streamOperators();

    abstract S referenceStream();

    abstract S initialStream();

    abstract Function<S, List<T>> finisher();

    @TestFactory
    Stream<DynamicTest> testPermutations() {
        return Permutation.of(streamOperators().collect(toList()))
            .map(s -> s.collect(toList()))
            .map(l -> DynamicTest.dynamicTest(
                l.stream().map(StreamOperator::name).collect(Collectors.joining(", ")),
                () -> assertStreamsEqualWith(l))
            );
    }

    @TestFactory
    Stream<DynamicTest> testCombinations() {
        return Combination.of(streamOperators().collect(toList()))
            .map(l -> DynamicTest.dynamicTest(
                l.stream().map(StreamOperator::name).collect(Collectors.joining(", ")),
                () -> assertStreamsEqualWith(l))
            );
    }

    @Test
    void noOperator() {
        assertStreamsEqualWith(emptyList());
    }

    @TestFactory
    Stream<DynamicTest> oneOperator() {
        return streamOperators()
            .map(o -> DynamicTest.dynamicTest(
                o.name(),
                () -> assertStreamsEqualWith(singletonList(o)))
            );
    }


    void assertStreamsEqualWith(List<StreamOperator<T, S>> operators) {
        try (final S expectedStream = evaluate(referenceStream(), operators);
             final S actualStream = evaluate(initialStream(), operators)) {

            assertEquals(expectedStream.isParallel(), actualStream.isParallel());
            final List<T> expected = finisher().apply(expectedStream);
            final List<T> actual = finisher().apply(actualStream);
            assertEquals(expected, actual);
        }

    }

    S evaluate(S s, List<StreamOperator<T, S>> operators) {
        return operators.stream().reduce(s, this::accumulate, this::combine);
    }

    S accumulate(S s, StreamOperator<T, S> operator) {
        return operator.apply(s);
    }

    S combine(S s0, S s1) {
        throw new UnsupportedOperationException();
    }

}
