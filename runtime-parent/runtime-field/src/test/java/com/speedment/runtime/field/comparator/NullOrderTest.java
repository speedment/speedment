/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.field.comparator;

import static com.speedment.runtime.field.comparator.NullOrder.FIRST;
import static com.speedment.runtime.field.comparator.NullOrder.LAST;
import static com.speedment.runtime.field.comparator.NullOrder.NONE;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class NullOrderTest {

    @Test
    public void testNoneReversed() {
        assertEquals(NONE, NONE.reversed());
    }

    @Test
    public void testFirstReversed() {
        assertEquals(LAST, FIRST.reversed());
    }

    @Test
    public void testLastReversed() {
        assertEquals(FIRST, LAST.reversed());
    }

}
