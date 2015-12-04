package com.speedment.internal.core.stream.parallelstrategy;

import com.speedment.stream.ParallelStrategy;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.IntStream;

/**
 *
 * @author pemi
 */
public final class ComputeIntensityMediumParallelStrategy implements ParallelStrategy {

    private final static int[] BATCH_SIZES = IntStream.range(4, 14)
            .map(ComputeIntensityUtil::toThePowerOfTwo)
            .toArray();

    @Override
    public <T> Spliterator<T> spliteratorUnknownSize(Iterator<? extends T> iterator, int characteristics) {
        return new ConfigurableIteratorSpliterator<>(iterator, characteristics, BATCH_SIZES);
    }

}
