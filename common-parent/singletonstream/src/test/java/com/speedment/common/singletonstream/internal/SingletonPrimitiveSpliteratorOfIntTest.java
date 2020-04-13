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
package com.speedment.common.singletonstream.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

import static org.junit.jupiter.api.Assertions.*;

final class SingletonPrimitiveSpliteratorOfIntTest {

    private static final int ELEMENT = 1;
    private SingletonPrimitiveSpliteratorOfInt instance;
    private AtomicInteger cnt;

    @BeforeEach
    void setup() {
        instance = new SingletonPrimitiveSpliteratorOfInt(ELEMENT);
        cnt = new AtomicInteger();
    }

    @Test
    void trySplit() {
        assertNull(instance.trySplit());
    }

    @Test
    void tryAdvanceIntConsumer() {
        assertTrue(instance.tryAdvance((IntConsumer) i -> cnt.incrementAndGet()));
        assertEquals(1, cnt.get());
    }

    @Test
    void tryAdvanceConsumer() {
        assertTrue(instance.tryAdvance((Consumer<Integer>) i -> cnt.incrementAndGet()));
        assertEquals(1, cnt.get());
    }

    @Test
    void tryAdvanceIntConsumerAfterAdvance() {
        instance.tryAdvance((IntConsumer) i -> {});
        assertFalse(instance.tryAdvance((IntConsumer) i -> cnt.incrementAndGet()));
        assertEquals(0, cnt.get());
    }

    @Test
    void tryAdvanceConsumerAfterAdvance() {
        instance.tryAdvance((Consumer<Integer>) i -> {});
        assertFalse(instance.tryAdvance((Consumer<Integer>) i -> cnt.incrementAndGet()));
        assertEquals(0, cnt.get());
    }

    @Test
    void forEachRemainingIntConsumer() {
        instance.forEachRemaining((IntConsumer) i -> cnt.incrementAndGet());
        assertEquals(1, cnt.get());
    }

    @Test
    void forEachRemainingConsumer() {
        instance.forEachRemaining((Consumer<Integer>) i -> cnt.incrementAndGet());
        assertEquals(1, cnt.get());
    }

    @Test
    void forEachRemainingIntConsumerAfterAdvance() {
        instance.tryAdvance((Consumer<Integer>) i -> {});
        instance.forEachRemaining((IntConsumer) i -> cnt.incrementAndGet());
        assertEquals(0, cnt.get());
    }

    @Test
    void forEachRemainingConsumerAfterAdvance() {
        instance.tryAdvance((Consumer<Integer>) i -> {});
        instance.forEachRemaining((Consumer<Integer>) i -> cnt.incrementAndGet());
        assertEquals(0, cnt.get());
    }

    @Test
    void estimateSize() {
        assertEquals(1L, instance.estimateSize());
    }

    @Test
    void estimateSizeAfterAdvance() {
        instance.tryAdvance((Consumer<Integer>) i -> {});
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