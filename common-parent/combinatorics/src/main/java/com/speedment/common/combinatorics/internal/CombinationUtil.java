/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.combinatorics.internal;

import com.speedment.common.combinatorics.*;
import java.util.*;
import java.util.function.Consumer;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 * General Combination support. The class eagerly calculates all combinations.
 *
 * @author Per Minborg
 */
public final class CombinationUtil {

    /**
     * Creates and returns all possible combinations of the given elements.
     *
     * The order of the combinations in the stream is unspecified.
     *
     * @param <T> element type
     * @param items to combine
     * @return all possible combinations of the given elements
     */
    @SafeVarargs
    @SuppressWarnings("varargs") // Creating a List from an array is safe
    public static <T> Stream<List<T>> of(final T... items) {
        final Stream.Builder<List<T>> builder = Stream.builder();
        final List<T> database = Arrays.asList(items);
        final List<T> buffer = new ArrayList<>();
        database.stream().map(c -> (T) null).forEach(buffer::add);
        int k = database.size();
        for (int i = 1; i <= k; i++) {
            generateCombinations(database, 0, i, buffer, l -> builder.add(
                l.stream()
                    .filter(Objects::nonNull)
                    .collect(toList())
            ));
        }
        return builder.build();
    }

    /**
     * Creates and returns all possible combinations of the given elements
     * whereby each element only occurs at most once in any given List. Elements
     * are checked for occurrence by its {@code equals() } method.
     *
     * The order of the combinations in the stream is unspecified.
     *
     * @param <T> element type
     * @param items to combine
     * @return all possible combinations of the given elements whereby each
     * element only occurs at most once in any given List
     */
    @SafeVarargs
    @SuppressWarnings("varargs") // Creating a List from an array is safe
    public static <T> Stream<List<T>> ofDistinct(final T... items) {
        return of(items).filter(CombinationUtil::isDistinctElements);
    }

    private static <T> boolean isDistinctElements(List<T> list) {
        final Set<T> discovered = Collections.newSetFromMap(new HashMap<>());
        return list.stream().allMatch(discovered::add);
    }

    private static <T> void generateCombinations(List<T> s, int i, int k, List<T> buff, Consumer<List<T>> consumer) {
        if (i < k) {
            for (int j = 0; j < s.size(); j++) {
                buff.set(i, s.get(j));
                generateCombinations(s, i + 1, k, buff, consumer);
            }
        } else {
            consumer.accept(buff);
        }
    }

    private CombinationUtil() {
        throw new UnsupportedOperationException();
    }
}
