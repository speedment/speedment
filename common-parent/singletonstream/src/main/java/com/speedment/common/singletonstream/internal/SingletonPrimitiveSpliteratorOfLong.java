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
