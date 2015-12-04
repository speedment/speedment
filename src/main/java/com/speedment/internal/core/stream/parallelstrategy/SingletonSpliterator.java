package com.speedment.internal.core.stream.parallelstrategy;

import java.util.Comparator;
import static java.util.Objects.requireNonNull;
import java.util.Spliterator;
import java.util.function.Consumer;

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
