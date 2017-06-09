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

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.function.Function.identity;

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
        return IntStream.rangeClosed(1, items.length)
            .mapToObj(r -> {
                @SuppressWarnings("unchecked")
                final T[] data = (T[]) new Object[r];
                return combinationHelper(items, data, 0, items.length - 1, 0, r);
            }).flatMap(identity());
    }

    /**
     *
     * @param <T>    the element type
     *
     * @param arr    input array
     * @param data   temporary array to store current combination
     * @param start  starting index in arr[]
     * @param end    ending index in arr[]
     * @param index  current index in data[]
     * @param r      size of a combination to be printed
     *
     * @return       stream of possible combinations
     */
    private static <T> Stream<List<T>> combinationHelper(
            T[] arr, T[] data,
            int start, int end,
            int index, int r) {

        // Current combination is ready to be printed, print it
        if (index == r) {
            return Stream.of(asList(data, r));
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        return IntStream.rangeClosed(start, end)
            .filter(i -> end - i + 1 >= r - index)
            .mapToObj(i -> {
                data[index] = arr[i];
                return combinationHelper(arr, data, i + 1, end, index + 1, r);
            }).flatMap(identity());
    }

    private static <T> List<T> asList(T[] array, int newSize) {
        final int limit = Math.min(newSize, array.length);
        switch (limit) {
            case 0 : return emptyList();
            case 1 : return singletonList(array[0]);
            default : {
                final List<T> list = new ArrayList<>(limit);
                for (int i = 0; i < limit; i++) {
                    list.add(array[i]);
                }
                return list;
            }
        }
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
