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
package com.speedment.runtime.core.internal.stream.parallel;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
final class SingletonSpliterator<T> implements Spliterator<T> {

    private final Object item;
    private final int characteristics;
    private boolean consumed;

    /**
     * Creates a {@link Spliterator} for a single item.
     *
     * @param item item to use
     * @param additionalCharacteristics Additional {@link Spliterator}
     * characteristics of this {@link Spliterator}'s source or elements beyond
     * {@code SIZED} and {@code SUBSIZED} which are are always reported
     */
    public SingletonSpliterator(Object item, int additionalCharacteristics) {
        this.item = item;
        this.characteristics = additionalCharacteristics | Spliterator.SIZED | Spliterator.SUBSIZED;
        this.consumed = false;
    }

    @Override
    public Spliterator<T> trySplit() {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        requireNonNull(action);
        if (!consumed) {
            action.accept((T) item);
            consumed = true;
        }
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        requireNonNull(action);
        if (!consumed) {
            @SuppressWarnings("unchecked")
            final T e = (T) item;
            action.accept(e);
            consumed = true;
            return true;
        }
        return false;
    }

    @Override
    public long estimateSize() {
        return consumed ? 0 : 1;
    }

    @Override
    public int characteristics() {
        return characteristics;
    }

    @Override
    public Comparator<? super T> getComparator() {
        if (hasCharacteristics(Spliterator.SORTED)) {
            return null;
        }
        throw new IllegalStateException();
    }
}
