/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.stream;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.internal.stream.parallelstrategy.ComputeIntensityExtremeParallelStrategy;
import com.speedment.runtime.internal.stream.parallelstrategy.ComputeIntensityHighParallelStrategy;
import com.speedment.runtime.internal.stream.parallelstrategy.ComputeIntensityMediumParallelStrategy;
import com.speedment.runtime.internal.stream.parallelstrategy.ConfigurableIteratorSpliterator;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;

/**
 *
 * @author  Per Minborg
 */
@FunctionalInterface
@Api(version = "3.0")
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
     * A Parallel Strategy that favors small sets with extremely high computational
     * overhead. The set will be split up in solitary elements that are executed
     * separately in their own thread.
     */
    static final ParallelStrategy COMPUTE_INTENSITY_EXTREME = new ComputeIntensityExtremeParallelStrategy();

    <T> Spliterator<T> spliteratorUnknownSize(Iterator<? extends T> iterator, int characteristics);

    public static <T> ParallelStrategy of(final int[] batchSizes)  {
        return new ParallelStrategy() {
            @Override
            public <T> Spliterator<T> spliteratorUnknownSize(Iterator<? extends T> iterator, int characteristics) {
                return new ConfigurableIteratorSpliterator<>(iterator, characteristics, batchSizes);
            }
        };
    }
    
}
