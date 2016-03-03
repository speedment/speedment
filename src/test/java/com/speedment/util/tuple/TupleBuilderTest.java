/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.util.tuple;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Per Minborg
 */
public class TupleBuilderTest {

    public TupleBuilderTest() {
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
    public void testBuilder() {
        final Tuple expected = Tuples.of("Arne", 1, 3L);
        final Tuple notExpected = Tuples.of("Arne", 1, 3L, "Tryggve");
        final Tuple result = TupleBuilder.builder().add("Arne").add(1).add(3L).build();
        assertEquals(expected, result);
        assertNotEquals(notExpected, result);
    }

}
