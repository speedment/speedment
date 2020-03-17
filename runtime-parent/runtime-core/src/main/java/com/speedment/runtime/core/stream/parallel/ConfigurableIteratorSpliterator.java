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
package com.speedment.runtime.core.stream.parallel;

import com.speedment.runtime.core.internal.stream.parallel.ConfigurableIteratorSpliteratorImpl;

import java.util.Iterator;
import java.util.Spliterator;

/**
 *
 * @author pemi
 * @param <T> type of {@link Spliterator} to implement
 */
public interface ConfigurableIteratorSpliterator<T> extends Spliterator<T> {

    static <T> Spliterator<T> of(Iterator<? extends T> iterator, long size, int characteristics, int[] batchSizes) {
        return new ConfigurableIteratorSpliteratorImpl<>(iterator, size, characteristics, batchSizes);
    }

    static <T> Spliterator<T> of(Iterator<? extends T> iterator, int characteristics, int[] batchSizes) {
        return new ConfigurableIteratorSpliteratorImpl<>(iterator, characteristics, batchSizes);
    }
}