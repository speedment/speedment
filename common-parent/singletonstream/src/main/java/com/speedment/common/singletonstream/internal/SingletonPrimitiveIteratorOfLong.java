package com.speedment.common.singletonstream.internal;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.function.LongConsumer;

import static java.util.Objects.requireNonNull;

public final class SingletonPrimitiveIteratorOfLong implements PrimitiveIterator.OfLong {

    private final long element;
    private boolean hasNext = true;

    public SingletonPrimitiveIteratorOfLong(long element) {
        this.element = element;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public long nextLong() {
        if (hasNext) {
            hasNext = false;
            return element;
        }
        throw new NoSuchElementException();
    }

    @Override
    public Long next() {
        if (!hasNext) {
            throw new NoSuchElementException();
        }
        return nextLong();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEachRemaining(LongConsumer action) {
        requireNonNull(action);
        if (hasNext) {
            action.accept(element);
            hasNext = false;
        }
    }
}
