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
package com.speedment.common.combinatorics.internal;

import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * General Permutation support from
 * http://minborgsjavapot.blogspot.com/2015/07/java-8-master-permutations.html
 *
 * @author Per Minborg
 */
public final class PermutationUtil {

    private PermutationUtil() {}

    public static long factorial(final int n) {
        if (n > 20 || n < 0) {
            throw new IllegalArgumentException(n + " is out of range");
        }
        return LongStream.rangeClosed(2, n).reduce(1, (a, b) -> a * b);
    }

    public static <T> List<T> permutation(final long no, final List<T> items) {
        return permutationHelper(no,
            new LinkedList<>(Objects.requireNonNull(items)),
            new ArrayList<>());
    }

    private static <T> List<T> permutationHelper(final long no, final LinkedList<T> in, final List<T> out) {
        if (in.isEmpty()) {
            return out;
        }
        final long subFactorial = factorial(in.size() - 1);
        out.add(in.remove((int) (no / subFactorial)));
        return permutationHelper((int) (no % subFactorial), in, out);
    }

    @SafeVarargs
    @SuppressWarnings("varargs") // Creating a List from an array is safe
    public static <T> Stream<Stream<T>> of(final T... items) {
        return of(Arrays.asList(items));
    }

    public static <T> Stream<Stream<T>> of(final List<T> items) {
        return LongStream.range(0, factorial(items.size()))
            .mapToObj(no -> permutation(no, items).stream());
    }

}
