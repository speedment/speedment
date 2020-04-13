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

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Per Minborg
 */
final class CombinationTest {

    @Test
    void testOfEmpty() {
        final List<List<String>> actual = Combination.<String>of()
            .collect(toList());

        assertTrue(actual.isEmpty());
    }

    @Test
    void testOfSingleton() {
        final List<List<String>> actual = Combination.of("a")
            .collect(toList());

        assertEquals(
            singletonList(
                singletonList("a")
            ),
            actual
        );
    }

    @Test
    void testOfTwo() {
        final Set<List<String>> actual = Combination.of("a", "b")
            .collect(toSet());

        final Set<List<String>> expected = new HashSet<>(asList(
            singletonList("a"),
            singletonList("b"),
            asList("a", "b")
        ));

        assertEquals(expected, actual);

    }

    @Test
    void testOfThree() {
        final Set<List<String>> actual = Combination.of("a", "b", "c")
            .collect(toSet());

        final Set<List<String>> expected = new HashSet<>(asList(
            singletonList("a"),
            singletonList("b"),
            singletonList("c"),
            asList("a", "b"),
            asList("a", "c"),
            asList("b", "c"),
            asList("a", "b", "c")
        ));

        assertEquals(expected, actual);
    }

    @Test
    void testOfTwoList() {
        final Set<List<String>> actual = Combination.of(Arrays.asList("a", "b"))
            .collect(toSet());

        final Set<List<String>> expected = new HashSet<>(asList(
            singletonList("a"),
            singletonList("b"),
            asList("a", "b")
        ));

        assertEquals(expected, actual);

    }
}
