package com.speedment.runtime.core.internal.stream.autoclose;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class AutoClosingReferenceStreamTest {

    private AtomicInteger closeCounter;

    @BeforeEach
    void setup() {
        closeCounter = new AtomicInteger();
    }

    @Test
    void test() {
        new AutoClosingReferenceStream<>(createStream())
            .onClose(closeHandler())
            .count();

        assertEquals(1, closeCounter.get());

    }

    private Stream<Integer> createStream() {
        return Stream.of(0, 1, 2, 3);
    }

    private Runnable closeHandler() {
        return () -> closeCounter.incrementAndGet();
    }

    private Stream<Consumer<Stream<Integer>>> terminatingOperations() {
        return Stream.of(
            Stream::count,
            s -> s.forEach(t -> { }),
            s -> s.forEachOrdered(t -> { }),
            Stream::toArray,
            s -> s.toArray(Integer[]::new),
            s -> s.reduce(0, Integer::sum),
            s -> s.reduce(Integer::sum),
            s -> s.reduce(0, Integer::sum, Integer::sum),
            s -> s.collect(() -> 0, Integer::sum, Integer::sum),
            s -> s.collect(toList()),
            s -> s.min(Integer::compareTo),
            s -> s.max(Integer::compareTo),
            s -> s.anyMatch(i -> i > 2),
            s -> s.allMatch(i -> i == 2),
            s -> s.noneMatch(i -> i == 2),
            Stream::findFirst,
            Stream::findAny
        );
    }



}