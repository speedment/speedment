/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.stream.parallel;

import com.speedment.runtime.core.stream.parallel.ConfigurableIteratorSpliterator;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;

import java.util.Iterator;
import java.util.Spliterator;

/**
 *
 * @author pemi
 */
public final class ComputeIntensityExtremeParallelStrategy implements ParallelStrategy {

    private static final int[] BATCH_SIZES = {1};

    @Override
    public <T> Spliterator<T> spliteratorUnknownSize(Iterator<? extends T> iterator, int characteristics) {
        return ConfigurableIteratorSpliterator.of(iterator, characteristics, BATCH_SIZES);
    }

}
