package com.speedment.common.singletonstream.internal;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.IntConsumer;

public final class SingletonPrimitiveSpliteratorOfInt implements Spliterator.OfInt {

    private final int element;
    private long estimatedSize = 1;

    public SingletonPrimitiveSpliteratorOfInt(int element) {
        this.element = element;
    }

    @Override
    public OfInt trySplit() {
        return null;
    }

    @Override
    public boolean tryAdvance(IntConsumer consumer) {
        Objects.requireNonNull(consumer);
        if (estimatedSize > 0) {
            estimatedSize--;
            consumer.accept(element);
            return true;
        }
        return false;
    }

    @Override
    public void forEachRemaining(IntConsumer consumer) {
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
