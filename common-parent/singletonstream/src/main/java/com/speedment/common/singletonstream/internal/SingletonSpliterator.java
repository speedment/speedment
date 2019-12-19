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
