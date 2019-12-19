package com.speedment.common.singletonstream.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

import static org.junit.jupiter.api.Assertions.*;

final class SingletonPrimitiveIteratorOfIntTest {

    private static final int ELEMENT = 1;
    private SingletonPrimitiveIteratorOfInt instance;
    private AtomicInteger cnt;

    @BeforeEach
    void setup() {
        instance = new SingletonPrimitiveIteratorOfInt(ELEMENT);
        cnt = new AtomicInteger();
    }

    @Test
    void hasNext() {
        assertTrue(instance.hasNext());
    }

    @Test
    void hasNextAfterConsumed() {
        instance.next();
        assertFalse(instance.hasNext());
    }

    @Test
    void nextInt() {
        assertEquals(ELEMENT, instance.nextInt());
    }

    @Test
    void next() {
        assertEquals(ELEMENT, instance.next());
    }

    @Test
    void nextAfterNext() {
        instance.next();
        assertThrows(NoSuchElementException.class, instance::next);
    }

    @Test
    void remove() {
        assertThrows(UnsupportedOperationException.class, instance::remove);
    }

    @Test
    void forEachRemainingIntConsumer() {
        instance.forEachRemaining((IntConsumer)i -> cnt.incrementAndGet());
        assertEquals(1, cnt.get());
    }

    @Test
    void forEachRemainingConsumer() {
        instance.forEachRemaining((Consumer<Integer>) i -> cnt.incrementAndGet());
        assertEquals(1, cnt.get());
    }


    @Test
    void forEachRemainingIntConsumerAfterNext() {
        instance.next();
        instance.forEachRemaining((IntConsumer)i -> cnt.incrementAndGet());
        assertEquals(0, cnt.get());
    }

    @Test
    void forEachRemainingConsumerAfterNext() {
        instance.next();
        instance.forEachRemaining((Consumer<Integer>) i -> cnt.incrementAndGet());
        assertEquals(0, cnt.get());
    }


}