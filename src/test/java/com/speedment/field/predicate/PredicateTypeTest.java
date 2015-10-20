/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.field.predicate;

import static com.speedment.field.predicate.PredicateType.EQUAL;
import static com.speedment.field.predicate.PredicateType.NOT_EQUAL;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pemi
 */
public class PredicateTypeTest {

    public PredicateTypeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testComplementType() {
        // Make sure that each Type has a (unique) negated type
        final Map<PredicateType, PredicateType> map = new EnumMap(PredicateType.class);

        for (PredicateType pt : PredicateType.values()) {
            final PredicateType negated = pt.negate();
            assertNotNull(negated);
            assertFalse(map.containsKey(negated));
            map.put(negated, pt);
        }

        final List<PredicateType> keys = map.keySet().stream().sorted().collect(toList());
        final List<PredicateType> values = map.values().stream().sorted().collect(toList());
        assertEquals(keys, values);

    }

    @Test
    public void testGetComplementType() {
        System.out.println("getComplementType");
        assertEquals(EQUAL, NOT_EQUAL.negate());
        assertEquals(NOT_EQUAL, EQUAL.negate());
    }

    @Test
    public void testEffectiveType() {
        System.out.println("effectiveType");
        assertEquals(EQUAL, EQUAL.effectiveType(false));
        assertEquals(NOT_EQUAL, EQUAL.effectiveType(true));
    }

}
