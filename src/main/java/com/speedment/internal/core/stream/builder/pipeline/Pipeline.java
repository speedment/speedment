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
package com.speedment.internal.core.stream.builder.pipeline;

import com.speedment.internal.core.stream.builder.action.Action;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public interface Pipeline extends Iterable<Action<?, ?>> {

    Action<?, ?> getFirst();

    Action<?, ?> getLast();

    Action<?, ?> removeFirst();

    Action<?, ?> removeLast();

    void addFirst(Action<?, ?> e);

    void addLast(Action<?, ?> e);

    int size();

    boolean add(Action<?, ?> e);

    void clear();

    Action<?, ?> get(int index);

    void add(int index, Action<?, ?> element);

    Action<?, ?> remove(int index);

    boolean isEmpty();

    Stream<Action<?, ?>> stream();

    Supplier<BaseStream<?, ?>> getInitialSupplier();

    void setInitialSupplier(Supplier<BaseStream<?, ?>> initialSupplier);

    /**
     * Returns whether this pipeline, if a terminal operation were to be
     * executed, would execute in parallel.
     *
     * @return {@code true} if this pipeline would execute in parallel if
     * executed
     */
    boolean isParallel();

    /**
     * Sets if this Pipeline is parallel.
     *
     * @param flag <code>true</code> if the Pipeline is parallel,
     * <code>false</code> if the Pipeline is sequential
     */
    void setParallel(boolean flag);
    
    
        /**
     * Returns whether this pipeline, if a terminal operation were to be
     * executed, would execute in parallel.
     *
     * @return {@code true} if this pipeline would execute in parallel if
     * executed
     */
    boolean isOrdered();

    /**
     * Sets if this Pipeline is ordered.
     *
     * @param flag <code>true</code> if the Pipeline is ordered,
     * <code>false</code> if the Pipeline is unordered
     */
    void setOrdered(boolean flag);
    

}
