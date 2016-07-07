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
package com.speedment.common.codegen.internal.util;

import java.util.Collections;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.speedment.common.codegen.internal.util.StaticClassUtil.instanceNotAllowed;

/**
 * Utility methods for collecting streams in various ways.
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.1
 */
public final class CollectorUtil {

    /**
     * Similar to the 
     * {@link Collectors#joining(CharSequence, CharSequence, CharSequence)}
     * method except that this method surrounds the result with the specified
     * {@code prefix} and {@code suffix} even if the stream is empty.
     *
     * @param delimiter  the delimiter to separate the strings
     * @param prefix     the prefix to put before the result
     * @param suffix     the suffix to put after the result
     * @return           a {@link Collector} for joining string elements
     */
    public static Collector<String, ?, String> joinIfNotEmpty(String delimiter, String prefix, String suffix) {
        return new CollectorImpl<>(
                () -> new StringJoiner(delimiter),
                StringJoiner::add,
                StringJoiner::merge,
                s -> s.length() > 0
                        ? prefix + s + suffix
                        : s.toString(),
                Collections.emptySet()
        );
    }

    /**
     * Simple implementation class for {@code Collector}.
     *
     * @param <T> the type of elements to be collected
     * @param <A> the type of the intermediate holder
     * @param <R> the type of the result
     */
    static class CollectorImpl<T, A, R> implements Collector<T, A, R> {

        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Collector.Characteristics> characteristics;

        CollectorImpl(Supplier<A> supplier,
                BiConsumer<A, T> accumulator,
                BinaryOperator<A> combiner,
                Function<A, R> finisher,
                Set<Collector.Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        @SuppressWarnings("unchecked")
        CollectorImpl(Supplier<A> supplier,
                BiConsumer<A, T> accumulator,
                BinaryOperator<A> combiner,
                Set<Collector.Characteristics> characteristics) {

            this(supplier, accumulator, combiner, i -> (R) i, characteristics);
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }

        @Override
        public Set<Collector.Characteristics> characteristics() {
            return characteristics;
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private CollectorUtil() {
        instanceNotAllowed(getClass());
    }
}
