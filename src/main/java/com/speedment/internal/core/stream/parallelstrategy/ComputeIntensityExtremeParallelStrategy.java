package com.speedment.internal.core.stream.parallelstrategy;

import com.speedment.stream.ParallelStrategy;
import java.util.Iterator;
import java.util.Spliterator;

/**
 *
 * @author pemi
 */
public final class ComputeIntensityExtremeParallelStrategy implements ParallelStrategy {

    private final static int[] BATCH_SIZES = {1};

    @Override
    public <T> Spliterator<T> spliteratorUnknownSize(Iterator<? extends T> iterator, int characteristics) {
        return new ConfigurableIteratorSpliterator<>(iterator, characteristics, BATCH_SIZES);
    }

}
