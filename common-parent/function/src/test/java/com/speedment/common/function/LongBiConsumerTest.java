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