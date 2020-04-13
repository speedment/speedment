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
package com.speedment.common.combinatorics;

import com.speedment.common.combinatorics.internal.PermutationUtil;

import java.util.List;
import java.util.stream.Stream;

/**
 * General Permutation support from
 * http://minborgsjavapot.blogspot.com/2015/07/java-8-master-permutations.html
 *
 * @author Per Minborg
 */
public final class Permutation {

    private Permutation() {}

    /**
     * Returns the factorial (n!) for the given {@code n} value.
     *
     * @param n parameter
     * @return the factorial (n!) for the given {@code n} value
     */
    public static long factorial(final int n) {
        return PermutationUtil.factorial(n);
    }

    /**
     * Returns the given {@code no} (of n!) permutation variant of the
     * given {@code items}.
     * <p>
     * Note that the number of permutations increases very rapidly as the length of
     * the provided {@code items} array increases.
     *
     * @param no index to get permutations for
     * @param items to create permutations for
     * @param <T> type of the elements
     * @return the given {@code no} (of n!) permutation variant of the
     *         given {@code items}
     */
    public static <T> List<T> permutation(final long no, final List<T> items) {
        return PermutationUtil.permutation(no, items);
    }

    /**
     * Creates and returns a Stream of all permutations of the provided {@code items} array.
     * <p>
     * Note that the number of permutations increases very rapidly as the length of
     * the provided {@code items} array increases.
     *
     * @param items to create permutations for
     * @param <T> type of the elements
     * @return a Stream of all permutations of the provided {@code items} array
     * @throws NullPointerException is the provided {@code items} array is {@code null}.
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Stream<Stream<T>> of(final T... items) {
        return PermutationUtil.of(items);
    }

    /**
     * Creates and returns a Stream of all permutations of the provided {@code items} list.
     * <p>
     * Note that the number of permutations increases very rapidly as the length of
     * the provided {@code items} list increases.
     *
     * @param items to create permutations for
     * @param <T> type of the elements
     * @return a Stream of all permutations of the provided {@code items} list
     * @throws NullPointerException is the provided {@code items} list is {@code null}.
     */
    public static <T> Stream<Stream<T>> of(final List<T> items) {
        return PermutationUtil.of(items);
    }

}
