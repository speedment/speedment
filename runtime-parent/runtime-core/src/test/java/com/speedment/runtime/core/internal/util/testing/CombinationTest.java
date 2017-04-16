/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.core.internal.util.testing;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class CombinationTest {

    @Test
    public void testOfEmpty() {
        final List<List<String>> actual = Combination.<String>of()
            .collect(toList());
        assertTrue(actual.isEmpty());
    }

    @Test
    public void testOfSingleton() {
        final List<List<String>> actual = Combination.of("a")
            .collect(toList());

        assertEquals(
            Arrays.asList(
                Arrays.asList("a")
            ),
            actual
        );
    }

    @Test
    public void testOfTwo() {
        final Set<List<String>> actual = Combination.of("a", "b")
            .collect(toSet());

        final Set<List<String>> expected = Arrays.asList(
            Arrays.asList("a"),
            Arrays.asList("a", "a"),
            Arrays.asList("a", "b"),
            Arrays.asList("b"),
            Arrays.asList("b", "b"),
            Arrays.asList("b", "a")
        ).stream().collect(toSet());

        assertEquals(expected, actual);

    }

    @Test
    public void testOfThree() {
        Set<List<String>> actual = Combination.of("a", "b", "c")
            .collect(toSet());

        final Set<List<String>> expected = Arrays.asList(
            Arrays.asList("a"),
            Arrays.asList("b"),
            Arrays.asList("c"),
            Arrays.asList("a", "a"),
            Arrays.asList("a", "b"),
            Arrays.asList("a", "c"),
            Arrays.asList("b", "a"),
            Arrays.asList("b", "b"),
            Arrays.asList("b", "c"),
            Arrays.asList("c", "a"),
            Arrays.asList("c", "b"),
            Arrays.asList("c", "c"),
            Arrays.asList("a", "a", "a"),
            Arrays.asList("a", "a", "b"),
            Arrays.asList("a", "a", "c"),
            Arrays.asList("a", "b", "a"),
            Arrays.asList("a", "b", "b"),
            Arrays.asList("a", "b", "c"),
            Arrays.asList("a", "c", "a"),
            Arrays.asList("a", "c", "b"),
            Arrays.asList("a", "c", "c"),
            Arrays.asList("b", "a", "a"),
            Arrays.asList("b", "a", "b"),
            Arrays.asList("b", "a", "c"),
            Arrays.asList("b", "b", "a"),
            Arrays.asList("b", "b", "b"),
            Arrays.asList("b", "b", "c"),
            Arrays.asList("b", "c", "a"),
            Arrays.asList("b", "c", "b"),
            Arrays.asList("b", "c", "c"),
            Arrays.asList("c", "a", "a"),
            Arrays.asList("c", "a", "b"),
            Arrays.asList("c", "a", "c"),
            Arrays.asList("c", "b", "a"),
            Arrays.asList("c", "b", "b"),
            Arrays.asList("c", "b", "c"),
            Arrays.asList("c", "c", "a"),
            Arrays.asList("c", "c", "b"),
            Arrays.asList("c", "c", "c")
        ).stream().collect(toSet());

        assertEquals(expected, actual);

    }

    @Test
    public void testOfUniqueEmpty() {
        final List<List<String>> actual = Combination.<String>of()
            .collect(toList());
        assertTrue(actual.isEmpty());
    }

    @Test
    public void testOfUniqueSingleton() {
        final List<List<String>> actual = Combination.ofUniqueIdentity("a")
            .collect(toList());

        assertEquals(
            Arrays.asList(
                Arrays.asList("a")
            ),
            actual
        );
    }

    @Test
    public void testUniqueOfTwo() {
        final Set<List<String>> actual = Combination.ofUniqueIdentity("a", "b")
            .collect(toSet());

        final Set<List<String>> expected = Arrays.asList(
            Arrays.asList("a"),
            Arrays.asList("a", "b"),
            Arrays.asList("b"),
            Arrays.asList("b", "a")
        ).stream().collect(toSet());

        assertEquals(expected, actual);

    }

    @Test
    public void testOfUniqueThree() {
        Set<List<String>> actual = Combination.ofUniqueIdentity("a", "b", "c")
            .collect(toSet());

        final Set<List<String>> expected = Arrays.asList(
            Arrays.asList("a"),
            Arrays.asList("b"),
            Arrays.asList("c"),
            Arrays.asList("a", "b"),
            Arrays.asList("a", "c"),
            Arrays.asList("b", "a"),
            Arrays.asList("b", "c"),
            Arrays.asList("c", "a"),
            Arrays.asList("c", "b"),
            Arrays.asList("a", "b", "c"),
            Arrays.asList("a", "c", "b"),
            Arrays.asList("b", "a", "c"),
            Arrays.asList("b", "c", "a"),
            Arrays.asList("c", "a", "b"),
            Arrays.asList("c", "b", "a")
        ).stream().collect(toSet());

        assertEquals(expected, actual);
    }

}
