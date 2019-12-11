package com.speedment.common.function;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

final class LongBiConsumerTest {

    @Test
    void accept() {
        final AtomicLong atomicLong = new AtomicLong();
        final LongBiConsumer consumer = (a, b) -> atomicLong.set(a + b);
        consumer.accept(1, 2);
        assertEquals(3, atomicLong.get());
    }

    @Test
    void andThen() {
        final AtomicLong atomicLong = new AtomicLong();
        final AtomicLong atomicLong2 = new AtomicLong();
        final LongBiConsumer consumer = (a, b) -> atomicLong.set(a + b);
        final LongBiConsumer consumer2 = (a, b) -> atomicLong2.set(2 * (a + b));
        final LongBiConsumer combined = consumer.andThen(consumer2);
        combined.accept(1, 2);
        assertEquals(3, atomicLong.get());
        assertEquals(3 * 2, atomicLong2.get());
    }
}