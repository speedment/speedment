package com.speedment.internal.core.stream.parallelstrategy;

import com.speedment.stream.ParallelStrategy;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.IntStream;

/**
 *
 * @author pemi
 */
public final class ComputeIntensityHighParallelStrategy implements ParallelStrategy {

    IntStream a = null;

    private final static int[] BATCH_SIZES = IntStream.range(0, 8)
            .map(ComputeIntensityUtil::toThePowerOfTwo)
            .flatMap(ComputeIntensityUtil::repeatOnHalfAvailableProcessors)
            .toArray();

    @Override
    public <T> Spliterator<T> spliteratorUnknownSize(Iterator<? extends T> iterator, int characteristics) {
        return new ConfigurableIteratorSpliterator<>(iterator, characteristics, BATCH_SIZES);
    }

}
