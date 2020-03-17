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

import com.speedment.runtime.core.internal.util.testing.JavaVersionUtil;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Execution(ExecutionMode.CONCURRENT)
abstract class AbstractAutoClosingStreamTest<T, S extends BaseStream<T, S>> {

    // Todo: iterator, spliterator etc.

    @TestFactory
    Stream<DynamicTest> testTerm() {
        return test(NamedUnaryOperator.of("", UnaryOperator.identity()));
    }

    @TestFactory
    Stream<DynamicTest> testInterTerm() {
        return intermediateOperations()
            .flatMap(this::test);
    }

    @TestFactory
    Stream<DynamicTest> testInterInterTerm() {
        return intermediateOperations()
            .flatMap(iop1 ->
                intermediateOperations()
                    .map(iop1::andThenNamed)
            )
            .flatMap(this::test);
    }

    @Test
    void testAutoCloseSingleCloseProperty() {
        final AtomicInteger cnt = new AtomicInteger();
        try (S stream = createAutoclosableStream()) {
            stream.onClose(cnt::incrementAndGet);
            stream.close();
        }
        assertEquals(1, cnt.get());
    }

    private Stream<DynamicTest> test(NamedUnaryOperator<S> iop) {
        return terminatingOperations()
            .map(top -> DynamicTest.dynamicTest(iop.name() + "." + top.name(), () -> {
                    final AtomicInteger cnt = new AtomicInteger();

                    final Object actual = top.apply(
                        iop.apply(
                            createAutoclosableStream()
                                .onClose(cnt::incrementAndGet)
                        ).onClose(cnt::incrementAndGet)

                    );

                    final Object expected = top.apply(
                        iop.apply(
                            createStream()
                        )
                    );

                    assertEquals(2, cnt.get());
                    if (expected.getClass().isArray()) {
                        assertTypedArraysEquals(expected, actual);
                    } else {
                        assertEquals(expected, actual);
                    }
                })
            );
    }

    abstract S createStream();

    abstract S createAutoclosableStream();

    private Stream<NamedUnaryOperator<S>> intermediateOperations() {
        if (JavaVersionUtil.getJavaVersion() == JavaVersionUtil.JavaVersion.EIGHT) {
            return intermediateJava8Operations();
        } else {
            return Stream.concat(
               intermediateJava8Operations(),
               intermediateJava9Operations()
            );
        }
    }

    abstract Stream<NamedUnaryOperator<S>> intermediateJava8Operations();

    abstract Stream<NamedUnaryOperator<S>> intermediateJava9Operations();

    abstract Stream<NamedFunction<S, Object>> terminatingOperations();

    abstract void assertTypedArraysEquals(Object expected, Object actual);

/*    abstract IntStream toIntStream(S stream);*/

    <A> Consumer<A> blackHole() {
        return (A t) -> {};
    }

    LongConsumer longBlackHole() {
        return (long t) -> {};
    }

    IntConsumer intBlackHole() {
        return (int t) -> {};
    }

    DoubleConsumer doubleBlackHole() {
        return (double t) -> {};
    }

}