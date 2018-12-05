package com.speedment.runtime.core.internal.stream.autoclose;

import com.speedment.runtime.core.internal.util.testing.JavaVersionUtil;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class AbstractAutoClosingStreamTest<T, S extends BaseStream<T, S>> {

    // Todo: Test parallel, sequential, iterator, spliterator etc.

    // Todo: test isParallel()

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