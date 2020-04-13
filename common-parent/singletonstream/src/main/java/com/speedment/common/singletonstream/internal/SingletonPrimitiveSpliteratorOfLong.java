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

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.LongConsumer;

public final class SingletonPrimitiveSpliteratorOfLong implements Spliterator.OfLong {

    private final long element;
    private long estimatedSize = 1;

    public SingletonPrimitiveSpliteratorOfLong(long element) {
        this.element = element;
    }

    @Override
    public OfLong trySplit() {
        return null;
    }

    @Override
    public boolean tryAdvance(LongConsumer consumer) {
        Objects.requireNonNull(consumer);
        if (estimatedSize > 0) {
            estimatedSize--;
            consumer.accept(element);
            return true;
        }
        return false;
    }

    @Override
    public void forEachRemaining(LongConsumer consumer) {
        tryAdvance(consumer);
    }

    @Override
    public long estimateSize() {
        return estimatedSize;
    }

    @Override
    public int characteristics() {
        return Spliterator.NONNULL | Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.IMMUTABLE
                | Spliterator.DISTINCT | Spliterator.ORDERED;
    }
}
