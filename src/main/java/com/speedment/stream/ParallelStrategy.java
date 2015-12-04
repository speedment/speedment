package com.speedment.stream;

import com.speedment.internal.core.stream.parallelstrategy.ComputeIntensityMediumParallelStrategy;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;

/**
 *
 * @author pemi
 */
@FunctionalInterface
public interface ParallelStrategy {

    static final ParallelStrategy DEFAULT = Spliterators::spliteratorUnknownSize;
    static final ParallelStrategy COMPUTE_INTENSITY_MEDIUM = new ComputeIntensityMediumParallelStrategy();
    
    <T> Spliterator<T> spliteratorUnknownSize(Iterator<? extends T> iterator, int characteristics);
    
}
