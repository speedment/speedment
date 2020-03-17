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
import java.util.function.Consumer;

public final class SingletonSpliterator<T> implements Spliterator<T> {

    final T element;
    long estimatedSize = 1;

    public SingletonSpliterator(T element) {
        this.element = element; // Nullable
    }

    @Override
    public Spliterator<T> trySplit() {
        return null;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        if (estimatedSize > 0) {
            estimatedSize--;
            consumer.accept(element);
            return true;
        }
        return false;
    }

    @Override
    public void forEachRemaining(Consumer<? super T> consumer) {
        tryAdvance(consumer);
    }

    @Override
    public long estimateSize() {
        return estimatedSize;
    }

    @Override
    public int characteristics() {
        int value = (element != null) ? Spliterator.NONNULL : 0;
        // ORDERED can be derived from member flag
        return value | Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.IMMUTABLE
                | Spliterator.DISTINCT | Spliterator.ORDERED;
    }
}
