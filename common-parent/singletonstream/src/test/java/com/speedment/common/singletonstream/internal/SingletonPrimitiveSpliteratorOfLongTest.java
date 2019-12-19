package com.speedment.common.singletonstream.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import static org.junit.jupiter.api.Assertions.*;

final class SingletonPrimitiveSpliteratorOfLongTest {

    private static final long ELEMENT = 1;
    private SingletonPrimitiveSpliteratorOfLong instance;
    private AtomicInteger cnt;

    @BeforeEach
    void setup() {
        instance = new SingletonPrimitiveSpliteratorOfLong(ELEMENT);
        cnt = new AtomicInteger();
    }

    @Test
    void trySplit() {
        assertNull(instance.trySplit());
    }

    @Test
    void tryAdvanceIntConsumer() {
        assertTrue(instance.tryAdvance((LongConsumer) i -> cnt.incrementAndGet()));
        assertEquals(1, cnt.get());
    }

    @Test
    void tryAdvanceConsumer() {
        assertTrue(instance.tryAdvance((Consumer<Long>) i -> cnt.incrementAndGet()));
        assertEquals(1, cnt.get());
    }

    @Test
    void tryAdvanceIntConsumerAfterAdvance() {
        instance.tryAdvance((LongConsumer) i -> {});
        assertFalse(instance.tryAdvance((LongConsumer) i -> cnt.incrementAndGet()));
        assertEquals(0, cnt.get());
    }

    @Test
    void tryAdvanceConsumerAfterAdvance() {
        instance.tryAdvance((Consumer<Long>) i -> {});
        assertFalse(instance.tryAdvance((Consumer<Long>) i -> cnt.incrementAndGet()));
        assertEquals(0, cnt.get());
    }

    @Test
    void forEachRemainingIntConsumer() {
        instance.forEachRemaining((LongConsumer) i -> cnt.incrementAndGet());
        assertEquals(1, cnt.get());
    }

    @Test
    void forEachRemainingConsumer() {
        instance.forEachRemaining((Consumer<Long>) i -> cnt.incrementAndGet());
        assertEquals(1, cnt.get());
    }

    @Test
    void forEachRemainingIntConsumerAfterAdvance() {
        instance.tryAdvance((Consumer<Long>) i -> {});
        instance.forEachRemaining((LongConsumer) i -> cnt.incrementAndGet());
        assertEquals(0, cnt.get());
    }

    @Test
    void forEachRemainingConsumerAfterAdvance() {
        instance.tryAdvance((Consumer<Long>) i -> {});
        instance.forEachRemaining((Consumer<Long>) i -> cnt.incrementAndGet());
        assertEquals(0, cnt.get());
    }

    @Test
    void estimateSize() {
        assertEquals(1L, instance.estimateSize());
    }

    @Test
    void estimateSizeAfterAdvance() {
        instance.tryAdvance((Consumer<Long>) i -> {});
        assertEquals(0L, instance.estimateSize());
    }

    @Test
    void characteristics() {
        assertNotEquals(0, instance.characteristics() & Spliterator.NONNULL);
        assertNotEquals(0, instance.characteristics() & Spliterator.SIZED);
        assertNotEquals(0, instance.characteristics() & Spliterator.SUBSIZED);
        assertNotEquals(0, instance.characteristics() & Spliterator.IMMUTABLE);
        assertNotEquals(0, instance.characteristics() & Spliterator.DISTINCT);
        assertNotEquals(0, instance.characteristics() & Spliterator.ORDERED);
    }

}