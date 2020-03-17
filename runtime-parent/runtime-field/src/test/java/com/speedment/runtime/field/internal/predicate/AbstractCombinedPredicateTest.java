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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.field.internal.predicate;

import com.speedment.runtime.field.predicate.CombinedPredicate;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Per Minborg
 */
final class AbstractCombinedPredicateTest {

    private static final Predicate<Integer> MOD2 = new Predicate<Integer>() {
        @Override
        public boolean test(Integer i) {
            return i % 2 == 0;
        }

        @Override
        public String toString() {
            return "MOD2";
        }
    };
    private static final Predicate<Integer> MOD4 = new Predicate<Integer>() {
        @Override
        public boolean test(Integer i) {
            return i % 4 == 0;
        }

        @Override
        public String toString() {
            return "MOD4";
        }
    };
    private static final Predicate<Integer> MOD8 = new Predicate<Integer>() {
        @Override
        public boolean test(Integer i) {
            return i % 8 == 0;
        }

        @Override
        public String toString() {
            return "MOD8";
        }
    };

    private static final Predicate<Integer> MOD3 = new Predicate<Integer>() {
        @Override
        public boolean test(Integer i) {
            return i % 3 == 0;
        }

        @Override
        public String toString() {
            return "MOD3";
        }
    };

    @Test
    void testStream() {
        final Set<Predicate<Integer>> set = Stream.of(MOD2, MOD4, MOD8).collect(toSet());

        CombinedPredicate<Integer> p = CombinedPredicate.and(MOD2, MOD4);
        p = p.and(MOD8);
        assertTrue(p.stream().allMatch(set::contains));
    }

    @Test
    void testSize() {
        CombinedPredicate<Integer> p = CombinedPredicate.and(MOD2, MOD4);
        p = p.and(MOD8);
        final long actual = p.stream().count();
        assertEquals(3, actual);
    }

    @Test
    void testGetPredicates() {
        CombinedPredicate<Integer> p = new AbstractCombinedPredicate.AndCombinedBasePredicateImpl<>(Arrays.asList(MOD2, MOD4));
        p = p.and(MOD8);
        final List<Predicate<Integer>> expected = Arrays.asList(MOD2, MOD4, MOD8);
        final List<Predicate<? super Integer>> actual = p.stream().collect(toList());
        assertEquals(expected, actual);
    }

    @Test
    void testGetType() {
        final CombinedPredicate<Integer> or = CombinedPredicate.or(MOD2, MOD4);
        assertEquals(CombinedPredicate.Type.OR, or.getType());
        final CombinedPredicate<Integer> and = CombinedPredicate.and(MOD2, MOD4);
        assertEquals(CombinedPredicate.Type.AND, and.getType());

        final CombinedPredicate<Integer> orComposed = CombinedPredicate.or(and, or);
        assertEquals(CombinedPredicate.Type.OR, orComposed.getType());
        final CombinedPredicate<Integer> andComposed = CombinedPredicate.and(and, or);
        assertEquals(CombinedPredicate.Type.AND, andComposed.getType());

    }

    @Test
    void testAnd() {
        CombinedPredicate<Integer> p = CombinedPredicate.and(MOD2, MOD4);
        p = p.and(MOD8);

        for (int i = 0; i < 100; i++) {
            boolean expected = MOD2.test(i) && MOD4.test(i) && MOD8.test(i);
            boolean actual = p.test(i);
            assertEquals(expected, actual);
        }

    }

    @Test
    void testOr() {
        CombinedPredicate<Integer> p = CombinedPredicate.or(MOD2, MOD4);
        p = p.or(MOD8);

        for (int i = 0; i < 100; i++) {
            boolean expected = MOD2.test(i) || MOD4.test(i) || MOD8.test(i);
            boolean actual = p.test(i);
            assertEquals(expected, actual);
        }
    }

    @Test
    void testComposed() {
        CombinedPredicate<Integer> p = CombinedPredicate.and(MOD2, MOD4);
        p = p.and(MOD8);
        p = p.or(MOD3);
        for (int i = 0; i < 100; i++) {
            boolean expected = (MOD2.test(i) && MOD4.test(i) && MOD8.test(i)) || MOD3.test(i);
            boolean actual = p.test(i);
            assertEquals(expected, actual);
        }
    }

    @Test
    void testNegate() {
        CombinedPredicate<Integer> p = CombinedPredicate.and(MOD2, MOD4);
        p = p.and(MOD8);
        p = p.negate();
        for (int i = 0; i < 100; i++) {
            boolean expected = !(MOD2.test(i) && MOD4.test(i) && MOD8.test(i));
            boolean actual = p.test(i);
            assertEquals(expected, actual);
        }
    }
}
