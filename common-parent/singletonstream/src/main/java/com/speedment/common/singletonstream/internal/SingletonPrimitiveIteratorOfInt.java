package com.speedment.common.singletonstream.internal;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.function.IntConsumer;

import static java.util.Objects.requireNonNull;

public final class SingletonPrimitiveIteratorOfInt implements PrimitiveIterator.OfInt {

    private final int element;
    private boolean hasNext = true;

    public SingletonPrimitiveIteratorOfInt(int e) {
        this.element = e;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public int nextInt() {
        if (hasNext) {
            hasNext = false;
            return element;
        }
        throw new NoSuchElementException();
    }

    @Override
    public Integer next() {
        if (!hasNext) {
            throw new NoSuchElementException();
        }
        return nextInt();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEachRemaining(IntConsumer action) {
        requireNonNull(action);
        if (hasNext) {
            action.accept(element);
            hasNext = false;
        }
    }

}
