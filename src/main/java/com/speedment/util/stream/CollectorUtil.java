/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.util.stream;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class CollectorUtil {

    private static final String NULL_TEXT = " must not be null";

    private CollectorUtil() {
    }

    public static <T> T of(Supplier<T> supplier, Consumer<T> modifier, Consumer<T>... additionalModifiers) {
        Objects.requireNonNull(supplier, "supplier " + NULL_TEXT);
        Objects.requireNonNull(modifier, "modifier " + NULL_TEXT);
        final T result = supplier.get();
        modifier.accept(result);
        Stream.of(additionalModifiers).forEach((Consumer<T> c) -> {
            c.accept(result);
        });
        return result;
    }

    public static <I, T> T of(Supplier<I> supplier, Consumer<I> modifier, Function<I, T> finisher) {
        Objects.requireNonNull(supplier, "supplier " + NULL_TEXT);
        Objects.requireNonNull(modifier, "modifier " + NULL_TEXT);
        Objects.requireNonNull(finisher, "finisher " + NULL_TEXT);
        final I intermediateResult = supplier.get();
        modifier.accept(intermediateResult);
        return finisher.apply(intermediateResult);
    }

    public static <T> Collector<T, Set<T>, Set<T>> toUnmodifiableSet() {
        return Collector.of(HashSet::new, Set::add, (left, right) -> {
            left.addAll(right);
            return left;
        }, Collections::unmodifiableSet, Collector.Characteristics.UNORDERED);
    }

}
