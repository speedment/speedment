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
