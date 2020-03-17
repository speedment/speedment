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
package com.speedment.runtime.core.internal.stream.autoclose;

import com.speedment.runtime.core.stream.java9.Java9StreamUtil;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.concurrent.atomic.LongAdder;
import java.util.stream.*;

import static com.speedment.runtime.core.internal.stream.autoclose.AutoClosingStreamTestUtil.MAX_VALUE;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@Execution(ExecutionMode.CONCURRENT)
final class AutoClosingLongStreamTest extends AbstractAutoClosingStreamTest<Long, LongStream> {

    @Override
    LongStream createStream() {
        return LongStream.iterate(0, i -> i++).limit(32);
    }

    @Override
    LongStream createAutoclosableStream() {
        return new AutoClosingLongStream(createStream(),  false);
    }

    @Override
    void assertTypedArraysEquals(Object expected, Object actual) {
        assertArrayEquals((long[]) expected, (long[]) actual);
    }

    @Override
    Stream<NamedUnaryOperator<LongStream>> intermediateJava8Operations() {
        return Stream.of(
            NamedUnaryOperator.of("filter", s -> s.filter(i -> i < MAX_VALUE / 2)),
            NamedUnaryOperator.of("map", s -> s.map(i -> i + 1)),
            // mapToInt, mapToLong, mapToDouble
            NamedUnaryOperator.of("flatMap", s -> s.flatMap(i -> LongStream.of(i, i + 1))),
            // flatMapToInt, flatMapToLong, flatMapToDouble
            NamedUnaryOperator.of("distinct", LongStream::distinct),
            NamedUnaryOperator.of("sorted", LongStream::sorted),
            NamedUnaryOperator.of("peek", s -> s.peek(longBlackHole())),
            NamedUnaryOperator.of("limit", s -> s.limit(MAX_VALUE - 2)),
            NamedUnaryOperator.of("skip", s -> s.skip(3)),
            NamedUnaryOperator.of("parallel", LongStream::parallel),
            NamedUnaryOperator.of("sequential", LongStream::sequential),
            NamedUnaryOperator.of("unordered", LongStream::unordered)
        );

    }

    @Override
    Stream<NamedUnaryOperator<LongStream>> intermediateJava9Operations() {
        return Stream.of(
            NamedUnaryOperator.of("takeWhile", s -> Java9StreamUtil.takeWhile(s, i -> i < MAX_VALUE / 3)),
            NamedUnaryOperator.of("dropWhile", s -> Java9StreamUtil.dropWhile(s, i -> i < MAX_VALUE / 3))
        );

    }

    @Override
    Stream<NamedFunction<LongStream, Object>> terminatingOperations() {
        return Stream.of(
            NamedFunction.of("count", LongStream::count),
            NamedFunction.of("forEach", (LongStream s) -> {
                s.forEach(longBlackHole());
                return void.class;
            }),
            NamedFunction.of("forEachOrdered", (LongStream s) -> {
                s.forEachOrdered(longBlackHole());
                return void.class;
            }),
            NamedFunction.of("toArray", LongStream::toArray),
            NamedFunction.of("reduce2Arg", s -> s.reduce(0L, (a, b) -> a + b)),
            NamedFunction.of("reduce1Arg", s -> s.reduce((a, b) -> a + b)),
            NamedFunction.of("collect3Arg", s -> s.collect(LongAdder::new, LongAdder::add, (a, b) -> a.add(b.longValue())).longValue()),
            NamedFunction.of("min", LongStream::min),
            NamedFunction.of("max", LongStream::max),
            NamedFunction.of("anyMatch", s -> s.anyMatch(i -> i > 2)),
            NamedFunction.of("allMatch", s -> s.allMatch(i -> i == 2)),
            NamedFunction.of("noneMatch", s -> s.noneMatch(i -> i == 2)),
            NamedFunction.of("findFirst", LongStream::findFirst),
            NamedFunction.of("findAny", LongStream::findAny),
            NamedFunction.of("average", LongStream::average),
            NamedFunction.of("summaryStatistics", s -> s.summaryStatistics().getSum()),

            // Simulated ones for mapToX and flatMapTox etc.
            NamedFunction.of(".boxed.collect", s -> s.boxed().collect(toList())),
            NamedFunction.of(".asDoubleStream.sum", s -> s.asDoubleStream().sum()),
            NamedFunction.of(".mapToInt.sum", s -> s.mapToInt(i -> (int)i).sum()),
            NamedFunction.of(".mapToObj.sum", s -> s.mapToObj(i -> i).collect(toList())),
            NamedFunction.of(".mapToDouble.sum", s -> s.mapToDouble(i -> i).sum())

        );
    }

}