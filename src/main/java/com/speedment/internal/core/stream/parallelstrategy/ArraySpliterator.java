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
