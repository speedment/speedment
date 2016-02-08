/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.stream.parallelstrategy;

import java.util.Comparator;
import static java.util.Objects.requireNonNull;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 *
 * @author pemi
 */
final class ArraySpliterator<T> implements Spliterator<T> {

    private final Object[] array;
    private final int size;
    private final int characteristics;
    private int index;

    /**
     * Creates a {@link Spliterator} covering all of the given array.
     *
     * @param array the array, assumed to be unmodified during use
     * @param additionalCharacteristics Additional {@link Spliterator}
     * characteristics of this {@link Spliterator}'s source or elements beyond
     * {@code SIZED} and {@code SUBSIZED} which are are always reported
     */
    public ArraySpliterator(Object[] array, int additionalCharacteristics) {
        this(array, 0, array.length, additionalCharacteristics);
    }

    /**
     * Creates a {@link Spliterator} covering the given array and range.
     *
     * @param array the array, assumed to be unmodified during use
     * @param origin the least index (inclusive) to cover
     * @param size one past the greatest index to cover
     * @param additionalCharacteristics Additional {@link Spliterator}
     * characteristics of this {@link Spliterator}'s source or elements beyond
     * {@code SIZED} and {@code SUBSIZED} which are are always reported
     */
    public ArraySpliterator(Object[] array, int origin, int size, int additionalCharacteristics) {
        this.array = array;
        this.index = origin;
        this.size = size;
        this.characteristics = additionalCharacteristics | Spliterator.SIZED | Spliterator.SUBSIZED;
    }

    @Override
    public Spliterator<T> trySplit() {
        final int lo = index;
        final int mid = (lo + size) >>> 1;
        return (lo >= mid)
                ? null
                : new ArraySpliterator<>(array, lo, index = mid, characteristics);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        requireNonNull(action);
        int i, hi;
        if (array.length >= (hi = size) && (i = index) >= 0 && i < (index = hi)) {
            do {
                action.accept((T) array[i]);
            } while (++i < hi);
        }
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        requireNonNull(action);
        if (index >= 0 && index < size) {
            @SuppressWarnings("unchecked")
            final T e = (T) array[index++];
            action.accept(e);
            return true;
        }
        return false;
    }

    @Override
    public long estimateSize() {
        return (long) (size - index);
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
