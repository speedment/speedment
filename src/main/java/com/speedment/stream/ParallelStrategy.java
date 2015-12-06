package com.speedment.stream;

import com.speedment.internal.core.stream.parallelstrategy.ComputeIntensityExtremeParallelStrategy;
import com.speedment.internal.core.stream.parallelstrategy.ComputeIntensityHighParallelStrategy;
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

    /**
     * A Parallel Strategy that is Java's default <code>Iterator</code> to
     * <code>Spliterator</code> converter. It favors relatively large sets (in
     * the ten thousands or more) with low computational overhead.
     */
    static final ParallelStrategy DEFAULT = Spliterators::spliteratorUnknownSize;
    /**
     * A Parallel Strategy that favors relatively small to medium sets with
     * medium computational overhead.
     */
    static final ParallelStrategy COMPUTE_INTENSITY_MEDIUM = new ComputeIntensityMediumParallelStrategy();
    /**
     * A Parallel Strategy that favors relatively small to medium sets with high
     * computational overhead.
     */
    static final ParallelStrategy COMPUTE_INTENSITY_HIGH = new ComputeIntensityHighParallelStrategy();
    /**
     * A Parallel Strategy that favors sets with extremely high computational
     * overhead. The set will be split up in solitary elements that are executed
     * separately in their own thread.
     */
    static final ParallelStrategy COMPUTE_INTENSITY_EXTREME = new ComputeIntensityExtremeParallelStrategy();

    <T> Spliterator<T> spliteratorUnknownSize(Iterator<? extends T> iterator, int characteristics);

}
