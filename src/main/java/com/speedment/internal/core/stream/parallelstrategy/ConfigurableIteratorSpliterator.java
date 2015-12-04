package com.speedment.internal.core.stream.parallelstrategy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 * @param <T> type of {@link Spliterator} to implement
 */
public final class ConfigurableIteratorSpliterator<T> implements Spliterator<T> {

    static final int MAX_BATCH = 1 << 25;
    private final Iterator<? extends T> iterator;
    private final int[] batchSizes;
    private final int characteristics;
    private int batchSizeIndex;
    private long sizeEstimate;

    /**
     * Creates a {@link Spliterator} using the given iterator for traversal, and
     * reporting the given initial size and characteristics.
     *
     * @param iterator the iterator for the source
     * @param size the number of elements in the source
     * @param characteristics properties of this {@link Spliterator}'s source or
     * elements.
     * @param batchSizes the batch sizes to use for pseudo parallelism
     */
    public ConfigurableIteratorSpliterator(Iterator<? extends T> iterator, long size, int characteristics, int[] batchSizes) {
        this.iterator = iterator;
        this.sizeEstimate = size;
        this.characteristics = (characteristics & Spliterator.CONCURRENT) == 0
                ? characteristics | Spliterator.SIZED | Spliterator.SUBSIZED
                : characteristics;
        this.batchSizes = Arrays.copyOf(batchSizes, batchSizes.length);
    }

    /**
     * Creates a {@link Spliterator} using the given iterator for traversal, and
     * reporting the given initial size and characteristics.
     *
     * @param iterator the iterator for the source
     * @param characteristics properties of this {@link Spliterator}'s source or
     * elements.
     * @param batchSizes the batch sizes to use for pseudo parallelism
     */
    public ConfigurableIteratorSpliterator(Iterator<? extends T> iterator, int characteristics, int[] batchSizes) {
        this.iterator = iterator;
        this.sizeEstimate = Long.MAX_VALUE;
        this.characteristics = characteristics & ~(Spliterator.SIZED | Spliterator.SUBSIZED);
        this.batchSizes = Arrays.copyOf(batchSizes, batchSizes.length);
    }

    protected int nextBatchSize() {
        if (batchSizeIndex >= batchSizes.length) {
            return batchSizes[batchSizes.length - 1];
        } else {
            return batchSizes[batchSizeIndex++];
        }
    }

    @Override
    public Spliterator<T> trySplit() {
        if (sizeEstimate > 1 && iterator.hasNext()) {
            int batchSize = nextBatchSize();
            if (batchSize > sizeEstimate) {
                batchSize = (int) sizeEstimate;
            }
            if (batchSize > MAX_BATCH) {
                batchSize = MAX_BATCH;
            }
            final Object[] array = new Object[batchSize];
            int noRead = 0;
            do {
                array[noRead] = iterator.next();
            } while (++noRead < batchSize && iterator.hasNext());
            if (sizeEstimate != Long.MAX_VALUE) {
                sizeEstimate -= noRead;
            }
            return new ArraySpliterator<>(array, 0, noRead, characteristics);
        }
        return null;
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        iterator.forEachRemaining(requireNonNull(action));
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        requireNonNull(action);
        if (iterator.hasNext()) {
            action.accept(iterator.next());
            return true;
        }
        return false;
    }

    @Override
    public long estimateSize() {
        return sizeEstimate;
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
