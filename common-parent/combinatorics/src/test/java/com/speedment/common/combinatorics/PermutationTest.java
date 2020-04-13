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

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

final class PermutationTest {

    @TestFactory
    Stream<DynamicTest> factorial() {
        return IntStream.range(0, 10)
            .mapToObj(i ->
                DynamicTest.dynamicTest("factorial(" + i + ")",
                    () -> {
                        assertEquals(Permutation.factorial(i), fac(i));
                    })
            );
    }

    @Test
    void factorialTooLarge() {
        assertThrows(IllegalArgumentException.class, () -> {
            Permutation.factorial(21);
        });
    }

    @Test
    void factorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            Permutation.factorial(-1);
        });
    }

    private static final List<Integer> LIST = Arrays.asList(1,2,3);

    /*
        1, 2, 3
        1, 3, 2
        2, 1, 3
        2, 3, 1
        3, 1, 2
        3, 2, 1
    */

    @Test
    void permutation0() {
        assertEquals(Arrays.asList(1, 2, 3), Permutation.permutation(0, LIST));
    }

    @Test
    void permutation1() {
        assertEquals(Arrays.asList(1, 3, 2), Permutation.permutation(1, LIST));
    }

    @Test
    void permutation2() {
        assertEquals(Arrays.asList(2, 1, 3), Permutation.permutation(2, LIST));
    }

    @Test
    void permutation3() {
        assertEquals(Arrays.asList(2, 3, 1), Permutation.permutation(3, LIST));
    }

    @Test
    void permutation4() {
        assertEquals(Arrays.asList(3, 1, 2), Permutation.permutation(4, LIST));
    }

    @Test
    void permutation5() {
        assertEquals(Arrays.asList(3, 2, 1), Permutation.permutation(5, LIST));
    }

    @Test
    void of() {
        final List<List<Integer>> expected = expectedFor123();
        final List<List<Integer>> actual =
            Permutation.of(1,2,3)
            .map(s -> s.collect(toList()))
            .collect(toList());

        assertEquals(expected, actual);
    }

    @Test
    void ofList() {
        final List<List<Integer>> expected = expectedFor123();
        final List<List<Integer>> actual =
            Permutation.of(LIST)
                .map(s -> s.collect(toList()))
                .collect(toList());

        assertEquals(expected, actual);
    }

    private List<List<Integer>> expectedFor123() {
        return Stream.of(
            Arrays.asList(1,2,3),
            Arrays.asList(1,3,2),
            Arrays.asList(2,1,3),
            Arrays.asList(2,3,1),
            Arrays.asList(3,1,2),
            Arrays.asList(3,2,1)
        ).collect(toList());
    }

    private long fac(int i) {
        if (i == 0) return 1;
        return i * fac(i - 1);
    }

}