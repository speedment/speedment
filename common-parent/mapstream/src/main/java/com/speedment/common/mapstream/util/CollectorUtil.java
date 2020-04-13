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
package com.speedment.common.mapstream.util;

import com.speedment.common.mapstream.MapStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * Utility methods for collecting Speedment streams in various ways.
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.1.0
 */
public final class CollectorUtil {

    private CollectorUtil() {}

    /**
     * Returns a new {@link MapStream} where the elements have been grouped together using
     * the specified function.
     * 
     * @param <T>      the stream element type
     * @param <C>      the type of the key to group by
     * @param grouper  the function to use for grouping
     * @return         a {@link MapStream} grouped by key
     */
    public static <T, C> Collector<T, ?, MapStream<C, List<T>>> groupBy(Function<T, C> grouper) {
        return Collector.of(
            () -> new GroupHolder<>(grouper),
            GroupHolder::add,
            GroupHolder::merge,
            GroupHolder::finisher
        );
    }

    protected static class GroupHolder<C, T> {

        private final Function<T, C> grouper;
        private final Map<C, List<T>> elements;

        private final Function<C, List<T>> createList = c -> new ArrayList<>();

        protected GroupHolder(Function<T, C> grouper) {
            this.grouper = grouper;
            this.elements = new HashMap<>();
        }

        public void add(T element) {
            final C key = grouper.apply(element);
            elements.computeIfAbsent(key, createList)
                .add(element);
        }

        public GroupHolder<C, T> merge(GroupHolder<C, T> holder) {
            holder.elements.entrySet().forEach(e
                -> elements.computeIfAbsent(e.getKey(), createList)
                .addAll(e.getValue())
            );

            return this;
        }

        public MapStream<C, List<T>> finisher() {
            return MapStream.of(elements);
        }
    }

}